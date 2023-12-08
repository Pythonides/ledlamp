package com.home.fragment.ble;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.home.view.custom.IndicatorSeekBar;
import com.ledlamp.R;

/* loaded from: classes.dex */
public class AisleFragment_ViewBinding implements Unbinder {
    private AisleFragment target;

    public AisleFragment_ViewBinding(AisleFragment aisleFragment, View view) {
        this.target = aisleFragment;
        aisleFragment.linearLayoutTab = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.linearLayoutTab, "field 'linearLayoutTab'", LinearLayout.class);
        aisleFragment.ll_aisle_a = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_aisle_a, "field 'll_aisle_a'", LinearLayout.class);
        aisleFragment.indicatorSeekBar = (IndicatorSeekBar) Utils.findRequiredViewAsType(view, R.id.indicator_seek_bar, "field 'indicatorSeekBar'", IndicatorSeekBar.class);
        aisleFragment.seekBarRedBrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarRedBrightNess, "field 'seekBarRedBrightNess'", SeekBar.class);
        aisleFragment.tvBrightness1 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness1, "field 'tvBrightness1'", TextView.class);
        aisleFragment.seekBarGreenBrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarGreenBrightNess, "field 'seekBarGreenBrightNess'", SeekBar.class);
        aisleFragment.tvBrightness2 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness2, "field 'tvBrightness2'", TextView.class);
        aisleFragment.seekBarBlueBrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarBlueBrightNess, "field 'seekBarBlueBrightNess'", SeekBar.class);
        aisleFragment.tvBrightness3 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness3, "field 'tvBrightness3'", TextView.class);
        aisleFragment.seekBarWhiteBrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarWhiteBrightNess, "field 'seekBarWhiteBrightNess'", SeekBar.class);
        aisleFragment.tvBrightness4 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness4, "field 'tvBrightness4'", TextView.class);
        aisleFragment.seekBarYellowBrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarYellowBrightNess, "field 'seekBarYellowBrightNess'", SeekBar.class);
        aisleFragment.tvBrightness5 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness5, "field 'tvBrightness5'", TextView.class);
        aisleFragment.seekBarPinkBrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarPinkBrightNess, "field 'seekBarPinkBrightNess'", SeekBar.class);
        aisleFragment.tvBrightness6 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness6, "field 'tvBrightness6'", TextView.class);
        aisleFragment.seekBarCrystalBrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarCrystalBrightNess, "field 'seekBarCrystalBrightNess'", SeekBar.class);
        aisleFragment.tvBrightness7 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness7, "field 'tvBrightness7'", TextView.class);
        aisleFragment.seekBarCH1BrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarCH1BrightNess, "field 'seekBarCH1BrightNess'", SeekBar.class);
        aisleFragment.tvBrightness8 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness8, "field 'tvBrightness8'", TextView.class);
        aisleFragment.seekBarCH2BrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarCH2BrightNess, "field 'seekBarCH2BrightNess'", SeekBar.class);
        aisleFragment.tvBrightness9 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness9, "field 'tvBrightness9'", TextView.class);
        aisleFragment.seekBarCH3BrightNess = (SeekBar) Utils.findRequiredViewAsType(view, R.id.seekBarCH3BrightNess, "field 'seekBarCH3BrightNess'", SeekBar.class);
        aisleFragment.tvBrightness10 = (TextView) Utils.findRequiredViewAsType(view, R.id.tvBrightness10, "field 'tvBrightness10'", TextView.class);
        aisleFragment.lvAisle = (ListView) Utils.findRequiredViewAsType(view, R.id.lvAisle, "field 'lvAisle'", ListView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        AisleFragment aisleFragment = this.target;
        if (aisleFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        aisleFragment.linearLayoutTab = null;
        aisleFragment.ll_aisle_a = null;
        aisleFragment.indicatorSeekBar = null;
        aisleFragment.seekBarRedBrightNess = null;
        aisleFragment.tvBrightness1 = null;
        aisleFragment.seekBarGreenBrightNess = null;
        aisleFragment.tvBrightness2 = null;
        aisleFragment.seekBarBlueBrightNess = null;
        aisleFragment.tvBrightness3 = null;
        aisleFragment.seekBarWhiteBrightNess = null;
        aisleFragment.tvBrightness4 = null;
        aisleFragment.seekBarYellowBrightNess = null;
        aisleFragment.tvBrightness5 = null;
        aisleFragment.seekBarPinkBrightNess = null;
        aisleFragment.tvBrightness6 = null;
        aisleFragment.seekBarCrystalBrightNess = null;
        aisleFragment.tvBrightness7 = null;
        aisleFragment.seekBarCH1BrightNess = null;
        aisleFragment.tvBrightness8 = null;
        aisleFragment.seekBarCH2BrightNess = null;
        aisleFragment.tvBrightness9 = null;
        aisleFragment.seekBarCH3BrightNess = null;
        aisleFragment.tvBrightness10 = null;
        aisleFragment.lvAisle = null;
    }
}
