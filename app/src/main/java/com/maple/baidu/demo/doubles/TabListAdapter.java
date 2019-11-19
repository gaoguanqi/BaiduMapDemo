package com.maple.baidu.demo.doubles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maple.baidu.R;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TabListAdapter extends RecyclerView.Adapter<TabListAdapter.ViewHolder> {
    private Context mContext;
    private List<ItemBean> mData;
    private OnClickListener mListener;

    public TabListAdapter(Context context, List<ItemBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TabListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_tab_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TabListAdapter.ViewHolder holder, int position) {
        if (mListener != null) {
            holder.root.setOnClickListener(v -> {
                for (ItemBean item : mData) {
                    item.setHasSelected(false);
                }
                mData.get(position).setHasSelected(true);
                mListener.onItemClick(position);
            });
        }

        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_dtab)
        TextView tvTab;

        @BindView(R.id.item_root)
        LinearLayout root;

        @BindDrawable(R.drawable.shape_tab_selected)
        Drawable drwSelected;
        @BindDrawable(R.drawable.shape_tab_noraml)
        Drawable drwNoraml;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ItemBean item) {
            tvTab.setText(item.getTxt());
            if(item.isHasSelected()){
                tvTab.setBackground(drwSelected);
            }else {
                tvTab.setBackground(drwNoraml);
            }
        }
    }

    public interface OnClickListener {
        void onItemClick(int pos);
    }
}
