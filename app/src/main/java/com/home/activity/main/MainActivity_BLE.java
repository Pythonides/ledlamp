package com.home.activity.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.core.media.session.PlaybackStateCompat;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import com.FirstActivity;
import com.common.gps.GPSPresenter;
import com.common.gps.GPS_Interface;
import com.common.listener.IListener;
import com.common.listener.ListenerManager;
import com.common.net.NetResult;
import com.common.task.BaseTask;
import com.common.task.NetCallBack;
import com.common.uitl.ListUtiles;
import com.common.uitl.LogUtil;
import com.common.uitl.MyReceiver;
import com.common.uitl.SharePersistent;
import com.common.uitl.StringUtils;
import com.common.uitl.Tool;
import com.common.view.SegmentedRadioGroup;
import com.common.view.toast.bamtoast.BamToast;
import com.google.android.material.snackbar.Snackbar;
import com.home.activity.other.DeviceListActivity;
import com.home.activity.set.AuxiliaryActivity;
import com.home.activity.set.ChipSelectActivity;
import com.home.activity.set.CodeActivity;
import com.home.activity.set.DynamicColorActivity;
import com.home.activity.set.RgbSortActivity;
import com.home.activity.set.VoiceCtlActivity;
import com.home.activity.set.light.ChannelSetActivity;
import com.home.activity.set.light.CodeActivity_WIFI;
import com.home.activity.set.light.TBQueryActivity;
import com.home.activity.set.smart.CurrentQueryActivity;
import com.home.activity.set.smart.ModeSelectActivity;
import com.home.activity.set.smart.OtherSettingActivity;
import com.home.activity.set.smart.TimingQueryActivity;
import com.home.activity.set.timer.ChioceTimeActivity;
import com.home.activity.set.timer.TimeActivity;
import com.home.adapter.BluetoothDataModel;
import com.home.base.LedBleActivity;
import com.home.base.LedBleApplication;
import com.home.bean.MyColor;
import com.home.constant.CommonConstant;
import com.home.constant.Constant;
import com.home.db.Group;
import com.home.db.GroupDevice;
import com.home.db.GroupDeviceDao;
import com.home.fragment.ble.AisleFragment;
import com.home.fragment.ble.CutomFragment;
import com.home.fragment.ble.CutomFragmentDmx00Dmx01;
import com.home.fragment.ble.ModeFragment;
import com.home.fragment.ble.MusicFragment;
import com.home.fragment.ble.RgbFragment;
import com.home.fragment.dmx03.DMX03TimerFragment;
import com.home.fragment.service.ServicesFragment;
import com.home.fragment.smart.BrightFragment;
import com.home.fragment.smart.LineFragment;
import com.home.fragment.smart.SceneFragment;
import com.home.fragment.sun.CtFragment_sun;
import com.home.fragment.sun.ModeFragment_sun;
import com.home.fragment.sun.TimerFragment_sun;
import com.home.net.NetConnectBle;
import com.home.net.NetExceptionInterface;
import com.home.net.WifiConenction;
import com.home.net.WifiConenction2;
import com.home.net.wifi.callback.ConnectDeviceListener;
import com.home.service.BluetoothLeServiceSingle;
import com.home.utils.ManageFragment;
import com.home.utils.Utils;
import com.home.view.ActionSheet;
import com.home.view.GroupView;
import com.home.view.SelectMultiCheckGroup;
import com.home.view.SlideSwitch;
import com.home.widget.effects.Effectstype;
import com.home.widget.effects.NiftyDialogBuilder;
import com.ledlamp.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.weigan.loopview.MessageHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class MainActivity_BLE extends LedBleActivity implements NetExceptionInterface, SensorEventListener, ActionSheet.ActionSheetListener, MyReceiver.MyListener, IListener, GPS_Interface {
    private static final int REQUEST_BLUETOOTH_CONNECT = 225;
    private static final int REQUEST_BLUETOOTH_SCAN = 224;
    private static final int REQUEST_CODE_OPEN_GPS = 222;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 223;
    private static final String TAG = "MainActivity_BLE";
    public static MainActivity_BLE mActivity;
    public static String sceneBean;
    private static SharedPreferences sharedPreferences;
    @BindView(R.id.linearLayoutTopItem)
    LinearLayout TopItem;
    private ArrayList<GroupView> arrayListGroupViews;
    @BindView(R.id.llMenu)
    LinearLayout activity_main;
    @BindView(R.id.backTextView)
    TextView backTextView;
    private int bannerPix;
    private int bannerSort;
    private BaseTask baseTask;
    private Bitmap bm;
    public BrightFragment brightFragment;
    @BindView(R.id.btnChangeColor)
    Button btnChangeColor;
    @BindView(R.id.btnChipselectCar01OK)
    Button btnChipselectCar01OK;
    @BindView(R.id.btnModeCycle)
    Button btnModeCycle;
    @BindView(R.id.btnPixSet1)
    Button btnPixSet1;
    @BindView(R.id.btnPixSet2)
    Button btnPixSet2;
    @BindView(R.id.btnTimerAdd)
    Button btnTimerAdd;
    @BindView(R.id.checkGroupWeek)
    SelectMultiCheckGroup checkGroupWeek;
    private int currentIndex;
    private LinearLayout devices_connect;
    private Dialog dialogConnect;
    private Dialog dialogDisconnect;
    private SharedPreferences.Editor editor;
    private FragmentManager fragmentManager;
    private GPSPresenter gps_presenter;
    private Handler handler;
    @BindView(R.id.ivEditColor)
    Button ivEditColor;
    @BindView(R.id.ivLeftMenu)
    ImageView ivLeftMenu;
    @BindView(R.id.ivRightMenu)
    ImageView ivRightMenu;
    @BindView(R.id.ivType)
    ImageView ivType;
    private ImageView iv_all11;
    private ImageView iv_all22;
    private Dialog lDialog;
    private RelativeLayout layout;
    @BindView(R.id.left_menu_content_layout)
    LinearLayout left_menu;
    public LineFragment lineFragment;
    private LinearLayout linearGroups;
    @BindView(R.id.linearLayoutBottom)
    LinearLayout linearLayoutBottom;
    @BindView(R.id.llBLE00Right)
    LinearLayout llBLE00Right;
    @BindView(R.id.llCAR00Right)
    LinearLayout llCAR00Right;
    @BindView(R.id.llCAR01Right)
    LinearLayout llCAR01Right;
    @BindView(R.id.llCommentRight)
    LinearLayout llCommentRight;
    @BindView(R.id.llDirection)
    LinearLayout llDirection;
    @BindView(R.id.llDoor)
    LinearLayout llDoor;
    RelativeLayout llModeDiyColor;
    @BindView(R.id.llPixNum1)
    LinearLayout llPixNum1;
    @BindView(R.id.llPixNum2)
    LinearLayout llPixNum2;
    LinearLayout llSmartTimerDays;
    private ListView lv_alldevices;
    private DeviceAdapter mAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private DrawerLayout mDrawerLayout;
    private TextView modeSelectTV;
    private MusicFragment musicFragment;
    @BindView(R.id.onOffButton)
    Button onOffButton;
    private String randStr;
    @BindView(R.id.rbAisle)
    RadioButton rbAisle;
    @BindView(R.id.rbBrightness)
    RadioButton rbBrightness;
    @BindView(R.id.rbCustom)
    RadioButton rbCustom;
    @BindView(R.id.rbCustomOne)
    RadioButton rbCustomOne;
    @BindView(R.id.rbCustomTwo)
    RadioButton rbCustomTwo;
    @BindView(R.id.rbMode)
    RadioButton rbMode;
    @BindView(R.id.rbMusic)
    RadioButton rbMusic;
    @BindView(R.id.rbRGB)
    RadioButton rbRGB;
    @BindView(R.id.rbRgbBright)
    RadioButton rbRgbBright;
    @BindView(R.id.rbScene)
    RadioButton rbScene;
    @BindView(R.id.rbTimer)
    RadioButton rbTimer;
    private BroadcastReceiver receiver;
    private ImageView refreshView;
    private TextView resetTV;
    @BindView(R.id.rgBottom)
    RadioGroup rgBottom;
    @BindView(R.id.rgBottom_car)
    RadioGroup rgBottom_car;
    @BindView(R.id.rgBottom_light)
    RadioGroup rgBottom_light;
    @BindView(R.id.rgBottom_sun)
    RadioGroup rgBottom_sun;
    public RgbFragment rgbFragment;
    private TextView rgbsortTV;
    @BindView(R.id.right_menu_frame)
    LinearLayout right_menu;
    @BindView(R.id.rlCustomtopInfo)
    RelativeLayout rlCustomtopInfo;
    @BindView(R.id.rlModeTopCAR00)
    RelativeLayout rlModeTopCAR00;
    @BindView(R.id.rlModeTopDMX00)
    RelativeLayout rlModeTopDMX00;
    @BindView(R.id.rlModeTopDMX01)
    RelativeLayout rlModeTopDMX01;
    @BindView(R.id.rlSetting)
    RelativeLayout rlSetting;
    private RelativeLayout rl_alldevices;
    private RelativeLayout rl_item_ble;
    private RelativeLayout rl_item_dmx;
    private RelativeLayout rl_item_shake;
    private RelativeLayout rl_item_smart;
    private RelativeLayout rl_item_stage;
    private Runnable runnable;
    @BindView(R.id.segmentCAR01CustomTop)
    SegmentedRadioGroup segmentCAR01CustomTop;
    @BindView(R.id.segmentCAR01ModeTop)
    SegmentedRadioGroup segmentCAR01ModeTop;
    @BindView(R.id.segmentCAR01RgbTop)
    SegmentedRadioGroup segmentCAR01RgbTop;
    @BindView(R.id.segmentCt)
    SegmentedRadioGroup segmentCt;
    @BindView(R.id.segmentCustomCAR00)
    SegmentedRadioGroup segmentCustomCAR00;
    @BindView(R.id.segmentCustomDMX00Top)
    SegmentedRadioGroup segmentCustomDMX00Top;
    @BindView(R.id.segmentCustomDMX01Top)
    SegmentedRadioGroup segmentCustomDMX01Top;
    @BindView(R.id.segmentDm)
    SegmentedRadioGroup segmentDm;
    @BindView(R.id.segmentModeSun)
    SegmentedRadioGroup segmentModeSun;
    @BindView(R.id.segmentMusic)
    SegmentedRadioGroup segmentMusic;
    @BindView(R.id.segmentRgb)
    SegmentedRadioGroup segmentRgb;
    @BindView(R.id.segmentSmartTimer)
    SegmentedRadioGroup segmentSmartTimer;
    private SensorManager sensorManager;
    private TextView setTV;
    private ImageView shakeColorIV;
    private ImageView shakeModelIV;
    private ImageView shakeNoneIV;
    private TextView shakeTV;
    @BindView(R.id.shakeView)
    RelativeLayout shakeView;
    private int soundID;
    private SoundPool soundPool;
    private SharedPreferences sp;
    private ImageView strobeIV;
    private Switch sw_bubble;
    @BindView(R.id.textViewAllDeviceIndicater)
    TextView textViewAllDeviceIndicater;
    @BindView(R.id.textViewConnectCount)
    TextView textViewConnectCount;
    @BindView(R.id.textViewCustomTitle_sun)
    TextView textViewCustomTitle_sun;
    private TextView timerheckTV;
    private Intent turnOnBluetoothIntent;
    private TextView tvAuxiliaryBLE;
    private TextView tvAuxiliaryDMX;
    @BindView(R.id.tvBtnBLE00_1)
    TextView tvBtnBLE00_1;
    @BindView(R.id.tvBtnBLE00_2)
    TextView tvBtnBLE00_2;
    @BindView(R.id.tvBtnBLE00_3)
    TextView tvBtnBLE00_3;
    private TextView tvBtquery;
    private TextView tvChannelsetStage;
    private TextView tvCodeStage;
    @BindView(R.id.tvPixHigh)
    TextView tvPixHigh;
    @BindView(R.id.tvPixLong)
    TextView tvPixLong;
    @BindView(R.id.tvPixNum)
    TextView tvPixNum;
    @BindView(R.id.tvPixWidth)
    TextView tvPixWidth;
    private TextView tvTimerBLE;
    @BindView(R.id.tvTimerBLE00)
    TextView tvTimerBLE00;
    private TextView tvTimerDMX;
    private TextView tvTimerStage;
    private TextView tvVoicecontrolDMX;
    private TextView tv_btn1;
    private TextView tv_btn2;
    private TextView tv_btn3;
    private TextView tv_btn4;
    @BindView(R.id.tv_car00_btn1)
    TextView tv_car00_btn1;
    @BindView(R.id.tv_car00_btn2)
    TextView tv_car00_btn2;
    @BindView(R.id.tv_car00_btn3)
    TextView tv_car00_btn3;
    @BindView(R.id.tv_car00_btn4)
    TextView tv_car00_btn4;
    private TextView tv_dmx_btn1;
    private TextView tv_dmx_btn2;
    private TextView tv_dmx_btn3;
    private TextView tv_dmx_btn4;
    private ImageView unlockIV;
    private WifiManager wifiManager;
    private boolean canSend = true;
    private final List<Fragment> fragmentList = new ArrayList();
    public boolean isLightOpen = true;
    public int speed = 1;
    private final String speedKey = "speedkey";
    public int brightness = 1;
    private final String brightnessKey = "brightnesskey";
    private String groupName = "";
    private boolean islegal = true;
    public boolean isFirstOpen = true;
    private final int OPEN_BLE = FirstActivity.RESULT333;
    private final int bannerType = 4;
    private final Random random = new Random();
    private int shakeStyle = 1;
    private final int STORAGE_CODE = 112;
    private ImageView imageView = null;
    private final int TAKE_PICTURE = 0;
    private final int CHOOSE_PICTURE = 1;
    private final int INT_GO_LIST = 111;
    private boolean isInitGroup = false;
    private final boolean isAllOn = true;
    private final Map<String, SlideSwitch> map = new HashMap();
    private final int LOCATION_CODE = 110;
    private final int INT_GO_CHANGEMODE = 115;
    ArrayList<BluetoothDevice> listDevices = new ArrayList<>();
    int isTime = 1;

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override // com.home.view.ActionSheet.ActionSheetListener
    public void onDismiss(ActionSheet actionSheet, boolean z) {
    }

    @Override // com.home.net.NetExceptionInterface
    public void onException(Exception exc) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.home.base.LedBleActivity, me.imid.swipebacklayout.lib.app.SwipeBackActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
            getWindow().setStatusBarColor(0);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        getSwipeBackLayout().setEnableGesture(false);
        mActivity = this;
        ListenerManager.getInstance().registerListtener(this);
        sceneBean = (String) getIntent().getSerializableExtra("scene");
        initFragment();
        initSlidingMenu();
        initView();
        if (getImagePath() != "") {
            showImage(getImagePath());
        }
    }

    @Override // com.common.listener.IListener
    public void notifyAllActivity(String str) {
        if (str.equalsIgnoreCase(Constant.UpdateNewFindDevice)) {
            updateNewFindDevice();
        } else if (str.equalsIgnoreCase(Constant.SmartTimeNowSet)) {
            String str2 = sceneBean;
            if (str2 == null || str2 == null || !str2.equalsIgnoreCase(CommonConstant.LEDSMART) || mActivity == null) {
                return;
            }
            getMainActivity().setSmartTimeNowSet();
        } else if (!str.equalsIgnoreCase(Constant.PasswordSet) || mActivity == null) {
        } else {
            getMainActivity().setPassword();
        }
    }

    public static MainActivity_BLE getMainActivity() {
        return mActivity;
    }

    public static String getSceneBean() {
        Log.e(TAG, "NOT_FIRST_INIT: " + sceneBean);
        return sceneBean;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4) {
            back();
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.common.uitl.MyReceiver.MyListener
    public void onListener(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.text_warming);
        builder.setMessage(R.string.timelow);
        builder.setNegativeButton(R.string.bind_end, (DialogInterface.OnClickListener) null).show();
    }

    private SpannableStringBuilder getBuilder() {
        Locale locale;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getResources().getString(R.string.access_request_description));
        ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.home.activity.main.MainActivity_BLE.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("https://source.android.google.cn/devices/bluetooth/ble"));
                MainActivity_BLE.this.startActivity(intent);
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(Color.parseColor("#0099EE"));
                textPaint.setUnderlineText(false);
                textPaint.clearShadowLayer();
            }
        };
        if (Build.VERSION.SDK_INT >= 24) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }
        if (locale.getLanguage().contains("zh")) {
            spannableStringBuilder.setSpan(clickableSpan, getResources().getString(R.string.access_request_description1).length(), getResources().getString(R.string.access_request_description1).length() + getResources().getString(R.string.access_request_description2).length(), 34);
        } else {
            spannableStringBuilder.setSpan(clickableSpan, getResources().getString(R.string.access_request_description1).length(), getResources().getString(R.string.access_request_description1).length() + getResources().getString(R.string.access_request_description2).length() + 1, 34);
        }
        return spannableStringBuilder;
    }

    @Override // com.common.gps.GPS_Interface
    public void gpsSwitchState(boolean z) {
        if (z) {
            this.layout.setVisibility(View.GONE);
        } else {
            this.layout.setVisibility(View.VISIBLE);
        }
    }

    private void initSlidingMenu() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mDrawerLayout = drawerLayout;
        drawerLayout.setScrimColor(0);
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            this.mDrawerLayout.setDrawerLockMode(1);
        }
    }

    public void initFragment() {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSUN)) {
            this.fragmentList.add(new CtFragment_sun());
            this.fragmentList.add(new ModeFragment_sun());
            this.fragmentList.add(new TimerFragment_sun());
        } else {
            RgbFragment rgbFragment = new RgbFragment();
            this.rgbFragment = rgbFragment;
            this.fragmentList.add(rgbFragment);
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                this.fragmentList.add(new SceneFragment());
                BrightFragment brightFragment = new BrightFragment();
                this.brightFragment = brightFragment;
                this.fragmentList.add(brightFragment);
            }
            if (sceneBean.contains(CommonConstant.LEDBLE) || sceneBean.equalsIgnoreCase("LEDDMX-00-") || sceneBean.equalsIgnoreCase("LEDDMX-01-") || sceneBean.equalsIgnoreCase("LEDDMX-03-") || sceneBean.equalsIgnoreCase("LEDCAR-00-") || sceneBean.equalsIgnoreCase("LEDCAR-01-") || sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT) || sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.fragmentList.add(new ModeFragment());
            }
            if (!sceneBean.equalsIgnoreCase("LEDDMX-03-")) {
                if (getSceneBean().contains("LEDDMX-00-") || getSceneBean().contains("LEDDMX-01-")) {
                    this.fragmentList.add(new CutomFragmentDmx00Dmx01());
                } else {
                    this.fragmentList.add(new CutomFragment());
                }
            } else {
                this.fragmentList.add(new DMX03TimerFragment());
            }
            if (sceneBean.contains(CommonConstant.LEDBLE) || sceneBean.equalsIgnoreCase("LEDDMX-00-") || sceneBean.equalsIgnoreCase("LEDDMX-01-") || sceneBean.equalsIgnoreCase("LEDDMX-03-") || sceneBean.equalsIgnoreCase("LEDCAR-00-") || sceneBean.equalsIgnoreCase("LEDCAR-01-") || sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                MusicFragment musicFragment = new MusicFragment();
                this.musicFragment = musicFragment;
                this.fragmentList.add(musicFragment);
            }
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                LineFragment lineFragment = new LineFragment();
                this.lineFragment = lineFragment;
                this.fragmentList.add(lineFragment);
            }
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                this.fragmentList.add(new AisleFragment());
            }
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        this.fragmentManager = supportFragmentManager;
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < this.fragmentList.size(); i++) {
            beginTransaction.add(R.id.flContent, this.fragmentList.get(i), this.fragmentList.get(i).getClass().getSimpleName());
        }
        beginTransaction.commitAllowingStateLoss();
        ManageFragment.showFragment(this.fragmentManager, this.fragmentList, this.currentIndex);
    }

    private void initView() {
        int i;
        int i2;
        if (!mActivity.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            Toast.makeText(mActivity, (int) R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            Tool.exitApp();
        }
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            this.wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        }
        BluetoothManager bluetoothManager = (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        this.mBluetoothAdapter = adapter;
        if (adapter == null) {
            Tool.ToastShow(mActivity, (int) R.string.ble_not_supported);
            Tool.exitApp();
        }
        this.ivLeftMenu.setOnClickListener(new MyOnClickListener());
        this.textViewAllDeviceIndicater = (TextView) mActivity.findViewById(R.id.textViewAllDeviceIndicater);
        this.arrayListGroupViews = new ArrayList<>();
        this.llModeDiyColor = (RelativeLayout) findViewById(R.id.llModeDiyColor);
        this.llSmartTimerDays = (LinearLayout) findViewById(R.id.llSmartTimerDays);
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
            this.mDrawerLayout.setDrawerLockMode(1);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.gps_presenter = new GPSPresenter(this, this);
            this.layout = (RelativeLayout) findViewById(R.id.location_layout);
            ((TextView) findViewById(R.id.btn_location_enable)).setOnClickListener(new MyOnClickListener());
            ((TextView) findViewById(R.id.btn_location_more)).setOnClickListener(new MyOnClickListener());
        }
        this.imageView = (ImageView) findViewById(R.id.activity_main_imageview);
        if (sceneBean.equalsIgnoreCase("LEDCAR-00-") || sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
            this.imageView.setImageDrawable(getResources().getDrawable(R.drawable.car_bg_all));
        }
        this.linearGroups = (LinearLayout) mActivity.findViewById(R.id.linearLayoutDefineGroups);
        this.TopItem.setOnClickListener(new MyOnClickListener());
        Button button = (Button) mActivity.findViewById(R.id.buttonAllOff);
        button.setOnClickListener(new MyOnClickListener());
        Button button2 = (Button) mActivity.findViewById(R.id.buttonAllOn);
        button2.setOnClickListener(new MyOnClickListener());
        Button button3 = (Button) mActivity.findViewById(R.id.buttonAddGroup);
        button3.setOnClickListener(new MyOnClickListener());
        ImageView imageView = (ImageView) mActivity.findViewById(R.id.ivRefresh);
        this.refreshView = imageView;
        imageView.setOnClickListener(new MyOnClickListener());
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDBLE_00)) {
            this.llBLE00Right.setVisibility(View.VISIBLE);
            this.llCAR01Right.setVisibility(View.GONE);
            this.llCommentRight.setVisibility(View.GONE);
        } else if (sceneBean.equalsIgnoreCase("LEDCAR-00-")) {
            this.llCAR00Right.setVisibility(View.VISIBLE);
            this.llCAR01Right.setVisibility(View.GONE);
            this.llCommentRight.setVisibility(View.GONE);
        } else if (sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
            this.rlSetting.setVisibility(View.GONE);
            this.llCAR00Right.setVisibility(View.GONE);
            this.llCAR01Right.setVisibility(View.VISIBLE);
            this.llCommentRight.setVisibility(View.GONE);
        } else {
            this.llBLE00Right.setVisibility(View.GONE);
            this.llCAR00Right.setVisibility(View.GONE);
            this.llCAR01Right.setVisibility(View.GONE);
            this.llCAR01Right.setVisibility(View.GONE);
            this.llCommentRight.setVisibility(View.VISIBLE);
        }
        this.rl_item_ble = (RelativeLayout) mActivity.findViewById(R.id.rl_item_ble);
        TextView textView = (TextView) mActivity.findViewById(R.id.dynamic_tv);
        textView.setOnClickListener(new MyOnClickListener());
        ImageView imageView2 = (ImageView) mActivity.findViewById(R.id.dynamic_gradient_iv);
        imageView2.setOnClickListener(new MyOnClickListener());
        ImageView imageView3 = (ImageView) mActivity.findViewById(R.id.dynamic_breath_iv);
        imageView3.setOnClickListener(new MyOnClickListener());
        ImageView imageView4 = (ImageView) mActivity.findViewById(R.id.dynamic_jump_iv);
        imageView4.setOnClickListener(new MyOnClickListener());
        ImageView imageView5 = (ImageView) mActivity.findViewById(R.id.dynamic_strobe_iv);
        this.strobeIV = imageView5;
        imageView5.setOnClickListener(new MyOnClickListener());
        TextView textView2 = (TextView) mActivity.findViewById(R.id.tvTimerBLE);
        this.tvTimerBLE = textView2;
        textView2.setOnClickListener(new MyOnClickListener());
        TextView textView3 = (TextView) mActivity.findViewById(R.id.rgb_sort_tv);
        this.rgbsortTV = textView3;
        textView3.setOnClickListener(new MyOnClickListener());
        ImageView imageView6 = (ImageView) mActivity.findViewById(R.id.lock_iv);
        imageView6.setOnClickListener(new MyOnClickListener());
        ImageView imageView7 = (ImageView) mActivity.findViewById(R.id.unlock_iv);
        this.unlockIV = imageView7;
        imageView7.setOnClickListener(new MyOnClickListener());
        TextView textView4 = (TextView) mActivity.findViewById(R.id.tv_auxiliary_ble);
        this.tvAuxiliaryBLE = textView4;
        textView4.setOnClickListener(new MyOnClickListener());
        TextView textView5 = (TextView) mActivity.findViewById(R.id.tv_btn1);
        this.tv_btn1 = textView5;
        textView5.setOnClickListener(new MyOnClickListener());
        TextView textView6 = (TextView) mActivity.findViewById(R.id.tv_btn2);
        this.tv_btn2 = textView6;
        textView6.setOnClickListener(new MyOnClickListener());
        TextView textView7 = (TextView) mActivity.findViewById(R.id.tv_btn3);
        this.tv_btn3 = textView7;
        textView7.setOnClickListener(new MyOnClickListener());
        TextView textView8 = (TextView) mActivity.findViewById(R.id.tv_btn4);
        this.tv_btn4 = textView8;
        textView8.setOnClickListener(new MyOnClickListener());
        this.rl_item_dmx = (RelativeLayout) mActivity.findViewById(R.id.rl_item_dmx);
        TextView textView9 = (TextView) mActivity.findViewById(R.id.tvTimerDMX);
        this.tvTimerDMX = textView9;
        textView9.setOnClickListener(new MyOnClickListener());
        TextView textView10 = (TextView) mActivity.findViewById(R.id.tvVoicecontrolDMX);
        this.tvVoicecontrolDMX = textView10;
        textView10.setOnClickListener(new MyOnClickListener());
        TextView textView11 = (TextView) mActivity.findViewById(R.id.code_tv);
        textView11.setOnClickListener(new MyOnClickListener());
        TextView textView12 = (TextView) mActivity.findViewById(R.id.tv_auxiliary);
        this.tvAuxiliaryDMX = textView12;
        textView12.setOnClickListener(new MyOnClickListener());
        this.tv_dmx_btn1 = (TextView) mActivity.findViewById(R.id.tv_dmx_btn1);
        tv_dmx_btn1.setOnClickListener(new MyOnClickListener());
        this.tv_dmx_btn2 =  (TextView) mActivity.findViewById(R.id.tv_dmx_btn2);
        this.tv_dmx_btn2.setOnClickListener(new MyOnClickListener());
        this.tv_dmx_btn3 = (TextView) mActivity.findViewById(R.id.tv_dmx_btn3);
        this.tv_dmx_btn3.setOnClickListener(new MyOnClickListener());
        this.tv_dmx_btn4 = (TextView) mActivity.findViewById(R.id.tv_dmx_btn4);;
        this.tv_dmx_btn4.setOnClickListener(new MyOnClickListener());
        if (sceneBean.equalsIgnoreCase("LEDDMX-00-") || sceneBean.equalsIgnoreCase("LEDDMX-01-")) {
            this.tvVoicecontrolDMX.setVisibility(View.GONE);
            textView11.setVisibility(View.GONE);
        }
        this.tvTimerBLE00.setOnClickListener(new MyOnClickListener());
        this.tvBtnBLE00_1.setOnClickListener(new MyOnClickListener());
        this.tvBtnBLE00_2.setOnClickListener(new MyOnClickListener());
        this.tvBtnBLE00_3.setOnClickListener(new MyOnClickListener());
        this.tv_car00_btn1.setOnClickListener(new MyOnClickListener());
        this.tv_car00_btn2.setOnClickListener(new MyOnClickListener());
        this.tv_car00_btn3.setOnClickListener(new MyOnClickListener());
        this.tv_car00_btn4.setOnClickListener(new MyOnClickListener());
        final int[] iArr = {R.drawable.car01_color_block_shap_blue_normal};
        final int[] iArr2 = {R.drawable.car01_color_block_shap_blue_select};
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(MainActivity_BLE.mActivity, R.anim.layout_scale));
                TextView textView17 = (TextView) view;
                for (int i3 = 0; i3 <= 1; i3++) {
                    String obj = textView17.getTag().toString();
                    if (obj.contains("" + i3)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            textView17.setTextColor(MainActivity_BLE.this.getColor(R.color.white));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            textView17.setBackground(MainActivity_BLE.this.getDrawable(iArr2[0]));
                        }
                        MainActivity_BLE.this.setAuxiliary(i3, view);
                        if (MainActivity_BLE.getMainActivity() != null) {
                            SharePersistent.savePerference(MainActivity_BLE.this.getApplicationContext(), "r3" + SharePersistent.getPerference(MainActivity_BLE.getMainActivity(), Constant.CUSTOM_DIY_APPKEY), i3);
                        }
                    } else {
                        LinearLayout linearLayout = MainActivity_BLE.this.llCAR01Right;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ((TextView) linearLayout.findViewWithTag("r3" + i3)).setBackground(MainActivity_BLE.this.getDrawable(iArr[0]));
                        }
                        LinearLayout linearLayout2 = MainActivity_BLE.this.llCAR01Right;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ((TextView) linearLayout2.findViewWithTag("r3" + i3)).setTextColor(MainActivity_BLE.this.getColor(R.color.white));
                        }
                    }
                }
            }
        };
        int i3 = getMainActivity() != null ? SharePersistent.getInt(getApplicationContext(), "viewMode" + SharePersistent.getPerference(getMainActivity(), Constant.CUSTOM_DIY_APPKEY)) : 0;
        int i4 = 0;
        for (int i5 = 1; i4 <= i5; i5 = 1) {
            TextView textView17 = (TextView) this.llCAR01Right.findViewWithTag("viewMode" + i4);
            textView17.setOnClickListener(onClickListener);
            textView17.setTag("viewMode" + i4);
            if (i3 == i4) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView17.setTextColor(getColor(R.color.white));
                }
                i2 = i3;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textView17.setBackground(getDrawable(iArr2[0]));
                }
            } else {
                i2 = i3;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((TextView) this.llCAR01Right.findViewWithTag("viewMode" + i4)).setBackground(getDrawable(iArr[0]));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((TextView) this.llCAR01Right.findViewWithTag("viewMode" + i4)).setTextColor(getColor(R.color.white));
                }
            }
            i4++;
            i3 = i2;
        }
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(MainActivity_BLE.mActivity, R.anim.layout_scale));
                TextView textView18 = (TextView) view;
                for (int i6 = 0; i6 <= 4; i6++) {
                    String obj = textView18.getTag().toString();
                    if (obj.contains("" + i6)) {
                        MainActivity_BLE.this.setAuxiliary(i6 + 2, view);
                    }
                }
            }
        };
        for (int i6 = 0; i6 <= 4; i6++) {
            TextView textView18 = (TextView) this.llCAR01Right.findViewWithTag("viewDoor" + i6);
            textView18.setOnClickListener(onClickListener2);
            textView18.setTag("viewDoor" + i6);
        }
        View.OnClickListener onClickListener3 = new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(MainActivity_BLE.mActivity, R.anim.layout_scale));
                TextView textView19 = (TextView) view;
                for (int i7 = 0; i7 <= 3; i7++) {
                    String obj = textView19.getTag().toString();
                    if (obj.contains("" + i7)) {
                        MainActivity_BLE.this.setAuxiliary(i7 + 7, view);
                    }
                }
            }
        };
        for (int i7 = 0; i7 <= 3; i7++) {
            TextView textView19 = (TextView) this.llCAR01Right.findViewWithTag("viewDirection" + i7);
            textView19.setOnClickListener(onClickListener3);
            textView19.setTag("viewDirection" + i7);
        }
        View.OnClickListener onClickListener4 = new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(MainActivity_BLE.mActivity, R.anim.layout_scale));
                TextView textView20 = (TextView) view;
                for (int i8 = 0; i8 <= 5; i8++) {
                    String obj = textView20.getTag().toString();
                    if (obj.contains("" + i8)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            textView20.setTextColor(MainActivity_BLE.this.getColor(R.color.white));
                        }
                        MainActivity_BLE mainActivity_BLE = MainActivity_BLE.this;
                        mainActivity_BLE.bannerSort = Integer.parseInt(((String) mainActivity_BLE.getRgbSortIndex().get(i8)).replaceAll(" ", "").trim());
                        Toast.makeText(MainActivity_BLE.this.getApplicationContext(), MainActivity_BLE.getMainActivity().getResources().getString(R.string.current_rgb_format, MainActivity_BLE.this.Car01RgbSortModel().get(i8)), Toast.LENGTH_SHORT).show();
                    } else {
                        LinearLayout linearLayout = MainActivity_BLE.this.llCAR01Right;
                        ((TextView) linearLayout.findViewWithTag(i8)).setTextColor(Color.parseColor("#444444"));
                    }
                }
            }
        };
        String preference = getMainActivity() != null ? SharePersistent.getPerference(getApplicationContext(), "viewRGB" + SharePersistent.getPerference(getMainActivity(), Constant.CUSTOM_DIY_APPKEY)) : null;
        if (preference != null && preference.length() > 0) {
            this.bannerSort = Integer.parseInt(preference.split(",")[1].trim()) + 1;
        } else {
            this.bannerSort = 3;
            this.tvPixNum.setText("200");
        }
        for (int i8 = 0; i8 <= 5; i8++) {
            TextView textView20 = (TextView) this.llCAR01Right.findViewWithTag("viewRGB" + i8);
            textView20.setOnClickListener(onClickListener4);
            textView20.setTag("viewRGB" + i8);
            if (this.bannerSort - 1 == i8) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView20.setTextColor(getColor(R.color.white));
                }
            } else {
                ((TextView) this.llCAR01Right.findViewWithTag("viewRGB" + i8)).setTextColor(Color.parseColor("#444444"));
            }
        }
        this.btnPixSet1.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_BLE.this.llPixNum1.setVisibility(View.GONE);
                MainActivity_BLE.this.llPixNum2.setVisibility(View.VISIBLE);
                MainActivity_BLE.this.llDoor.setVisibility(View.VISIBLE);
                MainActivity_BLE.this.llDirection.setVisibility(View.VISIBLE);
                MainActivity_BLE.this.initPixValue();
            }
        });
        this.tvPixNum.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_BLE.this.setPixValue(0);
            }
        });
        this.btnPixSet2.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_BLE.this.llPixNum1.setVisibility(View.VISIBLE);
                MainActivity_BLE.this.llPixNum2.setVisibility(View.GONE);
                MainActivity_BLE.this.llDoor.setVisibility(View.GONE);
                MainActivity_BLE.this.llDirection.setVisibility(View.GONE);
                MainActivity_BLE.this.initPixValue();
            }
        });
        this.tvPixLong.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_BLE.this.setPixValue(1);
            }
        });
        this.tvPixWidth.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_BLE.this.setPixValue(2);
            }
        });
        this.tvPixHigh.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_BLE.this.setPixValue(3);
            }
        });
        this.btnChipselectCar01OK.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivity_BLE.this.startAnimation(view);
                if (MainActivity_BLE.this.llPixNum1.getVisibility() == 0) {
                    if (MainActivity_BLE.mActivity != null) {
                        MainActivity_BLE.getMainActivity().setConfigSPI(MainActivity_BLE.this.bannerType, (byte) (MainActivity_BLE.this.bannerPix >> 8), (byte) MainActivity_BLE.this.bannerPix, MainActivity_BLE.this.bannerSort);
                    }
                } else if (MainActivity_BLE.this.llPixNum2.getVisibility() == 0) {
                    int parseInt = Integer.parseInt("" + ((Object) MainActivity_BLE.this.tvPixLong.getText()));
                    int parseInt2 = Integer.parseInt("" + ((Object) MainActivity_BLE.this.tvPixWidth.getText()));
                    MainActivity_BLE.getMainActivity().setConfigCAR01(parseInt, parseInt2, Integer.parseInt("" + ((Object) MainActivity_BLE.this.tvPixHigh.getText())), MainActivity_BLE.this.bannerSort);
                }
            }
        });
        initPixValue();
        this.rl_item_smart = (RelativeLayout) mActivity.findViewById(R.id.rl_item_smart);
        TextView textView21 = (TextView) mActivity.findViewById(R.id.fan_rotational_temperature_tv);
        textView21.setOnClickListener(new MyOnClickListener());
        TextView textView22 = (TextView) mActivity.findViewById(R.id.timer_check_tv);
        this.timerheckTV = textView22;
        textView22.setOnClickListener(new MyOnClickListener());
        TextView textView23 = (TextView) mActivity.findViewById(R.id.currentquery_tv);
        textView23.setOnClickListener(new MyOnClickListener());
        TextView textView24 = (TextView) mActivity.findViewById(R.id.mode_select_tv);
        this.modeSelectTV = textView24;
        textView24.setOnClickListener(new MyOnClickListener());
        Switch r1 = (Switch) mActivity.findViewById(R.id.sw_bubble);
        this.sw_bubble = r1;
        r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.home.activity.main.MainActivity_BLE.13
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    MainActivity_BLE.this.setSmartBubbleCheck(1);
                } else {
                    MainActivity_BLE.this.setSmartBubbleCheck(0);
                }
            }
        });
        this.rl_item_stage = (RelativeLayout) mActivity.findViewById(R.id.rl_item_stage);
        TextView textView25 = (TextView) mActivity.findViewById(R.id.tvChannelsetStage);
        this.tvChannelsetStage = textView25;
        textView25.setOnClickListener(new MyOnClickListener());
        TextView textView26 = (TextView) mActivity.findViewById(R.id.tvTimerStage);
        this.tvTimerStage = textView26;
        textView26.setOnClickListener(new MyOnClickListener());
        TextView textView27 = (TextView) mActivity.findViewById(R.id.tvCodeStage);
        this.tvCodeStage = textView27;
        textView27.setOnClickListener(new MyOnClickListener());
        TextView textView28 = (TextView) mActivity.findViewById(R.id.tvBtquery);
        this.tvBtquery = textView28;
        textView28.setOnClickListener(new MyOnClickListener());
        this.textViewCustomTitle_sun = (TextView) findViewById(R.id.textViewCustomTitle_sun);
        this.btnTimerAdd.setOnClickListener(new MyOnClickListener());
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSUN)) {
            this.rl_item_ble.setVisibility(View.GONE);
            this.rl_item_dmx.setVisibility(View.GONE);
            this.rl_item_smart.setVisibility(View.GONE);
            this.rl_item_stage.setVisibility(View.GONE);
            this.btnTimerAdd.setVisibility(View.VISIBLE);
            this.textViewCustomTitle_sun.setVisibility(View.VISIBLE);
        } else {
            this.textViewCustomTitle_sun.setVisibility(View.GONE);
            this.btnTimerAdd.setVisibility(View.GONE);
            if (sceneBean.contains(CommonConstant.LEDBLE) || sceneBean.equalsIgnoreCase("LEDCAR-00-")) {
                this.rl_item_dmx.setVisibility(View.GONE);
                this.rl_item_smart.setVisibility(View.GONE);
                this.rl_item_stage.setVisibility(View.GONE);
            } else if (sceneBean.equalsIgnoreCase("LEDDMX-00-") || sceneBean.equalsIgnoreCase("LEDDMX-01-") || sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.rl_item_ble.setVisibility(View.GONE);
                this.rl_item_smart.setVisibility(View.GONE);
                this.rl_item_stage.setVisibility(View.GONE);
            } else if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                this.rl_item_ble.setVisibility(View.GONE);
                this.rl_item_dmx.setVisibility(View.GONE);
                this.rl_item_stage.setVisibility(View.GONE);
            } else if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                this.rl_item_ble.setVisibility(View.GONE);
                this.rl_item_dmx.setVisibility(View.GONE);
                this.rl_item_smart.setVisibility(View.GONE);
                if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                    this.tvCodeStage.setVisibility(View.VISIBLE);
                    this.tvBtquery.setVisibility(View.VISIBLE);
                }
            }
        }
        this.backTextView.setOnClickListener(new MyOnClickListener());
        this.onOffButton.setOnClickListener(new MyOnClickListener());
        TextView textView29 = (TextView) mActivity.findViewById(R.id.change_under_pic_tv);
        textView29.setOnClickListener(new MyOnClickListener());
        TextView textView30 = (TextView) mActivity.findViewById(R.id.reset_tv);
        this.resetTV = textView30;
        textView30.setOnClickListener(new MyOnClickListener());
        TextView textView31 = (TextView) mActivity.findViewById(R.id.shake_tv);
        this.shakeTV = textView31;
        textView31.setOnClickListener(new MyOnClickListener());
        TextView textView32 = (TextView) mActivity.findViewById(R.id.set_tv);
        this.setTV = textView32;
        textView32.setOnClickListener(new MyOnClickListener());
        this.rl_item_shake = (RelativeLayout) mActivity.findViewById(R.id.rl_item_shake);
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT) || sceneBean.equalsIgnoreCase(CommonConstant.LEDSUN) || sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
            this.rl_item_shake.setVisibility(View.GONE);
        }
        ImageView imageView8 = (ImageView) mActivity.findViewById(R.id.shake_one_iv);
        this.shakeColorIV = imageView8;
        imageView8.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.setBackgroundResource(R.drawable.bg_in_press);
                MainActivity_BLE.this.shakeNoneIV.setBackgroundResource(R.drawable.bg_shake_green);
                MainActivity_BLE.this.shakeModelIV.setBackgroundResource(R.drawable.bg_shake_green);
                MainActivity_BLE.this.shakeView.setBackgroundResource(R.drawable.bg_shake_green);
                MainActivity_BLE.this.shakeStyle = 0;
            }
        });
        ImageView imageView9 = (ImageView) mActivity.findViewById(R.id.shake_two_iv);
        this.shakeNoneIV = imageView9;
        imageView9.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.setBackgroundResource(R.drawable.bg_in_press);
                MainActivity_BLE.this.shakeColorIV.setBackgroundResource(R.drawable.bg_shake_lightgray);
                MainActivity_BLE.this.shakeModelIV.setBackgroundResource(R.drawable.bg_shake_lightgray);
                MainActivity_BLE.this.shakeView.setBackgroundResource(R.drawable.bg_shake_lightgray);
                MainActivity_BLE.this.shakeStyle = 1;
            }
        });
        ImageView imageView10 = (ImageView) mActivity.findViewById(R.id.shake_three_iv);
        this.shakeModelIV = imageView10;
        imageView10.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                view.setBackgroundResource(R.drawable.bg_in_press);
                MainActivity_BLE.this.shakeColorIV.setBackgroundResource(R.drawable.bg_shake_orange);
                MainActivity_BLE.this.shakeNoneIV.setBackgroundResource(R.drawable.bg_shake_orange);
                MainActivity_BLE.this.shakeView.setBackgroundResource(R.drawable.bg_shake_orange);
                MainActivity_BLE.this.shakeStyle = 2;
            }
        });
        this.shakeNoneIV.performClick();
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 21) {
            this.soundPool = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()).build();
            i = 1;
        } else {
            i = 1;
            this.soundPool = new SoundPool(10, 3, 1);
        }
        this.soundID = this.soundPool.load(this, R.raw.dang, i);
        SharedPreferences sharedPreferences2 = getSharedPreferences(Constant.MODLE_TYPE, 0);
        this.sp = sharedPreferences2;
        this.editor = sharedPreferences2.edit();
        this.currentIndex = 0;
        this.ivType.setImageResource(R.drawable.tab_dim_check);
        this.ivType.setOnClickListener(new MyOnClickListener());
        this.ivRightMenu.setOnClickListener(new MyOnClickListener());
        if (sceneBean.contains(CommonConstant.LEDBLE) || sceneBean.equalsIgnoreCase("LEDDMX-00-") || sceneBean.equalsIgnoreCase("LEDDMX-01-") || sceneBean.equalsIgnoreCase("LEDCAR-00-") || sceneBean.equalsIgnoreCase("LEDCAR-01-") || sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
            this.rbScene.setVisibility(View.GONE);
            this.rbBrightness.setVisibility(View.GONE);
            this.rbTimer.setVisibility(View.GONE);
        } else if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
            this.rbMode.setVisibility(View.GONE);
            this.rbMusic.setVisibility(View.GONE);
        } else if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            this.rbScene.setVisibility(View.GONE);
            this.rbBrightness.setVisibility(View.GONE);
            this.rbMusic.setVisibility(View.GONE);
            this.rbTimer.setVisibility(View.GONE);
            this.rbAisle.setVisibility(View.VISIBLE);
        } else if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSUN)) {
            this.rbRGB.setVisibility(View.VISIBLE);
            this.rbScene.setVisibility(View.GONE);
            this.rbMode.setVisibility(View.VISIBLE);
            this.rbBrightness.setVisibility(View.GONE);
            this.rbMusic.setVisibility(View.GONE);
            this.rbCustom.setVisibility(View.GONE);
        }
        if (sceneBean.equalsIgnoreCase("LEDCAR-00-") || sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
            this.rgBottom_car.setVisibility(View.VISIBLE);
            this.rgBottom_sun.setVisibility(View.GONE);
            this.rgBottom.setVisibility(View.GONE);
            this.rgBottom_light.setVisibility(View.GONE);
            this.ivType.setVisibility(View.GONE);
            this.llSmartTimerDays.setVisibility(View.GONE);
            this.rgBottom_car.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.activity.main.MainActivity_BLE.17
                @Override // android.widget.RadioGroup.OnCheckedChangeListener
                public void onCheckedChanged(RadioGroup radioGroup, int i9) {
                    MainActivity_BLE.this.ivLeftMenu.setVisibility(View.GONE);
                    MainActivity_BLE.this.textViewConnectCount.setVisibility(View.GONE);
                    MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.GONE);
                    switch (i9) {
                        case R.id.rbCustom_car /* 2131297107 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.currentIndex = 2;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-00-")) {
                                MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.VISIBLE);
                            } else {
                                MainActivity_BLE.this.segmentCAR01RgbTop.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCAR01ModeTop.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCAR01CustomTop.setVisibility(View.VISIBLE);
                                MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.GONE);
                            }
                            MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(View.GONE);
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.editor.commit();
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            MainActivity_BLE.this.pauseMusicAndVolum(true);
                            break;
                        case R.id.rbMode_car /* 2131297114 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.currentIndex = 1;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-00-")) {
                                MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                                MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.VISIBLE);
                            } else {
                                MainActivity_BLE.this.segmentCAR01RgbTop.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCAR01ModeTop.setVisibility(View.VISIBLE);
                                MainActivity_BLE.this.segmentCAR01CustomTop.setVisibility(View.GONE);
                                MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                            }
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.editor.commit();
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            MainActivity_BLE.this.pauseMusicAndVolum(true);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            break;
                        case R.id.rbMusic_car /* 2131297122 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.currentIndex = 3;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(4);
                            MainActivity_BLE.this.segmentCAR01RgbTop.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCAR01ModeTop.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCAR01CustomTop.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-00-")) {
                                MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            }
                            if (MainActivity_BLE.this.musicFragment.isCheckSegmentedRadioGroupIndexTwo()) {
                                MainActivity_BLE.this.ivEditColor.setVisibility(View.VISIBLE);
                                MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            } else {
                                MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                                if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-00-")) {
                                    MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.VISIBLE);
                                    MainActivity_BLE.this.btnChangeColor.setVisibility(View.VISIBLE);
                                } else if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
                                    MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                                }
                            }
                            MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            MainActivity_BLE.this.musicFragment.startMusice();
                            break;
                        case R.id.rbRGB_car /* 2131297127 */:
                            MainActivity_BLE.this.ivLeftMenu.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.textViewConnectCount.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.currentIndex = 0;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
                                MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCAR01RgbTop.setVisibility(View.VISIBLE);
                                MainActivity_BLE.this.segmentCAR01ModeTop.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCAR01CustomTop.setVisibility(View.GONE);
                            } else {
                                MainActivity_BLE.this.segmentRgb.setVisibility(View.VISIBLE);
                            }
                            MainActivity_BLE.this.editor.commit();
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            MainActivity_BLE.this.pauseMusicAndVolum(true);
                            break;
                    }
                    MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                    MainActivity_BLE.this.editor.commit();
                    ManageFragment.showFragment(MainActivity_BLE.this.fragmentManager, MainActivity_BLE.this.fragmentList, MainActivity_BLE.this.currentIndex);
                }
            });
            this.rgBottom_car.check(R.id.rbRGB_car);
        } else if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSUN)) {
            this.rgBottom.setBackground(getResources().getDrawable(R.drawable.tab_sun_bg));
            this.rgBottom_sun.setVisibility(View.VISIBLE);
            this.rgBottom.setVisibility(View.GONE);
            this.rgBottom_light.setVisibility(View.GONE);
            this.ivType.setVisibility(View.GONE);
            this.btnChangeColor.setVisibility(View.GONE);
            this.llSmartTimerDays.setVisibility(View.GONE);
            this.rgBottom_sun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.activity.main.MainActivity_BLE.18
                @Override // android.widget.RadioGroup.OnCheckedChangeListener
                public void onCheckedChanged(RadioGroup radioGroup, int i9) {
                    MainActivity_BLE.this.ivLeftMenu.setVisibility(View.GONE);
                    MainActivity_BLE.this.textViewConnectCount.setVisibility(View.GONE);
                    if (i9 == R.id.rbOne_sun) {
                        MainActivity_BLE.this.currentIndex = 0;
                        MainActivity_BLE.this.ivLeftMenu.setVisibility(View.VISIBLE);
                        MainActivity_BLE.this.textViewConnectCount.setVisibility(View.VISIBLE);
                        MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                        MainActivity_BLE.this.btnTimerAdd.setVisibility(View.GONE);
                        MainActivity_BLE.this.onOffButton.setVisibility(View.VISIBLE);
                        MainActivity_BLE.this.textViewCustomTitle_sun.setVisibility(View.VISIBLE);
                        MainActivity_BLE.this.textViewCustomTitle_sun.setText(MainActivity_BLE.this.getResources().getString(R.string.colour));
                        MainActivity_BLE.this.segmentModeSun.setVisibility(View.GONE);
                    } else if (i9 == R.id.rbThree_sun) {
                        MainActivity_BLE.this.currentIndex = 2;
                        MainActivity_BLE.this.btnTimerAdd.setVisibility(View.VISIBLE);
                        MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                        MainActivity_BLE.this.textViewCustomTitle_sun.setVisibility(View.VISIBLE);
                        MainActivity_BLE.this.textViewCustomTitle_sun.setText(MainActivity_BLE.this.getResources().getString(R.string.tab_timer));
                        MainActivity_BLE.this.segmentModeSun.setVisibility(View.GONE);
                    } else if (i9 == R.id.rbTwo_sun) {
                        MainActivity_BLE.this.currentIndex = 1;
                        MainActivity_BLE.this.btnTimerAdd.setVisibility(View.GONE);
                        MainActivity_BLE.this.onOffButton.setVisibility(View.VISIBLE);
                        MainActivity_BLE.this.textViewCustomTitle_sun.setVisibility(View.GONE);
                        MainActivity_BLE.this.segmentModeSun.setVisibility(View.VISIBLE);
                    }
                    MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                    MainActivity_BLE.this.editor.commit();
                    ManageFragment.showFragment(MainActivity_BLE.this.fragmentManager, MainActivity_BLE.this.fragmentList, MainActivity_BLE.this.currentIndex);
                }
            });
            this.rgBottom_sun.check(R.id.rbOne_sun);
        } else if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            this.rgBottom.setVisibility(View.GONE);
            this.ivType.setVisibility(View.GONE);
            this.ivLeftMenu.setVisibility(View.GONE);
            this.textViewConnectCount.setVisibility(View.GONE);
            this.rbRgbBright.setVisibility(View.GONE);
            this.rgBottom_light.setVisibility(View.VISIBLE);
            this.rgBottom_light.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.activity.main.MainActivity_BLE.19
                @Override // android.widget.RadioGroup.OnCheckedChangeListener
                public void onCheckedChanged(RadioGroup radioGroup, int i9) {
                    MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                    MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.GONE);
                    switch (i9) {
                        case R.id.rbAisle_light /* 2131297086 */:
                            MainActivity_BLE.this.currentIndex = 3;
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.editor.commit();
                            break;
                        case R.id.rbCustom_light /* 2131297108 */:
                            MainActivity_BLE.this.currentIndex = 2;
                            MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.editor.commit();
                            break;
                        case R.id.rbMode_light /* 2131297115 */:
                            MainActivity_BLE.this.currentIndex = 1;
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.editor.commit();
                            break;
                        case R.id.rbRGB_light /* 2131297128 */:
                            MainActivity_BLE.this.currentIndex = 0;
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.editor.commit();
                            break;
                    }
                    ManageFragment.showFragment(MainActivity_BLE.this.fragmentManager, MainActivity_BLE.this.fragmentList, MainActivity_BLE.this.currentIndex);
                }
            });
            this.rgBottom_light.check(R.id.rbRGB_light);
        } else {
            this.rgBottom.setBackgroundColor(getResources().getColor(R.color.transparent));
            this.rgBottom_sun.setVisibility(View.GONE);
            this.rgBottom.setVisibility(View.VISIBLE);
            this.rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.activity.main.MainActivity_BLE.20
                @Override // android.widget.RadioGroup.OnCheckedChangeListener
                public void onCheckedChanged(RadioGroup radioGroup, int i9) {
                    MainActivity_BLE.this.ivLeftMenu.setVisibility(View.GONE);
                    MainActivity_BLE.this.textViewConnectCount.setVisibility(View.GONE);
                    MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.GONE);
                    switch (i9) {
                        case R.id.rbAisle /* 2131297085 */:
                            MainActivity_BLE.this.currentIndex = 3;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnModeCycle.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                                MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.GONE);
                                MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                                break;
                            }
                            break;
                        case R.id.rbBrightness /* 2131297088 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llSmartTimerDays.setVisibility(View.GONE);
                            MainActivity_BLE.this.currentIndex = 2;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX01Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(4);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnModeCycle.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                                MainActivity_BLE.this.pauseMusicAndVolum(true);
                                break;
                            }
                            break;
                        case R.id.rbCustom /* 2131297097 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llSmartTimerDays.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                                MainActivity_BLE.this.currentIndex = 3;
                                MainActivity_BLE.this.brightFragment.setActive(SharePersistent.getSmartModeString(MainActivity_BLE.mActivity, CommonConstant.SELECT_MODE_SMART_STRING));
                            } else {
                                MainActivity_BLE.this.currentIndex = 2;
                            }
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX00.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnModeCycle.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                                MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCustomDMX01Top.setVisibility(View.VISIBLE);
                            } else if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-")) {
                                MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.VISIBLE);
                                MainActivity_BLE.this.segmentCustomDMX01Top.setVisibility(View.GONE);
                            }
                            MainActivity_BLE.this.ivType.setVisibility(View.GONE);
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.editor.commit();
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                                MainActivity_BLE.this.pauseMusicAndVolum(true);
                            }
                            if (MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDBLE_01)) {
                                MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.VISIBLE);
                                break;
                            }
                            break;
                        case R.id.rbMode /* 2131297111 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llSmartTimerDays.setVisibility(View.GONE);
                            MainActivity_BLE.this.currentIndex = 1;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX01Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(View.GONE);
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.editor.commit();
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnModeCycle.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                                MainActivity_BLE.this.pauseMusicAndVolum(true);
                                if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                                    MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                                } else if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-")) {
                                    MainActivity_BLE.this.rlModeTopDMX00.setVisibility(View.VISIBLE);
                                } else {
                                    MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.VISIBLE);
                                }
                                if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                                    MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                                    MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.GONE);
                                    if (MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                                        MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.VISIBLE);
                                        break;
                                    }
                                }
                            }
                            break;
                        case R.id.rbMusic /* 2131297118 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llSmartTimerDays.setVisibility(View.GONE);
                            MainActivity_BLE.this.currentIndex = 3;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.segmentCustomDMX01Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX00.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(4);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                                MainActivity_BLE.this.musicFragment.startMusice();
                                if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE_00)) {
                                    if (MainActivity_BLE.this.musicFragment.isCheckSegmentedRadioGroupIndexTwo()) {
                                        MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.GONE);
                                        MainActivity_BLE.this.ivEditColor.setVisibility(View.VISIBLE);
                                        MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                                    } else {
                                        MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.VISIBLE);
                                        MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                                        MainActivity_BLE.this.btnChangeColor.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-")) {
                                    MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                                    if (MainActivity_BLE.this.musicFragment.isCheckSegmentedRadioGroupIndexTwo()) {
                                        MainActivity_BLE.this.ivEditColor.setVisibility(View.VISIBLE);
                                        MainActivity_BLE.this.btnModeCycle.setVisibility(View.GONE);
                                    } else {
                                        MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                                        MainActivity_BLE.this.btnModeCycle.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                                MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.GONE);
                                break;
                            }
                            break;
                        case R.id.rbRGB /* 2131297125 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.ivLeftMenu.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.textViewConnectCount.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llSmartTimerDays.setVisibility(View.GONE);
                            MainActivity_BLE.this.currentIndex = 0;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX01Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX00.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlCustomtopInfo.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnModeCycle.setVisibility(View.GONE);
                            MainActivity_BLE.this.editor.putInt(Constant.MODLE_VALUE, MainActivity_BLE.this.currentIndex);
                            MainActivity_BLE.this.editor.commit();
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                                MainActivity_BLE.this.pauseMusicAndVolum(true);
                            } else if (MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                                MainActivity_BLE.this.rgbFragment.setActive(SharePersistent.getSmartModeString(MainActivity_BLE.mActivity, CommonConstant.SELECT_MODE_SMART_STRING));
                            }
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                                MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                                MainActivity_BLE.this.rlModeTopCAR00.setVisibility(View.GONE);
                                MainActivity_BLE.this.segmentCustomCAR00.setVisibility(View.GONE);
                                break;
                            }
                            break;
                        case R.id.rbScene /* 2131297135 */:
                            MainActivity_BLE.this.isTime = 1;
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.llSmartTimerDays.setVisibility(View.GONE);
                            MainActivity_BLE.this.currentIndex = 1;
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX00Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCustomDMX01Top.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX00.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(4);
                            MainActivity_BLE.this.ivEditColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                            MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                            MainActivity_BLE.this.btnChangeColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.btnModeCycle.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-")) {
                                MainActivity_BLE.this.pauseMusicAndVolum(true);
                                break;
                            }
                            break;
                        case R.id.rbTimer /* 2131297140 */:
                            MainActivity_BLE.this.currentIndex = 4;
                            MainActivity_BLE.this.llModeDiyColor.setVisibility(View.GONE);
                            MainActivity_BLE.this.llSmartTimerDays.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.segmentDm.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentCt.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentRgb.setVisibility(View.GONE);
                            MainActivity_BLE.this.segmentMusic.setVisibility(View.GONE);
                            MainActivity_BLE.this.rlModeTopDMX01.setVisibility(View.GONE);
                            MainActivity_BLE.this.ivType.setVisibility(4);
                            MainActivity_BLE.this.onOffButton.setVisibility(View.VISIBLE);
                            MainActivity_BLE.this.btnModeCycle.setVisibility(View.GONE);
                            if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-")) {
                                MainActivity_BLE.this.pauseMusicAndVolum(true);
                                break;
                            } else if (MainActivity_BLE.sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                                MainActivity_BLE.this.onOffButton.setVisibility(View.GONE);
                                break;
                            }
                            break;
                    }
                    ManageFragment.showFragment(MainActivity_BLE.this.fragmentManager, MainActivity_BLE.this.fragmentList, MainActivity_BLE.this.currentIndex);
                }
            });
            int i9 = this.currentIndex;
            if (i9 == 0) {
                this.ivType.setImageResource(R.drawable.tab_dim_check);
                this.rgBottom.check(R.id.rbRGB);
            } else if (i9 == 1) {
                this.ivType.setImageResource(R.drawable.tab_ct_check);
                this.rgBottom.check(R.id.rbMode);
            } else if (i9 == 2) {
                this.ivType.setImageResource(R.drawable.tab_reg_check);
                this.rgBottom.check(R.id.rbCustom);
            }
        }
        this.iv_all11 = (ImageView) findViewById(R.id.iv_all11);
        this.iv_all22 = (ImageView) findViewById(R.id.iv_all22);
        this.iv_all11.setOnClickListener(new MyOnClickListener());
        this.iv_all22.setOnClickListener(new MyOnClickListener());
        Button bv_sure = (Button) findViewById(R.id.bv_sure);
        this.lv_alldevices = (ListView) findViewById(R.id.lv_alldevices);
        DeviceAdapter deviceAdapter = new DeviceAdapter();
        this.mAdapter = deviceAdapter;
        this.lv_alldevices.setAdapter((ListAdapter) deviceAdapter);
        this.lv_alldevices.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.home.activity.main.MainActivity_BLE.21
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i10, long j) {
                BluetoothDataModel bluetoothDataModel = LedBleApplication.getApp().getManmualBleDevices().get(i10);
                bluetoothDataModel.setSeleted(!bluetoothDataModel.isSeleted());
                MainActivity_BLE.this.mAdapter.notifyDataSetChanged();
            }
        });
        this.rl_alldevices = (RelativeLayout) findViewById(R.id.rl_alldevices);
        this.devices_connect = (LinearLayout) findViewById(R.id.devices_connect);
        bv_sure.setOnClickListener(new MyOnClickListener());
        if (SharePersistent.getPerference(getMainActivity(), Constant.AUTO_OR_MANUAL).equalsIgnoreCase("MANUAL")) {
            LedBleApplication.getApp().setAuto(false);
            LedBleApplication.getApp().setCanConnect(false);
            this.rl_alldevices.setVisibility(View.VISIBLE);
            this.iv_all22.setVisibility(View.VISIBLE);
            this.devices_connect.setVisibility(View.GONE);
            this.iv_all11.setVisibility(View.GONE);
            this.linearLayoutBottom.setVisibility(View.GONE);
        } else {
            LedBleApplication.getApp().setAuto(true);
            LedBleApplication.getApp().setCanConnect(true);
            this.iv_all11.setVisibility(View.VISIBLE);
            this.iv_all22.setVisibility(View.GONE);
        }
        this.brightness = SharePersistent.getInt(this, this.brightnessKey);
        this.speed = SharePersistent.getInt(this, this.speedKey);
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            return;
        }
        initBleScanList(this.isAllOn);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPixValue() {
        String perference;
        if (this.llPixNum1.getVisibility() == 0) {
            String str = "PixCar01Result" + SharePersistent.getPerference(getMainActivity(), Constant.CUSTOM_DIY_APPKEY);
            perference = getMainActivity() != null ? SharePersistent.getPerference(getApplicationContext(), str) : null;
            if (perference == null || perference.length() <= 0) {
                this.bannerPix = 200;
                SharePersistent.savePerference(getApplicationContext(), str, "200");
                if (sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
                    this.bannerPix = 60;
                    this.tvPixNum.setText("60");
                    SharePersistent.savePerference(getApplicationContext(), str, "60");
                    return;
                }
                return;
            }
            this.tvPixNum.setText(perference);
            this.bannerPix = Integer.parseInt(perference);
        } else if (this.llPixNum2.getVisibility() == 0) {
            String str2 = "PixCar01ResultPixLong" + SharePersistent.getPerference(getMainActivity(), Constant.CUSTOM_DIY_APPKEY);
            String perference2 = getMainActivity() != null ? SharePersistent.getPerference(getApplicationContext(), str2) : null;
            if (perference2 == null || perference2.length() <= 0) {
                this.bannerPix = 200;
                SharePersistent.savePerference(getApplicationContext(), str2, "200");
                this.tvPixLong.setText("200");
                if (sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
                    this.bannerPix = 60;
                    this.tvPixLong.setText("60");
                    SharePersistent.savePerference(getApplicationContext(), str2, "60");
                }
            } else {
                this.tvPixLong.setText(perference2);
                this.bannerPix = Integer.parseInt(perference2);
            }
            String perference3 = getMainActivity() != null ? SharePersistent.getPerference(getApplicationContext(), "PixCar01ResultPixWidth" + SharePersistent.getPerference(getMainActivity(), Constant.CUSTOM_DIY_APPKEY)) : null;
            if (perference3 == null || perference3.length() <= 0) {
                this.bannerPix = 200;
                this.tvPixWidth.setText("200");
                SharePersistent.savePerference(getApplicationContext(), perference3, "200");
                if (sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
                    this.bannerPix = 60;
                    this.tvPixWidth.setText("60");
                    SharePersistent.savePerference(getApplicationContext(), perference3, "60");
                }
            } else {
                this.tvPixWidth.setText(perference3);
                this.bannerPix = Integer.parseInt(perference3);
            }
            String str3 = "PixCar01ResultPixHigh" + SharePersistent.getPerference(getMainActivity(), Constant.CUSTOM_DIY_APPKEY);
            perference = getMainActivity() != null ? SharePersistent.getPerference(getApplicationContext(), str3) : null;
            if (perference == null || perference.length() <= 0) {
                this.bannerPix = 200;
                this.tvPixHigh.setText("200");
                SharePersistent.savePerference(getApplicationContext(), str3, "200");
                if (sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
                    this.bannerPix = 60;
                    this.tvPixHigh.setText("60");
                    SharePersistent.savePerference(getApplicationContext(), str3, "60");
                    return;
                }
                return;
            }
            this.tvPixHigh.setText(perference);
            this.bannerPix = Integer.parseInt(perference);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPixValue(final int i) {
        final EditText editText = new EditText(getMainActivity());
        if (this.llPixNum1.getVisibility() == 0) {
            editText.setHint("1~1024");
        } else if (this.llPixNum2.getVisibility() == 0) {
            editText.setHint("1~255");
        }
        new AlertDialog.Builder(getMainActivity()).setTitle(R.string.btn_pix_number).setIcon(17301659).setView(editText).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.23
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity_BLE.getMainActivity(), (int) R.string.input_format_error, 0).show();
                    return;
                }
                String obj = editText.getText().toString();
                if (!StringUtils.isNumeric(obj)) {
                    Toast.makeText(MainActivity_BLE.getMainActivity(), (int) R.string.key_in_numbers, 0).show();
                    return;
                }
                long parseLong = Long.parseLong(obj.trim());
                String valueOf = String.valueOf(parseLong);
                if (StringUtils.isEmpty(obj)) {
                    return;
                }
                if (MainActivity_BLE.this.llPixNum1.getVisibility() == 0) {
                    if (parseLong <= 0 || parseLong > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                        Toast.makeText(MainActivity_BLE.getMainActivity(), (int) R.string.input_format_error, 0).show();
                        return;
                    }
                    MainActivity_BLE.this.bannerPix = Integer.parseInt(valueOf.trim());
                    MainActivity_BLE.this.tvPixNum.setText(valueOf);
                    String str = "PixCar01Result" + SharePersistent.getPerference(MainActivity_BLE.getMainActivity(), Constant.CUSTOM_DIY_APPKEY);
                    if (MainActivity_BLE.getMainActivity() != null) {
                        SharePersistent.savePerference(MainActivity_BLE.this.getApplicationContext(), str, valueOf);
                    }
                } else if (MainActivity_BLE.this.llPixNum2.getVisibility() == 0) {
                    if (parseLong <= 0 || parseLong > 255) {
                        Toast.makeText(MainActivity_BLE.getMainActivity(), (int) R.string.input_format_error, 0).show();
                        return;
                    }
                    int i3 = i;
                    if (i3 == 1) {
                        MainActivity_BLE.this.tvPixLong.setText(valueOf);
                        String str2 = "PixCar01ResultPixLong" + SharePersistent.getPerference(MainActivity_BLE.getMainActivity(), Constant.CUSTOM_DIY_APPKEY);
                        if (MainActivity_BLE.getMainActivity() != null) {
                            SharePersistent.savePerference(MainActivity_BLE.this.getApplicationContext(), str2, valueOf);
                        }
                    } else if (i3 == 2) {
                        MainActivity_BLE.this.tvPixWidth.setText(valueOf);
                        String str3 = "PixCar01ResultPixWidth" + SharePersistent.getPerference(MainActivity_BLE.getMainActivity(), Constant.CUSTOM_DIY_APPKEY);
                        if (MainActivity_BLE.getMainActivity() != null) {
                            SharePersistent.savePerference(MainActivity_BLE.this.getApplicationContext(), str3, valueOf);
                        }
                    } else if (i3 == 3) {
                        MainActivity_BLE.this.tvPixHigh.setText(valueOf);
                        String str4 = "PixCar01ResultPixHigh" + SharePersistent.getPerference(MainActivity_BLE.getMainActivity(), Constant.CUSTOM_DIY_APPKEY);
                        if (MainActivity_BLE.getMainActivity() != null) {
                            SharePersistent.savePerference(MainActivity_BLE.this.getApplicationContext(), str4, valueOf);
                        }
                    }
                }
            }
        }).setNegativeButton(R.string.cancell_dialog, new DialogInterface.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.22
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    public void pauseMusicAndVolum(boolean z) {
        MusicFragment musicFragment = this.musicFragment;
        if (musicFragment != null) {
            musicFragment.pauseMusic();
            this.musicFragment.pauseVolum(true);
        }
    }

    public void back() {
        if (getMainActivity() != null) {
            SharePersistent.savePerference(getMainActivity(), Constant.CUSTOM_DIY_APPKEY, (String) null);
            LedBleApplication.getApp().setAuto(false);
        }
        finish();
    }

    /* loaded from: classes.dex */
    public class MyOnClickListener implements View.OnClickListener {
        public MyOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            MainActivity_BLE.this.pauseMusicAndVolum(true);
            switch (view.getId()) {
                case R.id.backTextView /* 2131296337 */:
                    MainActivity_BLE.this.back();
                    return;
                case R.id.btnTimerAdd /* 2131296392 */:
                    if (TimerFragment_sun.listDate_sun.size() < 15) {
                        MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, ChioceTimeActivity.class));
                        return;
                    } else {
                        Toast.makeText(MainActivity_BLE.mActivity, (int) R.string.supported, 1).show();
                        return;
                    }
                case R.id.btn_location_enable /* 2131296401 */:
                    MainActivity_BLE.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 222);
                    return;
                case R.id.btn_location_more /* 2131296402 */:
                    if (MainActivity_BLE.mActivity == null || MainActivity_BLE.mActivity.isFinishing()) {
                        return;
                    }
                    new AlertDialog.Builder(MainActivity_BLE.mActivity).setTitle(MainActivity_BLE.this.getResources().getString(R.string.position_service)).setMessage(MainActivity_BLE.this.getResources().getString(R.string.position_service_info)).setPositiveButton(MainActivity_BLE.this.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.MyOnClickListener.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                    return;
                case R.id.buttonAddGroup /* 2131296413 */:
                    MainActivity_BLE.this.addGroupMessage();
                    MainActivity_BLE.this.startAnimation(view);
                    return;
                case R.id.buttonAllOff /* 2131296414 */:
                    MainActivity_BLE.this.allOff();
                    MainActivity_BLE.this.startAnimation(view);
                    return;
                case R.id.buttonAllOn /* 2131296415 */:
                    MainActivity_BLE.this.allOn();
                    MainActivity_BLE.this.startAnimation(view);
                    return;
                case R.id.bv_sure /* 2131296438 */:
                    MainActivity_BLE.this.showConnect();
                    LedBleApplication.getApp().setCanConnect(true);
                    new Handler().postDelayed(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.MyOnClickListener.6
                        @Override // java.lang.Runnable
                        public void run() {
                            if (MainActivity_BLE.getMainActivity() == null || MainActivity_BLE.getMainActivity().isFinishing() || MainActivity_BLE.this.dialogConnect == null) {
                                return;
                            }
                            MainActivity_BLE.this.dialogConnect.dismiss();
                            MainActivity_BLE.this.dialogConnect = null;
                        }
                    }, 4000L);
                    if (LedBleApplication.getApp().getHashMapConnect().size() > 0) {
                        for (final String str : LedBleApplication.getApp().getHashMapConnect().keySet()) {
                            new Thread(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.MyOnClickListener.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    LedBleApplication.getApp().clearBleGatMap(str);
                                }
                            }).start();
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        LedBleApplication.getApp().getBleGattMap().clear();
                        new Handler().postDelayed(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.MyOnClickListener.8
                            @Override // java.lang.Runnable
                            public void run() {
                                LedBleApplication.getApp().getBleDevices().clear();
                                MainActivity_BLE.this.listDevices.clear();
                                Iterator<BluetoothDataModel> it = LedBleApplication.getApp().getManmualBleDevices().iterator();
                                while (it.hasNext()) {
                                    BluetoothDataModel next = it.next();
                                    if (next.isSeleted()) {
                                        MainActivity_BLE.this.listDevices.add(next.getDevice());
                                    }
                                }
                                LedBleApplication.getApp().getBleDevices().addAll(MainActivity_BLE.this.listDevices);
                                ListenerManager.getInstance().sendBroadCast(Constant.ManualModeConnectDevice);
                            }
                        }, 2000L);
                        return;
                    }
                    LedBleApplication.getApp().getBleDevices().clear();
                    MainActivity_BLE.this.listDevices.clear();
                    Iterator<BluetoothDataModel> it = LedBleApplication.getApp().getManmualBleDevices().iterator();
                    while (it.hasNext()) {
                        BluetoothDataModel next = it.next();
                        if (next.isSeleted()) {
                            MainActivity_BLE.this.listDevices.add(next.getDevice());
                        }
                    }
                    LedBleApplication.getApp().getBleDevices().addAll(MainActivity_BLE.this.listDevices);
                    ListenerManager.getInstance().sendBroadCast(Constant.ManualModeConnectDevice);
                    return;
                case R.id.change_under_pic_tv /* 2131296479 */:
                    MainActivity_BLE.this.showPicturePicker();
                    return;
                case R.id.code_tv /* 2131296509 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, CodeActivity.class));
                    return;
                case R.id.currentquery_tv /* 2131296524 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, CurrentQueryActivity.class));
                    SharePersistent.savePerference(MainActivity_BLE.mActivity, Constant.Activity, Constant.CurrentQueryActivity);
                    return;
                case R.id.dynamic_breath_iv /* 2131296563 */:
                    MainActivity_BLE.this.setDynamicModel(129);
                    MainActivity_BLE.this.showSure(view);
                    return;
                case R.id.dynamic_gradient_iv /* 2131296564 */:
                    MainActivity_BLE.this.setDynamicModel(131);
                    MainActivity_BLE.this.showSure(view);
                    return;
                case R.id.dynamic_jump_iv /* 2131296565 */:
                    MainActivity_BLE.this.setDynamicModel(128);
                    MainActivity_BLE.this.showSure(view);
                    return;
                case R.id.dynamic_strobe_iv /* 2131296566 */:
                    MainActivity_BLE.this.setDynamicModel(130);
                    MainActivity_BLE.this.showSure(view);
                    return;
                case R.id.dynamic_tv /* 2131296567 */:
                    if (MainActivity_BLE.mActivity == null || MainActivity_BLE.mActivity.isFinishing()) {
                        return;
                    }
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, DynamicColorActivity.class));
                    return;
                case R.id.fan_rotational_temperature_tv /* 2131296593 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, OtherSettingActivity.class));
                    return;
                case R.id.ivLeftMenu /* 2131296744 */:
                    MainActivity_BLE.this.mDrawerLayout.openDrawer(MainActivity_BLE.this.left_menu);
                    return;
                case R.id.ivRefresh /* 2131296751 */:
                    if (LedBleApplication.getApp().isAuto()) {
                        LedBleApplication.getApp().setCanConnect(true);
                    } else {
                        LedBleApplication.getApp().setCanConnect(false);
                        for (int i = 0; i < LedBleApplication.getApp().getManmualBleDevices().size(); i++) {
                            if (!LedBleApplication.getApp().getManmualBleDevices().get(i).isSeleted()) {
                                LedBleApplication.getApp().getManmualBleDevices().remove(i);
                            }
                        }
                        if (MainActivity_BLE.this.mAdapter != null) {
                            MainActivity_BLE.this.mAdapter.notifyDataSetChanged();
                        }
                    }
                    MainActivity_BLE.this.refreshDevices();
                    MainActivity_BLE.this.startAnimation(view);
                    return;
                case R.id.ivRightMenu /* 2131296754 */:
                    MainActivity_BLE.this.mDrawerLayout.openDrawer(MainActivity_BLE.this.right_menu);
                    return;
                case R.id.ivType /* 2131296762 */:
                    if (MainActivity_BLE.this.currentIndex != 0) {
                        if (MainActivity_BLE.this.currentIndex != 1) {
                            if (MainActivity_BLE.this.currentIndex == 2) {
                                MainActivity_BLE.this.ivType.setImageResource(R.drawable.tab_dim_check);
                                MainActivity_BLE.this.rgBottom.check(R.id.rbRGB);
                                return;
                            }
                            return;
                        }
                        MainActivity_BLE.this.ivType.setImageResource(R.drawable.tab_reg_check);
                        MainActivity_BLE.this.rgBottom.check(R.id.rbCustom);
                        return;
                    }
                    MainActivity_BLE.this.ivType.setImageResource(R.drawable.tab_ct_check);
                    MainActivity_BLE.this.rgBottom.check(R.id.rbMode);
                    return;
                case R.id.iv_all11 /* 2131296771 */:
                    MainActivity_BLE.this.backTextView.setVisibility(4);
                    LedBleApplication.getApp().setAuto(false);
                    MainActivity_BLE.this.rl_alldevices.setVisibility(View.VISIBLE);
                    MainActivity_BLE.this.iv_all22.setVisibility(View.VISIBLE);
                    MainActivity_BLE.this.devices_connect.setVisibility(View.GONE);
                    MainActivity_BLE.this.iv_all11.setVisibility(View.GONE);
                    MainActivity_BLE.this.linearLayoutBottom.setVisibility(View.GONE);
                    MainActivity_BLE.this.clearList();
                    new Handler().postDelayed(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.MyOnClickListener.4
                        @Override // java.lang.Runnable
                        public void run() {
                            if (MainActivity_BLE.getMainActivity() == null || MainActivity_BLE.getMainActivity().isFinishing()) {
                                return;
                            }
                            if (MainActivity_BLE.this.dialogDisconnect != null) {
                                MainActivity_BLE.this.dialogDisconnect.dismiss();
                                MainActivity_BLE.this.dialogDisconnect = null;
                            }
                            LedBleApplication.getApp().setCanConnect(false);
                            MainActivity_BLE.this.refreshDevices();
                            MainActivity_BLE.this.backTextView.setVisibility(View.VISIBLE);
                        }
                    }, 2000L);
                    SharePersistent.savePerference(MainActivity_BLE.getMainActivity(), Constant.AUTO_OR_MANUAL, "MANUAL");
                    return;
                case R.id.iv_all22 /* 2131296772 */:
                    MainActivity_BLE.this.backTextView.setVisibility(4);
                    LedBleApplication.getApp().setAuto(true);
                    MainActivity_BLE.this.devices_connect.setVisibility(View.VISIBLE);
                    MainActivity_BLE.this.iv_all11.setVisibility(View.VISIBLE);
                    MainActivity_BLE.this.rl_alldevices.setVisibility(View.GONE);
                    MainActivity_BLE.this.iv_all22.setVisibility(View.GONE);
                    MainActivity_BLE.this.linearLayoutBottom.setVisibility(View.VISIBLE);
                    MainActivity_BLE.this.clearList();
                    new Handler().postDelayed(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.MyOnClickListener.5
                        @Override // java.lang.Runnable
                        public void run() {
                            if (MainActivity_BLE.this.dialogDisconnect != null) {
                                MainActivity_BLE.this.dialogDisconnect.dismiss();
                                MainActivity_BLE.this.dialogDisconnect = null;
                            }
                            LedBleApplication.getApp().setCanConnect(true);
                            MainActivity_BLE.this.refreshDevices();
                            MainActivity_BLE.this.backTextView.setVisibility(View.VISIBLE);
                        }
                    }, 2000L);
                    SharePersistent.savePerference(MainActivity_BLE.getMainActivity(), Constant.AUTO_OR_MANUAL, "AUTO");
                    return;
                case R.id.linearLayoutTopItem /* 2131296850 */:
                    MainActivity_BLE.this.showActionSheet("");
                    return;
                case R.id.lock_iv /* 2131296993 */:
                    MainActivity_BLE.this.setPairCode(1);
                    MainActivity_BLE.this.showSure(view);
                    return;
                case R.id.mode_select_tv /* 2131297022 */:
                    MainActivity_BLE.this.startActivityForResult(new Intent(MainActivity_BLE.mActivity, ModeSelectActivity.class), MainActivity_BLE.this.INT_GO_CHANGEMODE);
                    return;
                case R.id.onOffButton /* 2131297049 */:
                    if (MainActivity_BLE.this.isLightOpen) {
                        MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.off_btn);
                        if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
                            if (MainActivity_BLE.this.rgbFragment.checkRelativeTab3() && MainActivity_BLE.this.currentIndex == 0) {
                                MainActivity_BLE.this.bledmxclose();
                            } else if (MainActivity_BLE.this.rgbFragment.checkRelativeTabDMXAisle()) {
                                MainActivity_BLE mainActivity_BLE = MainActivity_BLE.this;
                                mainActivity_BLE.close(true, mainActivity_BLE.rgbFragment.isCAR01DMX(), MainActivity_BLE.this.rgbFragment.isCAR01LED());
                            } else {
                                MainActivity_BLE mainActivity_BLE2 = MainActivity_BLE.this;
                                mainActivity_BLE2.close(false, mainActivity_BLE2.rgbFragment.isCAR01DMX(), MainActivity_BLE.this.rgbFragment.isCAR01LED());
                            }
                        } else if (MainActivity_BLE.this.rgbFragment != null) {
                            MainActivity_BLE mainActivity_BLE3 = MainActivity_BLE.this;
                            mainActivity_BLE3.close(false, mainActivity_BLE3.rgbFragment.isCAR01DMX(), MainActivity_BLE.this.rgbFragment.isCAR01LED());
                        } else {
                            MainActivity_BLE.this.close(false, false, false);
                        }
                        MainActivity_BLE.this.isLightOpen = false;
                        return;
                    }
                    MainActivity_BLE.this.onOffButton.setBackgroundResource(R.drawable.on_btn);
                    if (MainActivity_BLE.sceneBean.contains(CommonConstant.LEDBLE) || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-00-") || MainActivity_BLE.sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
                        if (MainActivity_BLE.this.rgbFragment.checkRelativeTab3() && MainActivity_BLE.this.currentIndex == 0) {
                            MainActivity_BLE.this.bledmxopen();
                        } else if (MainActivity_BLE.this.rgbFragment.checkRelativeTabDMXAisle()) {
                            MainActivity_BLE mainActivity_BLE4 = MainActivity_BLE.this;
                            mainActivity_BLE4.open(true, mainActivity_BLE4.rgbFragment.isCAR01DMX(), MainActivity_BLE.this.rgbFragment.isCAR01LED());
                        } else {
                            MainActivity_BLE mainActivity_BLE5 = MainActivity_BLE.this;
                            mainActivity_BLE5.open(false, mainActivity_BLE5.rgbFragment.isCAR01DMX(), MainActivity_BLE.this.rgbFragment.isCAR01LED());
                        }
                    } else if (MainActivity_BLE.this.rgbFragment != null) {
                        MainActivity_BLE mainActivity_BLE6 = MainActivity_BLE.this;
                        mainActivity_BLE6.open(false, mainActivity_BLE6.rgbFragment.isCAR01DMX(), MainActivity_BLE.this.rgbFragment.isCAR01LED());
                    } else {
                        MainActivity_BLE.this.open(false, false, false);
                    }
                    MainActivity_BLE.this.isLightOpen = true;
                    return;
                case R.id.reset_tv /* 2131297199 */:
                    MainActivity_BLE.this.imageView.setImageDrawable(MainActivity_BLE.this.getResources().getDrawable(R.drawable.bg_all));
                    MainActivity_BLE.this.getImagePath();
                    MainActivity_BLE.this.saveImagePathToSharedPreferences("");
                    return;
                case R.id.rgb_sort_tv /* 2131297210 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, RgbSortActivity.class));
                    return;
                case R.id.set_tv /* 2131297393 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, ChipSelectActivity.class));
                    return;
                case R.id.timer_check_tv /* 2131297529 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, TimingQueryActivity.class));
                    SharePersistent.savePerference(MainActivity_BLE.mActivity, Constant.TimingQueryActivity, "");
                    SharePersistent.savePerference(MainActivity_BLE.mActivity, Constant.Activity, Constant.TimingQueryActivity);
                    return;
                case R.id.tvBtnBLE00_1 /* 2131297595 */:
                case R.id.tv_btn1 /* 2131297747 */:
                    MainActivity_BLE.this.setAuxiliary(0, view);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.MyOnClickListener.2
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity_BLE.this.setPairCode(1);
                            handler.removeCallbacks(this);
                        }
                    }, 100L);
                    return;
                case R.id.tvBtnBLE00_2 /* 2131297596 */:
                case R.id.tv_btn2 /* 2131297748 */:
                    MainActivity_BLE.this.setAuxiliary(1, view);
                    final Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.MyOnClickListener.3
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity_BLE.this.setPairCode(0);
                            handler2.removeCallbacks(this);
                        }
                    }, 100L);
                    return;
                case R.id.tvBtnBLE00_3 /* 2131297597 */:
                case R.id.tv_btn3 /* 2131297749 */:
                    MainActivity_BLE.this.setAuxiliary(2, view);
                    return;
                case R.id.tvBtquery /* 2131297598 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, TBQueryActivity.class));
                    return;
                case R.id.tvChannelsetStage /* 2131297600 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, ChannelSetActivity.class));
                    return;
                case R.id.tvCodeStage /* 2131297603 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, CodeActivity_WIFI.class));
                    return;
                case R.id.tvTimerBLE /* 2131297711 */:
                case R.id.tvTimerBLE00 /* 2131297712 */:
                case R.id.tvTimerDMX /* 2131297713 */:
                    if (MainActivity_BLE.mActivity != null) {
                        Intent intent = new Intent(MainActivity_BLE.mActivity, TimeActivity.class);
                        intent.putExtra("name", "1");
                        MainActivity_BLE.this.startActivity(intent);
                        return;
                    }
                    return;
                case R.id.tvTimerStage /* 2131297714 */:
                    Intent intent2 = new Intent(MainActivity_BLE.mActivity, TimeActivity.class);
                    intent2.putExtra("name", ExifInterface.GPS_MEASUREMENT_3D);
                    MainActivity_BLE.this.startActivity(intent2);
                    return;
                case R.id.tvVoicecontrolDMX /* 2131297726 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, VoiceCtlActivity.class));
                    return;
                case R.id.tv_auxiliary /* 2131297742 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, AuxiliaryActivity.class));
                    return;
                case R.id.tv_auxiliary_ble /* 2131297743 */:
                    MainActivity_BLE.this.startActivity(new Intent(MainActivity_BLE.mActivity, AuxiliaryActivity.class));
                    return;
                case R.id.tv_btn4 /* 2131297750 */:
                    MainActivity_BLE.this.setAuxiliary(3, view);
                    return;
                case R.id.tv_car00_btn1 /* 2131297753 */:
                    MainActivity_BLE.this.setAuxiliary(0, view);
                    return;
                case R.id.tv_car00_btn2 /* 2131297754 */:
                    MainActivity_BLE.this.setAuxiliary(1, view);
                    return;
                case R.id.tv_car00_btn3 /* 2131297755 */:
                    MainActivity_BLE.this.setAuxiliary(2, view);
                    return;
                case R.id.tv_car00_btn4 /* 2131297756 */:
                    MainActivity_BLE.this.setAuxiliary(3, view);
                    return;
                case R.id.tv_car01_btn1 /* 2131297758 */:
                    MainActivity_BLE.this.setAuxiliary(0, view);
                    return;
                case R.id.tv_car01_btn2 /* 2131297759 */:
                    MainActivity_BLE.this.setAuxiliary(1, view);
                    return;
                case R.id.tv_dmx_btn1 /* 2131297779 */:
                    MainActivity_BLE.this.setAuxiliary(0, view);
                    return;
                case R.id.tv_dmx_btn2 /* 2131297780 */:
                    MainActivity_BLE.this.setAuxiliary(1, view);
                    return;
                case R.id.tv_dmx_btn3 /* 2131297781 */:
                    MainActivity_BLE.this.setAuxiliary(2, view);
                    return;
                case R.id.tv_dmx_btn4 /* 2131297782 */:
                    MainActivity_BLE.this.setAuxiliary(3, view);
                    return;
                case R.id.unlock_iv /* 2131297864 */:
                    MainActivity_BLE.this.setPairCode(0);
                    MainActivity_BLE.this.showSure(view);
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimation(View view) {
        if (getApplicationContext() != null) {
            view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.layout_scale));
        }
    }

    public static void showToast(Context context, String str) {
        BamToast.showText(context, str);
    }

    public void clearList() {
        if (LedBleApplication.getApp().getBleGattMap().size() > 0) {
            showDisconnect();
            for (final String str : LedBleApplication.getApp().getBleGattMap().keySet()) {
                new Thread(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.24
                    @Override // java.lang.Runnable
                    public void run() {
                        LedBleApplication.getApp().clearBleGatMap(str);
                    }
                }).start();
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LedBleApplication.getApp().getBleGattMap().clear();
        }
        LedBleApplication.getApp().getBleDevices().clear();
        LedBleApplication.getApp().getHashMapConnect().clear();
        LedBleApplication.getApp().getManmualBleDevices().clear();
        this.mAdapter.notifyDataSetChanged();
        updateNewFindDevice();
    }

    /* loaded from: classes.dex */
    class ViewHolder {
        private ImageView imageView;
        private TextView textView;

        ViewHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class DeviceAdapter extends BaseAdapter {
        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        private DeviceAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return LedBleApplication.getApp().getManmualBleDevices().size();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            ViewHolder viewHolder;
            BluetoothDataModel bluetoothDataModel = LedBleApplication.getApp().getManmualBleDevices().get(i);
            if (view == null) {
                viewHolder = new ViewHolder();
                view2 = LayoutInflater.from(MainActivity_BLE.this).inflate(R.layout.listlayout, (ViewGroup) null);
                viewHolder.imageView = (ImageView) view2.findViewById(R.id.iv_sure);
                viewHolder.textView = (TextView) view2.findViewById(R.id.tv);
                view2.setTag(viewHolder);
            } else {
                view2 = view;
                viewHolder = (ViewHolder) view.getTag();
            }
            if (bluetoothDataModel.isSeleted()) {
                viewHolder.imageView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imageView.setVisibility(View.GONE);
            }
            viewHolder.textView.setText(bluetoothDataModel.getDevice().getName());
            return view2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showConnect() {
        MainActivity_BLE mainActivity_BLE = mActivity;
        if (mainActivity_BLE == null || mainActivity_BLE.isFinishing() || this.dialogConnect != null) {
            return;
        }
        Dialog dialog = new Dialog(mActivity, 16973840);
        this.dialogConnect = dialog;
        dialog.requestWindowFeature(1024);
        this.dialogConnect.setContentView(R.layout.dialogview_connect);
        ((TextView) this.dialogConnect.findViewById(R.id.dialodTv)).setText(getString(R.string.connecting));
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(2000L);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(1);
        rotateAnimation.setStartTime(-1L);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        ((ImageView) this.dialogConnect.findViewById(R.id.imageViewWait)).startAnimation(rotateAnimation);
        Dialog dialog2 = this.dialogConnect;
        if (dialog2 != null) {
            dialog2.show();
        }
    }

    private void showDisconnect() {
        MainActivity_BLE mainActivity_BLE = mActivity;
        if (mainActivity_BLE == null || mainActivity_BLE.isFinishing() || this.dialogDisconnect != null) {
            return;
        }
        Dialog dialog = new Dialog(mActivity, 16973840);
        this.dialogDisconnect = dialog;
        dialog.requestWindowFeature(1024);
        this.dialogDisconnect.setContentView(R.layout.dialogview_scan);
        ((TextView) this.dialogDisconnect.findViewById(R.id.dialodTv)).setText(getString(R.string.disconnect));
        ((ImageView) this.dialogDisconnect.findViewById(R.id.imageViewWait)).setVisibility(View.GONE);
        Dialog dialog2 = this.dialogDisconnect;
        if (dialog2 != null) {
            dialog2.show();
        }
    }

    private void showCustomMessage() {
        MainActivity_BLE mainActivity_BLE = mActivity;
        if (mainActivity_BLE == null || mainActivity_BLE.isFinishing() || this.lDialog != null) {
            return;
        }
        Dialog dialog = new Dialog(this, 16973840);
        this.lDialog = dialog;
        dialog.requestWindowFeature(1024);
        this.lDialog.setContentView(R.layout.dialogview_scan);
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(2000L);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(1);
        rotateAnimation.setStartTime(-1L);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        ((ImageView) this.lDialog.findViewById(R.id.imageViewWait)).startAnimation(rotateAnimation);
        this.lDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSure(View view) {
        if (mActivity != null) {
            startAnimation(view);
        }
    }

    private boolean isAndroid12() {
        return Build.VERSION.SDK_INT >= 31;
    }

    public void turnOnBluetooth() {
        if (isAndroid12()) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_SCAN") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_CONNECT") != 0) {
                requestPermissions(new String[]{"android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_ADVERTISE", "android.permission.BLUETOOTH_CONNECT"}, REQUEST_BLUETOOTH_SCAN);
            } else if (this.turnOnBluetoothIntent == null) {
                Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                this.turnOnBluetoothIntent = intent;
                startActivityForResult(intent, this.OPEN_BLE);
            }
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, REQUEST_CODE_PERMISSION_LOCATION);
            }
        } else if (this.turnOnBluetoothIntent == null) {
            Intent intent2 = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
            this.turnOnBluetoothIntent = intent2;
            startActivityForResult(intent2, this.OPEN_BLE);
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
            MainActivity_BLE mainActivity_BLE = mActivity;
            if (mainActivity_BLE == null || mainActivity_BLE.isFinishing()) {
                return;
            }
            final NiftyDialogBuilder niftyDialogBuilder = new NiftyDialogBuilder(mActivity, R.style.dialog_user_agreement_and_privacy_policy);
            Effectstype effect = Effectstype.SlideBottom;
            niftyDialogBuilder.setCancelable(false);
            niftyDialogBuilder.withTitle(getResources().getString(R.string.access_request)).withTitleColor("#000000").withDividerColor("#11000000").isCancelableOnTouchOutside(false).withDuration(200).withEffect(effect).withButton1Text(getResources().getString(R.string.cancel)).withButton2Text(getResources().getString(R.string.agree));
            niftyDialogBuilder.setCustomView(R.layout.activity_spannable, getApplicationContext());
            TextView textView = (TextView) niftyDialogBuilder.getContentView().findViewById(R.id.span_builder);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setHighlightColor(getResources().getColor(17170445));
            textView.setText(getBuilder());
            niftyDialogBuilder.setButton1Click(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.26
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (niftyDialogBuilder.isShowing()) {
                        niftyDialogBuilder.dismiss();
                    }
                }
            }).setButton2Click(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.25
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (niftyDialogBuilder.isShowing()) {
                        niftyDialogBuilder.dismiss();
                    }
                    ActivityCompat.requestPermissions(MainActivity_BLE.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, MainActivity_BLE.REQUEST_CODE_PERMISSION_LOCATION);
                }
            }).show();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, REQUEST_CODE_PERMISSION_LOCATION);
    }

    public void initBleScanList(boolean z) {
        if (!this.isInitGroup) {
            initGroup(z);
            this.isInitGroup = true;
        }
        if (LedBleApplication.getApp().getBleGattMap().size() > 0) {
            updateNewFindDevice();
        } else {
            refreshDevices();
        }
    }

    protected void refreshDevices() {
        if (ServicesFragment.getBluetoothAdapter() != null && !ServicesFragment.getBluetoothAdapter().isEnabled()) {
            turnOnBluetooth();
        } else if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            requestLocationPermission();
        } else {

            showCustomMessage();

            startLeScan(true);
            new Handler().postDelayed(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.27
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.stopLeScan(true);
                }
            }, 6000L);
        }
    }

    protected void startLeScan(boolean z) {
        ListenerManager.getInstance().sendBroadCast(Constant.StartLeScan);
        if (z) {
            showCustomMessage();
        }
    }

    protected void stopLeScan(boolean z) {
        ListenerManager.getInstance().sendBroadCast(Constant.StopLeScan);
        if (z) {
            try {
                Dialog dialog = this.lDialog;
                if (dialog != null && dialog.isShowing()) {
                    this.lDialog.dismiss();
                }
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.lDialog = null;
                throw th;
            }
            this.lDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addGroupMessage() {
        if (mActivity != null) {
            final EditText editText = new EditText(mActivity);
            new AlertDialog.Builder(mActivity).setTitle(R.string.please_input).setIcon(17301659).setView(editText).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.29
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    String obj = editText.getText().toString();
                    Iterator<Group> it = new GroupDeviceDao(MainActivity_BLE.mActivity).getAllgroup().iterator();
                    while (it.hasNext()) {
                        if (it.next().getGroupName().equalsIgnoreCase(obj)) {
                            Tool.ToastShow(MainActivity_BLE.mActivity, (int) R.string.groupname_cannot_same);
                            return;
                        }
                    }
                    if (StringUtils.isEmpty(obj)) {
                        return;
                    }
                    MainActivity_BLE.this.addGroupByName(obj);
                }
            }).setNegativeButton(R.string.cancell_dialog, new DialogInterface.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.28
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addGroupByName(String str) {
        try {
            new GroupDeviceDao(mActivity).addGroup(str);
            addGroupView(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addGroupView(final String str) {
        final GroupView groupView = new GroupView(mActivity, str, this.isAllOn);
        final SlideSwitch slideSwitch = groupView.getSlideSwitch();
        this.linearGroups.addView(groupView.getGroupView());
        this.map.put(str, slideSwitch);
        slideSwitch.setStateNoListener(false);
        slideSwitch.setSlideListener(new SlideSwitch.SlideListener() { // from class: com.home.activity.main.MainActivity_BLE.30
            @Override // com.home.view.SlideSwitch.SlideListener
            public void open() {
                MainActivity_BLE.this.changeStatus(str);
            }

            @Override // com.home.view.SlideSwitch.SlideListener
            public void close() {
                slideSwitch.setStateNoListener(true);
            }
        });
        groupView.getGroupView().setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (groupView.isTurnOn()) {
                    if (groupView.getConnect() > 0) {
                        return;
                    }
                    Tool.ToastShow(MainActivity_BLE.mActivity, (int) R.string.edit_group_please);
                    MainActivity_BLE.this.showActionSheet(str);
                    return;
                }
                MainActivity_BLE.this.showActionSheet(str);
            }
        });
        groupView.getGroupView().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.home.activity.main.MainActivity_BLE.32
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                MainActivity_BLE.this.showDeleteDialog(str);
                return true;
            }
        });
        this.arrayListGroupViews.add(groupView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeStatus(String str) {
        setGroupName(str);
        for (String str2 : this.map.keySet()) {
            if (!str.equals(str2)) {
                this.map.get(str2).setStateNoListener(false);
            }
        }
    }

    private void gotoEdit(String str) {
        Intent intent = new Intent(mActivity, DeviceListActivity.class);
        intent.putExtra("group", str);
        ArrayList<GroupDevice> devicesByGroup = new GroupDeviceDao(mActivity).getDevicesByGroup(str);
        if (!ListUtiles.isEmpty(devicesByGroup)) {
            intent.putExtra("devices", devicesByGroup);
        }
        startActivityForResult(intent, 111);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImagePathToSharedPreferences(String str) {
        SharedPreferences.Editor edit = getSharedPreferences(Constant.IMAGE_VALUE, 0).edit();
        edit.putString(Constant.IMAGE_VALUE, str);
        edit.commit();
    }

    public void showPicturePicker() {
        if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <= 32) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, this.STORAGE_CODE);
                return;
            }
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 1);
            }
        } else if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_MEDIA_AUDIO") != 0 || ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_MEDIA_IMAGES") != 0 || ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_MEDIA_VIDEO") != 0) {
                requestPermissions(new String[]{"android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"}, this.STORAGE_CODE);
                return;
            }
            Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
            intent2.setType("image/*");
            if (intent2.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent2, 1);
            }
        } else {
            Intent intent3 = new Intent("android.intent.action.GET_CONTENT");
            intent3.setType("image/*");
            if (intent3.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent3, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getImagePath() {
        SharedPreferences sharedPreferences2 = getSharedPreferences(Constant.IMAGE_VALUE, 0);
        this.sp = sharedPreferences2;
        this.editor = sharedPreferences2.edit();
        return this.sp.getString(Constant.IMAGE_VALUE, "");
    }

    private void showImage(String str) {
        Bitmap bitmap = this.bm;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bm.recycle();
            this.bm = null;
            System.gc();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inTempStorage = new byte[5120];
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        if (getSceneBean().equalsIgnoreCase("LEDCAR-00-") || getSceneBean().equalsIgnoreCase("LEDCAR-01-")) {
            return;
        }
        this.imageView.setImageBitmap(decodeFile);
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        Uri uri2 = null;
        if ((Build.VERSION.SDK_INT >= 19) && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else {
                if (isMediaDocument(uri)) {
                    String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str = split2[0];
                    if (PictureMimeType.MIME_TYPE_PREFIX_IMAGE.equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if (PictureMimeType.MIME_TYPE_PREFIX_VIDEO.equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if (PictureMimeType.MIME_TYPE_PREFIX_AUDIO.equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
                }
            }
        } else if (Utils.RESPONSE_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else {
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndexOrThrow("_data"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            Throwable th = th2;
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private void updateNewFindDevice() {
        if (LedBleApplication.getApp().getBleDevices() != null) {
            this.textViewAllDeviceIndicater.setText(getResources().getString(R.string.conenct_device, String.valueOf(LedBleApplication.getApp().getHashMapConnect().size()), String.valueOf(LedBleApplication.getApp().getHashMapConnect().size())));
            if (this.groupName.equalsIgnoreCase("")) {
                this.textViewConnectCount.setText(LedBleApplication.getApp().getHashMapConnect().size());
            }
        }
        if (LedBleApplication.getApp().isAuto()) {
            return;
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void initGroup(boolean z) {
        Iterator<Group> it = new GroupDeviceDao(mActivity).getAllgroup().iterator();
        while (it.hasNext()) {
            Group next = it.next();
            addGroupViewFromInit(next.getGroupName(), next.getIsOn(), z);
        }
    }

    private void addGroupViewFromInit(final String str, String str2, boolean z) {
        final GroupView groupView = new GroupView(mActivity, str, z);
        ArrayList<GroupDevice> devicesByGroup = new GroupDeviceDao(mActivity).getDevicesByGroup(str);
        if (!ListUtiles.isEmpty(devicesByGroup)) {
            groupView.setGroupDevices(devicesByGroup);
        }
        final SlideSwitch slideSwitch = groupView.getSlideSwitch();
        this.map.put(str, slideSwitch);
        slideSwitch.setStateNoListener(false);
        slideSwitch.setSlideListener(new SlideSwitch.SlideListener() { // from class: com.home.activity.main.MainActivity_BLE.33
            @Override // com.home.view.SlideSwitch.SlideListener
            public void open() {
                MainActivity_BLE.this.changeStatus(str);
            }

            @Override // com.home.view.SlideSwitch.SlideListener
            public void close() {
                slideSwitch.setStateNoListener(true);
            }
        });
        groupView.getGroupView().setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.34
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (groupView.isTurnOn()) {
                    if (groupView.getConnect() > 0) {
                        return;
                    }
                    Tool.ToastShow(MainActivity_BLE.mActivity, (int) R.string.edit_group_please);
                    MainActivity_BLE.this.showActionSheet(str);
                    return;
                }
                MainActivity_BLE.this.showActionSheet(str);
            }
        });
        this.linearGroups.addView(groupView.getGroupView());
        groupView.getGroupView().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.home.activity.main.MainActivity_BLE.35
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                MainActivity_BLE.this.showDeleteDialog(str);
                return true;
            }
        });
        this.arrayListGroupViews.add(groupView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteDialog(final String str) {
        new AlertDialog.Builder(mActivity).setTitle(getResources().getString(R.string.tips)).setMessage(getResources().getString(R.string.delete_group, str)).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.37
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                try {
                    GroupDeviceDao groupDeviceDao = new GroupDeviceDao(MainActivity_BLE.mActivity);
                    groupDeviceDao.deleteGroup(str);
                    groupDeviceDao.delteByGroup(str);
                    MainActivity_BLE.this.linearGroups.removeView(MainActivity_BLE.this.linearGroups.findViewWithTag(str));
                    MainActivity_BLE.this.map.remove(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).setNegativeButton(R.string.cancell_dialog, new DialogInterface.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.36
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    private void save2GroupByGroupName(String str, Set<BluetoothDevice> set) throws Exception {
        GroupDeviceDao groupDeviceDao = new GroupDeviceDao(mActivity);
        groupDeviceDao.delteByGroup(str);
        ArrayList<GroupDevice> arrayList = new ArrayList<>();
        for (BluetoothDevice bluetoothDevice : set) {
            GroupDevice groupDevice = new GroupDevice();
            groupDevice.setAddress(bluetoothDevice.getAddress());
            groupDevice.setGroupName(str);
            arrayList.add(groupDevice);
        }
        groupDeviceDao.save2Group(arrayList);
    }

    private int findConnectCount(ArrayList<GroupDevice> arrayList) {
        int i = 0;
        if (ListUtiles.isEmpty(arrayList)) {
            return 0;
        }
        Iterator<GroupDevice> it = arrayList.iterator();
        while (it.hasNext()) {
            GroupDevice next = it.next();
            if (LedBleApplication.getApp().getHashMapConnect().containsKey(next.getAddress()) && LedBleApplication.getApp().getHashMapConnect().get(next.getAddress()).booleanValue()) {
                i++;
            }
        }
        return i;
    }

    public void showActionSheet(String str) {
        NetConnectBle.getInstanceByGroup(str);
        if (str.equalsIgnoreCase("")) {
            ActionSheet.createBuilder(this, getFragmentManager()).setCancelItem(new ActionSheet.Item(R.color.white, R.color.white, 0, 0, R.color.colorPrimary, R.color.white, getResources().getString(R.string.text_cancel), 1.0f)).setmOtherItems(new ActionSheet.Item(R.color.white, R.color.white, R.drawable.tab_ct, R.drawable.tab_ct, R.color.colorPrimary, R.color.white, getResources().getString(R.string.control), 1.0f)).setGroupName(str).setCancelableOnTouchOutside(true).setListener(this).show();
            return;
        }
        ActionSheet.createBuilder(this, getFragmentManager()).setCancelItem(new ActionSheet.Item(R.color.white, R.color.white, 0, 0, R.color.colorPrimary, R.color.white, getResources().getString(R.string.text_cancel), 1.0f)).setmOtherItems(new ActionSheet.Item(R.color.white, R.color.white, R.drawable.tab_ct, R.drawable.tab_ct, R.color.colorPrimary, R.color.white, getResources().getString(R.string.control), 1.0f), new ActionSheet.Item(R.color.white, R.color.white, R.drawable.tab_ct, R.drawable.tab_ct, R.color.colorPrimary, R.color.white, getResources().getString(R.string.add_device), 1.0f)).setGroupName(str).setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override // com.home.view.ActionSheet.ActionSheetListener
    public void onOtherButtonClick(ActionSheet actionSheet, int i, String str) {
        if (i != 0) {
            if (i != 1) {
                return;
            }
            gotoEdit(str);
        } else if (LedBleApplication.getApp().getHashMapConnect().size() > 0) {
            this.groupName = str;
            if (str.equalsIgnoreCase("")) {
                this.textViewConnectCount.setText(Integer.toString(LedBleApplication.getApp().getHashMapConnect().size()));
            } else {
                this.textViewConnectCount.setText(Integer.toString(NetConnectBle.getInstance().getDevicesByGroup().size()));
            }
        } else {
            Toast.makeText(getApplicationContext(), (int) R.string.no_device_found, 0).show();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 110) {
            if (iArr.length <= 0 || iArr[0] != 0 || Build.VERSION.SDK_INT < 23) {
                return;
            }
            LedBleApplication.getApp().setCanConnect(LedBleApplication.getApp().isAuto());
            refreshDevices();
        } else if (i == 111) {
            if (iArr.length > 0 && iArr[0] == 0) {
                Toast.makeText(mActivity, getString(R.string.micro_authorized), Toast.LENGTH_SHORT).show();
                this.musicFragment.requestMicroPermissionsSucess(true);
                return;
            }
            this.musicFragment.requestMicroPermissionsSucess(false);
        } else if (i == 200) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                return;
            }
            this.musicFragment.request();
        } else {
            switch (i) {
                case REQUEST_CODE_PERMISSION_LOCATION /* 223 */:
                    if (iArr.length > 0 && iArr[0] == 0) {
                        refreshDevices();
                        return;
                    } else {
                        Snackbar.make(findViewById(R.id.root), getResources().getString(R.string.scan_failed_with_missing_permissions), -2).setAction(getResources().getString(R.string.base_setting), new View.OnClickListener() { // from class: com.home.activity.main.MainActivity_BLE.38
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", MainActivity_BLE.this.getPackageName(), null));
                                MainActivity_BLE.this.startActivity(intent);
                            }
                        }).setDuration(MessageHandler.WHAT_SMOOTH_SCROLL).show();
                        return;
                    }
                case REQUEST_BLUETOOTH_SCAN /* 224 */:
                    if (iArr.length <= 0 || iArr[0] != 0) {
                        return;
                    }
                    turnOnBluetooth();
                    return;
                case REQUEST_BLUETOOTH_CONNECT /* 225 */:
                    if (iArr.length <= 0 || iArr[0] != 0) {
                        return;
                    }
                    turnOnBluetooth();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.home.base.LedBleActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Uri data;
        MainActivity_BLE mainActivity_BLE;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 111) {
            if (intent.getStringExtra("group") != null) {
                String stringExtra = intent.getStringExtra("group");
                try {
                    save2GroupByGroupName(stringExtra, LedBleApplication.getApp().getTempDevices());
                    ArrayList<GroupDevice> devicesByGroup = new GroupDeviceDao(mActivity).getDevicesByGroup(stringExtra);
                    Iterator<GroupView> it = this.arrayListGroupViews.iterator();
                    while (it.hasNext()) {
                        GroupView next = it.next();
                        if (stringExtra.equals(next.getGroupName())) {
                            next.setGroupDevices(devicesByGroup);
                            int findConnectCount = findConnectCount(devicesByGroup);
                            LogUtil.i(LedBleApplication.tag, "count:" + findConnectCount);
                            next.setConnectCount(findConnectCount);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Tool.ToastShow(mActivity, "保存失败！");
                }
            }
        } else if (i2 == -1 && i == this.INT_GO_CHANGEMODE) {
            String stringExtra2 = intent.getStringExtra(CommonConstant.SELECT_MODE_SMART_STRING);
            this.rgbFragment.setActive(stringExtra2);
            if (this.currentIndex != 0) {
                this.segmentRgb.setVisibility(View.GONE);
            }
            this.brightFragment.setActive(stringExtra2);
            this.lineFragment.onResume();
        } else if (i == this.OPEN_BLE) {
            if (i2 == -1) {
                refreshDevices();
            }
        } else if (i2 == -1 && i == 1) {
            ContentResolver contentResolver = getContentResolver();
            if (intent == null || (data = intent.getData()) == null) {
                return;
            }
            try {
                Bitmap bitmap = this.bm;
                if (bitmap != null && !bitmap.isRecycled()) {
                    this.bm.recycle();
                    this.bm = null;
                    System.gc();
                }
                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(contentResolver, data);
                this.bm = bitmap2;
                if (bitmap2 == null || (mainActivity_BLE = mActivity) == null) {
                    return;
                }
                String realPathFromUri = getRealPathFromUri(mainActivity_BLE, data);
                showImage(realPathFromUri);
                saveImagePathToSharedPreferences(realPathFromUri);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    @Override // com.home.base.LedBleActivity, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        float[] fArr = sensorEvent.values;
        if (type == 1) {
            if (Math.abs(fArr[0]) > 19.0f || Math.abs(fArr[1]) > 19.0f || Math.abs(fArr[2]) > 19.0f) {
                int i = this.shakeStyle;
                if (i == 0) {
                    this.soundPool.play(this.soundID, 1.0f, 1.0f, 0, 0, 1.0f);
                    setRgb(this.random.nextInt(255) + 1, this.random.nextInt(255) + 1, this.random.nextInt(255) + 1, false, false, false, false, false);
                } else if (i != 2) {
                } else {
                    this.soundPool.play(this.soundID, 1.0f, 1.0f, 0, 0, 1.0f);
                    if (sceneBean.equalsIgnoreCase(CommonConstant.LEDSMART)) {
                        setRegMode(this.random.nextInt(11) + 1, false);
                    } else {
                        setRegMode(this.random.nextInt(22) + 135, false);
                    }
                }
            }
        }
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public void open(boolean z, boolean z2, boolean z3) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().open();
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().open();
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).turnOn(sceneBean, z, z2, z3);
    }

    public void close(boolean z, boolean z2, boolean z3) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().close();
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().close();
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).turnOff(sceneBean, z, z2, z3);
    }

    public void bledmxopen() {
        NetConnectBle.getInstanceByGroup(this.groupName).bledmxturnOn(sceneBean);
    }

    public void bledmxclose() {
        NetConnectBle.getInstanceByGroup(this.groupName).bledmxturnOff(sceneBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allOn() {
        NetConnectBle.getInstanceByGroup("").turnOn(sceneBean, false, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allOff() {
        NetConnectBle.getInstanceByGroup("").turnOff(sceneBean, false, false, false);
    }

    public void settingTime(int i, int i2, int i3, int i4) {
        NetConnectBle.getInstanceByGroup(this.groupName).sendsetting(i, i2, i3, i4);
    }

    public void sendTime(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().sendTimeLightandStage(i, i6, i7, i4, i5, i2);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().sendTimeLightandStage(i, i6, i7, i4, i5, i2);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).sendTime(i, i2, i3, i4, i5, i6, i7, sceneBean);
    }

    public void sendStageTime(int i, int i2, int i3, int i4, int i5, int i6) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().sendTimeLightandStage(i, i2, i3, i4, i5, i6);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().sendTimeLightandStage(i, i2, i3, i4, i5, i6);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).sendtimestage(i, i2, i3, i4, i5, i6);
    }

    public void endTime(int i, int i2, int i3, int i4) {
        NetConnectBle.getInstanceByGroup(this.groupName).endTime(i, i2, i3, i4, sceneBean);
    }

    public void closeTime() {
        NetConnectBle.getInstanceByGroup(this.groupName).closeTime(sceneBean);
    }

    public void setRgb(int i, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setRgb(i, i2, i3, sceneBean, z4);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setRgb(i, i2, i3, sceneBean, z4);
                    }
                }
            } catch (Exception e) {
                onException(e);
            }
        } else if (z) {
            if (this.handler == null) {
                this.handler = new Handler();
            }
            if (this.canSend) {
                NetConnectBle.getInstanceByGroup(this.groupName).setRgb(i, i2, i3, sceneBean, z2, z3, z4, z5);
                this.canSend = false;
                this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.39
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity_BLE.this.canSend = true;
                    }
                };
                if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    this.handler.postDelayed(this.runnable, 200L);
                } else {
                    this.handler.postDelayed(this.runnable, 200L);
                }
            }
        } else {
            NetConnectBle.getInstanceByGroup(this.groupName).setRgb(i, i2, i3, sceneBean, z2, z3, z4, z5);
        }
    }

    public void setDmx00Dmx01Rgb(int i, int i2, int i3, boolean z, boolean z2, boolean z3, int i4) {
        if (z) {
            if (this.handler == null) {
                this.handler = new Handler();
            }
            if (this.canSend) {
                NetConnectBle.getInstanceByGroup(this.groupName).setDmx00Dmx01Rgb(i, i2, i3, sceneBean, z2, z3, i4);
                this.canSend = false;
                this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.40
                    @Override // java.lang.Runnable
                    public void run() {
                        MainActivity_BLE.this.canSend = true;
                    }
                };
                if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                    this.handler.postDelayed(this.runnable, 200L);
                    return;
                } else {
                    this.handler.postDelayed(this.runnable, 200L);
                    return;
                }
            }
            return;
        }
        NetConnectBle.getInstanceByGroup(this.groupName).setDmx00Dmx01Rgb(i, i2, i3, sceneBean, z2, z3, i4);
    }

    public void setRegMode(int i, boolean z) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setRgbMode(i);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setRgbMode(i);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.canSend) {
            NetConnectBle.getInstanceByGroup(this.groupName).setRgbMode(i, sceneBean, z);
            this.canSend = false;
            this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.41
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.canSend = true;
                }
            };
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.handler.postDelayed(this.runnable, 200L);
            } else {
                this.handler.postDelayed(this.runnable, 200L);
            }
        }
    }

    public void setRegModeNoInterval(int i, boolean z) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setRgbMode(i);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setRgbMode(i);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).setRgbMode(i, sceneBean, z);
    }

    public void setDim(int i) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setDim(i);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setDim(i);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.canSend) {
            NetConnectBle.getInstanceByGroup(this.groupName).setDim(i, sceneBean);
            this.canSend = false;
            this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.42
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.canSend = true;
                }
            };
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.handler.postDelayed(this.runnable, 200L);
            } else {
                this.handler.postDelayed(this.runnable, 200L);
            }
        }
    }

    public void setSPIModel(int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).setSPIModel(i, sceneBean);
    }

    public void setSpeed(int i, boolean z, boolean z2) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setSpeed(i);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setSpeed(i);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.canSend) {
            NetConnectBle.getInstanceByGroup(this.groupName).setSpeed(i, sceneBean, z, z2);
            this.canSend = false;
            this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.43
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.canSend = true;
                }
            };
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.handler.postDelayed(this.runnable, 200L);
            } else {
                this.handler.postDelayed(this.runnable, 200L);
            }
        }
    }

    public void setSpeedNoInterval(int i) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setSpeed(i);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setSpeed(i);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).setSpeed(i, sceneBean, false, false);
    }

    public void setBrightNess(int i, boolean z, boolean z2, boolean z3) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setBrightness(i);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setBrightness(i);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.canSend) {
            NetConnectBle.getInstanceByGroup(this.groupName).setBrightness(i, sceneBean, z, z2, z3);
            this.canSend = false;
            this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.44
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.canSend = true;
                }
            };
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.handler.postDelayed(this.runnable, 200L);
            } else {
                this.handler.postDelayed(this.runnable, 200L);
            }
        }
    }

    public void setBrightNessNoInterval(int i, boolean z, boolean z2) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setBrightness(i);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setBrightness(i);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).setBrightness(i, sceneBean, false, z, z2);
    }

    public void setDirection(int i, boolean z) {
        NetConnectBle.getInstanceByGroup(this.groupName).setDirection(i, sceneBean, z);
    }

    public void setTime(int i, int i2, int i3, int i4, int i5, int i6) {
        NetConnectBle.getInstanceByGroup(this.groupName).setrgbTime(i, i2, i3, i4, i5, i6);
    }

    public void setCirculation() {
        NetConnectBle.getInstanceByGroup(this.groupName).setCirculation();
    }

    public void setDiy(ArrayList<MyColor> arrayList, int i) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setDiy(arrayList, i);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setDiy(arrayList, i);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).setDiy(arrayList, i, sceneBean);
    }

    public void setMusicBrightness(int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).setMusicBrightness(i, sceneBean);
    }

    public void setCT(int i, int i2) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setColorWarm(i, i2);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setColorWarm(i, i2);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.canSend) {
            NetConnectBle.getInstanceByGroup(this.groupName).setColorWarm(i, i2, sceneBean);
            this.canSend = false;
            this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.45
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.canSend = true;
                }
            };
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.handler.postDelayed(this.runnable, 100L);
            } else {
                this.handler.postDelayed(this.runnable, 100L);
            }
        }
    }

    public void setConfigSPI(int i, byte b, byte b2, int i2) {
        NetConnectBle.getInstance().setConfigSPI(i, b, b2, i2, sceneBean);
    }

    public void setConfigCAR01(int i, int i2, int i3, int i4) {
        NetConnectBle.getInstance().setConfigCAR01(i, i2, i3, i4, sceneBean);
    }

    public void setDynamicModel(int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).setDynamicModel(i);
    }

    public void setSPIPause(int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).pauseSPI(i, sceneBean);
    }

    public void setSmartBrightnessNoInterval(int i, int i2) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setSmartBrightness(i, i2);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setSmartBrightness(i, i2);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).setSmartBrightness(i, i2, sceneBean);
    }

    public void setSmartBrightness(int i, int i2) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setSmartBrightness(i, i2);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setSmartBrightness(i, i2);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        Log.e(TAG, "brightness: " + i + ",   mode: " + i2);
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.canSend) {
            NetConnectBle.getInstanceByGroup(this.groupName).setSmartBrightness(i, i2, sceneBean);
            this.canSend = false;
            this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.46
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.canSend = true;
                }
            };
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.handler.postDelayed(this.runnable, 200L);
            } else {
                this.handler.postDelayed(this.runnable, 300L);
            }
        }
    }

    public void setRgbSort(int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).SetRgbSort(i);
    }

    public void setPairCode(int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).SetPairCode(i);
    }

    public void setTimeStart(int i, int i2, int i3, int i4, int i5, int i6) {
        NetConnectBle.getInstanceByGroup(this.groupName).setTime(i, i2, i3, i4, i5, i6);
    }

    public void setTimerSecData(int[] iArr) {
        NetConnectBle.getInstanceByGroup(this.groupName).setTimerSecData(iArr);
    }

    public void setSmartBubbleCheck(int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).setSmartBubbleCheck(i);
    }

    public void sendSun(int i, int i2, int i3, int i4, int i5) {
        NetConnectBle.getInstanceByGroup(this.groupName).sendSun(i, i2, i3, i4, i5);
    }

    public void timeSun(int i, int i2, int i3, int i4) {
        NetConnectBle.getInstanceByGroup(this.groupName).timeSun(i, i2, i3, i4);
    }

    public void setAuxiliary(int i, View view) {
        NetConnectBle.getInstanceByGroup(this.groupName).setAuxiliary(i, sceneBean);
        showSure(view);
    }

    public void setModeCycle(int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal && LedBleApplication.getApp().getWifiConenction1().isOnLine()) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().setModeCycle(i, i2, i3, i4, i5, i6);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().setModeCycle(i, i2, i3, i4, i5, i6);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).setModeCycle(i, i2, i3, i4, i5, i6, sceneBean, z);
    }

    public void setCustomCycle(boolean z) {
        NetConnectBle.getInstanceByGroup(this.groupName).setCustomCycle(sceneBean, z);
    }

    public void setChangeColor(boolean z, boolean z2, boolean z3, ArrayList<MyColor> arrayList) {
        NetConnectBle.getInstanceByGroup(this.groupName).setChangeColor(z, z2, z3, arrayList, sceneBean);
    }

    public void setMode(boolean z, boolean z2, boolean z3, int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).setMode(z, z2, z3, i, sceneBean);
    }

    public void setVoiceCtlAndMusicMode(boolean z, boolean z2, boolean z3, int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).setVoiceCtlAndMusicMode(z, z2, z3, i, sceneBean);
    }

    public void setSensitivity(int i, boolean z, boolean z2) {
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.canSend) {
            NetConnectBle.getInstance().setSensitivity(i, z, z2, sceneBean);
            this.canSend = false;
            this.runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.47
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.canSend = true;
                }
            };
            if (sceneBean.equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.handler.postDelayed(this.runnable, 200L);
            } else {
                this.handler.postDelayed(this.runnable, 300L);
            }
        }
    }

    public void codeCheck(int i, int i2) {
        try {
            if (this.islegal) {
                LedBleApplication.getApp().getWifiConenction1().code_check(i, i2);
            }
        } catch (Exception e) {
            onException(e);
        }
    }

    public ArrayList<String> setTBCheck(int i) {
        try {
            if (this.islegal && LedBleApplication.getApp().getWifiConenction1().isOnLine()) {
                return LedBleApplication.getApp().getWifiConenction1().setTBCheck(i);
            }
            return null;
        } catch (Exception e) {
            onException(e);
            return null;
        }
    }

    public void setlight(int i) {
        try {
            if (this.islegal && LedBleApplication.getApp().getWifiConenction1().isOnLine()) {
                LedBleApplication.getApp().getWifiConenction1().setSmartCheck(i);
            }
        } catch (Exception e) {
            onException(e);
        }
    }

    public ArrayList<String> TBCheckRecv() {
        try {
            if (this.islegal && LedBleApplication.getApp().getWifiConenction1().isOnLine()) {
                return LedBleApplication.getApp().getWifiConenction1().TBCheckRecv();
            }
            return null;
        } catch (Exception e) {
            onException(e);
            return null;
        }
    }

    public void setSmartTimeNowSet() {
        Date date = new Date(System.currentTimeMillis());
        List asList = Arrays.asList(getResources().getStringArray(R.array.week));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("ss");
        String format = new SimpleDateFormat(ExifInterface.LONGITUDE_EAST).format(date);
        int i = 6;
        if (format.contains((CharSequence) asList.get(0))) {
            i = 1;
        } else if (format.contains((CharSequence) asList.get(1))) {
            i = 2;
        } else if (format.contains((CharSequence) asList.get(2))) {
            i = 3;
        } else if (format.contains((CharSequence) asList.get(3))) {
            i = 4;
        } else if (format.contains((CharSequence) asList.get(4))) {
            i = 5;
        } else if (!format.contains((CharSequence) asList.get(5))) {
            i = format.contains((CharSequence) asList.get(6)) ? 7 : 0;
        }
        int parseInt = Integer.parseInt(simpleDateFormat.format(date).trim());
        int parseInt2 = Integer.parseInt(simpleDateFormat2.format(date).trim());
        int parseInt3 = Integer.parseInt(simpleDateFormat3.format(date).trim());
        for (int i2 = 0; i2 < 2; i2++) {
            NetConnectBle.getInstanceByGroup(this.groupName).setSmartTimeSet(parseInt, parseInt2, parseInt3, i);
        }
    }

    public void SetCHN(int i, int i2, int i3, int i4, int i5, int i6) {
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            try {
                if (this.islegal && LedBleApplication.getApp().getWifiConenction1().isOnLine()) {
                    if (LedBleApplication.getApp().getWifiConenction1() != null) {
                        LedBleApplication.getApp().getWifiConenction1().SetCHN(i, i2, i3, i4, i5, i6);
                    }
                    if (LedBleApplication.getApp().getWifiConenction2() != null) {
                        LedBleApplication.getApp().getWifiConenction2().SetCHN(i, i2, i3, i4, i5, i6);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                onException(e);
                return;
            }
        }
        NetConnectBle.getInstanceByGroup(this.groupName).SetCHN(i, i2, i3, i4, i5, i6);
    }

    public void setSunVcMode(int i) {
        NetConnectBle.getInstanceByGroup(this.groupName).setSunVcMode(i);
    }

    public void setSunSensitivity(int i) {
        if (this.handler == null) {
            this.handler = new Handler();
        }
        if (this.canSend) {
            NetConnectBle.getInstanceByGroup(this.groupName).setSunSensitivity(i);
            this.canSend = false;
            Runnable runnable = new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.48
                @Override // java.lang.Runnable
                public void run() {
                    MainActivity_BLE.this.canSend = true;
                }
            };
            this.runnable = runnable;
            this.handler.postDelayed(runnable, 200L);
        }
    }

    public void setPassword() {
        Arrays.asList(getResources().getStringArray(R.array.week));
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("ss");
        int parseInt = Integer.parseInt(simpleDateFormat.format(date).trim());
        int parseInt2 = Integer.parseInt(simpleDateFormat2.format(date).trim());
        Integer.parseInt(simpleDateFormat3.format(date).trim());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int i = 7;
        String valueOf = String.valueOf(calendar.get(7));
        if (!"1".equals(valueOf)) {
            if (ExifInterface.GPS_MEASUREMENT_2D.equals(valueOf)) {
                i = 1;
            } else if (ExifInterface.GPS_MEASUREMENT_3D.equals(valueOf)) {
                i = 2;
            } else if ("4".equals(valueOf)) {
                i = 3;
            } else if ("5".equals(valueOf)) {
                i = 4;
            } else if ("6".equals(valueOf)) {
                i = 5;
            } else {
                i = "7".equals(valueOf) ? 6 : 0;
            }
        }
        int i2 = (i << 5) | parseInt;
        String perference = SharePersistent.getPerference(this, Constant.PasswordSet);
        if (perference != null && perference.length() == 8) {
            NetConnectBle.getInstanceByGroup(this.groupName).setPassword(Integer.parseInt(perference.substring(0, 2), 16), Integer.parseInt(perference.substring(2, 4), 16), Integer.parseInt(perference.substring(4, 6), 16), Integer.parseInt(perference.substring(6, 8), 16), i2, parseInt2);
        } else {
            NetConnectBle.getInstanceByGroup(this.groupName).setPassword(0, 0, 0, 0, i2, parseInt2);
        }
    }

    private static SharedPreferences getSp(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("SpUtil", 0);
        }
        return sharedPreferences;
    }

    public static void putString(Context context, String str, String str2) {
        SharedPreferences.Editor edit = getSp(context).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String getString(Context context, String str, String str2) {
        return getSp(context).getString(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> Car01RgbSortModel() {
        ArrayList arrayList = new ArrayList();
        String[] stringArray = getResources().getStringArray(R.array.rgb_sort_ble);
        ArrayList arrayList2 = new ArrayList();
        for (String str : stringArray) {
            arrayList2.add(str);
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            arrayList.add(str2.substring(0, str2.lastIndexOf(",")));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> getRgbSortIndex() {
        String[] stringArray;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String str : getResources().getStringArray(R.array.rgb_sort_ble)) {
            arrayList.add(str.substring(str.lastIndexOf(",") + 1).trim());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    @Override // com.home.base.LedBleActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        GPSPresenter gPSPresenter;
        super.onResume();
        SensorManager sensorManager = this.sensorManager;
        if (sensorManager != null) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(1), 3);
        }
        if (Build.VERSION.SDK_INT >= 23 && (gPSPresenter = this.gps_presenter) != null && !gPSPresenter.gpsIsOpen(this)) {
            this.layout.setVisibility(View.VISIBLE);
        }
        if (sceneBean.equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
            checkConnect();
            registerWifiChangeBoradCast();
        }
    }

    @Override // com.home.base.LedBleActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        unregisterWifiChangeReceiver();
    }

    @Override // com.home.base.LedBleActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        GPSPresenter gPSPresenter = this.gps_presenter;
        if (gPSPresenter != null) {
            gPSPresenter.onDestroy();
            this.gps_presenter = null;
        }
        if (sceneBean.contains(CommonConstant.LEDBLE) || sceneBean.equalsIgnoreCase("LEDDMX-00-") || sceneBean.equalsIgnoreCase("LEDDMX-01-") || sceneBean.equalsIgnoreCase("LEDCAR-00-") || sceneBean.equalsIgnoreCase("LEDCAR-01-")) {
            pauseMusicAndVolum(true);
        }
        if (this.iv_all22.getVisibility() == View.VISIBLE) {
            putString(mActivity, sceneBean, "two");
        } else {
            putString(mActivity, sceneBean, "one");
        }
        mActivity = null;
    }

    public SegmentedRadioGroup getSegmentRgb() {
        return this.segmentRgb;
    }

    public SegmentedRadioGroup getSegmentCAR01RgbTop() {
        return this.segmentCAR01RgbTop;
    }

    public SegmentedRadioGroup getSegmentCAR01ModeTop() {
        return this.segmentCAR01ModeTop;
    }

    public SegmentedRadioGroup getSegmentCAR01CustomTop() {
        return this.segmentCAR01CustomTop;
    }

    public RelativeLayout getRlModeTopCAR00() {
        return this.rlModeTopCAR00;
    }

    public SegmentedRadioGroup getSegmentModeSun() {
        return this.segmentModeSun;
    }

    public SegmentedRadioGroup getSegmentCustomDMX00Top() {
        return this.segmentCustomDMX00Top;
    }

    public SegmentedRadioGroup getSegmentCustomDMX01Top() {
        return this.segmentCustomDMX01Top;
    }

    public SegmentedRadioGroup getSegmentCustomCAR00() {
        return this.segmentCustomCAR00;
    }

    public SegmentedRadioGroup getSegmentMusic() {
        return this.segmentMusic;
    }

    public Button getBtnChangeColor() {
        return this.btnChangeColor;
    }

    public Button getBtnModeCycle() {
        return this.btnModeCycle;
    }

    public RelativeLayout getRlModeTopDMX00() {
        return this.rlModeTopDMX00;
    }

    public RelativeLayout getRlModeTopDMX01() {
        return this.rlModeTopDMX01;
    }

    public Button getIvEditColor() {
        return this.ivEditColor;
    }

    public RelativeLayout getRlCustomtopInfo() {
        return this.rlCustomtopInfo;
    }

    public SelectMultiCheckGroup getCheckGroupWeek() {
        return this.checkGroupWeek;
    }

    public SegmentedRadioGroup getSegmentSmartTimer() {
        return this.segmentSmartTimer;
    }

    private void checkConnect() {
        conenctDevice(new ConnectDeviceListener() { // from class: com.home.activity.main.MainActivity_BLE.49
            @Override // com.home.net.wifi.callback.ConnectDeviceListener
            public void onConnect(boolean z) {
                if (z) {
                    return;
                }
                MainActivity_BLE.this.showWifiListDialogAnimation();
            }
        });
    }

    private void registerWifiChangeBoradCast() {
        this.receiver = new BroadcastReceiver() { // from class: com.home.activity.main.MainActivity_BLE.50
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
            }
        };
        registerReceiver(this.receiver, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
    }

    private void unregisterWifiChangeReceiver() {
        BroadcastReceiver broadcastReceiver = this.receiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    public void showWifiListDialogAnimation() {
        if (this.wifiManager.isWifiEnabled()) {
            return;
        }
        this.wifiManager.setWifiEnabled(true);
    }

    @Deprecated
    public void showWifiListDialogAnimation(List<ScanResult> ignore) {
        if (this.wifiManager.isWifiEnabled()) {
            return;
        }
        this.wifiManager.setWifiEnabled(true);
    }

    public String[] getString(List<ScanResult> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).SSID;
            if (!StringUtils.isEmpty(str)) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public void conenctDevice(final ConnectDeviceListener connectDeviceListener) {
        BaseTask baseTask = this.baseTask;
        if (baseTask != null && !baseTask.isCancelled()) {
            this.baseTask.cancel(true);
        }
        BaseTask baseTask2 = new BaseTask(new NetCallBack() { // from class: com.home.activity.main.MainActivity_BLE.51
            @Override // com.common.task.NetCallBack
            public void onPreCall() {
            }

            @Override // com.common.task.NetCallBack
            public void onFinish(NetResult netResult) {
                if (netResult != null) {
                    Tool.ToastShow(MainActivity_BLE.mActivity, (int) R.string.connect_success);
                    new Thread(new Runnable() { // from class: com.home.activity.main.MainActivity_BLE.51.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                MainActivity_BLE.this.verifyModuleValidity();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(MainActivity_BLE.TAG, "run: " + e);
                            }
                        }
                    }).start();
                } else if (MainActivity_BLE.mActivity != null) {
                    Tool.ToastShow(MainActivity_BLE.mActivity, (int) R.string.can_not_connect);
                }
                ConnectDeviceListener connectDeviceListener2 = connectDeviceListener;
                if (connectDeviceListener2 != null) {
                    connectDeviceListener2.onConnect(netResult != null);
                }
            }

            @Override // com.common.task.NetCallBack
            public NetResult onDoInBack(HashMap<String, String> hashMap) {
                Log.e(MainActivity_BLE.TAG, "getConnectWifiSsid : " + MainActivity_BLE.this.getConnectWifiSsid());
                if (!MainActivity_BLE.this.getConnectWifiSsid().startsWith(BluetoothLeServiceSingle.LED_)) {
                    if (MainActivity_BLE.this.getConnectWifiSsid().startsWith(BluetoothLeServiceSingle.LED)) {
                        if (LedBleApplication.getApp().getWifiConenction2() == null) {
                            LedBleApplication.getApp().setWifiConenction2(new WifiConenction2(MainActivity_BLE.mActivity, false, null));
                        }
                        if (LedBleApplication.getApp().getWifiConenction2().connect()) {
                            return new NetResult();
                        }
                    }
                } else {
                    if (LedBleApplication.getApp().getWifiConenction1() == null) {
                        LedBleApplication.getApp().setWifiConenction1(new WifiConenction(MainActivity_BLE.mActivity, false, null));
                    }
                    if (LedBleApplication.getApp().getWifiConenction1().connect()) {
                        return new NetResult();
                    }
                }
                return null;
            }
        });
        this.baseTask = baseTask2;
        baseTask2.execute(new HashMap());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getConnectWifiSsid() {
        if (mActivity == null || getApplicationContext() == null) {
            return "";
        }
        WifiInfo connectionInfo = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
        Log.d("wifiInfo", connectionInfo.toString());
        Log.d("SSID", connectionInfo.getSSID());
        return connectionInfo.getSSID().replace("\"", "");
    }

    public void verifyModuleValidity() throws Exception {
        MainActivity_BLE mainActivity_BLE;
        String sendVerification = sendVerification();
        String calculateEncryptedValue = calculateEncryptedValue();
        Log.e(TAG, "verify==" + calculateEncryptedValue);
        Log.e(TAG, "verifyModuleValidity: ==" + sendVerification);
        if (sendVerification.equalsIgnoreCase(calculateEncryptedValue)) {
            this.islegal = true;
            if (this.isFirstOpen && (mainActivity_BLE = mActivity) != null) {
                Tool.ToastShow(mainActivity_BLE, (int) R.string.connect_success);
                this.isFirstOpen = false;
            }
            Log.e(TAG, "verifyModuleValidity: ");
            return;
        }
        this.islegal = false;
    }

    public String calculateEncryptedValue() throws Exception {
        byte[] bytes = getResources().getString(R.string.keyStr).getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(bytes));
        return BytetohexString(cipher.doFinal(this.randStr.getBytes()));
    }

    private static String BytetohexString(byte[] bArr) {
        int length = bArr.length;
        StringBuilder sb = new StringBuilder(bArr.length * 3);
        Formatter formatter = new Formatter(sb);
        for (int i = 0; i < length; i++) {
            if (i < length - 1) {
                formatter.format("%02X", Byte.valueOf(bArr[i]));
            } else {
                formatter.format("%02X", Byte.valueOf(bArr[i]));
            }
        }
        formatter.close();
        return sb.toString();
    }

    public String sendVerification() throws IOException {
        String valueOf = String.valueOf(new Random().nextInt(1000));
        this.randStr = valueOf;
        String convertStringToHex = convertStringToHex(valueOf);
        String receiveDataReturned = receiveDataReturned(convertStringToHex);
        String receiveDataReturned2 = receiveDataReturned2(convertStringToHex);
        if (receiveDataReturned == "") {
            receiveDataReturned = receiveDataReturned2;
        }
        return receiveDataReturned.split("[=]")[2].split("[\r]")[0];
    }

    public String convertStringToHex(String str) {
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (char c : charArray) {
            stringBuffer.append(Integer.toHexString(c));
        }
        return stringBuffer.toString();
    }

    public String receiveDataReturned(String str) throws IOException {
        InetAddress byName = InetAddress.getByName("192.168.2.3");
        byte[] bytes = ("xcmd_req::cmd=encrypt,key=1,data=" + str + "\r\n").getBytes();
        Socket socket = new Socket(byName, 5000);
        socket.getOutputStream().write(bytes);
        byte[] bArr = new byte[1024];
        socket.setSoTimeout(1000);
        int read = socket.getInputStream().read(bArr);
        int position = ByteBuffer.wrap(bArr, 0, read).position();
        String str2 = new String(bArr, position, read - position);
        socket.close();
        return str2.length() > 0 ? str2 : "";
    }

    public String receiveDataReturned2(String str) throws IOException {
        InetAddress byName = InetAddress.getByName(WifiConenction2.LOCALHOST);
        byte[] bytes = ("xcmd_req::cmd=encrypt,key=1,data=" + str + "\r\n").getBytes();
        Socket socket = new Socket(byName, 5000);
        socket.getOutputStream().write(bytes);
        byte[] bArr = new byte[1024];
        socket.setSoTimeout(1000);
        int read = socket.getInputStream().read(bArr);
        int position = ByteBuffer.wrap(bArr, 0, read).position();
        String str2 = new String(bArr, position, read - position);
        socket.close();
        return str2.length() > 0 ? str2 : "";
    }
}
