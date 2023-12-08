package com.home.activity.set.light;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.common.uitl.SharePersistent;
import com.home.activity.main.MainActivity_BLE;
import com.home.base.LedBleActivity;
import com.home.bean.SceneBean;
import com.home.view.wheel.OnWheelChangedListener;
import com.home.view.wheel.WheelModelAdapter;
import com.home.view.wheel.WheelView;
import com.ledlamp.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ChannelSetActivity extends LedBleActivity {
    public static SceneBean sceneBean;
    private ArrayList<Integer> arrayList;
    private ArrayList<Integer> arrayTempList;
    private WheelView listViewCrystal3;
    private WheelView listViewGreen3;
    private WheelView listViewLightblue3;
    private WheelView listViewPink3;
    private WheelView listViewRed3;
    private WheelView listViewWhite3;
    private int tempValue;
    private WheelModelAdapter wheelAdapterModel;
    private int rValue = 255;
    private int gValue = 255;
    private int bValue = 255;
    private int wValue = 255;
    private int yValue = 255;
    private int pValue = 255;
    private String modelText = "";
    private String RGB = "RGBDataBase";
    private String RGBW = "RGBWDataBase";
    private String RGBWC = "RGBWC";
    private String RGBWCP = "RGBWCPDataBase";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.home.base.LedBleActivity, me.imid.swipebacklayout.lib.app.SwipeBackActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
    }

    public void initView() {
        setContentView(R.layout.activity_channel_set);
        this.arrayList = new ArrayList<>();
        this.arrayTempList = new ArrayList<>();
        this.listViewRed3 = (WheelView) findViewById(R.id.listViewModel31);
        this.listViewGreen3 = (WheelView) findViewById(R.id.listViewModel32);
        this.listViewLightblue3 = (WheelView) findViewById(R.id.listViewModel33);
        this.listViewWhite3 = (WheelView) findViewById(R.id.listViewModel34);
        this.listViewCrystal3 = (WheelView) findViewById(R.id.listViewModel35);
        this.listViewPink3 = (WheelView) findViewById(R.id.listViewModel36);
        String[] strArr = new String[256];
        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                strArr[i] = "NC";
            } else {
                strArr[i] = "" + i;
            }
        }
        final String[] strArr2 = new String[256];
        for (int i2 = 0; i2 < 256; i2++) {
            if (i2 == 0) {
                strArr2[i2] = "255";
            } else {
                strArr2[i2] = "" + i2;
            }
        }
        WheelModelAdapter wheelModelAdapter = new WheelModelAdapter(this, strArr);
        this.wheelAdapterModel = wheelModelAdapter;
        this.listViewRed3.setViewAdapter(wheelModelAdapter);
        this.listViewRed3.addChangingListener(new OnWheelChangedListener() { // from class: com.home.activity.set.light.ChannelSetActivity.1
            @Override // com.home.view.wheel.OnWheelChangedListener
            public void onChanged(WheelView wheelView, int i3, int i4) {
                ChannelSetActivity.this.rValue = Integer.parseInt(strArr2[i4].trim());
                ChannelSetActivity.this.arrayList.add(Integer.valueOf(ChannelSetActivity.this.rValue));
                if (ChannelSetActivity.this.rValue == ChannelSetActivity.this.tempValue) {
                    Toast.makeText(ChannelSetActivity.this.getApplication(), ChannelSetActivity.this.getString(R.string.Values_can_be_the_same), 0).show();
                }
                ChannelSetActivity channelSetActivity = ChannelSetActivity.this;
                channelSetActivity.tempValue = channelSetActivity.rValue;
            }
        });
        this.listViewGreen3.setViewAdapter(this.wheelAdapterModel);
        this.listViewGreen3.addChangingListener(new OnWheelChangedListener() { // from class: com.home.activity.set.light.ChannelSetActivity.2
            @Override // com.home.view.wheel.OnWheelChangedListener
            public void onChanged(WheelView wheelView, int i3, int i4) {
                ChannelSetActivity.this.gValue = Integer.parseInt(strArr2[i4].trim());
                ChannelSetActivity.this.arrayList.add(Integer.valueOf(ChannelSetActivity.this.rValue));
                if (ChannelSetActivity.this.gValue == ChannelSetActivity.this.tempValue) {
                    Toast.makeText(ChannelSetActivity.this.getApplication(), ChannelSetActivity.this.getString(R.string.Values_can_be_the_same), 0).show();
                }
                ChannelSetActivity channelSetActivity = ChannelSetActivity.this;
                channelSetActivity.tempValue = channelSetActivity.gValue;
            }
        });
        this.listViewLightblue3.setViewAdapter(this.wheelAdapterModel);
        this.listViewLightblue3.addChangingListener(new OnWheelChangedListener() { // from class: com.home.activity.set.light.ChannelSetActivity.3
            @Override // com.home.view.wheel.OnWheelChangedListener
            public void onChanged(WheelView wheelView, int i3, int i4) {
                ChannelSetActivity.this.bValue = Integer.parseInt(strArr2[i4].trim());
                ChannelSetActivity.this.arrayList.add(Integer.valueOf(ChannelSetActivity.this.rValue));
                if (ChannelSetActivity.this.bValue == ChannelSetActivity.this.tempValue) {
                    Toast.makeText(ChannelSetActivity.this.getApplication(), ChannelSetActivity.this.getString(R.string.Values_can_be_the_same), 0).show();
                }
                ChannelSetActivity channelSetActivity = ChannelSetActivity.this;
                channelSetActivity.tempValue = channelSetActivity.bValue;
            }
        });
        this.listViewWhite3.setViewAdapter(this.wheelAdapterModel);
        this.listViewWhite3.addChangingListener(new OnWheelChangedListener() { // from class: com.home.activity.set.light.ChannelSetActivity.4
            @Override // com.home.view.wheel.OnWheelChangedListener
            public void onChanged(WheelView wheelView, int i3, int i4) {
                ChannelSetActivity.this.wValue = Integer.parseInt(strArr2[i4].trim());
                ChannelSetActivity.this.arrayList.add(Integer.valueOf(ChannelSetActivity.this.wValue));
                if (ChannelSetActivity.this.wValue == ChannelSetActivity.this.tempValue) {
                    Toast.makeText(ChannelSetActivity.this.getApplication(), ChannelSetActivity.this.getString(R.string.Values_can_be_the_same), 0).show();
                }
                ChannelSetActivity channelSetActivity = ChannelSetActivity.this;
                channelSetActivity.tempValue = channelSetActivity.wValue;
            }
        });
        this.listViewPink3.setViewAdapter(this.wheelAdapterModel);
        this.listViewPink3.addChangingListener(new OnWheelChangedListener() { // from class: com.home.activity.set.light.ChannelSetActivity.5
            @Override // com.home.view.wheel.OnWheelChangedListener
            public void onChanged(WheelView wheelView, int i3, int i4) {
                ChannelSetActivity.this.pValue = Integer.parseInt(strArr2[i4].trim());
                ChannelSetActivity.this.arrayList.add(Integer.valueOf(ChannelSetActivity.this.pValue));
                if (ChannelSetActivity.this.wValue == ChannelSetActivity.this.tempValue) {
                    Toast.makeText(ChannelSetActivity.this.getApplication(), ChannelSetActivity.this.getString(R.string.Values_can_be_the_same), 0).show();
                }
                ChannelSetActivity channelSetActivity = ChannelSetActivity.this;
                channelSetActivity.tempValue = channelSetActivity.pValue;
            }
        });
        this.listViewCrystal3.setViewAdapter(this.wheelAdapterModel);
        this.listViewCrystal3.addChangingListener(new OnWheelChangedListener() { // from class: com.home.activity.set.light.ChannelSetActivity.6
            @Override // com.home.view.wheel.OnWheelChangedListener
            public void onChanged(WheelView wheelView, int i3, int i4) {
                ChannelSetActivity.this.yValue = Integer.parseInt(strArr2[i4].trim());
                ChannelSetActivity.this.arrayList.add(Integer.valueOf(ChannelSetActivity.this.yValue));
                if (ChannelSetActivity.this.wValue == ChannelSetActivity.this.tempValue) {
                    Toast.makeText(ChannelSetActivity.this.getApplication(), ChannelSetActivity.this.getString(R.string.Values_can_be_the_same), 0).show();
                }
                ChannelSetActivity channelSetActivity = ChannelSetActivity.this;
                channelSetActivity.tempValue = channelSetActivity.yValue;
            }
        });
        if (MainActivity_BLE.getMainActivity() != null) {
            Context applicationContext = getApplicationContext();
            int i3 = SharePersistent.getInt(applicationContext, MainActivity_BLE.getSceneBean() + "CH_R_STAGE");
            if (i3 > 0 && i3 != 255) {
                this.listViewRed3.setCurrentItem(i3);
            }
            Context applicationContext2 = getApplicationContext();
            int i4 = SharePersistent.getInt(applicationContext2, MainActivity_BLE.getSceneBean() + "CH_G_STAGE");
            if (i4 > 0 && i4 != 255) {
                this.listViewGreen3.setCurrentItem(i4);
            }
            Context applicationContext3 = getApplicationContext();
            int i5 = SharePersistent.getInt(applicationContext3, MainActivity_BLE.getSceneBean() + "CH_B_STAGE");
            if (i5 > 0 && i5 != 255) {
                this.listViewLightblue3.setCurrentItem(i5);
            }
            Context applicationContext4 = getApplicationContext();
            int i6 = SharePersistent.getInt(applicationContext4, MainActivity_BLE.getSceneBean() + "CH_W_STAGE");
            if (i6 > 0 && i6 != 255) {
                this.listViewWhite3.setCurrentItem(i6);
            }
            Context applicationContext5 = getApplicationContext();
            int i7 = SharePersistent.getInt(applicationContext5, MainActivity_BLE.getSceneBean() + "CH_P_STAGE");
            if (i7 > 0 && i7 != 255) {
                this.listViewPink3.setCurrentItem(i7);
            }
            Context applicationContext6 = getApplicationContext();
            int i8 = SharePersistent.getInt(applicationContext6, MainActivity_BLE.getSceneBean() + "CH_Y_STAGE");
            if (i8 > 0 && i8 != 255) {
                this.listViewCrystal3.setCurrentItem(i8);
            }
        }
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.home.activity.set.light.ChannelSetActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.buttonCancell) {
                    ChannelSetActivity.this.finish();
                } else if (id != R.id.buttonSave) {
                } else {
                    if (MainActivity_BLE.getMainActivity() != null) {
                        MainActivity_BLE.getMainActivity().SetCHN(ChannelSetActivity.this.rValue, ChannelSetActivity.this.gValue, ChannelSetActivity.this.bValue, ChannelSetActivity.this.wValue, ChannelSetActivity.this.yValue, ChannelSetActivity.this.pValue);
                        MainActivity_BLE mainActivity = MainActivity_BLE.getMainActivity();
                        SharePersistent.savePerference(mainActivity, MainActivity_BLE.getSceneBean() + "CH_R_STAGE", ChannelSetActivity.this.rValue);
                        MainActivity_BLE mainActivity2 = MainActivity_BLE.getMainActivity();
                        SharePersistent.savePerference(mainActivity2, MainActivity_BLE.getSceneBean() + "CH_G_STAGE", ChannelSetActivity.this.gValue);
                        MainActivity_BLE mainActivity3 = MainActivity_BLE.getMainActivity();
                        SharePersistent.savePerference(mainActivity3, MainActivity_BLE.getSceneBean() + "CH_B_STAGE", ChannelSetActivity.this.bValue);
                        MainActivity_BLE mainActivity4 = MainActivity_BLE.getMainActivity();
                        SharePersistent.savePerference(mainActivity4, MainActivity_BLE.getSceneBean() + "CH_W_STAGE", ChannelSetActivity.this.wValue);
                        MainActivity_BLE mainActivity5 = MainActivity_BLE.getMainActivity();
                        SharePersistent.savePerference(mainActivity5, MainActivity_BLE.getSceneBean() + "CH_P_STAGE", ChannelSetActivity.this.pValue);
                        MainActivity_BLE mainActivity6 = MainActivity_BLE.getMainActivity();
                        SharePersistent.savePerference(mainActivity6, MainActivity_BLE.getSceneBean() + "CH_Y_STAGE", ChannelSetActivity.this.yValue);
                    }
                    ChannelSetActivity.this.finish();
                }
            }
        };
        findViewById(R.id.buttonCancell).setOnClickListener(onClickListener);
        findViewById(R.id.buttonSave).setOnClickListener(onClickListener);
    }
}
