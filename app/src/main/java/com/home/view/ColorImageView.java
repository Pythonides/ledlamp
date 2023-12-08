package com.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class ColorImageView extends ImageView {
    private int color;

    public ColorImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.color = 0;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }
}
