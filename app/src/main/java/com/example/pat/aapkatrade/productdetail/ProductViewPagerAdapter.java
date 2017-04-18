package com.example.pat.aapkatrade.productdetail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.ZoomImage.ZoomImageDialog;
import com.example.pat.aapkatrade.ZoomImage.ZoomViewPagerAdapter;
import com.example.pat.aapkatrade.general.CustomImageVIew;
import com.example.pat.aapkatrade.general.Tabletsize;
import com.koushikdutta.ion.Ion;
import com.theartofdev.fastimageloader.target.TargetImageView;

import java.util.ArrayList;

/**
 * Created by PPC16 on 10-Feb-17.
 */




public class ProductViewPagerAdapter extends PagerAdapter
{

    Context mContext;
    ArrayList<String> imageurl = new ArrayList<>();


    public ProductViewPagerAdapter(Context mContext, ArrayList<String>productImage_url)
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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_product_detail, container, false);

//        final ImageView imageView = (ImageView) itemView.findViewById(R.id.img_viewpager_detail);
        final TargetImageView imageView = (TargetImageView) itemView.findViewById(R.id.img_viewpager_detail);

        System.out.println("position============" + position);
        if(Tabletsize.isTablet(mContext))
        {
            String product_imageurl=imageurl.get(position).replace("small","large");

            Ion.with(imageView)
                    .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .load(product_imageurl);
            Log.e("image_large","image_large");

        }
        else if(Tabletsize.isMedium(mContext))
        {
            String product_imageurl=imageurl.get(position).replace("small","medium");

//            Ion.with(imageView)
//                    .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
//                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
//                    .load(product_imageurl);

            Log.e("image_medium","image_medium");

        }
        else if(Tabletsize.isSmall(mContext))
        {


            Ion.with(imageView)
                    .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                    .load(imageurl.get(position));

            Log.e("image_small","image_small");
        }


//        Ion.with(imageView)
//                .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
//                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
//                .load(imageurl.get(position));
        // imageView.setImageResource(R.drawable.banner_home);

        container.addView(itemView);

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //ZoomImageDialog zoomImageDialog=new ZoomImageDialog();
                Intent goto_zoomimageview=new Intent(mContext,ZoomImageDialog.class);
                goto_zoomimageview.putStringArrayListExtra("imageurl",imageurl);

                mContext.startActivity(goto_zoomimageview);
//                imageView.setDrawingCacheEnabled(true);
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
//                normalDialog.show();






                // ZoomImageDialog editNameDialogFragment = new ZoomImageDialog(mContext,bitmap);
//
//                editNameDialogFragment.show();

                //showEditDialog(bitmap);


            }
        });
        return itemView;

    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
//    private void showEditDialog(Bitmap bitmap) {
//        FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
//
//ZoomImageDialog editNameDialogFragment = ZoomImageDialog.newInstance(bitmap);
//        editNameDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//        editNameDialogFragment.show(fm, "fragment_edit_name");
//    }


}