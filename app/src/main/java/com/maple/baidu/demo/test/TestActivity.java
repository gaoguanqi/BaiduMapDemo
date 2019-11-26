package com.maple.baidu.demo.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.maple.baidu.R;
import com.maple.baidu.demo.location.LocationActivity;
import com.maple.baidu.demo.map.MapActivity;
import com.maple.baidu.demo.map.MarkerActivity;
import com.maple.baidu.demo.navigation.NavigationActivity;
import com.maple.baidu.utils.GPSUtils;
import com.maple.baidu.utils.LogUtils;
import com.maple.baidu.utils.MaterialDialogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_file, R.id.btn_download,R.id.btn_distance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_file:
                String path = "";
                boolean isFileExists = FileUtils.isFileExists(path);
                ToastUtils.showShort("是否存在："+isFileExists);
                break;
            case R.id.btn_download:
                showDialog("下载文件啊啊啊");
                break;
            case R.id.btn_distance:


                //百度定位当前位置
                double startLon = 121.432619;
                double startLat = 31.188608;


                //服务端配置基站位置，需要转换 到百度坐标系
                double endLon = 117.238312;
                double endLat = 31.853603;



                //将坐标转化成百度地图的坐标
                CoordinateConverter converter = new CoordinateConverter();
                converter.from(CoordinateConverter.CoordType.GPS);
                converter.coord(new LatLng(endLat,endLon));
                final LatLng desLatLng = converter.convert();
                double distance = GPSUtils.getDistance(startLat, startLon, desLatLng.latitude, desLatLng.longitude);
                LogUtils.logGGQ("距离："+distance +"km");

                break;
            default:
                break;
        }
    }


    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
             dialog = new MaterialDialog.Builder(this)
                    .customView(R.layout.dialog_download, true)
                     .autoDismiss(false)
                     .contentGravity(GravityEnum.CENTER)
                    .build();
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);

            tvTitle.setText(title);

            Button btnCancle = (Button) dialog.findViewById(R.id.btn_cancle);
            btnCancle.setOnClickListener(v -> dialog.cancel());
            dialog.show();

        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
