package com.home.fragment.ble;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
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
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import butterknife.BindView;
import com.common.adapter.OnSeekBarChangeListenerAdapter;
import com.common.uitl.ListUtiles;
import com.common.uitl.SharePersistent;
import com.common.uitl.Tool;
import com.common.view.SegmentedRadioGroup;
import com.home.activity.main.MainActivity_BLE;
import com.home.base.LedBleFragment;
import com.home.bean.MyColor;
import com.home.constant.CommonConstant;
import com.home.constant.Constant;
import com.home.view.BlackWiteSelectView;
import com.home.view.ColorImageView;
import com.home.view.MyColorPicker;
import com.ledlamp.R;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;

/* loaded from: classes.dex */
public class CutomFragmentDmx00Dmx01 extends LedBleFragment {
    private static final int COLOR_DEFALUT = 0;
    private static final String TAG = "CutomFragment";
    private ColorImageView actionView;
    private ImageView backImage;
    private BlackWiteSelectView blackWiteSelectView2;
    private Button buttonSelectColorConfirm;
    @BindView(R.id.changeButtonEight)
    RadioButton changeButtonEight;
    @BindView(R.id.changeButtonFive)
    RadioButton changeButtonFive;
    @BindView(R.id.changeButtonFour)
    RadioButton changeButtonFour;
    @BindView(R.id.changeButtonOne)
    RadioButton changeButtonOne;
    @BindView(R.id.changeButtonSeven)
    RadioButton changeButtonSeven;
    @BindView(R.id.changeButtonSix)
    RadioButton changeButtonSix;
    @BindView(R.id.changeButtonThree)
    RadioButton changeButtonThree;
    @BindView(R.id.changeButtonTwo)
    RadioButton changeButtonTwo;
    private ArrayList<ColorImageView> colorTextViews;
    private int currentSelecColorFromPicker;
    private String diyViewTag;
    private ColorPickerView imageViewPicker2;
    private ImageView iv_switch_select;
    private LinearLayout linearChouse_select;
    private LinearLayout llCoverMode;
    private LinearLayout llRing;
    @BindView(R.id.llSegmentedRadioGroupTwo)
    LinearLayout llSegmentedRadioGroupTwo;
    @BindView(R.id.llViewBlocks)
    LinearLayout llViewBlocks;
    private MainActivity_BLE mActivity;
    private View mContentView;
    private PopupWindow mPopupWindow;
    private View menuView;
    private MyColorPicker myColor_select;
    private RadioButton rbCustomDMX01Cycle;
    private RadioButton rbCustomDMX01Forward;
    private RadioButton rbCustomDMX01Reverse;
    @BindView(R.id.seekBarBrightCustom)
    SeekBar seekBarBrightCustom;
    @BindView(R.id.seekBarSpeedCustom)
    SeekBar seekBarSpeedCustom;
    private SegmentedRadioGroup segmentCustomDMX00Top;
    private SegmentedRadioGroup segmentCustomDMX01Top;
    @BindView(R.id.segmentedRadioGroupOne)
    SegmentedRadioGroup segmentedRadioGroupOne;
    @BindView(R.id.segmentedRadioGroupTwo)
    SegmentedRadioGroup segmentedRadioGroupTwo;
    private SharedPreferences sp;
    private SegmentedRadioGroup srgCover;
    private TextView textGreen_select;
    private TextView textRed_select;
    @BindView(R.id.textViewBrightCustom)
    TextView textViewBrightCustom;
    private TextView textViewRingBrightSC;
    @BindView(R.id.textViewSpeedCustom)
    TextView textViewSpeedCustom;
    private TextView tvBlue_select;
    private Boolean isCAR01DMX = false;
    private int styleOne = -1;
    private int styleTwo = -1;
    private int currentTab = 1;
    private int select_r = 255;
    private int select_g = 255;
    private int select_b = 255;
    private final String NOT_FIRST_INIT = "NOT_FIRST_INIT-" + MainActivity_BLE.getSceneBean();

    @Override // com.home.base.LedBleFragment
    public void initData() {
    }

    @Override // com.home.base.LedBleFragment
    public void initEvent() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContentView = layoutInflater.inflate(R.layout.fragment_custom1, viewGroup, false);
        this.menuView = layoutInflater.inflate(R.layout.activity_select_color, viewGroup, false);
        return this.mContentView;
    }

    @Override // com.home.base.LedBleFragment
    public void initView() {
        if (MainActivity_BLE.getMainActivity() != null) {
            this.mActivity = MainActivity_BLE.getMainActivity();
            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-")) {
                SegmentedRadioGroup segmentCustomDMX00Top = MainActivity_BLE.getMainActivity().getSegmentCustomDMX00Top();
                this.segmentCustomDMX00Top = segmentCustomDMX00Top;
                segmentCustomDMX00Top.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.1
                    @Override // android.widget.RadioGroup.OnCheckedChangeListener
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    }
                });
                this.segmentCustomDMX00Top.findViewById(R.id.rbCustomOne).setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CutomFragmentDmx00Dmx01.this.mActivity.setDirection(0, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                    }
                });
                this.segmentCustomDMX00Top.findViewById(R.id.rbCustomTwo).setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CutomFragmentDmx00Dmx01.this.mActivity.setDirection(1, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                    }
                });
                this.segmentCustomDMX00Top.findViewById(R.id.rbCustomThree).setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CutomFragmentDmx00Dmx01.this.mActivity.setChangeColor(false, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue(), CutomFragmentDmx00Dmx01.this.getSelectColor());
                    }
                });
                this.segmentCustomDMX00Top.findViewById(R.id.rbCustomFour).setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CutomFragmentDmx00Dmx01.this.mActivity.setCustomCycle(false);
                    }
                });
            } else if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-01-")) {
                SegmentedRadioGroup segmentCustomDMX01Top = MainActivity_BLE.getMainActivity().getSegmentCustomDMX01Top();
                this.segmentCustomDMX01Top = segmentCustomDMX01Top;
                this.rbCustomDMX01Forward = (RadioButton) segmentCustomDMX01Top.findViewById(R.id.rbCustomDMX01Forward);
                this.rbCustomDMX01Reverse = (RadioButton) this.segmentCustomDMX01Top.findViewById(R.id.rbCustomDMX01Reverse);
                this.rbCustomDMX01Cycle = (RadioButton) this.segmentCustomDMX01Top.findViewById(R.id.rbCustomDMX01Cycle);
                this.segmentCustomDMX01Top.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.6
                    @Override // android.widget.RadioGroup.OnCheckedChangeListener
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    }
                });
                this.rbCustomDMX01Forward.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.7
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CutomFragmentDmx00Dmx01.this.setDirection(0);
                    }
                });
                this.rbCustomDMX01Reverse.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.8
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CutomFragmentDmx00Dmx01.this.setDirection(1);
                    }
                });
                this.rbCustomDMX01Cycle.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.9
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (CutomFragmentDmx00Dmx01.this.mActivity != null) {
                            CutomFragmentDmx00Dmx01.this.mActivity.setCirculation();
                        }
                    }
                });
            }
        }
        this.sp = getActivity().getSharedPreferences(SharePersistent.getPerference(this.mActivity, Constant.CUSTOM_DIY_APPKEY), 0);
        if (MainActivity_BLE.getMainActivity() != null) {
            if (MainActivity_BLE.getSceneBean().contains(CommonConstant.LEDDMX) || ((MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && this.isCAR01DMX.booleanValue()) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDWiFi))) {
                this.changeButtonOne.setText(getString(R.string.GD));
                this.changeButtonTwo.setText(getString(R.string.FD));
                this.changeButtonThree.setText(getString(R.string.FW));
                this.changeButtonFour.setText(getString(R.string.FS));
                this.changeButtonEight.setVisibility(0);
                this.changeButtonSeven.setVisibility(0);
                this.changeButtonSix.setVisibility(0);
                this.changeButtonFive.setVisibility(0);
            } else if (MainActivity_BLE.getSceneBean().contains(CommonConstant.LEDBLE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-00-") || ((MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDCAR-01-") && !this.isCAR01DMX.booleanValue()) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSMART) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDSTAGE) || MainActivity_BLE.getSceneBean().equalsIgnoreCase(CommonConstant.LEDLIGHT))) {
                this.llSegmentedRadioGroupTwo.setVisibility(8);
            }
        }
        this.segmentedRadioGroupOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.10
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (R.id.changeButtonOne == i) {
                    CutomFragmentDmx00Dmx01.this.styleOne = 0;
                } else if (R.id.changeButtonTwo == i) {
                    CutomFragmentDmx00Dmx01.this.styleOne = 1;
                } else if (R.id.changeButtonThree == i) {
                    CutomFragmentDmx00Dmx01.this.styleOne = 2;
                } else if (R.id.changeButtonFour == i) {
                    CutomFragmentDmx00Dmx01.this.styleOne = 3;
                }
            }
        });
        this.changeButtonOne.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                cutomFragmentDmx00Dmx01.SendCMD(cutomFragmentDmx00Dmx01.styleOne, true);
            }
        });
        this.changeButtonTwo.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                cutomFragmentDmx00Dmx01.SendCMD(cutomFragmentDmx00Dmx01.styleOne, true);
            }
        });
        this.changeButtonThree.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                cutomFragmentDmx00Dmx01.SendCMD(cutomFragmentDmx00Dmx01.styleOne, true);
            }
        });
        this.changeButtonFour.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                cutomFragmentDmx00Dmx01.SendCMD(cutomFragmentDmx00Dmx01.styleOne, true);
            }
        });
        this.segmentedRadioGroupTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.15
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (R.id.changeButtonFive == i) {
                    CutomFragmentDmx00Dmx01.this.styleTwo = 4;
                } else if (R.id.changeButtonSix == i) {
                    CutomFragmentDmx00Dmx01.this.styleTwo = 5;
                } else if (R.id.changeButtonSeven == i) {
                    CutomFragmentDmx00Dmx01.this.styleTwo = 6;
                } else if (R.id.changeButtonEight == i) {
                    CutomFragmentDmx00Dmx01.this.styleTwo = 7;
                }
            }
        });
        this.changeButtonFive.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                cutomFragmentDmx00Dmx01.SendCMD(cutomFragmentDmx00Dmx01.styleTwo, false);
            }
        });
        this.changeButtonSix.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                cutomFragmentDmx00Dmx01.SendCMD(cutomFragmentDmx00Dmx01.styleTwo, false);
            }
        });
        this.changeButtonSeven.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                cutomFragmentDmx00Dmx01.SendCMD(cutomFragmentDmx00Dmx01.styleTwo, false);
            }
        });
        this.changeButtonEight.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                cutomFragmentDmx00Dmx01.SendCMD(cutomFragmentDmx00Dmx01.styleTwo, false);
            }
        });
        this.seekBarSpeedCustom.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.20
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                if (CutomFragmentDmx00Dmx01.this.textViewSpeedCustom == null || CutomFragmentDmx00Dmx01.this.textViewSpeedCustom.getTag() == null) {
                    return;
                }
                if (CutomFragmentDmx00Dmx01.this.textViewSpeedCustom.getTag().equals(100)) {
                    if (CutomFragmentDmx00Dmx01.this.mActivity != null) {
                        CutomFragmentDmx00Dmx01.this.mActivity.setSpeed(100, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                    }
                } else if (!CutomFragmentDmx00Dmx01.this.textViewSpeedCustom.getTag().equals(1) || CutomFragmentDmx00Dmx01.this.mActivity == null) {
                } else {
                    CutomFragmentDmx00Dmx01.this.mActivity.setSpeed(1, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (i == 0) {
                    if (CutomFragmentDmx00Dmx01.this.mActivity != null) {
                        CutomFragmentDmx00Dmx01.this.mActivity.setSpeed(1, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                    }
                    CutomFragmentDmx00Dmx01.this.textViewSpeedCustom.setText(CutomFragmentDmx00Dmx01.this.getActivity().getResources().getString(R.string.speed_set, 1));
                    CutomFragmentDmx00Dmx01.this.textViewSpeedCustom.setTag(1);
                } else {
                    if (CutomFragmentDmx00Dmx01.this.mActivity != null) {
                        CutomFragmentDmx00Dmx01.this.mActivity.setSpeed(i, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                    }
                    CutomFragmentDmx00Dmx01.this.textViewSpeedCustom.setText(CutomFragmentDmx00Dmx01.this.getActivity().getResources().getString(R.string.speed_set, Integer.valueOf(i)));
                    CutomFragmentDmx00Dmx01.this.textViewSpeedCustom.setTag(Integer.valueOf(i));
                }
                if (MainActivity_BLE.sceneBean != null) {
                    MainActivity_BLE mainActivity_BLE = CutomFragmentDmx00Dmx01.this.mActivity;
                    SharePersistent.saveInt(mainActivity_BLE, MainActivity_BLE.sceneBean + CutomFragmentDmx00Dmx01.TAG + "speed", i);
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE = this.mActivity;
            int i = SharePersistent.getInt(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + "speed");
            if (i > 0) {
                this.seekBarSpeedCustom.setProgress(i);
                this.textViewSpeedCustom.setText(getContext().getResources().getString(R.string.speed_set, String.valueOf(i)));
            } else {
                this.textViewSpeedCustom.setText(getContext().getResources().getString(R.string.speed_set, String.valueOf(80)));
            }
        }
        this.seekBarBrightCustom.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.21
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                if (CutomFragmentDmx00Dmx01.this.textViewBrightCustom == null || CutomFragmentDmx00Dmx01.this.textViewBrightCustom.getTag() == null) {
                    return;
                }
                if (CutomFragmentDmx00Dmx01.this.textViewBrightCustom.getTag().equals(100)) {
                    if (CutomFragmentDmx00Dmx01.this.mActivity != null) {
                        CutomFragmentDmx00Dmx01.this.mActivity.setBrightNess(100, false, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                    }
                } else if (!CutomFragmentDmx00Dmx01.this.textViewBrightCustom.getTag().equals(1) || CutomFragmentDmx00Dmx01.this.mActivity == null) {
                } else {
                    CutomFragmentDmx00Dmx01.this.mActivity.setBrightNess(1, false, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                if (z) {
                    if (i2 == 0) {
                        if (CutomFragmentDmx00Dmx01.this.mActivity != null) {
                            CutomFragmentDmx00Dmx01.this.mActivity.setBrightNess(1, false, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                        }
                        CutomFragmentDmx00Dmx01.this.textViewBrightCustom.setText(CutomFragmentDmx00Dmx01.this.getActivity().getResources().getString(R.string.brightness_set, 1));
                        CutomFragmentDmx00Dmx01.this.textViewBrightCustom.setTag(1);
                    } else {
                        if (CutomFragmentDmx00Dmx01.this.mActivity != null) {
                            CutomFragmentDmx00Dmx01.this.mActivity.setBrightNess(i2, false, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
                        }
                        CutomFragmentDmx00Dmx01.this.textViewBrightCustom.setText(CutomFragmentDmx00Dmx01.this.getActivity().getResources().getString(R.string.brightness_set, Integer.valueOf(i2)));
                        CutomFragmentDmx00Dmx01.this.textViewBrightCustom.setTag(Integer.valueOf(i2));
                    }
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE2 = CutomFragmentDmx00Dmx01.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + CutomFragmentDmx00Dmx01.TAG + "bright", i2);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE2 = this.mActivity;
            int i2 = SharePersistent.getInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + TAG + "bright");
            if (i2 > 0) {
                this.seekBarBrightCustom.setProgress(i2);
                this.textViewBrightCustom.setText(getContext().getResources().getString(R.string.brightness_set, String.valueOf(i2)));
            } else {
                this.textViewBrightCustom.setText(getContext().getResources().getString(R.string.brightness_set, String.valueOf(100)));
            }
        }
        this.buttonSelectColorConfirm = (Button) this.menuView.findViewById(R.id.buttonSelectColorConfirm);
        this.srgCover = (SegmentedRadioGroup) this.menuView.findViewById(R.id.srgCover);
        this.llRing = (LinearLayout) this.menuView.findViewById(R.id.llRing);
        this.llCoverMode = (LinearLayout) this.menuView.findViewById(R.id.llCoverMode);
        this.imageViewPicker2 = (ColorPickerView) this.menuView.findViewById(R.id.imageViewPicker2);
        this.blackWiteSelectView2 = (BlackWiteSelectView) this.menuView.findViewById(R.id.blackWiteSelectView2);
        this.textViewRingBrightSC = (TextView) this.menuView.findViewById(R.id.tvRingBrightnessSC);
        initColorBlock();
        initColorSelecterView();
        ImageView imageView = (ImageView) this.menuView.findViewById(R.id.backImage);
        this.backImage = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CutomFragmentDmx00Dmx01.this.hideColorCover();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDirection(int i) {
        MainActivity_BLE mainActivity_BLE = this.mActivity;
        if (mainActivity_BLE != null) {
            mainActivity_BLE.setDirection(i, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void SendCMD(int i, boolean z) {
        if (this.mActivity != null) {
            if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-")) {
                this.mActivity.setMode(false, false, this.isCAR01DMX.booleanValue(), i);
            } else {
                this.mActivity.setDiy(getSelectColor(), i);
            }
        }
        if (z) {
            if (this.styleTwo >= 4) {
                this.segmentedRadioGroupTwo.clearCheck();
                return;
            }
            return;
        }
        int i2 = this.styleOne;
        if (i2 < 0 || i2 > 3) {
            return;
        }
        this.segmentedRadioGroupOne.clearCheck();
    }

    private void initColorBlock() {
        String str;
        int i;
        int i2;
        boolean z;
        boolean z2;
        CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = this;
        int[] iArr = {SupportMenu.CATEGORY_MASK, -16711936, -16776961, InputDeviceCompat.SOURCE_ANY, -16711681, -65281, -1};
        LinearLayout linearLayout = cutomFragmentDmx00Dmx01.llViewBlocks;
        cutomFragmentDmx00Dmx01.colorTextViews = new ArrayList<>();
        int i3 = 1;
        while (true) {
            str = "labelColor";
            i = 25;
            i2 = 0;
            if (i3 > 25) {
                z = false;
                break;
            }
            if (cutomFragmentDmx00Dmx01.sp.getInt((String) ((ColorImageView) linearLayout.findViewWithTag("labelColor" + i3)).getTag(), 0) != 0) {
                z = true;
                break;
            }
            i3++;
        }
        int i4 = 1;
        while (i4 <= i) {
            final ColorImageView colorImageView = (ColorImageView) linearLayout.findViewWithTag(str + i4);
            String str2 = (String) colorImageView.getTag();
            int i5 = cutomFragmentDmx00Dmx01.sp.getInt(str2, i2);
            LinearLayout linearLayout2 = linearLayout;
            String str3 = str;
            final int[] iArr2 = iArr;
            if (i5 != 0) {
                String substring = str2.substring(10);
                if (substring.equals("1")) {
                    colorImageView.setImageResource(R.drawable.dmx_custom_red_1);
                } else if (substring.equals(ExifInterface.GPS_MEASUREMENT_2D) || substring.equals(ExifInterface.GPS_MEASUREMENT_3D) || substring.equals("4")) {
                    colorImageView.setImageResource(R.drawable.dmx_custom_red_2_3_4);
                } else if (substring.equals("5") || substring.equals("15")) {
                    colorImageView.setImageResource(R.drawable.dmx_custom_red_5_15);
                } else if (substring.equals("6") || substring.equals("16")) {
                    colorImageView.setImageResource(R.drawable.dmx_custom_red_6_16);
                } else if (substring.equals("7") || substring.equals("8") || substring.equals("9") || substring.equals("12") || substring.equals("13") || substring.equals("14") || substring.equals("17") || substring.equals("18") || substring.equals("19") || substring.equals("22") || substring.equals("23") || substring.equals("24")) {
                    colorImageView.setImageResource(R.drawable.dmx_custom_red_7_8_9_12_13_14_17_18_19_22_23_24);
                } else if (substring.equals("10") || substring.equals("20")) {
                    colorImageView.setImageResource(R.drawable.dmx_custom_red_10_20);
                } else if (substring.equals("11") || substring.equals("21")) {
                    colorImageView.setImageResource(R.drawable.dmx_custom_red_11_21);
                } else if (substring.equals("25")) {
                    colorImageView.setImageResource(R.drawable.dmx_custom_red_25);
                }
                colorImageView.setColorFilter(Color.parseColor(String.format("#%06X", Integer.valueOf(i5 & ViewCompat.MEASURED_SIZE_MASK))));
                colorImageView.setColor(i5);
            } else if (!z && i4 <= 7) {
                cutomFragmentDmx00Dmx01 = this;
                z2 = z;
                if (!SharePersistent.getBoolean(getContext(), cutomFragmentDmx00Dmx01.NOT_FIRST_INIT)) {
                    String substring2 = str2.substring(10);
                    if (substring2.equals("1")) {
                        colorImageView.setImageResource(R.drawable.dmx_custom_red_1);
                    } else if (substring2.equals(ExifInterface.GPS_MEASUREMENT_2D) || substring2.equals(ExifInterface.GPS_MEASUREMENT_3D) || substring2.equals("4")) {
                        colorImageView.setImageResource(R.drawable.dmx_custom_red_2_3_4);
                    } else if (substring2.equals("5") || substring2.equals("15")) {
                        colorImageView.setImageResource(R.drawable.dmx_custom_red_5_15);
                    } else if (substring2.equals("6") || substring2.equals("16")) {
                        colorImageView.setImageResource(R.drawable.dmx_custom_red_6_16);
                    } else if (substring2.equals("7") || substring2.equals("8") || substring2.equals("9") || substring2.equals("12") || substring2.equals("13") || substring2.equals("14") || substring2.equals("17") || substring2.equals("18") || substring2.equals("19") || substring2.equals("22") || substring2.equals("23") || substring2.equals("24")) {
                        colorImageView.setImageResource(R.drawable.dmx_custom_red_7_8_9_12_13_14_17_18_19_22_23_24);
                    } else if (substring2.equals("10") || substring2.equals("20")) {
                        colorImageView.setImageResource(R.drawable.dmx_custom_red_10_20);
                    } else if (substring2.equals("11") || substring2.equals("21")) {
                        colorImageView.setImageResource(R.drawable.dmx_custom_red_11_21);
                    } else if (substring2.equals("25")) {
                        colorImageView.setImageResource(R.drawable.dmx_custom_red_25);
                    }
                    int i6 = i4 - 1;
                    colorImageView.setColorFilter(Color.parseColor(String.format("#%06X", Integer.valueOf(iArr2[i6] & ViewCompat.MEASURED_SIZE_MASK))));
                    colorImageView.setColor(iArr2[i6]);
                    cutomFragmentDmx00Dmx01.sp.edit().putInt((String) colorImageView.getTag(), iArr2[i6]).commit();
                    if (i4 == 7) {
                        SharePersistent.saveBoolean(getContext(), cutomFragmentDmx00Dmx01.NOT_FIRST_INIT, true);
                        colorImageView.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.23
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                view.startAnimation(AnimationUtils.loadAnimation(CutomFragmentDmx00Dmx01.this.getActivity(), R.anim.layout_scale));
                                int color = colorImageView.getColor();
                                if (color == 0) {
                                    CutomFragmentDmx00Dmx01.this.showColorCover((ColorImageView) view, false);
                                    return;
                                }
                                CutomFragmentDmx00Dmx01.this.updateRgbText(Tool.getRGB(color), false, true, false, Integer.parseInt(((String) colorImageView.getTag()).substring(10)));
                            }
                        });
                        colorImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.24
                            @Override // android.view.View.OnLongClickListener
                            public boolean onLongClick(View view) {
                                ColorImageView colorImageView2 = (ColorImageView) view;
                                colorImageView2.setColor(0);
                                colorImageView2.setColorFilter(Color.parseColor("#ffffff"));
                                String str4 = (String) colorImageView.getTag();
                                CutomFragmentDmx00Dmx01.this.sp.edit().putInt(str4, 0).commit();
                                String substring3 = str4.substring(10);
                                if (substring3.equals("1")) {
                                    colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_1);
                                } else if (substring3.equals(ExifInterface.GPS_MEASUREMENT_2D) || substring3.equals(ExifInterface.GPS_MEASUREMENT_3D) || substring3.equals("4")) {
                                    colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_2_3_4);
                                } else if (substring3.equals("5") || substring3.equals("15")) {
                                    colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_5_15);
                                } else if (substring3.equals("6") || substring3.equals("16")) {
                                    colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_6_16);
                                } else if (substring3.equals("7") || substring3.equals("8") || substring3.equals("9") || substring3.equals("12") || substring3.equals("13") || substring3.equals("14") || substring3.equals("17") || substring3.equals("18") || substring3.equals("19") || substring3.equals("22") || substring3.equals("23") || substring3.equals("24")) {
                                    colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_7_8_9_12_13_14_17_18_19_22_23_24);
                                } else if (substring3.equals("10") || substring3.equals("20")) {
                                    colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_10_20);
                                } else if (substring3.equals("11") || substring3.equals("21")) {
                                    colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_11_21);
                                } else if (substring3.equals("25")) {
                                    colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_25);
                                }
                                Log.e(CutomFragmentDmx00Dmx01.TAG, "currentSelecColorFromPicker = 0");
                                CutomFragmentDmx00Dmx01.this.updateRgbText(iArr2, false, true, true, Integer.parseInt(substring3));
                                return true;
                            }
                        });
                        cutomFragmentDmx00Dmx01.colorTextViews.add(colorImageView);
                        i4++;
                        iArr = iArr2;
                        linearLayout = linearLayout2;
                        str = str3;
                        z = z2;
                        i = 25;
                        i2 = 0;
                    }
                }
                colorImageView.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.23
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        view.startAnimation(AnimationUtils.loadAnimation(CutomFragmentDmx00Dmx01.this.getActivity(), R.anim.layout_scale));
                        int color = colorImageView.getColor();
                        if (color == 0) {
                            CutomFragmentDmx00Dmx01.this.showColorCover((ColorImageView) view, false);
                            return;
                        }
                        CutomFragmentDmx00Dmx01.this.updateRgbText(Tool.getRGB(color), false, true, false, Integer.parseInt(((String) colorImageView.getTag()).substring(10)));
                    }
                });
                colorImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.24
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view) {
                        ColorImageView colorImageView2 = (ColorImageView) view;
                        colorImageView2.setColor(0);
                        colorImageView2.setColorFilter(Color.parseColor("#ffffff"));
                        String str4 = (String) colorImageView.getTag();
                        CutomFragmentDmx00Dmx01.this.sp.edit().putInt(str4, 0).commit();
                        String substring3 = str4.substring(10);
                        if (substring3.equals("1")) {
                            colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_1);
                        } else if (substring3.equals(ExifInterface.GPS_MEASUREMENT_2D) || substring3.equals(ExifInterface.GPS_MEASUREMENT_3D) || substring3.equals("4")) {
                            colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_2_3_4);
                        } else if (substring3.equals("5") || substring3.equals("15")) {
                            colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_5_15);
                        } else if (substring3.equals("6") || substring3.equals("16")) {
                            colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_6_16);
                        } else if (substring3.equals("7") || substring3.equals("8") || substring3.equals("9") || substring3.equals("12") || substring3.equals("13") || substring3.equals("14") || substring3.equals("17") || substring3.equals("18") || substring3.equals("19") || substring3.equals("22") || substring3.equals("23") || substring3.equals("24")) {
                            colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_7_8_9_12_13_14_17_18_19_22_23_24);
                        } else if (substring3.equals("10") || substring3.equals("20")) {
                            colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_10_20);
                        } else if (substring3.equals("11") || substring3.equals("21")) {
                            colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_11_21);
                        } else if (substring3.equals("25")) {
                            colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_25);
                        }
                        Log.e(CutomFragmentDmx00Dmx01.TAG, "currentSelecColorFromPicker = 0");
                        CutomFragmentDmx00Dmx01.this.updateRgbText(iArr2, false, true, true, Integer.parseInt(substring3));
                        return true;
                    }
                });
                cutomFragmentDmx00Dmx01.colorTextViews.add(colorImageView);
                i4++;
                iArr = iArr2;
                linearLayout = linearLayout2;
                str = str3;
                z = z2;
                i = 25;
                i2 = 0;
            }
            z2 = z;
            cutomFragmentDmx00Dmx01 = this;
            colorImageView.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.23
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    view.startAnimation(AnimationUtils.loadAnimation(CutomFragmentDmx00Dmx01.this.getActivity(), R.anim.layout_scale));
                    int color = colorImageView.getColor();
                    if (color == 0) {
                        CutomFragmentDmx00Dmx01.this.showColorCover((ColorImageView) view, false);
                        return;
                    }
                    CutomFragmentDmx00Dmx01.this.updateRgbText(Tool.getRGB(color), false, true, false, Integer.parseInt(((String) colorImageView.getTag()).substring(10)));
                }
            });
            colorImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.24
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    ColorImageView colorImageView2 = (ColorImageView) view;
                    colorImageView2.setColor(0);
                    colorImageView2.setColorFilter(Color.parseColor("#ffffff"));
                    String str4 = (String) colorImageView.getTag();
                    CutomFragmentDmx00Dmx01.this.sp.edit().putInt(str4, 0).commit();
                    String substring3 = str4.substring(10);
                    if (substring3.equals("1")) {
                        colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_1);
                    } else if (substring3.equals(ExifInterface.GPS_MEASUREMENT_2D) || substring3.equals(ExifInterface.GPS_MEASUREMENT_3D) || substring3.equals("4")) {
                        colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_2_3_4);
                    } else if (substring3.equals("5") || substring3.equals("15")) {
                        colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_5_15);
                    } else if (substring3.equals("6") || substring3.equals("16")) {
                        colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_6_16);
                    } else if (substring3.equals("7") || substring3.equals("8") || substring3.equals("9") || substring3.equals("12") || substring3.equals("13") || substring3.equals("14") || substring3.equals("17") || substring3.equals("18") || substring3.equals("19") || substring3.equals("22") || substring3.equals("23") || substring3.equals("24")) {
                        colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_7_8_9_12_13_14_17_18_19_22_23_24);
                    } else if (substring3.equals("10") || substring3.equals("20")) {
                        colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_10_20);
                    } else if (substring3.equals("11") || substring3.equals("21")) {
                        colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_11_21);
                    } else if (substring3.equals("25")) {
                        colorImageView2.setImageResource(R.drawable.dmx_custom_translucence_25);
                    }
                    Log.e(CutomFragmentDmx00Dmx01.TAG, "currentSelecColorFromPicker = 0");
                    CutomFragmentDmx00Dmx01.this.updateRgbText(iArr2, false, true, true, Integer.parseInt(substring3));
                    return true;
                }
            });
            cutomFragmentDmx00Dmx01.colorTextViews.add(colorImageView);
            i4++;
            iArr = iArr2;
            linearLayout = linearLayout2;
            str = str3;
            z = z2;
            i = 25;
            i2 = 0;
        }
    }

    public Drawable getImage(String str) {
        Resources resources = getActivity().getResources();
        return getActivity().getResources().getDrawable(resources.getIdentifier("img_" + str, "drawable", "com.Home.ledble"));
    }

    public void showColorCover(ColorImageView colorImageView, boolean z) {
        this.actionView = colorImageView;
        this.currentSelecColorFromPicker = 0;
        this.srgCover.check(R.id.rbRing);
        if (z) {
            this.srgCover.setVisibility(4);
            this.llRing.setVisibility(0);
            this.llCoverMode.setVisibility(8);
            this.blackWiteSelectView2.setVisibility(0);
        } else {
            this.srgCover.setVisibility(4);
            this.llRing.setVisibility(0);
            this.llCoverMode.setVisibility(8);
            this.blackWiteSelectView2.setVisibility(8);
            this.textViewRingBrightSC.setVisibility(8);
        }
        View view = this.menuView;
        if (view != null && view.getParent() != null) {
            ((ViewGroup) this.menuView.getParent()).removeAllViews();
        }
        PopupWindow popupWindow = new PopupWindow(this.menuView, -1, -1, true);
        this.mPopupWindow = popupWindow;
        popupWindow.showAtLocation(this.mContentView, 80, 0, 0);
    }

    private void initColorSelecterView() {
        this.myColor_select = (MyColorPicker) this.menuView.findViewById(R.id.myColor_select);
        this.linearChouse_select = (LinearLayout) this.menuView.findViewById(R.id.linearChouse_select);
        this.textRed_select = (TextView) this.menuView.findViewById(R.id.textRed_select);
        this.textGreen_select = (TextView) this.menuView.findViewById(R.id.textGreen_select);
        this.tvBlue_select = (TextView) this.menuView.findViewById(R.id.tvBlue_select);
        this.iv_switch_select = (ImageView) this.menuView.findViewById(R.id.iv_switch_select);
        this.myColor_select.setOnColorChangedListener(new MyColorPicker.OnColorChangedListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.25
            @Override // com.home.view.MyColorPicker.OnColorChangedListener
            public void onColorChanged(int i) {
                int[] rgb = Tool.getRGB(i);
                CutomFragmentDmx00Dmx01.this.blackWiteSelectView2.setStartColor(i);
                CutomFragmentDmx00Dmx01.this.currentSelecColorFromPicker = i;
                CutomFragmentDmx00Dmx01.this.updateRgbText(rgb, true, false, false, 0);
                CutomFragmentDmx00Dmx01.this.select_r = rgb[0];
                CutomFragmentDmx00Dmx01.this.select_g = rgb[1];
                CutomFragmentDmx00Dmx01.this.select_b = rgb[2];
                CutomFragmentDmx00Dmx01.this.textRed_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.red, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_r)}));
                CutomFragmentDmx00Dmx01.this.textRed_select.setBackgroundColor(Color.rgb(CutomFragmentDmx00Dmx01.this.select_r, 0, 0));
                CutomFragmentDmx00Dmx01.this.textGreen_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.green, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_g)}));
                CutomFragmentDmx00Dmx01.this.textGreen_select.setBackgroundColor(Color.rgb(0, CutomFragmentDmx00Dmx01.this.select_g, 0));
                CutomFragmentDmx00Dmx01.this.tvBlue_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.blue, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_b)}));
                CutomFragmentDmx00Dmx01.this.tvBlue_select.setBackgroundColor(Color.rgb(0, 0, CutomFragmentDmx00Dmx01.this.select_b));
                SharePersistent.saveBrightData(CutomFragmentDmx00Dmx01.this.mActivity, CutomFragmentDmx00Dmx01.this.diyViewTag + "bright", CutomFragmentDmx00Dmx01.this.diyViewTag + "bright", 32);
            }
        });
        this.iv_switch_select.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CutomFragmentDmx00Dmx01.this.imageViewPicker2.getVisibility() == 0) {
                    CutomFragmentDmx00Dmx01.this.iv_switch_select.setImageResource(R.drawable.bg_collor_picker);
                    CutomFragmentDmx00Dmx01.this.imageViewPicker2.setVisibility(8);
                    CutomFragmentDmx00Dmx01.this.myColor_select.setVisibility(0);
                    return;
                }
                CutomFragmentDmx00Dmx01.this.iv_switch_select.setImageResource(R.drawable.collor_picker);
                CutomFragmentDmx00Dmx01.this.myColor_select.setVisibility(8);
                CutomFragmentDmx00Dmx01.this.imageViewPicker2.setVisibility(0);
            }
        });
        final ColorPicker colorPicker = new ColorPicker(this.mActivity, this.select_r, this.select_g, this.select_b);
        colorPicker.setCallback(new ColorPickerCallback() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.27
            @Override // com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback
            public void onColorChosen(int i) {
                CutomFragmentDmx00Dmx01.this.currentSelecColorFromPicker = i;
                CutomFragmentDmx00Dmx01.this.blackWiteSelectView2.setStartColor(i);
                CutomFragmentDmx00Dmx01.this.updateRgbText(Tool.getRGB(i), true, false, false, 0);
                CutomFragmentDmx00Dmx01.this.select_r = Color.red(i);
                CutomFragmentDmx00Dmx01.this.select_g = Color.green(i);
                CutomFragmentDmx00Dmx01.this.select_b = Color.blue(i);
                CutomFragmentDmx00Dmx01.this.textRed_select.setBackgroundColor(Color.rgb(CutomFragmentDmx00Dmx01.this.select_r, 0, 0));
                CutomFragmentDmx00Dmx01.this.textGreen_select.setBackgroundColor(Color.rgb(0, CutomFragmentDmx00Dmx01.this.select_g, 0));
                CutomFragmentDmx00Dmx01.this.tvBlue_select.setBackgroundColor(Color.rgb(0, 0, CutomFragmentDmx00Dmx01.this.select_b));
                CutomFragmentDmx00Dmx01.this.textRed_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.red, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_r)}));
                CutomFragmentDmx00Dmx01.this.textGreen_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.green, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_g)}));
                CutomFragmentDmx00Dmx01.this.tvBlue_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.blue, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_b)}));
                SharePersistent.saveBrightData(CutomFragmentDmx00Dmx01.this.mActivity, CutomFragmentDmx00Dmx01.this.diyViewTag + "bright", CutomFragmentDmx00Dmx01.this.diyViewTag + "bright", 32);
            }
        });
        colorPicker.enableAutoClose();
        this.linearChouse_select.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                colorPicker.setColors(CutomFragmentDmx00Dmx01.this.select_r, CutomFragmentDmx00Dmx01.this.select_g, CutomFragmentDmx00Dmx01.this.select_b);
                colorPicker.show();
            }
        });
        this.imageViewPicker2.setInitialColor(getResources().getColor(R.color.white));
        this.imageViewPicker2.subscribe(new ColorObserver() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01$$ExternalSyntheticLambda0
            @Override // top.defaults.colorpicker.ColorObserver
            public final void onColor(int i, boolean z, boolean z2) {
                CutomFragmentDmx00Dmx01.this.m17x5a893350(i, z, z2);
            }
        });
        this.blackWiteSelectView2.setOnSelectColor(new BlackWiteSelectView.OnSelectColor() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.29
            @Override // com.home.view.BlackWiteSelectView.OnSelectColor
            public void onColorSelect(int i, int i2) {
                if (i2 <= 0) {
                    i2 = -3;
                } else if (i2 >= 100) {
                    i2 = 100;
                }
                if (i2 > 95) {
                    return;
                }
                int i3 = (i2 + 3) / 3;
                CutomFragmentDmx00Dmx01.this.textViewRingBrightSC.setText(CutomFragmentDmx00Dmx01.this.getActivity().getResources().getString(R.string.brightness_set, Integer.valueOf(i3)));
                SharePersistent.saveBrightData(CutomFragmentDmx00Dmx01.this.mActivity, CutomFragmentDmx00Dmx01.this.diyViewTag + "bright", CutomFragmentDmx00Dmx01.this.diyViewTag + "bright", i3);
                if (CutomFragmentDmx00Dmx01.this.mActivity != null) {
                    CutomFragmentDmx00Dmx01.this.mActivity.setBrightNess(i3, false, false, CutomFragmentDmx00Dmx01.this.isCAR01DMX.booleanValue());
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
            findViewWithTag.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.30
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    view.startAnimation(AnimationUtils.loadAnimation(CutomFragmentDmx00Dmx01.this.getActivity(), R.anim.layout_scale));
                    int intValue = ((Integer) view.getTag()).intValue();
                    CutomFragmentDmx00Dmx01.this.currentSelecColorFromPicker = intValue;
                    CutomFragmentDmx00Dmx01.this.blackWiteSelectView2.setStartColor(intValue);
                    CutomFragmentDmx00Dmx01.this.imageViewPicker2.setInitialColor(intValue);
                    int[] rgb = Tool.getRGB(intValue);
                    CutomFragmentDmx00Dmx01.this.select_r = rgb[0];
                    CutomFragmentDmx00Dmx01.this.select_g = rgb[1];
                    CutomFragmentDmx00Dmx01.this.select_b = rgb[2];
                    CutomFragmentDmx00Dmx01.this.textRed_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.red, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_r)}));
                    CutomFragmentDmx00Dmx01.this.textRed_select.setBackgroundColor(Color.rgb(CutomFragmentDmx00Dmx01.this.select_r, 0, 0));
                    CutomFragmentDmx00Dmx01.this.textGreen_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.green, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_g)}));
                    CutomFragmentDmx00Dmx01.this.textGreen_select.setBackgroundColor(Color.rgb(0, CutomFragmentDmx00Dmx01.this.select_g, 0));
                    CutomFragmentDmx00Dmx01.this.tvBlue_select.setText(CutomFragmentDmx00Dmx01.this.getActivity().getString(R.string.blue, new Object[]{Integer.valueOf(CutomFragmentDmx00Dmx01.this.select_b)}));
                    CutomFragmentDmx00Dmx01.this.tvBlue_select.setBackgroundColor(Color.rgb(0, 0, CutomFragmentDmx00Dmx01.this.select_b));
                    CutomFragmentDmx00Dmx01.this.updateRgbText(rgb, true, false, false, 0);
                }
            });
            arrayList.add(findViewWithTag);
        }
        this.buttonSelectColorConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.home.fragment.ble.CutomFragmentDmx00Dmx01.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CutomFragmentDmx00Dmx01.this.currentSelecColorFromPicker != 0 && CutomFragmentDmx00Dmx01.this.currentTab == 1) {
                    float f = 10;
                    ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{f, f, f, f, f, f, f, f}, null, null));
                    shapeDrawable.getPaint().setColor(CutomFragmentDmx00Dmx01.this.currentSelecColorFromPicker);
                    shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
                    CutomFragmentDmx00Dmx01.this.actionView.setColor(CutomFragmentDmx00Dmx01.this.currentSelecColorFromPicker);
                    String str = (String) CutomFragmentDmx00Dmx01.this.actionView.getTag();
                    CutomFragmentDmx00Dmx01.this.sp.edit().putInt(str, CutomFragmentDmx00Dmx01.this.currentSelecColorFromPicker).commit();
                    String substring = str.substring(10);
                    if (substring.equals("1")) {
                        CutomFragmentDmx00Dmx01.this.actionView.setImageResource(R.drawable.dmx_custom_red_1);
                    } else if (substring.equals(ExifInterface.GPS_MEASUREMENT_2D) || substring.equals(ExifInterface.GPS_MEASUREMENT_3D) || substring.equals("4")) {
                        CutomFragmentDmx00Dmx01.this.actionView.setImageResource(R.drawable.dmx_custom_red_2_3_4);
                    } else if (substring.equals("5") || substring.equals("15")) {
                        CutomFragmentDmx00Dmx01.this.actionView.setImageResource(R.drawable.dmx_custom_red_5_15);
                    } else if (substring.equals("6") || substring.equals("16")) {
                        CutomFragmentDmx00Dmx01.this.actionView.setImageResource(R.drawable.dmx_custom_red_6_16);
                    } else if (substring.equals("7") || substring.equals("8") || substring.equals("9") || substring.equals("12") || substring.equals("13") || substring.equals("14") || substring.equals("17") || substring.equals("18") || substring.equals("19") || substring.equals("22") || substring.equals("23") || substring.equals("24")) {
                        CutomFragmentDmx00Dmx01.this.actionView.setImageResource(R.drawable.dmx_custom_red_7_8_9_12_13_14_17_18_19_22_23_24);
                    } else if (substring.equals("10") || substring.equals("20")) {
                        CutomFragmentDmx00Dmx01.this.actionView.setImageResource(R.drawable.dmx_custom_red_10_20);
                    } else if (substring.equals("11") || substring.equals("21")) {
                        CutomFragmentDmx00Dmx01.this.actionView.setImageResource(R.drawable.dmx_custom_red_11_21);
                    } else if (substring.equals("25")) {
                        CutomFragmentDmx00Dmx01.this.actionView.setImageResource(R.drawable.dmx_custom_red_25);
                    }
                    CutomFragmentDmx00Dmx01.this.actionView.setColorFilter(Color.parseColor(String.format("#%06X", Integer.valueOf(16777215 & CutomFragmentDmx00Dmx01.this.currentSelecColorFromPicker))));
                    if (MainActivity_BLE.getSceneBean().equalsIgnoreCase("LEDDMX-00-")) {
                        CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = CutomFragmentDmx00Dmx01.this;
                        cutomFragmentDmx00Dmx01.updateRgbText(Tool.getRGB(cutomFragmentDmx00Dmx01.currentSelecColorFromPicker), false, true, false, Integer.parseInt(substring));
                    }
                }
                CutomFragmentDmx00Dmx01.this.hideColorCover();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$initColorSelecterView$0$com-home-fragment-ble-CutomFragmentDmx00Dmx01  reason: not valid java name */
    public /* synthetic */ void m17x5a893350(int i, boolean z, boolean z2) {
        if (z) {
            this.blackWiteSelectView2.setStartColor(i);
            this.currentSelecColorFromPicker = i;
            int[] rgb = Tool.getRGB(i);
            updateRgbText(rgb, true, false, false, 0);
            this.select_r = rgb[0];
            this.select_g = rgb[1];
            this.select_b = rgb[2];
            this.textRed_select.setText(getActivity().getString(R.string.red, new Object[]{Integer.valueOf(this.select_r)}));
            this.textRed_select.setBackgroundColor(Color.rgb(this.select_r, 0, 0));
            this.textGreen_select.setText(getActivity().getString(R.string.green, new Object[]{Integer.valueOf(this.select_g)}));
            this.textGreen_select.setBackgroundColor(Color.rgb(0, this.select_g, 0));
            this.tvBlue_select.setText(getActivity().getString(R.string.blue, new Object[]{Integer.valueOf(this.select_b)}));
            this.tvBlue_select.setBackgroundColor(Color.rgb(0, 0, this.select_b));
            SharePersistent.saveBrightData(this.mActivity, this.diyViewTag + "bright", this.diyViewTag + "bright", 32);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideColorCover() {
        this.mPopupWindow.dismiss();
    }

    public void updateRgbText(int[] iArr, boolean z, boolean z2, boolean z3, int i) {
        MainActivity_BLE mainActivity_BLE = this.mActivity;
        if (mainActivity_BLE != null) {
            mainActivity_BLE.setDmx00Dmx01Rgb(iArr[0], iArr[1], iArr[2], z, z2, z3, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<MyColor> getSelectColor() {
        ArrayList<MyColor> arrayList = new ArrayList<>();
        if (!ListUtiles.isEmpty(this.colorTextViews)) {
            Iterator<ColorImageView> it = this.colorTextViews.iterator();
            while (it.hasNext()) {
                ColorImageView next = it.next();
                if (next.getColor() != 0) {
                    int[] rgb = Tool.getRGB(next.getColor());
                    arrayList.add(new MyColor(rgb[0], rgb[1], rgb[2]));
                }
            }
        }
        return arrayList;
    }
}
