package com.home.fragment.ble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import com.common.adapter.OnSeekBarChangeListenerAdapter;
import com.common.uitl.SharePersistent;
import com.home.activity.main.MainActivity_BLE;
import com.home.adapter.AisleAdapter;
import com.home.base.LedBleFragment;
import com.home.bean.AisleBean;
import com.home.bean.Mp3;
import com.home.view.AisleView;
import com.home.view.custom.IndicatorSeekBar;
import com.ledlamp.R;
import java.util.ArrayList;
import java.util.HashSet;

/* loaded from: classes.dex */
public class AisleFragment extends LedBleFragment {
    private static final String TAG = "AisleFragment";
    private static ArrayList<AisleBean> aisleBeans = new ArrayList<>();
    private AisleAdapter aisleAdapter;
    private ArrayList<AisleView> arrayListAisleViews = new ArrayList<>();
    @BindView(R.id.indicator_seek_bar)
    IndicatorSeekBar indicatorSeekBar;
    @BindView(R.id.linearLayoutTab)
    LinearLayout linearLayoutTab;
    @BindView(R.id.ll_aisle_a)
    LinearLayout ll_aisle_a;
    @BindView(R.id.lvAisle)
    ListView lvAisle;
    private MainActivity_BLE mActivity;
    private View mContentView;
    @BindView(R.id.seekBarBlueBrightNess)
    SeekBar seekBarBlueBrightNess;
    @BindView(R.id.seekBarCH1BrightNess)
    SeekBar seekBarCH1BrightNess;
    @BindView(R.id.seekBarCH2BrightNess)
    SeekBar seekBarCH2BrightNess;
    @BindView(R.id.seekBarCH3BrightNess)
    SeekBar seekBarCH3BrightNess;
    @BindView(R.id.seekBarCrystalBrightNess)
    SeekBar seekBarCrystalBrightNess;
    @BindView(R.id.seekBarGreenBrightNess)
    SeekBar seekBarGreenBrightNess;
    @BindView(R.id.seekBarPinkBrightNess)
    SeekBar seekBarPinkBrightNess;
    @BindView(R.id.seekBarRedBrightNess)
    SeekBar seekBarRedBrightNess;
    @BindView(R.id.seekBarWhiteBrightNess)
    SeekBar seekBarWhiteBrightNess;
    @BindView(R.id.seekBarYellowBrightNess)
    SeekBar seekBarYellowBrightNess;
    @BindView(R.id.tvBrightness1)
    TextView tvBrightness1;
    @BindView(R.id.tvBrightness10)
    TextView tvBrightness10;
    @BindView(R.id.tvBrightness2)
    TextView tvBrightness2;
    @BindView(R.id.tvBrightness3)
    TextView tvBrightness3;
    @BindView(R.id.tvBrightness4)
    TextView tvBrightness4;
    @BindView(R.id.tvBrightness5)
    TextView tvBrightness5;
    @BindView(R.id.tvBrightness6)
    TextView tvBrightness6;
    @BindView(R.id.tvBrightness7)
    TextView tvBrightness7;
    @BindView(R.id.tvBrightness8)
    TextView tvBrightness8;
    @BindView(R.id.tvBrightness9)
    TextView tvBrightness9;

    @Override // com.home.base.LedBleFragment
    public void initEvent() {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_brightness_wifi, viewGroup, false);
        this.mContentView = inflate;
        return inflate;
    }

    @Override // com.home.base.LedBleFragment
    public void initData() {
        if (MainActivity_BLE.getMainActivity() != null) {
            this.mActivity = MainActivity_BLE.getMainActivity();
        }
    }

    @Override // com.home.base.LedBleFragment
    public void initView() {
        aisleBeans.clear();
        for (int i = 1; i <= 255; i++) {
            AisleBean aisleBean = new AisleBean();
            aisleBean.setTitle("CH" + i);
            aisleBeans.add(aisleBean);
        }
        AisleAdapter aisleAdapter = new AisleAdapter(getContext(), aisleBeans);
        this.aisleAdapter = aisleAdapter;
        aisleAdapter.setOnSelectListener(new AisleAdapter.OnSelectListener() { // from class: com.home.fragment.ble.AisleFragment.1
            @Override // com.home.adapter.AisleAdapter.OnSelectListener
            public void onSelect(int i2, Mp3 mp3, HashSet<Mp3> hashSet, boolean z, BaseAdapter baseAdapter) {
            }
        });
        this.lvAisle.setAdapter((ListAdapter) this.aisleAdapter);
        this.lvAisle.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.home.fragment.ble.AisleFragment.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
            }
        });
        this.indicatorSeekBar.setOnSeekBarChangeListener(new IndicatorSeekBar.OnIndicatorSeekBarChangeListener() { // from class: com.home.fragment.ble.AisleFragment.3
            @Override // com.home.view.custom.IndicatorSeekBar.OnIndicatorSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, float f) {
            }

            @Override // com.home.view.custom.IndicatorSeekBar.OnIndicatorSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // com.home.view.custom.IndicatorSeekBar.OnIndicatorSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.seekBarRedBrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.4
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness1.setText(Integer.toString(i2));
                    AisleFragment.this.setSmartBrightness(i2, 1);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarRedBrightNess", i2);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE = this.mActivity;
            int i2 = SharePersistent.getInt(mainActivity_BLE, MainActivity_BLE.sceneBean + TAG + "seekBarRedBrightNess");
            if (i2 >= 0) {
                this.seekBarRedBrightNess.setProgress(i2);
                this.tvBrightness1.setText(String.valueOf(i2));
            }
        }
        this.seekBarGreenBrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.5
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness2.setText(Integer.toString(i3));
                    AisleFragment.this.setSmartBrightness(i3, 2);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE2 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarGreenBrightNess", i3);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE2 = this.mActivity;
            int i3 = SharePersistent.getInt(mainActivity_BLE2, MainActivity_BLE.sceneBean + TAG + "seekBarGreenBrightNess");
            if (i3 >= 0) {
                this.seekBarGreenBrightNess.setProgress(i3);
                this.tvBrightness2.setText(String.valueOf(i3));
            }
        }
        this.seekBarBlueBrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.6
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i4, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness3.setText(Integer.toString(i4));
                    AisleFragment.this.setSmartBrightness(i4, 3);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE3 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarBlueBrightNess", i4);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE3 = this.mActivity;
            int i4 = SharePersistent.getInt(mainActivity_BLE3, MainActivity_BLE.sceneBean + TAG + "seekBarBlueBrightNess");
            if (i4 >= 0) {
                this.seekBarBlueBrightNess.setProgress(i4);
                this.tvBrightness3.setText(String.valueOf(i4));
            }
        }
        this.seekBarWhiteBrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.7
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i5, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness4.setText(Integer.toString(i5));
                    AisleFragment.this.setSmartBrightness(i5, 4);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE4 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarWhiteBrightNess", i5);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE4 = this.mActivity;
            int i5 = SharePersistent.getInt(mainActivity_BLE4, MainActivity_BLE.sceneBean + TAG + "seekBarWhiteBrightNess");
            if (i5 >= 0) {
                this.seekBarWhiteBrightNess.setProgress(i5);
                this.tvBrightness4.setText(String.valueOf(i5));
            }
        }
        this.seekBarYellowBrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.8
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i6, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness5.setText(Integer.toString(i6));
                    AisleFragment.this.setSmartBrightness(i6, 5);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE5 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarYellowBrightNess", i6);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE5 = this.mActivity;
            int i6 = SharePersistent.getInt(mainActivity_BLE5, MainActivity_BLE.sceneBean + TAG + "seekBarYellowBrightNess");
            if (i6 >= 0) {
                this.seekBarYellowBrightNess.setProgress(i6);
                this.tvBrightness5.setText(String.valueOf(i6));
            }
        }
        this.seekBarPinkBrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.9
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i7, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness6.setText(Integer.toString(i7));
                    AisleFragment.this.setSmartBrightness(i7, 6);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE6 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarPinkBrightNess", i7);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE6 = this.mActivity;
            int i7 = SharePersistent.getInt(mainActivity_BLE6, MainActivity_BLE.sceneBean + TAG + "seekBarPinkBrightNess");
            if (i7 >= 0) {
                this.seekBarPinkBrightNess.setProgress(i7);
                this.tvBrightness6.setText(String.valueOf(i7));
            }
        }
        this.seekBarCrystalBrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.10
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i8, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness7.setText(Integer.toString(i8));
                    AisleFragment.this.setSmartBrightness(i8, 7);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE7 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE7, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarCrystalBrightNess", i8);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE7 = this.mActivity;
            int i8 = SharePersistent.getInt(mainActivity_BLE7, MainActivity_BLE.sceneBean + TAG + "seekBarCrystalBrightNess");
            if (i8 >= 0) {
                this.seekBarCrystalBrightNess.setProgress(i8);
                this.tvBrightness7.setText(String.valueOf(i8));
            }
        }
        this.seekBarCH1BrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.11
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i9, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness8.setText(Integer.toString(i9));
                    AisleFragment.this.setSmartBrightness(i9, 8);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE8 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE8, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarCH1BrightNess", i9);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE8 = this.mActivity;
            int i9 = SharePersistent.getInt(mainActivity_BLE8, MainActivity_BLE.sceneBean + TAG + "seekBarCH1BrightNess");
            if (i9 >= 0) {
                this.seekBarCH1BrightNess.setProgress(i9);
                this.tvBrightness8.setText(String.valueOf(i9));
            }
        }
        this.seekBarCH2BrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.12
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i10, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness9.setText(Integer.toString(i10));
                    AisleFragment.this.setSmartBrightness(i10, 9);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE9 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE9, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarCH2BrightNess", i10);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE9 = this.mActivity;
            int i10 = SharePersistent.getInt(mainActivity_BLE9, MainActivity_BLE.sceneBean + TAG + "seekBarCH2BrightNess");
            if (i10 >= 0) {
                this.seekBarCH2BrightNess.setProgress(i10);
                this.tvBrightness9.setText(String.valueOf(i10));
            }
        }
        this.seekBarCH3BrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.fragment.ble.AisleFragment.13
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i11, boolean z) {
                if (z) {
                    AisleFragment.this.tvBrightness10.setText(Integer.toString(i11));
                    AisleFragment.this.setSmartBrightness(i11, 10);
                    if (MainActivity_BLE.sceneBean != null) {
                        MainActivity_BLE mainActivity_BLE10 = AisleFragment.this.mActivity;
                        SharePersistent.saveInt(mainActivity_BLE10, MainActivity_BLE.sceneBean + AisleFragment.TAG + "seekBarCH3BrightNess", i11);
                    }
                }
            }
        });
        if (MainActivity_BLE.sceneBean != null) {
            MainActivity_BLE mainActivity_BLE10 = this.mActivity;
            int i11 = SharePersistent.getInt(mainActivity_BLE10, MainActivity_BLE.sceneBean + TAG + "seekBarCH3BrightNess");
            if (i11 >= 0) {
                this.seekBarCH3BrightNess.setProgress(i11);
                this.tvBrightness10.setText(String.valueOf(i11));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSmartBrightness(int i, int i2) {
        if (MainActivity_BLE.getMainActivity() != null) {
            this.mActivity.setSmartBrightness(i, i2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        AisleAdapter aisleAdapter = this.aisleAdapter;
        if (aisleAdapter != null) {
            aisleAdapter.notifyDataSetChanged();
        }
    }
}
