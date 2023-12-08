package com.home.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.home.base.LedBleApplication;
import com.home.db.GroupDevice;
import com.ledlamp.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AisleView {
    private int connect;
    private Context context;
    private ArrayList<GroupDevice> groupDevices;
    private String groupName;
    private View groupView;
    private ImageView imageViewControll;
    private boolean isAllon;
    private TextView textViewTotal;
    private View viewBottomLine;
    private View viewTopLine;

    public AisleView(Context context, String str, boolean z) {
        this.context = context;
        this.isAllon = z;
        this.groupName = str;
        View inflate = View.inflate(context, R.layout.item_aisle, null);
        this.groupView = inflate;
        inflate.setTag(str);
        this.imageViewControll = (ImageView) this.groupView.findViewById(R.id.imageViewControll);
        this.viewTopLine = this.groupView.findViewById(R.id.viewTopLine);
        this.viewBottomLine = this.groupView.findViewById(R.id.viewBottomLine);
        this.textViewTotal = (TextView) this.groupView.findViewById(R.id.textViewTotal);
        ((TextView) this.groupView.findViewById(R.id.textViewGroupName)).setText(str);
    }

    public void hideTopLine() {
        this.viewTopLine.setVisibility(0);
    }

    public void hideBottomLine() {
        this.viewBottomLine.setVisibility(0);
    }

    public int getConnect() {
        return this.connect;
    }

    public ImageView getImageViewControll() {
        return this.imageViewControll;
    }

    public void setImageViewControll(ImageView imageView) {
        this.imageViewControll = imageView;
    }

    public ArrayList<GroupDevice> getGroupDevices() {
        return this.groupDevices;
    }

    public void setGroupDevices(ArrayList<GroupDevice> arrayList) {
        this.groupDevices = arrayList;
        setConnectCount(0);
    }

    public void setConnect(String str) {
        this.textViewTotal.setText(LedBleApplication.getApp().getResources().getString(R.string.connect_count, str));
    }

    public void setConnectCount(int i) {
        this.connect = i;
        this.textViewTotal.setText(LedBleApplication.getApp().getResources().getString(R.string.connect_count, String.valueOf(i)));
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public View getGroupView() {
        return this.groupView;
    }

    public void setGroupView(View view) {
        this.groupView = view;
    }
}
