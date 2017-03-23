package com.example.pat.aapkatrade.Home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Netforce on 7/25/2016.
 */
public class CommomData implements Parcelable{
    public String imageurl,price,name,id,product_location, categoryName;
    public CommomData(String id, String name, String price, String imageurl){
        this.id=id;
        this.imageurl=imageurl;
        this.price=price;
        this.name=name;

    }
    public CommomData(String id, String name, String price, String imageurl,String product_location){
        this.id=id;
        this.imageurl=imageurl;
        this.price=price;
        this.name=name;
        this.product_location=product_location;
    }

    public CommomData(String id, String name, String price, String imageurl,String product_location, String categoryName){
        this.id=id;
        this.imageurl=imageurl;
        this.price=price;
        this.name=name;
        this.product_location=product_location;
        this.categoryName = categoryName;
    }

    protected CommomData(Parcel in) {
        imageurl = in.readString();
        price = in.readString();
        name = in.readString();
        id = in.readString();
        product_location=in.readString();

    }

    public static final Creator<CommomData> CREATOR = new Creator<CommomData>() {
        @Override
        public CommomData createFromParcel(Parcel in) {
            return new CommomData(in);
        }

        @Override
        public CommomData[] newArray(int size) {
            return new CommomData[size];
        }
    };

    @Override
    public String toString() {
        return "CommomData{" +
                "imageurl='" + imageurl + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageurl);
        dest.writeString(price);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(product_location);
    }
}
