package com.home.activity.other;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.core.content.ContextCompat;
import com.common.net.NetResult;
import com.common.task.BaseTask;
import com.common.task.NetCallBack;
import com.common.uitl.ListUtiles;
import com.home.adapter.Mp3Adapter;
import com.home.base.LedBleActivity;
import com.home.base.LedBleApplication;
import com.home.bean.Mp3;
import com.ledlamp.R;
import com.luck.picture.lib.tools.PictureFileUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import wseemann.media.FFmpegMediaMetadataRetriever;

/* loaded from: classes.dex */
public class MusicLibActivity extends LedBleActivity {
    private static ArrayList<Mp3> mp3s = new ArrayList<>();
    private final int LOCATION_CODE = 120;
    private final int READ_MEDIA = 120;
    private Button backButton;
    private BaseTask baseTask;
    private Button buttonAdd;
    private Button buttonAddAll;
    private ListView listViewMuiscs;
    private Context mContext;
    private Loader.ForceLoadContentObserver mObserver;
    private Mp3Adapter mp3Adapter;
    private Handler startHandler;
    private Runnable startRunnable;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.home.base.LedBleActivity, me.imid.swipebacklayout.lib.app.SwipeBackActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        initView();
    }

    public void initView() {
        setContentView(R.layout.activity_music_lib);
        this.listViewMuiscs = (ListView) findViewById(R.id.listViewMuiscsList);
        if (ListUtiles.isEmpty(mp3s)) {
            if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <= 32) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                    requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 120);
                } else {
                    refresh();
                }
            } else if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_MEDIA_AUDIO") != 0 || ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_MEDIA_IMAGES") != 0 || ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_MEDIA_VIDEO") != 0) {
                    requestPermissions(new String[]{"android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"}, 120);
                } else {
                    refresh();
                }
            } else {
                refresh();
            }
        } else {
            buildmp3Adapter(mp3s, true);
        }
        Button button = (Button) findViewById(R.id.buttonConfirm);
        this.buttonAdd = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.other.MusicLibActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MusicLibActivity.this.mp3Adapter == null || MusicLibActivity.this.mp3Adapter.getSelectSet().isEmpty()) {
                    return;
                }
                MusicLibActivity musicLibActivity = MusicLibActivity.this;
                musicLibActivity.goBackWithData(musicLibActivity.mp3Adapter);
            }
        });
        Button button2 = (Button) findViewById(R.id.buttonAddAll);
        this.buttonAddAll = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.other.MusicLibActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MusicLibActivity.this.mp3Adapter != null) {
                    MusicLibActivity.this.mp3Adapter.selectAll();
                    MusicLibActivity musicLibActivity = MusicLibActivity.this;
                    musicLibActivity.goBackWithData(musicLibActivity.mp3Adapter);
                }
            }
        });
        Button button3 = (Button) findViewById(R.id.buttonBack);
        this.backButton = button3;
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.home.activity.other.MusicLibActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MusicLibActivity.this.finish();
            }
        });
    }

    public void refresh() {
        if (this.startHandler == null) {
            this.startHandler = new Handler();
        }
        if (this.startRunnable == null) {
            this.startRunnable = new Runnable() { // from class: com.home.activity.other.MusicLibActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    MusicLibActivity.this.startHandler.postDelayed(MusicLibActivity.this.startRunnable, 100L);
                    if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT <= 32) {
                        if (ContextCompat.checkSelfPermission(MusicLibActivity.this.getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                            if (ListUtiles.isEmpty(MusicLibActivity.mp3s)) {
                                MusicLibActivity.this.scanMp3();
                            } else {
                                MusicLibActivity.this.buildmp3Adapter(MusicLibActivity.mp3s, true);
                            }
                            MusicLibActivity.this.startHandler.removeCallbacks(MusicLibActivity.this.startRunnable);
                        }
                    } else if (Build.VERSION.SDK_INT >= 33) {
                        if (ContextCompat.checkSelfPermission(MusicLibActivity.this.getApplicationContext(), "android.permission.READ_MEDIA_AUDIO") == 0 || ContextCompat.checkSelfPermission(MusicLibActivity.this.getApplicationContext(), "android.permission.READ_MEDIA_IMAGES") == 0 || ContextCompat.checkSelfPermission(MusicLibActivity.this.getApplicationContext(), "android.permission.READ_MEDIA_VIDEO") == 0) {
                            if (ListUtiles.isEmpty(MusicLibActivity.mp3s)) {
                                MusicLibActivity.this.scanMp3();
                            } else {
                                MusicLibActivity.this.buildmp3Adapter(MusicLibActivity.mp3s, true);
                            }
                            MusicLibActivity.this.startHandler.removeCallbacks(MusicLibActivity.this.startRunnable);
                        }
                    }
                }
            };
        }
        this.startHandler.postDelayed(this.startRunnable, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goBackWithData(Mp3Adapter mp3Adapter) {
        LedBleApplication.getApp().setMp3s(new ArrayList<>(mp3Adapter.getSelectSet()));
        putDataBack(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Mp3Adapter buildmp3Adapter(ArrayList<Mp3> arrayList, boolean z) {
        if (ListUtiles.isEmpty(arrayList)) {
            return null;
        }
        if (!z) {
            mp3s.clear();
            mp3s.addAll(arrayList);
        }
        this.mp3Adapter = new Mp3Adapter(this, mp3s);
        if (!ListUtiles.isEmpty(LedBleApplication.getApp().getMp3s())) {
            this.mp3Adapter.setSelectSet(new HashSet<>(LedBleApplication.getApp().getMp3s()));
        }
        this.mp3Adapter.setOnSelectListener(new Mp3Adapter.OnSelectListener() { // from class: com.home.activity.other.MusicLibActivity.5
            @Override // com.home.adapter.Mp3Adapter.OnSelectListener
            public void onSelect(int i, Mp3 mp3, HashSet<Mp3> hashSet, boolean z2, BaseAdapter baseAdapter) {
                if (hashSet.contains(mp3)) {
                    hashSet.remove(mp3);
                } else {
                    hashSet.add(mp3);
                }
                baseAdapter.notifyDataSetChanged();
            }
        });
        this.listViewMuiscs.setAdapter((ListAdapter) this.mp3Adapter);
        this.listViewMuiscs.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.home.activity.other.MusicLibActivity.6
            /* JADX WARN: Type inference failed for: r1v2, types: [android.widget.Adapter] */
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                try {
                    MusicLibActivity.this.putDataBack((Mp3) adapterView.getAdapter().getItem(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return this.mp3Adapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putDataBack(Mp3 mp3) {
        Intent intent = new Intent();
        intent.putExtra("mp3", mp3);
        setResult(-1, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanMp3() {
        BaseTask baseTask = this.baseTask;
        if (baseTask != null && !baseTask.cancel(true)) {
            this.baseTask.cancel(true);
        }
        BaseTask baseTask2 = new BaseTask(new NetCallBack() { // from class: com.home.activity.other.MusicLibActivity.7
            @Override // com.common.task.NetCallBack
            public void onPreCall() {
            }

            @Override // com.common.task.NetCallBack
            public void onFinish(NetResult netResult) {
                if (netResult != null) {
                    MusicLibActivity.this.buildmp3Adapter((ArrayList) netResult.getTag(), false);
                }
            }

            @Override // com.common.task.NetCallBack
            public NetResult onDoInBack(HashMap<String, String> hashMap) {
                NetResult netResult;
                Exception e;
                ArrayList<Mp3> mp3Files;
                try {
                    mp3Files = MusicLibActivity.this.getMp3Files();
                    netResult = new NetResult();
                } catch (Exception e2) {
                    netResult = null;
                    e = e2;
                }
                try {
                    netResult.setTag(mp3Files);
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    return netResult;
                }
                return netResult;
            }
        });
        this.baseTask = baseTask2;
        baseTask2.execute(new HashMap());
    }

    public ArrayList<Mp3> getMp3Files() {
        ArrayList<Mp3> arrayList = new ArrayList<>();
        Cursor query = this.mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, "title_key");
        while (query.moveToNext()) {
            Mp3 mp3 = new Mp3();
            mp3.setId(query.getInt(query.getColumnIndexOrThrow("_id")));
            mp3.setTitle(query.getString(query.getColumnIndexOrThrow(FFmpegMediaMetadataRetriever.METADATA_KEY_TITLE)));
            mp3.setAlbum(query.getString(query.getColumnIndexOrThrow(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM)));
            mp3.setArtist(query.getString(query.getColumnIndexOrThrow(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST)));
            String string = query.getString(query.getColumnIndexOrThrow("_data"));
            mp3.setUrl(string);
            mp3.setDuration(query.getInt(query.getColumnIndexOrThrow(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION)));
            Long valueOf = Long.valueOf(query.getLong(query.getColumnIndexOrThrow("_size")));
            mp3.setSize(valueOf.longValue());
            if (valueOf.longValue() > 819200 && string.endsWith(PictureFileUtils.POST_AUDIO)) {
                arrayList.add(mp3);
            }
        }
        return arrayList;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 120) {
            return;
        }
        refresh();
    }
}
