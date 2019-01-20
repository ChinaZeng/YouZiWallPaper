package com.youzi.framework.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.youzi.framework.common.R;
import com.youzi.framework.common.SimpleTextWatcher;

/**
 * Created by LuoHaifeng on 2018/2/25 0025.
 * Email:496349136@qq.com
 */

public class MultiFunctionEditText extends AppCompatEditText implements TextWatcher {
    enum HintTitleShowType {
        ALWAYS_SHOW(0),
        WHEN_FILL_SHOW(1);

        private int value;

        HintTitleShowType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static HintTitleShowType findByValue(int value) {
            HintTitleShowType[] types = HintTitleShowType.values();
            for (HintTitleShowType showType : types) {
                if (showType.value == value) {
                    return showType;
                }
            }

            return HintTitleShowType.ALWAYS_SHOW; //默认
        }
    }

    private boolean letterSpaceEnable;
    private boolean clearButtonEnable;
    private boolean togglePasswordEnable;
    private boolean startZero;
    private boolean onlyTextBold;
    private Drawable clearDrawable;
    private String letterSpaceRule;
    private Drawable passwordVisibleIcon, passwordInVisibleIcon;
    private int iconPadding = 0;

    private int[] letterSpaceGap = new int[]{Integer.MAX_VALUE};
    private boolean isRepeatLastGap = false;

    private ProxyEditable proxyEditable;

    private float touchDownX, touchDownY;
    private final Drawable[] extraDrawablesSrc = new Drawable[3];//0是clearButton，1是passwordToggle,2是原生的右边图标
    private HorizontalDrawables extraDrawables = new HorizontalDrawables(extraDrawablesSrc);
    private TextWatcher onlyTextBoldWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            getPaint().setFakeBoldText(s.length() > 0);
        }
    };

    private TextWatcher noZeroStartWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            if (getInputType() == InputType.TYPE_CLASS_NUMBER) {
                if (s.toString().startsWith("0") && !s.toString().equals("0")) {
                    s.replace(0, 1, "");
                }
            }
        }
    };

    private TextWatcher clearButtonWatcher = new SimpleTextWatcher(){
        @Override
        public void afterTextChanged(Editable s) {
            updateClearButtonVisible();
        }
    };

    Paint bottomLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String tips;
    private StaticLayout tipsLayout;

    int tipsNeedHeight = 0;
    TextPaint tipsPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    int bottomLineColor;
    boolean bottomLineEnable;
    final int bottomLineHeight = 1;
    int tipsColor;
    int tipsMarginTop,tipsMarginBottom;
    int tipsTextSize;

    //以下是兼容TipsLayout搭配使用
    boolean hintTitleEnable = false;
    String hintTitle = null;
    HintTitleShowType hintTitleShowType = HintTitleShowType.ALWAYS_SHOW;

//    {
////        tips = "这是固定的错误提示内容这是固定的错误提示内容这是固定的错误提示内容这是固定的错误提示内容这是固定的错误提示内容";
//        tipsPaint.setColor(Color.RED);
//        tipsPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,14,getResources().getDisplayMetrics()));
//        tipsMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,getResources().getDisplayMetrics());
//    }

    public MultiFunctionEditText(Context context) {
        super(context);
        init(null, -1);
    }

    public MultiFunctionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, -1);
    }

    public MultiFunctionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (extraDrawablesSrc != null) {
            if(!(right instanceof HorizontalDrawables)){
                extraDrawablesSrc[2] = right;
            }
            extraDrawables.setBounds(0,0,extraDrawables.getIntrinsicWidth(),extraDrawables.getIntrinsicHeight());
            super.setCompoundDrawables(left, top, extraDrawables, bottom);
        } else {
            super.setCompoundDrawables(left, top, right, bottom);
        }

    }

    private void init(AttributeSet attributeSet, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.MultiFunctionEditText, defStyleAttr, -1);
        letterSpaceEnable = typedArray.getBoolean(R.styleable.MultiFunctionEditText_letterSpaceEnable, false);
        clearButtonEnable = typedArray.getBoolean(R.styleable.MultiFunctionEditText_clearButtonEnable, false);
        togglePasswordEnable = typedArray.getBoolean(R.styleable.MultiFunctionEditText_togglePasswordEnable, false);
        clearDrawable = typedArray.getDrawable(R.styleable.MultiFunctionEditText_clearButtonIcon);
        letterSpaceRule = typedArray.getString(R.styleable.MultiFunctionEditText_letterSpaceRule);
        passwordVisibleIcon = typedArray.getDrawable(R.styleable.MultiFunctionEditText_togglePasswordVisibleIcon);
        passwordInVisibleIcon = typedArray.getDrawable(R.styleable.MultiFunctionEditText_togglePasswordHideIcon);
        iconPadding = typedArray.getDimensionPixelSize(R.styleable.MultiFunctionEditText_iconPadding, 0);
        startZero = typedArray.getBoolean(R.styleable.MultiFunctionEditText_startZero, true);
        onlyTextBold = typedArray.getBoolean(R.styleable.MultiFunctionEditText_onlyTextBold, false);
        bottomLineColor = typedArray.getColor(R.styleable.MultiFunctionEditText_bottomLineColor, Color.GRAY);
        bottomLineEnable = typedArray.getBoolean(R.styleable.MultiFunctionEditText_bottomLineEnable, false);
        tipsColor = typedArray.getColor(R.styleable.MultiFunctionEditText_tipsColor, Color.RED);
        tipsMarginTop = typedArray.getDimensionPixelSize(R.styleable.MultiFunctionEditText_tipsMarginTop, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        tipsMarginBottom = typedArray.getDimensionPixelSize(R.styleable.MultiFunctionEditText_tipsMarginBottom, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        tipsTextSize = typedArray.getDimensionPixelSize(R.styleable.MultiFunctionEditText_tipsTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));

        hintTitleEnable = typedArray.getBoolean(R.styleable.MultiFunctionEditText_hintTitleEnable, false);
        hintTitle = typedArray.getString(R.styleable.MultiFunctionEditText_hintTitle);
        int hintTitleShowTypeValue = typedArray.getInt(R.styleable.MultiFunctionEditText_hintTitleShowType, HintTitleShowType.ALWAYS_SHOW.getValue());
        hintTitleShowType = HintTitleShowType.findByValue(hintTitleShowTypeValue);

        if (TextUtils.isEmpty(letterSpaceRule)) {
            letterSpaceRule = "3,4...";
        }
        typedArray.recycle();

        if (letterSpaceEnable) {
            enableLetterSpaceFunc();
        }

        if (!startZero) {
            removeTextChangedListener(noZeroStartWatcher);
            addTextChangedListener(noZeroStartWatcher);
        }

        if (onlyTextBold) {
            removeTextChangedListener(onlyTextBoldWatcher);
            addTextChangedListener(onlyTextBoldWatcher);
        }

        bottomLinePaint.setColor(bottomLineColor);
        tipsPaint.setColor(tipsColor);
        tipsPaint.setTextSize(tipsTextSize);

       resetDrawables();

       if(clearButtonEnable){
           removeTextChangedListener(clearButtonWatcher);
           addTextChangedListener(clearButtonWatcher);
           updateClearButtonVisible();
       }

       if(togglePasswordEnable){
           updatePasswordToggleButtonVisible();
       }

        this.post(new Runnable() {
            @Override
            public void run() {
                final TipsLayout tipsLayout = findTipsLayout();
                if (tipsLayout != null) {
                    tipsLayout.setHintTitleEnable(hintTitleEnable);

                    if (hintTitleShowType == HintTitleShowType.ALWAYS_SHOW) {
                        String hintTitleStr = hintTitle;
                        if (TextUtils.isEmpty(hintTitleStr)) {
                            hintTitleStr = getHint() == null ? null : getHint().toString();
                        }
                        tipsLayout.setHintTitle(hintTitleStr);
                    } else if (hintTitleShowType == HintTitleShowType.WHEN_FILL_SHOW) {
                        addTextChangedListener(new SimpleTextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (hintTitleEnable && hintTitleShowType == HintTitleShowType.WHEN_FILL_SHOW) {
                                    if (s != null && !s.toString().isEmpty()) {
                                        String hintTitleStr = hintTitle;
                                        if (TextUtils.isEmpty(hintTitleStr)) {
                                            hintTitleStr = getHint() == null ? null : getHint().toString();
                                        }
                                        tipsLayout.setHintTitle(hintTitleStr);
                                    } else {
                                        tipsLayout.setHintTitle(null);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void updateClearButtonVisible(){
        boolean needShowClearButton = isShowClearButton();
        boolean oldClearButtonVisible = extraDrawablesSrc[0] != null;
        if(needShowClearButton != oldClearButtonVisible){
            if(needShowClearButton){
                extraDrawablesSrc[0] = clearDrawable;
            }else {
                extraDrawablesSrc[0] = null;
            }
            resetDrawables();
        }
    }

    private void updatePasswordToggleButtonVisible(){
        boolean needShowPasswordToggleButton = isShowPasswordToggleButton();
        boolean needReset = false;
        boolean oldShowPasswordToggleButtonVisible = extraDrawablesSrc[1] != null;

        Drawable drawable = null;
        if(!oldShowPasswordToggleButtonVisible && !needShowPasswordToggleButton){ //以前 现在都不显示
            needReset = false;
        }else if(!oldShowPasswordToggleButtonVisible){//以前不显示 现在显示
            needReset = true;
            if (getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {//明文状态
                drawable = passwordVisibleIcon;
            } else if (getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                drawable = passwordInVisibleIcon;
            } else {
                drawable = null;
            }
        }else if(!needShowPasswordToggleButton){ //以前显示 现在不显示
            needReset = true;
            drawable = null;
        }else { //以前显示 现在也显示
            Drawable oldDrawable = extraDrawablesSrc[1];
            Drawable newDrawable = null;
            if (getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {//明文状态
                newDrawable = passwordVisibleIcon;
            } else if (getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                newDrawable = passwordInVisibleIcon;
            } else {
                newDrawable = null;
            }

            if(newDrawable != oldDrawable){
                needReset = true;
                drawable = newDrawable;
            }
        }

        if(needReset){
            extraDrawablesSrc[1] = drawable;
            resetDrawables();
        }
    }

    private void resetDrawables(){
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public boolean notInV(int value, int[] comVal) {
        for (int aComVal : comVal) {
            if (value == aComVal) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!letterSpaceEnable) {
            return;
        }
        if (s == null || s.length() == 0) {
            return;
        }
        int spaceStartPos[] = new int[letterSpaceGap.length];
        int position = 0;
        for (int i = 0; i < letterSpaceGap.length; i++) {
            int append;
            if (i == 0) {
                append = 0;
            } else {
                append = 1;
            }
            position = position + letterSpaceGap[i] + append;
            spaceStartPos[i] = position;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!notInV(i, spaceStartPos) || s.charAt(i) != ' ') {
                sb.append(s.charAt(i));
                for (int spaceStartPo : spaceStartPos) {
                    if (sb.length() == (spaceStartPo + 1) && sb.charAt(sb.length() - 1) != ' ') {
                        sb.insert(sb.length() - 1, ' ');
                    }
                }
            }
        }
        try {
            if (!sb.toString().equals(s.toString())) {
                int index = start + 1;
                if (sb.charAt(start) == ' ') {
                    if (before == 0) {
                        index++;
                    } else {
                        index--;
                    }
                } else {
                    if (before == 1) {
                        index--;
                    }
                }
                setText(sb.toString());
                setSelection(index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        updateClearButtonVisible(); //clearButton依赖于焦点是否获取
        updatePasswordToggleButtonVisible();
        if (focused) {//修正第一次设置值后光标位置
            setSelection(getText().length());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public Editable getText() {
        if (letterSpaceEnable) {
            if (proxyEditable == null) {
                proxyEditable = new ProxyEditable() {
                    @Override
                    public String toString() {
                        return super.toString().replace(" ", "");
                    }
                };
            }
            return proxyEditable.setSrc(super.getText());
        }
        return super.getText();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制底部线条和提示文字
        if (bottomLineEnable) {
            canvas.drawRect(0, getMeasuredHeight() - tipsNeedHeight, getMeasuredWidth() + getScrollX(), getMeasuredHeight() - tipsNeedHeight + bottomLineHeight, bottomLinePaint);
        }
        if (!TextUtils.isEmpty(tips) && tipsLayout != null) {
            canvas.save();
            canvas.translate(getScrollX(), getMeasuredHeight() - tipsLayout.getHeight() - tipsMarginBottom);
            tipsLayout.draw(canvas);
            canvas.restore();
        }
    }

    protected boolean isShowClearButton() {
        return isFocused() && getText() != null && getText().length() > 0 && clearButtonEnable && clearDrawable != null;
    }

    protected boolean isShowPasswordToggleButton() {
        return togglePasswordEnable && passwordInVisibleIcon != null && passwordVisibleIcon != null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                touchDownX = event.getX();
                touchDownY = event.getY();
                if (isInExtraDrawableRegion(touchDownX, touchDownY)) { //修复触发 粘帖 的bug
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (isShowClearButton()) {
                    boolean isTouchDownInClearRegion = isInClearDrawableRegion(touchDownX, touchDownY);
                    boolean isTouchUpInClearRegion = isInClearDrawableRegion(event.getX(), event.getY());
                    if (isTouchUpInClearRegion && isTouchDownInClearRegion) {
                        setText("");//清空数据
                        return false;
                    }
                }

                if (isShowPasswordToggleButton()) {
                    boolean isTouchDownInToggleRegion = isInToggleVisibleDrawableRegion(touchDownX, touchDownY);
                    boolean isTouchUpInToggleRegion = isInToggleVisibleDrawableRegion(event.getX(), event.getY());
                    if (isTouchDownInToggleRegion && isTouchUpInToggleRegion) {
                        togglePassword();
                        return false;
                    }
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public int getCompoundPaddingBottom() {
        return super.getCompoundPaddingBottom() + tipsNeedHeight;
    }

    private void togglePassword() {
        int selectionIndex = getSelectionStart();
        if (getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {//明文状态
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else if (getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        setSelection(selectionIndex);
        updatePasswordToggleButtonVisible();
    }

    private boolean isInClearDrawableRegion(float x, float y) {
        if (extraDrawablesSrc[0] != null) {
            Rect bounds = new Rect();
            bounds.top = getCompoundPaddingTop();
            bounds.left = getMeasuredWidth() - getCompoundPaddingRight() + extraDrawablesSrc[0].getBounds().left;
            bounds.right = bounds.left + extraDrawablesSrc[0].getBounds().width();
            bounds.bottom = getMeasuredHeight() - getCompoundPaddingBottom();
            return bounds.contains((int) x, (int) y);
        }
        return false;
    }

    private boolean isInExtraDrawableRegion(float x, float y) {
        Rect bounds = new Rect(extraDrawables.getBounds());
        bounds.top = getCompoundPaddingTop();
        bounds.left = getMeasuredWidth() - getCompoundPaddingRight();
        bounds.right = getMeasuredWidth();
        bounds.bottom = getMeasuredHeight() - getCompoundPaddingBottom();
        return bounds.contains((int) x, (int) y);
    }

    private boolean isInToggleVisibleDrawableRegion(float x, float y) {
        if (extraDrawablesSrc[1] != null) {
            Rect bounds = new Rect();
            bounds.top = getCompoundPaddingTop();
            bounds.left = getMeasuredWidth() - getCompoundPaddingRight() + extraDrawablesSrc[1].getBounds().left;
            bounds.right = bounds.left + extraDrawablesSrc[1].getBounds().width();
            bounds.bottom = getMeasuredHeight() - getCompoundPaddingBottom();
            return bounds.contains((int) x, (int) y);
        }
        return false;
    }

    private void enableLetterSpaceFunc() {
        isRepeatLastGap = letterSpaceRule.endsWith("...");
        String[] strs = letterSpaceRule.replace("...", "").split(",");
        letterSpaceGap = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            int gap = Integer.valueOf(strs[i]);
            letterSpaceGap[i] = gap;
        }

        removeTextChangedListener(this);
        addTextChangedListener(this);
    }

    private class HorizontalDrawables extends Drawable {
        private Drawable[] drawables;

        public HorizontalDrawables() {
        }

        public HorizontalDrawables(Drawable[] drawables) {
            this.drawables = drawables == null ? new Drawable[]{} : drawables;
        }

        public HorizontalDrawables setDrawables(Drawable[] drawables) {
            this.drawables = drawables;
            return this;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    drawable.draw(canvas);
                }
            }
        }

        @Override
        public void setBounds(int leftV, int topV, int rightV, int bottomV) {
            super.setBounds(leftV,topV,rightV,bottomV);
            Rect bounds = new Rect(leftV,topV,rightV,bottomV);
            int widthUsed = 0;
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    int left = bounds.left + widthUsed;
                    int top = bounds.centerY() - drawable.getIntrinsicHeight() / 2;
                    int right = left + drawable.getIntrinsicWidth();
                    int bottom = top + drawable.getIntrinsicHeight();
                    drawable.setBounds(left, top, right, bottom);
                    widthUsed += drawable.getIntrinsicWidth();
                    widthUsed += iconPadding;
                }
            }
        }

        @Override
        public int getIntrinsicWidth() {
            int width = 0;
            int validDrawableCount = 0;
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    width += drawable.getIntrinsicWidth();
                    validDrawableCount++;
                }
            }
            width += (validDrawableCount == 0 ? 0 : (validDrawableCount - 1) * iconPadding);
            return width;
        }

        @Override
        public int getIntrinsicHeight() {
            int height = 0;
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    height = Math.max(height, drawable.getIntrinsicHeight());
                }
            }
            return height;
        }

        @Override
        public void setAlpha(int i) {
            for (Drawable drawable : drawables) {
                if (drawable != null)
                    drawable.setAlpha(i);
            }
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            for (Drawable drawable : drawables) {
                if (drawable != null)
                    drawable.setColorFilter(colorFilter);
            }
        }

        @Override
        public int getOpacity() {
            return PixelFormat.UNKNOWN;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        tipsNeedHeight = 0;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!TextUtils.isEmpty(tips)) {
            tipsLayout = new StaticLayout(tips, tipsPaint, getMeasuredWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            tipsNeedHeight = tipsLayout.getHeight() + tipsMarginTop + tipsMarginBottom;
        } else {
            tipsNeedHeight = 0;
            tipsLayout = null;
        }

        if (bottomLineEnable) {
            tipsNeedHeight += bottomLineHeight;
        }
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + tipsNeedHeight);
    }

    public void setTips(String tips) {
        TipsLayout tipsLayout = findTipsLayout();
        if (tipsLayout != null) {
            tipsLayout.setTipsTextSize(tipsTextSize);
            tipsLayout.setTipsMarginTop(tipsMarginTop);
            tipsLayout.setTipsTextColor(tipsColor);
            tipsLayout.setTips(tips);
            return;
        }

        this.tips = tips;
        requestLayout();
    }

    public TipsLayout findTipsLayout() {
        if (this.getParent() == null || !(this.getParent() instanceof ViewGroup)) {
            return null;
        }

        ViewGroup parent = (ViewGroup) this.getParent();
        while (parent != null) {
            if (parent instanceof TipsLayout) {
                return (TipsLayout) parent;
            }

            if (parent.getParent() == null || !(parent.getParent() instanceof ViewGroup)) {
                return null;
            }
            parent = (ViewGroup) parent.getParent();
        }

        return null;
    }
}
