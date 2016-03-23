package com.halfcup.wordview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hp on 2016/3/22 14:34.
 * Description:
 * 将单词转换为图片的
 */
public final class WordView extends View {
    private final String TAG = getClass().getSimpleName();

    private String word = "T";
    @ColorInt
    private int textColor;
    @ColorInt
    private int backgroundColor;
    private Paint mPaint;

    public WordView(Context context) {
        super(context);
        init(context, null);
    }

    public WordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WordView);

        backgroundColor = array.getColor(R.styleable.WordView_fillColor, Color.WHITE);
        textColor = array.getColor(R.styleable.WordView_wordColor, Color.BLACK);
        word = checkStr(array.getString(R.styleable.WordView_words));

        array.recycle();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw text
        if (!TextUtils.isEmpty(word)) {
            float radius = Math.min(canvas.getWidth(), canvas.getHeight()) / 2;

            mPaint.setColor(backgroundColor);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius, mPaint);


            mPaint.setColor(textColor);
            mPaint.setTextSize(radius * 8 / 10);
            canvas.drawText(word, canvas.getWidth() / 2, canvas.getHeight() / 2 - (mPaint.descent() + mPaint.ascent()) / 2, mPaint);
        }

    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    private String checkStr(String str) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim())) {
            return "";
        }
        return str.substring(0, 1);
    }
}
