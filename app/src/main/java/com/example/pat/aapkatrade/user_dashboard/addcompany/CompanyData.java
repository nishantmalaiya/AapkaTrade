package com.example.pat.aapkatrade.user_dashboard.addcompany;

/**
 * Created by PPC09 on 16-Feb-17.
 */

public class CompanyData {
    private String companyName;
    private String companyId;

    public CompanyData() {
    }

    public CompanyData(String companyName, String companyId) {
        this.companyName = companyName;
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CompanyData{" +
                "companyName='" + companyName + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
