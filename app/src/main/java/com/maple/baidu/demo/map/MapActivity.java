package com.maple.baidu.demo.map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.ScaleAnimation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.maple.baidu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements SensorEventListener {
    @BindView(R.id.bmap_view)
    MapView mMapView;
    @BindView(R.id.btn_add)
    Button btnAdd;

    // 定位相关
    private LocationClient mLocClient;
    private MyLocationListener mListener = new MyLocationListener();

    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true; // 是否首次定位

    private SensorManager mSensorManager;
    private MyLocationConfiguration.LocationMode mCurrentMode;

    //自定义
    private BitmapDescriptor mCurrentMarker;
    private BitmapDescriptor mIconMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        mIconMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务
        //默认模式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng1 = new LatLng(39.91076, 116.31507);
                addMarkers(latLng1);
            }
        });

        // 地图初始化
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //点击地图某个位置获取经纬度latLng.latitude、latLng.longitude
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                //点击地图上的poi图标获取描述信息：mapPoi.getName()，经纬度：mapPoi.getPosition()
                return false;
            }
        });
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

    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);


                //跟随模式
                mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                mBaiduMap.setMyLocationConfiguration(
                        new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


                // onSuccess();
            }
        }
    }

    private Double lastX = 0.0;
    private MyLocationData locData;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //每次方向改变，重新给地图设置定位数据，用上一次onReceiveLocation得到的经纬度、精度
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {// 方向改变大于1度才设置，以免地图上的箭头转动过于频繁
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder().accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat).longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);

        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    //成功获取到数据
    private void onSuccess() {
        //添加marker
        double lat = 39.93076;
        double lng = 116.30507;
        LatLng latLng = new LatLng(lat, lng);

        addMarkers(latLng);

        LatLng latLng1 = new LatLng(39.91076, 116.31507);
        addMarkers(latLng1);

        LatLng latLng2 = new LatLng(39.92076, 116.32507);
        addMarkers(latLng2);
    }

//    private MarkerOptions getMarkerOptions(){
//        /**
//         * 绘制Marker，地图上常见的类似气球形状的图层
//         */
//       // double lat = 111.75199;
//        //double lng = 40.84149;
//        double lat = mCurrentLat;
//        double lng = mCurrentLon;
//        LatLng latLng = new LatLng(lat,lng);
//
//        MarkerOptions markerOptions = new MarkerOptions();//参数设置类
//        markerOptions.position(latLng);//marker坐标位置
//        markerOptions.icon(mIconMarker);//marker图标，可以自定义
//        markerOptions.draggable(false);//是否可拖拽，默认不可拖拽
//        markerOptions.anchor(0.5f, 1.0f);//设置 marker覆盖物与位置点的位置关系，默认（0.5f, 1.0f）水平居中，垂直下对齐
//        markerOptions.alpha(0.8f);//marker图标透明度，0~1.0，默认为1.0
//        //markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);//marker出现的方式，从天上掉下
//        markerOptions.animateType(MarkerOptions.MarkerAnimateType.none);
//        markerOptions.flat(false);//marker突变是否平贴地面
//        markerOptions.zIndex(1);//index
//
//        //Marker动画效果
//        // markerOptions.icons(bitmapList);//如果需要显示动画，可以设置多张图片轮番显示
//        // markerOptions.period(10);//每个10ms显示bitmapList里面的图片
//        // mMarker = (Marker) mBaiduMap.addOverlay(markerOptions);//在地图上增加mMarker图层
//        return markerOptions;
//    }


    private void addMarkers(LatLng latLng) {
        //创建marker
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(mIconMarker);
        //添加marker
        Marker mMarker = (Marker) (mBaiduMap.addOverlay(markerOptions));
        startSingleScaleAnimation(mMarker);

        setUserMapCenter(latLng);

    }

    /**
     * 设置中心点
     */
    private void setUserMapCenter(LatLng latLng) {
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    /**
     * 开启单边缩放动画 X或Y方向
     */
    public void startSingleScaleAnimation(Marker marker) {
        //marker设置动画
        marker.setAnimation(getScaleAnimation());
        //开启marker动画
        marker.startAnimation();
    }

    private ScaleAnimation mScale;

    private Animation getScaleAnimation() {
        //创建缩放动画
        if (mScale == null) {
            mScale = new ScaleAnimation(1f, 1.5f, 1f);
        }
        //设置动画执行时间
        mScale.setDuration(2000);
        //动画重复模式
        mScale.setRepeatMode(Animation.RepeatMode.RESTART);
        //动画重复次数
        mScale.setRepeatCount(100);
        //设置缩放动画监听
        mScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });
        return mScale;
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        // 取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mScale != null) {
            mScale.cancel();
        }
        // 退出时销毁定位
        mLocClient.unRegisterLocationListener(mListener);
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
