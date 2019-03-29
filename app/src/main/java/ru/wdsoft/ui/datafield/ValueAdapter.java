package ru.wdsoft.ui.datafield;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.wdsoft.R;
import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 26.02.2019.
 */
public class ValueAdapter extends ArrayAdapter<ApiSerializable> {

    public interface IValueListener {
        void onSelected(ApiSerializable object);
    }


    private ArrayList<ApiSerializable> listVehicle;
    private ArrayList<ApiSerializable> suggestions;
    private ArrayList<ApiSerializable> itemsAll;
    private IValueListener mListner;
    private int mResourceId;

    public ValueAdapter(Context context, int resource, ArrayList<ApiSerializable> objects) {
        super(context, resource, objects);
        this.listVehicle = objects;
        this.suggestions = new ArrayList<>();
        this.itemsAll = (ArrayList<ApiSerializable>) listVehicle.clone();
        this.mResourceId = resource;
    }

    public void setLisner(IValueListener lisner){
        mListner = lisner;
    }

    @Override
    public int getCount() {
        if (listVehicle == null){
            return 0;
        }
        return listVehicle.size();
    }

    @Override
    public ApiSerializable getItem(int position) {

        if (listVehicle != null){
            if (position < listVehicle.size()){
                return listVehicle.get(position);
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (listVehicle == null){
            return 0;
        }
        return position;
    }

    @Override
    public Filter getFilter() {
        return filterVehicle;
    }

    Filter filterVehicle = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            if (resultValue != null){
                String str = ((ApiSerializable)resultValue).getUIName();
                return str;
            }
            return super.convertResultToString(resultValue);
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if(charSequence != null) {
                suggestions.clear();
                for (ApiSerializable item : itemsAll) {
                    if(item.getUIName().toLowerCase()
                            .contains(charSequence.toString().toLowerCase())){
                        suggestions.add(item);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                suggestions.clear();
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsAll;
                filterResults.count = itemsAll.size();
                return filterResults;
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<ApiSerializable> filteredList = (ArrayList<ApiSerializable>) filterResults.values;
            if(filterResults != null && filterResults.count > 0) {
                clear();
                for (ApiSerializable c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

    private void bindViewHolder(ViewHolder holder, int position) {

        ApiSerializable item = listVehicle.get(position);

        holder.setText(item.getUIName());
        holder.setSelected(item.isChecked());
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResourceId, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ApiSerializable item = getItem(position);
                    item.isChecked(!item.isChecked());

                    if (mListner != null){
                        if (item != null){
                            mListner.onSelected(item);
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

    class ViewHolder {

        public TextView tvItem;
        public CheckBox cbItem;

        public ViewHolder(View view) {
            tvItem = view.findViewById(R.id.tvItem);
            cbItem = view.findViewById(R.id.cbItem);
        }

        public void setText(String txt){
            if (tvItem != null){
                tvItem.setText(txt);
            } else if (cbItem != null){
                cbItem.setText(txt);
            }
        }

        public void setSelected(boolean selected){
            if (cbItem != null){
                cbItem.setChecked(selected);
            }
        }
    }
}

