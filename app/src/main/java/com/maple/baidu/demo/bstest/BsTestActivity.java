package com.maple.baidu.demo.bstest;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.maple.baidu.R;
import com.maple.baidu.demo.map.MapActivity;
import com.maple.baidu.utils.GPSUtils;
import com.maple.baidu.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BsTestActivity extends AppCompatActivity {
    @BindView(R.id.bmap_view)
    MapView bmapView;
    @BindView(R.id.tv_content)
    TextView tvContent;

    // 定位相关
    private LocationClient mLocClient;
    private BsTestActivity.MyLocationListener mListener = new BsTestActivity.MyLocationListener();

    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true; // 是否首次定位

    private SensorManager mSensorManager;
    private MyLocationConfiguration.LocationMode mCurrentMode;

    private BitmapDescriptor currDes,bsDes;
    private int currentDirection = 0;

    private double lon = 121.421897;
    private double lat = 31.183865;


    private double currentLat;
    private double currentLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bs_test);
        ButterKnife.bind(this);

        currDes = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        bsDes = BitmapDescriptorFactory.fromResource(R.drawable.icon_bs);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务
        //默认模式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 地图初始化
        mBaiduMap = bmapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        initLocation();
    }

    private void initLocation() {
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(mListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(0); //可选3000，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        //option.setNeedDeviceDirect(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            // map view 销毁后不在处理新接收的位置
            if (location == null || bmapView == null) {
                return;
            }
             currentLat = location.getLatitude();
             currentLon = location.getLongitude();
            float currentAccracy = location.getRadius();
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(currentDirection).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);


                //跟随模式
                mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, currDes));
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                addMarker();
            }


            double distance = GPSUtils.getDistance(currentLat, currentLon, desLatLng.latitude, desLatLng.longitude);
            LogUtils.logGGQ("距离："+distance +"km");

            tvContent.setText("当前位置 Longitude:" +currentLon +"--Latitude:"+currentLat +"\t"+
                    "基站位置 Longitude:"+lon+"--Latitude:"+lat+"\t"+
                    "计算距离："+ distance+"km");
        }
    }

    private LatLng desLatLng;
    private void addMarker() {
        LatLng latlng = new LatLng(lat,lon);
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(latlng);
        desLatLng = converter.convert();

        //创建marker
        OverlayOptions markerOptions = new MarkerOptions()
                .position(desLatLng)
                .animateType(MarkerOptions.MarkerAnimateType.none)
                .title("title")
                .anchor(0.5f, 1.0f)
                .icon(bsDes)
                .draggable(false);//marker是否可拖拽
        //添加marker
        Marker mMarker = (Marker) (mBaiduMap.addOverlay(markerOptions));
        //设置地图中心点
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(mMarker.getPosition());
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

    }
}
