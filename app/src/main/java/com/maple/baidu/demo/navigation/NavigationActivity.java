package com.maple.baidu.demo.navigation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.maple.baidu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NavigationActivity extends AppCompatActivity {

    private double lon = 117.238312;
    private double lat = 31.853603;

    @BindView(R.id.bmap_view)
    MapView mMapView;

    private BaiduMap mBaiduMap;

    private BitmapDescriptor descriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE); //卫星图
        LatLng latlng = new LatLng(lat,lon );
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(latlng);
        final LatLng desLatLng = converter.convert();


        descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        //创建marker
        OverlayOptions markerOptions = new MarkerOptions()
                .position(desLatLng)
                .animateType(MarkerOptions.MarkerAnimateType.none)
                .title("title")
                .anchor(0.5f, 1.0f)
                .icon(descriptor)
                .draggable(false);//marker是否可拖拽
        //添加marker
        Marker mMarker = (Marker) (mBaiduMap.addOverlay(markerOptions));
        //设置地图中心点
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(mMarker.getPosition());
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

    }



}
