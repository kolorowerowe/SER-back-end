package com.github.ser.model.statistics;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OccurrenceInfo<T> {

    private T object;
    private Long occurrences;
}
