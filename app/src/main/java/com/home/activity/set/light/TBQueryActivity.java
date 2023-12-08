package com.home.activity.set.light;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.home.activity.main.MainActivity_BLE;
import com.home.base.LedBleActivity;
import com.ledlamp.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class TBQueryActivity extends LedBleActivity {
    public static final String LOCALHOST = "192.168.2.3";
    private ArrayList<String> dataList;
    private TextView tvBattery;
    private TextView tvTemperature;
    private boolean canSend = true;
    private final int MSG_START_CONNECT = 10000;
    private final int CHECK_NOW = 10001;
    private Handler conectHandler = new Handler() { // from class: com.home.activity.set.light.TBQueryActivity.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 10000) {
                if (i != 10001) {
                    return;
                }
                Context applicationContext = TBQueryActivity.this.getApplicationContext();
                Toast.makeText(applicationContext, "" + TBQueryActivity.this.getString(R.string.currentquery), 0).show();
            } else if (TBQueryActivity.this.dataList == null || TBQueryActivity.this.dataList.size() < 2) {
            } else {
                TBQueryActivity.this.tvTemperature.setText((CharSequence) TBQueryActivity.this.dataList.get(0));
                TBQueryActivity.this.tvBattery.setText((CharSequence) TBQueryActivity.this.dataList.get(1));
                Context applicationContext2 = TBQueryActivity.this.getApplicationContext();
                Toast.makeText(applicationContext2, "查询结果：" + TBQueryActivity.this.dataList, 0).show();
                TBQueryActivity.this.canSend = true;
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.home.base.LedBleActivity, me.imid.swipebacklayout.lib.app.SwipeBackActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
    }

    public void initView() {
        setContentView(R.layout.activity_tb_query);
        this.tvTemperature = (TextView) findViewById(R.id.tvTemperature);
        this.tvBattery = (TextView) findViewById(R.id.tvBattery);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.home.activity.set.light.TBQueryActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.buttonCancell) {
                    TBQueryActivity.this.finish();
                } else if (id != R.id.tvQuery) {
                } else {
                    new Thread(new Runnable() { // from class: com.home.activity.set.light.TBQueryActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TBQueryActivity.this.conectHandler.sendEmptyMessage(10001);
                            if (MainActivity_BLE.getMainActivity() != null) {
                                MainActivity_BLE.getMainActivity().setTBCheck(1);
                                if (TBQueryActivity.this.canSend) {
                                    TBQueryActivity.this.canSend = false;
                                    TBQueryActivity.this.dataList = MainActivity_BLE.getMainActivity().TBCheckRecv();
                                    TBQueryActivity.this.conectHandler.sendEmptyMessage(10000);
                                }
                            }
                        }
                    }).start();
                }
            }
        };
        findViewById(R.id.buttonCancell).setOnClickListener(onClickListener);
        findViewById(R.id.buttonSave).setOnClickListener(onClickListener);
    }
}
