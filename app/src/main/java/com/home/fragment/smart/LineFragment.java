package com.home.fragment.smart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.PointerIconCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import com.common.net.NetResult;
import com.common.uitl.SharePersistent;
import com.common.uitl.Utility;
import com.common.view.SegmentedRadioGroup;
import com.example.linechartlibrary.LineChartView;
import com.example.linechartlibrary.Viewport;
import com.home.activity.main.MainActivity_BLE;
import com.home.activity.set.smart.TimerSettingActivity_Smart;
import com.home.activity.set.smart.modeTime.BWDataBase;
import com.home.activity.set.smart.modeTime.BWDataBaseOne;
import com.home.activity.set.smart.modeTime.MySqliteModeHelper;
import com.home.activity.set.smart.modeTime.RGBDataBase;
import com.home.activity.set.smart.modeTime.RGBDataBaseOne;
import com.home.activity.set.smart.modeTime.RGBWCPDataBase;
import com.home.activity.set.smart.modeTime.RGBWCPDataBaseOne;
import com.home.activity.set.smart.modeTime.RGBWDataBase;
import com.home.activity.set.smart.modeTime.RGBWDataBaseOne;
import com.home.activity.set.smart.modeTime.TimeModle;
import com.home.activity.set.smart.modeTime.WDataBase;
import com.home.activity.set.smart.modeTime.WDataBaseOne;
import com.home.base.LedBleApplication;
import com.home.constant.CommonConstant;
import com.home.view.SelectMultiCheckGroup;
import com.ledlamp.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public class LineFragment extends Fragment implements LineChartView.ReturnValueCallback, LineChartView.SendCallBack {
    private static final String SeekBarB = "SeekBarB";
    private static final String SeekBarC = "SeekBarC";
    private static final String SeekBarG = "SeekBarG";
    private static final String SeekBarP = "SeekBarP";
    private static final String SeekBarR = "SeekBarR";
    private static final String SeekBarW = "SeekBarW";
    private static final String TAG = "LineFragment";
    public static int isChange = 0;
    public static int lineshow = 0;
    public static List<TimeModle> listDate_line = null;
    public static String modeBean = null;
    private static SharedPreferences sp = null;
    private static String table = "one";
    BWDataBase bwDataBase;
    BWDataBaseOne bwDataBaseOne;
    private SelectMultiCheckGroup checkGroupWeek;
    int day;
    private Dialog dialog;
    LineChartView lineChartView;
    MainActivity_BLE mActivity;
    View mView;
    ListView mlistView;
    MyAdapterTime_line myAdapterTime_line;
    RGBDataBase rgbDataBase;
    RGBDataBaseOne rgbDataBaseOne;
    RGBWDataBase rgbwDataBase;
    RGBWDataBaseOne rgbwDataBaseOne;
    RGBWCPDataBase rgbwcpDataBase;
    RGBWCPDataBaseOne rgbwcpDataBaseOne;
    RelativeLayout rl_1;
    RelativeLayout rl_2;
    RelativeLayout rl_3;
    RelativeLayout rl_4;
    RelativeLayout rl_5;
    RelativeLayout rl_6;
    RelativeLayout rl_b;
    RelativeLayout rl_cyan;
    RelativeLayout rl_g;
    RelativeLayout rl_pink;
    RelativeLayout rl_r;
    RelativeLayout rl_w;
    ScrollView scrollView;
    SeekBar seekBarB;
    SeekBar seekBarG;
    SeekBar seekBarR;
    SeekBar seekBarW;
    private SegmentedRadioGroup segmentSmartTimer;
    SeekBar skcyan;
    SeekBar skpink;
    Timer timer;
    private TextView tvAddTimer;
    TextView tvGradient;
    TextView tvJump;
    TextView tvLook;
    TextView tvReset;
    TextView tvb;
    TextView tvcycn;
    TextView tvg;
    TextView tvpink;
    TextView tvr;
    TextView tvw;
    WDataBase wDataBase;
    WDataBaseOne wDataBaseOne;
    private int style = 0;
    int size = 0;
    boolean stare = true;
    boolean first = true;
    int idx = 0;
    private final int MSG_START = 1000;
    private final int MSG_PREVIEW = PointerIconCompat.TYPE_CONTEXT_MENU;
    private Handler conectHandler = new Handler() { // from class: com.home.fragment.smart.LineFragment.19
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1000) {
                Utility.setListViewHeightBasedOnChildren(LineFragment.this.getContext(), LineFragment.this.mlistView);
                LineFragment.this.myAdapterTime_line.notifyDataSetChanged();
            } else if (i != 1001) {
            } else {
                if (LineFragment.this.size <= LineChartView.getWidth) {
                    LineFragment.this.size += 4;
                    ArrayList start = LineFragment.this.lineChartView.start(LineFragment.this.size, LineFragment.lineshow);
                    LineFragment.this.mActivity.setTime(((Integer) start.get(0)).intValue(), ((Integer) start.get(1)).intValue(), ((Integer) start.get(2)).intValue(), ((Integer) start.get(3)).intValue(), ((Integer) start.get(4)).intValue(), ((Integer) start.get(5)).intValue());
                    return;
                }
                LineChartView.isChange = true;
                LineFragment.this.stare = true;
                ArrayList start2 = LineFragment.this.lineChartView.start(LineFragment.this.lineChartView.getNowTime(), LineFragment.lineshow);
                LineFragment.this.mActivity.setTime(((Integer) start2.get(0)).intValue(), ((Integer) start2.get(1)).intValue(), ((Integer) start2.get(2)).intValue(), ((Integer) start2.get(3)).intValue(), ((Integer) start2.get(4)).intValue(), ((Integer) start2.get(5)).intValue());
                LineFragment.this.timer.cancel();
                LineFragment.this.dialogdismiss();
            }
        }
    };

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mView = layoutInflater.inflate(R.layout.fragment_line, viewGroup, false);
        MainActivity_BLE mainActivity_BLE = (MainActivity_BLE) getActivity();
        this.mActivity = mainActivity_BLE;
        this.wDataBase = WDataBase.getInstance(mainActivity_BLE);
        this.bwDataBase = BWDataBase.getInstance(this.mActivity);
        this.rgbDataBase = RGBDataBase.getInstance(this.mActivity);
        this.rgbwDataBase = RGBWDataBase.getInstance(this.mActivity);
        this.rgbwcpDataBase = RGBWCPDataBase.getInstance(this.mActivity);
        this.wDataBaseOne = WDataBaseOne.getInstance(this.mActivity);
        this.bwDataBaseOne = BWDataBaseOne.getInstance(this.mActivity);
        this.rgbDataBaseOne = RGBDataBaseOne.getInstance(this.mActivity);
        this.rgbwDataBaseOne = RGBWDataBaseOne.getInstance(this.mActivity);
        this.rgbwcpDataBaseOne = RGBWCPDataBaseOne.getInstance(this.mActivity);
        listDate_line = new ArrayList();
        initView();
        drawLine();
        changed();
        if (getBoolean(getContext(), "caodan", this.first)) {
            abc();
        }
        return this.mView;
    }

    public void setModeBean(String str) {
        modeBean = str;
    }

    public static void putBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = getSp(context).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public static boolean getBoolean(Context context, String str, boolean z) {
        return getSp(context).getBoolean(str, z);
    }

    private void insertWDataBase() {
        this.wDataBase.insert(MySqliteModeHelper.W, 0, 0, 0, 0, 0, 0);
        this.wDataBase.insert(MySqliteModeHelper.W, 9, 0, 0, 0, 0, 0);
        this.wDataBase.insert(MySqliteModeHelper.W, 10, 0, 10, 10, 0, 0);
        this.wDataBase.insert(MySqliteModeHelper.W, 12, 0, 80, 85, 85, 85);
        this.wDataBase.insert(MySqliteModeHelper.W, 13, 0, 80, 100, 100, 100);
        this.wDataBase.insert(MySqliteModeHelper.W, 14, 0, 100, 100, 100, 100);
        this.wDataBase.insert(MySqliteModeHelper.W, 16, 0, 100, 100, 100, 60);
        this.wDataBase.insert(MySqliteModeHelper.W, 18, 0, 100, 100, 80, 30);
        this.wDataBase.insert(MySqliteModeHelper.W, 20, 0, 90, 80, 60, 10);
        this.wDataBase.insert(MySqliteModeHelper.W, 22, 0, 70, 70, 50, 10);
        this.wDataBase.insert(MySqliteModeHelper.W, 23, 0, 0, 0, 0, 0);
    }

    private void insertWDataBaseOne() {
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 0, 0, 0, 0, 0, 0);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 9, 0, 0, 0, 0, 0);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 10, 0, 10, 10, 0, 0);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 12, 0, 80, 85, 85, 85);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 13, 0, 80, 100, 100, 100);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 14, 0, 100, 100, 100, 100);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 16, 0, 100, 100, 100, 60);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 18, 0, 100, 100, 80, 30);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 20, 0, 90, 80, 60, 10);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 22, 0, 70, 70, 50, 10);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 23, 0, 0, 0, 0, 0);
    }

    private void insertBWDataBase() {
        this.bwDataBase.insert(MySqliteModeHelper.BW, 0, 0, 0, 0, 0, 0);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 9, 0, 0, 0, 0, 0);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 10, 0, 10, 10, 0, 0);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 12, 0, 80, 85, 85, 85);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 13, 0, 80, 100, 100, 100);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 14, 0, 100, 100, 100, 100);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 16, 0, 100, 100, 100, 60);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 18, 0, 100, 100, 80, 30);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 20, 0, 90, 80, 60, 10);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 22, 0, 70, 70, 50, 10);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 23, 0, 0, 0, 0, 0);
    }

    private void insertBWDataBaseOne() {
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 0, 0, 0, 0, 0, 0);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 9, 0, 0, 0, 0, 0);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 10, 0, 10, 10, 0, 0);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 12, 0, 80, 85, 85, 85);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 13, 0, 80, 100, 100, 100);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 14, 0, 100, 100, 100, 100);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 16, 0, 100, 100, 100, 60);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 18, 0, 100, 100, 80, 30);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 20, 0, 90, 80, 60, 10);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 22, 0, 70, 70, 50, 10);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 23, 0, 0, 0, 0, 0);
    }

    public void abc() {
        this.wDataBase.insert(MySqliteModeHelper.W, 0, 0, 0, 0, 0, 0);
        this.wDataBase.insert(MySqliteModeHelper.W, 9, 0, 0, 0, 0, 0);
        this.wDataBase.insert(MySqliteModeHelper.W, 10, 0, 10, 10, 0, 0);
        this.wDataBase.insert(MySqliteModeHelper.W, 12, 0, 80, 85, 85, 85);
        this.wDataBase.insert(MySqliteModeHelper.W, 13, 0, 80, 100, 100, 100);
        this.wDataBase.insert(MySqliteModeHelper.W, 14, 0, 100, 100, 100, 100);
        this.wDataBase.insert(MySqliteModeHelper.W, 16, 0, 100, 100, 100, 60);
        this.wDataBase.insert(MySqliteModeHelper.W, 18, 0, 100, 100, 80, 30);
        this.wDataBase.insert(MySqliteModeHelper.W, 20, 0, 90, 80, 60, 10);
        this.wDataBase.insert(MySqliteModeHelper.W, 22, 0, 70, 70, 50, 10);
        this.wDataBase.insert(MySqliteModeHelper.W, 23, 0, 0, 0, 0, 0);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 0, 0, 0, 0, 0, 0);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 9, 0, 0, 0, 0, 0);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 10, 0, 10, 10, 0, 0);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 12, 0, 80, 85, 85, 85);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 13, 0, 80, 100, 100, 100);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 14, 0, 100, 100, 100, 100);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 16, 0, 100, 100, 100, 60);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 18, 0, 100, 100, 80, 30);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 20, 0, 90, 80, 60, 10);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 22, 0, 70, 70, 50, 10);
        this.bwDataBase.insert(MySqliteModeHelper.BW, 23, 0, 0, 0, 0, 0);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 0, 0, 0, 0, 0);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 9, 0, 0, 0, 0);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 10, 0, 10, 10, 0);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 12, 0, 80, 85, 85);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 13, 0, 80, 100, 100);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 14, 0, 100, 100, 100);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 16, 0, 100, 100, 100);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 18, 0, 100, 100, 80);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 20, 0, 90, 80, 60);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 22, 0, 70, 70, 50);
        this.rgbDataBase.insert(MySqliteModeHelper.RGB, 23, 0, 0, 0, 0);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 0, 0, 0, 0, 0, 0);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 9, 0, 0, 0, 0, 0);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 10, 0, 10, 10, 0, 0);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 12, 0, 80, 85, 85, 85);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 13, 0, 80, 100, 100, 100);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 14, 0, 100, 100, 100, 100);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 16, 0, 100, 100, 100, 60);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 18, 0, 100, 100, 80, 30);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 20, 0, 90, 80, 60, 10);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 22, 0, 70, 70, 50, 10);
        this.rgbwDataBase.insert(MySqliteModeHelper.RGBW, 23, 0, 0, 0, 0, 0);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 0, 0, 0, 0, 0, 0, 0, 0);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 9, 0, 0, 0, 0, 0, 0, 0);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 10, 0, 10, 10, 0, 0, 0, 0);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 12, 0, 80, 85, 85, 85, 85, 85);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 13, 0, 80, 100, 100, 100, 100, 100);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 14, 0, 100, 100, 100, 100, 100, 100);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 16, 0, 100, 100, 100, 60, 60, 60);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 18, 0, 100, 100, 80, 30, 30, 30);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 20, 0, 90, 80, 60, 10, 10, 10);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 22, 0, 70, 70, 50, 10, 10, 10);
        this.rgbwcpDataBase.insert(MySqliteModeHelper.RGBWCP, 23, 0, 0, 0, 0, 0, 0, 0);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 0, 0, 0, 0, 0, 0);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 9, 0, 0, 0, 0, 0);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 10, 0, 10, 10, 0, 0);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 12, 0, 80, 85, 85, 85);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 13, 0, 80, 100, 100, 100);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 14, 0, 100, 100, 100, 100);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 16, 0, 100, 100, 100, 60);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 18, 0, 100, 100, 80, 30);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 20, 0, 90, 80, 60, 10);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 22, 0, 70, 70, 50, 10);
        this.wDataBaseOne.insert(MySqliteModeHelper.WONE, 23, 0, 0, 0, 0, 0);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 0, 0, 0, 0, 0, 0);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 9, 0, 0, 0, 0, 0);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 10, 0, 10, 10, 0, 0);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 12, 0, 80, 85, 85, 85);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 13, 0, 80, 100, 100, 100);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 14, 0, 100, 100, 100, 100);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 16, 0, 100, 100, 100, 60);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 18, 0, 100, 100, 80, 30);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 20, 0, 90, 80, 60, 10);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 22, 0, 70, 70, 50, 10);
        this.bwDataBaseOne.insert(MySqliteModeHelper.BWONE, 23, 0, 0, 0, 0, 0);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 0, 0, 0, 0, 0);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 9, 0, 0, 0, 0);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 10, 0, 10, 10, 0);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 12, 0, 80, 85, 85);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 13, 0, 80, 100, 100);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 14, 0, 100, 100, 100);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 16, 0, 100, 100, 100);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 18, 0, 100, 100, 80);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 20, 0, 90, 80, 60);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 22, 0, 70, 70, 50);
        this.rgbDataBaseOne.insert(MySqliteModeHelper.RGBONE, 23, 0, 0, 0, 0);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 0, 0, 0, 0, 0, 0);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 9, 0, 0, 0, 0, 0);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 10, 0, 10, 10, 0, 0);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 12, 0, 80, 85, 85, 85);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 13, 0, 80, 100, 100, 100);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 14, 0, 100, 100, 100, 100);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 16, 0, 100, 100, 100, 60);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 18, 0, 100, 100, 80, 30);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 20, 0, 90, 80, 60, 10);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 22, 0, 70, 70, 50, 10);
        this.rgbwDataBaseOne.insert(MySqliteModeHelper.RGBWONE, 23, 0, 0, 0, 0, 0);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 0, 0, 0, 0, 0, 0, 0, 0);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 9, 0, 0, 0, 0, 0, 0, 0);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 10, 0, 10, 10, 0, 0, 0, 0);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 12, 0, 80, 85, 85, 85, 85, 85);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 13, 0, 80, 100, 100, 100, 100, 100);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 14, 0, 100, 100, 100, 100, 100, 100);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 16, 0, 100, 100, 100, 60, 60, 60);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 18, 0, 100, 100, 80, 30, 30, 30);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 20, 0, 90, 80, 60, 10, 10, 10);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 22, 0, 70, 70, 50, 10, 10, 10);
        this.rgbwcpDataBaseOne.insert(MySqliteModeHelper.RGBWCPONE, 23, 0, 0, 0, 0, 0, 0, 0);
        this.first = false;
        putBoolean(getContext(), "caodan", this.first);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        SegmentedRadioGroup segmentSmartTimer = MainActivity_BLE.getMainActivity().getSegmentSmartTimer();
        this.segmentSmartTimer = segmentSmartTimer;
        segmentSmartTimer.check(R.id.rbTable1);
        this.segmentSmartTimer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.fragment.smart.LineFragment.1
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (R.id.rbTable1 == i) {
                    String unused = LineFragment.table = "one";
                    LineFragment.this.onResume();
                    LineFragment.this.reloadChartView();
                } else if (R.id.rbTable2 == i) {
                    String unused2 = LineFragment.table = "two";
                    LineFragment.this.onResume();
                    LineFragment.this.reloadChartView();
                }
                LineFragment.this.reloadWeekGroupData();
                LineFragment.this.reloadSeekBarData();
                if (LineFragment.listDate_line == null || LineFragment.listDate_line.size() != 0) {
                    return;
                }
                LineFragment.this.lineChartView.clear();
            }
        });
        SelectMultiCheckGroup checkGroupWeek = MainActivity_BLE.getMainActivity().getCheckGroupWeek();
        this.checkGroupWeek = checkGroupWeek;
        checkGroupWeek.initData(Arrays.asList(getResources().getStringArray(R.array.week)));
        reloadWeekGroupData();
        this.checkGroupWeek.setOnItemSelectedListener(new SelectMultiCheckGroup.OnItemSelectedListener() { // from class: com.home.fragment.smart.LineFragment.2
            @Override // com.home.view.SelectMultiCheckGroup.OnItemSelectedListener
            public void checked(View view, int i, boolean z) {
                List<Integer> selectedAll = LineFragment.this.checkGroupWeek.getSelectedAll();
                Collections.sort(selectedAll);
                StringBuffer stringBuffer = new StringBuffer();
                String str = null;
                for (int i2 = 0; i2 < selectedAll.size(); i2++) {
                    stringBuffer.append(selectedAll.get(i2));
                    stringBuffer.append(" ");
                    str = stringBuffer.toString();
                }
                MainActivity_BLE mainActivity_BLE = LineFragment.this.mActivity;
                SharePersistent.savePerference(mainActivity_BLE, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table, str);
            }
        });
        TextView textView = (TextView) this.mView.findViewById(R.id.tvAddTimer);
        this.tvAddTimer = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.smart.LineFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LineFragment.listDate_line.size() < 24) {
                    Intent intent = new Intent(LineFragment.this.mActivity, TimerSettingActivity_Smart.class);
                    intent.putExtra("tag", LineFragment.listDate_line.size() + 1);
                    LineFragment.this.startActivityForResult(intent, 113);
                    return;
                }
                Toast.makeText(LineFragment.this.mActivity, (int) R.string.timeSize, 1).show();
            }
        });
        this.scrollView = (ScrollView) this.mView.findViewById(R.id.scrollView);
        this.rl_r = (RelativeLayout) this.mView.findViewById(R.id.rl_r);
        this.rl_g = (RelativeLayout) this.mView.findViewById(R.id.rl_g);
        this.rl_b = (RelativeLayout) this.mView.findViewById(R.id.rl_b);
        this.rl_w = (RelativeLayout) this.mView.findViewById(R.id.rl_w);
        this.rl_cyan = (RelativeLayout) this.mView.findViewById(R.id.rl_cyan);
        this.rl_pink = (RelativeLayout) this.mView.findViewById(R.id.rl_pink);
        this.rl_1 = (RelativeLayout) this.mView.findViewById(R.id.rl_1);
        this.rl_2 = (RelativeLayout) this.mView.findViewById(R.id.rl_2);
        this.rl_3 = (RelativeLayout) this.mView.findViewById(R.id.rl_3);
        this.rl_4 = (RelativeLayout) this.mView.findViewById(R.id.rl_4);
        this.rl_5 = (RelativeLayout) this.mView.findViewById(R.id.rl_5);
        this.rl_6 = (RelativeLayout) this.mView.findViewById(R.id.rl_6);
        this.rl_r.getBackground().setAlpha(0);
        this.rl_b.getBackground().setAlpha(0);
        this.rl_g.getBackground().setAlpha(0);
        this.rl_w.getBackground().setAlpha(0);
        this.rl_cyan.getBackground().setAlpha(0);
        this.rl_pink.getBackground().setAlpha(0);
        this.tvReset = (TextView) this.mView.findViewById(R.id.tvReset);
        this.tvLook = (TextView) this.mView.findViewById(R.id.tvLook);
        this.tvGradient = (TextView) this.mView.findViewById(R.id.tvGradient);
        this.tvJump = (TextView) this.mView.findViewById(R.id.tvJump);
        this.tvb = (TextView) this.mView.findViewById(R.id.tvb);
        this.tvg = (TextView) this.mView.findViewById(R.id.tvg);
        this.tvr = (TextView) this.mView.findViewById(R.id.tvr);
        this.tvw = (TextView) this.mView.findViewById(R.id.tvw);
        this.tvpink = (TextView) this.mView.findViewById(R.id.tvpink);
        this.tvcycn = (TextView) this.mView.findViewById(R.id.tvcycn);
        this.seekBarB = (SeekBar) this.mView.findViewById(R.id.skb);
        this.seekBarG = (SeekBar) this.mView.findViewById(R.id.skg);
        this.seekBarR = (SeekBar) this.mView.findViewById(R.id.skr);
        this.seekBarW = (SeekBar) this.mView.findViewById(R.id.skw);
        this.skcyan = (SeekBar) this.mView.findViewById(R.id.skcyan);
        this.skpink = (SeekBar) this.mView.findViewById(R.id.skpink);
        this.seekBarR.getThumb().setColorFilter(SupportMenu.CATEGORY_MASK, PorterDuff.Mode.SRC_ATOP);
        this.seekBarR.getProgressDrawable().setColorFilter(SupportMenu.CATEGORY_MASK, PorterDuff.Mode.SRC_ATOP);
        this.seekBarG.getThumb().setColorFilter(-16711936, PorterDuff.Mode.SRC_ATOP);
        this.seekBarG.getProgressDrawable().setColorFilter(-16711936, PorterDuff.Mode.SRC_ATOP);
        this.seekBarB.getThumb().setColorFilter(-16776961, PorterDuff.Mode.SRC_ATOP);
        this.seekBarB.getProgressDrawable().setColorFilter(-16776961, PorterDuff.Mode.SRC_ATOP);
        this.seekBarW.getThumb().setColorFilter(-1, PorterDuff.Mode.SRC_ATOP);
        this.seekBarW.getProgressDrawable().setColorFilter(-1, PorterDuff.Mode.SRC_ATOP);
        this.skcyan.getThumb().setColorFilter(-16711681, PorterDuff.Mode.SRC_ATOP);
        this.skcyan.getProgressDrawable().setColorFilter(-16711681, PorterDuff.Mode.SRC_ATOP);
        this.skpink.getThumb().setColorFilter(-65281, PorterDuff.Mode.SRC_ATOP);
        this.skpink.getProgressDrawable().setColorFilter(-65281, PorterDuff.Mode.SRC_ATOP);
        LineChartView lineChartView = (LineChartView) this.mView.findViewById(R.id.lineView);
        this.lineChartView = lineChartView;
        lineChartView.setReturnValueCallback(this);
        this.lineChartView.setSendCallBack(this);
        this.mlistView = (ListView) this.mView.findViewById(R.id.list_item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadWeekGroupData() {
        MainActivity_BLE mainActivity_BLE = this.mActivity;
        String perference = SharePersistent.getPerference(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + table);
        int i = 0;
        if (perference.length() > 0) {
            while (i <= 6) {
                if (perference.contains("" + i)) {
                    this.checkGroupWeek.setSeleted(i);
                } else {
                    this.checkGroupWeek.setUnSeleted(i);
                }
                i++;
            }
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (i <= 6) {
            this.checkGroupWeek.setSeleted(i);
            stringBuffer.append(i + " ");
            String stringBuffer2 = stringBuffer.toString();
            MainActivity_BLE mainActivity_BLE2 = this.mActivity;
            SharePersistent.savePerference(mainActivity_BLE2, MainActivity_BLE.sceneBean + TAG + table, stringBuffer2);
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadSeekBarData() {
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE = this.mActivity;
            int i = SharePersistent.getInt(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + table + SeekBarR);
            this.seekBarR.setProgress(i);
            TextView textView = this.tvr;
            textView.setText(String.valueOf(i) + "%");
            MainActivity_BLE mainActivity_BLE2 = this.mActivity;
            int i2 = SharePersistent.getInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + TAG + table + SeekBarG);
            this.seekBarG.setProgress(i2);
            TextView textView2 = this.tvg;
            textView2.setText(String.valueOf(i2) + "%");
            MainActivity_BLE mainActivity_BLE3 = this.mActivity;
            int i3 = SharePersistent.getInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + TAG + table + SeekBarB);
            this.seekBarB.setProgress(i3);
            TextView textView3 = this.tvb;
            textView3.setText(String.valueOf(i3) + "%");
            Log.e(TAG, "table = " + table + ",  bValue = " + i3);
            MainActivity_BLE mainActivity_BLE4 = this.mActivity;
            int i4 = SharePersistent.getInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + TAG + table + SeekBarW);
            this.seekBarW.setProgress(i4);
            TextView textView4 = this.tvw;
            textView4.setText(String.valueOf(i4) + "%");
            MainActivity_BLE mainActivity_BLE5 = this.mActivity;
            int i5 = SharePersistent.getInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + TAG + table + SeekBarC);
            this.skcyan.setProgress(i5);
            TextView textView5 = this.tvcycn;
            textView5.setText(String.valueOf(i5) + "%");
            MainActivity_BLE mainActivity_BLE6 = this.mActivity;
            int i6 = SharePersistent.getInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + TAG + table + SeekBarP);
            this.skpink.setProgress(i6);
            TextView textView6 = this.tvpink;
            textView6.setText(String.valueOf(i6) + "%");
        }
    }

    public void radioClick() {
        if (!this.stare || LedBleApplication.getApp().getBleGattMap().size() <= 0) {
            return;
        }
        showCustomMessage(1);
        new Handler().postDelayed(new Runnable() { // from class: com.home.fragment.smart.LineFragment.4
            @Override // java.lang.Runnable
            public void run() {
                LineFragment.this.runNow();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCustomMessage(int i) {
        if (this.dialog == null) {
            Dialog dialog = new Dialog(this.mActivity, 16973840);
            this.dialog = dialog;
            dialog.requestWindowFeature(1024);
            this.dialog.setContentView(R.layout.dialogview);
            TextView textView = (TextView) this.dialog.findViewById(R.id.dialodTv);
            if (1 == i) {
                textView.setText(getString(R.string.sendtie));
            } else if (2 == i) {
                textView.setText(getString(R.string.looking));
            }
            ((ImageView) this.dialog.findViewById(R.id.imageViewWait)).setVisibility(8);
            this.dialog.show();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (table.equals("one")) {
            this.lineChartView.table(11);
        } else if (table.equals("two")) {
            this.lineChartView.table(22);
        }
        if (this.myAdapterTime_line == null) {
            this.myAdapterTime_line = new MyAdapterTime_line();
        }
        getDate();
        update();
    }

    public void getDate() {
        this.mlistView.setAdapter((ListAdapter) this.myAdapterTime_line);
        init();
        if (table.equals("one")) {
            int i = lineshow;
            if (i == 1) {
                listDate_line.clear();
                if (!this.wDataBase.tabbleIsExist(MySqliteModeHelper.W)) {
                    this.wDataBase.create(MySqliteModeHelper.W);
                    if (this.wDataBase.tabbleIsExist(MySqliteModeHelper.W)) {
                        insertWDataBase();
                    }
                }
                listDate_line.addAll(this.wDataBase.query(MySqliteModeHelper.W));
            } else if (i == 2) {
                listDate_line.clear();
                if (!this.bwDataBase.tabbleIsExist(MySqliteModeHelper.BW)) {
                    this.bwDataBase.create(MySqliteModeHelper.BW);
                    if (this.bwDataBase.tabbleIsExist(MySqliteModeHelper.BW)) {
                        insertBWDataBase();
                    }
                }
                listDate_line.addAll(this.bwDataBase.query(MySqliteModeHelper.BW));
            } else if (i == 3) {
                listDate_line.clear();
                listDate_line.addAll(this.rgbDataBase.query(MySqliteModeHelper.RGB));
            } else if (i == 4) {
                listDate_line.clear();
                listDate_line.addAll(this.rgbwDataBase.query(MySqliteModeHelper.RGBW));
            } else if (i == 5) {
                listDate_line.clear();
                listDate_line.addAll(this.rgbwcpDataBase.query(MySqliteModeHelper.RGBWCP));
            }
        } else if (table.equals("two")) {
            int i2 = lineshow;
            if (i2 == 1) {
                listDate_line.clear();
                if (!this.wDataBaseOne.tabbleIsExist(MySqliteModeHelper.WONE)) {
                    this.wDataBaseOne.create(MySqliteModeHelper.WONE);
                    if (this.wDataBaseOne.tabbleIsExist(MySqliteModeHelper.WONE)) {
                        insertWDataBaseOne();
                    }
                }
                listDate_line.addAll(this.wDataBaseOne.query(MySqliteModeHelper.WONE));
            } else if (i2 == 2) {
                listDate_line.clear();
                if (!this.bwDataBaseOne.tabbleIsExist(MySqliteModeHelper.BWONE)) {
                    this.bwDataBaseOne.create(MySqliteModeHelper.BWONE);
                    if (this.bwDataBaseOne.tabbleIsExist(MySqliteModeHelper.BWONE)) {
                        insertBWDataBaseOne();
                    }
                }
                listDate_line.addAll(this.bwDataBaseOne.query(MySqliteModeHelper.BWONE));
            } else if (i2 == 3) {
                listDate_line.clear();
                listDate_line.addAll(this.rgbDataBaseOne.query(MySqliteModeHelper.RGBONE));
            } else if (i2 == 4) {
                listDate_line.clear();
                listDate_line.addAll(this.rgbwDataBaseOne.query(MySqliteModeHelper.RGBWONE));
            } else if (i2 == 5) {
                listDate_line.clear();
                listDate_line.addAll(this.rgbwcpDataBaseOne.query(MySqliteModeHelper.RGBWCPONE));
            }
        }
        Utility.setListViewHeightBasedOnChildren(getContext(), this.mlistView);
        this.myAdapterTime_line.notifyDataSetChanged();
    }

    public void cle() {
        this.seekBarR.setProgress(0);
        this.seekBarG.setProgress(0);
        this.seekBarB.setProgress(0);
        TextView textView = this.tvg;
        textView.setText(String.valueOf(0) + "%");
        TextView textView2 = this.tvb;
        textView2.setText(String.valueOf(0) + "%");
        this.rl_r.getBackground().setAlpha(0);
        this.rl_b.getBackground().setAlpha(0);
        this.rl_g.getBackground().setAlpha(0);
        this.seekBarW.setProgress(0);
        TextView textView3 = this.tvw;
        textView3.setText(String.valueOf(0) + "%");
        this.rl_w.getBackground().setAlpha(0);
        this.skcyan.setProgress(0);
        TextView textView4 = this.tvcycn;
        textView4.setText(String.valueOf(0) + "%");
        this.rl_cyan.getBackground().setAlpha(0);
        this.skpink.setProgress(0);
        TextView textView5 = this.tvpink;
        textView5.setText(String.valueOf(0) + "%");
        this.rl_pink.getBackground().setAlpha(0);
        isChange = lineshow;
        this.lineChartView.clear();
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
        }
    }

    public void init() {
        String smartModeString = SharePersistent.getSmartModeString(this.mActivity, CommonConstant.SELECT_MODE_SMART_STRING);
        if (smartModeString.equalsIgnoreCase(ExifInterface.LONGITUDE_WEST)) {
            lineshow = 1;
            this.lineChartView.showLine(1);
            this.rl_r.setVisibility(8);
            this.rl_g.setVisibility(8);
            this.rl_b.setVisibility(8);
            this.rl_w.setVisibility(0);
            this.rl_cyan.setVisibility(8);
            this.rl_pink.setVisibility(8);
            this.rl_1.setVisibility(8);
            this.rl_2.setVisibility(8);
            this.rl_3.setVisibility(8);
            this.rl_4.setVisibility(0);
            this.rl_5.setVisibility(8);
            this.rl_6.setVisibility(8);
        } else if (smartModeString.equalsIgnoreCase("BW")) {
            lineshow = 2;
            this.lineChartView.showLine(2);
            this.rl_r.setVisibility(8);
            this.rl_g.setVisibility(8);
            this.rl_b.setVisibility(0);
            this.rl_w.setVisibility(0);
            this.rl_cyan.setVisibility(8);
            this.rl_pink.setVisibility(8);
            this.rl_1.setVisibility(8);
            this.rl_2.setVisibility(8);
            this.rl_3.setVisibility(0);
            this.rl_4.setVisibility(0);
            this.rl_5.setVisibility(8);
            this.rl_6.setVisibility(8);
        } else {
            this.rl_r.setVisibility(0);
            this.rl_g.setVisibility(0);
            this.rl_b.setVisibility(0);
            if (smartModeString.equalsIgnoreCase("RGB")) {
                lineshow = 3;
                this.lineChartView.showLine(3);
                this.rl_w.setVisibility(8);
                this.rl_cyan.setVisibility(8);
                this.rl_pink.setVisibility(8);
                this.rl_1.setVisibility(0);
                this.rl_2.setVisibility(0);
                this.rl_3.setVisibility(0);
                this.rl_4.setVisibility(8);
                this.rl_5.setVisibility(8);
                this.rl_6.setVisibility(8);
            } else if (smartModeString.equalsIgnoreCase("RGBW")) {
                lineshow = 4;
                this.lineChartView.showLine(4);
                this.rl_w.setVisibility(0);
                this.rl_cyan.setVisibility(8);
                this.rl_pink.setVisibility(8);
                this.rl_1.setVisibility(0);
                this.rl_2.setVisibility(0);
                this.rl_3.setVisibility(0);
                this.rl_4.setVisibility(0);
                this.rl_5.setVisibility(8);
                this.rl_6.setVisibility(8);
            } else if (smartModeString.equalsIgnoreCase("RGBWCP")) {
                lineshow = 5;
                this.lineChartView.showLine(5);
                this.rl_w.setVisibility(0);
                this.rl_cyan.setVisibility(0);
                this.rl_pink.setVisibility(0);
                this.rl_1.setVisibility(0);
                this.rl_2.setVisibility(0);
                this.rl_3.setVisibility(0);
                this.rl_4.setVisibility(0);
                this.rl_5.setVisibility(0);
                this.rl_6.setVisibility(0);
            }
        }
    }

    private void drawLine() {
        Viewport viewport = new Viewport(this.lineChartView.getMaximumViewport());
        viewport.bottom = 0.0f;
        viewport.f12top = 100.0f;
        viewport.left = 0.0f;
        viewport.right = 6.0f;
        this.lineChartView.setMaximumViewport(viewport);
        this.lineChartView.setCurrentViewport(viewport);
    }

    public void sendOne(int i) {
        Arrays.asList(getResources().getStringArray(R.array.week));
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("ss");
        int parseInt = Integer.parseInt(simpleDateFormat.format(date).trim());
        int parseInt2 = Integer.parseInt(simpleDateFormat2.format(date).trim());
        int parseInt3 = Integer.parseInt(simpleDateFormat3.format(date).trim());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String valueOf = String.valueOf(calendar.get(7));
        if ("1".equals(valueOf)) {
            this.day = 7;
        } else if (ExifInterface.GPS_MEASUREMENT_2D.equals(valueOf)) {
            this.day = 1;
        } else if (ExifInterface.GPS_MEASUREMENT_3D.equals(valueOf)) {
            this.day = 2;
        } else if ("4".equals(valueOf)) {
            this.day = 3;
        } else if ("5".equals(valueOf)) {
            this.day = 4;
        } else if ("6".equals(valueOf)) {
            this.day = 5;
        } else if ("7".equals(valueOf)) {
            this.day = 6;
        }
        MainActivity_BLE mainActivity_BLE = this.mActivity;
        int seletedOfWeek = getSeletedOfWeek(SharePersistent.getPerference(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + table));
        if (seletedOfWeek == 0) {
            Toast.makeText(this.mActivity, (int) R.string.choose, 0).show();
            return;
        }
        saveWeekValue(seletedOfWeek);
        this.mActivity.setTimeStart(i, this.style, (this.day << 5) | parseInt, parseInt2, parseInt3, seletedOfWeek);
    }

    public static String getWeekOfDate(Date date) {
        String[] strArr = {"7", "1", ExifInterface.GPS_MEASUREMENT_2D, ExifInterface.GPS_MEASUREMENT_3D, "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(7) - 1;
        if (i < 0) {
            i = 0;
        }
        return strArr[i];
    }

    public static int getSeletedOfWeek(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            if (str.contains("" + i)) {
                sb.append("1");
            } else {
                sb.append(NetResult.CODE_OK);
            }
        }
        return Integer.parseInt(sb.toString(), 2);
    }

    public void saveWeekValue(int i) {
        if (table.equals("one")) {
            int i2 = lineshow;
            if (i2 == 1) {
                putInt(getContext(), "onew", i);
            } else if (i2 == 2) {
                putInt(getContext(), "onebw", i);
            } else if (i2 == 3) {
                putInt(getContext(), "onergb", i);
            } else if (i2 == 4) {
                putInt(getContext(), "onergbw", i);
            } else if (i2 == 5) {
                putInt(getContext(), "onergbwcp", i);
            }
        } else if (table.equals("two")) {
            int i3 = lineshow;
            if (i3 == 1) {
                putInt(getContext(), "twow", i);
            } else if (i3 == 2) {
                putInt(getContext(), "twobw", i);
            } else if (i3 == 3) {
                putInt(getContext(), "tworgb", i);
            } else if (i3 == 4) {
                putInt(getContext(), "tworgbw", i);
            } else if (i3 == 5) {
                putInt(getContext(), "tworgbwcp", i);
            }
        }
    }

    private static SharedPreferences getSp(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("SpUtil", 0);
        }
        return sp;
    }

    public static void putInt(Context context, String str, int i) {
        SharedPreferences.Editor edit = getSp(context).edit();
        edit.putInt(str, i);
        edit.commit();
    }

    public static int getInt(Context context, String str, int i) {
        return getSp(context).getInt(str, i);
    }

    public int getWeekValue() {
        if (table.equals("one")) {
            int i = lineshow;
            if (i == 1) {
                return getInt(getContext(), "onew", 127);
            }
            if (i == 2) {
                return getInt(getContext(), "onebw", 127);
            }
            if (i == 3) {
                return getInt(getContext(), "onergb", 127);
            }
            if (i == 4) {
                return getInt(getContext(), "onergbw", 127);
            }
            if (i == 5) {
                return getInt(getContext(), "onergbwcp", 127);
            }
        } else if (table.equals("two")) {
            int i2 = lineshow;
            if (i2 == 1) {
                return getInt(getContext(), "twow", 127);
            }
            if (i2 == 2) {
                return getInt(getContext(), "twobw", 127);
            }
            if (i2 == 3) {
                return getInt(getContext(), "tworgb", 127);
            }
            if (i2 == 4) {
                return getInt(getContext(), "tworgbw", 127);
            }
            if (i2 == 5) {
                return getInt(getContext(), "tworgbwcp", 127);
            }
        }
        return -1;
    }

    public void sqlclear() {
        if (table.equals("one")) {
            int i = lineshow;
            if (i == 1) {
                listDate_line.clear();
                this.wDataBase.deleteDatebase(MySqliteModeHelper.W);
            } else if (i == 2) {
                listDate_line.clear();
                this.bwDataBase.deleteDatebase(MySqliteModeHelper.BW);
            } else if (i == 3) {
                listDate_line.clear();
                this.rgbDataBase.deleteDatebase(MySqliteModeHelper.RGB);
            } else if (i == 4) {
                listDate_line.clear();
                this.rgbwDataBase.deleteDatebase(MySqliteModeHelper.RGBW);
            } else if (i == 5) {
                listDate_line.clear();
                this.rgbwcpDataBase.deleteDatebase(MySqliteModeHelper.RGBWCP);
            }
        } else if (table.equals("two")) {
            int i2 = lineshow;
            if (i2 == 1) {
                listDate_line.clear();
                this.wDataBaseOne.deleteDatebase(MySqliteModeHelper.WONE);
            } else if (i2 == 2) {
                listDate_line.clear();
                this.bwDataBaseOne.deleteDatebase(MySqliteModeHelper.BWONE);
            } else if (i2 == 3) {
                listDate_line.clear();
                this.rgbDataBaseOne.deleteDatebase(MySqliteModeHelper.RGBONE);
            } else if (i2 == 4) {
                listDate_line.clear();
                this.rgbwDataBaseOne.deleteDatebase(MySqliteModeHelper.RGBWONE);
            } else if (i2 == 5) {
                listDate_line.clear();
                this.rgbwcpDataBaseOne.deleteDatebase(MySqliteModeHelper.RGBWCPONE);
            }
        }
        Utility.setListViewHeightBasedOnChildren(getContext(), this.mlistView);
        this.myAdapterTime_line.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 113) {
            reloadChartView();
            Utility.setListViewHeightBasedOnChildren(getContext(), this.mlistView);
            this.myAdapterTime_line.notifyDataSetChanged();
            update();
        }
    }

    public void gotoTimerSettting(int i, TimeModle timeModle) {
        Intent intent = new Intent(this.mActivity, TimerSettingActivity_Smart.class);
        intent.putExtra("tag", i);
        intent.putExtra("data", timeModle);
        intent.putExtra("666", 666);
        startActivityForResult(intent, 113);
    }

    public void sendNowTime() {
        Date date = new Date(System.currentTimeMillis());
        if (isAdded()) {
            List asList = Arrays.asList(getResources().getStringArray(R.array.week));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm");
            SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("ss");
            String format = new SimpleDateFormat(ExifInterface.LONGITUDE_EAST).format(date);
            if (format.contains((CharSequence) asList.get(0))) {
                this.day = 1;
            } else if (format.contains((CharSequence) asList.get(1))) {
                this.day = 2;
            } else if (format.contains((CharSequence) asList.get(2))) {
                this.day = 3;
            } else if (format.contains((CharSequence) asList.get(3))) {
                this.day = 4;
            } else if (format.contains((CharSequence) asList.get(4))) {
                this.day = 5;
            } else if (format.contains((CharSequence) asList.get(5))) {
                this.day = 6;
            } else if (format.contains((CharSequence) asList.get(6))) {
                this.day = 7;
            }
            int parseInt = Integer.parseInt(simpleDateFormat.format(date).trim());
            int parseInt2 = Integer.parseInt(simpleDateFormat2.format(date).trim());
            int parseInt3 = Integer.parseInt(simpleDateFormat3.format(date).trim());
            for (int i = 0; i < 2; i++) {
                this.mActivity.settingTime(parseInt, parseInt2, parseInt3, this.day);
            }
        }
    }

    public void runNow() {
        MainActivity_BLE mainActivity_BLE = this.mActivity;
        if (getSeletedOfWeek(SharePersistent.getPerference(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + table)) == 0) {
            Toast.makeText(getContext(), (int) R.string.choose, 0).show();
            return;
        }
        reloadChartView();
        LineChartView.isChange = false;
        this.stare = false;
        sendOne(this.lineChartView.getTimeSize(lineshow));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() { // from class: com.home.fragment.smart.LineFragment.5
            @Override // java.lang.Runnable
            public void run() {
                if (LineFragment.this.idx >= LineFragment.listDate_line.size()) {
                    LineFragment.this.idx = 0;
                    LineFragment.this.sendNowTime();
                    LineFragment.this.dialogdismiss();
                    handler.removeCallbacks(this);
                    return;
                }
                TimeModle timeModle = LineFragment.listDate_line.get(LineFragment.this.idx);
                if (LineFragment.lineshow == 1) {
                    LineFragment.this.mActivity.setTimerSecData(new int[]{timeModle.getHour(), timeModle.getMinute(), 0, 0, 0, timeModle.getWhite(), 0, 0});
                } else if (LineFragment.lineshow == 2) {
                    LineFragment.this.mActivity.setTimerSecData(new int[]{timeModle.getHour(), timeModle.getMinute(), 0, 0, timeModle.getBlude(), timeModle.getWhite(), 0, 0});
                } else if (LineFragment.lineshow == 3) {
                    LineFragment.this.mActivity.setTimerSecData(new int[]{timeModle.getHour(), timeModle.getMinute(), timeModle.getRed(), timeModle.getGreen(), timeModle.getBlude(), 0, 0, 0});
                } else if (LineFragment.lineshow == 4) {
                    LineFragment.this.mActivity.setTimerSecData(new int[]{timeModle.getHour(), timeModle.getMinute(), timeModle.getRed(), timeModle.getGreen(), timeModle.getBlude(), timeModle.getWhite(), 0, 0});
                } else if (LineFragment.lineshow == 5) {
                    LineFragment.this.mActivity.setTimerSecData(new int[]{timeModle.getHour(), timeModle.getMinute(), timeModle.getRed(), timeModle.getGreen(), timeModle.getBlude(), timeModle.getWhite(), timeModle.getCyan(), timeModle.getViolet()});
                }
                LineFragment.this.idx++;
                handler.postDelayed(this, 300L);
            }
        }, 300L);
        LineChartView.isChange = true;
        this.stare = true;
    }

    public void reloadChartView() {
        int i = 0;
        LineChartView.isChange = false;
        this.stare = false;
        if (table.equals("one")) {
            int i2 = lineshow;
            if (i2 == 1) {
                this.wDataBase.deleteDatebase(MySqliteModeHelper.W);
                while (i < listDate_line.size()) {
                    WDataBase.getInstance(getContext()).insert(MySqliteModeHelper.W, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude(), listDate_line.get(i).getWhite());
                    i++;
                }
            } else if (i2 == 2) {
                this.bwDataBase.deleteDatebase(MySqliteModeHelper.BW);
                while (i < listDate_line.size()) {
                    BWDataBase.getInstance(getContext()).insert(MySqliteModeHelper.BW, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude(), listDate_line.get(i).getWhite());
                    i++;
                }
            } else if (i2 == 3) {
                this.rgbDataBase.deleteDatebase(MySqliteModeHelper.RGB);
                while (i < listDate_line.size()) {
                    RGBDataBase.getInstance(getContext()).insert(MySqliteModeHelper.RGB, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude());
                    i++;
                }
            } else if (i2 == 4) {
                this.rgbwDataBase.deleteDatebase(MySqliteModeHelper.RGBW);
                while (i < listDate_line.size()) {
                    RGBWDataBase.getInstance(getContext()).insert(MySqliteModeHelper.RGBW, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude(), listDate_line.get(i).getWhite());
                    i++;
                }
            } else if (i2 == 5) {
                this.rgbwcpDataBase.deleteDatebase(MySqliteModeHelper.RGBWCP);
                while (i < listDate_line.size()) {
                    RGBWCPDataBase.getInstance(getContext()).insert(MySqliteModeHelper.RGBWCP, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude(), listDate_line.get(i).getWhite(), listDate_line.get(i).getCyan(), listDate_line.get(i).getViolet());
                    i++;
                }
            }
        } else if (table.equals("two")) {
            int i3 = lineshow;
            if (i3 == 1) {
                this.wDataBaseOne.deleteDatebase(MySqliteModeHelper.WONE);
                while (i < listDate_line.size()) {
                    WDataBaseOne.getInstance(getContext()).insert(MySqliteModeHelper.WONE, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude(), listDate_line.get(i).getWhite());
                    i++;
                }
            } else if (i3 == 2) {
                this.bwDataBaseOne.deleteDatebase(MySqliteModeHelper.BWONE);
                while (i < listDate_line.size()) {
                    BWDataBaseOne.getInstance(getContext()).insert(MySqliteModeHelper.BWONE, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude(), listDate_line.get(i).getWhite());
                    i++;
                }
            } else if (i3 == 3) {
                this.rgbDataBaseOne.deleteDatebase(MySqliteModeHelper.RGBONE);
                while (i < listDate_line.size()) {
                    RGBDataBaseOne.getInstance(getContext()).insert(MySqliteModeHelper.RGBONE, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude());
                    i++;
                }
            } else if (i3 == 4) {
                this.rgbwDataBaseOne.deleteDatebase(MySqliteModeHelper.RGBWONE);
                while (i < listDate_line.size()) {
                    RGBWDataBaseOne.getInstance(getContext()).insert(MySqliteModeHelper.RGBWONE, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude(), listDate_line.get(i).getWhite());
                    i++;
                }
            } else if (i3 == 5) {
                this.rgbwcpDataBaseOne.deleteDatebase(MySqliteModeHelper.RGBWCPONE);
                while (i < listDate_line.size()) {
                    RGBWCPDataBaseOne.getInstance(getContext()).insert(MySqliteModeHelper.RGBWCPONE, listDate_line.get(i).getHour(), listDate_line.get(i).getMinute(), listDate_line.get(i).getRed(), listDate_line.get(i).getGreen(), listDate_line.get(i).getBlude(), listDate_line.get(i).getWhite(), listDate_line.get(i).getCyan(), listDate_line.get(i).getViolet());
                    i++;
                }
            }
        }
        LineChartView.isChange = true;
        this.stare = true;
    }

    public void changed() {
        this.mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.home.fragment.smart.LineFragment.6
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (LineChartView.isChange) {
                    LineFragment.this.gotoTimerSettting(i, LineFragment.listDate_line.get(i));
                }
            }
        });
        this.mlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.home.fragment.smart.LineFragment.7
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long j) {
                if (i != 0) {
                    if (LineFragment.this.isAdded()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LineFragment.this.getContext());
                        builder.setTitle(R.string.tips);
                        builder.setMessage(R.string.timer);
                        builder.setPositiveButton(R.string.bind_end, new DialogInterface.OnClickListener() { // from class: com.home.fragment.smart.LineFragment.7.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i2) {
                                if (LineFragment.listDate_line.size() == 2) {
                                    LineFragment.this.seekBarR.setProgress(0);
                                    LineFragment.this.seekBarG.setProgress(0);
                                    LineFragment.this.seekBarB.setProgress(0);
                                    TextView textView = LineFragment.this.tvr;
                                    textView.setText(String.valueOf(0) + "%");
                                    TextView textView2 = LineFragment.this.tvg;
                                    textView2.setText(String.valueOf(0) + "%");
                                    TextView textView3 = LineFragment.this.tvb;
                                    textView3.setText(String.valueOf(0) + "%");
                                    LineFragment.this.rl_r.getBackground().setAlpha(0);
                                    LineFragment.this.rl_b.getBackground().setAlpha(0);
                                    LineFragment.this.rl_g.getBackground().setAlpha(0);
                                    if (LineFragment.lineshow == 4 || LineFragment.lineshow == 5) {
                                        LineFragment.this.seekBarW.setProgress(0);
                                        TextView textView4 = LineFragment.this.tvw;
                                        textView4.setText(String.valueOf(0) + "%");
                                        LineFragment.this.rl_w.getBackground().setAlpha(0);
                                    }
                                    if (LineFragment.lineshow == 5) {
                                        LineFragment.this.skcyan.setProgress(0);
                                        TextView textView5 = LineFragment.this.tvcycn;
                                        textView5.setText(String.valueOf(0) + "%");
                                        LineFragment.this.rl_cyan.getBackground().setAlpha(0);
                                        LineFragment.this.skpink.setProgress(0);
                                        TextView textView6 = LineFragment.this.tvpink;
                                        textView6.setText(String.valueOf(0) + "%");
                                        LineFragment.this.rl_pink.getBackground().setAlpha(0);
                                    }
                                    LineFragment.listDate_line.clear();
                                    Utility.setListViewHeightBasedOnChildren(LineFragment.this.getContext(), LineFragment.this.mlistView);
                                    LineFragment.this.myAdapterTime_line.notifyDataSetChanged();
                                    LineFragment.this.lineChartView.clear();
                                    return;
                                }
                                LineFragment.listDate_line.remove(i);
                                LineFragment.this.update();
                            }
                        });
                        builder.setNegativeButton(R.string.cancell_dialog, (DialogInterface.OnClickListener) null).show();
                        return true;
                    }
                    return false;
                }
                return true;
            }
        });
        this.lineChartView.setOnTouchListener(new View.OnTouchListener() { // from class: com.home.fragment.smart.LineFragment.8
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                LineFragment.this.scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        this.tvLook.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.smart.LineFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(LineFragment.this.getActivity(), R.anim.layout_scale));
                if (LineChartView.isGone && LineFragment.this.lineChartView.getTimeSize(LineFragment.lineshow) != 0 && LineFragment.this.stare) {
                    LineFragment.this.showCustomMessage(2);
                    LineChartView.isChange = false;
                    LineFragment.this.stare = false;
                    LineFragment.this.size = 0;
                    if (LineFragment.this.timer == null) {
                        LineFragment.this.timer = new Timer();
                    } else {
                        LineFragment.this.timer = null;
                        LineFragment.this.timer = new Timer();
                    }
                    LineFragment.this.timer.schedule(new TimerTask() { // from class: com.home.fragment.smart.LineFragment.9.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            LineFragment.this.conectHandler.sendEmptyMessage(PointerIconCompat.TYPE_CONTEXT_MENU);
                        }
                    }, 0L, 100L);
                }
            }
        });
        this.tvReset.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.smart.LineFragment.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(LineFragment.this.getActivity(), R.anim.layout_scale));
                AlertDialog.Builder builder = new AlertDialog.Builder(LineFragment.this.getContext());
                builder.setTitle(LineFragment.this.getResources().getString(R.string.tips));
                builder.setMessage(LineFragment.this.getResources().getString(R.string.reset_message));
                builder.setNegativeButton(LineFragment.this.getResources().getString(R.string.text_cancel), new DialogInterface.OnClickListener() { // from class: com.home.fragment.smart.LineFragment.10.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton(LineFragment.this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.home.fragment.smart.LineFragment.10.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LineFragment.this.seekBarR.setProgress(0);
                        LineFragment.this.seekBarG.setProgress(0);
                        LineFragment.this.seekBarB.setProgress(0);
                        MainActivity_BLE mainActivity_BLE = LineFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarR, 0);
                        MainActivity_BLE mainActivity_BLE2 = LineFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarG, 0);
                        MainActivity_BLE mainActivity_BLE3 = LineFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarB, 0);
                        TextView textView = LineFragment.this.tvr;
                        StringBuilder sb = new StringBuilder();
                        sb.append(String.valueOf(0));
                        sb.append("%");
                        textView.setText(sb.toString());
                        TextView textView2 = LineFragment.this.tvg;
                        textView2.setText(String.valueOf(0) + "%");
                        TextView textView3 = LineFragment.this.tvb;
                        textView3.setText(String.valueOf(0) + "%");
                        LineFragment.this.rl_r.getBackground().setAlpha(0);
                        LineFragment.this.rl_b.getBackground().setAlpha(0);
                        LineFragment.this.rl_g.getBackground().setAlpha(0);
                        LineFragment.this.saveWeekValue(127);
                        if (LineFragment.lineshow == 4 || LineFragment.lineshow == 5) {
                            LineFragment.this.seekBarW.setProgress(0);
                            TextView textView4 = LineFragment.this.tvw;
                            textView4.setText(String.valueOf(0) + "%");
                            LineFragment.this.rl_w.getBackground().setAlpha(0);
                            MainActivity_BLE mainActivity_BLE4 = LineFragment.this.mActivity;
                            SharePersistent.saveInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarW, 0);
                        }
                        if (LineFragment.lineshow == 5) {
                            LineFragment.this.skcyan.setProgress(0);
                            TextView textView5 = LineFragment.this.tvcycn;
                            textView5.setText(String.valueOf(0) + "%");
                            LineFragment.this.rl_cyan.getBackground().setAlpha(0);
                            LineFragment.this.skpink.setProgress(0);
                            TextView textView6 = LineFragment.this.tvpink;
                            textView6.setText(String.valueOf(0) + "%");
                            LineFragment.this.rl_pink.getBackground().setAlpha(0);
                            MainActivity_BLE mainActivity_BLE5 = LineFragment.this.mActivity;
                            SharePersistent.saveInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarC, 0);
                            MainActivity_BLE mainActivity_BLE6 = LineFragment.this.mActivity;
                            SharePersistent.saveInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarP, 0);
                        }
                        if (LineFragment.this.timer != null) {
                            LineFragment.this.timer.cancel();
                        }
                        LineFragment.this.lineChartView.clear();
                        LineFragment.this.sqlclear();
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        this.tvGradient.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.smart.LineFragment.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(LineFragment.this.getActivity(), R.anim.layout_scale));
                LineFragment.this.style = 0;
                LineFragment.this.radioClick();
            }
        });
        this.tvJump.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.smart.LineFragment.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(LineFragment.this.getActivity(), R.anim.layout_scale));
                LineFragment.this.style = 1;
                LineFragment.this.radioClick();
            }
        });
        this.seekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.home.fragment.smart.LineFragment.13
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextView textView = LineFragment.this.tvr;
                textView.setText(String.valueOf(i) + "%");
                LineFragment.this.rl_r.getBackground().setAlpha(255);
                LineFragment.this.rl_b.getBackground().setAlpha(0);
                LineFragment.this.rl_g.getBackground().setAlpha(0);
                LineFragment.this.rl_w.getBackground().setAlpha(0);
                LineFragment.this.rl_cyan.getBackground().setAlpha(0);
                LineFragment.this.rl_pink.getBackground().setAlpha(0);
                if (z) {
                    LineChartView.isGone = true;
                    LineFragment.this.lineChartView.setPaint(1);
                    LineFragment.this.lineChartView.setSize(i);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE = LineFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarR, i);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LineFragment.this.reloadChartView();
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE = this.mActivity;
            int i = SharePersistent.getInt(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + table + SeekBarR);
            if (i > 0) {
                this.seekBarR.setProgress(i);
                TextView textView = this.tvr;
                textView.setText(String.valueOf(i) + "%");
            } else {
                this.seekBarR.setProgress(0);
                TextView textView2 = this.tvr;
                textView2.setText(String.valueOf(0) + "%");
            }
        }
        this.seekBarG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.home.fragment.smart.LineFragment.14
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                TextView textView3 = LineFragment.this.tvg;
                textView3.setText(String.valueOf(i2) + "%");
                LineFragment.this.rl_r.getBackground().setAlpha(0);
                LineFragment.this.rl_b.getBackground().setAlpha(0);
                LineFragment.this.rl_g.getBackground().setAlpha(255);
                LineFragment.this.rl_w.getBackground().setAlpha(0);
                LineFragment.this.rl_cyan.getBackground().setAlpha(0);
                LineFragment.this.rl_pink.getBackground().setAlpha(0);
                LineChartView.isGone = true;
                LineFragment.this.lineChartView.setPaint(2);
                LineFragment.this.lineChartView.setSize(i2);
                if (MainActivity_BLE.sceneBean != null) {
                    MainActivity_BLE mainActivity_BLE2 = LineFragment.this.mActivity;
                    SharePersistent.saveInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarG, i2);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LineFragment.this.reloadChartView();
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE2 = this.mActivity;
            int i2 = SharePersistent.getInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + TAG + table + SeekBarG);
            if (i2 > 0) {
                this.seekBarG.setProgress(i2);
                TextView textView3 = this.tvg;
                textView3.setText(String.valueOf(i2) + "%");
            } else {
                this.seekBarG.setProgress(0);
                TextView textView4 = this.tvg;
                textView4.setText(String.valueOf(0) + "%");
            }
        }
        this.seekBarB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.home.fragment.smart.LineFragment.15
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                TextView textView5 = LineFragment.this.tvb;
                textView5.setText(String.valueOf(i3) + "%");
                LineFragment.this.rl_r.getBackground().setAlpha(0);
                LineFragment.this.rl_b.getBackground().setAlpha(255);
                LineFragment.this.rl_g.getBackground().setAlpha(0);
                LineFragment.this.rl_w.getBackground().setAlpha(0);
                LineFragment.this.rl_cyan.getBackground().setAlpha(0);
                LineFragment.this.rl_pink.getBackground().setAlpha(0);
                LineChartView.isGone = true;
                LineFragment.this.lineChartView.setPaint(3);
                LineFragment.this.lineChartView.setSize(i3);
                if (MainActivity_BLE.sceneBean != null) {
                    MainActivity_BLE mainActivity_BLE3 = LineFragment.this.mActivity;
                    SharePersistent.saveInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarB, i3);
                    Log.e(LineFragment.TAG, "table = " + LineFragment.table + ",  bValue = " + i3);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LineFragment.this.reloadChartView();
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE3 = this.mActivity;
            int i3 = SharePersistent.getInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + TAG + table + SeekBarB);
            if (i3 > 0) {
                this.seekBarB.setProgress(i3);
                TextView textView5 = this.tvb;
                textView5.setText(String.valueOf(i3) + "%");
            } else {
                this.seekBarB.setProgress(0);
                TextView textView6 = this.tvb;
                textView6.setText(String.valueOf(0) + "%");
            }
        }
        this.seekBarW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.home.fragment.smart.LineFragment.16
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i4, boolean z) {
                TextView textView7 = LineFragment.this.tvw;
                textView7.setText(String.valueOf(i4) + "%");
                LineFragment.this.rl_r.getBackground().setAlpha(0);
                LineFragment.this.rl_b.getBackground().setAlpha(0);
                LineFragment.this.rl_g.getBackground().setAlpha(0);
                LineFragment.this.rl_w.getBackground().setAlpha(255);
                LineFragment.this.rl_cyan.getBackground().setAlpha(0);
                LineFragment.this.rl_pink.getBackground().setAlpha(0);
                LineChartView.isGone = true;
                LineFragment.this.lineChartView.setPaint(4);
                LineFragment.this.lineChartView.setSize(i4);
                if (MainActivity_BLE.sceneBean != null) {
                    MainActivity_BLE mainActivity_BLE4 = LineFragment.this.mActivity;
                    SharePersistent.saveInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarW, i4);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LineFragment.this.reloadChartView();
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE4 = this.mActivity;
            int i4 = SharePersistent.getInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + TAG + table + SeekBarW);
            if (i4 > 0) {
                this.seekBarW.setProgress(i4);
                TextView textView7 = this.tvw;
                textView7.setText(String.valueOf(i4) + "%");
            } else {
                this.seekBarW.setProgress(0);
                TextView textView8 = this.tvw;
                textView8.setText(String.valueOf(0) + "%");
            }
        }
        this.skcyan.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.home.fragment.smart.LineFragment.17
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i5, boolean z) {
                TextView textView9 = LineFragment.this.tvcycn;
                textView9.setText(String.valueOf(i5) + "%");
                LineFragment.this.rl_r.getBackground().setAlpha(0);
                LineFragment.this.rl_b.getBackground().setAlpha(0);
                LineFragment.this.rl_g.getBackground().setAlpha(0);
                LineFragment.this.rl_w.getBackground().setAlpha(0);
                LineFragment.this.rl_cyan.getBackground().setAlpha(255);
                LineFragment.this.rl_pink.getBackground().setAlpha(0);
                LineChartView.isGone = true;
                LineFragment.this.lineChartView.setPaint(5);
                LineFragment.this.lineChartView.setSize(i5);
                if (MainActivity_BLE.sceneBean != null) {
                    MainActivity_BLE mainActivity_BLE5 = LineFragment.this.mActivity;
                    SharePersistent.saveInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarC, i5);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LineFragment.this.reloadChartView();
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE5 = this.mActivity;
            int i5 = SharePersistent.getInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + TAG + table + SeekBarC);
            if (i5 > 0) {
                this.skcyan.setProgress(i5);
                TextView textView9 = this.tvcycn;
                textView9.setText(String.valueOf(i5) + "%");
            } else {
                this.skcyan.setProgress(0);
                TextView textView10 = this.tvcycn;
                textView10.setText(String.valueOf(0) + "%");
            }
        }
        this.skpink.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.home.fragment.smart.LineFragment.18
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i6, boolean z) {
                TextView textView11 = LineFragment.this.tvpink;
                textView11.setText(String.valueOf(i6) + "%");
                LineFragment.this.rl_r.getBackground().setAlpha(0);
                LineFragment.this.rl_b.getBackground().setAlpha(0);
                LineFragment.this.rl_g.getBackground().setAlpha(0);
                LineFragment.this.rl_w.getBackground().setAlpha(0);
                LineFragment.this.rl_cyan.getBackground().setAlpha(0);
                LineFragment.this.rl_pink.getBackground().setAlpha(255);
                LineChartView.isGone = true;
                LineFragment.this.lineChartView.setPaint(6);
                LineFragment.this.lineChartView.setSize(i6);
                if (MainActivity_BLE.sceneBean != null) {
                    MainActivity_BLE mainActivity_BLE6 = LineFragment.this.mActivity;
                    SharePersistent.saveInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + LineFragment.TAG + LineFragment.table + LineFragment.SeekBarP, i6);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LineFragment.this.reloadChartView();
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE6 = this.mActivity;
            int i6 = SharePersistent.getInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + TAG + table + SeekBarP);
            if (i6 > 0) {
                this.skpink.setProgress(i6);
                TextView textView11 = this.tvpink;
                textView11.setText(String.valueOf(i6) + "%");
                return;
            }
            this.skpink.setProgress(0);
            TextView textView12 = this.tvpink;
            textView12.setText(String.valueOf(0) + "%");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dialogdismiss() {
        try {
            Dialog dialog = this.dialog;
            if (dialog != null && dialog.isShowing()) {
                this.dialog.dismiss();
            }
        } catch (IllegalArgumentException | Exception unused) {
        } catch (Throwable th) {
            this.dialog = null;
            throw th;
        }
        this.dialog = null;
    }

    @Override // com.example.linechartlibrary.LineChartView.ReturnValueCallback
    public void returnValue(int i, boolean z) {
        if (z) {
            reloadChartView();
        }
        if (i > 100) {
            i = 100;
        } else if (i < 0) {
            i = 0;
        }
        if (this.lineChartView.getPaint() == 1) {
            this.mActivity.setSmartBrightness(0, i);
        } else if (this.lineChartView.getPaint() == 2) {
            this.mActivity.setSmartBrightness(1, i);
        } else if (this.lineChartView.getPaint() == 3) {
            this.mActivity.setSmartBrightness(2, i);
        } else if (this.lineChartView.getPaint() == 4) {
            this.mActivity.setSmartBrightness(3, i);
        } else if (this.lineChartView.getPaint() == 5) {
            this.mActivity.setSmartBrightness(4, i);
        } else if (this.lineChartView.getPaint() == 6) {
            this.mActivity.setSmartBrightness(5, i);
        } else {
            Log.e(TAG, "returnValue: ");
        }
    }

    @Override // com.example.linechartlibrary.LineChartView.SendCallBack
    public void sendValue(int[] iArr) {
        int i = lineshow;
        if (i == 1) {
            listDate_line.add(new TimeModle(iArr[0], iArr[1], 0, 0, 0, iArr[5]));
        } else if (i == 2) {
            listDate_line.add(new TimeModle(iArr[0], iArr[1], 0, 0, iArr[4], iArr[5]));
        } else if (i == 3) {
            listDate_line.add(new TimeModle(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]));
        } else if (i == 4) {
            listDate_line.add(new TimeModle(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5]));
        } else if (i == 5) {
            listDate_line.add(new TimeModle(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6], iArr[7]));
        }
        this.conectHandler.sendEmptyMessage(1000);
    }

    @Override // com.example.linechartlibrary.LineChartView.SendCallBack
    public void clear() {
        listClear();
    }

    public void listClear() {
        listDate_line.clear();
    }

    @Override // com.example.linechartlibrary.LineChartView.ReturnValueCallback
    public void update() {
        this.lineChartView.updatePoint(lineshow);
        boolean z = true;
        boolean z2 = true;
        boolean z3 = true;
        boolean z4 = true;
        boolean z5 = true;
        boolean z6 = true;
        for (int i = 0; i < listDate_line.size(); i++) {
            int i2 = lineshow;
            if (i2 == 1) {
                if (listDate_line.get(i).getWhite() >= 0 && z) {
                    this.lineChartView.addPoint(4);
                    z = false;
                }
            } else {
                if (i2 == 2) {
                    if (listDate_line.get(i).getBlude() >= 0 && z3) {
                        this.lineChartView.addPoint(3);
                        z3 = false;
                    }
                    if (listDate_line.get(i).getWhite() >= 0 && z) {
                        this.lineChartView.addPoint(4);
                        z = false;
                    }
                } else {
                    if (listDate_line.get(i).getRed() >= 0 && z2) {
                        this.lineChartView.addPoint(1);
                        z2 = false;
                    }
                    if (listDate_line.get(i).getGreen() >= 0 && z4) {
                        this.lineChartView.addPoint(2);
                        z4 = false;
                    }
                    if (listDate_line.get(i).getBlude() >= 0 && z3) {
                        this.lineChartView.addPoint(3);
                        z3 = false;
                    }
                    int i3 = lineshow;
                    if ((i3 == 4 || i3 == 5) && listDate_line.get(i).getWhite() >= 0 && z) {
                        this.lineChartView.addPoint(4);
                        z = false;
                    }
                    if (lineshow == 5) {
                        if (listDate_line.get(i).getCyan() >= 0 && z5) {
                            this.lineChartView.addPoint(5);
                            z5 = false;
                        }
                        if (listDate_line.get(i).getViolet() >= 0 && z6) {
                            this.lineChartView.addPoint(6);
                            z6 = false;
                        }
                    }
                }
            }
        }
        for (int i4 = 0; i4 < listDate_line.size(); i4++) {
            int i5 = lineshow;
            if (i5 == 1) {
                this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getWhite(), 4);
            } else if (i5 == 2) {
                this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getBlude(), 3);
                this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getWhite(), 4);
            } else {
                this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getRed(), 1);
                this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getGreen(), 2);
                this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getBlude(), 3);
                int i6 = lineshow;
                if (i6 == 4 || i6 == 5) {
                    this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getWhite(), 4);
                }
                if (lineshow == 5) {
                    this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getCyan(), 5);
                    this.lineChartView.setPoint(listDate_line.get(i4).getHour(), listDate_line.get(i4).getMinute(), listDate_line.get(i4).getViolet(), 6);
                }
            }
        }
        Utility.setListViewHeightBasedOnChildren(getContext(), this.mlistView);
        this.myAdapterTime_line.notifyDataSetChanged();
    }

    /* loaded from: classes.dex */
    class MyHolder {
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;
        TextView tv_6;
        TextView tv_7;
        TextView tv_8;

        MyHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyAdapterTime_line extends BaseAdapter {
        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }

        MyAdapterTime_line() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return LineFragment.listDate_line.size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            MyHolder myHolder;
            TimeModle timeModle = LineFragment.listDate_line.get(i);
            if (view == null) {
                myHolder = new MyHolder();
                view2 = LayoutInflater.from(LineFragment.this.getContext()).inflate(R.layout.item, (ViewGroup) null);
                myHolder.tv_1 = (TextView) view2.findViewById(R.id.tv1);
                myHolder.tv_2 = (TextView) view2.findViewById(R.id.tv2);
                myHolder.tv_3 = (TextView) view2.findViewById(R.id.tv3);
                myHolder.tv_4 = (TextView) view2.findViewById(R.id.tv4);
                myHolder.tv_5 = (TextView) view2.findViewById(R.id.tv5);
                myHolder.tv_6 = (TextView) view2.findViewById(R.id.tv6);
                myHolder.tv_7 = (TextView) view2.findViewById(R.id.tv7);
                myHolder.tv_8 = (TextView) view2.findViewById(R.id.tv8);
                view2.setTag(myHolder);
            } else {
                view2 = view;
                myHolder = (MyHolder) view.getTag();
            }
            if (timeModle.getMinute() < 10 && timeModle.getHour() < 10) {
                TextView textView = myHolder.tv_2;
                textView.setText(NetResult.CODE_OK + timeModle.getHour() + ":0" + timeModle.getMinute());
            } else if (timeModle.getHour() < 10) {
                TextView textView2 = myHolder.tv_2;
                textView2.setText(NetResult.CODE_OK + timeModle.getHour() + ":" + timeModle.getMinute());
            } else if (timeModle.getMinute() < 10) {
                TextView textView3 = myHolder.tv_2;
                textView3.setText(timeModle.getHour() + ":0" + timeModle.getMinute());
            } else {
                TextView textView4 = myHolder.tv_2;
                textView4.setText(timeModle.getHour() + ":" + timeModle.getMinute());
            }
            if (LineFragment.lineshow == 1) {
                myHolder.tv_3.setVisibility(8);
                myHolder.tv_4.setVisibility(8);
                myHolder.tv_5.setVisibility(8);
                TextView textView5 = myHolder.tv_6;
                textView5.setText("W " + timeModle.getWhite() + "");
                myHolder.tv_7.setVisibility(8);
                myHolder.tv_8.setVisibility(8);
            } else if (LineFragment.lineshow == 2) {
                myHolder.tv_3.setVisibility(8);
                myHolder.tv_4.setVisibility(8);
                TextView textView6 = myHolder.tv_5;
                textView6.setText("B " + timeModle.getBlude() + "");
                TextView textView7 = myHolder.tv_6;
                textView7.setText("W " + timeModle.getWhite() + "");
                myHolder.tv_7.setVisibility(8);
                myHolder.tv_8.setVisibility(8);
            } else if (LineFragment.lineshow == 3) {
                TextView textView8 = myHolder.tv_3;
                textView8.setText("R " + timeModle.getRed() + "");
                TextView textView9 = myHolder.tv_4;
                textView9.setText("G " + timeModle.getGreen() + "");
                TextView textView10 = myHolder.tv_5;
                textView10.setText("B " + timeModle.getBlude() + "");
                myHolder.tv_6.setVisibility(8);
                myHolder.tv_7.setVisibility(8);
                myHolder.tv_8.setVisibility(8);
            } else if (LineFragment.lineshow == 4) {
                TextView textView11 = myHolder.tv_3;
                textView11.setText("R " + timeModle.getRed() + "");
                TextView textView12 = myHolder.tv_4;
                textView12.setText("G " + timeModle.getGreen() + "");
                TextView textView13 = myHolder.tv_5;
                textView13.setText("B " + timeModle.getBlude() + "");
                TextView textView14 = myHolder.tv_6;
                textView14.setText("W " + timeModle.getWhite() + "");
                myHolder.tv_7.setVisibility(8);
                myHolder.tv_8.setVisibility(8);
            } else if (LineFragment.lineshow == 5) {
                TextView textView15 = myHolder.tv_3;
                textView15.setText("R " + timeModle.getRed() + "");
                TextView textView16 = myHolder.tv_4;
                textView16.setText("G " + timeModle.getGreen() + "");
                TextView textView17 = myHolder.tv_5;
                textView17.setText("B " + timeModle.getBlude() + "");
                TextView textView18 = myHolder.tv_6;
                textView18.setText("W " + timeModle.getWhite() + "");
                TextView textView19 = myHolder.tv_7;
                textView19.setText("C " + timeModle.getCyan() + "");
                TextView textView20 = myHolder.tv_8;
                textView20.setText("P " + timeModle.getViolet() + "");
            }
            return view2;
        }
    }
}
