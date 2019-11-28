package com.maple.baidu.demo.spinner;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.baidu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SpinnerActivity extends AppCompatActivity {

    private SpinerPopWindow<String> mSpinerPopWindow;
    private List<String> list;
    private TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        ButterKnife.bind(this);
        initData();

        tvValue = (TextView) findViewById(R.id.tv_value);
        tvValue.setOnClickListener(clickListener);
        mSpinerPopWindow = new SpinerPopWindow<String>(this, list,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);

    }

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setTextImage(R.drawable.icon_marker);
        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            tvValue.setText(list.get(position));
            Toast.makeText(SpinnerActivity.this, "点击了:" + list.get(position),Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 显示PopupWindow
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_value:
                    mSpinerPopWindow.setWidth(tvValue.getWidth());
                    mSpinerPopWindow.showAsDropDown(tvValue);
                    setTextImage(R.drawable.icon_bs);
                    break;
            }
        }
    };

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("test:" + i);
        }
    }

    /**
     * 给TextView右边设置图片
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        tvValue.setCompoundDrawables(null, null, drawable, null);
    }

}
