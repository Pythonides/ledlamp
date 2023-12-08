package com.home.net;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.common.net.NetResult;
import com.common.uitl.ListUtiles;
import com.common.uitl.LogUtil;
import com.common.uitl.StringUtils;
import com.common.uitl.Tool;
import com.home.activity.main.MainActivity_BLE;
import com.home.activity.main.MainActivity_DMX02;
import com.home.base.LedBleApplication;
import com.home.bean.MyColor;
import com.home.constant.CommonConstant;
import com.home.db.GroupDevice;
import com.home.db.GroupDeviceDao;
import com.home.fragment.service.ServicesFragment;
import com.home.utils.Utils;
import com.ledlamp.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes.dex */
public class NetConnectBle {
    public static final String FFE0 = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String FFE1 = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static final String FFE2 = "0000ffe2-0000-1000-8000-00805f9b34fb";
    private static final String TAG = "NetConnectBle";
    private static NetConnectBle netConnect;
    public static Set<String> setAddress;
    private NetExceptionInterface exceptionCallBack;
    private ArrayList<GroupDevice> groupDevices;
    private String groupName;
    Handler handler;
    Handler handlerrgb;
    private int k;
    boolean aa = true;
    int idx = 0;

    static /* synthetic */ int access$008(NetConnectBle netConnectBle) {
        int i = netConnectBle.k;
        netConnectBle.k = i + 1;
        return i;
    }

    public NetConnectBle() {
        setAddress = new HashSet();
    }

    public static NetConnectBle getInstance() {
        if (netConnect == null) {
            netConnect = new NetConnectBle();
        }
        return netConnect;
    }

    public static NetConnectBle getInstanceByGroup(String str) {
        if (netConnect == null) {
            netConnect = new NetConnectBle();
        }
        netConnect.setGroupName(str);
        return netConnect;
    }

    public void sendCharacteristic(byte[] bArr) {
        HashMap<String, BluetoothGatt> bleGattMap = LedBleApplication.getApp().getBleGattMap();
        if (bleGattMap == null || bleGattMap.isEmpty()) {
            return;
        }
        if (!ListUtiles.isEmpty(this.groupDevices) || StringUtils.isEmpty(this.groupName)) {
            for (Map.Entry<String, BluetoothGatt> entry : bleGattMap.entrySet()) {
                try {
                    BluetoothGatt value = entry.getValue();
                    BluetoothGattService service = value.getService(UUID.fromString(FFE0));
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(FFE1));
                    if (service != null && setAddress.contains(value.getDevice().getAddress()) && characteristic != null) {
                        characteristic.setValue(bArr);
                        value.writeCharacteristic(characteristic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getGroupName() {
        return this.groupName;
    }

    public ArrayList<GroupDevice> getDevicesByGroup() {
        return this.groupDevices;
    }

    public void setGroupName(String str) {
        ArrayList<BluetoothDevice> bleDevices;
        this.groupName = str;
        setAddress.clear();
        GroupDeviceDao groupDeviceDao = new GroupDeviceDao(LedBleApplication.getApp());
        int i = 0;
        if (StringUtils.isEmpty(str)) {
            if (ServicesFragment.sceneBeanFragment == null || ServicesFragment.sceneBeanFragment == null) {
                return;
            }
            if (ServicesFragment.sceneBeanFragment.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                bleDevices = LedBleApplication.getApp().getBleDevices();
            } else {
                bleDevices = LedBleApplication.getApp().getBleDevices();
            }
            if (bleDevices != null) {
                int size = bleDevices.size();
                while (i < size) {
                    if (bleDevices.get(i) != null) {
                        setAddress.add(bleDevices.get(i).getAddress());
                    }
                    i++;
                }
                return;
            }
            return;
        }
        if (StringUtils.isEmpty(str) || "null".equalsIgnoreCase(str)) {
            this.groupDevices = groupDeviceDao.getAllGroupDevices();
        } else {
            this.groupDevices = groupDeviceDao.getDevicesByGroup(str);
        }
        while (ListUtiles.getListSize(this.groupDevices) > 0 && i < this.groupDevices.size()) {
            setAddress.add(this.groupDevices.get(i).getAddress());
            i++;
        }
    }

    public NetConnectBle(NetExceptionInterface netExceptionInterface) {
        this.exceptionCallBack = netExceptionInterface;
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0170, code lost:
        if (r23.equalsIgnoreCase("LEDDMX-00-") != false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0176, code lost:
        if (r23.equalsIgnoreCase("LEDDMX-03-") == false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0178, code lost:
        sendData(new int[]{123, 255, 4, 5, 255, 255, 255, 255, 191});
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01bb, code lost:
        if (r23.equalsIgnoreCase("LEDDMX-01-") == false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01bd, code lost:
        sendData(new int[]{123, 4, 4, 5, 255, 255, 255, 255, 191});
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void turnOn(java.lang.String r23, boolean r24, boolean r25, boolean r26) {
        /*
            Method dump skipped, instructions count: 532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.home.net.NetConnectBle.turnOn(java.lang.String, boolean, boolean, boolean):void");
    }

    public void bledmxturnOn(String str) {
        try {
            if (str.equalsIgnoreCase(CommonConstant.LEDBLE_01)) {
                sendData(new int[]{126, 255, 4, 3, 255, 255, 255, 255, 239});
            } else {
                if (!str.equalsIgnoreCase(CommonConstant.LEDBLE_00) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                    if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                        if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                            if (str.equalsIgnoreCase("LEDDMX-02-")) {
                                sendData(new int[]{123, 255, 4, 1, 255, 255, 255, 255, 191});
                            }
                        }
                        sendData(new int[]{123, 255, 4, 3, 255, 255, 255, 255, 191});
                    }
                    if (str.equalsIgnoreCase("LEDDMX-01-")) {
                        sendData(new int[]{123, 4, 4, 3, 255, 255, 255, 255, 191});
                    }
                }
                sendData(new int[]{126, 255, 4, 3, 255, 255, 255, 255, 239});
            }
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x016b, code lost:
        sendData(new int[]{123, 255, 4, 4, 255, 255, 255, 255, 191});
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01a2, code lost:
        if (r22.equalsIgnoreCase("LEDDMX-01-") == false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01a4, code lost:
        sendData(new int[]{123, 4, 4, 4, 255, 255, 255, 255, 191});
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void turnOff(java.lang.String r22, boolean r23, boolean r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 507
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.home.net.NetConnectBle.turnOff(java.lang.String, boolean, boolean, boolean):void");
    }

    public void bledmxturnOff(String str) {
        int[] iArr = null;
        try {
            if (str.equalsIgnoreCase(CommonConstant.LEDBLE_01)) {
                iArr = new int[]{126, 255, 4, 2, 255, 255, 255, 255, 239};
                sendData(iArr);
            } else {
                if (!str.equalsIgnoreCase(CommonConstant.LEDBLE_00) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                    if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                        if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                            if (str.equalsIgnoreCase("LEDDMX-02-")) {
                                iArr = new int[]{123, 255, 4, 0, 255, 255, 255, 255, 191};
                                sendData(iArr);
                            }
                        }
                        iArr = new int[]{123, -1, 4, 2, -1, -1, -1, -1, -65};
                        sendData(iArr);
                    }
                    if (str.equalsIgnoreCase("LEDDMX-01-")) {
                        iArr = new int[]{123, 4, 4, 2, -1, -1, -1, -1, -65};
                        sendData(iArr);
                    }
                }
                iArr = new int[]{126, 255, 4, 2, 255, 255, 255, 255, 239};
                sendData(iArr);
            }
            sendData(iArr);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void sendsetting(int i, int i2, int i3, int i4) {
        try {
            Thread.sleep(50L);
            sendData(new int[]{125, 1, 3, i, i2, i3, i4, 255, 223});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void sendTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) {
        try {
            if (!str.contains(CommonConstant.LEDBLE_00) && !str.contains(CommonConstant.LEDBLE_01)) {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-02-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase(CommonConstant.LEDLIKE)) {
                            sendData(new int[]{112, 5, i, i2, i3, i4, i5, 255, 15});
                        }
                    }
                    sendData(new int[]{139, i, i2, i3, i4, i5, i6, i7, 191});
                }
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    sendData(new int[]{139, i, i2, i3, i4, i5, i6, i7, 191});
                }
            }
            sendData(new int[]{142, i, i2, i3, i4, i5, i6, i7, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void endTime(int i, int i2, int i3, int i4, String str) {
        try {
            if (str.contains(CommonConstant.LEDBLE)) {
                sendData(new int[]{126, 255, 17, i, i2, 255, i3, i4, 239});
            } else {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-02-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase(CommonConstant.LEDLIKE)) {
                            sendData(new int[]{112, 6, i2, i, i3, i4, 255, 255, 15});
                        }
                    }
                    sendData(new int[]{123, 255, 16, i, i2, 255, i3, i4, 191});
                }
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    sendData(new int[]{123, 4, 16, i, i2, 255, i3, i4, 191});
                }
            }
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setrgbTime(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            sendData(new int[]{125, 4, i, i2, i3, i4, i5, i6, 223});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void closeTime(String str) {
        try {
            if (str.contains(CommonConstant.LEDBLE)) {
                sendData(new int[]{142, 255, 255, 255, 255, 255, 255, 255, 239});
            } else {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-02-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase(CommonConstant.LEDSUN)) {
                            sendData(new int[]{122, 11, 255, 255, 255, 255, 255, 255, 175});
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDLIKE)) {
                            sendData(new int[]{112, 7, 255, 255, 255, 255, 255, 255, 15});
                        }
                    }
                    sendData(new int[]{139, 255, 255, 255, 255, 255, 255, 255, 191});
                }
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    sendData(new int[]{139, 255, 255, 255, 255, 255, 255, 255, 191});
                }
            }
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setRgb(int i, int i2, int i3, String str, boolean z, boolean z2, boolean z3, boolean z4) {
        int[] iArr;
        try {
            int i4 = 0;
            int i5 = 1;
            if (!str.contains(CommonConstant.LEDBLE) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase("LEDDMX-02-")) {
                            sendData(new int[]{123, 255, 7, i, i2, i3, 255, 255, 191});
                            return;
                        } else if (str.equalsIgnoreCase("LEDCAR-01-")) {
                            if (z || z2) {
                                iArr = new int[9];
                                iArr[0] = 123;
                                if (!z2) {
                                    if (z4) {
                                    }
                                    iArr[1] = i4;
                                    iArr[2] = 7;
                                    iArr[3] = i;
                                    iArr[4] = i2;
                                    iArr[5] = i3;
                                    iArr[6] = 255;
                                    iArr[7] = 255;
                                    iArr[8] = 191;
                                }
                                i4 = 1;
                                iArr[1] = i4;
                                iArr[2] = 7;
                                iArr[3] = i;
                                iArr[4] = i2;
                                iArr[5] = i3;
                                iArr[6] = 255;
                                iArr[7] = 255;
                                iArr[8] = 191;
                            } else {
                                iArr = new int[]{126, 255, 5, 3, i, i2, i3, 255, 239};
                            }
                            sendData(iArr);
                            return;
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                            sendData(new int[]{125, 2, 1, 255, i, i2, i3, 255, 223});
                            return;
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                            int[] iArr2 = new int[9];
                            iArr2[0] = 126;
                            iArr2[1] = 255;
                            iArr2[2] = 5;
                            iArr2[3] = 3;
                            iArr2[4] = i;
                            iArr2[5] = i2;
                            iArr2[6] = i3;
                            if (!z3) {
                                i5 = 255;
                            }
                            iArr2[7] = i5;
                            iArr2[8] = 239;
                            sendData(iArr2);
                            return;
                        } else {
                            return;
                        }
                    }
                    sendData(new int[]{123, 255, 7, i, i2, i3, 255, 255, 191});
                    return;
                }
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    sendData(new int[]{123, 4, 7, i, i2, i3, 255, 255, 191});
                    return;
                }
                return;
            }
            if (this.handlerrgb == null) {
                this.handlerrgb = new Handler();
            }
            if (this.aa) {
                sendData(new int[]{126, 255, 5, 3, i, i2, i3, 255, 239});
                this.aa = false;
                this.handlerrgb.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.1
                    @Override // java.lang.Runnable
                    public void run() {
                        NetConnectBle.this.aa = true;
                    }
                }, 50L);
                return;
            }
            Log.i(TAG, "setRgb: AAAAAAAAA");
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx00Dmx01Rgb(int i, int i2, int i3, String str, boolean z, boolean z2, int i4) {
        int[] iArr;
        int i5 = 2;
        int i6 = 0;
        try {
            if (z) {
                iArr = new int[9];
                iArr[0] = 123;
                iArr[1] = 255;
                iArr[2] = 7;
                iArr[3] = z2 ? 0 : i;
                iArr[4] = z2 ? 0 : i2;
                if (!z2) {
                    i6 = i3;
                }
                iArr[5] = i6;
                if (!z2) {
                    i5 = 1;
                }
                iArr[6] = i5;
                iArr[7] = i4;
                iArr[8] = 191;
            } else {
                iArr = new int[]{123, 255, 7, i, i2, i3, 0, 255, 191};
            }
            sendData(iArr);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setRgbMode(int i, String str, boolean z) {
        int[] iArr = null;
        try {
            if (!str.contains(CommonConstant.LEDBLE) && !str.equalsIgnoreCase("LEDCAR-00-") && (!str.equalsIgnoreCase("LEDCAR-01-") || z)) {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase("LEDCAR-01-") && z) {
                            iArr = new int[]{123, 255, 3, i, 255, 255, 255, 255, 191};
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                            iArr = new int[]{125, 2, 5, i, 255, 255, 255, 255, 223};
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                            iArr = new int[]{126, 255, 3, i, 3, 255, 255, 255, 239};
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSUN)) {
                            iArr = new int[]{122, 6, i, 255, 255, 255, 255, 255, 175};
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDLIKE)) {
                            iArr = new int[]{112, 4, i, 255, 255, 255, 255, 255, 15};
                        }
                        sendData(iArr);
                    }
                    iArr = new int[]{123, 255, 3, i, 255, 255, 255, 255, 191};
                    sendData(iArr);
                }
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    iArr = new int[]{123, 4, 3, i, 255, 255, 255, 255, 191};
                }
                sendData(iArr);
            }
            iArr = new int[]{126, 255, 3, i, 3, 255, 255, 255, 239};
            sendData(iArr);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setLikeMode(int i, boolean z) {
        try {
            sendData(z ? new int[]{112, 4, i, 1, 255, 255, 255, 255, 15} : new int[]{112, 4, i, 0, 255, 255, 255, 255, 15});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDirection(int i, String str, boolean z) {
        try {
            if ((!str.equalsIgnoreCase("LEDCAR-01-") || !z) && !str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                sendData(new int[]{123, 4, 13, i, 255, 255, 255, 255, 191});
            }
            sendData(new int[]{123, 255, 13, i, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setMusicSpeed(int i) {
        try {
            sendData(new int[]{123, 4, 12, i, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setMusicModel(int i, String str) {
        try {
            if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                    if (str.equalsIgnoreCase(CommonConstant.LEDLIKE)) {
                        sendData(new int[]{112, 4, i, 255, 255, 255, 255, 255, 15});
                    }
                }
                sendData(new int[]{123, 255, 11, i, 255, 255, 255, 255, 191});
            }
            if (str.equalsIgnoreCase("LEDDMX-01-")) {
                sendData(new int[]{123, 4, 11, i, 255, 255, 255, 255, 191});
            }
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setCirculation() {
        try {
            sendData(new int[]{123, 4, 15, 1, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSpeed(int i, String str, boolean z, boolean z2) {
        try {
            int i2 = 1;
            if (!str.contains(CommonConstant.LEDBLE) && !str.equalsIgnoreCase("LEDCAR-00-") && (!str.equalsIgnoreCase("LEDCAR-01-") || z2)) {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDCAR-01-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                            sendData(new int[]{125, 2, 4, i, 255, 255, 255, 255, 223});
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                            sendData(new int[]{126, 255, 2, i, 255, 255, 255, 255, 239});
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSUN)) {
                            sendData(new int[]{122, 3, i, 255, 255, 255, 255, 255, 175});
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDLIKE)) {
                            sendData(new int[]{112, 3, i, 255, 255, 255, 255, 255, 15});
                        }
                    }
                    int[] iArr = new int[9];
                    iArr[0] = 123;
                    iArr[1] = 255;
                    iArr[2] = 2;
                    iArr[3] = i;
                    if (!z) {
                        i2 = 0;
                    }
                    iArr[4] = i2;
                    iArr[5] = 255;
                    iArr[6] = 255;
                    iArr[7] = 255;
                    iArr[8] = 191;
                    sendData(iArr);
                }
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    int[] iArr2 = new int[9];
                    iArr2[0] = 123;
                    iArr2[1] = 4;
                    iArr2[2] = 2;
                    iArr2[3] = i;
                    iArr2[4] = 255;
                    if (!z) {
                        i2 = 0;
                    }
                    iArr2[5] = i2;
                    iArr2[6] = 255;
                    iArr2[7] = 255;
                    iArr2[8] = 191;
                    sendData(iArr2);
                }
            }
            Log.e(TAG, "setSpeed: " + i);
            int[] iArr3 = new int[9];
            iArr3[0] = 126;
            iArr3[1] = 255;
            iArr3[2] = 2;
            iArr3[3] = i;
            if (!z) {
                i2 = 0;
            }
            iArr3[4] = i2;
            iArr3[5] = 255;
            iArr3[6] = 255;
            iArr3[7] = 255;
            iArr3[8] = 239;
            sendData(iArr3);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDiy(final ArrayList<MyColor> arrayList, final int i, final String str) {
        if (str.contains(CommonConstant.LEDBLE) || str.equalsIgnoreCase("LEDCAR-00-")) {
            try {
                sendData(new int[]{126, 255, 14, i, 3, 255, 255, 255, 239});
                this.k = 0;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (NetConnectBle.this.k != arrayList.size()) {
                            int[] iArr = {126, 255, 16, i, ((MyColor) arrayList.get(NetConnectBle.this.k)).r, ((MyColor) arrayList.get(NetConnectBle.this.k)).g, ((MyColor) arrayList.get(NetConnectBle.this.k)).b, arrayList.size(), 239};
                            NetConnectBle.access$008(NetConnectBle.this);
                            try {
                                NetConnectBle.this.sendData(iArr);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            handler.postDelayed(this, 100L);
                            return;
                        }
                        handler.removeCallbacks(this);
                        try {
                            NetConnectBle.this.sendData(new int[]{126, 255, 15, i, 3, 255, 255, 255, 239});
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        NetConnectBle.this.k = 0;
                    }
                }, 10L);
                return;
            } catch (Exception e) {
                NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
                if (netExceptionInterface != null) {
                    netExceptionInterface.onException(e);
                    return;
                }
                return;
            }
        }
        try {
            this.k = 0;
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.3
                @Override // java.lang.Runnable
                public void run() {
                    if (NetConnectBle.this.k != arrayList.size()) {
                        int i2 = ((MyColor) arrayList.get(NetConnectBle.this.k)).r;
                        int i3 = ((MyColor) arrayList.get(NetConnectBle.this.k)).g;
                        int i4 = ((MyColor) arrayList.get(NetConnectBle.this.k)).b;
                        if (str.equalsIgnoreCase(CommonConstant.LEDBLE)) {
                            try {
                                NetConnectBle.this.sendData(new int[]{126, 255, 16, i, i2, i3, i4, arrayList.size(), 239});
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        } else if (str.equalsIgnoreCase("LEDDMX-01-") || str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                            if (str.equalsIgnoreCase("LEDDMX-01-")) {
                                try {
                                    NetConnectBle.this.sendData(new int[]{123, 4, 14, i, i2, i3, i4, arrayList.size(), 191});
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                        } else if (str.equalsIgnoreCase("LEDDMX-00-") || str.equalsIgnoreCase("LEDDMX-02-") || str.equalsIgnoreCase("LEDDMX-03-")) {
                            try {
                                NetConnectBle.this.sendData(new int[]{123, 255, 14, i, i2, i3, i4, arrayList.size(), 191});
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                            try {
                                NetConnectBle.this.sendData(new int[]{125, 2, 3, i, i2, i3, i4, arrayList.size(), 223});
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                            try {
                                NetConnectBle.this.sendData(new int[]{126, 255, 6, i, i2, i3, i4, arrayList.size(), 239});
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSUN)) {
                            try {
                                NetConnectBle.this.sendData(new int[]{126, 255, 6, i, i2, i3, i4, arrayList.size(), 239});
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                        }
                        NetConnectBle.access$008(NetConnectBle.this);
                        handler2.postDelayed(this, 100L);
                        return;
                    }
                    handler2.removeCallbacks(this);
                    NetConnectBle.this.k = 0;
                }
            }, 10L);
        } catch (Exception e2) {
            NetExceptionInterface netExceptionInterface2 = this.exceptionCallBack;
            if (netExceptionInterface2 != null) {
                netExceptionInterface2.onException(e2);
            }
        }
    }

    public void setMusicBrightness(int i, String str) {
        int i2 = i;
        if (i2 > 100) {
            i2 = 100;
        } else if (i2 <= 0) {
            i2 = 0;
        }
        try {
            if (str.equalsIgnoreCase(CommonConstant.LEDBLE_01)) {
                sendData(new int[]{126, 255, 1, i2, 1, 255, 255, 255, 239});
                return;
            }
            if (!str.equalsIgnoreCase(CommonConstant.LEDBLE_00) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                if (str.equalsIgnoreCase("LEDCAR-01-")) {
                    sendData(new int[]{123, 255, 1, (i2 * 32) / 100, i2, 1, 255, 255, 191});
                    return;
                }
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-02-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase(CommonConstant.LEDSUN)) {
                            sendData(new int[]{122, 9, i2, 255, 255, 255, 255, 0, 175});
                            return;
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDLIKE)) {
                            sendData(new int[]{112, 2, i2, 255, 255, 255, 255, 255, 15});
                            return;
                        } else {
                            return;
                        }
                    }
                    sendData(new int[]{123, 255, 1, (i2 * 32) / 100, i2, 1, 255, 255, 191});
                    return;
                }
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    sendData(new int[]{123, 4, 1, (i2 * 32) / 100, i2, 1, 255, 255, 191});
                    return;
                }
                return;
            }
            sendData(new int[]{126, 255, 1, i2, 1, 255, 255, 255, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setBrightness(int i, String str, boolean z, boolean z2, boolean z3) {
        int[] iArr;
        int i2 = i;
        int i3 = 0;
        if (i2 > 100) {
            i2 = 100;
        } else if (i2 <= 0) {
            i2 = 0;
        }
        try {
            int i4 = 1;
            if (!str.contains(CommonConstant.LEDBLE) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-02-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase("LEDCAR-01-")) {
                            if (z2 || z3) {
                                iArr = new int[9];
                                iArr[0] = 123;
                                iArr[1] = 255;
                                iArr[2] = 1;
                                iArr[3] = (i2 * 32) / 100;
                                iArr[4] = i2;
                                if (z) {
                                    i3 = 1;
                                } else if (z2) {
                                    i3 = 2;
                                }
                                iArr[5] = i3;
                                iArr[6] = 255;
                                iArr[7] = 255;
                                iArr[8] = 191;
                            } else {
                                iArr = new int[]{126, 255, 1, i2, 0, 255, 255, 255, 239};
                            }
                            sendData(iArr);
                            return;
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                            sendData(new int[]{125, 2, 2, i2, 255, 255, 255, 255, 223});
                            return;
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                            sendData(new int[]{126, 255, 1, i2, 255, 255, 255, 255, 239});
                            return;
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSUN)) {
                            sendData(new int[]{122, 2, i2, 255, 255, 255, 255, 255, 175});
                            return;
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDLIKE)) {
                            sendData(new int[]{112, 2, i2, 255, 255, 255, 255, 255, 15});
                            return;
                        } else {
                            return;
                        }
                    }
                    if (this.handler == null) {
                        this.handler = new Handler();
                    }
                    if (this.aa) {
                        int[] iArr2 = new int[9];
                        iArr2[0] = 123;
                        iArr2[1] = 255;
                        iArr2[2] = 1;
                        iArr2[3] = (i2 * 32) / 100;
                        iArr2[4] = i2;
                        if (!z) {
                            i4 = 0;
                        }
                        iArr2[5] = i4;
                        iArr2[6] = 255;
                        iArr2[7] = 255;
                        iArr2[8] = 191;
                        sendData(iArr2);
                        this.aa = false;
                        this.handler.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.6
                            @Override // java.lang.Runnable
                            public void run() {
                                NetConnectBle.this.aa = true;
                            }
                        }, 30L);
                        return;
                    }
                    Log.i(TAG, "setRgb: AAAAAAAAA");
                    return;
                }
                if (this.handler == null) {
                    this.handler = new Handler();
                }
                if (this.aa) {
                    if (str.equalsIgnoreCase("LEDDMX-01-")) {
                        int[] iArr3 = new int[9];
                        iArr3[0] = 123;
                        iArr3[1] = 4;
                        iArr3[2] = 1;
                        iArr3[3] = (i2 * 32) / 100;
                        iArr3[4] = i2;
                        if (!z) {
                            i4 = 0;
                        }
                        iArr3[5] = i4;
                        iArr3[6] = 255;
                        iArr3[7] = 255;
                        iArr3[8] = 191;
                        sendData(iArr3);
                    }
                    Log.e(TAG, "setBrightness: " + i2);
                    this.aa = false;
                    this.handler.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.5
                        @Override // java.lang.Runnable
                        public void run() {
                            NetConnectBle.this.aa = true;
                        }
                    }, 30L);
                    return;
                }
                Log.i(TAG, "setRgb: AAAAAAAAA");
                return;
            }
            if (this.handler == null) {
                this.handler = new Handler();
            }
            if (this.aa) {
                Log.e(TAG, "setBrightness: " + i2);
                int[] iArr4 = new int[9];
                iArr4[0] = 126;
                iArr4[1] = 255;
                iArr4[2] = 1;
                iArr4[3] = i2;
                if (!z) {
                    i4 = 0;
                }
                iArr4[4] = i4;
                iArr4[5] = 255;
                iArr4[6] = 255;
                iArr4[7] = 255;
                iArr4[8] = 239;
                sendData(iArr4);
                this.aa = false;
                this.handler.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.4
                    @Override // java.lang.Runnable
                    public void run() {
                        NetConnectBle.this.aa = true;
                    }
                }, 30L);
                return;
            }
            Log.i(TAG, "setRgb: AAAAAAAAA");
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setLikeBrightness(int i, String str, boolean z, boolean z2) {
        int i2 = i;
        if (i2 > 100) {
            i2 = 100;
        } else if (i2 <= 0) {
            i2 = 0;
        }
        try {
            sendData(z ? new int[]{112, 2, i2, 1, 255, 255, 255, 255, 15} : z2 ? new int[]{112, 2, i2, 2, 255, 255, 255, 255, 15} : new int[]{112, 2, i2, 0, 255, 255, 255, 255, 15});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setColorWarmModel(int i) {
        try {
            sendData(new int[]{126, 255, 3, i, 2, 255, 255, 255, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDimModel(int i) {
        try {
            sendData(new int[]{126, 255, 3, i, 1, 255, 255, 255, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDim(int i, String str) {
        try {
            if (!str.contains(CommonConstant.LEDBLE) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                            sendData(new int[]{125, 2, 7, i, 255, 255, 255, 255, 223});
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                            sendData(new int[]{126, 255, 5, 1, i, 255, 255, 255, 239});
                        }
                    }
                    sendData(new int[]{123, 255, 9, (i * 32) / 100, i, 255, 255, 255, 191});
                }
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    sendData(new int[]{123, 4, 9, (i * 32) / 100, i, 255, 255, 255, 191});
                }
            }
            sendData(new int[]{126, 255, 5, 1, i, 255, 255, 255, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setColorWarm(int i, int i2, String str) {
        try {
            if (str.equalsIgnoreCase(CommonConstant.LEDBLE)) {
                sendData(new int[]{126, 255, 5, 2, i, i2, 255, 255, 239});
            } else if (str.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                sendData(new int[]{126, 255, 5, 2, i, i2, 255, 255, 239});
            } else if (str.equalsIgnoreCase(CommonConstant.LEDSUN)) {
                sendData(new int[]{122, 5, i, 255, 255, 255, 255, 255, 175});
            }
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDynamicModel(int i) {
        try {
            sendData(new int[]{126, 255, 3, i, 4, 255, 255, 255, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSensitivity(int i, boolean z, boolean z2, String str) {
        int[] iArr = null;
        try {
            int i2 = 1;
            if (str.equalsIgnoreCase(CommonConstant.LEDBLE_01)) {
                iArr = new int[]{126, 255, 7, i, 255, 255, 255, 255, 239};
            } else if (str.equalsIgnoreCase("LEDDMX-01-")) {
                iArr = new int[9];
                iArr[0] = 123;
                iArr[1] = 4;
                iArr[2] = 12;
                iArr[3] = i;
                if (!z) {
                    i2 = 0;
                }
                iArr[4] = i2;
                iArr[5] = 255;
                iArr[6] = 255;
                iArr[7] = 255;
                iArr[8] = 191;
            } else {
                if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-02-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                    if (!str.equalsIgnoreCase(CommonConstant.LEDBLE_00) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                        if (str.equalsIgnoreCase("LEDCAR-01-")) {
                            if (z2) {
                                iArr = new int[9];
                                iArr[0] = 123;
                                iArr[1] = 255;
                                iArr[2] = 12;
                                iArr[3] = i;
                                if (!z) {
                                    i2 = 0;
                                }
                                iArr[4] = i2;
                                iArr[5] = 255;
                                iArr[6] = 255;
                                iArr[7] = 255;
                                iArr[8] = 191;
                            } else {
                                iArr = new int[]{126, 255, 7, i, 255, 255, 255, 255, 239};
                            }
                        }
                    }
                    iArr = new int[]{126, 255, 7, i, 255, 255, 255, 255, 239};
                }
                iArr = new int[9];
                iArr[0] = 123;
                iArr[1] = 255;
                iArr[2] = 12;
                iArr[3] = i;
                if (!z) {
                    i2 = 0;
                }
                iArr[4] = i2;
                iArr[5] = 255;
                iArr[6] = 255;
                iArr[7] = 255;
                iArr[8] = 191;
            }
            sendData(iArr);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setLikeSensitivity(int i) {
        try {
            sendData(new int[]{112, 8, i, 255, 255, 255, 255, 255, 15});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDynamicDiy(final ArrayList<MyColor> arrayList, final int i) {
        try {
            sendData(new int[]{126, 255, 10, i, 3, 255, 255, 255, 239});
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.7
                @Override // java.lang.Runnable
                public void run() {
                    if (NetConnectBle.this.idx >= arrayList.size()) {
                        NetConnectBle.this.idx = 0;
                        try {
                            NetConnectBle.this.sendData(new int[]{126, 255, 12, i, 3, 255, 255, 255, 239});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        handler.removeCallbacks(this);
                        return;
                    }
                    int i2 = ((MyColor) arrayList.get(NetConnectBle.this.idx)).r;
                    int i3 = ((MyColor) arrayList.get(NetConnectBle.this.idx)).g;
                    int i4 = ((MyColor) arrayList.get(NetConnectBle.this.idx)).b;
                    LogUtil.i(LedBleApplication.tag, "r:" + i2 + " g:" + i3 + " b:" + i4);
                    try {
                        NetConnectBle.this.sendData(new int[]{126, 255, 11, i, i2, i3, i4, arrayList.size(), 239});
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    NetConnectBle.this.idx++;
                    handler.postDelayed(this, 300L);
                }
            }, 300L);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void sendtimestage(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            Thread.sleep(200L);
            sendData(new int[]{126, i, 8, i2, i3, i4, i5, i6, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void sendSun(int i, int i2, int i3, int i4, int i5) {
        try {
            sendData(new int[]{122, 10, i, i2, i3, i4, i5, 255, 175});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void timeSun(int i, int i2, int i3, int i4) {
        try {
            sendData(new int[]{122, 12, i, i2, i3, i4, 255, 255, 175});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSPIModel(int i, String str) {
        int[] iArr = null;
        try {
            if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    iArr = new int[]{123, 4, 3, i, 255, 255, 255, 255, 191};
                } else if (str.equalsIgnoreCase("LEDCAR-01-")) {
                    iArr = new int[]{123, 255, 3, i, 255, 255, 255, 255, 191};
                }
                sendData(iArr);
            }
            iArr = new int[]{123, 255, 3, i, 255, 255, 255, 255, 191};
            sendData(iArr);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setConfigSPI(int i, byte b, byte b2, int i2, String str) {
        int[] iArr;
        try {
            if (str.equalsIgnoreCase("LEDCAR-01-")) {
                iArr = new int[]{123, 255, 5, 4, b, b2, i2, 255, 191};
            } else {
                if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                    iArr = new int[]{123, 4, 5, i, b, b2, i2, 255, 191};
                }
                iArr = new int[]{123, 255, 5, i, b, b2, i2, 255, 191};
            }
            sendData(iArr);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setConfigCAR01(int i, int i2, int i3, int i4, String str) {
        try {
            sendData(str.equalsIgnoreCase("LEDCAR-01-") ? new int[]{123, i, 5, 5, i2, i3, i4, 255, 191} : null);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void pauseSPI(int i, String str) {
        try {
            sendData(str.equalsIgnoreCase("LEDDMX-01-") ? new int[]{123, 4, 6, i, 255, 255, 255, 255, 191} : new int[]{123, 255, 6, i, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void SetRgbSort(int i) {
        try {
            sendData(new int[]{126, 255, 8, i, 255, 255, 255, 255, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void SetPairCode(int i) {
        try {
            sendData(new int[]{126, 255, 9, i, 255, 255, 255, 255, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void SetCHN(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            sendData(new int[]{126, i, 11, i2, i3, i4, i5, i6, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setTimerFirData(int i) {
        try {
            Calendar calendar = Calendar.getInstance();
            sendData(new int[]{125, 3, 1, i, calendar.get(11), calendar.get(12), calendar.get(13), 255, 223});
        } catch (IOException e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setTime(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            sendData(new int[]{125, 3, i, i2, i3, i4, i5, i6, 223});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTimerSecData(int[] iArr) {
        try {
            Thread.sleep(200L);
            sendData(new int[]{124, iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6], iArr[7]});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void configCode(int i, int i2, int i3) {
        try {
            sendData(new int[]{123, 4, 10, i, i2, i3, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setAuxiliary(int i, String str) {
        try {
            if (!str.contains(CommonConstant.LEDBLE) && !str.equalsIgnoreCase("LEDCAR-00-") && !str.equalsIgnoreCase("LEDCAR-01-")) {
                if (str.contains(CommonConstant.LEDDMX)) {
                    sendData(new int[]{123, 255, 17, i, 255, 255, 255, 255, 191});
                }
            }
            sendData(new int[]{126, 255, 18, i, 255, 255, 255, 255, 239});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setModeCycle(int i, int i2, int i3, int i4, int i5, int i6, String str, boolean z) {
        try {
            if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                if (!str.equalsIgnoreCase(CommonConstant.LEDBLE_00) && !str.equalsIgnoreCase(CommonConstant.LEDSTAGE) && !str.equalsIgnoreCase(CommonConstant.LEDLIGHT) && !str.equalsIgnoreCase("LEDCAR-00-") && (!str.equalsIgnoreCase("LEDCAR-01-") || z)) {
                    if (str.equalsIgnoreCase("LEDCAR-01-") && z) {
                        sendData(new int[]{123, i, 18, i2, i3, i4, i5, i6, 191});
                    }
                }
                sendData(new int[]{126, i, 15, 1, i2, i3, i4, i5, 239});
            }
            sendData(new int[]{123, i, 18, i2, i3, i4, i5, i6, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setCustomCycle(String str, boolean z) {
        try {
            if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                if (!str.equalsIgnoreCase(CommonConstant.LEDBLE_00) && !str.equalsIgnoreCase(CommonConstant.LEDBLE_01) && !str.equalsIgnoreCase(CommonConstant.LEDSTAGE) && !str.equalsIgnoreCase(CommonConstant.LEDLIGHT) && !str.equalsIgnoreCase("LEDCAR-00-") && (!str.equalsIgnoreCase("LEDCAR-01-") || z)) {
                    if (str.equalsIgnoreCase("LEDCAR-01-") && z) {
                        sendData(new int[]{123, 255, 15, 1, 255, 255, 255, 255, 191});
                    }
                }
                sendData(new int[]{126, 255, 15, 0, 255, 255, 255, 255, 239});
            }
            sendData(new int[]{123, 255, 15, 1, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setChangeColor(final boolean z, final boolean z2, final boolean z3, final ArrayList<MyColor> arrayList, final String str) {
        try {
            this.k = 0;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.8
                @Override // java.lang.Runnable
                public void run() {
                    int[] iArr;
                    if (NetConnectBle.this.k != arrayList.size()) {
                        int i = ((MyColor) arrayList.get(NetConnectBle.this.k)).r;
                        int i2 = ((MyColor) arrayList.get(NetConnectBle.this.k)).g;
                        int i3 = ((MyColor) arrayList.get(NetConnectBle.this.k)).b;
                        if (str.equalsIgnoreCase("LEDDMX-00-") || str.equalsIgnoreCase("LEDDMX-02-") || str.equalsIgnoreCase("LEDDMX-03-")) {
                            iArr = new int[9];
                            iArr[0] = 123;
                            iArr[1] = NetConnectBle.this.k + 1;
                            iArr[2] = 14;
                            iArr[3] = z ? 254 : 253;
                            iArr[4] = i;
                            iArr[5] = i2;
                            iArr[6] = i3;
                            iArr[7] = arrayList.size();
                            iArr[8] = 191;
                        } else if (str.equalsIgnoreCase(CommonConstant.LEDBLE_00) || str.equalsIgnoreCase(CommonConstant.LEDBLE_01) || str.equalsIgnoreCase(CommonConstant.LEDSTAGE) || str.equalsIgnoreCase(CommonConstant.LEDLIGHT) || str.equalsIgnoreCase("LEDCAR-00-") || (str.equalsIgnoreCase("LEDCAR-01-") && !z3)) {
                            iArr = new int[9];
                            iArr[0] = 126;
                            iArr[1] = NetConnectBle.this.k + 1;
                            iArr[2] = 13;
                            iArr[3] = z ? 254 : z2 ? 252 : 253;
                            iArr[4] = i;
                            iArr[5] = i2;
                            iArr[6] = i3;
                            iArr[7] = arrayList.size();
                            iArr[8] = 239;
                        } else if (str.equalsIgnoreCase("LEDCAR-01-") && z3) {
                            iArr = new int[9];
                            iArr[0] = 123;
                            iArr[1] = NetConnectBle.this.k + 1;
                            iArr[2] = 14;
                            iArr[3] = z ? 254 : z2 ? 252 : 253;
                            iArr[4] = i;
                            iArr[5] = i2;
                            iArr[6] = i3;
                            iArr[7] = arrayList.size();
                            iArr[8] = 191;
                        } else {
                            iArr = null;
                        }
                        try {
                            NetConnectBle.this.sendData(iArr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        NetConnectBle.access$008(NetConnectBle.this);
                        handler.postDelayed(this, 100L);
                        return;
                    }
                    handler.removeCallbacks(this);
                    NetConnectBle.this.k = 0;
                }
            }, 100L);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setMode(boolean z, boolean z2, boolean z3, int i, String str) {
        int[] iArr = null;
        try {
            int i2 = 1;
            int i3 = 0;
            if (!str.equalsIgnoreCase(CommonConstant.LEDBLE_00) && !str.equalsIgnoreCase(CommonConstant.LEDBLE_01) && !str.equalsIgnoreCase(CommonConstant.LEDSTAGE) && !str.equalsIgnoreCase(CommonConstant.LEDLIGHT) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                if (str.equalsIgnoreCase("LEDCAR-01-")) {
                    if (z3) {
                        iArr = new int[9];
                        iArr[0] = 123;
                        iArr[1] = 255;
                        iArr[2] = z2 ? 11 : 19;
                        iArr[3] = i;
                        if (z) {
                            i2 = 0;
                        } else if (!z2) {
                            i2 = 255;
                        }
                        iArr[4] = i2;
                        iArr[5] = 255;
                        iArr[6] = 255;
                        iArr[7] = 255;
                        iArr[8] = 191;
                    } else {
                        iArr = new int[9];
                        iArr[0] = 126;
                        if (!z) {
                            i3 = 1;
                        }
                        iArr[1] = i3;
                        iArr[2] = 14;
                        iArr[3] = i;
                        iArr[4] = 255;
                        iArr[5] = 255;
                        iArr[6] = 255;
                        iArr[7] = 255;
                        iArr[8] = 239;
                    }
                } else if (str.equalsIgnoreCase("LEDDMX-00-") || str.equalsIgnoreCase("LEDDMX-02-") || str.equalsIgnoreCase("LEDDMX-03-")) {
                    iArr = new int[]{123, 255, 19, i, 255, 255, 255, 255, 191};
                }
                sendData(iArr);
            }
            iArr = new int[9];
            iArr[0] = 126;
            if (z2) {
                i3 = 2;
            } else if (!z) {
                i3 = 1;
            }
            iArr[1] = i3;
            iArr[2] = 14;
            iArr[3] = i;
            iArr[4] = 255;
            iArr[5] = 255;
            iArr[6] = 255;
            iArr[7] = 255;
            iArr[8] = 239;
            sendData(iArr);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setVoiceCtlAndMusicMode(boolean z, boolean z2, boolean z3, int i, String str) {
        int[] iArr = null;
        try {
            int i2 = 11;
            if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-02-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    iArr = new int[9];
                    iArr[0] = 123;
                    iArr[1] = 4;
                    iArr[2] = 11;
                    iArr[3] = i;
                    iArr[4] = z2 ? 1 : 0;
                    iArr[5] = 255;
                    iArr[6] = 255;
                    iArr[7] = 255;
                    iArr[8] = 191;
                } else if (str.equalsIgnoreCase("LEDDMX-02-")) {
                    iArr = new int[9];
                    iArr[0] = 123;
                    iArr[1] = 255;
                    iArr[2] = 11;
                    iArr[3] = i;
                    iArr[4] = z2 ? 1 : 0;
                    iArr[5] = 255;
                    iArr[6] = 255;
                    iArr[7] = 255;
                    iArr[8] = 191;
                } else {
                    if (!str.equalsIgnoreCase(CommonConstant.LEDBLE_00) && !str.equalsIgnoreCase(CommonConstant.LEDBLE_01) && !str.equalsIgnoreCase("LEDCAR-00-")) {
                        if (str.equalsIgnoreCase("LEDCAR-01-")) {
                            iArr = new int[9];
                            iArr[0] = 123;
                            iArr[1] = 255;
                            if (!z3) {
                                i2 = 1;
                            }
                            iArr[2] = i2;
                            iArr[3] = i;
                            iArr[4] = z2 ? 1 : 0;
                            iArr[5] = 255;
                            iArr[6] = 255;
                            iArr[7] = 255;
                            iArr[8] = 191;
                        }
                    }
                    iArr = new int[9];
                    iArr[0] = 126;
                    if (z2) {
                        r16 = 2;
                    } else if (!z) {
                        r16 = 1;
                    }
                    iArr[1] = r16;
                    iArr[2] = 14;
                    iArr[3] = i;
                    iArr[4] = 255;
                    iArr[5] = 255;
                    iArr[6] = 255;
                    iArr[7] = 255;
                    iArr[8] = 239;
                }
                sendData(iArr);
            }
            iArr = new int[9];
            iArr[0] = 123;
            iArr[1] = 255;
            if (!z2 && !z) {
                i2 = 19;
            }
            iArr[2] = i2;
            iArr[3] = i;
            iArr[4] = z2 ? 1 : 0;
            iArr[5] = 255;
            iArr[6] = 255;
            iArr[7] = 255;
            iArr[8] = 191;
            sendData(iArr);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSmartBrightness(int i, int i2, String str) {
        try {
            if (str.equalsIgnoreCase(CommonConstant.LEDSTAGE)) {
                Log.e(TAG, "setSmartBrightness: " + i + "===" + i2);
                sendData(new int[]{126, 255, 7, i, i2, 255, 255, 255, 239});
            } else {
                if (!str.equalsIgnoreCase("LEDDMX-01-") && !str.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    if (!str.equalsIgnoreCase("LEDDMX-00-") && !str.equalsIgnoreCase("LEDDMX-03-")) {
                        if (str.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                            sendData(new int[]{125, 1, 2, i, i2, 255, 255, 255, 223});
                        }
                    }
                    sendData(new int[]{123, 255, 8, i2, (i * 32) / 100, i, 255, 255, 191});
                }
                int i3 = (i * 32) / 100;
                if (str.equalsIgnoreCase("LEDDMX-01-")) {
                    sendData(new int[]{123, 4, 8, i2, i3, i, 255, 255, 191});
                }
            }
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSmartTimeSet(int i, int i2, int i3, int i4) {
        try {
            sendData(new int[]{125, 1, 3, i, i2, i3, i4, 255, 223});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSmartFanSet(int i) {
        try {
            sendData(new int[]{125, 1, 5, i, 255, 255, 255, 255, 223});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSmartCheck(int i) {
        try {
            sendSmartDataToFFE1WithCallback(new int[]{125, 1, 5, i, 255, 255, 255, 255, 223});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSmartBubbleCheck(int i) {
        try {
            sendData(new int[]{125, 1, 6, i, 255, 255, 255, 255, 223});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void sendSmartDataToFFE1WithCallback(int[] iArr) {
        BluetoothGatt value;
        List<BluetoothGattDescriptor> descriptors;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i : iArr) {
                byteArrayOutputStream.write(Tool.int2bytearray(i));
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            HashMap<String, BluetoothGatt> bleGattMap = LedBleApplication.getApp().getBleGattMap();
            if (bleGattMap == null || bleGattMap.isEmpty()) {
                return;
            }
            if (!ListUtiles.isEmpty(this.groupDevices) || StringUtils.isEmpty(this.groupName)) {
                for (Map.Entry<String, BluetoothGatt> entry : bleGattMap.entrySet()) {
                    try {
                        value = entry.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (value != null) {
                        BluetoothGattCharacteristic characteristic = value.getService(UUID.fromString(FFE0)).getCharacteristic(UUID.fromString(FFE1));
                        if (characteristic != null) {
                            characteristic.setValue(byteArray);
                            value.writeCharacteristic(characteristic);
                            if (!value.setCharacteristicNotification(characteristic, true) || (descriptors = characteristic.getDescriptors()) == null || descriptors.size() <= 0) {
                                return;
                            }
                            for (BluetoothGattDescriptor bluetoothGattDescriptor : descriptors) {
                                bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                value.writeDescriptor(bluetoothGattDescriptor);
                            }
                            return;
                        }
                        return;
                    }
                    continue;
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void setSunVcMode(int i) {
        try {
            sendData(new int[]{122, 7, i + 128, 255, 255, 255, 255, 255, 175});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setSunSensitivity(int i) {
        try {
            sendData(new int[]{122, 8, i, 255, 255, 255, 255, 255, 175});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setPassword(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            sendPasswordDataWithCallback(new int[]{26, 1, i, i2, i3, i4, i5, i6, 175});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setPasswordFeedback() {
        try {
            sendPasswordDataWithCallback(new int[]{26, 5, 255, 255, 255, 255, 255, 255, 175});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02Speed(int i) {
        try {
            sendData(new int[]{123, 255, 2, i, 0, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02Bright(int i) {
        try {
            sendData(new int[]{123, 255, 1, (i * 32) / 100, i, 0, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02AnimationMode(int i) {
        try {
            sendData(new int[]{123, 255, 23, i, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02AnimationPlay(int i) {
        try {
            sendData(new int[]{123, 255, 6, i, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02AnimationDirection(int i) {
        try {
            sendData(new int[]{123, 255, 13, i, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02AnimationCycle(int i) {
        try {
            sendData(new int[]{123, 255, 15, 1, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02ChangeColor(final ArrayList<MyColor> arrayList) {
        try {
            this.k = 0;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() { // from class: com.home.net.NetConnectBle.9
                @Override // java.lang.Runnable
                public void run() {
                    if (NetConnectBle.this.k == arrayList.size()) {
                        handler.removeCallbacks(this);
                        NetConnectBle.this.k = 0;
                        return;
                    }
                    try {
                        NetConnectBle.this.sendData(new int[]{123, NetConnectBle.this.k + 1, 14, 253, ((MyColor) arrayList.get(NetConnectBle.this.k)).r, ((MyColor) arrayList.get(NetConnectBle.this.k)).g, ((MyColor) arrayList.get(NetConnectBle.this.k)).b, arrayList.size(), 191});
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    NetConnectBle.access$008(NetConnectBle.this);
                    handler.postDelayed(this, 100L);
                }
            }, 100L);
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void sendDmx02DataToFFE1WithCallback(int[] iArr) {
        List<BluetoothGattDescriptor> descriptors;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i : iArr) {
                byteArrayOutputStream.write(Tool.int2bytearray(i));
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            HashMap<String, BluetoothGatt> bleGattMap = LedBleApplication.getApp().getBleGattMap();
            if (bleGattMap == null || bleGattMap.isEmpty()) {
                return;
            }
            if (!ListUtiles.isEmpty(this.groupDevices) || StringUtils.isEmpty(this.groupName)) {
                for (Map.Entry<String, BluetoothGatt> entry : bleGattMap.entrySet()) {
                    try {
                        BluetoothGatt value = entry.getValue();
                        BluetoothGattCharacteristic characteristic = value.getService(UUID.fromString(FFE0)).getCharacteristic(UUID.fromString(FFE1));
                        if (characteristic != null) {
                            characteristic.setValue(byteArray);
                            value.writeCharacteristic(characteristic);
                            if (value.setCharacteristicNotification(characteristic, true) && (descriptors = characteristic.getDescriptors()) != null && descriptors.size() > 0) {
                                for (BluetoothGattDescriptor bluetoothGattDescriptor : descriptors) {
                                    bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    value.writeDescriptor(bluetoothGattDescriptor);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void sendDmx02DataToFFE2WithCallback(int[] iArr) {
        List<BluetoothGattDescriptor> descriptors;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i : iArr) {
                byteArrayOutputStream.write(Tool.int2bytearray(i));
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            HashMap<String, BluetoothGatt> bleGattMap = LedBleApplication.getApp().getBleGattMap();
            if (bleGattMap == null || bleGattMap.isEmpty()) {
                return;
            }
            if (!ListUtiles.isEmpty(this.groupDevices) || StringUtils.isEmpty(this.groupName)) {
                for (Map.Entry<String, BluetoothGatt> entry : bleGattMap.entrySet()) {
                    try {
                        BluetoothGatt value = entry.getValue();
                        BluetoothGattCharacteristic characteristic = value.getService(UUID.fromString(FFE0)).getCharacteristic(UUID.fromString(FFE2));
                        if (characteristic != null) {
                            characteristic.setValue(byteArray);
                            value.writeCharacteristic(characteristic);
                            if (value.setCharacteristicNotification(characteristic, true) && (descriptors = characteristic.getDescriptors()) != null && descriptors.size() > 0) {
                                for (BluetoothGattDescriptor bluetoothGattDescriptor : descriptors) {
                                    bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    value.writeDescriptor(bluetoothGattDescriptor);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void setDmx02CustomanimationMode(int i) {
        try {
            sendData(new int[]{123, 255, 21, i, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02TextFoneSize(int i) {
        try {
            sendData(new int[]{123, i, 24, 255, 255, 255, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02TextColor(int[] iArr) {
        try {
            sendData(new int[]{123, 1, 22, iArr[0], iArr[1], iArr[2], 0, 0, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02TextAlignmentColorBackgroundAnimation(int i, int[] iArr, int i2, int i3) {
        try {
            sendData(new int[]{123, i, 22, iArr[0], iArr[1], iArr[2], i2, i3, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setDmx02GraffitColor(int i, int i2, int i3, int i4) {
        try {
            sendData(new int[]{123, i, 25, i2, i3, i4, 255, 255, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    public void setConfigDmx02(int i, int i2, byte b, byte b2, byte b3, byte b4) {
        try {
            sendData(new int[]{123, i, 20, i2, b, b2, b3, b4, 191});
        } catch (Exception e) {
            NetExceptionInterface netExceptionInterface = this.exceptionCallBack;
            if (netExceptionInterface != null) {
                netExceptionInterface.onException(e);
            }
        }
    }

    private String to04Hex(int i) {
        String hexString = Integer.toHexString(i);
        int length = hexString.length();
        for (int i2 = 0; i2 < 4 - length; i2++) {
            hexString = NetResult.CODE_OK + hexString;
        }
        return hexString;
    }

    private int[] getNumb(int i) {
        int[] iArr = new int[4];
        String hexString = Integer.toHexString(i);
        int length = hexString.length();
        for (int i2 = 0; i2 < 8 - length; i2++) {
            hexString = NetResult.CODE_OK + hexString;
        }
        String substring = hexString.substring(4);
        String substring2 = hexString.substring(0, 4);
        iArr[0] = Integer.parseInt(substring.substring(2), 16);
        iArr[1] = Integer.parseInt(substring.substring(0, 2), 16);
        iArr[2] = Integer.parseInt(substring2.substring(2), 16);
        iArr[3] = Integer.parseInt(substring2.substring(0, 2), 16);
        return iArr;
    }

    private static int[] HexStringToIntArray(String str) {
        int length = str.length() / 2;
        String[] strArr = new String[length];
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            int i3 = i2 + 2;
            strArr[i] = str.substring(i2, i3);
            iArr[i] = Integer.parseInt(str.substring(i2, i3), 16);
        }
        return iArr;
    }

    public void sendPasswordDataWithCallback(int[] iArr) {
        List<BluetoothGattDescriptor> descriptors;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i : iArr) {
                byteArrayOutputStream.write(Tool.int2bytearray(i));
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            HashMap<String, BluetoothGatt> bleGattMap = LedBleApplication.getApp().getBleGattMap();
            if (bleGattMap == null || bleGattMap.isEmpty()) {
                return;
            }
            if (!ListUtiles.isEmpty(this.groupDevices) || StringUtils.isEmpty(this.groupName)) {
                for (Map.Entry<String, BluetoothGatt> entry : bleGattMap.entrySet()) {
                    try {
                        BluetoothGatt value = entry.getValue();
                        BluetoothGattCharacteristic characteristic = value.getService(UUID.fromString(FFE0)).getCharacteristic(UUID.fromString(FFE1));
                        if (characteristic != null) {
                            characteristic.setValue(byteArray);
                            value.writeCharacteristic(characteristic);
                            if (value.setCharacteristicNotification(characteristic, true) && (descriptors = characteristic.getDescriptors()) != null && descriptors.size() > 0) {
                                for (BluetoothGattDescriptor bluetoothGattDescriptor : descriptors) {
                                    bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    value.writeDescriptor(bluetoothGattDescriptor);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void sendData(int[] iArr) throws IOException {
        if (MainActivity_BLE.getMainActivity() != null) {
            Utils.getInstance();
            Utils.versionIdentificationTips(MainActivity_BLE.getMainActivity(), MainActivity_BLE.getSceneBean());
        }
        if (MainActivity_DMX02.getMainActivity() != null) {
            Utils.getInstance();
            Utils.versionIdentificationTips(MainActivity_DMX02.getMainActivity(), MainActivity_DMX02.getSceneBean());
            if (LedBleApplication.getApp().getSafetyLevel() == 1) {
                Toast.makeText(MainActivity_DMX02.getMainActivity(), "" + MainActivity_DMX02.getMainActivity().getResources().getString(R.string.password_error), 0).show();
            } else if (LedBleApplication.getApp().getSafetyLevel() == 2) {
                Toast.makeText(MainActivity_DMX02.getMainActivity(), "" + MainActivity_DMX02.getMainActivity().getResources().getString(R.string.The_password_cannot_be_the_ame_number_or_letter), 0).show();
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i : iArr) {
            byteArrayOutputStream.write(Tool.int2bytearray(i));
        }
        sendCharacteristic(byteArrayOutputStream.toByteArray());
    }
}
