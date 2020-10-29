package com.github.ser.model.statistics;


import com.github.ser.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatistics {

    private Integer allUsersCount;
    private List<OccurrenceInfo<Role>> roleOccurrenceList;


}

