package com.maple.baidu.demo.telephone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.telephony.CellIdentity;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.maple.baidu.R;
import com.maple.baidu.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint({"NewApi", "MissingPermission"})
public class TelePhoneActivity extends AppCompatActivity {
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_change)
    TextView tvChange;
    private TelephonyManager tm;


    /**
     * put(PhoneStateListener.LISTEN_SERVICE_STATE, "SERVICE_STATE");
     * put(PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR, "MESSAGE_WAITING_INDICATOR");
     * put(PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR, "CALL_FORWARDING_INDICATOR");
     * put(PhoneStateListener.LISTEN_CELL_LOCATION, "CELL_LOCATION");
     * put(PhoneStateListener.LISTEN_CALL_STATE, "CALL_STATE");
     * put(PhoneStateListener.LISTEN_DATA_CONNECTION_STATE, "DATA_CONNECTION_STATE");
     * put(PhoneStateListener.LISTEN_DATA_ACTIVITY, "DATA_ACTIVITY");
     * put(PhoneStateListener.LISTEN_SIGNAL_STRENGTHS, "SIGNAL_STRENGTHS");
     * put(PhoneStateListener.LISTEN_OTASP_CHANGED, "OTASP_CHANGED");
     * put(PhoneStateListener.LISTEN_CELL_INFO, "CELL_INFO");
     * put(PhoneStateListener.LISTEN_PRECISE_CALL_STATE, "PRECISE_CALL_STATE");
     * put(PhoneStateListener.LISTEN_PRECISE_DATA_CONNECTION_STATE,
     * "PRECISE_DATA_CONNECTION_STATE");
     * put(PhoneStateListener.LISTEN_SRVCC_STATE_CHANGED, "SRVCC_STATE");
     * put(PhoneStateListener.LISTEN_CARRIER_NETWORK_CHANGE, "CARRIER_NETWORK_CHANGE");
     * put(PhoneStateListener.LISTEN_VOICE_ACTIVATION_STATE, "VOICE_ACTIVATION_STATE");
     * put(PhoneStateListener.LISTEN_DATA_ACTIVATION_STATE, "DATA_ACTIVATION_STATE");
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele_phone);
        ButterKnife.bind(this);

//        PhoneStateListener.LISTEN_SERVICE_STATE|
//        PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR|
//        PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR|
//        PhoneStateListener.LISTEN_CELL_LOCATION|
//        PhoneStateListener.LISTEN_CALL_STATE|
//        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE|
//        PhoneStateListener.LISTEN_DATA_ACTIVITY|
//        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS|
//        PhoneStateListener.LISTEN_CELL_INFO|
//        PhoneStateListener.LISTEN_OTASP_CHANGED|
//        PhoneStateListener.LISTEN_PRECISE_CALL_STATE|
//        PhoneStateListener.LISTEN_PRECISE_DATA_CONNECTION_STATE|
//        PhoneStateListener.LISTEN_SRVCC_STATE_CHANGED|
//        PhoneStateListener.LISTEN_CARRIER_NETWORK_CHANGE|
//        PhoneStateListener.LISTEN_VOICE_ACTIVATION_STATE|
//        PhoneStateListener.LISTEN_DATA_ACTIVATION_STATE

        tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        tm.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
//                | PhoneStateListener.LISTEN_CELL_INFO
//                | PhoneStateListener.LISTEN_CELL_LOCATION
//                | PhoneStateListener.LISTEN_SERVICE_STATE
//                | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
//                | PhoneStateListener.LISTEN_DATA_ACTIVITY);

        tm.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_SERVICE_STATE |
                PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR |
                PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
                PhoneStateListener.LISTEN_CELL_LOCATION |
                PhoneStateListener.LISTEN_CALL_STATE |
                PhoneStateListener.LISTEN_DATA_CONNECTION_STATE |
                PhoneStateListener.LISTEN_DATA_ACTIVITY |
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS |
                PhoneStateListener.LISTEN_CELL_INFO);


        List<CellInfo> cellInfoList = tm.getAllCellInfo();
        if (cellInfoList != null && !cellInfoList.isEmpty()) {
            for (CellInfo info : cellInfoList) {
                if (info instanceof CellInfoLte) {
                    CellSignalStrengthLte th = ((CellInfoLte) info).getCellSignalStrength();
                    int dbm = th.getDbm();
                    int rsrp = th.getRsrp();
                    LogUtils.logGGQ("dbm:" + dbm);
                    LogUtils.logGGQ("rsrp:" + rsrp);

                    tvType.setText("CellInfoLte");


                    if (info instanceof CellInfoGsm) {
                        final CellSignalStrengthGsm gsm = ((CellInfoGsm) info).getCellSignalStrength();
                        final CellIdentityGsm identityGsm = ((CellInfoGsm) info).getCellIdentity();
                        // Signal Strength
//                    pDevice.mCell.setDBM(gsm.getDbm()); // [dBm]
//                    // Cell Identity
//                    pDevice.mCell.setCID(identityGsm.getCid());
//                    pDevice.mCell.setMCC(identityGsm.getMcc());
//                    pDevice.mCell.setMNC(identityGsm.getMnc());
//                    pDevice.mCell.setLAC(identityGsm.getLac());

                        LogUtils.logGGQ("gsm:" + gsm.getDbm());
                        tvType.setText("CellInfoGsm");
                    } else if (info instanceof CellInfoCdma) {
                        final CellSignalStrengthCdma cdma = ((CellInfoCdma) info).getCellSignalStrength();
                        final CellIdentityCdma identityCdma = ((CellInfoCdma) info).getCellIdentity();
                        // Signal Strength
//                    pDevice.mCell.setDBM(cdma.getDbm());
//                    // Cell Identity
//                    pDevice.mCell.setCID(identityCdma.getBasestationId());
//                    pDevice.mCell.setMNC(identityCdma.getSystemId());
//                    pDevice.mCell.setLAC(identityCdma.getNetworkId());
//                    pDevice.mCell.setSID(identityCdma.getSystemId());
                        LogUtils.logGGQ("cdma:" + cdma.getDbm());
                        tvType.setText("CellInfoCdma");
                    } else if (info instanceof CellInfoLte) {
                        final CellSignalStrengthLte lte = ((CellInfoLte) info).getCellSignalStrength();
                        final CellIdentityLte identityLte = ((CellInfoLte) info).getCellIdentity();
                        // Signal Strength
//                    pDevice.mCell.setDBM(lte.getDbm());
//                    pDevice.mCell.setTimingAdvance(lte.getTimingAdvance());
//                    // Cell Identity
//                    pDevice.mCell.setMCC(identityLte.getMcc());
//                    pDevice.mCell.setMNC(identityLte.getMnc());
//                    pDevice.mCell.setCID(identityLte.getCi());
                        LogUtils.logGGQ("lte:" + lte.getDbm());
                        tvType.setText("CellInfoLte");
                    } else if (info instanceof CellInfoWcdma) {
                        final CellSignalStrengthWcdma wcdma = ((CellInfoWcdma) info).getCellSignalStrength();
                        final CellIdentityWcdma identityWcdma = ((CellInfoWcdma) info).getCellIdentity();
                        // Signal Strength
//                    pDevice.mCell.setDBM(wcdma.getDbm());
//                    // Cell Identity
//                    pDevice.mCell.setLAC(identityWcdma.getLac());
//                    pDevice.mCell.setMCC(identityWcdma.getMcc());
//                    pDevice.mCell.setMNC(identityWcdma.getMnc());
//                    pDevice.mCell.setCID(identityWcdma.getCid());
//                    pDevice.mCell.setPSC(identityWcdma.getPsc());
                        LogUtils.logGGQ("wcdma:" + wcdma.getDbm());
                        tvType.setText("CellInfoWcdma");
                    } else if (info instanceof CellInfoNr) {
                        CellInfoNr cellInfoNr = (CellInfoNr) info;
                        CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();

                        int csiRsrp = cellSignalStrengthNr.getCsiRsrp();
                        int level = cellSignalStrengthNr.getAsuLevel();
                        int csiSinr = cellSignalStrengthNr.getCsiSinr();
                        int csiRsrq = cellSignalStrengthNr.getCsiRsrq();
                        int ssRsrp = cellSignalStrengthNr.getSsRsrp();
                        int ssSinr = cellSignalStrengthNr.getSsSinr();
                        int ddbm = cellSignalStrengthNr.getDbm();

                        String text= "CellSignalStrengthNr---"+"csiRsrp:"+csiRsrp+"---"+
                                "level:"+level+"---"+
                                "csiSinr:"+csiSinr+"---"+
                                "csiRsrq:"+csiRsrq+"---"+
                                "ssRsrp:"+ssRsrp+"---"+
                                "ssSinr:"+ssSinr+"---"+
                                "ddbm:"+ddbm;
                        tvContent.setText(text);
                        LogUtils.logGGQ("irsrp:" + csiRsrp);
                        tvType.setText("CellInfoNr");
                    }
                }else if (info instanceof CellInfoGsm) {
                    final CellSignalStrengthGsm gsm = ((CellInfoGsm) info).getCellSignalStrength();
                    final CellIdentityGsm identityGsm = ((CellInfoGsm) info).getCellIdentity();
                    // Signal Strength
//                    pDevice.mCell.setDBM(gsm.getDbm()); // [dBm]
//                    // Cell Identity
//                    pDevice.mCell.setCID(identityGsm.getCid());
//                    pDevice.mCell.setMCC(identityGsm.getMcc());
//                    pDevice.mCell.setMNC(identityGsm.getMnc());
//                    pDevice.mCell.setLAC(identityGsm.getLac());

                    LogUtils.logGGQ("gsm:" + gsm.getDbm());
                    tvType.setText("CellInfoGsm");
                } else if (info instanceof CellInfoCdma) {
                    final CellSignalStrengthCdma cdma = ((CellInfoCdma) info).getCellSignalStrength();
                    final CellIdentityCdma identityCdma = ((CellInfoCdma) info).getCellIdentity();
                    // Signal Strength
//                    pDevice.mCell.setDBM(cdma.getDbm());
//                    // Cell Identity
//                    pDevice.mCell.setCID(identityCdma.getBasestationId());
//                    pDevice.mCell.setMNC(identityCdma.getSystemId());
//                    pDevice.mCell.setLAC(identityCdma.getNetworkId());
//                    pDevice.mCell.setSID(identityCdma.getSystemId());
                    LogUtils.logGGQ("cdma:" + cdma.getDbm());
                    tvType.setText("CellInfoCdma");
                } else if (info instanceof CellInfoLte) {
                    final CellSignalStrengthLte lte = ((CellInfoLte) info).getCellSignalStrength();
                    final CellIdentityLte identityLte = ((CellInfoLte) info).getCellIdentity();
                    // Signal Strength
//                    pDevice.mCell.setDBM(lte.getDbm());
//                    pDevice.mCell.setTimingAdvance(lte.getTimingAdvance());
//                    // Cell Identity
//                    pDevice.mCell.setMCC(identityLte.getMcc());
//                    pDevice.mCell.setMNC(identityLte.getMnc());
//                    pDevice.mCell.setCID(identityLte.getCi());
                    LogUtils.logGGQ("lte:" + lte.getDbm());
                    tvType.setText("CellInfoLte");
                } else if (info instanceof CellInfoWcdma) {
                    final CellSignalStrengthWcdma wcdma = ((CellInfoWcdma) info).getCellSignalStrength();
                    final CellIdentityWcdma identityWcdma = ((CellInfoWcdma) info).getCellIdentity();
                    // Signal Strength
//                    pDevice.mCell.setDBM(wcdma.getDbm());
//                    // Cell Identity
//                    pDevice.mCell.setLAC(identityWcdma.getLac());
//                    pDevice.mCell.setMCC(identityWcdma.getMcc());
//                    pDevice.mCell.setMNC(identityWcdma.getMnc());
//                    pDevice.mCell.setCID(identityWcdma.getCid());
//                    pDevice.mCell.setPSC(identityWcdma.getPsc());
                    LogUtils.logGGQ("wcdma:" + wcdma.getDbm());
                    tvType.setText("CellInfoWcdma");
                } else if (info instanceof CellInfoNr) {
                    CellInfoNr cellInfoNr = (CellInfoNr) info;
                    CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();


                    CellIdentityNr cellIdentityNr = (CellIdentityNr) cellInfoNr.getCellIdentity();




                    int csiRsrp = cellSignalStrengthNr.getCsiRsrp();
                    int level = cellSignalStrengthNr.getAsuLevel();
                    int csiSinr = cellSignalStrengthNr.getCsiSinr();
                    int csiRsrq = cellSignalStrengthNr.getCsiRsrq();
                    int ssRsrp = cellSignalStrengthNr.getSsRsrp();
                    int ssSinr = cellSignalStrengthNr.getSsSinr();
                    int ddbm = cellSignalStrengthNr.getDbm();


                    String mcc = cellIdentityNr.getMccString();
                    String mnc = cellIdentityNr.getMncString();
                    long nci = cellIdentityNr.getNci();
                    int nrarfcn = cellIdentityNr.getNrarfcn();
                    int pci = cellIdentityNr.getPci();
                    int tac = cellIdentityNr.getTac();

                    loop++;
                    String text= "CellSignalStrengthNr---"+loop+"---csiRsrp:"+csiRsrp+"---"+
                            "level:"+level+"---"+
                            "csiSinr:"+csiSinr+"---"+
                            "csiRsrq:"+csiRsrq+"---"+
                            "ssRsrp:"+ssRsrp+"---"+
                            "ssSinr:"+ssSinr+"---"+
                            "ddbm:"+ddbm +"---"+
                            "mcc:"+mcc+"---"+
                            "mnc:"+mnc+"---"+
                            "nci:"+nci+"---"+
                            "nrarfcn:"+nrarfcn+"---"+
                            "pci:"+pci+"---"+
                            "tac:"+tac+"---";
                    tvContent.setText(text);
                    LogUtils.logGGQ("irsrp:" + csiRsrp);
                    tvType.setText("CellInfoNr");
                } else {
                    LogUtils.logGGQ("strengthAmplitude:" + "Unknown type of cell signal!"
                            + "\n ClassName: " + info.getClass().getSimpleName()
                            + "\n ToString: " + info.toString());

                    tvType.setText("no");
                }
            }

        } else {
            LogUtils.logGGQ("cellInfoList:" + cellInfoList.isEmpty());
            ToastUtils.showShort("cellInfoList empty");
        }

    }


    private int loop = 1;
    public class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);

            int strengthAmplitude = signalStrength.getGsmSignalStrength();
            LogUtils.logGGQ("onSignalStrengthsChanged");
//            tvType.setText("strengthAmplitude:"+strengthAmplitude);
            loop++;
            tvChange.setText("onSignalStrengthsChanged---:"+loop);
            List<CellSignalStrength> cellSignalStrengthList = signalStrength.getCellSignalStrengths();
            if (cellSignalStrengthList != null && !cellSignalStrengthList.isEmpty()) {
                for (CellSignalStrength strength : cellSignalStrengthList) {
                    if(strength instanceof  CellSignalStrengthNr){
                        ToastUtils.showShort("strength is Nr");
                    }
                }
            }
        }

        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            LogUtils.logGGQ("onCallStateChanged");

        }

        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfo) {
            super.onCellInfoChanged(cellInfo);
            ToastUtils.showShort("onCellInfoChanged");
            if (cellInfo != null && !cellInfo.isEmpty()) {
                for (CellInfo info : cellInfo) {
                    if (info instanceof CellInfoLte) {
                        CellSignalStrengthLte th = ((CellInfoLte) info).getCellSignalStrength();
                        int dbm = th.getDbm();
                        int rsrp = th.getRsrp();
                        LogUtils.logGGQ("dbm:" + dbm);
                        LogUtils.logGGQ("rsrp:" + rsrp);

                        tvType.setText("CellInfoLte");


                        if (info instanceof CellInfoGsm) {
                            final CellSignalStrengthGsm gsm = ((CellInfoGsm) info).getCellSignalStrength();
                            final CellIdentityGsm identityGsm = ((CellInfoGsm) info).getCellIdentity();
                            // Signal Strength
//                    pDevice.mCell.setDBM(gsm.getDbm()); // [dBm]
//                    // Cell Identity
//                    pDevice.mCell.setCID(identityGsm.getCid());
//                    pDevice.mCell.setMCC(identityGsm.getMcc());
//                    pDevice.mCell.setMNC(identityGsm.getMnc());
//                    pDevice.mCell.setLAC(identityGsm.getLac());

                            LogUtils.logGGQ("gsm:" + gsm.getDbm());
                            tvType.setText("CellInfoGsm");
                        } else if (info instanceof CellInfoCdma) {
                            final CellSignalStrengthCdma cdma = ((CellInfoCdma) info).getCellSignalStrength();
                            final CellIdentityCdma identityCdma = ((CellInfoCdma) info).getCellIdentity();
                            // Signal Strength
//                    pDevice.mCell.setDBM(cdma.getDbm());
//                    // Cell Identity
//                    pDevice.mCell.setCID(identityCdma.getBasestationId());
//                    pDevice.mCell.setMNC(identityCdma.getSystemId());
//                    pDevice.mCell.setLAC(identityCdma.getNetworkId());
//                    pDevice.mCell.setSID(identityCdma.getSystemId());
                            LogUtils.logGGQ("cdma:" + cdma.getDbm());
                            tvType.setText("CellInfoCdma");
                        } else if (info instanceof CellInfoLte) {
                            final CellSignalStrengthLte lte = ((CellInfoLte) info).getCellSignalStrength();
                            final CellIdentityLte identityLte = ((CellInfoLte) info).getCellIdentity();
                            // Signal Strength
//                    pDevice.mCell.setDBM(lte.getDbm());
//                    pDevice.mCell.setTimingAdvance(lte.getTimingAdvance());
//                    // Cell Identity
//                    pDevice.mCell.setMCC(identityLte.getMcc());
//                    pDevice.mCell.setMNC(identityLte.getMnc());
//                    pDevice.mCell.setCID(identityLte.getCi());
                            LogUtils.logGGQ("lte:" + lte.getDbm());
                            tvType.setText("CellInfoLte");
                        } else if (info instanceof CellInfoWcdma) {
                            final CellSignalStrengthWcdma wcdma = ((CellInfoWcdma) info).getCellSignalStrength();
                            final CellIdentityWcdma identityWcdma = ((CellInfoWcdma) info).getCellIdentity();
                            // Signal Strength
//                    pDevice.mCell.setDBM(wcdma.getDbm());
//                    // Cell Identity
//                    pDevice.mCell.setLAC(identityWcdma.getLac());
//                    pDevice.mCell.setMCC(identityWcdma.getMcc());
//                    pDevice.mCell.setMNC(identityWcdma.getMnc());
//                    pDevice.mCell.setCID(identityWcdma.getCid());
//                    pDevice.mCell.setPSC(identityWcdma.getPsc());
                            LogUtils.logGGQ("wcdma:" + wcdma.getDbm());
                            tvType.setText("CellInfoWcdma");
                        } else if (info instanceof CellInfoNr) {
                            CellInfoNr cellInfoNr = (CellInfoNr) info;
                            CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();

                            int csiRsrp = cellSignalStrengthNr.getCsiRsrp();
                            int level = cellSignalStrengthNr.getAsuLevel();
                            int csiSinr = cellSignalStrengthNr.getCsiSinr();
                            int csiRsrq = cellSignalStrengthNr.getCsiRsrq();
                            int ssRsrp = cellSignalStrengthNr.getSsRsrp();
                            int ssSinr = cellSignalStrengthNr.getSsSinr();
                            int ddbm = cellSignalStrengthNr.getDbm();

                            String text= "CellSignalStrengthNr---"+"csiRsrp:"+csiRsrp+"---"+
                                    "level:"+level+"---"+
                                    "csiSinr:"+csiSinr+"---"+
                                    "csiRsrq:"+csiRsrq+"---"+
                                    "ssRsrp:"+ssRsrp+"---"+
                                    "ssSinr:"+ssSinr+"---"+
                                    "ddbm:"+ddbm;
                            tvContent.setText(text);
                            LogUtils.logGGQ("irsrp:" + csiRsrp);
                            tvType.setText("CellInfoNr");
                        }
                    }else if (info instanceof CellInfoGsm) {
                        final CellSignalStrengthGsm gsm = ((CellInfoGsm) info).getCellSignalStrength();
                        final CellIdentityGsm identityGsm = ((CellInfoGsm) info).getCellIdentity();
                        // Signal Strength
//                    pDevice.mCell.setDBM(gsm.getDbm()); // [dBm]
//                    // Cell Identity
//                    pDevice.mCell.setCID(identityGsm.getCid());
//                    pDevice.mCell.setMCC(identityGsm.getMcc());
//                    pDevice.mCell.setMNC(identityGsm.getMnc());
//                    pDevice.mCell.setLAC(identityGsm.getLac());

                        LogUtils.logGGQ("gsm:" + gsm.getDbm());
                        tvType.setText("CellInfoGsm");
                    } else if (info instanceof CellInfoCdma) {
                        final CellSignalStrengthCdma cdma = ((CellInfoCdma) info).getCellSignalStrength();
                        final CellIdentityCdma identityCdma = ((CellInfoCdma) info).getCellIdentity();
                        // Signal Strength
//                    pDevice.mCell.setDBM(cdma.getDbm());
//                    // Cell Identity
//                    pDevice.mCell.setCID(identityCdma.getBasestationId());
//                    pDevice.mCell.setMNC(identityCdma.getSystemId());
//                    pDevice.mCell.setLAC(identityCdma.getNetworkId());
//                    pDevice.mCell.setSID(identityCdma.getSystemId());
                        LogUtils.logGGQ("cdma:" + cdma.getDbm());
                        tvType.setText("CellInfoCdma");
                    } else if (info instanceof CellInfoLte) {
                        final CellSignalStrengthLte lte = ((CellInfoLte) info).getCellSignalStrength();
                        final CellIdentityLte identityLte = ((CellInfoLte) info).getCellIdentity();
                        // Signal Strength
//                    pDevice.mCell.setDBM(lte.getDbm());
//                    pDevice.mCell.setTimingAdvance(lte.getTimingAdvance());
//                    // Cell Identity
//                    pDevice.mCell.setMCC(identityLte.getMcc());
//                    pDevice.mCell.setMNC(identityLte.getMnc());
//                    pDevice.mCell.setCID(identityLte.getCi());
                        LogUtils.logGGQ("lte:" + lte.getDbm());
                        tvType.setText("CellInfoLte");
                    } else if (info instanceof CellInfoWcdma) {
                        final CellSignalStrengthWcdma wcdma = ((CellInfoWcdma) info).getCellSignalStrength();
                        final CellIdentityWcdma identityWcdma = ((CellInfoWcdma) info).getCellIdentity();
                        // Signal Strength
//                    pDevice.mCell.setDBM(wcdma.getDbm());
//                    // Cell Identity
//                    pDevice.mCell.setLAC(identityWcdma.getLac());
//                    pDevice.mCell.setMCC(identityWcdma.getMcc());
//                    pDevice.mCell.setMNC(identityWcdma.getMnc());
//                    pDevice.mCell.setCID(identityWcdma.getCid());
//                    pDevice.mCell.setPSC(identityWcdma.getPsc());
                        LogUtils.logGGQ("wcdma:" + wcdma.getDbm());
                        tvType.setText("CellInfoWcdma");
                    } else if (info instanceof CellInfoNr) {
                        CellInfoNr cellInfoNr = (CellInfoNr) info;
                        CellSignalStrengthNr cellSignalStrengthNr = (CellSignalStrengthNr) cellInfoNr.getCellSignalStrength();


                        CellIdentityNr cellIdentityNr = (CellIdentityNr) cellInfoNr.getCellIdentity();




                        int csiRsrp = cellSignalStrengthNr.getCsiRsrp();
                        int level = cellSignalStrengthNr.getAsuLevel();
                        int csiSinr = cellSignalStrengthNr.getCsiSinr();
                        int csiRsrq = cellSignalStrengthNr.getCsiRsrq();
                        int ssRsrp = cellSignalStrengthNr.getSsRsrp();
                        int ssSinr = cellSignalStrengthNr.getSsSinr();
                        int ddbm = cellSignalStrengthNr.getDbm();


                        String mcc = cellIdentityNr.getMccString();
                        String mnc = cellIdentityNr.getMncString();
                        long nci = cellIdentityNr.getNci();
                        int nrarfcn = cellIdentityNr.getNrarfcn();
                        int pci = cellIdentityNr.getPci();
                        int tac = cellIdentityNr.getTac();

                        loop++;
                        String text= "CellSignalStrengthNr---"+loop+"---csiRsrp:"+csiRsrp+"---"+
                                "level:"+level+"---"+
                                "csiSinr:"+csiSinr+"---"+
                                "csiRsrq:"+csiRsrq+"---"+
                                "ssRsrp:"+ssRsrp+"---"+
                                "ssSinr:"+ssSinr+"---"+
                                "ddbm:"+ddbm +"---"+
                                "mcc:"+mcc+"---"+
                                "mnc:"+mnc+"---"+
                                "nci:"+nci+"---"+
                                "nrarfcn:"+nrarfcn+"---"+
                                "pci:"+pci+"---"+
                                "tac:"+tac+"---";
                        tvContent.setText(text);
                        LogUtils.logGGQ("irsrp:" + csiRsrp);
                        tvType.setText("CellInfoNr");
                    } else {
                        LogUtils.logGGQ("strengthAmplitude:" + "Unknown type of cell signal!"
                                + "\n ClassName: " + info.getClass().getSimpleName()
                                + "\n ToString: " + info.toString());

                        tvType.setText("no");
                    }
                }

            } else {
                LogUtils.logGGQ("cellInfoList:" + cellInfo.isEmpty());
                ToastUtils.showShort("cellInfoList empty");
            }
        }
    }

}
