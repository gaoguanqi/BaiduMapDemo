package com.maple.baidu.demo.telephone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.maple.baidu.R;
import com.maple.baidu.utils.LogUtils;

import java.util.List;

@SuppressLint({"NewApi", "MissingPermission"})
public class TelePhoneActivity extends AppCompatActivity {
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
                    } else {
                        LogUtils.logGGQ("strengthAmplitude:" + "Unknown type of cell signal!"
                                + "\n ClassName: " + info.getClass().getSimpleName()
                                + "\n ToString: " + info.toString());
                    }
                }
            }

        }else {
            LogUtils.logGGQ("cellInfoList:"+cellInfoList.isEmpty());
            ToastUtils.showShort("cellInfoList empty");
        }

    }


    public class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);

            int strengthAmplitude = signalStrength.getGsmSignalStrength();
            LogUtils.logGGQ("strengthAmplitude:" + strengthAmplitude);
        }
    }

}
