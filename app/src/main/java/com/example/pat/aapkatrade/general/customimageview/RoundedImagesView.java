package com.example.pat.aapkatrade.general.customimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedImagesView extends android.support.v7.widget.AppCompatImageView {
    public RoundedImagesView(Context context) {
        super(context);
    }

    public RoundedImagesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RoundedImagesView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null && getWidth() != 0 && getHeight() != 0) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap = bitmap.copy(Config.ARGB_4444, true);
                int width = getWidth();
                getHeight();
                bitmap = getCroppedBitmap(bitmap, width);
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                }
            }
        }
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        if (!(bitmap.getWidth() == i && bitmap.getHeight() == i)) {
            bitmap = Bitmap.createScaledBitmap(bitmap, i, i, false);
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_4444);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(((float) (bitmap.getWidth() / 2)) + 0.7f, ((float) (bitmap.getHeight() / 2)) + 0.7f, ((float) (bitmap.getWidth() / 2)) + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }
}
