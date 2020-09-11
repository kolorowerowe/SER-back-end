package com.github.ser.model.lists;

import com.github.ser.model.response.CompanyResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CompanyListResponse {
    private List<CompanyResponse> companyList;
    private int count;
}
