package com.example.pat.aapkatrade.Home.registration.entity;

/**
 * Created by PPC09 on 30-Jan-17.
 */

public class BusinessAssociateRegistration {
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String mobile_no;
    private String dob;
    private String address;
    private String stateID;
    private String cityID;
    private String pinCode;
    private boolean isAgreementAccepted;
    private String qualification;
    private String totalExperience;
    private String relaventExperience;
    private String bankName;
    private String accountNumber;
    private String branchCode;
    private String ifscCode;
    private String micrCode;
    private String accountHolderName;
    private String registeredMobileWithBank;
    private String branchName;

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

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isAgreementAccepted() {
        return isAgreementAccepted;
    }

    public void setAgreementAccepted(boolean agreementAccepted) {
        isAgreementAccepted = agreementAccepted;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(String totalExperience) {
        this.totalExperience = totalExperience;
    }

    public String getRelaventExperience() {
        return relaventExperience;
    }

    public void setRelaventExperience(String relaventExperience) {
        this.relaventExperience = relaventExperience;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getRegisteredMobileWithBank() {
        return registeredMobileWithBank;
    }

    public void setRegisteredMobileWithBank(String registeredMobileWithBank) {
        this.registeredMobileWithBank = registeredMobileWithBank;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "BusinessAssociateRegistration{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", stateID='" + stateID + '\'' +
                ", cityID='" + cityID + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", isAgreementAccepted=" + isAgreementAccepted +
                ", qualification='" + qualification + '\'' +
                ", totalExperience='" + totalExperience + '\'' +
                ", relaventExperience='" + relaventExperience + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", micrCode='" + micrCode + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", registeredMobileWithBank='" + registeredMobileWithBank + '\'' +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
