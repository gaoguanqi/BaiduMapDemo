package com.maple.baidu.demo.map;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.maple.baidu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkerActivity extends AppCompatActivity implements BaiduMap.OnMarkerClickListener {

    @BindView(R.id.bmap_view)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private BitmapDescriptor descriptor;
    private BitmapDescriptor poDescriptor;
    private List<LatLng> mLatLngs;
    private List<Marker> mMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);
        ButterKnife.bind(this);
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        poDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker);
        mLatLngs = new ArrayList<>();
        mMarkers = new ArrayList<>();

        LatLng latLng1 = new LatLng(39.91076, 116.31507);
        LatLng latLng2 = new LatLng(39.97986, 116.39517);
        mLatLngs.add(latLng1);
        mLatLngs.add(latLng2);
        addMarkers(mLatLngs);
        mBaiduMap.setOnMarkerClickListener(this);
    }

    private void addMarkers(List<LatLng> list) {
        for (int i = 0; i < list.size(); i++) {
            Bundle mBundle = new Bundle();
            mBundle.putInt("id", i);
            LatLng latLng = list.get(i);
            //创建marker
            OverlayOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .animateType(MarkerOptions.MarkerAnimateType.none)
                    .title("title")
                    .anchor(0.5f, 1.0f)
                    .extraInfo(mBundle)
                    .icon(descriptor)
                    .draggable(false);//marker是否可拖拽
            //添加marker
            Marker mMarker = (Marker) (mBaiduMap.addOverlay(markerOptions));
            mMarkers.add(mMarker);
        }
      

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //动态更换marker 图标
        for (int i = 0; i < mMarkers.size(); i++) {
            mMarkers.get(i).setIcon(descriptor);
        }
        marker.setIcon(poDescriptor);
        View view = View.inflate(this, R.layout.info_window, null);
        final InfoWindow infoWindow = new InfoWindow(view, marker.getPosition(), -100);
        mBaiduMap.showInfoWindow(infoWindow);

        return false;
    }
}
