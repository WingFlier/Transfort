package ru.wdsoft.ui.datafield;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import ru.wdsoft.db.WDDbHelper;
import ru.wdsoft.ui.base.WDActivity;
import ru.wdsoft.ui.nomenclature.NomenclatureDialog;
import ru.wdsoft.R;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.LogUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 29.03.2016.
 */
public class DataField extends RelativeLayout implements View.OnClickListener {

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
    private int iconBgId;
    private int iconId;

    private View mValue_ll;
    private TextView mCaption, mValue;
    private ImageView mIcon, mIcon2, mIconClear;
    private CheckBox mCheckBox;
    private int mType = TYPE_NONE;

    private IUIFieldDataModel mIUIFieldDataModel;

    private String mId;
    private ContentValues mItems;

    private View mObligatory;
    private boolean mIsObligatory = false;

    private boolean mDisabled = false;
    private boolean mUsePhoneMask = true;

    private ArrayAdapter<String> mAutoCompleteList;

    private OnValueChangedListener mValueChangedListener;

    private DatePickerDialog.OnDateSetListener setDateValue = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String date = String.format("%1$02d-%2$02d-%3$04d", dayOfMonth, monthOfYear+1, year);

            setValue(-1, date);
        }
    };

    public DataField(Context context, AttributeSet attrs) {

        super(context, attrs);

        this.setOnClickListener(this);

        inflate(context, R.layout.input_field, this);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.DataField);

        int orient = styledAttrs.getInt(R.styleable.DataField_caption_orientation, LinearLayout.HORIZONTAL);
        if (orient == LinearLayout.VERTICAL){
            LinearLayout ll = (LinearLayout)findViewById(R.id.field);
            ll.setOrientation(LinearLayout.VERTICAL);
        }

        mCaption = (TextView)findViewById(R.id.caption);
        if (styledAttrs.hasValue(R.styleable.DataField_caption)) {
            mCaption.setText(styledAttrs.getString(R.styleable.DataField_caption));
        } else {
            mCaption.setVisibility(GONE);
        }


        mIcon2 = (ImageView)findViewById(R.id.icon2);
        mIcon2.setOnClickListener(this);

        mIcon  = (ImageView)findViewById(R.id.icon);
        mIcon.setOnClickListener(this);

        mIconClear  = (ImageView)findViewById(R.id.icon_clear);
        mIconClear.setOnClickListener(this);

        mValue_ll = findViewById(R.id.value_ll);

        mValue = (TextView)findViewById(R.id.value);
        mValue.setOnClickListener(this);

        mCheckBox = (CheckBox)findViewById(R.id.checkbox);

        mObligatory = findViewById(R.id.obligatory);
        mIsObligatory = styledAttrs.getBoolean(R.styleable.DataField_obligatory, false);
        setIsObligatory(mIsObligatory);

        if (styledAttrs.hasValue(R.styleable.DataField_field_hint)) {
            mValue.setHint(styledAttrs.getString(R.styleable.DataField_field_hint));
        }

        iconId = -1;
        if (styledAttrs.hasValue(R.styleable.DataField_field_icon)) {
            iconId = styledAttrs.getResourceId(R.styleable.DataField_field_icon,-1);
        }

        iconBgId = -1;
        if (styledAttrs.hasValue(R.styleable.DataField_field_icon_bg)) {
            iconBgId = styledAttrs.getResourceId(R.styleable.DataField_field_icon_bg,-1);
        }

        if (styledAttrs.hasValue(R.styleable.DataField_text_size)) {
            float tsize = styledAttrs.getDimensionPixelSize(R.styleable.DataField_text_size, 0);
           //getDimension(R.styleable.R24InputField_text_size, 0);
            if (tsize > 0) {
                mValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, tsize);
            }

        }

        int type = styledAttrs.getInt(R.styleable.DataField_field_type, TYPE_NONE);
        setType(type);

    }

    public void initData(Bundle params){

    }

    public void setType(int type){

        mType = type;

        switch (mType){

            case TYPE_MONEY:
            case TYPE_PHONE:
            case TYPE_NONE:
                mIcon.setVisibility(GONE);
                break;

            case TYPE_DATE:
            case TYPE_DATETIME:
                mIcon.setImageResource(R.drawable.ic_calendar);
                break;


            case TYPE_TIME150:
            case TYPE_TIME:
                mIcon.setImageResource(R.drawable.ic_schedule_white_24dp);
                break;

            case TYPE_REF:
                if (iconId > 0) {
                    mIcon.setImageResource(iconId);
                    if (iconBgId >0){
                        mIcon.setBackgroundResource(iconBgId);
                    }
                } else {
                    mIcon.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
                }
                break;

            case TYPE_INT:

                if (iconId > 0) {
                    mIcon.setImageResource(iconId);
                    if (iconBgId >0){
                        mIcon.setBackgroundResource(iconBgId);
                    }
                } else {
                    mIcon.setImageResource(R.drawable.ic_up_down_drill);
                }

                mIconClear.setVisibility(GONE);
                break;

            case TYPE_LIST:

                if (iconId > 0) {
                    mIcon.setImageResource(iconId);
                    if (iconBgId >0){
                        mIcon.setBackgroundResource(iconBgId);
                    }
                } else {
                    mIcon.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
                }
                break;

            case TYPE_READ_ONLY:
                mIcon.setVisibility(GONE);
                mIconClear.setVisibility(GONE);
                setDisableEdit(true);
                break;

            case TYPE_CHECK_BOX:
                mValue_ll.setVisibility(GONE);
                mValue_ll.setBackgroundResource(0);
                mIcon.setVisibility(GONE);
                mIconClear.setVisibility(GONE);
                mValue.setVisibility(GONE);
                mCheckBox.setVisibility(VISIBLE);

                mCheckBox.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mValueChangedListener != null){
                            mValueChangedListener.onChanged(DataField.this);
                        }
                    }
                });

                break;

            default:
                mIcon.setVisibility(GONE);
                break;

        }

    }

    public void setValue(Integer id, String val){

        boolean value_changed = false;

        if (getValueId() != id || !getValue().equalsIgnoreCase(val)) {
            value_changed = true;
        }

        setValue(val);
        setValueId(id);

        if (value_changed && mValueChangedListener != null){
            mValueChangedListener.onChanged(this);
        }
    }

    public void setValueGuid(String id, String val){

        boolean value_changed = false;

        if (!getValueGuid().equalsIgnoreCase(id) || !getValue().equalsIgnoreCase(val)) {
            value_changed = true;
        }

        setValue(val);
        setValueGuid(id);

        if (value_changed && mValueChangedListener != null){
            mValueChangedListener.onChanged(this);
        }

    }

    public void setValue(String val){
        mValue.setText(val);
    }

    public void setChecked(int checked){
        mCheckBox.setChecked(checked == 1);
    }

    public void setChecked(boolean checked){
        mCheckBox.setChecked(checked);
    }

    public boolean getChecked(){
        return mCheckBox.isChecked();
    }

    private String getDialogTitle(){

        String title = mCaption.getText().toString();

        if (!Utils.stringsNotEmpty(title)){

            if (mValue.getHint() != null) {
                return mValue.getHint().toString();
            } else {
                return "";
            }
        }

        return title;
    }

    public String getValue(){

        if (mValue.getText().toString().isEmpty() && mType == TYPE_INT) {
            return "0";
        }

        return mValue.getText().toString();
    }

    public String getValueGuid(){
        if (mId == null) return "0";
        return mId;
    }

    public Integer getValueId(){

        if (!Utils.stringsNotEmpty(mId)) return 0;

        return Integer.valueOf(mId);
    }

    public void setValueId(Integer id){
        mId = String.valueOf(id);
    }

    public void setValueGuid(String id){
        mId = id;
    }

    public String getCaption(){
        if (mCaption != null) {
            return mCaption.getText().toString();
        } else {
            return "";
        }
    }

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
                setValueGuid(def.getString("id"));
                setValue(def.getString("val"));
            }
        }
    }

    public void setDataModel(IUIFieldDataModel IUIFieldDataModel){

        if (IUIFieldDataModel != null){
            mIUIFieldDataModel = IUIFieldDataModel;

            Bundle def = mIUIFieldDataModel.getDefaultSelected();
            if (def != null){
                setValueGuid(def.getString("id"));
                setValue(def.getString("val"));
            }
        }

    }

    public void setSql(String sql, String sqlFilter, String itemFilter){

        // ПОДУМАТЬ

//        if (Utils.stringsNotEmpty(sql, itemFilter)){
//
//            Cursor c = getDb().selectData(sql, itemFilter);
//
//            if (c != null && c.getCount() > 0){
//
//                if (c.moveToFirst()){
//                    setValueGuid(c.getString(0));
//                    setValue(c.getString(1));
//                }
//
//                c.close();
//            }
//
//        }
    }

    public boolean isObligatory() {
        return mIsObligatory;
    }

    public void setIsObligatory(boolean mIsObligatory) {

        if (mDisabled) {
            mObligatory.setVisibility(GONE);
            return;
        }

        this.mIsObligatory = mIsObligatory;

        if (mIsObligatory) {
            mObligatory.setVisibility(VISIBLE);
            mIconClear.setVisibility(GONE);
        } else {
            mObligatory.setVisibility(GONE);
            mIconClear.setVisibility(VISIBLE);
        }
    }

    public void setDefaultValue(){

        switch (mType){

            case TYPE_NONE:
                break;

            case TYPE_DATE:
                break;

            case TYPE_TIME:
                break;

            case TYPE_REF:
                break;

            case TYPE_INT:
                break;

            case TYPE_LIST:
                break;

            case TYPE_READ_ONLY:
                break;

            case TYPE_CHECK_BOX:
                break;

            default:
                break;
        }


    }

    public void setIcon(int resId, int bgId){
        mIcon.setImageResource(resId);
        mIcon.setBackgroundResource(bgId);
    }

    public void setIcon2(int resId, int bgId){
        mIcon2.setImageResource(resId);
        mIcon2.setBackgroundResource(bgId);
        mIcon2.setVisibility(VISIBLE);

    }

    public void setIcon2Click(OnClickListener listener){
        mIcon2.setOnClickListener(listener);
    }


    public void clearValue(){
        setValue(-1,"");
    }

    public void clearGuidValue(){
//        setValueGuid("", "");
        setValue("");
        setValueGuid("");

    }


    public boolean hasValue(){
//        return Utils.hasValue(getValueId()) && Utils.stringsNotEmpty(getValue());

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

    public void usePhoneMask(boolean val){
        mUsePhoneMask = val;
    }

    public void setDisableEdit(boolean disable){

        mDisabled = disable;

        mCheckBox.setEnabled(!disable);

        if (disable) {

//            mIconClear.setVisibility(GONE);
//            mIcon.setVisibility(GONE);
//            mIcon2.setVisibility(GONE);

            mIconClear.setBackgroundResource(R.color.colorGrayLight);
            mIcon.setBackgroundResource(R.color.colorGrayLight);
            mIcon2.setBackgroundResource(R.color.colorGrayLight);

            mValue_ll.setBackgroundResource(R.drawable.rect_lightgrey_grey_border);

            if (mIsObligatory) {
                mObligatory.setVisibility(GONE);
            }

        } else {

            mIconClear.setBackgroundResource(R.color.colorAccent);
            mIcon.setBackgroundResource(R.color.colorAccent);
            mIcon2.setBackgroundResource(R.color.colorAccent);

            if (mType != TYPE_CHECK_BOX) {
                mValue_ll.setBackgroundResource(R.drawable.rect_white_green_border);
            }
            if (mIsObligatory){
                mObligatory.setVisibility(VISIBLE);
            }

        }

    }


    public void setItems(ContentValues items){
        mItems = items;
    }

    @Override
    public void onClick(View v) {

        if (mDisabled) return;

        if (mValueChangedListener != null){
            mValueChangedListener.onBeforeChange(DataField.this);
        }

        switch (v.getId()){

            case R.id.value:
            case R.id.icon:
                showDialog();
                break;

            case R.id.icon_clear:
//                clearValue();
                clearGuidValue();
                break;

            default:
                break;

        }
    }

    public void showDialog(){

//        if (mValueChangedListener != null){
//            mValueChangedListener.onBeforeChange(DataField.this);
//        }

        switch (mType){

            case TYPE_MONEY:
            case TYPE_PHONE:
            case TYPE_NONE:
                showEditTextDialog();
                break;

            case TYPE_DATE:
                showDateTimePickerDialog(false);
                break;

            case TYPE_DATETIME:
                showDateTimePickerDialog(true);
                break;

            case TYPE_TIME150:
                showTime150PickerDialog();
                break;

            case TYPE_TIME:
                showTimePickerDialog();
                break;

            case TYPE_REF:
                showNomenclatureDialog();
                break;

            case TYPE_INT:
                showNumberPickerDialog();
                break;

            case TYPE_LIST:
                showItemsDialog();
                break;

            case TYPE_READ_ONLY:
                break;

            case TYPE_CHECK_BOX:
                showCheckBoxDialog();
                break;

            default:
                showEditTextDialog();
                break;
        }
    }

    private WDActivity getActivity(){
        return (WDActivity)getContext();
    }

    private WDDbHelper getDb(){
        return getActivity().getDb();
    }

    private void showNomenclatureDialog(){

        NomenclatureDialog d = NomenclatureDialog.newInstance(getDialogTitle(), mIUIFieldDataModel);

        d.show(getActivity().getSupportFragmentManager(), "NoticeDialogFragment");

        d.setListner(new NomenclatureDialog.OnNomenclatureInteractionListener() {

            @Override
            public void onItemClicked(String id, String val) {
                setValueGuid(id, val);
            }

            @Override
            public void onClose() {
                if (mValueChangedListener != null){
                    mValueChangedListener.onClose(DataField.this);
                }
            }

            @Override
            public void onCancel() {
                if (mValueChangedListener != null){
                    mValueChangedListener.onCancel(DataField.this);
                }
            }

        });

    }

    public void setAutoCompleteList(String[] list){

        if (list == null) return;

        mAutoCompleteList = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, list);
    }

    private void showCheckBoxDialog(){

        String dtitle = getDialogTitle();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCustomTitle(DialogUtils.getTitleView(getActivity(), dtitle));

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_chekbox, null);
        builder.setView(view);

        final CheckBox cb = (CheckBox ) view.findViewById(R.id.checkbox);
        cb.setText(dtitle);

        cb.setChecked(mCheckBox.isChecked());

        builder.setPositiveButton(getResources().getString(R.string.apply),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mCheckBox.setChecked(cb.isChecked());
                        if (mValueChangedListener != null){
                            mValueChangedListener.onChanged(DataField.this);
                        }
                    }
                });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onClose(DataField.this);
                }
            }
        });

        Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onCancel(DataField.this);
                }
            }
        });

        dialog.show();
    }

    private void showEditTextDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCustomTitle(DialogUtils.getTitleView(getActivity(), getDialogTitle()));

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.edit_dialog, null);
        builder.setView(view);

        final AutoCompleteTextView et = (AutoCompleteTextView ) view.findViewById(R.id.txt);

        if (mAutoCompleteList != null) {
            et.setAdapter(mAutoCompleteList);
        }

        switch (mType) {

            case TYPE_MONEY:
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (hasValue()){
                    et.setText(mValue.getText().toString());
                }
                break;

            case TYPE_PHONE:
                et.setInputType(InputType.TYPE_CLASS_PHONE);
                if (mUsePhoneMask) {
                    int maxLength = 17;
                    et.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
                    et.addTextChangedListener(new MaskWatcher("##(###)-###-##-##"));
                    if (!hasValue()){
                        et.setText("+7");
                    } else {
                        et.setText(mValue.getText().toString());
                    }
                } else{
                    et.setText(mValue.getText().toString());
                }
                break;

            case TYPE_NONE:
                if (hasValue()){
                    et.setText(mValue.getText().toString());
                }
                break;

            default:
                if (hasValue()){
                    et.setText(mValue.getText().toString());
                }
                break;
        }

        if (Utils.stringsNotEmpty(et.getText().toString())){
            et.post(new Runnable() {
                @Override
                public void run() {
                    et.setSelection(et.getText().toString().length());
                }
            });
        }

        builder.setPositiveButton(getResources().getString(R.string.apply),
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                hideSoftKeyboard(et);
                setValue(-1, et.getText().toString());
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                hideSoftKeyboard(et);

                if (mValueChangedListener != null){
                    mValueChangedListener.onClose(DataField.this);
                }
            }
        });

        Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onCancel(DataField.this);
                }
            }
        });

        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showSoftKeyboard(et);
            }
        }, 500);
    }

    private void showItemsDialog(){

        final ArrayList<String> items = new ArrayList<>();

        for (Map.Entry<String, Object> entry : mItems.valueSet()) {
            items.add(entry.getKey().toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setCustomTitle(DialogUtils.getTitleView(getActivity(), getDialogTitle()))
                .setItems(items.toArray(new String[0]), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String key = items.get(which);

                        try {

                            Integer int_key = mItems.getAsInteger(key);

                            if (int_key != null){
                                setValue(mItems.getAsInteger(key), key);
                            } else {
                                setValueGuid(mItems.getAsString(key), key);
                            }


                        }catch (Exception e){
                            LogUtils.debugErrorLog(LOG_PREFIX, e);
                            setValueGuid(mItems.getAsString(key), key);
                        }



                    }

                });



        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onClose(DataField.this);
                }
            }
        });

        Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onCancel(DataField.this);
                }
            }
        });


        dialog.show();

    }

    private void showNumberPickerDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCustomTitle(DialogUtils.getTitleView(getActivity(), getDialogTitle()));
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.numeric_dialog, null);
        builder.setView(view);

        final NumberPicker np = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np.setMaxValue(1000); // max value 100
        np.setMinValue(0);   // min value 0
        np.setWrapSelectorWheel(false);

        int val = 0;

        try {
            val = Integer.valueOf(mValue.getText().toString());
        } catch (Exception e){

        }

        np.setValue(val);

        builder.setPositiveButton(getResources().getString(R.string.apply),
                new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                setValue(-1, String.valueOf(np.getValue()));
            }

        });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onClose(DataField.this);
                }
            }
        });

        Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onCancel(DataField.this);
                }
            }
        });

        dialog.show();
    }

    private void showTimePickerDialog() {

        String val = mValue.getText().toString();
        String vals[] = val.split(":");

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);

        if (vals.length >= 2){
            hour = Integer.valueOf(vals[0]);
            min = Integer.valueOf(vals[1]);
        }

        TimePickerDialog dialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format("%1$02d:%2$02d", hourOfDay, minute);

                setValue(-1, time);
            }

        }, hour, min, true);

        dialog.setCustomTitle(DialogUtils.getTitleView(getActivity(), getDialogTitle()));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onCancel(DataField.this);
                }
            }
        });

        dialog.show();


    }

    private void showTime150PickerDialog(){

        String val = mValue.getText().toString();
        String vals[] = val.split(":");

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);

        if (vals.length >= 2){
            hour = Integer.valueOf(vals[0]);
            min = Integer.valueOf(vals[1]);
        }

        TimePickerDialog150Hours dialog = new TimePickerDialog150Hours(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format("%1$02d:%2$02d", hourOfDay, minute);

                setValue(-1, time);
            }
        }, hour, min, true);


        dialog.setCustomTitle(DialogUtils.getTitleView(getActivity(), getDialogTitle()));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onCancel(DataField.this);
                }
            }
        });

        dialog.show();

    }

    private void showDateTimePickerDialog(final boolean show_time){

        String val = mValue.getText().toString();

        Calendar cal = Calendar.getInstance();

        if (!val.isEmpty()){

            SimpleDateFormat sdf;

            if (show_time) {
                sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            } else {
                sdf = new SimpleDateFormat("dd-MM-yyyy");
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View viewTitle = DialogUtils.getTitleView(getActivity(), getDialogTitle());
        final TextView tvTitle = (TextView)viewTitle.findViewById(R.id.title);

        builder.setCustomTitle(viewTitle);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.date_time_dialog, null);
        builder.setView(view);

        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                String dateSelected = String.format("%1$02d-%2$02d-%3$04d %4$02d:%5$02d",
                        datePicker.getDayOfMonth(),
                        datePicker.getMonth()+1,  datePicker.getYear(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                tvTitle.setText(getDialogTitle() + " " + dateSelected);
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

                tvTitle.setText(getDialogTitle() + " " + dateSelected);
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

            tvTitle.setText(getDialogTitle() + " " + dateSelected);
        }

        builder.setPositiveButton(getResources().getString(R.string.select),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String date = "";

                        if (show_time){
                            date = String.format("%1$02d-%2$02d-%3$04d %4$02d:%5$02d",
                                    datePicker.getDayOfMonth(),
                                    datePicker.getMonth()+1,  datePicker.getYear(),
                                    timePicker.getCurrentHour(),
                                    timePicker.getCurrentMinute());

                        } else {
                            date = String.format("%1$02d-%2$02d-%3$04d",
                                    datePicker.getDayOfMonth(),
                                    datePicker.getMonth()+1,  datePicker.getYear());

                        }

                        setValue(-1, date);

                    }
                });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mValueChangedListener != null){
                    mValueChangedListener.onClose(DataField.this);
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
                    mValueChangedListener.onCancel(DataField.this);
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

    public void hideSoftKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

        try {
            if (imm.isAcceptingText()) { // verify if the soft keyboard is open
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    public void showSoftKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (NullPointerException e) {
            LogUtils.debugErrorLog(LOG_PREFIX, e);
        }
    }

    public boolean fillField(final String defVal) {

        boolean res = false;

        if (this.getVisibility() == View.VISIBLE && !this.hasValue()){

            res = true;

            if (Utils.stringsNotEmpty(defVal)) {
                DataField.this.setValue(defVal);
            }
            DataField.this.showDialog();


//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//
//                    if (Utils.stringsNotEmpty(defVal)) {
//                        R24InputField.this.setValue(defVal);
//                    }
//                    R24InputField.this.showDialog();
//                }
//            });

//            return true;
        }
        return res;
    }



//    protected boolean fillField(){
//        return fillField("");
//    }

    public void setOnValueChangedListener(OnValueChangedListener listener){
        mValueChangedListener = listener;
    }

    public interface OnValueChangedListener {

        void onBeforeChange(DataField view);

        void onChanged(DataField view);

        void onClose(DataField view);

        void onCancel(DataField view);
    }

}
