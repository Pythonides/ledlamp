package com.home.fragment.ble;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.itheima.wheelpicker.WheelPicker;
import com.ledlamp.R;

/* loaded from: classes.dex */
public class ModeFragment_ViewBinding implements Unbinder {
    private ModeFragment target;

    public ModeFragment_ViewBinding(ModeFragment modeFragment, View view) {
        this.target = modeFragment;
        modeFragment.wheelPicker = (WheelPicker) Utils.findRequiredViewAsType(view, R.id.wheelPicker, "field 'wheelPicker'", WheelPicker.class);
        modeFragment.wheelPickerCar01DMX = (WheelPicker) Utils.findRequiredViewAsType(view, R.id.wheelPickerCar01DMX, "field 'wheelPickerCar01DMX'", WheelPicker.class);
        modeFragment.imageViewOnOff = (Button) Utils.findRequiredViewAsType(view, R.id.imageViewOnOff, "field 'imageViewOnOff'", Button.class);
        modeFragment.llmode = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.llmode, "field 'llmode'", LinearLayout.class);
        modeFragment.seekBarMode = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarMode, "field 'seekBarMode'", SeekBar.class);
        modeFragment.textViewMode = (TextView) Utils.findRequiredViewAsType(view, R.id.textViewMode, "field 'textViewMode'", TextView.class);
        modeFragment.seekBarSpeedBar = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarSpeed, "field 'seekBarSpeedBar'", SeekBar.class);
        modeFragment.textViewSpeed = (TextView) Utils.findRequiredViewAsType(view, R.id.textViewSpeed, "field 'textViewSpeed'", TextView.class);
        modeFragment.seekBarBrightness = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarBrightNess, "field 'seekBarBrightness'", SeekBar.class);
        modeFragment.textViewBrightness = (TextView) Utils.findRequiredViewAsType(view, R.id.textViewBrightNess, "field 'textViewBrightness'", TextView.class);
        modeFragment.llmodeCar01DMX = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.llmodeCar01DMX, "field 'llmodeCar01DMX'", LinearLayout.class);
        modeFragment.seekBarModeCar01DMX = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarModeCar01DMX, "field 'seekBarModeCar01DMX'", SeekBar.class);
        modeFragment.textViewModeCar01DMX = (TextView) Utils.findRequiredViewAsType(view, R.id.textViewModeCar01DMX, "field 'textViewModeCar01DMX'", TextView.class);
        modeFragment.rlModeTopDIYCAR01BLE = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rlModeTopDIYCAR01BLE, "field 'rlModeTopDIYCAR01BLE'", RelativeLayout.class);
        modeFragment.rlModeTopDIYCAR01DMX = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rlModeTopDIYCAR01DMX, "field 'rlModeTopDIYCAR01DMX'", RelativeLayout.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ModeFragment modeFragment = this.target;
        if (modeFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        modeFragment.wheelPicker = null;
        modeFragment.wheelPickerCar01DMX = null;
        modeFragment.imageViewOnOff = null;
        modeFragment.llmode = null;
        modeFragment.seekBarMode = null;
        modeFragment.textViewMode = null;
        modeFragment.seekBarSpeedBar = null;
        modeFragment.textViewSpeed = null;
        modeFragment.seekBarBrightness = null;
        modeFragment.textViewBrightness = null;
        modeFragment.llmodeCar01DMX = null;
        modeFragment.seekBarModeCar01DMX = null;
        modeFragment.textViewModeCar01DMX = null;
        modeFragment.rlModeTopDIYCAR01BLE = null;
        modeFragment.rlModeTopDIYCAR01DMX = null;
    }
}
