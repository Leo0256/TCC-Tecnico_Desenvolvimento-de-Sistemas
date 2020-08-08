package com.LizCore.app.AccountOptions.ListViewOptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LizCore.R;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.mViewHolder> implements Filterable {

    private ArrayList<ItemListView> list;
    private ArrayList<ItemListView> listFull;
    private OnItemClickListener mListener;

    public AdapterRecyclerView(ArrayList<ItemListView> items) {
        this.list = items;
        this.listFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item_card, parent, false);
        return new mViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        ItemListView currentItem = list.get(position);

        if (currentItem != null) {
            holder.mId.setText(String.valueOf(currentItem.getId()));
            holder.mName.setText(currentItem.getName());
            holder.mValue.setText(currentItem.getValue());
            holder.mDate.setText(currentItem.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ItemListView> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemListView item : listFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    static class mViewHolder extends RecyclerView.ViewHolder {

        private TextView mId, mName, mValue, mDate;
        private ImageView mDeleteItem;

        private mViewHolder(@NonNull View itemView, @NonNull final OnItemClickListener listener) {
            super(itemView);

            mId = itemView.findViewById(R.id.id_item);
            mName = itemView.findViewById(R.id.name_item);
            mValue = itemView.findViewById(R.id.value_item);
            mDate = itemView.findViewById(R.id.date_item);

            mDeleteItem = itemView.findViewById(R.id.delete_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

            mDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}
