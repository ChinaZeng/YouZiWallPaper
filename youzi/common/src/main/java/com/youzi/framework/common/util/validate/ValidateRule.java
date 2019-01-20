package com.youzi.framework.common.util.validate;

import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.youzi.framework.common.Config;
import com.youzi.framework.common.error.ValidateException;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by LuoHaifeng on 2018/4/24 0024.
 * Email:496349136@qq.com
 */
public abstract class ValidateRule implements Function<Boolean, Boolean> {
    enum Mode{
        MODE_NORMAL,
        MODE_VIEW
    }

    protected View view;
    protected boolean trim;
    protected Mode mode = Mode.MODE_NORMAL;

    public ValidateRule() {
    }

    public ValidateRule(View view, boolean trim) {
        this.view = view;
        this.trim = trim;
        this.mode = Mode.MODE_VIEW;
    }

    public ValidateRule or(ValidateRule rule) {
        return OrRule.instance(this, rule);
    }

    public ValidateRule and(ValidateRule rule) {
        return AndRule.instance(this, rule);
    }

    public boolean wrap() {
        try {
            return this.apply(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void wrap(String message) throws Exception {
        if (!this.apply(true)) {
            throw new ValidateException(message);
        }
    }

    public void wrap(@StringRes int messageResId) throws Exception {
        if (!this.apply(true)) {
            throw new ValidateException(Config.getAppContext().getString(messageResId));
        }
    }

    public void wrap(final String message, Consumer<Boolean> success) {
        QValidator
                .validate(new QValidator.OnValidate() {
                    @Override
                    public void validate() throws Exception {
                        ValidateRule.this.wrap(message);
                    }
                }, success);
    }

    public void wrap(final String message, Consumer<Boolean> success, Consumer<Throwable> error) {
        QValidator
                .validate(new QValidator.OnValidate() {
                    @Override
                    public void validate() throws Exception {
                        ValidateRule.this.wrap(message);
                    }
                }, success, error);
    }

    public void wrap(final int messageResId, Consumer<Boolean> success) {
        QValidator
                .validate(new QValidator.OnValidate() {
                    @Override
                    public void validate() throws Exception {
                        ValidateRule.this.wrap(messageResId);
                    }
                }, success);
    }

    public void wrap(final int messageResId, Consumer<Boolean> success, Consumer<Throwable> error) {
        QValidator
                .validate(new QValidator.OnValidate() {
                    @Override
                    public void validate() throws Exception {
                        ValidateRule.this.wrap(messageResId);
                    }
                }, success, error);
    }

    protected View getView() {
        return view;
    }

    protected ValidateRule setView(View view) {
        this.view = view;
        return this;
    }

    protected boolean isTrim() {
        return trim;
    }

    protected ValidateRule setTrim(boolean trim) {
        this.trim = trim;
        return this;
    }

    protected Mode getMode() {
        return mode;
    }

    protected ValidateRule setMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    protected IllegalArgumentException newNotSupportModeException(){
        return new IllegalArgumentException("not support this validate mode");
    }

    protected IllegalArgumentException newNotSupportViewTypeException(){
        return new IllegalArgumentException("not support this view");
    }

    protected String getTextByView(String normalValue) throws Exception{
        String text;
        if(mode == Mode.MODE_NORMAL){
            text = normalValue;
        }else if(mode == Mode.MODE_VIEW){
            if(view == null || !(view instanceof TextView)){
                throw newNotSupportViewTypeException();
            }

            TextView textView = (TextView) view;
            text = textView.getText().toString();
            if(isTrim()){
                text = text.trim();
            }
        }else {
            throw newNotSupportModeException();
        }

        return text;
    }

    protected double getDoubleValueByView(double normalValue) throws Exception{
        double value;
        if(mode == Mode.MODE_NORMAL){
            value = normalValue;
        }else if(mode == Mode.MODE_VIEW){
            if(view == null || !(view instanceof TextView)){
                throw newNotSupportViewTypeException();
            }

            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            if(isTrim()){
                text = text.trim();
            }
            value = Double.valueOf(text);
        }else {
            throw newNotSupportModeException();
        }

        return value;
    }
}
