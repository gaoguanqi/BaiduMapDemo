package com.maple.baidu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.baidu.demo.anim.MyAnimActivity;
import com.maple.baidu.demo.bstest.BsTestActivity;
import com.maple.baidu.demo.doubles.DoublesActivity;
import com.maple.baidu.demo.line.PaintLineActivity;
import com.maple.baidu.demo.location.LocationActivity;
import com.maple.baidu.demo.map.MapActivity;
import com.maple.baidu.demo.map.MarkerActivity;
import com.maple.baidu.demo.navigation.NavigationActivity;
import com.maple.baidu.demo.remove.ListRemoveActivity;
import com.maple.baidu.demo.spinner.SpinnerActivity;
import com.maple.baidu.demo.story.StoryActivity;
import com.maple.baidu.demo.tablayout.TabLayoutActivity;
import com.maple.baidu.demo.telephone.TelePhoneActivity;
import com.maple.baidu.demo.test.TestActivity;
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


    @OnClick({R.id.btn_location, R.id.btn_map, R.id.btn_nav,R.id.btn_marker,R.id.btn_test,R.id.btn_tab,R.id.btn_viewPage2,R.id.btn_doubles,R.id.btn_bstest,R.id.btn_telephone,R.id.btn_spinner,R.id.btn_remove,R.id.btn_tablayout,R.id.btn_paintline,R.id.btn_my_anim})
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
            case R.id.btn_test:
                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.btn_tab:
                startActivity(new Intent(this, StoryActivity.class));
                break;
            case R.id.btn_viewPage2:
                startActivity(new Intent(this, ViewPage2Activity.class));
                break;
            case R.id.btn_doubles:
                startActivity(new Intent(this, DoublesActivity.class));
                break;
            case R.id.btn_bstest:
                startActivity(new Intent(this, BsTestActivity.class));
                break;
            case R.id.btn_telephone:
                startActivity(new Intent(this, TelePhoneActivity.class));
                break;
            case R.id.btn_spinner:
                startActivity(new Intent(this, SpinnerActivity.class));
                break;
            case R.id.btn_remove:
                startActivity(new Intent(this, ListRemoveActivity.class));
                break;
            case R.id.btn_tablayout:
                startActivity(new Intent(this, TabLayoutActivity.class));
                break;
            case R.id.btn_paintline:
                startActivity(new Intent(this, PaintLineActivity.class));
                break;
            case R.id.btn_my_anim:
                startActivity(new Intent(this, MyAnimActivity.class));
                break;
            default:
                break;
        }
    }
}
