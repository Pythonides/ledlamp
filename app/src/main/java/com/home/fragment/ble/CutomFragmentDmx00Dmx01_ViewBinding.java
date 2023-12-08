package com.home.fragment.ble;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.common.view.SegmentedRadioGroup;
import com.ledlamp.R;

/* loaded from: classes.dex */
public class CutomFragmentDmx00Dmx01_ViewBinding implements Unbinder {
    private CutomFragmentDmx00Dmx01 target;

    public CutomFragmentDmx00Dmx01_ViewBinding(CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01, View view) {
        this.target = cutomFragmentDmx00Dmx01;
        cutomFragmentDmx00Dmx01.llViewBlocks = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.llViewBlocks, "field 'llViewBlocks'", LinearLayout.class);
        cutomFragmentDmx00Dmx01.segmentedRadioGroupOne = (SegmentedRadioGroup) Utils.findRequiredViewAsType(view, R.id.segmentedRadioGroupOne, "field 'segmentedRadioGroupOne'", SegmentedRadioGroup.class);
        cutomFragmentDmx00Dmx01.segmentedRadioGroupTwo = (SegmentedRadioGroup) Utils.findRequiredViewAsType(view, R.id.segmentedRadioGroupTwo, "field 'segmentedRadioGroupTwo'", SegmentedRadioGroup.class);
        cutomFragmentDmx00Dmx01.llSegmentedRadioGroupTwo = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.llSegmentedRadioGroupTwo, "field 'llSegmentedRadioGroupTwo'", LinearLayout.class);
        cutomFragmentDmx00Dmx01.seekBarSpeedCustom = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarSpeedCustom, "field 'seekBarSpeedCustom'", SeekBar.class);
        cutomFragmentDmx00Dmx01.textViewSpeedCustom = (TextView) Utils.findRequiredViewAsType(view, R.id.textViewSpeedCustom, "field 'textViewSpeedCustom'", TextView.class);
        cutomFragmentDmx00Dmx01.seekBarBrightCustom = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarBrightCustom, "field 'seekBarBrightCustom'", SeekBar.class);
        cutomFragmentDmx00Dmx01.textViewBrightCustom = (TextView) Utils.findRequiredViewAsType(view, R.id.textViewBrightCustom, "field 'textViewBrightCustom'", TextView.class);
        cutomFragmentDmx00Dmx01.changeButtonOne = (RadioButton) Utils.findRequiredViewAsType(view, R.id.changeButtonOne, "field 'changeButtonOne'", RadioButton.class);
        cutomFragmentDmx00Dmx01.changeButtonTwo = (RadioButton) Utils.findRequiredViewAsType(view, R.id.changeButtonTwo, "field 'changeButtonTwo'", RadioButton.class);
        cutomFragmentDmx00Dmx01.changeButtonThree = (RadioButton) Utils.findRequiredViewAsType(view, R.id.changeButtonThree, "field 'changeButtonThree'", RadioButton.class);
        cutomFragmentDmx00Dmx01.changeButtonFour = (RadioButton) Utils.findRequiredViewAsType(view, R.id.changeButtonFour, "field 'changeButtonFour'", RadioButton.class);
        cutomFragmentDmx00Dmx01.changeButtonFive = (RadioButton) Utils.findRequiredViewAsType(view, R.id.changeButtonFive, "field 'changeButtonFive'", RadioButton.class);
        cutomFragmentDmx00Dmx01.changeButtonSix = (RadioButton) Utils.findRequiredViewAsType(view, R.id.changeButtonSix, "field 'changeButtonSix'", RadioButton.class);
        cutomFragmentDmx00Dmx01.changeButtonSeven = (RadioButton) Utils.findRequiredViewAsType(view, R.id.changeButtonSeven, "field 'changeButtonSeven'", RadioButton.class);
        cutomFragmentDmx00Dmx01.changeButtonEight = (RadioButton) Utils.findRequiredViewAsType(view, R.id.changeButtonEight, "field 'changeButtonEight'", RadioButton.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        CutomFragmentDmx00Dmx01 cutomFragmentDmx00Dmx01 = this.target;
        if (cutomFragmentDmx00Dmx01 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        cutomFragmentDmx00Dmx01.llViewBlocks = null;
        cutomFragmentDmx00Dmx01.segmentedRadioGroupOne = null;
        cutomFragmentDmx00Dmx01.segmentedRadioGroupTwo = null;
        cutomFragmentDmx00Dmx01.llSegmentedRadioGroupTwo = null;
        cutomFragmentDmx00Dmx01.seekBarSpeedCustom = null;
        cutomFragmentDmx00Dmx01.textViewSpeedCustom = null;
        cutomFragmentDmx00Dmx01.seekBarBrightCustom = null;
        cutomFragmentDmx00Dmx01.textViewBrightCustom = null;
        cutomFragmentDmx00Dmx01.changeButtonOne = null;
        cutomFragmentDmx00Dmx01.changeButtonTwo = null;
        cutomFragmentDmx00Dmx01.changeButtonThree = null;
        cutomFragmentDmx00Dmx01.changeButtonFour = null;
        cutomFragmentDmx00Dmx01.changeButtonFive = null;
        cutomFragmentDmx00Dmx01.changeButtonSix = null;
        cutomFragmentDmx00Dmx01.changeButtonSeven = null;
        cutomFragmentDmx00Dmx01.changeButtonEight = null;
    }
}
