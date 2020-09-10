package com.github.ser.model.lists;

import com.github.ser.model.database.Company;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CompanyListResponse {
    private List<Company> companyList;
    private int count;
}
