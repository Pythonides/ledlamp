package com.home.activity.set.light;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.media.session.*;

import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.common.uitl.SharePersistent;
import com.common.uitl.StringUtils;
import com.home.activity.main.MainActivity_BLE;
import com.home.base.LedBleActivity;
import com.home.constant.Constant;
import com.ledlamp.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class CodeActivity_WIFI extends LedBleActivity implements View.OnClickListener {
    ImageView backwifi;
    Button btn_pix_set_wifi;
    Button button_chipselect_wifi;
    private ArrayList<String> dataList;
    TextView tvQuerywifi;
    Button tv_pix_num_wifi;
    TextView tvcodewifi;
    int bannerPix = 0;
    private final int MSG_START_CONNECT = 1000;
    private final int CHECK_NOW = 2000;
    private boolean canSend = true;
    private final Handler conectHandler = new Handler() { // from class: com.home.activity.set.light.CodeActivity_WIFI.2
        @SuppressLint("HandlerLeak")
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 1000) {
                if (i != 2000) {
                    return;
                }
                Context applicationContext = CodeActivity_WIFI.this.getApplicationContext();
                Toast.makeText(applicationContext, "" + CodeActivity_WIFI.this.getString(R.string.currentquery), Toast.LENGTH_SHORT).show();
            } else if (CodeActivity_WIFI.this.dataList != null) {
                if (2 == CodeActivity_WIFI.this.dataList.size()) {
                    TextView textView = CodeActivity_WIFI.this.tvcodewifi;
                    textView.setText(CodeActivity_WIFI.this.getResources().getString(R.string.light_code) + (Integer.parseInt(((String) CodeActivity_WIFI.this.dataList.get(1)).trim()) + 256));
                    CodeActivity_WIFI.this.canSend = true;
                    return;
                }
                TextView textView2 = CodeActivity_WIFI.this.tvcodewifi;
                textView2.setText(CodeActivity_WIFI.this.getResources().getString(R.string.light_code) + ((String) CodeActivity_WIFI.this.dataList.get(0)));
                CodeActivity_WIFI.this.canSend = true;
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.home.base.LedBleActivity, me.imid.swipebacklayout.lib.app.SwipeBackActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_code_wifi);
        Button button = (Button) findViewById(R.id.btn_pix_set_wifi);
        this.btn_pix_set_wifi = button;
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.tv_pix_num_wifi);
        this.tv_pix_num_wifi = button2;
        button2.setOnClickListener(this);
        Button button3 = (Button) findViewById(R.id.button_chipselect_wifi);
        this.button_chipselect_wifi = button3;
        button3.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.tvQuerywifi);
        this.tvQuerywifi = textView;
        textView.setOnClickListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.backwifi);
        this.backwifi = imageView;
        imageView.setOnClickListener(this);
        TextView textView2 = (TextView) findViewById(R.id.tvcodewifi);
        this.tvcodewifi = textView2;
        textView2.setText(getResources().getString(R.string.light_code) + ":0");
        String configData = SharePersistent.getConfigData(this, Constant.PIX_VALUE_LIGHT, Constant.PIX_VALUE_KEY_LIGHT);
        if (!configData.equals("")) {
            String[] split = configData.split(",");
            this.tv_pix_num_wifi.setText(split[0]);
            this.bannerPix = Integer.parseInt(split[0]);
            return;
        }
        this.tv_pix_num_wifi.setText("200");
        this.bannerPix = 200;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backwifi /* 2131296343 */:
                finish();
                return;
            case R.id.btn_pix_set_wifi /* 2131296404 */:
            case R.id.tv_pix_num_wifi /* 2131297815 */:
                setPixValue();
                return;
            case R.id.button_chipselect_wifi /* 2131296436 */:
                if (this.bannerPix != 0) {
                    MainActivity_BLE mainActivity = MainActivity_BLE.getMainActivity();
                    int i = this.bannerPix;
                    mainActivity.codeCheck((byte) (i >> 8), (byte) i);
                    return;
                }
                return;
            case R.id.tvQuerywifi /* 2131297672 */:
                new Thread(new Runnable() { // from class: com.home.activity.set.light.CodeActivity_WIFI.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CodeActivity_WIFI.this.conectHandler.sendEmptyMessage(2000);
                        if (MainActivity_BLE.getMainActivity() != null) {
                            MainActivity_BLE.getMainActivity().setlight(2);
                            if (CodeActivity_WIFI.this.canSend) {
                                CodeActivity_WIFI.this.canSend = false;
                                CodeActivity_WIFI.this.dataList = MainActivity_BLE.getMainActivity().TBCheckRecv();
                                CodeActivity_WIFI.this.conectHandler.sendEmptyMessage(1000);
                            }
                        }
                    }
                }).start();
                return;
            default:
        }
    }

    private void setPixValue() {
        final EditText editText = new EditText(this);
        editText.setHint("0~1024");
        new AlertDialog.Builder(this).setTitle(R.string.btn_pix_number).setIcon(R.drawable.wifi_hotspot).setView(editText).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.home.activity.set.light.CodeActivity_WIFI.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(CodeActivity_WIFI.this.getApplicationContext(), (int) R.string.valuetip, Toast.LENGTH_SHORT).show();
                    return;
                }
                String obj = editText.getText().toString();
                if (!StringUtils.isNumeric(obj)) {
                    Toast.makeText(CodeActivity_WIFI.this.getApplicationContext(), (int) R.string.key_in_numbers, Toast.LENGTH_SHORT).show();
                    return;
                }
                long parseLong = Long.parseLong(obj.trim());
                if (parseLong <= 0 || parseLong >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                    Toast.makeText(CodeActivity_WIFI.this, (int) R.string.please_enter_again, Toast.LENGTH_SHORT).show();
                    return;
                }
                String valueOf = String.valueOf(parseLong);
                if (StringUtils.isEmpty(obj)) {
                    return;
                }
                CodeActivity_WIFI.this.bannerPix = Integer.parseInt(valueOf.trim());
                CodeActivity_WIFI.this.tv_pix_num_wifi.setText(valueOf);
                SharePersistent.saveConfigData(CodeActivity_WIFI.this, Constant.PIX_VALUE_LIGHT, Constant.PIX_VALUE_KEY_LIGHT, valueOf, 0);
            }
        }).setNegativeButton(R.string.cancell_dialog, new DialogInterface.OnClickListener() { // from class: com.home.activity.set.light.CodeActivity_WIFI.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
}
