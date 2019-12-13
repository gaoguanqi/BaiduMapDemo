package com.maple.baidu.demo.remove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maple.baidu.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListTabAdapter extends RecyclerView.Adapter<ListTabAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mList;

    private OnClickListener mListener;


    public ListTabAdapter(Context context) {
        this.mContext = context;
    }


    public void setData(List<String> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext() == null ? mContext : parent.getContext()).inflate(R.layout.item_list_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position,mList.get(position));
        if (mListener != null) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tab)
        TextView tvTab;
        @BindView(R.id.item_root)
        LinearLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(int position, String data) {
            tvTab.setText(data);
        }
    }

    interface OnClickListener {
        void onItemClick(int pos);
    }
}
