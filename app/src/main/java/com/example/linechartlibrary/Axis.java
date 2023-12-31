package com.example.linechartlibrary;

import android.graphics.Typeface;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class Axis {
    public static final int DEFAULT_MAX_AXIS_LABEL_CHARS = 3;
    public static final int DEFAULT_TEXT_SIZE_SP = 12;
    private AxisValueFormatter formatter;
    private boolean hasLines;
    private boolean hasSeparationLine;
    private boolean hasTiltedLabels;
    private boolean isAutoGenerated;
    private boolean isInside;
    private int lineColor;
    private int maxLabelChars;
    private String name;
    private int separationLineColor;
    private int textColor;
    private int textSize;
    private Typeface typeface;
    private List<AxisValue> values;

    public Axis() {
        this.textSize = 12;
        this.maxLabelChars = 3;
        this.values = new ArrayList();
        this.isAutoGenerated = true;
        this.hasLines = false;
        this.isInside = false;
        this.textColor = -3355444;
        this.lineColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.separationLineColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.formatter = new SimpleAxisValueFormatter();
        this.hasSeparationLine = true;
        this.hasTiltedLabels = false;
    }

    public Axis(List<AxisValue> list) {
        this.textSize = 12;
        this.maxLabelChars = 3;
        this.values = new ArrayList();
        this.isAutoGenerated = true;
        this.hasLines = false;
        this.isInside = false;
        this.textColor = -3355444;
        this.lineColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.separationLineColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.formatter = new SimpleAxisValueFormatter();
        this.hasSeparationLine = true;
        this.hasTiltedLabels = false;
        setValues(list);
    }

    public Axis(Axis axis) {
        this.textSize = 12;
        this.maxLabelChars = 3;
        this.values = new ArrayList();
        this.isAutoGenerated = true;
        this.hasLines = false;
        this.isInside = false;
        this.textColor = -3355444;
        this.lineColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.separationLineColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.formatter = new SimpleAxisValueFormatter();
        this.hasSeparationLine = true;
        this.hasTiltedLabels = false;
        this.name = axis.name;
        this.isAutoGenerated = axis.isAutoGenerated;
        this.hasLines = axis.hasLines;
        this.isInside = axis.isInside;
        this.textColor = axis.textColor;
        this.lineColor = axis.lineColor;
        this.textSize = axis.textSize;
        this.maxLabelChars = axis.maxLabelChars;
        this.typeface = axis.typeface;
        this.formatter = axis.formatter;
        this.hasSeparationLine = axis.hasSeparationLine;
        for (AxisValue axisValue : axis.values) {
            this.values.add(new AxisValue(axisValue));
        }
    }

    public static Axis generateAxisFromRange(float f, float f2, float f3) {
        ArrayList arrayList = new ArrayList();
        while (f <= f2) {
            arrayList.add(new AxisValue(f));
            f += f3;
        }
        return new Axis(arrayList);
    }

    public static Axis generateAxisFromCollection(List<Float> list) {
        ArrayList arrayList = new ArrayList();
        for (Float f : list) {
            arrayList.add(new AxisValue(f.floatValue()));
        }
        return new Axis(arrayList);
    }

    public static Axis generateAxisFromCollection(List<Float> list, List<String> list2) {
        if (list.size() != list2.size()) {
            throw new IllegalArgumentException("Values and labels lists must have the same size!");
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Float f : list) {
            arrayList.add(new AxisValue(f.floatValue()).setLabel(list2.get(i)));
            i++;
        }
        return new Axis(arrayList);
    }

    public List<AxisValue> getValues() {
        return this.values;
    }

    public Axis setValues(List<AxisValue> list) {
        if (list == null) {
            this.values = new ArrayList();
        } else {
            this.values = list;
        }
        this.isAutoGenerated = false;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Axis setName(String str) {
        this.name = str;
        return this;
    }

    public boolean isAutoGenerated() {
        return this.isAutoGenerated;
    }

    public Axis setAutoGenerated(boolean z) {
        this.isAutoGenerated = z;
        return this;
    }

    public boolean hasLines() {
        return this.hasLines;
    }

    public Axis setHasLines(boolean z) {
        this.hasLines = z;
        return this;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public Axis setTextColor(int i) {
        this.textColor = i;
        return this;
    }

    public boolean isInside() {
        return this.isInside;
    }

    public Axis setInside(boolean z) {
        this.isInside = z;
        return this;
    }

    public int getLineColor() {
        return this.lineColor;
    }

    public Axis setLineColor(int i) {
        this.lineColor = i;
        return this;
    }

    public int getTextSize() {
        return this.textSize;
    }

    public Axis setTextSize(int i) {
        this.textSize = i;
        return this;
    }

    public int getMaxLabelChars() {
        return this.maxLabelChars;
    }

    public Axis setMaxLabelChars(int i) {
        if (i < 0) {
            i = 0;
        } else if (i > 32) {
            i = 32;
        }
        this.maxLabelChars = i;
        return this;
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public Axis setTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public AxisValueFormatter getFormatter() {
        return this.formatter;
    }

    public Axis setFormatter(AxisValueFormatter axisValueFormatter) {
        if (axisValueFormatter == null) {
            this.formatter = new SimpleAxisValueFormatter();
        } else {
            this.formatter = axisValueFormatter;
        }
        return this;
    }

    public Axis setHasSeparationLine(boolean z) {
        this.hasSeparationLine = z;
        return this;
    }

    public Axis setHasSeparationLineColor(int i) {
        this.separationLineColor = i;
        return this;
    }

    public int getHasSeparationLineColor() {
        return this.separationLineColor;
    }

    public boolean hasSeparationLine() {
        return this.hasSeparationLine;
    }

    public boolean hasTiltedLabels() {
        return this.hasTiltedLabels;
    }

    public Axis setHasTiltedLabels(boolean z) {
        this.hasTiltedLabels = z;
        return this;
    }
}
