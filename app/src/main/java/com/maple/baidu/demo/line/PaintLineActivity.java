package com.maple.baidu.demo.line;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.maple.baidu.R;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaintLineActivity extends AppCompatActivity implements SensorEventListener {

    @BindView(R.id.bmap_view)
    MapView mMapView;
    @BindView(R.id.btn_line)
    Button btnLine;

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

    private List<LatLng> latLngs = new ArrayList<LatLng>();
    private Polyline mPolyline;
    private LatLng target;
    private MapStatus.Builder builder;

    private Marker mMarkerA;
    private Marker mMarkerB;
    private InfoWindow mInfoWindow;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{

                }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_line);
        ButterKnife.bind(this);

        mIconMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务
        //默认模式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        btnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MarkerOptions oStart = new MarkerOptions();//地图标记覆盖物参数配置类
                oStart.position(latLngs.get(0));//覆盖物位置点，第一个点为起点
                oStart.icon(mIconMarker);//设置覆盖物图片
                oStart.zIndex(1);//设置覆盖物Index
                mMarkerA = (Marker) (mBaiduMap.addOverlay(oStart)); //在地图上添加此图层

                //添加终点图层
                MarkerOptions oFinish = new MarkerOptions().position(latLngs.get(latLngs.size()-1)).icon(mCurrentMarker).zIndex(2);
                mMarkerB = (Marker) (mBaiduMap.addOverlay(oFinish));

                OverlayOptions ooPolyline = new PolylineOptions()
                        .width(13)
                        .color(0xAAFF0000)
                        .points(latLngs)
                        .dottedLine(true);
                mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
                mPolyline.setZIndex(3);
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
        mBaiduMap.setOnPolylineClickListener(new BaiduMap.OnPolylineClickListener() {
            @Override
            public boolean onPolylineClick(Polyline polyline) {
                if (polyline.getZIndex() == mPolyline.getZIndex()) {
                    Toast.makeText(getApplicationContext(),"点数：" + polyline.getPoints().size() + ",width:" + polyline.getWidth(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


//        initLocation();
        coordinateConvert();
        builder = new MapStatus.Builder();
        builder.target(target).zoom(18f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

//        MarkerOptions oStart = new MarkerOptions();//地图标记覆盖物参数配置类
//        oStart.position(latLngs.get(0));//覆盖物位置点，第一个点为起点
//        oStart.icon(mIconMarker);//设置覆盖物图片
//        oStart.zIndex(1);//设置覆盖物Index
//        mMarkerA = (Marker) (mBaiduMap.addOverlay(oStart)); //在地图上添加此图层
//
//        //添加终点图层
//        MarkerOptions oFinish = new MarkerOptions().position(latLngs.get(latLngs.size()-1)).icon(mCurrentMarker).zIndex(2);
//        mMarkerB = (Marker) (mBaiduMap.addOverlay(oFinish));


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {

                if (marker.getZIndex() == mMarkerA.getZIndex() ) {//如果是起始点图层
                    TextView textView = new TextView(getApplicationContext());
                    textView.setText("起点");
                    textView.setTextColor(Color.BLACK);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundResource(R.drawable.icon_bs);

                    //设置信息窗口点击回调
                    InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            Toast.makeText(getApplicationContext(),"这里是起点", Toast.LENGTH_SHORT).show();
                            mBaiduMap.hideInfoWindow();//隐藏信息窗口
                        }
                    };
                    LatLng latLng = marker.getPosition();//信息窗口显示的位置点
                    /**
                     * 通过传入的 bitmap descriptor 构造一个 InfoWindow
                     * bd - 展示的bitmap
                     position - InfoWindow显示的位置点
                     yOffset - 信息窗口会与图层图标重叠，设置Y轴偏移量可以解决
                     listener - 点击监听者
                     */
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(textView), latLng, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);//显示信息窗口

                } else if (marker.getZIndex() == mMarkerB.getZIndex()) {//如果是终点图层
                    Button button = new Button(getApplicationContext());
                    button.setText("终点");
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),"这里是终点", Toast.LENGTH_SHORT).show();
                            mBaiduMap.hideInfoWindow();
                        }
                    });
                    LatLng latLng = marker.getPosition();
                    /**
                     * 通过传入的 view 构造一个 InfoWindow, 此时只是利用该view生成一个Bitmap绘制在地图中，监听事件由自己实现。
                     view - 展示的 view
                     position - 显示的地理位置
                     yOffset - Y轴偏移量
                     */
                    mInfoWindow = new InfoWindow(button, latLng, -47);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });

        mBaiduMap.setOnPolylineClickListener(new BaiduMap.OnPolylineClickListener() {
            @Override
            public boolean onPolylineClick(Polyline polyline) {
                if (polyline.getZIndex() == mPolyline.getZIndex()) {
                    Toast.makeText(getApplicationContext(),"点数：" + polyline.getPoints().size() + ",width:" + polyline.getWidth(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //添加纹理图片
//        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
//        textureList.add(mIconMarker);
//        textureList.add(mIconMarker);
//        textureList.add(mIconMarker);

//        List<Integer> textureIndexs = new ArrayList<Integer>();
//        textureIndexs.add(0);
//        textureIndexs.add(1);
//        textureIndexs.add(2);

        OverlayOptions ooPolyline = new PolylineOptions()
                .width(13)
                .color(0xAAFF0000)
                .points(latLngs)
//                .customTextureList(textureList)
//                .textureIndex(textureIndexs)
                .dottedLine(true);

//        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
//        mPolyline.setZIndex(3);


    }

    /**
     * 讲google地图的wgs84坐标转化为百度地图坐标
     */
    private void  coordinateConvert(){
        CoordinateConverter converter  = new CoordinateConverter();
        converter.from(CoordType.COMMON);
        double lanSum = 0;
        double lonSum = 0;


        for (int i = 0; i < Const.googleWGS84.length; i++) {
            String[] ll = Const.googleWGS84[i].split(",");
            LatLng sourceLatLng = new LatLng(Double.valueOf(ll[0]), Double.valueOf(ll[1]));
            converter.coord(sourceLatLng);
            LatLng desLatLng = converter.convert();
            latLngs.add(desLatLng);
            lanSum += desLatLng.latitude;
            lonSum += desLatLng.longitude;
        }
        target = new LatLng(lanSum/latLngs.size(), lonSum/latLngs.size());
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
                target = ll;
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
        mMapView.getMap().clear();
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
