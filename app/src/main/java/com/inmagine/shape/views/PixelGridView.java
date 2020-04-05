package com.inmagine.shape.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.inmagine.shape.R;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImage3x3ConvolutionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;

public class PixelGridView extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private Paint blackPaint = new Paint();
    private boolean[][] cellChecked;
    private Drawable mCustomImage;
    Context mContext;
    GPUImage gpuImage;
    public enum TYPE {
        DEFAULT, ALPHA_BLEND_FILTER, HUE_FILTER, BULDGE_DISTORTION
    };

    private TYPE currentType;


    public PixelGridView(Context context) {
        this(context, null);
        mContext = context;
        gpuImage = new GPUImage(mContext);
        currentType = TYPE.DEFAULT;

        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(3);
    }

    public PixelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        gpuImage = new GPUImage(mContext);
        currentType = TYPE.DEFAULT;

        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(3);

        mCustomImage = context.getResources().getDrawable(R.drawable.download);
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }

    public void setHueFilter(){
        this.currentType = TYPE.HUE_FILTER;
    }

    public void setAlphaBlendFilter(){
        this.currentType = TYPE.ALPHA_BLEND_FILTER;
    }

    public void setBuldgeDistortionFilter(){
        this.currentType = TYPE.BULDGE_DISTORTION;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getMeasuredWidth() / numColumns;
        cellHeight = getMeasuredHeight() / numRows;

        cellChecked = new boolean[numColumns][numRows];

        invalidate();
    }



    public static Bitmap drawableToBitmap (Drawable drawable, int width, int height) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (numColumns == 0 || numRows == 0) {
            return;
        }

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                int padding = 10;

                Rect rect = new Rect();

                rect.set(
                        i * cellWidth + padding,
                        j * cellHeight + padding,
                        (i + 1) * cellWidth - padding,
                        (j+1) *cellHeight - padding
                );


                if(i == 0 && j == 0){
                    Bitmap bitmap = drawableToBitmap(mCustomImage, cellWidth, cellHeight);

                    switch(currentType){
                        case ALPHA_BLEND_FILTER:

                            gpuImage.setFilter(new GPUImageAlphaBlendFilter());
                            bitmap = gpuImage.getBitmapWithFilterApplied(bitmap);
                            break;

                        case HUE_FILTER:

                            gpuImage.setFilter(new GPUImageHueFilter());
                            bitmap = gpuImage.getBitmapWithFilterApplied(bitmap);
                            break;

                        case BULDGE_DISTORTION:

                            gpuImage.setFilter(new GPUImageBulgeDistortionFilter());
                            bitmap = gpuImage.getBitmapWithFilterApplied(bitmap);
                            break;
                    }

                    canvas.drawBitmap(bitmap, rect, rect, blackPaint);


                }

                canvas.drawRect(rect,
                        blackPaint);




            }
        }


    }


}