package com.github.ser.util;

import com.github.ser.model.database.Address;
import com.github.ser.model.database.Company;
import com.github.ser.model.requests.ChangeCompanyDetailsRequest;

public class ModelUtils {

    private ModelUtils(){
        // private constructor
    }

    public static Company copyCompanyNonNullProperties(Company company, ChangeCompanyDetailsRequest changeCompanyDetailsRequest){

        String newContactPhone = changeCompanyDetailsRequest.getContactPhone();
        if (notEmpty(newContactPhone)) {
            company.setContactPhone(newContactPhone);
        }

        String newTaxId= changeCompanyDetailsRequest.getTaxId();
        if (notEmpty(newTaxId)) {
            company.setTaxId(newTaxId);
        }


        Address companyAddress = company.getAddress();

        String newStreet = changeCompanyDetailsRequest.getAddress().getStreet();
        if (notEmpty(newStreet)) {
            companyAddress.setStreet(newStreet);
        }

        String newBuildingNumber = changeCompanyDetailsRequest.getAddress().getBuildingNumber();
        if (notEmpty(newBuildingNumber)) {
            companyAddress.setBuildingNumber(newBuildingNumber);
        }

        String newFlatNumber = changeCompanyDetailsRequest.getAddress().getFlatNumber();
        companyAddress.setFlatNumber(newFlatNumber);


        String newCity = changeCompanyDetailsRequest.getAddress().getCity();
        if (notEmpty(newCity)) {
            companyAddress.setCity(newCity);
        }

        String newPostalCode = changeCompanyDetailsRequest.getAddress().getPostalCode();
        if (notEmpty(newPostalCode)) {
            companyAddress.setPostalCode(newPostalCode);
        }

        company.setAddress(companyAddress);

        return company;
    }

    private static boolean notEmpty(String field){
        return (field != null && !field.isEmpty());
    }
}
