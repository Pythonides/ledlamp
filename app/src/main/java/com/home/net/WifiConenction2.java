package com.home.net;

import android.content.Context;
import android.os.Handler;
import android.text.format.Time;
import com.common.uitl.Constant;
import com.common.uitl.NumberHelper;
import com.common.uitl.Tool;
import com.home.bean.MyColor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public class WifiConenction2 extends BaseConnection {
    public static final String LOCALHOST = "192.168.4.1";
    public static final int PORT = 5000;
    private static WifiConenction2 netConnect = null;
    public static String tag = "WifiConenction";
    Handler handlerrgb;
    private InputStream inputStream;
    private ArrayList<String> ips;
    private boolean isUDP;
    private int k;
    private OutputStream outStream;
    private Socket socket;
    boolean aa = true;
    private boolean isMusic = false;

    @Override // com.home.net.BaseConnection
    public void sendSearchOnlineDevices() throws Exception {
    }

    static /* synthetic */ int access$008(WifiConenction2 wifiConenction2) {
        int i = wifiConenction2.k;
        wifiConenction2.k = i + 1;
        return i;
    }

    public WifiConenction2(Context context) {
    }

    public WifiConenction2() {
    }

    public static WifiConenction2 getInstance() {
        if (netConnect == null) {
            netConnect = new WifiConenction2();
        }
        return netConnect;
    }

    public WifiConenction2(Context context, boolean z, ArrayList<String> arrayList) {
        this.isUDP = z;
        this.ips = arrayList;
    }

    @Override // com.home.net.BaseConnection
    public void open() throws Exception {
        write(new int[]{126, 255, 4, 1, 255, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void close() throws Exception {
        write(new int[]{126, 255, 4, 0, 255, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void setRgb(int i, int i2, int i3, String str, boolean z) throws Exception {
        int[] iArr = new int[9];
        iArr[0] = 126;
        iArr[1] = 255;
        iArr[2] = 5;
        iArr[3] = 3;
        iArr[4] = i;
        iArr[5] = i2;
        iArr[6] = i3;
        iArr[7] = z ? 1 : 255;
        iArr[8] = 239;
        write(iArr);
    }

    @Override // com.home.net.BaseConnection
    public void setRgbMode(int i) throws Exception {
        write(new int[]{126, 255, 3, i, 3, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void setSpeed(int i) throws Exception {
        write(new int[]{126, 255, 2, i, 255, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void setBrightness(int i) throws Exception {
        write(new int[]{126, 255, 1, i, 255, 255, 255, 255, 239});
    }

    public void sendTimeLightandStage(int i, int i2, int i3, int i4, int i5, int i6) throws Exception {
        Thread.sleep(200L);
        write(new int[]{126, i, 8, i2, i3, i4, i5, i6, 239});
    }

    public void setDiy(final ArrayList<MyColor> arrayList, final int i) {
        try {
            this.k = 0;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() { // from class: com.home.net.WifiConenction2.1
                @Override // java.lang.Runnable
                public void run() {
                    if (WifiConenction2.this.k != arrayList.size()) {
                        int[] iArr = {126, 255, 6, i, ((MyColor) arrayList.get(WifiConenction2.this.k)).r, ((MyColor) arrayList.get(WifiConenction2.this.k)).g, ((MyColor) arrayList.get(WifiConenction2.this.k)).b, arrayList.size(), 239};
                        WifiConenction2.access$008(WifiConenction2.this);
                        try {
                            WifiConenction2.this.write(iArr);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handler.postDelayed(this, 200L);
                        return;
                    }
                    handler.removeCallbacks(this);
                    WifiConenction2.this.k = 0;
                }
            }, 100L);
        } catch (Exception unused) {
        }
    }

    @Override // com.home.net.BaseConnection
    public void setColorWarmModel(int i) throws Exception {
        write(new int[]{126, 255, 3, i, 2, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void setDimModel(int i) throws Exception {
        write(new int[]{126, 255, 3, i, 1, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void setDim(int i) throws Exception {
        write(new int[]{126, 255, 5, 1, i, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void setMusic(int i, int i2, int i3, int i4) throws Exception {
        write(new int[]{126, 7, 6, i, 0, 0, 0, 0, 239});
    }

    @Override // com.home.net.BaseConnection
    public void setColorWarm(int i, int i2) throws Exception {
        write(new int[]{126, 255, 5, 2, i, i2, 255, 255, 239});
    }

    public void setModeCycle(int i, int i2, int i3, int i4, int i5, int i6) throws Exception {
        write(new int[]{126, i, 15, 1, i2, i3, i4, i5, 239});
    }

    @Override // com.home.net.BaseConnection
    public void setSPIBrightness(int i) throws Exception {
        write(new int[]{123, 4, 1, i, 255, 255, 255, 0, 191});
    }

    @Override // com.home.net.BaseConnection
    public void setSPISpeed(int i) throws Exception {
        write(new int[]{123, 4, 2, i, 255, 255, 255, 0, 191});
    }

    public void setSPIModel(int i) throws Exception {
        write(new int[]{123, 5, 3, i, 3, 255, 255, 0, 191});
    }

    @Override // com.home.net.BaseConnection
    public void turnOnSPI(int i) throws Exception {
        write(new int[]{123, 4, 4, i, 255, 255, 255, 0, 191});
    }

    @Override // com.home.net.BaseConnection
    public void configSPI(int i, byte b, byte b2, int i2) throws Exception {
        write(new int[]{123, 4, 5, i, b, b2, i2, 0, 191});
    }

    @Override // com.home.net.BaseConnection
    public void pauseSPI(int i) throws Exception {
        write(new int[]{123, 4, 6, i, 255, 255, 255, 0, 191});
    }

    public void sendOffline() throws Exception {
        write(new int[]{126, 7, 11, 255, 255, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void sendRouteSSID() throws Exception {
        write(new int[]{126, 7, 7, 255, 255, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void sendRoutePassword() throws Exception {
        write(new int[]{126, 7, 8, 255, 255, 255, 255, 255, 239});
    }

    @Override // com.home.net.BaseConnection
    public void sendSSID(String str) throws Exception {
        writeByte((str + "\n").getBytes("utf-8"));
    }

    @Override // com.home.net.BaseConnection
    public void sendPassword(String str) throws Exception {
        writeByte((str + "\n").getBytes("utf-8"));
    }

    public void setSmartBrightness(int i, int i2) throws Exception {
        write(new int[]{126, 255, 7, i, i2, 255, 255, 255, 239});
    }

    public void code_check(int i, int i2) throws Exception {
        write(new int[]{126, 255, 10, i, i2, 255, 255, 255, 191});
    }

    public void SetCHN(int i, int i2, int i3, int i4, int i5, int i6) throws Exception {
        write(new int[]{126, i, 11, i2, i3, i4, i5, i6, 239});
    }

    public void setSmartCheck(int i) throws Exception {
        write(new int[]{126, 255, 9, i, 255, 255, 255, 255, 239});
    }

    public ArrayList<String> setTBCheck(int i) throws Exception {
        write(new int[]{126, 255, 9, i, 255, 255, 255, 255, 239});
        return null;
    }

    public ArrayList<String> TBCheckRecv() throws Exception {
        try {
            Socket socket = this.socket;
            if (socket == null || !socket.isConnected()) {
                return null;
            }
            String str = new String();
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                this.inputStream.available();
                int read = this.inputStream.read();
                if (read > 0 && (i == 3 || i == 4)) {
                    str = str + Integer.toString(read);
                    arrayList.add(Integer.toString(read));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // com.home.net.BaseConnection
    public void sendRouteCommand(String str, String str2) throws Exception {
        sendRouteSSID();
        Thread.sleep(300L);
        sendSSID(str);
        Thread.sleep(300L);
        sendRoutePassword();
        Thread.sleep(300L);
        sendPassword(str2);
    }

    private static int computeTime(int i, int i2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.STRING_DAY_FORMAT5);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(Constant.STRING_DAY_FORMAT2);
        Date time = Calendar.getInstance().getTime();
        String format = simpleDateFormat2.format(time);
        int time2 = (int) ((simpleDateFormat.parse(format + " " + NumberHelper.LeftPad_Tow_Zero(i) + ":" + NumberHelper.LeftPad_Tow_Zero(i2)).getTime() - time.getTime()) / 1000);
        return time2 < 0 ? time2 + 86400 : time2;
    }

    public void turnOnOffTimerOn(int i, int i2, int i3, int i4) {
        try {
            Time time = new Time();
            time.setToNow();
            write(new int[]{126, i, 8, time.hour, time.minute, i2, i3, i4, 239});
        } catch (Exception unused) {
        }
    }

    public void turnOnOrOffTimerOff(int i, int i2, int i3, int i4) {
        try {
            Time time = new Time();
            time.setToNow();
            write(new int[]{126, i, 8, time.hour, time.minute, i2, i3, i4, 239});
        } catch (Exception unused) {
        }
    }

    public void timerOn(int i, int i2, int i3) {
        try {
            int computeTime = computeTime(i, i2);
            int i4 = computeTime / 60;
            write(new int[]{126, 1, 13, (byte) (i4 >> 8), (byte) i4, 1, i3, computeTime % 60, 239});
        } catch (Exception unused) {
        }
    }

    public void timerOff(int i, int i2) {
        try {
            int computeTime = computeTime(i, i2);
            int i3 = computeTime / 60;
            write(new int[]{126, 1, 13, (byte) (i3 >> 8), (byte) i3, 0, 255, computeTime % 60, 239});
        } catch (Exception unused) {
        }
    }

    @Override // com.home.net.BaseConnection
    public boolean isOnLine() {
        if (this.isUDP) {
            return true;
        }
        Socket socket = this.socket;
        return (socket == null || socket.isClosed()) ? false : true;
    }

    public void isMusic(boolean z) {
        this.isMusic = z;
    }

    @Override // com.home.net.BaseConnection
    public void write(final int[] iArr) throws Exception {
        new Thread(new Runnable() { // from class: com.home.net.WifiConenction2.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int i = 0;
                    while (true) {
                        int[] iArr2 = iArr;
                        if (i >= iArr2.length) {
                            break;
                        }
                        byteArrayOutputStream.write(Tool.int2bytearray(iArr2[i]));
                        i++;
                    }
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    if (byteArray == null || WifiConenction2.this.outStream == null) {
                        return;
                    }
                    WifiConenction2.this.outStream.write(byteArray);
                    WifiConenction2.this.outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override // com.home.net.BaseConnection
    public void writeByte(byte[] bArr) throws Exception {
        this.outStream.write(bArr);
        this.outStream.flush();
    }

    @Override // com.home.net.BaseConnection
    public void closeSocket() {
        if (this.isUDP) {
            return;
        }
        try {
            Socket socket = this.socket;
            if (socket != null) {
                socket.close();
                this.socket = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.home.net.BaseConnection
    public boolean connect() {
        closeSocket();
        try {
            Socket socket = new Socket(InetAddress.getByName(LOCALHOST), 5000);
            this.socket = socket;
            this.inputStream = socket.getInputStream();
            this.outStream = this.socket.getOutputStream();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendBroadcast(int[] iArr, String str) {
        DatagramSocket datagramSocket = null;
        try {
            try {
                DatagramSocket datagramSocket2 = new DatagramSocket();
                try {
                    InetAddress byName = InetAddress.getByName(str);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    for (int i : iArr) {
                        byteArrayOutputStream.write(Tool.int2bytearray(i));
                    }
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    datagramSocket2.send(new DatagramPacket(byteArray, byteArray.length, byName, 5000));
                    datagramSocket2.close();
                } catch (Exception e) {
                    e = e;
                    datagramSocket = datagramSocket2;
                    e.printStackTrace();
                    if (datagramSocket != null) {
                        datagramSocket.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    datagramSocket = datagramSocket2;
                    if (datagramSocket != null) {
                        datagramSocket.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }
}
