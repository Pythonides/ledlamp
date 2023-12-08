package com.home.fragment.ble;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import butterknife.BindView;
import com.common.adapter.OnSeekBarChangeListenerAdapter;
import com.common.uitl.SharePersistent;
import com.common.uitl.Tool;
import com.common.view.SegmentedRadioGroup;
import com.home.activity.main.MainActivity_BLE;
import com.home.base.LedBleFragment;
import com.home.constant.CommonConstant;
import com.home.constant.Constant;
import com.home.view.BlackWiteSelectView;
import com.home.view.ColorTextView;
import com.home.view.MyColorPicker;
import com.itheima.wheelpicker.WheelPicker;
import com.ledlamp.BuildConfig;
import com.ledlamp.R;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;

/* loaded from: classes.dex */
public class ModeFragment extends LedBleFragment {
    private static final int COLOR_DEFALUT = 0;
    private static final String TAG = "ModeFragment";
    private ColorTextView actionView;
    private ImageView backImage;
    private BlackWiteSelectView blackWiteSelectView2;
    private int bright;
    private Button buttonCycle;
    private Button buttonPlay;
    private Button buttonSelectColorConfirm;
    private ArrayList<ColorTextView> colorTextViews;
    private int currentSelecColorFromPicker;
    private int diyValue1;
    private int diyValue2;
    private int diyValue3;
    private int diyValue4;
    private int diyValue5;
    private int diyValue6;
    private String diyViewTag;
    @BindView(R.id.imageViewOnOff)
    Button imageViewOnOff;
    private ColorPickerView imageViewPicker2;
    private ImageView iv_switch_select;
    private LinearLayout linearChouse_select;
    private LinearLayout llCoverMode;
    private LinearLayout llRing;
    @BindView(R.id.llmode)
    LinearLayout llmode;
    @BindView(R.id.llmodeCar01DMX)
    LinearLayout llmodeCar01DMX;
    private MainActivity_BLE mActivity;
    private View mContentView;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRlModeTopDMX00;
    private RelativeLayout mRlModeTopDMX01;
    private View menuView;
    private MyColorPicker myColor_select;
    private RadioButton rbCAR01ModeBLE;
    private RadioButton rbCAR01ModeDMX;
    private RelativeLayout rlModeTopCAR00;
    @BindView(R.id.rlModeTopDIYCAR01BLE)
    RelativeLayout rlModeTopDIYCAR01BLE;
    @BindView(R.id.rlModeTopDIYCAR01DMX)
    RelativeLayout rlModeTopDIYCAR01DMX;
    private RelativeLayout rlModeTopWiFi;
    private SeekBar seekBarBrightBarSC;
    @BindView(R.id.seekBarBrightNess)
    SeekBar seekBarBrightness;
    @BindView(R.id.seekBarMode)
    SeekBar seekBarMode;
    @BindView(R.id.seekBarModeCar01DMX)
    SeekBar seekBarModeCar01DMX;
    private SeekBar seekBarModeSC;
    @BindView(R.id.seekBarSpeed)
    SeekBar seekBarSpeedBar;
    private SeekBar seekBarSpeedBarSC;
    private SegmentedRadioGroup segmentCAR01Top;
    private SharedPreferences sharedPreferences;
    private int speed;
    private SegmentedRadioGroup srgCover;
    private TextView textGreen_select;
    private TextView textRed_select;
    private TextView textViewBrightSC;
    @BindView(R.id.textViewBrightNess)
    TextView textViewBrightness;
    @BindView(R.id.textViewMode)
    TextView textViewMode;
    @BindView(R.id.textViewModeCar01DMX)
    TextView textViewModeCar01DMX;
    private TextView textViewModeSC;
    @BindView(R.id.textViewSpeed)
    TextView textViewSpeed;
    private TextView textViewSpeedSC;
    private TextView tvBlue_select;
    @BindView(R.id.wheelPicker)
    WheelPicker wheelPicker;
    @BindView(R.id.wheelPickerCar01DMX)
    WheelPicker wheelPickerCar01DMX;
    private WheelPicker wheelPicker_tang;
    private Boolean isCAR01DMX = false;
    private int playBtnState = 0;
    private List<String> listName = new ArrayList();
    private List<String> listNubmer = new ArrayList();
    private int select_r = 255;
    private int select_g = 255;
    private int select_b = 255;

    @Override // com.home.base.LedBleFragment
    public void initEvent() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContentView = layoutInflater.inflate(R.layout.fragment_mode, viewGroup, false);
        this.menuView = layoutInflater.inflate(R.layout.activity_select_color, viewGroup, false);
        return this.mContentView;
    }

    @Override // com.home.base.LedBleFragment
    public void initData() {
        if (MainActivity_BLE.getMainActivity() != null) {
            this.mActivity = MainActivity_BLE.getMainActivity();
            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-")) {
                this.mRlModeTopDMX01 = MainActivity_BLE.getMainActivity().getRlModeTopDMX01();
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-")) {
                this.mRlModeTopDMX00 = MainActivity_BLE.getMainActivity().getRlModeTopDMX00();
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-")) {
                this.rlModeTopCAR00 = MainActivity_BLE.getMainActivity().getRlModeTopCAR00();
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-")) {
                this.wheelPicker.setData(carModel());
                this.llmode.setVisibility(8);
                SegmentedRadioGroup segmentCAR01ModeTop = MainActivity_BLE.getMainActivity().getSegmentCAR01ModeTop();
                this.segmentCAR01Top = segmentCAR01ModeTop;
                this.rbCAR01ModeBLE = (RadioButton) segmentCAR01ModeTop.findViewById(R.id.rbCAR01ModeBLE);
                this.rbCAR01ModeDMX = (RadioButton) this.segmentCAR01Top.findViewById(R.id.rbCAR01ModeDMX);
                this.rlModeTopDIYCAR01BLE.setVisibility(0);
                Button button = (Button) this.rlModeTopDIYCAR01BLE.findViewById(R.id.btnCycleCAR01BLE);
                this.buttonCycle = button;
                button.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ModeFragment.this.startAnimation(view);
                        for (int i = 1; i <= 5; i++) {
                            SharedPreferences sharedPreferences = ModeFragment.this.sharedPreferences;
                            int i2 = sharedPreferences.getInt("modediyColor" + i + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY), 0);
                            if (i == 1) {
                                ModeFragment.this.diyValue1 = i2 > 0 ? i2 : 0;
                            } else if (i == 2) {
                                ModeFragment.this.diyValue2 = i2 > 0 ? i2 : 0;
                            } else if (i == 3) {
                                ModeFragment.this.diyValue3 = i2 > 0 ? i2 : 0;
                            } else if (i == 4) {
                                ModeFragment.this.diyValue4 = i2 > 0 ? i2 : 0;
                            } else if (i == 5) {
                                ModeFragment.this.diyValue5 = i2 > 0 ? i2 : 0;
                            }
                        }
                        ModeFragment.this.mActivity.setModeCycle(ModeFragment.this.diyValue1, ModeFragment.this.diyValue2, ModeFragment.this.diyValue3, ModeFragment.this.diyValue4, ModeFragment.this.diyValue5, ModeFragment.this.diyValue6, false);
                    }
                });
                this.rbCAR01ModeBLE.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ModeFragment.this.isCAR01DMX = false;
                        ModeFragment.this.wheelPickerCar01DMX.setStopScroll(true);
                        ModeFragment.this.rlModeTopDIYCAR01BLE.setVisibility(0);
                        ModeFragment.this.rlModeTopDIYCAR01DMX.setVisibility(8);
                        ModeFragment.this.llmode.setVisibility(8);
                        if (ModeFragment.this.isCAR01DMX.booleanValue()) {
                            return;
                        }
                        ModeFragment.this.wheelPickerCar01DMX.setVisibility(8);
                        ModeFragment.this.llmodeCar01DMX.setVisibility(8);
                        ModeFragment.this.wheelPicker.setVisibility(0);
                        ModeFragment.this.wheelPicker.setData(ModeFragment.this.carModel());
                    }
                });
                this.rbCAR01ModeDMX.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        int i;
                        ModeFragment.this.isCAR01DMX = true;
                        ModeFragment.this.wheelPicker.setStopScroll(true);
                        ModeFragment.this.rlModeTopDIYCAR01BLE.setVisibility(8);
                        ModeFragment.this.rlModeTopDIYCAR01DMX.setVisibility(0);
                        ModeFragment.this.llmode.setVisibility(0);
                        ModeFragment.this.initColorCar01DMXBlock();
                        if (ModeFragment.this.isCAR01DMX.booleanValue()) {
                            ModeFragment.this.wheelPickerCar01DMX.setVisibility(0);
                            ModeFragment.this.llmodeCar01DMX.setVisibility(0);
                            ModeFragment.this.wheelPicker.setVisibility(8);
                            ModeFragment.this.llmode.setVisibility(8);
                            ModeFragment.this.wheelPickerCar01DMX.setData(ModeFragment.this.dmxModel());
                            if (MainActivity_BLE.sceneBean != null) {
                                if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && ModeFragment.this.isCAR01DMX.booleanValue()) {
                                    MainActivity_BLE mainActivity_BLE = ModeFragment.this.mActivity;
                                    i = SharePersistent.getInt(mainActivity_BLE, MainActivity_BLE.sceneBean + ModeFragment.TAG + "car01dmx-mode-mode");
                                } else {
                                    i = 0;
                                }
                                ModeFragment.this.seekBarModeCar01DMX.setProgress(i);
                                ModeFragment.this.wheelPickerCar01DMX.setSelectedItemPosition(i);
                                List data = ModeFragment.this.wheelPickerCar01DMX.getData();
                                if (i < data.size()) {
                                    TextView textView = ModeFragment.this.textViewModeCar01DMX;
                                    textView.setText("" + data.get(i));
                                }
                                ModeFragment.this.wheelPicker_tang.setData(ModeFragment.this.dmxNoAutoListName());
                                ModeFragment.this.seekBarModeSC.setVisibility(0);
                                ModeFragment.this.textViewModeSC.setVisibility(0);
                                ModeFragment.this.seekBarModeSC.setMax(209);
                                MainActivity_BLE mainActivity_BLE2 = ModeFragment.this.mActivity;
                                int i2 = SharePersistent.getInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + ModeFragment.TAG + "modediymode");
                                if (ModeFragment.this.dmxNoAutoListName().size() > i2) {
                                    TextView textView2 = ModeFragment.this.textViewModeSC;
                                    textView2.setText("" + ((String) ModeFragment.this.dmxNoAutoListName().get(i2)));
                                }
                            }
                            ((Button) ModeFragment.this.rlModeTopDIYCAR01DMX.findViewById(R.id.btnCycleCAR01DMX)).setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.3.1
                                @Override // android.view.View.OnClickListener
                                public void onClick(View view2) {
                                    ModeFragment.this.startAnimation(view2);
                                    for (int i3 = 1; i3 <= 6; i3++) {
                                        SharedPreferences sharedPreferences = ModeFragment.this.sharedPreferences;
                                        int i4 = sharedPreferences.getInt("modediyColorCar01DMX" + i3 + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY), 0);
                                        if (i3 == 1) {
                                            ModeFragment.this.diyValue1 = i4 > 0 ? i4 : 0;
                                        } else if (i3 == 2) {
                                            ModeFragment.this.diyValue2 = i4 > 0 ? i4 : 0;
                                        } else if (i3 == 3) {
                                            ModeFragment.this.diyValue3 = i4 > 0 ? i4 : 0;
                                        } else if (i3 == 4) {
                                            ModeFragment.this.diyValue4 = i4 > 0 ? i4 : 0;
                                        } else if (i3 == 5) {
                                            ModeFragment.this.diyValue5 = i4 > 0 ? i4 : 0;
                                        } else if (i3 == 6) {
                                            ModeFragment.this.diyValue6 = i4 > 0 ? i4 : 0;
                                        }
                                    }
                                    ModeFragment.this.mActivity.setModeCycle(ModeFragment.this.diyValue1, ModeFragment.this.diyValue2, ModeFragment.this.diyValue3, ModeFragment.this.diyValue4, ModeFragment.this.diyValue5, ModeFragment.this.diyValue6, true);
                                }
                            });
                        }
                    }
                });
            }
        }
        this.sharedPreferences = getActivity().getSharedPreferences(SharePersistent.getPerference(this.mActivity, Constant.CUSTOM_DIY_APPKEY), 0);
    }

    @Override // com.home.base.LedBleFragment
    public void initView() {
        String str;
        if (this.mActivity != null && MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && this.isCAR01DMX.booleanValue()) {
            this.rlModeTopDIYCAR01BLE.setVisibility(0);
            this.rlModeTopDIYCAR01DMX.setVisibility(8);
        }
        if (this.mActivity != null) {
            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_01)) {
                this.wheelPicker.setData(bleModel());
                this.llmode.setVisibility(8);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-") || (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && this.isCAR01DMX.booleanValue())) {
                this.wheelPicker.setData(dmxModel());
                this.seekBarMode.setMax(210);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && this.isCAR01DMX.booleanValue()) {
                this.wheelPickerCar01DMX.setData(dmxModel());
                this.seekBarModeCar01DMX.setMax(210);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-") || (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && !this.isCAR01DMX.booleanValue())) {
                this.wheelPicker.setData(carModel());
                this.llmode.setVisibility(8);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                this.wheelPicker.setData(stageModel());
                this.seekBarMode.setMax(210);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.wheelPicker.setData(dmxModel());
                this.seekBarMode.setMax(210);
            }
        }
        this.wheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() { // from class: com.home.fragment.ble.ModeFragment.4
            @Override // com.itheima.wheelpicker.WheelPicker.OnItemSelectedListener
            public void onItemSelected(WheelPicker wheelPicker, Object obj, int i) {
                if (i >= 0) {
                    Log.e(ModeFragment.TAG, "onItemSelected: " + i);
                    if (ModeFragment.this.seekBarMode != null) {
                        ModeFragment.this.seekBarMode.setProgress(i);
                        List data = wheelPicker.getData();
                        Log.e(ModeFragment.TAG, "data: " + data.get(i));
                        TextView textView = ModeFragment.this.textViewMode;
                        textView.setText("" + data.get(i));
                    }
                    if (ModeFragment.this.mActivity != null) {
                        if (MainActivity_BLE.getSceneBean().contains(CommonConstant.LEDBLE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-")) {
                            ModeFragment.this.mActivity.setRegModeNoInterval(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i)).trim()), ModeFragment.this.isCAR01DMX.booleanValue());
                        } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-")) {
                            ModeFragment.this.mActivity.setSPIModel(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i)).trim()));
                        } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                            ModeFragment.this.mActivity.setRegMode(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i)).trim()), false);
                        }
                    }
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE = ModeFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE, MainActivity_BLE.sceneBean + ModeFragment.TAG + "mode-mode", i);
                    }
                }
            }
        });
        this.wheelPickerCar01DMX.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() { // from class: com.home.fragment.ble.ModeFragment.5
            @Override // com.itheima.wheelpicker.WheelPicker.OnItemSelectedListener
            public void onItemSelected(WheelPicker wheelPicker, Object obj, int i) {
                if (i >= 0) {
                    Log.e(ModeFragment.TAG, "onItemSelected: " + i);
                    if (ModeFragment.this.seekBarModeCar01DMX != null) {
                        ModeFragment.this.seekBarModeCar01DMX.setProgress(i);
                        List data = wheelPicker.getData();
                        Log.e(ModeFragment.TAG, "data: " + data.get(i));
                        TextView textView = ModeFragment.this.textViewModeCar01DMX;
                        textView.setText("" + ((String) ModeFragment.this.listName.get(i)));
                    }
                    if (ModeFragment.this.mActivity != null) {
                        ModeFragment.this.mActivity.setRegModeNoInterval(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i)).trim()), ModeFragment.this.isCAR01DMX.booleanValue());
                    }
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE = ModeFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE, MainActivity_BLE.sceneBean + ModeFragment.TAG + "car01dmx-mode-mode", i);
                    }
                }
            }
        });
        if (this.mActivity != null && (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi))) {
            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-")) {
                this.buttonPlay = (Button) this.mRlModeTopDMX00.findViewById(R.id.imageViewPlayModeDMX00);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.buttonPlay = (Button) this.mRlModeTopDMX01.findViewById(R.id.imageViewPlayMode);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-")) {
                this.buttonPlay = (Button) this.rlModeTopDIYCAR01DMX.findViewById(R.id.ivPlayModeCAR01DMX);
            }
            this.buttonPlay.setVisibility(0);
            this.buttonPlay.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ModeFragment.this.startAnimation(view);
                    if (ModeFragment.this.playBtnState == 0) {
                        ModeFragment.this.buttonPlay.setBackgroundResource(R.drawable.mode_play);
                        ModeFragment.this.playBtnState = 1;
                        if (ModeFragment.this.mActivity != null) {
                            ModeFragment.this.mActivity.setSPIPause(0);
                            return;
                        }
                        return;
                    }
                    ModeFragment.this.buttonPlay.setBackgroundResource(R.drawable.mode_stop);
                    ModeFragment.this.playBtnState = 0;
                    if (ModeFragment.this.mActivity != null) {
                        ModeFragment.this.mActivity.setSPIPause(1);
                    }
                }
            });
        }
        if (this.mActivity != null) {
            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-")) {
                Button button = (Button) this.mRlModeTopDMX00.findViewById(R.id.btnCycleDMX00);
                this.buttonCycle = button;
                button.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.7
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ModeFragment.this.startAnimation(view);
                        for (int i = 1; i <= 6; i++) {
                            SharedPreferences sharedPreferences = ModeFragment.this.sharedPreferences;
                            int i2 = sharedPreferences.getInt("modediyColor" + i + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY), 0);
                            if (i == 1) {
                                ModeFragment.this.diyValue1 = i2 > 0 ? i2 : 0;
                            } else if (i == 2) {
                                ModeFragment.this.diyValue2 = i2 > 0 ? i2 : 0;
                            } else if (i == 3) {
                                ModeFragment.this.diyValue3 = i2 > 0 ? i2 : 0;
                            } else if (i == 4) {
                                ModeFragment.this.diyValue4 = i2 > 0 ? i2 : 0;
                            } else if (i == 5) {
                                ModeFragment.this.diyValue5 = i2 > 0 ? i2 : 0;
                            } else if (i == 6) {
                                ModeFragment.this.diyValue6 = i2 > 0 ? i2 : 0;
                            }
                        }
                        ModeFragment.this.mActivity.setModeCycle(ModeFragment.this.diyValue1, ModeFragment.this.diyValue2, ModeFragment.this.diyValue3, ModeFragment.this.diyValue4, ModeFragment.this.diyValue5, ModeFragment.this.diyValue6, false);
                    }
                });
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-")) {
                Button button2 = (Button) this.rlModeTopCAR00.findViewById(R.id.btnCycleCAR00);
                this.buttonCycle = button2;
                button2.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.8
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ModeFragment.this.startAnimation(view);
                        for (int i = 1; i <= 5; i++) {
                            SharedPreferences sharedPreferences = ModeFragment.this.sharedPreferences;
                            int i2 = sharedPreferences.getInt("modediyColor" + i + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY), 0);
                            if (i == 1) {
                                ModeFragment.this.diyValue1 = i2 > 0 ? i2 : 0;
                            } else if (i == 2) {
                                ModeFragment.this.diyValue2 = i2 > 0 ? i2 : 0;
                            } else if (i == 3) {
                                ModeFragment.this.diyValue3 = i2 > 0 ? i2 : 0;
                            } else if (i == 4) {
                                ModeFragment.this.diyValue4 = i2 > 0 ? i2 : 0;
                            } else if (i == 5) {
                                ModeFragment.this.diyValue5 = i2 > 0 ? i2 : 0;
                            }
                        }
                        ModeFragment.this.mActivity.setModeCycle(ModeFragment.this.diyValue1, ModeFragment.this.diyValue2, ModeFragment.this.diyValue3, ModeFragment.this.diyValue4, ModeFragment.this.diyValue5, ModeFragment.this.diyValue6, false);
                    }
                });
            }
        }
        this.seekBarMode.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.ModeFragment.9
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    ModeFragment.this.wheelPicker.setStopScroll(true);
                    ModeFragment.this.wheelPickerCar01DMX.setStopScroll(true);
                    List data = ModeFragment.this.wheelPicker.getData();
                    if (i < data.size()) {
                        TextView textView = ModeFragment.this.textViewMode;
                        textView.setText("" + data.get(i));
                        if (ModeFragment.this.mActivity != null) {
                            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE)) {
                                if (i >= 0 && i <= 28) {
                                    ModeFragment.this.wheelPicker.setSelectedItemPosition(i);
                                    ModeFragment.this.mActivity.setRegMode(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i)).trim().trim()), ModeFragment.this.isCAR01DMX.booleanValue());
                                }
                            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-")) {
                                if (i >= 0 && i <= 210) {
                                    ModeFragment.this.wheelPicker.setSelectedItemPosition(i);
                                    ModeFragment.this.mActivity.setSPIModel(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i)).trim()));
                                }
                            } else if ((MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT)) && i >= 0 && i <= 210) {
                                ModeFragment.this.wheelPicker.setSelectedItemPosition(i);
                                ModeFragment.this.mActivity.setRegMode(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i)).trim()), ModeFragment.this.isCAR01DMX.booleanValue());
                            }
                        }
                        if (MainActivity_BLE.sceneBean != null) {
                            MainActivity_BLE mainActivity_BLE = ModeFragment.this.mActivity;
                            SharePersistent.saveInt(mainActivity_BLE, MainActivity_BLE.sceneBean + ModeFragment.TAG + "mode-mode", i);
                        }
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE = this.mActivity;
            int i = SharePersistent.getInt(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + "mode-mode");
            this.seekBarMode.setProgress(i);
            this.wheelPicker.setSelectedItemPosition(i);
            List data = this.wheelPicker.getData();
            if (i < data.size()) {
                TextView textView = this.textViewMode;
                textView.setText("" + data.get(i));
            }
        }
        this.seekBarModeCar01DMX.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.ModeFragment.10
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                if (z) {
                    ModeFragment.this.wheelPicker.setStopScroll(true);
                    ModeFragment.this.wheelPickerCar01DMX.setStopScroll(true);
                    List data2 = ModeFragment.this.wheelPickerCar01DMX.getData();
                    if (i2 < data2.size()) {
                        TextView textView2 = ModeFragment.this.textViewModeCar01DMX;
                        textView2.setText("" + data2.get(i2));
                        if (ModeFragment.this.mActivity != null && i2 >= 0 && i2 <= 210) {
                            ModeFragment.this.wheelPickerCar01DMX.setSelectedItemPosition(i2);
                            ModeFragment.this.mActivity.setRegMode(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i2)).trim()), ModeFragment.this.isCAR01DMX.booleanValue());
                        }
                        if (MainActivity_BLE.sceneBean != null) {
                            MainActivity_BLE mainActivity_BLE2 = ModeFragment.this.mActivity;
                            SharePersistent.saveInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + ModeFragment.TAG + "car01dmx-mode-mode", i2);
                        }
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE2 = this.mActivity;
            int i2 = SharePersistent.getInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + TAG + "car01dmx-mode-mode");
            this.seekBarModeCar01DMX.setProgress(i2);
            this.wheelPickerCar01DMX.setSelectedItemPosition(i2);
            List data2 = this.wheelPickerCar01DMX.getData();
            if (i2 < data2.size()) {
                TextView textView2 = this.textViewModeCar01DMX;
                textView2.setText("" + data2.get(i2));
            }
        }
        this.seekBarSpeedBar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.ModeFragment.11
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                if (ModeFragment.this.mActivity == null || ModeFragment.this.textViewSpeed.getTag() == null) {
                    return;
                }
                if (ModeFragment.this.textViewSpeed.getTag().equals(100)) {
                    ModeFragment.this.mActivity.setSpeed(100, false, ModeFragment.this.isCAR01DMX.booleanValue());
                } else if (ModeFragment.this.textViewSpeed.getTag().equals(1)) {
                    ModeFragment.this.mActivity.setSpeed(1, false, ModeFragment.this.isCAR01DMX.booleanValue());
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                if (z) {
                    if (i3 == 0) {
                        ModeFragment.this.textViewSpeed.setText(ModeFragment.this.getActivity().getResources().getString(R.string.speed_set, String.valueOf(1)));
                        if (ModeFragment.this.mActivity != null) {
                            ModeFragment.this.mActivity.setSpeed(1, false, ModeFragment.this.isCAR01DMX.booleanValue());
                        }
                        ModeFragment.this.textViewSpeed.setTag(1);
                        return;
                    }
                    if (MainActivity_BLE.getMainActivity() != null) {
                        MainActivity_BLE.getMainActivity().setSpeed(i3, false, ModeFragment.this.isCAR01DMX.booleanValue());
                    }
                    ModeFragment.this.textViewSpeed.setText(ModeFragment.this.getActivity().getResources().getString(R.string.speed_set, Integer.valueOf(i3)));
                    ModeFragment.this.textViewSpeed.setTag(Integer.valueOf(i3));
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE3 = ModeFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + ModeFragment.TAG + "speed", i3);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE3 = this.mActivity;
            int i3 = SharePersistent.getInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + TAG + "speed");
            if (i3 > 0) {
                this.seekBarSpeedBar.setProgress(i3);
                str = "";
                this.textViewSpeed.setText(this.mActivity.getResources().getString(R.string.speed_set, String.valueOf(i3)));
            } else {
                str = "";
                this.textViewSpeed.setText(this.mActivity.getResources().getString(R.string.speed_set, String.valueOf(80)));
                this.seekBarSpeedBar.setProgress(80);
            }
        } else {
            str = "";
        }
        this.seekBarBrightness.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.ModeFragment.12
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                if (ModeFragment.this.textViewBrightness == null || ModeFragment.this.textViewBrightness.getTag() == null) {
                    return;
                }
                if (ModeFragment.this.textViewBrightness.getTag().equals(100)) {
                    if (ModeFragment.this.mActivity != null) {
                        ModeFragment.this.mActivity.setBrightNess(100, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                    }
                } else if (!ModeFragment.this.textViewBrightness.getTag().equals(1) || ModeFragment.this.mActivity == null) {
                } else {
                    ModeFragment.this.mActivity.setBrightNess(1, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i4, boolean z) {
                if (z) {
                    if (i4 == 0) {
                        ModeFragment.this.textViewBrightness.setText(ModeFragment.this.getActivity().getResources().getString(R.string.brightness_set, String.valueOf(1)));
                        ModeFragment.this.textViewBrightness.setTag(1);
                        if (ModeFragment.this.mActivity != null) {
                            ModeFragment.this.mActivity.setBrightNess(1, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                            return;
                        }
                        return;
                    }
                    ModeFragment.this.textViewBrightness.setText(ModeFragment.this.getActivity().getResources().getString(R.string.brightness_set, String.valueOf(i4)));
                    ModeFragment.this.textViewBrightness.setTag(Integer.valueOf(i4));
                    if (ModeFragment.this.mActivity != null) {
                        ModeFragment.this.mActivity.setBrightNess(i4, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                    }
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE4 = ModeFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + ModeFragment.TAG + "bright", i4);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE4 = this.mActivity;
            int i4 = SharePersistent.getInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + TAG + "bright");
            if (i4 > 0) {
                this.seekBarBrightness.setProgress(i4);
                this.textViewBrightness.setText(this.mActivity.getResources().getString(R.string.brightness_set, String.valueOf(i4)));
            } else {
                this.textViewBrightness.setText(this.mActivity.getResources().getString(R.string.brightness_set, String.valueOf(100)));
                this.seekBarBrightness.setProgress(100);
            }
        }
        this.myColor_select = (MyColorPicker) this.menuView.findViewById(R.id.myColor_select);
        this.linearChouse_select = (LinearLayout) this.menuView.findViewById(R.id.linearChouse_select);
        this.textRed_select = (TextView) this.menuView.findViewById(R.id.textRed_select);
        this.textGreen_select = (TextView) this.menuView.findViewById(R.id.textGreen_select);
        this.tvBlue_select = (TextView) this.menuView.findViewById(R.id.tvBlue_select);
        this.iv_switch_select = (ImageView) this.menuView.findViewById(R.id.iv_switch_select);
        SegmentedRadioGroup segmentedRadioGroup = (SegmentedRadioGroup) this.menuView.findViewById(R.id.srgCover);
        this.srgCover = segmentedRadioGroup;
        segmentedRadioGroup.check(R.id.rbRing);
        this.srgCover.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.fragment.ble.ModeFragment.13
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i5) {
                if (R.id.rbRing == i5) {
                    ModeFragment.this.llRing.setVisibility(0);
                    ModeFragment.this.llCoverMode.setVisibility(8);
                } else if (R.id.rbModle == i5) {
                    ModeFragment.this.llCoverMode.setVisibility(0);
                    ModeFragment.this.llRing.setVisibility(8);
                }
            }
        });
        this.wheelPicker_tang = (WheelPicker) this.menuView.findViewById(R.id.wheelPicker_tang);
        this.seekBarModeSC = (SeekBar) this.menuView.findViewById(R.id.seekBarModeSC);
        this.textViewModeSC = (TextView) this.menuView.findViewById(R.id.textViewModeSC);
        if (this.mActivity != null) {
            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-") || (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && this.isCAR01DMX.booleanValue())) {
                this.wheelPicker_tang.setData(dmxNoAutoListName());
                this.seekBarModeSC.setMax(210);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-") || (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && !this.isCAR01DMX.booleanValue())) {
                this.wheelPicker_tang.setData(carModel());
                this.llmode.setVisibility(8);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                this.wheelPicker_tang.setData(stageModel());
                this.seekBarModeSC.setMax(210);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.wheelPicker_tang.setData(dmxModel());
                this.seekBarModeSC.setMax(210);
            }
        }
        this.wheelPicker_tang.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() { // from class: com.home.fragment.ble.ModeFragment.14
            @Override // com.itheima.wheelpicker.WheelPicker.OnItemSelectedListener
            public void onItemSelected(WheelPicker wheelPicker, Object obj, int i5) {
                if (i5 >= 0) {
                    MainActivity_BLE unused = ModeFragment.this.mActivity;
                    Log.e(ModeFragment.TAG, "wheelPicker_tang: " + i5);
                    if (ModeFragment.this.mActivity != null) {
                        if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-") || (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && !ModeFragment.this.isCAR01DMX.booleanValue())) {
                            ModeFragment modeFragment = ModeFragment.this;
                            modeFragment.currentSelecColorFromPicker = Integer.parseInt(((String) modeFragment.car00ListNubmer().get(i5)).trim());
                            ModeFragment.this.mActivity.setRegMode(Integer.parseInt(((String) ModeFragment.this.car00ListNubmer().get(i5)).trim()), ModeFragment.this.isCAR01DMX.booleanValue());
                        } else if (MainActivity_BLE.getSceneBean().contains(CommonConstant.LEDDMX) || (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && ModeFragment.this.isCAR01DMX.booleanValue())) {
                            ModeFragment modeFragment2 = ModeFragment.this;
                            modeFragment2.currentSelecColorFromPicker = Integer.parseInt(((String) modeFragment2.dmxNoAutoListNubmer().get(i5)).trim());
                            ModeFragment.this.mActivity.setRegMode(Integer.parseInt(((String) ModeFragment.this.dmxNoAutoListNubmer().get(i5)).trim()), ModeFragment.this.isCAR01DMX.booleanValue());
                        } else if (MainActivity_BLE.getSceneBean().contains(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                            ModeFragment modeFragment3 = ModeFragment.this;
                            modeFragment3.currentSelecColorFromPicker = Integer.parseInt(((String) modeFragment3.listNubmer.get(i5)).trim());
                            ModeFragment.this.mActivity.setRegMode(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i5)).trim()), false);
                        }
                    }
                    if (ModeFragment.this.seekBarModeSC != null) {
                        ModeFragment.this.seekBarModeSC.setProgress(i5);
                        List data3 = ModeFragment.this.wheelPicker_tang.getData();
                        TextView textView3 = ModeFragment.this.textViewModeSC;
                        textView3.setText("" + data3.get(i5));
                    }
                    SeekBar seekBar = ModeFragment.this.seekBarModeSC;
                    seekBar.setTag("" + ModeFragment.this.currentSelecColorFromPicker);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE5 = ModeFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + ModeFragment.TAG + "modediymode", i5);
                    }
                }
            }
        });
        this.seekBarModeSC.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.ModeFragment.15
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i5, boolean z) {
                if (z) {
                    ModeFragment.this.wheelPicker_tang.setStopScroll(true);
                    List data3 = ModeFragment.this.wheelPicker_tang.getData();
                    if (ModeFragment.this.mActivity != null && (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.getSceneBean().contains(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT) || ((MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && ModeFragment.this.isCAR01DMX.booleanValue()) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi)))) {
                        if (MainActivity_BLE.getSceneBean().contains(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                            if (i5 >= 0 && i5 < ModeFragment.this.listNubmer.size()) {
                                ModeFragment.this.wheelPicker_tang.setSelectedItemPosition(i5);
                                ModeFragment modeFragment = ModeFragment.this;
                                modeFragment.currentSelecColorFromPicker = Integer.parseInt(((String) modeFragment.listNubmer.get(i5)).trim());
                                TextView textView3 = ModeFragment.this.textViewModeSC;
                                textView3.setText("" + data3.get(i5));
                                ModeFragment.this.mActivity.setSPIModel(Integer.parseInt(((String) ModeFragment.this.listNubmer.get(i5)).trim()));
                            }
                        } else if (i5 >= 0 && i5 <= 210) {
                            ModeFragment.this.wheelPicker_tang.setSelectedItemPosition(i5);
                            ModeFragment modeFragment2 = ModeFragment.this;
                            modeFragment2.currentSelecColorFromPicker = Integer.parseInt(((String) modeFragment2.dmxNoAutoListNubmer().get(i5)).trim());
                            ModeFragment.this.mActivity.setSPIModel(Integer.parseInt(((String) ModeFragment.this.dmxNoAutoListNubmer().get(i5)).trim()));
                            TextView textView4 = ModeFragment.this.textViewModeSC;
                            textView4.setText("" + data3.get(i5));
                        }
                    }
                    SeekBar seekBar2 = ModeFragment.this.seekBarModeSC;
                    seekBar2.setTag("" + ModeFragment.this.currentSelecColorFromPicker);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE5 = ModeFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + ModeFragment.TAG + "modediymode", i5);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE5 = this.mActivity;
            int i5 = SharePersistent.getInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + TAG + "modediymode");
            this.seekBarModeSC.setProgress(i5);
            this.wheelPicker_tang.setSelectedItemPosition(i5);
            List data3 = this.wheelPicker_tang.getData();
            if (data3.size() > i5) {
                TextView textView3 = this.textViewModeSC;
                textView3.setText(str + data3.get(i5));
            }
        }
        this.seekBarBrightBarSC = (SeekBar) this.menuView.findViewById(R.id.seekBarBrightNess);
        this.textViewBrightSC = (TextView) this.menuView.findViewById(R.id.textViewBrightNess);
        this.seekBarBrightBarSC.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.ModeFragment.16
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i6, boolean z) {
                if (z) {
                    if (i6 == 0) {
                        if (ModeFragment.this.mActivity != null) {
                            ModeFragment.this.mActivity.setBrightNess(1, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                        }
                        ModeFragment.this.textViewBrightSC.setText(ModeFragment.this.getActivity().getResources().getString(R.string.brightness_set, 1));
                        return;
                    }
                    if (ModeFragment.this.mActivity != null) {
                        ModeFragment.this.mActivity.setBrightNess(i6, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                    }
                    ModeFragment.this.textViewBrightSC.setText(ModeFragment.this.getActivity().getResources().getString(R.string.brightness_set, Integer.valueOf(i6)));
                    if (MainActivity_BLE.sceneBean != null) {
                        Log.e(ModeFragment.TAG, "progress: " + i6);
                        MainActivity_BLE mainActivity_BLE6 = ModeFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + ModeFragment.TAG + "modediybright", i6);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE6 = this.mActivity;
            int i6 = SharePersistent.getInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + TAG + "modediybright");
            if (i6 > 0) {
                this.seekBarBrightBarSC.setProgress(i6);
                this.textViewBrightSC.setText(this.mActivity.getResources().getString(R.string.brightness_set, String.valueOf(i6)));
            } else {
                this.seekBarBrightBarSC.setProgress(100);
                this.textViewBrightSC.setText(this.mActivity.getResources().getString(R.string.brightness_set, String.valueOf(100)));
            }
        }
        this.seekBarSpeedBarSC = (SeekBar) this.menuView.findViewById(R.id.seekBarSpeedSC);
        this.textViewSpeedSC = (TextView) this.menuView.findViewById(R.id.textViewSpeedSC);
        this.seekBarSpeedBarSC.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.ModeFragment.17
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i7, boolean z) {
                if (z) {
                    if (i7 == 0) {
                        if (ModeFragment.this.mActivity != null) {
                            ModeFragment.this.mActivity.setSpeed(1, false, ModeFragment.this.isCAR01DMX.booleanValue());
                        }
                        ModeFragment.this.textViewSpeedSC.setText(ModeFragment.this.getActivity().getResources().getString(R.string.speed_set, 1));
                        return;
                    }
                    if (ModeFragment.this.mActivity != null) {
                        ModeFragment.this.mActivity.setSpeed(i7, false, ModeFragment.this.isCAR01DMX.booleanValue());
                    }
                    ModeFragment.this.textViewSpeedSC.setText(ModeFragment.this.getActivity().getResources().getString(R.string.speed_set, Integer.valueOf(i7)));
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE7 = ModeFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE7, MainActivity_BLE.sceneBean + ModeFragment.TAG + "modediyspeed", i7);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE7 = this.mActivity;
            int i7 = SharePersistent.getInt(mainActivity_BLE7, MainActivity_BLE.sceneBean + TAG + "modediyspeed");
            if (i7 > 0) {
                this.seekBarSpeedBarSC.setProgress(i7);
                this.textViewSpeedSC.setText(this.mActivity.getResources().getString(R.string.speed_set, String.valueOf(i7)));
            } else {
                this.seekBarSpeedBarSC.setProgress(80);
                this.textViewSpeedSC.setText(this.mActivity.getResources().getString(R.string.speed_set, String.valueOf(80)));
            }
        }
        if (this.mActivity != null && (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi))) {
            this.buttonSelectColorConfirm = (Button) this.menuView.findViewById(R.id.buttonSelectColorConfirm);
            this.srgCover = (SegmentedRadioGroup) this.menuView.findViewById(R.id.srgCover);
            this.llRing = (LinearLayout) this.menuView.findViewById(R.id.llRing);
            this.llCoverMode = (LinearLayout) this.menuView.findViewById(R.id.llCoverMode);
            this.imageViewPicker2 = (ColorPickerView) this.menuView.findViewById(R.id.imageViewPicker2);
            this.blackWiteSelectView2 = (BlackWiteSelectView) this.menuView.findViewById(R.id.blackWiteSelectView2);
            initColorBlock();
            initColorSelecterView();
        }
        ImageView imageView = (ImageView) this.menuView.findViewById(R.id.backImage);
        this.backImage = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ModeFragment.this.hideColorCover();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimation(View view) {
        if (getActivity() != null) {
            view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.layout_scale));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initColorBlock() {
        /*
            Method dump skipped, instructions count: 400
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.home.fragment.ble.ModeFragment.initColorBlock():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initColorCar01DMXBlock() {
        RelativeLayout relativeLayout;
        int i;
        if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && this.isCAR01DMX.booleanValue()) {
            relativeLayout = this.rlModeTopDIYCAR01DMX;
            i = 6;
        } else {
            relativeLayout = null;
            i = 0;
        }
        this.colorTextViews = new ArrayList<>();
        for (int i2 = 1; i2 <= i; i2++) {
            final ColorTextView colorTextView = (ColorTextView) relativeLayout.findViewWithTag("modediyColorCar01DMX" + i2);
            SharedPreferences sharedPreferences = this.sharedPreferences;
            int i3 = sharedPreferences.getInt(((String) colorTextView.getTag()) + SharePersistent.getPerference(this.mActivity, Constant.CUSTOM_DIY_APPKEY), 0);
            if (i3 != 0) {
                if (i3 == 255) {
                    colorTextView.setText("Auto");
                } else {
                    colorTextView.setText("" + i3);
                }
            }
            colorTextView.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.21
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    view.startAnimation(AnimationUtils.loadAnimation(ModeFragment.this.getActivity(), R.anim.layout_scale));
                    ModeFragment.this.diyViewTag = (String) colorTextView.getTag();
                    int i4 = ModeFragment.this.sharedPreferences.getInt(ModeFragment.this.diyViewTag + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY), 0);
                    Log.e(ModeFragment.TAG, "tag  === " + ModeFragment.this.diyViewTag);
                    if (i4 != 0) {
                        if (ModeFragment.this.mActivity != null) {
                            ModeFragment.this.mActivity.setRegModeNoInterval(i4, ModeFragment.this.isCAR01DMX.booleanValue());
                            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-")) {
                                ModeFragment modeFragment = ModeFragment.this;
                                modeFragment.bright = SharePersistent.getBrightData(modeFragment.getActivity(), ModeFragment.this.diyViewTag + "modediybrightLEDDMX-01-", ModeFragment.this.diyViewTag + "modediybrightLEDDMX-01-");
                                ModeFragment modeFragment2 = ModeFragment.this;
                                modeFragment2.speed = SharePersistent.getBrightData(modeFragment2.getActivity(), ModeFragment.this.diyViewTag + "modediyspeedLEDDMX-01-", ModeFragment.this.diyViewTag + "modediyspeedLEDDMX-01-");
                            }
                        }
                        if (ModeFragment.this.bright == 0) {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() { // from class: com.home.fragment.ble.ModeFragment.21.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (ModeFragment.this.mActivity != null) {
                                        ModeFragment.this.mActivity.setBrightNess(100, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                                    }
                                    handler.removeCallbacksAndMessages(null);
                                }
                            }, 300L);
                        } else {
                            final Handler handler2 = new Handler();
                            handler2.postDelayed(new Runnable() { // from class: com.home.fragment.ble.ModeFragment.21.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (ModeFragment.this.mActivity != null) {
                                        ModeFragment.this.mActivity.setBrightNess(ModeFragment.this.bright, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                                    }
                                    handler2.removeCallbacksAndMessages(null);
                                }
                            }, 300L);
                        }
                        if (ModeFragment.this.speed == 0) {
                            final Handler handler3 = new Handler();
                            handler3.postDelayed(new Runnable() { // from class: com.home.fragment.ble.ModeFragment.21.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (ModeFragment.this.mActivity != null) {
                                        ModeFragment.this.mActivity.setSpeed(85, false, ModeFragment.this.isCAR01DMX.booleanValue());
                                    }
                                    handler3.removeCallbacksAndMessages(null);
                                }
                            }, 300L);
                            return;
                        }
                        final Handler handler4 = new Handler();
                        handler4.postDelayed(new Runnable() { // from class: com.home.fragment.ble.ModeFragment.21.4
                            @Override // java.lang.Runnable
                            public void run() {
                                if (ModeFragment.this.mActivity != null) {
                                    ModeFragment.this.mActivity.setSpeed(ModeFragment.this.speed, false, ModeFragment.this.isCAR01DMX.booleanValue());
                                }
                                handler4.removeCallbacksAndMessages(null);
                            }
                        }, 300L);
                        return;
                    }
                    ModeFragment.this.showColorCover((ColorTextView) view, true);
                }
            });
            colorTextView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.home.fragment.ble.ModeFragment.22
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    ColorTextView colorTextView2 = (ColorTextView) view;
                    colorTextView2.setColor(0);
                    String str = ((String) colorTextView.getTag()) + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY);
                    Log.e(ModeFragment.TAG, "tag  === " + str);
                    ModeFragment.this.sharedPreferences.edit().putInt(str, 0).commit();
                    colorTextView2.setBackgroundDrawable(ModeFragment.this.getActivity().getResources().getDrawable(R.drawable.block_shap_color));
                    colorTextView2.setText("+");
                    return true;
                }
            });
            this.colorTextViews.add(colorTextView);
        }
    }

    public void showColorCover(ColorTextView colorTextView, boolean z) {
        this.actionView = colorTextView;
        this.currentSelecColorFromPicker = 0;
        this.srgCover.check(R.id.rbRing);
        if (z) {
            this.srgCover.setVisibility(8);
            this.llRing.setVisibility(8);
            this.llCoverMode.setVisibility(0);
            this.blackWiteSelectView2.setVisibility(8);
        } else {
            this.srgCover.setVisibility(4);
            this.llRing.setVisibility(0);
            this.llCoverMode.setVisibility(8);
            this.blackWiteSelectView2.setVisibility(8);
        }
        if (this.mActivity != null) {
            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_01)) {
                this.wheelPicker_tang.setData(bleModel());
                this.seekBarModeSC.setMax(28);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-")) {
                this.wheelPicker_tang.setData(dmxNoAutoListName());
                this.seekBarModeSC.setMax(209);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_00) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-")) {
                this.wheelPicker_tang.setData(carModel());
                this.seekBarModeSC.setVisibility(8);
                this.textViewModeSC.setVisibility(8);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && !this.isCAR01DMX.booleanValue()) {
                this.wheelPicker_tang.setData(carModel());
                this.seekBarModeSC.setVisibility(8);
                this.textViewModeSC.setVisibility(8);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && this.isCAR01DMX.booleanValue()) {
                this.wheelPicker_tang.setData(dmxNoAutoListName());
                this.seekBarModeSC.setMax(209);
                if (MainActivity_BLE.sceneBean != null) {
                    MainActivity_BLE mainActivity_BLE = this.mActivity;
                    int i = SharePersistent.getInt(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + "modediymode");
                    this.seekBarModeSC.setProgress(i);
                    this.wheelPicker_tang.setSelectedItemPosition(i);
                    if (dmxNoAutoListName().size() > i) {
                        TextView textView = this.textViewModeSC;
                        textView.setText("" + dmxNoAutoListName().get(i));
                    }
                }
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                this.wheelPicker_tang.setData(stageModel());
                this.seekBarModeSC.setMax(210);
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                this.wheelPicker_tang.setData(dmxNoAutoListName());
                this.seekBarModeSC.setMax(209);
            }
        }
        if (this.mPopupWindow == null) {
            this.mPopupWindow = new PopupWindow(this.menuView, -1, -1, true);
        }
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            this.mPopupWindow.showAtLocation(this.mContentView, 80, 0, 0);
        }
    }

    private void initColorSelecterView() {
        this.imageViewPicker2.subscribe(new ColorObserver() { // from class: com.home.fragment.ble.ModeFragment$$ExternalSyntheticLambda0
            @Override // top.defaults.colorpicker.ColorObserver
            public final void onColor(int i, boolean z, boolean z2) {
                ModeFragment.this.m18x9ea1ae94(i, z, z2);
            }
        });
        this.myColor_select.setOnColorChangedListener(new MyColorPicker.OnColorChangedListener() { // from class: com.home.fragment.ble.ModeFragment.23
            @Override // com.home.view.MyColorPicker.OnColorChangedListener
            public void onColorChanged(int i) {
                int[] rgb = Tool.getRGB(i);
                ModeFragment.this.blackWiteSelectView2.setStartColor(i);
                ModeFragment.this.currentSelecColorFromPicker = i;
                ModeFragment.this.updateRgbText(rgb, false);
                ModeFragment.this.select_r = rgb[0];
                ModeFragment.this.select_g = rgb[1];
                ModeFragment.this.select_b = rgb[2];
                ModeFragment.this.textRed_select.setText(ModeFragment.this.mActivity.getString(R.string.red, new Object[]{Integer.valueOf(ModeFragment.this.select_r)}));
                ModeFragment.this.textRed_select.setBackgroundColor(Color.rgb(ModeFragment.this.select_r, 0, 0));
                ModeFragment.this.textGreen_select.setText(ModeFragment.this.mActivity.getString(R.string.green, new Object[]{Integer.valueOf(ModeFragment.this.select_g)}));
                ModeFragment.this.textGreen_select.setBackgroundColor(Color.rgb(0, ModeFragment.this.select_g, 0));
                ModeFragment.this.tvBlue_select.setText(ModeFragment.this.mActivity.getString(R.string.blue, new Object[]{Integer.valueOf(ModeFragment.this.select_b)}));
                ModeFragment.this.tvBlue_select.setBackgroundColor(Color.rgb(0, 0, ModeFragment.this.select_b));
                SharePersistent.saveBrightData(ModeFragment.this.getActivity(), ModeFragment.this.diyViewTag + "bright", ModeFragment.this.diyViewTag + "bright", 32);
            }
        });
        this.iv_switch_select.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ModeFragment.this.imageViewPicker2.getVisibility() == 0) {
                    ModeFragment.this.iv_switch_select.setImageResource(R.drawable.bg_collor_picker);
                    ModeFragment.this.imageViewPicker2.setVisibility(8);
                    ModeFragment.this.myColor_select.setVisibility(0);
                    return;
                }
                ModeFragment.this.iv_switch_select.setImageResource(R.drawable.collor_picker);
                ModeFragment.this.myColor_select.setVisibility(8);
                ModeFragment.this.imageViewPicker2.setVisibility(0);
            }
        });
        final ColorPicker colorPicker = new ColorPicker(this.mActivity, this.select_r, this.select_g, this.select_b);
        colorPicker.setCallback(new ColorPickerCallback() { // from class: com.home.fragment.ble.ModeFragment.25
            @Override // com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback
            public void onColorChosen(int i) {
                int[] rgb = Tool.getRGB(i);
                ModeFragment.this.currentSelecColorFromPicker = i;
                ModeFragment.this.blackWiteSelectView2.setStartColor(i);
                ModeFragment.this.updateRgbText(rgb, false);
                ModeFragment.this.select_r = Color.red(i);
                ModeFragment.this.select_g = Color.green(i);
                ModeFragment.this.select_b = Color.blue(i);
                ModeFragment.this.textRed_select.setBackgroundColor(Color.rgb(ModeFragment.this.select_r, 0, 0));
                ModeFragment.this.textGreen_select.setBackgroundColor(Color.rgb(0, ModeFragment.this.select_g, 0));
                ModeFragment.this.tvBlue_select.setBackgroundColor(Color.rgb(0, 0, ModeFragment.this.select_b));
                ModeFragment.this.textRed_select.setText(ModeFragment.this.mActivity.getString(R.string.red, new Object[]{Integer.valueOf(ModeFragment.this.select_r)}));
                ModeFragment.this.textGreen_select.setText(ModeFragment.this.mActivity.getString(R.string.green, new Object[]{Integer.valueOf(ModeFragment.this.select_g)}));
                ModeFragment.this.tvBlue_select.setText(ModeFragment.this.mActivity.getString(R.string.blue, new Object[]{Integer.valueOf(ModeFragment.this.select_b)}));
                if (ModeFragment.this.mActivity != null) {
                    ModeFragment.this.mActivity.setRgb(ModeFragment.this.select_r, ModeFragment.this.select_g, ModeFragment.this.select_b, true, ModeFragment.this.isCAR01DMX.booleanValue(), false, false, false);
                }
                SharePersistent.saveBrightData(ModeFragment.this.getActivity(), ModeFragment.this.diyViewTag + "bright", ModeFragment.this.diyViewTag + "bright", 32);
            }
        });
        colorPicker.enableAutoClose();
        this.linearChouse_select.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                colorPicker.setColors(ModeFragment.this.select_r, ModeFragment.this.select_g, ModeFragment.this.select_b);
                colorPicker.show();
            }
        });
        this.blackWiteSelectView2.setOnSelectColor(new BlackWiteSelectView.OnSelectColor() { // from class: com.home.fragment.ble.ModeFragment.27
            @Override // com.home.view.BlackWiteSelectView.OnSelectColor
            public void onColorSelect(int i, int i2) {
                if (i2 <= 0) {
                    i2 = 0;
                } else if (i2 >= 100) {
                    i2 = 100;
                }
                SharePersistent.saveBrightData(ModeFragment.this.getActivity(), ModeFragment.this.diyViewTag + "bright", ModeFragment.this.diyViewTag + "bright", i2);
                if (ModeFragment.this.mActivity != null) {
                    if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-") || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi)) {
                        ModeFragment.this.mActivity.setBrightNess((i2 * 32) / 100, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                    } else {
                        ModeFragment.this.mActivity.setBrightNess(i2, false, false, ModeFragment.this.isCAR01DMX.booleanValue());
                    }
                }
            }
        });
        View findViewById = this.menuView.findViewById(R.id.viewColors);
        ArrayList arrayList = new ArrayList();
        int[] iArr = {SupportMenu.CATEGORY_MASK, -16711936, -16776961, -1, InputDeviceCompat.SOURCE_ANY, -65281};
        HashMap hashMap = new HashMap();
        hashMap.put(Integer.valueOf(iArr[0]), Double.valueOf(0.0d));
        hashMap.put(Integer.valueOf(iArr[1]), Double.valueOf(1.0471975511965976d));
        hashMap.put(Integer.valueOf(iArr[2]), Double.valueOf(2.0943951023931953d));
        hashMap.put(Integer.valueOf(iArr[3]), Double.valueOf(3.141592653589793d));
        hashMap.put(Integer.valueOf(iArr[4]), Double.valueOf(4.1887902047863905d));
        hashMap.put(Integer.valueOf(iArr[5]), Double.valueOf(5.235987755982989d));
        for (int i = 1; i <= 6; i++) {
            View findViewWithTag = findViewById.findViewWithTag("viewColor" + i);
            findViewWithTag.setTag(Integer.valueOf(iArr[i + (-1)]));
            findViewWithTag.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.28
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    int intValue = ((Integer) view.getTag()).intValue();
                    ModeFragment.this.currentSelecColorFromPicker = intValue;
                    ModeFragment.this.blackWiteSelectView2.setStartColor(intValue);
                    ModeFragment.this.imageViewPicker2.setInitialColor(intValue);
                    ModeFragment.this.updateRgbText(Tool.getRGB(intValue), true);
                    Tool.getRGB(intValue);
                }
            });
            arrayList.add(findViewWithTag);
        }
        this.buttonSelectColorConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.ModeFragment.29
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ModeFragment.this.currentSelecColorFromPicker != 0) {
                    if (ModeFragment.this.mActivity == null) {
                        ModeFragment.this.actionView.setColor(ModeFragment.this.currentSelecColorFromPicker);
                        ModeFragment.this.sharedPreferences.edit().putInt(ModeFragment.this.actionView.getTag() + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY), ModeFragment.this.currentSelecColorFromPicker).commit();
                        if (ModeFragment.this.currentSelecColorFromPicker == 255) {
                            ModeFragment.this.actionView.setText("Auto");
                        } else {
                            ModeFragment.this.actionView.setText("" + ModeFragment.this.currentSelecColorFromPicker);
                        }
                    } else if (!MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDBLE_00) && !MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-") && (!MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") || ModeFragment.this.isCAR01DMX.booleanValue())) {
                        ModeFragment.this.actionView.setColor(ModeFragment.this.currentSelecColorFromPicker);
                        String str = ModeFragment.this.actionView.getTag() + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY);
                        ModeFragment.this.sharedPreferences.edit().putInt(str, ModeFragment.this.currentSelecColorFromPicker).commit();
                        if (MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT)) {
                            if (ModeFragment.this.currentSelecColorFromPicker <= 157) {
                                ModeFragment.this.sharedPreferences.edit().putInt(str, ModeFragment.this.currentSelecColorFromPicker).commit();
                                Drawable image = ModeFragment.this.getImage("" + ModeFragment.this.currentSelecColorFromPicker);
                                if (image != null) {
                                    ModeFragment.this.actionView.setBackgroundDrawable(image);
                                }
                                ModeFragment.this.actionView.setText("");
                            } else {
                                ColorTextView colorTextView = ModeFragment.this.actionView;
                                StringBuilder sb = new StringBuilder();
                                sb.append("");
                                sb.append(ModeFragment.this.currentSelecColorFromPicker - 134);
                                colorTextView.setText(sb.toString());
                            }
                        } else if (ModeFragment.this.currentSelecColorFromPicker == 255) {
                            ModeFragment.this.actionView.setText("Auto");
                        } else {
                            ModeFragment.this.actionView.setText("" + ModeFragment.this.currentSelecColorFromPicker);
                        }
                    } else {
                        String str2 = ((String) ModeFragment.this.actionView.getTag()) + SharePersistent.getPerference(ModeFragment.this.mActivity, Constant.CUSTOM_DIY_APPKEY);
                        if (ModeFragment.this.seekBarModeSC != null && ModeFragment.this.seekBarModeSC.getTag() != null) {
                            if (ModeFragment.this.seekBarModeSC.getTag().toString() != null) {
                                String obj = ModeFragment.this.seekBarModeSC.getTag().toString();
                                ModeFragment.this.sharedPreferences.edit().putInt(str2, Integer.parseInt(obj.trim())).commit();
                                Drawable image2 = ModeFragment.this.getImage(obj);
                                if (image2 != null) {
                                    ModeFragment.this.actionView.setBackgroundDrawable(image2);
                                }
                            }
                            ModeFragment.this.actionView.setText("");
                        }
                    }
                }
                ModeFragment.this.hideColorCover();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$initColorSelecterView$0$com-home-fragment-ble-ModeFragment  reason: not valid java name */
    public /* synthetic */ void m18x9ea1ae94(int i, boolean z, boolean z2) {
        if (z) {
            this.blackWiteSelectView2.setStartColor(i);
            this.currentSelecColorFromPicker = i;
            int[] rgb = Tool.getRGB(i);
            updateRgbText(rgb, false);
            int i2 = rgb[0];
            this.select_r = i2;
            this.select_g = rgb[1];
            this.select_b = rgb[2];
            this.textRed_select.setText(this.mActivity.getString(R.string.red, new Object[]{Integer.valueOf(i2)}));
            this.textRed_select.setBackgroundColor(Color.rgb(this.select_r, 0, 0));
            this.textGreen_select.setText(this.mActivity.getString(R.string.green, new Object[]{Integer.valueOf(this.select_g)}));
            this.textGreen_select.setBackgroundColor(Color.rgb(0, this.select_g, 0));
            this.tvBlue_select.setText(this.mActivity.getString(R.string.blue, new Object[]{Integer.valueOf(this.select_b)}));
            this.tvBlue_select.setBackgroundColor(Color.rgb(0, 0, this.select_b));
            SharePersistent.saveBrightData(getActivity(), this.diyViewTag + "bright", this.diyViewTag + "bright", 32);
        }
    }

    public Drawable getImage(String str) {
        Resources resources = this.mActivity.getResources();
        return this.mActivity.getResources().getDrawable(resources.getIdentifier("img_" + str, "drawable", BuildConfig.APPLICATION_ID));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideColorCover() {
        this.mPopupWindow.dismiss();
    }

    public void updateRgbText(int[] iArr, boolean z) {
        try {
            MainActivity_BLE mainActivity_BLE = this.mActivity;
            if (mainActivity_BLE != null) {
                mainActivity_BLE.setRgb(iArr[0], iArr[1], iArr[2], !z, this.isCAR01DMX.booleanValue(), false, false, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> bleModel() {
        String[] stringArray;
        this.listName.clear();
        this.listNubmer.clear();
        for (String str : this.mActivity.getResources().getStringArray(R.array.ble_mode)) {
            this.listName.add(str.substring(0, str.lastIndexOf(",")));
            this.listNubmer.add(str.substring(str.lastIndexOf(",") + 1).trim());
        }
        return this.listName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> dmxModel() {
        String[] stringArray;
        this.listName.clear();
        this.listNubmer.clear();
        for (String str : this.mActivity.getResources().getStringArray(R.array.dmx_model)) {
            this.listName.add(str.substring(0, str.lastIndexOf(",")));
            this.listNubmer.add(str.substring(str.lastIndexOf(",") + 1).trim());
        }
        return this.listName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> dmxNoAutoListName() {
        ArrayList arrayList = new ArrayList();
        ArrayList<String> arrayList2 = new ArrayList(Arrays.asList(this.mActivity.getResources().getStringArray(R.array.dmx_model)));
        arrayList2.remove(0);
        for (String str : arrayList2) {
            arrayList.add(str.substring(0, str.lastIndexOf(",")));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> car00ListNubmer() {
        String[] stringArray;
        ArrayList arrayList = new ArrayList();
        for (String str : this.mActivity.getResources().getStringArray(R.array.car_mode)) {
            arrayList.add(str.substring(str.lastIndexOf(",") + 1).trim());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> dmxNoAutoListNubmer() {
        ArrayList arrayList = new ArrayList();
        ArrayList<String> arrayList2 = new ArrayList(Arrays.asList(this.mActivity.getResources().getStringArray(R.array.dmx_model)));
        arrayList2.remove(0);
        for (String str : arrayList2) {
            arrayList.add(str.substring(str.lastIndexOf(",") + 1).trim());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> carModel() {
        String[] stringArray;
        this.listName.clear();
        this.listNubmer.clear();
        for (String str : this.mActivity.getResources().getStringArray(R.array.car_mode)) {
            this.listName.add(str.substring(0, str.lastIndexOf(",")));
            this.listNubmer.add(str.substring(str.lastIndexOf(",") + 1).trim());
        }
        return this.listName;
    }

    private List<String> stageModel() {
        this.listName.clear();
        this.listNubmer.clear();
        String[] stringArray = this.mActivity.getResources().getStringArray(R.array.ble_mode);
        ArrayList arrayList = new ArrayList();
        for (String str : stringArray) {
            arrayList.add(str);
        }
        for (int i = 24; i <= 210; i++) {
            arrayList.add("MODE" + i + "," + i);
        }
        for (int i2 = 1; i2 <= arrayList.size(); i2++) {
            String str2 = (String) arrayList.get(i2 - 1);
            this.listName.add(str2.substring(0, str2.lastIndexOf(",")));
            if (i2 <= 23) {
                this.listNubmer.add(str2.substring(str2.lastIndexOf(",") + 1).trim());
            } else {
                this.listNubmer.add("" + (i2 + 134));
            }
        }
        return this.listName;
    }
}
