package com.example.sebsp.kalendreo.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sebsp.kalendreo.structure.AbstractAppCompatActivity;

/**
 * Created by Gaetan on 15/11/2017.
 * Linear Layout with custom's attributes
 */
public abstract class AbstractLinearLayout extends LinearLayout {

    protected AbstractAppCompatActivity currentActivity;

    public AbstractLinearLayout(Context context) {
        super(context);
    }

    public AbstractLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected abstract void init();

    protected LinearLayout.LayoutParams defaultLayoutParams = null;

    /**
     * Default Layout Params
     *
     * @return the LayoutParams set
     */
    protected LinearLayout.LayoutParams getDefaultLayoutParams() {

        if (defaultLayoutParams == null) {
            defaultLayoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setAllMarginsInDp(defaultLayoutParams, 16);
        }
        return defaultLayoutParams;
    }

    /**
     * Set paddings in Dp from Pixel (int)
     *
     * @param view View to set the paddings to
     * @param l    left
     * @param r    right
     * @param t    top
     * @param b    bottom
     */
    protected void setPaddingsInDp(View view, int l, int r, int t, int b) {
        view.setPadding(getDpFromPixel(l), getDpFromPixel(r), getDpFromPixel(t), getDpFromPixel(b));
    }

    /**
     * @param view    view to set
     * @param padding padding to apply on each side
     * @see this.setPaddingsInDp
     */
    protected void setAllPaddingsInDp(View view, int padding) {
        this.setPaddingsInDp(view, padding, padding, padding, padding);
    }

    /**
     * Set Margins in Dp from Pixel (int)
     *
     * @param lp LinearLayout.LayoutParams to set
     * @param l  left
     * @param r  right
     * @param t  top
     * @param b  bottom
     */
    protected void setMarginsInDp(LinearLayout.LayoutParams lp, int l, int r, int t, int b) {
        lp.setMargins(getDpFromPixel(l), getDpFromPixel(r), getDpFromPixel(t), getDpFromPixel(b));
    }

    /**
     * @param lp     LinearLayout.LayoutParams to set
     * @param margin padding to apply on each side
     * @see this.setMarginsInDp
     */
    protected void setAllMarginsInDp(LinearLayout.LayoutParams lp, int margin) {
        this.setMarginsInDp(lp, margin, margin, margin, margin);
    }

    /**
     * Convert a pixel value in dp
     *
     * @param value pixels
     * @return int dp
     */
    protected int getDpFromPixel(int value) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value, getResources().getDisplayMetrics());
    }
}
