package com.example.pat.aapkatrade.general.Utils;

import android.graphics.Bitmap;
import android.os.Build;

/**
 * Created by PPC09 on 31-Jan-17.
 */

public class ImageUtils {
    public static int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            return data.getByteCount();
        } else{
            return data.getAllocationByteCount();
        }
    }

    public static Bitmap resize(Bitmap image, int maxHeight, int maxWidth) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

}
