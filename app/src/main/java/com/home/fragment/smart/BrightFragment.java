package com.home.fragment.smart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import butterknife.BindView;
import com.common.adapter.OnSeekBarChangeListenerAdapter;
import com.common.uitl.SharePersistent;
import com.home.activity.main.MainActivity_BLE;
import com.home.base.LedBleFragment;
import com.home.constant.CommonConstant;
import com.ledlamp.R;

/* loaded from: classes.dex */
public class BrightFragment extends LedBleFragment {
    private static final String TAG = "ModeFragment";
    @BindView(R.id.five)
    LinearLayout five;
    @BindView(R.id.four)
    LinearLayout four;
    private MainActivity_BLE mActivity;
    private View mContentView;
    @BindView(R.id.one)
    LinearLayout one;
    @BindView(R.id.seekBarBlueBrightNess3)
    SeekBar seekBarBlueBrightNess3;
    @BindView(R.id.seekBarCrystalBrightNess3)
    SeekBar seekBarCrystalBrightNess3;
    @BindView(R.id.seekBarGreenBrightNess3)
    SeekBar seekBarGreenBrightNess3;
    @BindView(R.id.seekBarPinkBrightNess3)
    SeekBar seekBarPinkBrightNess3;
    @BindView(R.id.seekBarRedBrightNess3)
    SeekBar seekBarRedBrightNess3;
    @BindView(R.id.seekBarWhiteBrightNess3)
    SeekBar seekBarWhiteBrightNess3;
    @BindView(R.id.six)
    LinearLayout six;
    @BindView(R.id.three)
    LinearLayout three;
    @BindView(R.id.tvBlueBrightNess3)
    TextView tvBlueBrightNess3;
    @BindView(R.id.tvCrystalBrightNess3)
    TextView tvCrystalBrightNess3;
    @BindView(R.id.tvGreenBrightNess3)
    TextView tvGreenBrightNess3;
    @BindView(R.id.tvPinkBrightNess3)
    TextView tvPinkBrightNess3;
    @BindView(R.id.tvRedBrightNess3)
    TextView tvRedBrightNess3;
    @BindView(R.id.tvWhiteBrightNess3)
    TextView tvWhiteBrightNess3;
    @BindView(R.id.two)
    LinearLayout two;

    @Override // com.home.base.LedBleFragment
    public void initEvent() {
    }

    @Override // com.home.base.LedBleFragment
    public void initView() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_brightness_smart, viewGroup, false);
        this.mContentView = inflate;
        return inflate;
    }

    @Override // com.home.base.LedBleFragment
    public void initData() {
        MainActivity_BLE mainActivity_BLE = (MainActivity_BLE) getActivity();
        this.mActivity = mainActivity_BLE;
        setActive(SharePersistent.getSmartModeString(mainActivity_BLE, CommonConstant.SELECT_MODE_SMART_STRING));
    }

    public void setActive(String str) {
        if (str.equalsIgnoreCase(ExifInterface.LONGITUDE_WEST)) {
            this.one.setVisibility(8);
            this.two.setVisibility(8);
            this.three.setVisibility(8);
            this.four.setVisibility(0);
            this.five.setVisibility(8);
            this.six.setVisibility(8);
        } else if (str.equalsIgnoreCase("BW")) {
            this.one.setVisibility(8);
            this.two.setVisibility(8);
            this.three.setVisibility(0);
            this.four.setVisibility(0);
            this.five.setVisibility(8);
            this.six.setVisibility(8);
        } else if (str.equalsIgnoreCase("RGB") || str == "") {
            this.one.setVisibility(0);
            this.two.setVisibility(0);
            this.three.setVisibility(0);
            this.four.setVisibility(8);
            this.five.setVisibility(8);
            this.six.setVisibility(8);
        } else if (str.equalsIgnoreCase("RGBW")) {
            this.one.setVisibility(0);
            this.two.setVisibility(0);
            this.three.setVisibility(0);
            this.five.setVisibility(8);
            this.six.setVisibility(8);
            this.four.setVisibility(0);
        } else if (str.equalsIgnoreCase("RGBWCP")) {
            this.one.setVisibility(0);
            this.two.setVisibility(0);
            this.three.setVisibility(0);
            this.four.setVisibility(0);
            this.five.setVisibility(0);
            this.six.setVisibility(0);
        }
        initViews();
    }

    public void initViews() {
        this.seekBarRedBrightNess3.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.smart.BrightFragment.1
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    BrightFragment.this.tvRedBrightNess3.setText(Integer.toString(i));
                    BrightFragment.this.setSmartBrightness(i, 0);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE = BrightFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE, MainActivity_BLE.sceneBean + BrightFragment.TAG + "red-bright", i);
                    }
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                BrightFragment.this.tvRedBrightNess3.setText(Integer.toString(BrightFragment.this.seekBarRedBrightNess3.getProgress()));
                BrightFragment.this.mActivity.setSmartBrightnessNoInterval(BrightFragment.this.seekBarRedBrightNess3.getProgress(), 0);
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE = this.mActivity;
            int i = SharePersistent.getInt(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + "red-bright");
            if (i >= 0) {
                this.seekBarRedBrightNess3.setProgress(i);
                this.tvRedBrightNess3.setText(String.valueOf(i));
            }
        }
        this.seekBarGreenBrightNess3.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.smart.BrightFragment.2
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                if (z) {
                    BrightFragment.this.tvGreenBrightNess3.setText(Integer.toString(i2));
                    BrightFragment.this.setSmartBrightness(i2, 1);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE2 = BrightFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + BrightFragment.TAG + "green-bright", i2);
                    }
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                BrightFragment.this.tvGreenBrightNess3.setText(Integer.toString(BrightFragment.this.seekBarGreenBrightNess3.getProgress()));
                BrightFragment.this.mActivity.setSmartBrightnessNoInterval(BrightFragment.this.seekBarGreenBrightNess3.getProgress(), 1);
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE2 = this.mActivity;
            int i2 = SharePersistent.getInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + TAG + "green-bright");
            if (i2 >= 0) {
                this.seekBarGreenBrightNess3.setProgress(i2);
                this.tvGreenBrightNess3.setText(String.valueOf(i2));
            }
        }
        this.seekBarBlueBrightNess3.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.smart.BrightFragment.3
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                if (z) {
                    BrightFragment.this.tvBlueBrightNess3.setText(Integer.toString(i3));
                    BrightFragment.this.setSmartBrightness(i3, 2);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE3 = BrightFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + BrightFragment.TAG + "blue-bright", i3);
                    }
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                BrightFragment.this.tvBlueBrightNess3.setText(Integer.toString(BrightFragment.this.seekBarBlueBrightNess3.getProgress()));
                BrightFragment.this.mActivity.setSmartBrightnessNoInterval(BrightFragment.this.seekBarBlueBrightNess3.getProgress(), 2);
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE3 = this.mActivity;
            int i3 = SharePersistent.getInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + TAG + "blue-bright");
            if (i3 >= 0) {
                this.seekBarBlueBrightNess3.setProgress(i3);
                this.tvBlueBrightNess3.setText(String.valueOf(i3));
            }
        }
        this.seekBarWhiteBrightNess3.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.smart.BrightFragment.4
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i4, boolean z) {
                if (z) {
                    BrightFragment.this.tvWhiteBrightNess3.setText(Integer.toString(i4));
                    BrightFragment.this.setSmartBrightness(i4, 3);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE4 = BrightFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + BrightFragment.TAG + "white-bright", i4);
                    }
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                BrightFragment.this.tvWhiteBrightNess3.setText(Integer.toString(BrightFragment.this.seekBarWhiteBrightNess3.getProgress()));
                BrightFragment.this.mActivity.setSmartBrightnessNoInterval(BrightFragment.this.seekBarWhiteBrightNess3.getProgress(), 3);
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE4 = this.mActivity;
            int i4 = SharePersistent.getInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + TAG + "white-bright");
            if (i4 >= 0) {
                this.seekBarWhiteBrightNess3.setProgress(i4);
                this.tvWhiteBrightNess3.setText(String.valueOf(i4));
            }
        }
        this.seekBarCrystalBrightNess3.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.smart.BrightFragment.5
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i5, boolean z) {
                if (z) {
                    BrightFragment.this.tvCrystalBrightNess3.setText(Integer.toString(i5));
                    BrightFragment.this.setSmartBrightness(i5, 4);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE5 = BrightFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + BrightFragment.TAG + "crystal-bright", i5);
                    }
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                BrightFragment.this.tvCrystalBrightNess3.setText(Integer.toString(BrightFragment.this.seekBarCrystalBrightNess3.getProgress()));
                BrightFragment.this.mActivity.setSmartBrightnessNoInterval(BrightFragment.this.seekBarCrystalBrightNess3.getProgress(), 4);
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE5 = this.mActivity;
            int i5 = SharePersistent.getInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + TAG + "crystal-bright");
            if (i5 >= 0) {
                this.seekBarCrystalBrightNess3.setProgress(i5);
                this.tvCrystalBrightNess3.setText(String.valueOf(i5));
            }
        }
        this.seekBarPinkBrightNess3.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.smart.BrightFragment.6
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i6, boolean z) {
                if (z) {
                    BrightFragment.this.tvPinkBrightNess3.setText(Integer.toString(i6));
                    BrightFragment.this.setSmartBrightness(i6, 5);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE6 = BrightFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + BrightFragment.TAG + "pink-bright", i6);
                    }
                }
            }

            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                BrightFragment.this.tvPinkBrightNess3.setText(Integer.toString(BrightFragment.this.seekBarPinkBrightNess3.getProgress()));
                BrightFragment.this.mActivity.setSmartBrightnessNoInterval(BrightFragment.this.seekBarPinkBrightNess3.getProgress(), 5);
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE6 = this.mActivity;
            int i6 = SharePersistent.getInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + TAG + "pink-bright");
            if (i6 >= 0) {
                this.seekBarPinkBrightNess3.setProgress(i6);
                this.tvPinkBrightNess3.setText(String.valueOf(i6));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSmartBrightness(int i, int i2) {
        this.mActivity.setSmartBrightness(i, i2);
    }
}
