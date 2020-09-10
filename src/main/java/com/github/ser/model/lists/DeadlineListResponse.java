package com.github.ser.model.lists;

import com.github.ser.model.database.Deadline;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DeadlineListResponse {
    private List<Deadline> deadlines;
    private int count;
}
