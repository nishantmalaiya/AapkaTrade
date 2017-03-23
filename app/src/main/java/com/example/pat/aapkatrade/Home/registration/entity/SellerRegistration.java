package com.example.pat.aapkatrade.Home.registration.entity;

/**
 * Created by PPC09 on 25-Jan-17.
 */

public class SellerRegistration {
    private String businessType;
    private String companyName;
    private String shopName;
    private String firstName;
    private String lastName;
    private String DOB;
    private String mobile;
    private String email;
    private String password;
    private String confirmPassword;
    private String countryId;
    private String stateId;
    private String cityId;
    private String clientId;

    public SellerRegistration() {
    }

    public SellerRegistration(String businessType, String companyName, String shopName, String firstName, String lastName, String DOB, String mobile, String email, String password, String confirmPassword, String countryId, String stateId, String cityId, String clientId) {
        this.businessType = businessType;
        this.companyName = companyName;
        this.shopName = shopName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.countryId = countryId;
        this.stateId = stateId;
        this.cityId = cityId;
        this.clientId = clientId;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        setShopName(companyName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "SellerRegistration{" +
                "businessType='" + businessType + '\'' +
                ", companyName='" + companyName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", DOB='" + DOB + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", countryId='" + countryId + '\'' +
                ", stateId='" + stateId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
