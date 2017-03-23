package com.example.pat.aapkatrade.Home.navigation.entity;

/**
 * Created by PPC09 on 21-Jan-17.
 */

public class SubCategory {
    public String subCategoryId;
    public String subCategoryName;

    public SubCategory(String subCategoryId, String subCategoryName) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "subCategoryId='" + subCategoryId + '\'' +
                ", subCategoryName='" + subCategoryName + '\'' +
                '}';
    }
}
