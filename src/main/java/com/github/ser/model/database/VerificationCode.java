package com.github.ser.model.database;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.ser.util.DateTimeUtils.DATE_TIME_FORMAT;

@Data
@With
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "verification_code")
@Entity
public class VerificationCode {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID uuid;

    @NonNull
    private String code;

    private String userEmail;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime generationTime;

}
