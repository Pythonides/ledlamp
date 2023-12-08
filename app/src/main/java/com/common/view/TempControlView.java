package com.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.ledlamp.R;

/* loaded from: classes.dex */
public class TempControlView extends View {
    private float angleOne;
    private int angleRate;
    private Paint arcPaint;
    private int arcRadius;
    private Paint arrowPaint;
    private Bitmap buttonImage;
    private Paint buttonPaint;
    private boolean canRotate;
    private float currentAngle;
    private Paint dialPaint;
    private int dialRadius;
    private int height;
    private boolean isDown;
    private boolean isMove;
    private int maxTemp;
    private int minTemp;
    private OnClickListener onClickListener;
    private OnTempChangeListener onTempChangeListener;
    private PaintFlagsDrawFilter paintFlagsDrawFilter;
    private float rotateAngle;
    private int scaleHeight;
    private Paint tempFlagPaint;
    private Paint tempPaint;
    private int temperature;
    private String title;
    private int width;

    /* loaded from: classes.dex */
    public interface OnClickListener {
        void onClick(int i);
    }

    /* loaded from: classes.dex */
    public interface OnTempChangeListener {
        void change(int i);
    }

    public TempControlView(Context context) {
        this(context, null);
    }

    public TempControlView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TempControlView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.scaleHeight = dp2px(10.0f);
        this.title = getResources().getString(R.string.sun_sei);
        this.temperature = 15;
        this.minTemp = 15;
        this.maxTemp = 30;
        this.angleRate = 4;
        this.angleOne = (270.0f / (30 - 15)) / 4;
        this.buttonImage = BitmapFactory.decodeResource(getResources(), R.drawable.btn_rotate);
        this.canRotate = false;
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.dialPaint = paint;
        paint.setAntiAlias(true);
        this.dialPaint.setStrokeWidth(dp2px(2.0f));
        this.dialPaint.setStyle(Paint.Style.STROKE);
        Paint paint2 = new Paint();
        this.arcPaint = paint2;
        paint2.setAntiAlias(true);
        this.arcPaint.setStrokeWidth(dp2px(6.0f));
        this.arcPaint.setStyle(Paint.Style.STROKE);
        this.arcPaint.setShader(new SweepGradient(0.0f, 0.0f, new int[]{-1, InputDeviceCompat.SOURCE_ANY}, (float[]) null));
        Paint paint3 = new Paint();
        this.tempFlagPaint = paint3;
        paint3.setAntiAlias(true);
        this.tempFlagPaint.setTextSize(sp2px(25.0f));
        this.tempFlagPaint.setColor(0);
        this.tempFlagPaint.setStyle(Paint.Style.STROKE);
        Paint paint4 = new Paint();
        this.buttonPaint = paint4;
        paint4.setColor(-1);
        this.tempFlagPaint.setAntiAlias(true);
        this.paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        Paint paint5 = new Paint();
        this.tempPaint = paint5;
        paint5.setAntiAlias(true);
        this.tempPaint.setTextSize(sp2px(60.0f));
        this.tempPaint.setColor(Color.parseColor("#E27A3F"));
        this.tempPaint.setStyle(Paint.Style.STROKE);
        Paint paint6 = new Paint();
        this.arrowPaint = paint6;
        paint6.setColor(SupportMenu.CATEGORY_MASK);
        this.arrowPaint.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int min = Math.min(i2, i);
        this.height = min;
        this.width = min;
        int dp2px = (min / 2) - dp2px(20.0f);
        this.dialRadius = dp2px;
        this.arcRadius = dp2px - dp2px(10.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawText(canvas);
        drawButton(canvas);
    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(133.0f);
        this.dialPaint.setColor(Color.parseColor("#3CB7EA"));
        for (int i = this.angleRate * this.maxTemp; i > this.angleRate * this.temperature; i--) {
            int i2 = this.dialRadius;
            canvas.drawLine(0.0f, -i2, 0.0f, (-i2) + this.scaleHeight, this.dialPaint);
            canvas.rotate(-this.angleOne);
        }
        this.dialPaint.setColor(Color.parseColor("#E37364"));
        for (int i3 = this.temperature * this.angleRate; i3 >= this.minTemp * this.angleRate; i3--) {
            int i4 = this.dialRadius;
            canvas.drawLine(0.0f, -i4, 0.0f, (-i4) + this.scaleHeight, this.dialPaint);
            canvas.rotate(-this.angleOne);
        }
        canvas.restore();
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(135.0f);
        int i = this.arcRadius;
        canvas.drawArc(new RectF(-i, -i, i, i), 0.0f, 270.0f, false, this.arcPaint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        int i = this.minTemp;
        canvas.rotate(55.0f, this.width / 2, this.height / 2);
        canvas.rotate(-105.0f, this.width / 2, this.height / 2);
        canvas.restore();
    }

    private void drawButton(Canvas canvas) {
        int width = this.buttonImage.getWidth();
        int height = this.buttonImage.getHeight();
        Matrix matrix = new Matrix();
        matrix.setTranslate((this.width - width) / 2, (this.height - height) / 2);
        matrix.postRotate(this.rotateAngle + 45.0f, this.width / 2, this.height / 2);
        canvas.setDrawFilter(this.paintFlagsDrawFilter);
        canvas.drawBitmap(tintBitmap(this.buttonImage, Color.argb(255, 255, 255, 255 - ((this.temperature * 255) / 100))), matrix, this.buttonPaint);
    }

    private void drawTemp(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        Paint paint = this.tempPaint;
        float measureText = paint.measureText(this.temperature + "");
        canvas.drawText(this.temperature + "°", ((-measureText) / 2.0f) - dp2px(5.0f), -((this.tempPaint.ascent() + this.tempPaint.descent()) / 2.0f), this.tempPaint);
        canvas.restore();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0016, code lost:
        if (r0 != 3) goto L13;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.canRotate
            if (r0 != 0) goto L9
            boolean r5 = super.onTouchEvent(r5)
            return r5
        L9:
            int r0 = r5.getAction()
            r1 = 1
            if (r0 == 0) goto L87
            if (r0 == r1) goto L53
            r2 = 2
            if (r0 == r2) goto L1a
            r5 = 3
            if (r0 == r5) goto L53
            goto L97
        L1a:
            r4.isMove = r1
            float r0 = r5.getX()
            float r5 = r5.getY()
            float r5 = r4.calcAngle(r0, r5)
            float r0 = r4.currentAngle
            float r0 = r5 - r0
            r2 = -1014562816(0xffffffffc3870000, float:-270.0)
            r3 = 1135869952(0x43b40000, float:360.0)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 >= 0) goto L36
            float r0 = r0 + r3
            goto L3d
        L36:
            r2 = 1132920832(0x43870000, float:270.0)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L3d
            float r0 = r0 - r3
        L3d:
            r4.IncreaseAngle(r0)
            r4.currentAngle = r5
            r4.invalidate()
            boolean r5 = r4.isMove
            if (r5 == 0) goto L97
            com.common.view.TempControlView$OnTempChangeListener r5 = r4.onTempChangeListener
            if (r5 == 0) goto L97
            int r0 = r4.temperature
            r5.change(r0)
            goto L97
        L53:
            boolean r5 = r4.isDown
            if (r5 == 0) goto L97
            boolean r5 = r4.isMove
            r0 = 0
            if (r5 == 0) goto L7b
            int r5 = r4.temperature
            int r2 = r4.minTemp
            int r5 = r5 - r2
            int r2 = r4.angleRate
            int r5 = r5 * r2
            float r5 = (float) r5
            float r2 = r4.angleOne
            float r5 = r5 * r2
            r4.rotateAngle = r5
            r4.invalidate()
            com.common.view.TempControlView$OnTempChangeListener r5 = r4.onTempChangeListener
            if (r5 == 0) goto L78
            int r2 = r4.temperature
            r5.change(r2)
        L78:
            r4.isMove = r0
            goto L84
        L7b:
            com.common.view.TempControlView$OnClickListener r5 = r4.onClickListener
            if (r5 == 0) goto L84
            int r2 = r4.temperature
            r5.onClick(r2)
        L84:
            r4.isDown = r0
            goto L97
        L87:
            r4.isDown = r1
            float r0 = r5.getX()
            float r5 = r5.getY()
            float r5 = r4.calcAngle(r0, r5)
            r4.currentAngle = r5
        L97:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.common.view.TempControlView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private float calcAngle(float f, float f2) {
        double d;
        float f3 = f - (this.width / 2);
        float f4 = f2 - (this.height / 2);
        if (f3 != 0.0f) {
            float abs = Math.abs(f4 / f3);
            if (f3 > 0.0f) {
                if (f4 >= 0.0f) {
                    d = Math.atan(abs);
                } else {
                    d = 6.283185307179586d - Math.atan(abs);
                }
            } else if (f4 >= 0.0f) {
                d = 3.141592653589793d - Math.atan(abs);
            } else {
                d = Math.atan(abs) + 3.141592653589793d;
            }
        } else {
            d = f4 > 0.0f ? 1.5707963267948966d : -1.5707963267948966d;
        }
        return (float) ((d * 180.0d) / 3.141592653589793d);
    }

    private void IncreaseAngle(float f) {
        float f2 = this.rotateAngle + f;
        this.rotateAngle = f2;
        if (f2 < 0.0f) {
            this.rotateAngle = 0.0f;
        } else if (f2 > 270.0f) {
            this.rotateAngle = 270.0f;
        }
        double d = (this.rotateAngle / this.angleOne) / this.angleRate;
        Double.isNaN(d);
        this.temperature = ((int) (d + 0.5d)) + this.minTemp;
        this.title = getResources().getString(R.string.cool_warm, String.valueOf(100 - this.temperature), String.valueOf(this.temperature));
        this.buttonPaint.setColor(Color.argb(255, 255, 255, 255 - ((this.temperature * 255) / 100)));
        this.tempPaint.setColor(Color.argb(255, 255, 255, 255 - ((this.temperature * 255) / 100)));
    }

    public void setAngleRate(int i) {
        this.angleRate = i;
    }

    public void setTemp(int i) {
        setTemp(this.minTemp, this.maxTemp, i);
    }

    public void setTemp(int i, int i2, int i3) {
        this.minTemp = i;
        this.maxTemp = i2;
        if (i3 < i) {
            this.temperature = i;
        } else {
            this.temperature = i3;
        }
        float f = 270.0f / (i2 - i);
        int i4 = this.angleRate;
        float f2 = f / i4;
        this.angleOne = f2;
        this.rotateAngle = (i3 - i) * i4 * f2;
        invalidate();
    }

    public void setCanRotate(boolean z) {
        this.canRotate = z;
    }

    public boolean getCanRotate() {
        return this.canRotate;
    }

    public void setOnTempChangeListener(OnTempChangeListener onTempChangeListener) {
        this.onTempChangeListener = onTempChangeListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static Bitmap tintBitmap(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public int dp2px(float f) {
        return (int) TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    private int sp2px(float f) {
        return (int) TypedValue.applyDimension(2, f, getResources().getDisplayMetrics());
    }
}
