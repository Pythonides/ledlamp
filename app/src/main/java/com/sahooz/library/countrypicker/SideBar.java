package com.sahooz.library.countrypicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class SideBar extends View {
    private int cellHeight;
    private int cellWidth;
    private int currentIndex;
    public final ArrayList<String> indexes;
    private int letterColor;
    private int letterSize;
    private OnLetterChangeListener onLetterChangeListener;
    private Paint paint;
    private int selectColor;
    private float textHeight;

    /* loaded from: classes.dex */
    public interface OnLetterChangeListener {
        void onLetterChange(String str);

        void onReset();
    }

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SideBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ArrayList<String> arrayList = new ArrayList<>();
        this.indexes = arrayList;
        this.currentIndex = -1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SideBar, i, 0);
        this.letterColor = obtainStyledAttributes.getColor(R.styleable.SideBar_letterColor, ViewCompat.MEASURED_STATE_MASK);
        this.selectColor = obtainStyledAttributes.getColor(R.styleable.SideBar_selectColor, -16711681);
        this.letterSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SideBar_letterSize, 24);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        Paint.FontMetrics fontMetrics = this.paint.getFontMetrics();
        this.textHeight = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
        arrayList.addAll(Arrays.asList(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z"));
    }

    public void addIndex(String str, int i) {
        this.indexes.add(i, str);
        invalidate();
    }

    public void removeIndex(String str) {
        this.indexes.remove(str);
        invalidate();
    }

    public void setLetterSize(int i) {
        if (this.letterSize == i) {
            return;
        }
        this.letterSize = i;
        invalidate();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.cellWidth = getMeasuredWidth();
        this.cellHeight = getMeasuredHeight() / this.indexes.size();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setTextSize(this.letterSize);
        for (int i = 0; i < this.indexes.size(); i++) {
            String str = this.indexes.get(i);
            float measureText = (this.cellWidth - this.paint.measureText(str)) * 0.5f;
            int i2 = this.cellHeight;
            float f = ((i2 + this.textHeight) * 0.5f) + (i2 * i);
            if (i == this.currentIndex) {
                this.paint.setColor(this.selectColor);
            } else {
                this.paint.setColor(this.letterColor);
            }
            canvas.drawText(str, measureText, f, this.paint);
        }
    }

    public OnLetterChangeListener getOnLetterChangeListener() {
        return this.onLetterChangeListener;
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener) {
        this.onLetterChangeListener = onLetterChangeListener;
    }

    public String getLetter(int i) {
        return (i < 0 || i >= this.indexes.size()) ? "" : this.indexes.get(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000a, code lost:
        if (r0 != 2) goto L6;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r4) {
        /*
            r3 = this;
            int r0 = r4.getAction()
            r1 = 1
            if (r0 == 0) goto L1b
            if (r0 == r1) goto Ld
            r2 = 2
            if (r0 == r2) goto L1b
            goto L45
        Ld:
            r4 = -1
            r3.currentIndex = r4
            r3.invalidate()
            com.sahooz.library.countrypicker.SideBar$OnLetterChangeListener r4 = r3.onLetterChangeListener
            if (r4 == 0) goto L45
            r4.onReset()
            goto L45
        L1b:
            float r4 = r4.getY()
            int r4 = (int) r4
            int r0 = r3.cellHeight
            int r4 = r4 / r0
            r3.currentIndex = r4
            if (r4 < 0) goto L42
            java.util.ArrayList<java.lang.String> r0 = r3.indexes
            int r0 = r0.size()
            int r0 = r0 - r1
            if (r4 <= r0) goto L31
            goto L42
        L31:
            com.sahooz.library.countrypicker.SideBar$OnLetterChangeListener r4 = r3.onLetterChangeListener
            if (r4 == 0) goto L42
            java.util.ArrayList<java.lang.String> r0 = r3.indexes
            int r2 = r3.currentIndex
            java.lang.Object r0 = r0.get(r2)
            java.lang.String r0 = (java.lang.String) r0
            r4.onLetterChange(r0)
        L42:
            r3.invalidate()
        L45:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sahooz.library.countrypicker.SideBar.onTouchEvent(android.view.MotionEvent):boolean");
    }
}
