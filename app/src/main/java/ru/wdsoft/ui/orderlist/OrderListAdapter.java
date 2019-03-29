package ru.wdsoft.ui.orderlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ru.wdsoft.R;
import ru.wdsoft.api.models.Order;
import ru.wdsoft.utils.CastUtils;
import ru.wdsoft.utils.DateTimeUtils;
import ru.wdsoft.utils.Utils;

/**
 * Created by slaventii@mail.ru on 28.02.2019.
 */
public class OrderListAdapter extends ArrayAdapter<Object> {


    public interface IOrderListListener {
        void OnItemClick(String id);
        void OnMenuClick(String id, View view);
    }

    private IOrderListListener mListner;

    private ArrayList<Order> mList = new ArrayList<>();


    public OrderListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void setListner(IOrderListListener listner){
        mListner = listner;
    }

    public void setList(Object list) {

        if (list instanceof ArrayList<?>){

            ArrayList<Order> listOrders = (ArrayList<Order>)list;
            setList(listOrders);
        }

    }

    public void setList(ArrayList<Order> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (mList == null){
            return 0;
        }
        return mList.size();
    }

    @Override
    public Order getItem(int position) {

        if (mList != null){
            if (position < mList.size()){
                return mList.get(position);
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mList == null){
            return 0;
        }
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListner != null){
                        Order order = getItem(position);
                        if (order != null){
                            mListner.OnItemClick(order.getId());
                        }
                    }
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        bindViewHolder(holder, position);

        return convertView;
    }


    private void bindViewHolder(ViewHolder holder, int position) {

        final Order order = mList.get(position);

        holder.rlOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListner != null){
                    mListner.OnItemClick(order.getId());
                }
            }
        });

        if (Utils.stringsNotEmpty(order.getExtNumber()))
            holder.tvOrderNum.setText("№ " + order.getExtNumber());
        else holder.tvOrderNum.setText("№ " + order.getNumber());

        holder.tvOrderDate.setText("от " + CastUtils.toUIOnlyDate(order.getDate()));

        holder.tvOrderCity.setText(order.getLocalityName());

        holder.tvOrderRegion.setText(order.getRentAddress());
        holder.tvOrderStatus.setText(order.getStateName());
        holder.tvOrderVehicle.setText(order.getVehicleTypeName());
        holder.tvOrderCalendar.setText(CastUtils.toUIDateTime(order.getRentStartDate()));
        holder.tvOrderClock.setText(DateTimeUtils.getTimeFromMinutes(order.getRentTime())  + " часов");
        holder.tvOrderRoute.setText(order.getMileage() + " км");
        holder.tvOrderCost.setText(String.valueOf(order.getPrice()) + " р./км" );

        holder.tvOrderDetailVehicle.setText(order.getVehicleName());

        holder.tvOrderStar.setText(String.valueOf(order.getRating().intValue()) + "/5");

        holder.ivOrderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListner != null){
                    mListner.OnMenuClick(order.getId(), view);
                }
            }
        });
    }

    class ViewHolder {

        RelativeLayout rlOrderItem;
        TextView tvOrderNum;
        TextView tvOrderStatus;
        ImageView ivOrderMore;
        TextView tvOrderDate;
        TextView tvOrderCity;
        TextView tvOrderRegion;
        TextView tvOrderVehicle;
        TextView tvOrderCalendar;
        TextView tvOrderClock;
        TextView tvOrderRoute;
        TextView tvOrderCost;
        RelativeLayout rlOrderDetail;
        TextView tvOrderDetailVehicle;
        TextView tvOrderDetailLink;
        TextView tvOrderStar;

        public ViewHolder(View view) {
            rlOrderItem = view.findViewById(R.id.rlOrderItem);
            tvOrderNum = view.findViewById(R.id.tvOrderNum);
            tvOrderStatus = view.findViewById(R.id.tvOrderStatus);
            ivOrderMore = view.findViewById(R.id.ivOrderMore);
            tvOrderDate = view.findViewById(R.id.tvOrderDate);
            tvOrderCity = view.findViewById(R.id.tvOrderCity);
            tvOrderRegion = view.findViewById(R.id.tvOrderRegion);
            tvOrderVehicle = view.findViewById(R.id.tvOrderVehicle);
            tvOrderCalendar = view.findViewById(R.id.tvOrderCalendar);
            tvOrderClock = view.findViewById(R.id.tvOrderClock);
            tvOrderRoute = view.findViewById(R.id.tvOrderRoute);
            tvOrderCost = view.findViewById(R.id.tvOrderCost);
            rlOrderDetail = view.findViewById(R.id.rlOrderDetail);
            tvOrderDetailVehicle = view.findViewById(R.id.tvOrderDetailVehicle);
            tvOrderDetailLink = view.findViewById(R.id.tvOrderDetailLink);
            tvOrderStar = view.findViewById(R.id.tvOrderStar);
        }
    }

}
