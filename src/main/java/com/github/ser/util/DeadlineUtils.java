package com.github.ser.util;

import com.github.ser.enums.DeadlineActivity;
import com.github.ser.exception.badRequest.InvalidDeadlinesException;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.Deadline;

import java.util.List;

public class DeadlineUtils {

    private DeadlineUtils() {
        // private constructor
    }

    public static void validateDeadlines(List<Deadline> deadlines) {
        if (deadlines.size() != 5) {
            throw new InvalidDeadlinesException("Expected 5 deadlines, " + deadlines.size() + " provided");
        }
    }

    public static boolean isActivityDone(DeadlineActivity activity, Company company) {

        switch (activity) {
            case FILL_COMPANY_DATA:
                return company.getAddress() != null;

            case CHOOSE_SPONSORSHIP_PACKAGE:
                return company.getSponsorshipPackage() != null;

            case CHOOSE_ADDITIONAL_EQUIPMENT:
                //TODO 11/09/2020: fill this
               return false;

            case FILL_CATALOG_INFORMATION:
                return company.getCatalogInformation() != null;

            case SIGN_THE_CONTRACT:
                //TODO 11/09/2020: fill this
                return false;

            default:
                return false;
        }
    }
}
