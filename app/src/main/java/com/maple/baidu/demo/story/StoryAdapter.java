package com.maple.baidu.demo.story;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maple.baidu.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mData;
    private OnClickListener mListener;

    public StoryAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_story, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.ViewHolder holder, int position) {
        holder.setData(mData.get(position));
        if(mListener != null){
            holder.btnAdd.setOnClickListener(v -> mListener.onAddClick(position));
            holder.btnRemove.setOnClickListener(v -> mListener.onRemoveClick(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv_name)
        TextView tvName;
        @BindView(R.id.btn_add)
        Button btnAdd;
        @BindView(R.id.btn_remove)
        Button btnRemove;
        @BindView(R.id.item_root)
        LinearLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(String s) {
            tvName.setText(s);
        }
    }

    public interface OnClickListener {
        void onAddClick(int pos);
        void onRemoveClick(int pos);
    }
}
