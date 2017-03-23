package com.example.pat.aapkatrade.general;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by PPC21 on 23-Jan-17.
 */

public class TypefaceUtil {

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
          //  Log.e("working  " ,""+ customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        } catch (Exception e) {
            Log.e("Can  " ,""+ customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }


}
