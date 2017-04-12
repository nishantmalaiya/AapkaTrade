package com.example.pat.aapkatrade.Home.navigation.entity;

import android.support.annotation.NonNull;

import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;

import java.util.ArrayList;

/**
 * Created by PPC09 on 20-Jan-17.
 */

public class CategoryHome {
    private String categoryId;
    private String categoryName;
    private String categoryIconName;
    private String categoryIconPath;
    private ArrayList<SubCategory> subCategoryList;
    private String basePath = AndroidUtils.BaseUrl + "/public/appicon/";
    private String iconExtention = ".png";

    public CategoryHome(String categoryId, String categoryName, String categoryIconName, ArrayList<SubCategory> subCategoryList) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryIconName = categoryIconName;
        this.subCategoryList = subCategoryList;
        setCategoryIconPath(createIconPath(categoryName));
    }

    private String createIconPath(String iconName) {
        return Validation.isNonEmptyStr(iconName) ? basePath + iconName.replaceAll(" |/|&", "") + iconExtention : "";
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryIconName() {
        return categoryIconName;
    }

    public String getCategoryIconPath() {
        return categoryIconPath;
    }

    public void setCategoryIconPath(String categoryIconPath) {
        this.categoryIconPath = categoryIconPath;
    }

    public ArrayList<SubCategory> getSubCategoryList() {
        return this.subCategoryList;
    }


    @Override
    public String toString() {
        return "CategoryHome{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryIconName='" + categoryIconName + '\'' +
                ", categoryIconPath='" + categoryIconPath + '\'' +
                ", subCategoryList=" + subCategoryList +
                ", basePath='" + basePath + '\'' +
                ", iconExtention='" + iconExtention + '\'' +
                '}';
    }

}
