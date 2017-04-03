package com.example.pat.aapkatrade.ZoomImage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Tabletsize;
import com.example.pat.aapkatrade.general.customimageview.ZoomableImageView;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import static com.example.pat.aapkatrade.R.id.imageView;

/**
 * Created by PPC16 on 10-Feb-17.
 */




public class ZoomViewPagerAdapter extends PagerAdapter implements GestureDetector.OnDoubleTapListener
{

     Context mContext;
    ArrayList<String> imageurl = new ArrayList<>();


    public ZoomViewPagerAdapter(Context mContext, ArrayList<String>productImage_url)
    {

        this. imageurl=productImage_url;
        this.mContext=mContext;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }


    public int getCount()
    {

        return imageurl.size();

    }

    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    public Object instantiateItem(final ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.zoom_imageview, container, false);

        final ZoomableImageView imageView = (ZoomableImageView) itemView.findViewById(R.id.action_infolinks_splash);

        System.out.println("position============" + position);
        if(Tabletsize.isTablet(mContext))
        {
            String product_imageurl=imageurl.get(position).replace("small","large");

            Ion.with(imageView)
                    .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .load(product_imageurl);
            imageView.setMaxZoom(4f);
            Log.e("image_large","image_large");

        }
        else if(Tabletsize.isMedium(mContext))
        {
            String product_imageurl=imageurl.get(position).replace("small","medium");

            Ion.with(imageView)
                    .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .load(product_imageurl);
            Log.e("image_medium","image_medium");
            imageView.setMaxZoom(4f);

        }
        else if(Tabletsize.isSmall(mContext))
        {


            Ion.with(imageView)
                    .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .load(imageurl.get(position));
            imageView.setMaxZoom(4f);


            Log.e("image_small","image_small");
        }


//        int deviceWidthInPixels = mContext.getResources().getDisplayMetrics().widthPixels;
//        int deviceHeightInPixels = mContext.getResources().getDisplayMetrics().heightPixels;
//        imageView.getLayoutParams().width = deviceWidthInPixels;
//        imageView.getLayoutParams().height = deviceHeightInPixels;


//        Ion.with(imageView)
//                .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
//                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
//                .load(imageurl.get(position));
        // imageView.setImageResource(R.drawable.banner_home);

        container.addView(itemView);

//        imageView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent goto_zoom_imageview=new Intent(mContext,ZoomImageDialog.class);
//
//                goto_zoom_imageview.putStringArrayListExtra("imageurl",imageurl);
//                mContext.startActivity(goto_zoom_imageview);
//                /*imageView.setDrawingCacheEnabled(true);
//                imageView.buildDrawingCache();
//                final Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
//
//                Dialog normalDialog = new Dialog(mContext, R.style.DialogTheme);
//
//
//                normalDialog.setContentView(R.layout.zoom_image_dialog_layout);
//                CustomImageVIew img = (CustomImageVIew)normalDialog.findViewById(R.id.customImageVIew1);
//                img.setImageResource(R.drawable.banner_home);
//                img.setMaxZoom(4f);
//
//                normalDialog.show();*/
//
//
//
//
//
//
//              // ZoomImageDialog editNameDialogFragment = new ZoomImageDialog(mContext,bitmap);
////
////                editNameDialogFragment.show();
//
//                //showEditDialog(bitmap);
//
//
//            }
//        });
        return itemView;

    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
//    private void showEditDialog(Bitmap bitmap) {
//        FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
//
//ZoomImageDialog editNameDialogFragment = ZoomImageDialog.newInstance(bitmap);
//        editNameDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//        editNameDialogFragment.show(fm, "fragment_edit_name");
//    }


}