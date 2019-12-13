package com.maple.baidu.demo.remove;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maple.baidu.R;
import com.maple.baidu.demo.doubles.TabListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListRemoveActivity extends AppCompatActivity {


    @BindView(R.id.rv_remove)
    RecyclerView rvRemove;
    @BindView(R.id.rv_tab)
    RecyclerView rvTab;
    @BindView(R.id.btn_add)
    Button btnAdd;

    private List<String> mData;
    private ListRemoveAdapter mAdapter;
    private ListTabAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_remove);
        ButterKnife.bind(this);

        mData = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mData.add("item"+i);
        }

        rvRemove.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager m = new LinearLayoutManager(this);
        m.setOrientation(RecyclerView.HORIZONTAL);
        rvTab.setLayoutManager(m);
        mAdapter = new ListRemoveAdapter(this);
        mTabAdapter = new ListTabAdapter(this);
        mAdapter.setData(mData);
        mTabAdapter.setData(mData);
        mAdapter.setListener(new ListRemoveAdapter.OnClickListener() {
            @Override
            public void onDeleteItemClick(int pos) {
                mData.remove(pos);
                mAdapter.notifyDataSetChanged();
                mTabAdapter.notifyDataSetChanged();
            }
        });
        rvRemove.setAdapter(mAdapter);
        rvTab.setAdapter(mTabAdapter);

    }

    @OnClick({ R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                mData.add("item"+(mData.size()+1));
                mAdapter.notifyDataSetChanged();
                mTabAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
