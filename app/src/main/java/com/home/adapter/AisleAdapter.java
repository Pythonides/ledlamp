package com.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import com.common.adapter.OnSeekBarChangeListenerAdapter;
import com.common.uitl.SharePersistent;
import com.home.activity.main.MainActivity_BLE;
import com.home.bean.AisleBean;
import com.home.bean.Mp3;
import com.home.view.custom.IndicatorSeekBar;
import com.ledlamp.R;
import java.util.ArrayList;
import java.util.HashSet;

/* loaded from: classes.dex */
public class AisleAdapter extends BaseAdapter {
    private static final String TAG = "AisleFragment";
    private Context context;
    private ArrayList<AisleBean> list;
    private OnSelectListener onSelectListener;
    private int index = -1;
    private HashSet<AisleBean> selectSet = new HashSet<>();

    /* loaded from: classes.dex */
    public interface OnSelectListener {
        void onSelect(int i, Mp3 mp3, HashSet<Mp3> hashSet, boolean z, BaseAdapter baseAdapter);
    }

    public AisleAdapter(Context context, ArrayList<AisleBean> arrayList) {
        this.list = arrayList;
        this.context = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return this.list.get(i).getId();
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2;
        final ViewHolder viewHolder;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = View.inflate(this.context, R.layout.item_aisle, null);
            viewHolder.tvAisleName = (TextView) view2.findViewById(R.id.tvAisleName);
            viewHolder.tvBrightness = (TextView) view2.findViewById(R.id.tvBrightness);
            viewHolder.seekBarBrightNess = (IndicatorSeekBar) view2.findViewById(R.id.seekBarAisleBrightNess);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvAisleName.setText(this.list.get(i).getTitle());
        viewHolder.seekBarBrightNess.setProgress(this.list.get(i).getSeekbarValue());
        viewHolder.seekBarBrightNess.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() { // from class: com.home.adapter.AisleAdapter.1
            @Override // com.common.adapter.OnSeekBarChangeListenerAdapter, android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i8, boolean z) {
                if (z) {
                    viewHolder.tvBrightness.setText(Integer.toString(i8));
                    ((AisleBean) AisleAdapter.this.list.get(i)).setSeekbarValue(i8);
                    if (MainActivity_BLE.getMainActivity() != null) {
                        MainActivity_BLE.getMainActivity().setSmartBrightness(i8, i + 1);
                    }
                    if (MainActivity_BLE.sceneBean != null) {
                        Context context = AisleAdapter.this.context;
                        SharePersistent.saveInt(context, MainActivity_BLE.sceneBean + AisleAdapter.TAG + (i + 1) + "seekBarBrightNess", i8);
                    }
                }
            }
        });
        TextView textView = viewHolder.tvBrightness;
        textView.setText("" + this.list.get(i).getSeekbarValue());
        if (MainActivity_BLE.sceneBean != null) {
            Context context = this.context;
            int i8 = SharePersistent.getInt(context, MainActivity_BLE.sceneBean + TAG + (i + 1) + "seekBarBrightNess");
            if (i8 >= 0) {
                viewHolder.seekBarBrightNess.setProgress(i8);
                viewHolder.tvBrightness.setText(String.valueOf(i8));
            }
        }
        int i9 = -1;
        if (MainActivity_BLE.getMainActivity() != null) {
            Context context2 = this.context;
            if (SharePersistent.getInt(context2, MainActivity_BLE.getSceneBean() + "CH_R_STAGE") != 0) {
                Context context3 = this.context;
                i7 = SharePersistent.getInt(context3, MainActivity_BLE.getSceneBean() + "CH_R_STAGE");
            } else {
                i7 = -1;
            }
            Context context4 = this.context;
            if (SharePersistent.getInt(context4, MainActivity_BLE.getSceneBean() + "CH_G_STAGE") != 0) {
                Context context5 = this.context;
                i3 = SharePersistent.getInt(context5, MainActivity_BLE.getSceneBean() + "CH_G_STAGE");
            } else {
                i3 = -1;
            }
            Context context6 = this.context;
            if (SharePersistent.getInt(context6, MainActivity_BLE.getSceneBean() + "CH_B_STAGE") != 0) {
                Context context7 = this.context;
                i4 = SharePersistent.getInt(context7, MainActivity_BLE.getSceneBean() + "CH_B_STAGE");
            } else {
                i4 = -1;
            }
            Context context8 = this.context;
            if (SharePersistent.getInt(context8, MainActivity_BLE.getSceneBean() + "CH_W_STAGE") != 0) {
                Context context9 = this.context;
                i5 = SharePersistent.getInt(context9, MainActivity_BLE.getSceneBean() + "CH_W_STAGE");
            } else {
                i5 = -1;
            }
            Context context10 = this.context;
            if (SharePersistent.getInt(context10, MainActivity_BLE.getSceneBean() + "CH_Y_STAGE") != 0) {
                Context context11 = this.context;
                i6 = SharePersistent.getInt(context11, MainActivity_BLE.getSceneBean() + "CH_Y_STAGE");
            } else {
                i6 = -1;
            }
            Context context12 = this.context;
            if (SharePersistent.getInt(context12, MainActivity_BLE.getSceneBean() + "CH_P_STAGE") != 0) {
                Context context13 = this.context;
                i9 = SharePersistent.getInt(context13, MainActivity_BLE.getSceneBean() + "CH_P_STAGE");
            }
            int i10 = i9;
            i9 = i7;
            i2 = i10;
        } else {
            i2 = -1;
            i3 = -1;
            i4 = -1;
            i5 = -1;
            i6 = -1;
        }
        if (viewHolder.seekBarBrightNess != null) {
            viewHolder.seekBarBrightNess.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.seekbar_bringh_ch1_ch6));
            int i11 = i + 1;
            if (i9 == i11) {
                viewHolder.seekBarBrightNess.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.seekbar_red));
            }
            if (i3 == i11) {
                viewHolder.seekBarBrightNess.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.seekbar_green));
            }
            if (i4 == i11) {
                viewHolder.seekBarBrightNess.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.seekbar_blue));
            }
            if (i5 == i11) {
                viewHolder.seekBarBrightNess.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.seekbar_writ));
            }
            if (i6 == i11) {
                viewHolder.seekBarBrightNess.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.seekbar_yellow));
            }
            if (i2 == i11) {
                viewHolder.seekBarBrightNess.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.seekbar_p));
            }
        }
        return view2;
    }

    public void selectAll() {
        this.selectSet.addAll(this.list);
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public OnSelectListener getOnSelectListener() {
        return this.onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    /* loaded from: classes.dex */
    class ViewHolder {
        IndicatorSeekBar seekBarBrightNess;
        TextView tvAisleName;
        TextView tvBrightness;

        ViewHolder() {
        }
    }
}
