package com.example.pat.aapkatrade.Home.navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.Home.navigation.entity.SubCategory;
import com.example.pat.aapkatrade.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.res.StringArrayRes;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.X509TrustManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Netforce on 7/12/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener {

    private Context _context;
    private ArrayList<CategoryHome> _listDataHeader;

    private clickListner click;

    ProgressDialog _progressDialog;





    public ExpandableListAdapter(Context context, ArrayList<CategoryHome> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        _progressDialog = new ProgressDialog(context);

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataHeader.get(groupPosition).getSubCategoryList().get(childPosititon).subCategoryName;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, parent, false);
            Log.e("subcategorylist",this._listDataHeader.get(groupPosition).getSubCategoryList().get(childPosition).subCategoryName);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);



        txtListChild.setText(childText);
        final View finalConvertView = convertView;


        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                click.itemClicked(finalConvertView, groupPosition, childPosition);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getSubCategoryList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getCategoryName();
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        String headerTitle = (String) getGroup(groupPosition);

        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.list_group, parent, false);

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        final ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
       // imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);

        Ion.with(_context).load(_listDataHeader.get(groupPosition).getCategoryIconPath()).withBitmap().asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
                        if(result!=null) {

                                imageViewIcon.setImageBitmap(result);


                            //get random resource


                           TypedArray images = _context.getResources().obtainTypedArray(R.array.category_icon_background);
                            int choice = (int) (Math.random() * images.length());
                            imageViewIcon.setBackgroundResource(images.getResourceId(choice,R.drawable.circle_sienna));
                            images.recycle();


                        }
                    }
                });

        if(_listDataHeader.get(groupPosition).getSubCategoryList().size() != 0){
            Log.e("result_IN ADAPTER","null"+"****"+_listDataHeader.get(groupPosition).getSubCategoryList().size()+"88888");
        }

        if (_listDataHeader.get(groupPosition).getSubCategoryList().size() == 0) {
            imageView.setVisibility(View.GONE);
            final View finalConvertView = convertView;
            Log.e("result_IN ADAPTER==","null"+"****"+_listDataHeader.get(groupPosition).getSubCategoryList().size()+"88888");
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click.itemClicked(finalConvertView, groupPosition, 0);
                }
            });
        }

        else {
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);

//            if (isExpanded) {
//                imageView.setImageResource(R.drawable.ic_chevron_grey);
//                convertView.setBackgroundResource(R.color.navigation_child_color);
//               // notifyDataSetChanged();
//                Log.e("call notify","call notify");
//            } else {
//                imageView.setImageResource(R.drawable.ic_chevron_grey);
//               // notifyDataSetChanged();
//            }
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // callSubscribedwebservice();

    }

    public interface clickListner {
        void itemClicked(View view, int groupview, int childview);
    }

    public void setClickListner(clickListner click) {
        this.click = click;
    }



    private void ShowMessage(String message) {
        Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();

    }

    private static class Trust implements X509TrustManager {

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }

//    public  void setupSelfSSLCert() {
//        final Trust trust = new Trust();
//        final TrustManager[] trustmanagers = new TrustManager[]{trust};
//        SSLContext sslContext;
//        try {
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustmanagers, new SecureRandom());
//            Ion.getInstance(_context, "rest").getHttpClient().getSSLSocketMiddleware().setTrustManagers(trustmanagers);
//            Ion.getInstance(_context, "rest").getHttpClient().getSSLSocketMiddleware().setSSLContext(sslContext);
//        } catch (final NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (final KeyManagementException e) {
//            e.printStackTrace();
//        }
//    }
}