package com.maple.baidu.demo.doubles;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maple.baidu.R;
import com.maple.baidu.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoublesActivity extends AppCompatActivity {

    @BindView(R.id.rv_tab)
    RecyclerView rvTab;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private TabListAdapter tabListAdapter;
    private MyListAdapter myListAdapter;

    private List<ItemBean> list;
    private List<ItemBean> tabList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubles);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        tabList = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            ItemBean item = new ItemBean(false,i+"");
            list.add(item);
            tabList.add(item);
        }

        tabListAdapter = new TabListAdapter(this,tabList);
        myListAdapter = new MyListAdapter(this,list);

        WrapContentLinearLayoutManager listManager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        WrapContentLinearLayoutManager tabManager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvTab.setLayoutManager(tabManager);
        rvList.setLayoutManager(listManager);

        rvTab.setAdapter(tabListAdapter);
        rvList.setAdapter(myListAdapter);


        tabListAdapter.setListener(new TabListAdapter.OnClickListener() {
            @Override
            public void onItemClick(int pos) {
                tabManager.smoothScrollToPosition(rvTab, new RecyclerView.State(), pos);
                rvList.smoothScrollToPosition(pos);
            }
        });



        myListAdapter.setListener(new MyListAdapter.OnClickListener() {
            @Override
            public void onAddClick(int pos) {
                ItemBean item = new ItemBean(false,"新"+list.size());
                list.add(item);
                tabList.add(item);
                tabListAdapter.notifyItemChanged(tabList.size());
                myListAdapter.notifyItemChanged(list.size());

                rvTab.smoothScrollToPosition(tabList.size());
                rvList.smoothScrollToPosition(list.size());
            }

            @Override
            public void onRemoveClick(int pos) {
                LogUtils.logGGQ("移除pos："+pos);
                list.remove(pos);
                tabList.remove(pos);
                tabListAdapter.notifyDataSetChanged();
                myListAdapter.notifyDataSetChanged();
            }
        });

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtils.logGGQ("onScrollStateChanged  newState:"+newState);
            }
        });


    }



}
