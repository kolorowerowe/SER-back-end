package com.github.ser.util;

import com.github.ser.model.database.*;
import com.github.ser.model.requests.ChangeCompanyDetailsRequest;
import com.github.ser.model.requests.ChangeSponsorshipPackageRequest;
import com.github.ser.model.requests.CreateEquipmentRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

@Log4j2
public class ModelUtils {

    private ModelUtils() {
        // private constructor
    }

    public static Company copyCompanyNonNullProperties(Company company, ChangeCompanyDetailsRequest changeCompanyDetailsRequest) throws InvocationTargetException, IllegalAccessException {

        String newContactPhone = changeCompanyDetailsRequest.getContactPhone();
        if (notEmpty(newContactPhone)) {
            company.setContactPhone(newContactPhone);
        }

        String newTaxId = changeCompanyDetailsRequest.getTaxId();
        if (notEmpty(newTaxId)) {
            company.setTaxId(newTaxId);
        }


        Address companyAddress = company.getAddress();
        if (changeCompanyDetailsRequest.getAddress() != null) {
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
        }

        if (changeCompanyDetailsRequest.getCatalogInformationRequest() != null) {
            CatalogInformation catalogInformation = company.getCatalogInformation();
            if (catalogInformation == null) {
                catalogInformation = CatalogInformation.builder()
                        .company(company)
                        .build();
            }

            BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
            notNull.copyProperties(catalogInformation, changeCompanyDetailsRequest.getCatalogInformationRequest());

            company.setCatalogInformation(catalogInformation);
        }


        return company;
    }

    public static SponsorshipPackage copySponsorshipPackageNonNullProperties(SponsorshipPackage sponsorshipPackage, ChangeSponsorshipPackageRequest changeSponsorshipPackageRequest) {

        if (changeSponsorshipPackageRequest.getPrices() != null) {
            sponsorshipPackage.getPrices().clear();
            sponsorshipPackage.getPrices().addAll(changeSponsorshipPackageRequest.getPrices());
        }

        if (changeSponsorshipPackageRequest.getTranslations() != null) {
            sponsorshipPackage.getTranslations().clear();
            sponsorshipPackage.getTranslations().addAll(changeSponsorshipPackageRequest.getTranslations());
        }

        Double newStandSize = changeSponsorshipPackageRequest.getStandSize();
        if (notEmpty(newStandSize)) {
            sponsorshipPackage.setStandSize(newStandSize);
        }

        Integer newMaxCompanies = changeSponsorshipPackageRequest.getMaxCompanies();
        if (notEmpty(newMaxCompanies)) {
            sponsorshipPackage.setMaxCompanies(newMaxCompanies);
        }

        Boolean newIsAvailable = changeSponsorshipPackageRequest.getIsAvailable();
        if (notEmpty(newIsAvailable)) {
            sponsorshipPackage.setIsAvailable(newIsAvailable);
        }


        return sponsorshipPackage;
    }


    public static Equipment copyEquipmentNonNullProperties(Equipment equipment, CreateEquipmentRequest createEquipmentRequest) {

        if (createEquipmentRequest.getPrices() != null) {
            equipment.getPrices().clear();
            equipment.getPrices().addAll(createEquipmentRequest.getPrices());
        }
        if (createEquipmentRequest.getTranslations() != null) {
            equipment.getTranslations().clear();
            equipment.getTranslations().addAll(createEquipmentRequest.getTranslations());
        }
        Integer newMaxCountPerCompany = createEquipmentRequest.getMaxCountPerCompany();
        if (notEmpty(newMaxCountPerCompany)) {
            equipment.setMaxCountPerCompany(newMaxCountPerCompany);
        }

        return equipment;
    }

    private static boolean notEmpty(String field) {
        return (field != null && !field.isEmpty());
    }

    private static boolean notEmpty(Double field) {
        return (field != null);
    }

    private static boolean notEmpty(Boolean field) {
        return (field != null);
    }

    private static boolean notEmpty(Integer field) {
        return (field != null);
    }
}
