package com.maple.baidu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.baidu.demo.location.LocationActivity;
import com.maple.baidu.demo.map.MapActivity;
import com.maple.baidu.demo.map.MarkerActivity;
import com.maple.baidu.demo.navigation.NavigationActivity;
import com.maple.baidu.utils.LogUtils;
import com.maple.baidu.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        applyPermissions();
    }

    private void applyPermissions() {
        PermissionUtil.applyPermissions(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                LogUtils.logGGQ("权限通过");
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                LogUtils.logGGQ("权限拒绝");
                // showPermissionFailDialog(permissions);
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                LogUtils.logGGQ("权限不在提示");
                //showPermissionFailDialog(permissions);
            }
        }, new RxPermissions(this));
    }


    @OnClick({R.id.btn_location, R.id.btn_map, R.id.btn_nav,R.id.btn_marker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_location:
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.btn_map:
                startActivity(new Intent(this, MapActivity.class));
                break;
            case R.id.btn_nav:
                startActivity(new Intent(this, NavigationActivity.class));
                break;
            case R.id.btn_marker:
                startActivity(new Intent(this, MarkerActivity.class));
                break;
            default:
                break;
        }
    }
}
