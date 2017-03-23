package com.example.pat.aapkatrade.Home.registration.entity;

/**
 * Created by PPC09 on 27-Jan-17.
 */

public class BuyerRegistration {
    private String countryId;
    private String stateId;
    private String cityId;
    private String address ;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String userName;
    private String password;
    private String confirmPassword;
    private String clientId;

    public BuyerRegistration() {
    }

    public BuyerRegistration(String countryId, String stateId, String cityId, String address, String firstName, String lastName, String email, String mobile, String userName, String password, String confirmPassword, String clientId) {
        this.countryId = countryId;
        this.stateId = stateId;
        this.cityId = cityId;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.clientId = clientId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "BuyerRegistration{" +
                "countryId='" + countryId + '\'' +
                ", stateId='" + stateId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", address='" + address + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
