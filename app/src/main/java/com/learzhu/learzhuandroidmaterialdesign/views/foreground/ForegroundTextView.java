package com.learzhu.learzhuandroidmaterialdesign.views.foreground;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.learzhu.learzhuandroidmaterialdesign.R;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/8/22 21:19
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  21:19
 * @updateDes ${TODO}
 */
public class ForegroundTextView extends TextView {
    //UI
    private Drawable foreground;

    //Controller/logic fields
    private final Rect rectPadding = new Rect();
    private boolean foregroundPadding = false;
    private boolean foregroundBoundsChanged = false;
    private boolean backgroundAsForeground = false;

    //Constructors
    public ForegroundTextView(Context context) {
        super(context);
    }

    public ForegroundTextView(Context context, AttributeSet attrs) {
//            super(context, attrs);
        this(context, attrs, 0);
    }

    public ForegroundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ForegroundLinearLayout, defStyleAttr, 0);

//            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ForegroundLayout, defStyle, 0);
//            <declare-styleable name="ForegroundLinearLayout">
//            <attr name="android:foreground"/>
//            <attr name="android:foregroundGravity"/>
//            <attr format="boolean" name="foregroundInsidePadding"/>
//            </declare-styleable>

//        <declare-styleable name="ForegroundLayout">
//        <attr format="reference" name="foreground"/>
//        <attr name="foregroundGravity"/>
//        <attr format="boolean" name="foregroundInsidePadding"/>
//        <attr format="boolean" name="backgroundAsForeground"/>
//        </declare-styleable>
//        final Drawable drawable = array.getDrawable(R.styleable.ForegroundLayout_foreground);
//        foregroundPadding = array.getBoolean(R.styleable.ForegroundLayout_foregroundInsidePadding, false);
//        backgroundAsForeground = array.getBoolean(R.styleable.ForegroundLayout_backgroundAsForeground, false);

        final Drawable drawable = array.getDrawable(R.styleable.ForegroundLinearLayout_android_foreground);
        foregroundPadding = array.getBoolean(R.styleable.ForegroundLinearLayout_foregroundInsidePadding, false);
        backgroundAsForeground = array.getBoolean(R.styleable.ForegroundLinearLayout_foregroundInsidePadding, false);

        // Apply foreground padding for ninepatches automatically
        if (!foregroundPadding && getBackground() instanceof NinePatchDrawable) {
            final NinePatchDrawable npd = (NinePatchDrawable) getBackground();
            if (npd != null && npd.getPadding(rectPadding)) {
                foregroundPadding = true;
            }
        }

        final Drawable b = getBackground();
        if (backgroundAsForeground && b != null) {
            setForeground(b);
        } else if (drawable != null) {
            setForeground(drawable);
        }
        array.recycle();
    }

    /**
     * Supply a Drawable that is to be rendered on top of all of the child views in the layout.
     *
     * @param drawable The Drawable to be drawn on top of the children.
     */
    public void setForeground(Drawable drawable) {
        if (foreground != drawable) {
            if (foreground != null) {
                foreground.setCallback(null);
                unscheduleDrawable(foreground);
            }

            foreground = drawable;

            if (drawable != null) {
                setWillNotDraw(false);
                drawable.setCallback(this);
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    /**
     * Returns the drawable used as the foreground of this layout. The foreground drawable,
     * if non-null, is always drawn on top of the children.
     *
     * @return A Drawable or null if no foreground was set.
     */
    public Drawable getForeground() {
        return foreground;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (foreground != null && foreground.isStateful()) {
            foreground.setState(getDrawableState());
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || (who == foreground);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();

        if (foreground != null) {
            foreground.jumpToCurrentState();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        foregroundBoundsChanged = true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (foreground != null) {
            final Drawable foreground = this.foreground;
            if (foregroundBoundsChanged) {
                foregroundBoundsChanged = false;
                final int w = getRight() - getLeft();
                final int h = getBottom() - getTop();
                if (foregroundPadding) {
                    foreground.setBounds(rectPadding.left, rectPadding.top, w - rectPadding.right, h - rectPadding.bottom);
                } else {
                    foreground.setBounds(0, 0, w, h);
                }
            }
            foreground.draw(canvas);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (e.getActionMasked() == MotionEvent.ACTION_DOWN) {
                if (foreground != null) {
                    foreground.setHotspot(e.getX(), e.getY());
                }
            }
        }
        return super.onTouchEvent(e);
    }
//        public ForegroundTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//            super(context, attrs, defStyleAttr, defStyleRes);
//        }
}

