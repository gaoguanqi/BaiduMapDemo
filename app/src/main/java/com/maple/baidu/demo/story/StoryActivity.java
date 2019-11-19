package com.maple.baidu.demo.story;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.maple.baidu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StoryActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindDrawable(R.drawable.shape_tab_noraml)
//    Drawable drwNormal;
//    @BindDrawable(R.drawable.shape_tab_selected)
//    Drawable drwselected;

    private List<String> list;
    private StoryAdapter mAdapter;
    private LinearLayoutManager manager;

    //判读是否是recyclerView主动引起的滑动，true- 是，false- 否，由tablayout引起的
    private boolean isRecyclerScroll;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int lastPos;
    //用于recyclerView滑动到指定的位置
    private boolean canScroll;
    private int scrollToPosition;

    private List<TabLayout.Tab> tabList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);


        list = new ArrayList<>();
        tabList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i+"");
            TabLayout.Tab tab = tabLayout.newTab();
            View inflate = View.inflate(this, R.layout.item_tab, null);
            TextView textView = inflate.findViewById(R.id.tv_tab);
            textView.setText("标题"+i);
            tab.setCustomView(inflate);
            tabLayout.addTab(tab);
            tabList.add(tab);
        }





        manager = new LinearLayoutManager(this);
        mAdapter = new StoryAdapter(this,list);
        mAdapter.setListener(new StoryAdapter.OnClickListener() {

            @Override
            public void onAddClick(int pos) {
                list.add(String.valueOf(list.size()+1));

                TabLayout.Tab tab = tabLayout.newTab();
                View inflate = View.inflate(StoryActivity.this, R.layout.item_tab, null);
                TextView textView = inflate.findViewById(R.id.tv_tab);
                textView.setText("新增标题"+list.size());
                tab.setCustomView(inflate);
                tabLayout.addTab(tab);
                tabList.add(tab);
                manager.scrollToPosition(0);


            }

            @Override
            public void onRemoveClick(int pos) {
                list.remove(pos);
                tabLayout.removeTabAt(pos);
                mAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //点击标签，使recyclerView滑动，isRecyclerScroll置false
                int pos = tab.getPosition();
                isRecyclerScroll = false;
                moveToPosition(manager, recyclerView, pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                //当滑动由recyclerView触发时，isRecyclerScroll 置true
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    isRecyclerScroll = true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (canScroll) {
                    canScroll = false;
                    moveToPosition(manager, recyclerView, scrollToPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isRecyclerScroll) {
                    //第一个可见的view的位置，即tablayou需定位的位置
                    int position = manager.findFirstVisibleItemPosition();
                    if (lastPos != position) {
                        tabLayout.setScrollPosition(position, 0, true);
                    }
                    lastPos = position;
                }
            }
        });

    }

    public void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int position) {
        // 第一个可见的view的位置
        int firstItem = manager.findFirstVisibleItemPosition();
        // 最后一个可见的view的位置
        int lastItem = manager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            // 如果跳转位置firstItem 之前(滑出屏幕的情况)，就smoothScrollToPosition可以直接跳转，
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在firstItem 之后，lastItem 之间（显示在当前屏幕），smoothScrollBy来滑动到指定位置
            int top = mRecyclerView.getChildAt(position - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            // 如果要跳转的位置在lastItem 之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用当前moveToPosition方法，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            scrollToPosition = position;
            canScroll = true;
        }
    }

}
