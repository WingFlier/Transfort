package ru.wdsoft.ui.nomenclature;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;

import ru.wdsoft.R;
import ru.wdsoft.ui.datafield.IUIFieldDataModel;
import ru.wdsoft.utils.DialogUtils;
import ru.wdsoft.utils.Utils;


public class NomenclatureDialog extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private String mTitle;

    private IUIFieldDataModel mIUIFieldDataModel;

    private OnNomenclatureInteractionListener mListener;
    private SimpleCursorAdapter mListAdapter;
    TextView tvCount;

    public NomenclatureDialog() {}

    public static NomenclatureDialog newInstance(String title, IUIFieldDataModel IUIFieldDataModel) {

        NomenclatureDialog fragment = new NomenclatureDialog();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);

        fragment.setDataModel(IUIFieldDataModel);

        fragment.setArguments(args);
        return fragment;
    }

    public static NomenclatureDialog newInstance(String title, String sql, String filter) {

        NomenclatureDialog fragment = new NomenclatureDialog();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


    private void setDataModel(IUIFieldDataModel IUIFieldDataModel){
        this.mIUIFieldDataModel = IUIFieldDataModel;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_TITLE, mTitle);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mTitle = savedInstanceState.getString(ARG_TITLE);
        } else {
            if (getArguments() != null) {
                mTitle = getArguments().getString(ARG_TITLE);
            }
        }

        String[] from = {"name"};

        int[] to = {R.id.item_name};

        mListAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.nomenclature_item, null, from, to, 0);
        mListAdapter.setViewBinder(new NomenclatureListBinder());

        if (mIUIFieldDataModel == null) return;

        Cursor c = mIUIFieldDataModel.getCursor();

        if (c!= null) {
            mListAdapter.swapCursor(c);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.nomenclature_dialog, null);

        EditText filter = (EditText)view.findViewById(R.id.filter);

        tvCount = (TextView)view.findViewById(R.id.count);

        filter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                mListAdapter.getFilter().filter(s.toString());
            }
        });

        if (mListAdapter.getCount() < 10) {

            filter.setVisibility(View.GONE);

        } else {

            mListAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {

                    final Cursor c = filterList(constraint.toString());

                    if (c!=null){

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setElementCount(c.getCount());
                            }
                        });
                    }
                    return c;
                }
            });

        }

        if (mListAdapter != null && mListAdapter.getCount() > 0) {
            setElementCount(mListAdapter.getCount());
        }

        ListView list = (ListView)view.findViewById(R.id.list);
        list.setAdapter(mListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor item = (Cursor) mListAdapter.getItem(position);

                if (item != null && mListener != null) {
                    mListener.onItemClicked(item.getString(0), item.getString(1));
                    dismiss();
                }

            }
        });


        builder.setView(view);

        builder.setCustomTitle(DialogUtils.getTitleView(getActivity(), mTitle));

        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                if (mListener != null) {
                    mListener.onClose();
                }
            }
        });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        return dialog;

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        if (mListener != null) {
            mListener.onCancel();
        }

    }

    private Cursor filterList(String name){

        if (mIUIFieldDataModel != null){

            if (Utils.stringsNotEmpty(name)){

                String filter = " name LIKE '%" + name + "%' ";
                return mIUIFieldDataModel.getCursor(filter);
            }

        }

        return null;
    }

    private void setElementCount(int count){
        tvCount.setText(String.valueOf(count) + " элементов в списке");
    }

    public void setListner(OnNomenclatureInteractionListener listner){
        mListener = listner;
    }

    public interface OnNomenclatureInteractionListener {

        void onItemClicked(String id, String val);

        void onClose();

        void onCancel();

    }

    private class NomenclatureListBinder implements SimpleCursorAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            switch (view.getId()) {

                case R.id.item_name:
                    ((TextView)view).setText(cursor.getString(columnIndex));
                    return true;

                default:
                    return false;
            }

        }
    }
}
