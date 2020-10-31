package com.github.ser.model.statistics;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PercentageProgress<T> {

    private T object;
    private Integer currentProgress;
    private Integer maxProgress;
}
