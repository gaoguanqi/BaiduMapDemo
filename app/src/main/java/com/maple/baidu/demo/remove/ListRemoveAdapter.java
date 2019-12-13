package com.maple.baidu.demo.remove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maple.baidu.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRemoveAdapter extends RecyclerView.Adapter<ListRemoveAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mList;

    private OnClickListener mListener;


    public ListRemoveAdapter(Context context) {
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
        return new ViewHolder(LayoutInflater.from(parent.getContext() == null ? mContext : parent.getContext()).inflate(R.layout.item_list_remove, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position,mList.get(position));
        if (mListener != null) {
            holder.ibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDeleteItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_no)
        TextView tvNo;
        @BindView(R.id.tv_content)
        TextView tvContent;

        @BindView(R.id.ibtn_delete)
        ImageButton ibtnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(int position, String data) {
            tvNo.setText(data);
            tvContent.setText(data);
        }
    }

    public interface OnClickListener {
        void onDeleteItemClick(int pos);
    }
}
