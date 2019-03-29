package ru.wdsoft.ui.datafield;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ru.wdsoft.R;
import ru.wdsoft.main.AppMode;
import ru.wdsoft.main.WDData;
import ru.wdsoft.ui.field.FullLengthListView;
import ru.wdsoft.utils.CastUtils;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;
import ru.wdsoft.utils.ViewUtils;

/**
 * Created by slaventii@mail.ru on 25.02.2019.
 */
public class EditDataField extends FrameLayout implements View.OnClickListener,
        TextView.OnEditorActionListener {

    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    public final static int TYPE_NONE = 0;
    public final static int TYPE_DATE = 1;
    public final static int TYPE_TIME = 2;
    public final static int TYPE_REF = 3;
    public final static int TYPE_INT = 4;
    public final static int TYPE_READ_ONLY = 5;
    public final static int TYPE_CHECK_BOX = 6;
    public final static int TYPE_LIST = 7;
    public final static int TYPE_DATETIME = 8;
    public final static int TYPE_TIME150 = 9;
    public final static int TYPE_PHONE = 10;
    public final static int TYPE_MONEY = 11;
    public final static int TYPE_CHECK_LIST = 12;
    public final static int TYPE_EMAIL = 13;
    public final static int TYPE_ADDRESS = 14;
    public final static int TYPE_PASSW = 15;

    private int mType = TYPE_NONE;

    private AutoCompleteTextView atvValue;
    private ImageView ivRight;
    private TextInputLayout tlBorder;

    private boolean mGroupExpanded = false;
    private boolean mGroupSelected = false;

    private LinearLayout llGroup;
    private TextView tvGroupName;
    private TextView tvGroupIndicator;
    private FrameLayout flDetail, flValue;
    private View vGroupBottomLine;
    private ListView lvValues;

    private String mId, mCaption;

    protected IUIFieldDataModel mIUIFieldDataModel;

    protected OnValueChangedListener mValueChangedListener;

    public EditDataField(Context context, AttributeSet attrs) {

        super(context, attrs);

        this.setOnClickListener(this);

        inflate(context, R.layout.edit_field, this);

        atvValue = findViewById(R.id.atvValue);
        atvValue.setOnClickListener(this);
        atvValue.setOnEditorActionListener(this);

        ivRight = findViewById(R.id.ivRight);
        ivRight.setOnClickListener(this);

        tlBorder = findViewById(R.id.tlBorder);

        llGroup = findViewById(R.id.llGroup);
        tvGroupName = findViewById(R.id.tvGroupName);
        tvGroupIndicator = findViewById(R.id.tvGroupIndicator);
        flDetail = findViewById(R.id.flDetail);
        vGroupBottomLine = findViewById(R.id.vGroupBottomLine);
        flValue = findViewById(R.id.flValue);
        lvValues = findViewById(R.id.lvValues);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.EditDataField);
        initField(styledAttrs);
    }

    /**
     * Инициализация поля (интерфейс)
     * @param styledAttrs
     */
    private void initField(TypedArray styledAttrs){

        if (styledAttrs == null) return;

        if (styledAttrs.hasValue(R.styleable.EditDataField_efld_group_name)) {
            mCaption = styledAttrs.getString(R.styleable.EditDataField_efld_group_name);
            tvGroupName.setText(mCaption);
        }

        boolean group_visible = false;

        if (styledAttrs.hasValue(R.styleable.EditDataField_efld_group)) {
            group_visible = styledAttrs.getBoolean(R.styleable.EditDataField_efld_group,
                    false);
        }

        if (group_visible){
            llGroup.setVisibility(VISIBLE);
            vGroupBottomLine.setVisibility(VISIBLE);
            flDetail.setVisibility(GONE);
            llGroup.setOnClickListener(this);

            int paddingDp = 32;
            float density = getContext().getResources().getDisplayMetrics().density;
            int paddingPixel = (int)(paddingDp * density);
            flDetail.setPadding(0,0,paddingPixel,0);
        } else {
            llGroup.setVisibility(GONE);
            vGroupBottomLine.setVisibility(GONE);
            flDetail.setVisibility(VISIBLE);
        }
        setGroupSelected(false);

        if (styledAttrs.hasValue(R.styleable.EditDataField_efld_hint)) {
        }

        if (styledAttrs.hasValue(R.styleable.EditDataField_efld_caption)) {
            mCaption = styledAttrs.getString(R.styleable.EditDataField_efld_caption);
            tlBorder.setHint(mCaption);
        }

        if (styledAttrs.hasValue(R.styleable.EditDataField_efld_right_icon)) {
            int id = styledAttrs.getResourceId(R.styleable.EditDataField_efld_right_icon,0);
            ivRight.setImageResource(id);
        }

        if (styledAttrs.hasValue(R.styleable.EditDataField_efld_left_icon)) {
            int id = styledAttrs.getResourceId(R.styleable.EditDataField_efld_left_icon,0);
            atvValue.setCompoundDrawablesWithIntrinsicBounds(id,0,0,0);
        }

        if (styledAttrs.hasValue(R.styleable.EditDataField_efld_obligatory)) {
        }

        if (styledAttrs.hasValue(R.styleable.EditDataField_efld_text_size)) {
        }

        int type = styledAttrs.getInt(R.styleable.EditDataField_field_type, TYPE_NONE);
        setType(type);
    }

    /**
     * Установка/Снятие индикатора выбора группы
     * @param selected
     */
    public void setGroupSelected(boolean selected){
        mGroupSelected = selected;
        setGroupIndicator();
    }

    /**
     * Установка типа поля
     * @param type
     */
    public void setType(int type){

        mType = type;

        switch (mType){

            case TYPE_CHECK_LIST:
                flValue.setVisibility(GONE);
                lvValues.setVisibility(VISIBLE);
                break;

            case TYPE_EMAIL:
            case TYPE_ADDRESS:
            case TYPE_PASSW:
            case TYPE_MONEY:
            case TYPE_PHONE:
            case TYPE_NONE:
                ivRight.setVisibility(GONE);
                atvValue.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;

            case TYPE_DATE:
            case TYPE_DATETIME:
                atvValue.setFocusable(false);
                atvValue.setKeyListener(null);
                atvValue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar,
                        0,0,0);
                break;


            case TYPE_TIME150:
            case TYPE_TIME:
                atvValue.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock,0,0,0);
                break;

            case TYPE_REF:
                ivRight.setVisibility(VISIBLE);
                ivRight.setImageResource(R.drawable.down_arrow);
                atvValue.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                break;

            case TYPE_INT:
                ivRight.setVisibility(GONE);
                atvValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                atvValue.setImeOptions(EditorInfo.IME_ACTION_DONE);

                break;

            case TYPE_LIST:
                ivRight.setVisibility(VISIBLE);
                ivRight.setImageResource(R.drawable.down_arrow);
                break;

            case TYPE_READ_ONLY:
                ivRight.setVisibility(GONE);
                break;

            case TYPE_CHECK_BOX:
                ivRight.setVisibility(GONE);
                break;

            default:
                ivRight.setVisibility(GONE);
                break;

        }

    }


    /**
     * Поиск значения для поля по введенному тексту (например тут можно выполнить поиск в БД)
     * @param val
     */
    protected void searchForValue(String val){

    }

    public void expandField(boolean expand){
        mGroupExpanded = expand;
        if (mGroupExpanded){
            flDetail.setVisibility(VISIBLE);
        } else {
            flDetail.setVisibility(GONE);
        }
        setGroupIndicator();
    }

    private void setGroupIndicator(){
        int id_left = 0;
        int id_right = R.drawable.ic_group_right;
        if (mGroupSelected){
            id_left = R.drawable.red_oval;
        }
        if (mGroupExpanded){
            id_right = R.drawable.down_arrow;
        }
        tvGroupIndicator.setCompoundDrawablesWithIntrinsicBounds(id_left,0,id_right,0);
    }

    /**
     * СОБЫТИЯ ПОЛЬЗОВАТЕЛЬСКОГО ИНТЕРФЕЙСА ПОЛЯ
     */

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            if (hasValue()){
                searchForValue(getValue());
            }

            ViewUtils.hideSoftKeyboard(getContext(), atvValue);
            handled = true;

        } else if (actionId == EditorInfo.IME_ACTION_DONE) {

            if (mValueChangedListener != null){
                mValueChangedListener.onChanged(this);
            }

            ViewUtils.hideSoftKeyboard(getContext(), atvValue);
            handled = true;
        }

        return handled;

    }

    @Override
    public void onClick(View view) {

        if (mValueChangedListener != null){
            mValueChangedListener.onBeforeChange(this);
        }

        switch (view.getId()){

            case R.id.llGroup:
                onGroupClick();
                break;

            case R.id.atvValue:
                onValueClick();
                break;

            case R.id.ivRight:
                onRightIconClick();
                break;

            default:
                break;

        }
    }

    private void onRightIconClick() {

        switch (mType){

            case TYPE_REF:
                atvValue.showDropDown();
                break;

            default:
                break;

        }

    }

    private void onValueClick() {

        switch (mType){

            case TYPE_DATE:
                showDateTimePickerDialog(false);
                break;

            case TYPE_DATETIME:
                showDateTimePickerDialog(true);
                break;

            default:
                break;
        }
    }

    private void onGroupClick(){
        mGroupExpanded = !mGroupExpanded;
        expandField(mGroupExpanded);
    }

    private void showDateTimePickerDialog(final boolean show_time){

        String val = atvValue.getText().toString();

        Calendar cal = Calendar.getInstance();

        if (!val.isEmpty()){

            SimpleDateFormat sdf;

            if (show_time) {
                sdf = new SimpleDateFormat("dd MMMM yyyy, EE HH:mm");
            } else {
                sdf = new SimpleDateFormat("dd MMMM yyyy, EE");
            }

            try {

                cal.setTime(sdf.parse(val));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        final String dialog_title = mCaption;

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        Integer color = getResources().getColor(R.color.colorCustomer);
        if (getData().getMode() == AppMode.SUPPLIER) {
            color = getResources().getColor(R.color.colorSuplier);
        }
        View viewTitle = DialogUtils.getTitleView((Activity) getContext(), dialog_title,color);
        final TextView tvTitle = viewTitle.findViewById(R.id.title);

        builder.setCustomTitle(viewTitle);

        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();

        View view = inflater.inflate(R.layout.date_time_dialog, null);
        builder.setView(view);

        final DatePicker datePicker = view.findViewById(R.id.datePicker);

        final TimePicker timePicker = view.findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                String dateSelected = String.format("%1$02d-%2$02d-%3$04d %4$02d:%5$02d",
                        datePicker.getDayOfMonth(),
                        datePicker.getMonth()+1,  datePicker.getYear(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                tvTitle.setText(dialog_title + " " + dateSelected);
            }
        });

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                String dateSelected = String.format("%1$02d-%2$02d-%3$04d %4$02d:%5$02d",
                        datePicker.getDayOfMonth(),
                        datePicker.getMonth()+1,  datePicker.getYear(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                tvTitle.setText(dialog_title + " " + dateSelected);
            }
        });

        if (!show_time){
            timePicker.setVisibility(GONE);
        } else {

            builder.setNeutralButton("Время", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            timePicker.setVisibility(GONE);

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);

            String dateSelected = String.format("%1$02d-%2$02d-%3$04d %4$02d:%5$02d",
                    datePicker.getDayOfMonth(),
                    datePicker.getMonth()+1,  datePicker.getYear(),
                    timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute());

            tvTitle.setText(dialog_title + " " + dateSelected);
        }

        builder.setPositiveButton(getResources().getString(R.string.select),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String date = "";
//                        UI_DATE_TIME_PATTERN_LONG

                        Calendar calendar = Calendar.getInstance();
                        if (show_time){
//                            date = String.format("%1$02d-%2$02d-%3$04d %4$02d:%5$02d",
//                                    datePicker.getDayOfMonth(),
//                                    datePicker.getMonth()+1,  datePicker.getYear(),
//                                    timePicker.getCurrentHour(),
//                                    timePicker.getCurrentMinute());

                            calendar.set(datePicker.getYear(), datePicker.getMonth(),
                                    datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                                    timePicker.getCurrentMinute() );

                            date = CastUtils.toUIDateTime(calendar.getTime().getTime());

                        } else {
//                            date = String.format("%1$02d-%2$02d-%3$04d",
//                                    datePicker.getDayOfMonth(),
//                                    datePicker.getMonth()+1,  datePicker.getYear());

                            calendar.set(datePicker.getYear(), datePicker.getMonth(),
                                    datePicker.getDayOfMonth());

                            date = CastUtils.toUIDate(calendar.getTime().getTime());
                        }

                        setValue(null, date);

                    }
                });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onClose(EditDataField.this);
                }
            }
        });

        final Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onCancel(EditDataField.this);
                }
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEUTRAL);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (datePicker.getVisibility() == VISIBLE){
                            datePicker.setVisibility(GONE);
                            timePicker.setVisibility(VISIBLE);

                            ((Button)view).setText("Дата");

                        } else {
                            datePicker.setVisibility(VISIBLE);
                            timePicker.setVisibility(GONE);

                            ((Button)view).setText("Время");

                        }

                    }
                });


            }
        });

        dialog.show();
    }


    /**
     * ИНИЦИАЛИЗАЦИЯ ЗНАЧЕНИЙ ПОЛЯ
     */

    public void initData(Bundle params){}

    protected void setValueAdapter(){
        ArrayAdapter adapter = getValueAdapter();
        if (adapter != null){
            if (mType == TYPE_CHECK_LIST) {
                lvValues.setAdapter(adapter);
            } else {
                atvValue.setAdapter(adapter);
            }
        }
    }

    protected ArrayAdapter getValueAdapter(){
        return null;
    }

    public ArrayList<Object> getAdapterValues(){

        ListAdapter adapter;
        ArrayList<Object> list = null;

        if (mType == TYPE_CHECK_LIST) {
            adapter = lvValues.getAdapter();
        } else {
            adapter = atvValue.getAdapter();
        }

        if (adapter != null){
            list = new ArrayList<>();
            int count = adapter.getCount();
            for (int i = 0; i<count; i++){
                list.add(adapter.getItem(i));
            }
        }

        return list;
    }


    /**
     * РАБОТА СО ЗНАЧЕНИЕМ ПОЛЯ (УСТАНОВКА, ПОЛУЧЕНИЕ, ОЧИСТКА ЗНАЧЕНИЯ, ГУИДА)
     */

    public boolean hasValue(){

        if (mType == TYPE_INT) {

            try {
                return Integer.valueOf(getValue()) > 0 ? true : false;
            } catch (Exception e){
                LogUtils.debugErrorLog(LOG_PREFIX, e);
                return false;
            }

        } else {
            return Utils.stringsNotEmpty(getValue());
        }
    }

    public void clearValue(){
        setValue("","");
    }

    public void setValue(String id, String val){

        boolean value_changed = false;

        if (!getGuid().equalsIgnoreCase(id) || !getValue().equalsIgnoreCase(val)) {
            value_changed = true;
        }

        setValue(val);
        setGuid(id);

        if (value_changed && mValueChangedListener != null){
            mValueChangedListener.onChanged(this);
        }

        ViewUtils.hideSoftKeyboard(getContext(), atvValue);
        atvValue.dismissDropDown();

    }

    public void setValue(String val){
        atvValue.setText(val);
    }

    public void setGuid(String id){
        mId = id;
    }

    public String getValue(){
        if (atvValue.getText().toString().isEmpty() && mType == TYPE_INT) {
            return "0";
        }
        return atvValue.getText().toString();
    }

    public String getGuid(){
        if (mId == null) return "";
        return mId;
    }

    /**
     * НАСТРОЙКА СПИСКА ВЫБОРА ДОСТУПНЫХ ЗНАЧЕНИЙ ПОЛЯ
     */

    public void updateDataFilter(String[] params){
        if (mIUIFieldDataModel != null){
            mIUIFieldDataModel.updateDataFilter(params);
        }
    }

    public void updateItemFilter(String[] params){
        if (mIUIFieldDataModel != null){
            mIUIFieldDataModel.udpateItemFilter(params);
            Bundle def = mIUIFieldDataModel.getDefaultSelected();
            if (def != null){
                setGuid(def.getString("id"));
                setValue(def.getString("val"));
            }
        }
    }

    public void setDataModel(IUIFieldDataModel IUIFieldDataModel){

        if (IUIFieldDataModel != null){
            mIUIFieldDataModel = IUIFieldDataModel;

            Bundle def = mIUIFieldDataModel.getDefaultSelected();
            if (def != null){
                setGuid(def.getString("id"));
                setValue(def.getString("val"));
            }
        }

        if (mType == TYPE_REF || mType == TYPE_CHECK_LIST){
            setValueAdapter();
        }
    }

    protected IUIFieldDataModel getDataModel(){
        return mIUIFieldDataModel;
    }


    private WDData getData(){
        return WDData.getInstance();
    }

    /**
     * ПОДПИСКА НА СОБЫТИЯ ИЗМЕНЕНИЯ ЗНАЧЕНИЯ ПОЛЯ
     */

    public void setOnValueChangedListener(OnValueChangedListener listener){
        mValueChangedListener = listener;
    }

    /**
     * ДОПУСТИМЫЕ СОБЫТИЯ ПО ИЗМЕНЕНИЮ ЗНАЧЕНИЯ ПОЛЯ
     */

    public interface OnValueChangedListener {

        void onBeforeChange(EditDataField view);

        void onChanged(EditDataField view);

        void onClose(EditDataField view);

        void onCancel(EditDataField view);
    }

}
