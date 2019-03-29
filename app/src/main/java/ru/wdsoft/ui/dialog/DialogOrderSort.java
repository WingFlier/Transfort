package ru.wdsoft.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.wdsoft.R;
import ru.wdsoft.ui.base.WDDialog;

public class DialogOrderSort extends WDDialog implements TextView.OnClickListener {

    @BindView(R.id.cvOrderSort) CardView cvOrderSort;
    @BindView(R.id.btnOrderSortCancel) Button btnOrderSortCancel;
    @BindView(R.id.btnOrderSortOk) Button btnOrderSortOk;

    private ISortOrder mLister;

    public DialogOrderSort(@NonNull Context context) {
        super(context);
    }

    public static void newInstance(Context context, ISortOrder listner) {
        DialogOrderSort dialog = new DialogOrderSort(context);
        dialog.setListner(listner);
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_order_sort);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ButterKnife.bind(this);

        btnOrderSortOk.setOnClickListener(this);
        btnOrderSortCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnOrderSortCancel:
                dismiss();
                break;

            case R.id.btnOrderSortOk:
                setSortOrder();
                break;

            default:
                break;
        }

    }

    private void setSortOrder(){
        if (mLister != null){
            mLister.onSortClick();
        }
        dismiss();
    }

    public void setListner(ISortOrder listner){
        mLister = listner;
    }

    public interface ISortOrder {
        void onSortClick();
    }
}
