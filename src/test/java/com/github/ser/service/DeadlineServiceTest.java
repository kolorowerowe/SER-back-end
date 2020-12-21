package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.enums.DeadlineActivity;
import com.github.ser.exception.badRequest.InvalidDeadlinesException;
import com.github.ser.model.database.Deadline;
import com.github.ser.model.lists.DeadlineListResponse;
import com.github.ser.repository.DeadlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

import static com.github.ser.testutils.PopulateDatabase.populateDeadlineRepository;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Deadline Service tests")
class DeadlineServiceTest {

    @Autowired
    private DeadlineRepository deadlineRepository;

    private DeadlineService deadlineService;

    @BeforeEach
    void setup() {
        populateDeadlineRepository(deadlineRepository);

        deadlineService = new DeadlineService(deadlineRepository);
    }

    @Test
    @DisplayName("Get deadlines - success")
    void getDeadlines_success() {

        DeadlineListResponse deadlineListResponse = deadlineService.getAllDeadlines();

        assertAll(
                () -> assertEquals(5, deadlineListResponse.getCount())
        );
    }

    @Test
    @DisplayName("Save deadlines - success")
    void saveDeadlines_success() {

        DeadlineListResponse newDeadlines = DeadlineListResponse.builder()
                .deadlines(Arrays.asList(
                        Deadline.builder()
                                .activity(DeadlineActivity.FILL_COMPANY_DATA)
                                .deadlineDate("2020-11-01T10:00:00.000Z")
                                .orderNumber(1)
                                .build(),
                        Deadline.builder()
                                .activity(DeadlineActivity.CHOOSE_SPONSORSHIP_PACKAGE)
                                .deadlineDate("2020-11-02T10:00:00.000Z")
                                .orderNumber(2)
                                .build(),
                        Deadline.builder()
                                .activity(DeadlineActivity.CHOOSE_ADDITIONAL_EQUIPMENT)
                                .deadlineDate("2020-11-03T10:00:00.000Z")
                                .orderNumber(3)
                                .build(),
                        Deadline.builder()
                                .activity(DeadlineActivity.FILL_CATALOG_INFORMATION)
                                .deadlineDate("2020-11-04T10:00:00.000Z")
                                .orderNumber(4)
                                .build(),
                        Deadline.builder()
                                .activity(DeadlineActivity.SIGN_THE_CONTRACT)
                                .deadlineDate("2020-11-05T10:00:00.000Z")
                                .orderNumber(5)
                                .build()
                )).build();

        DeadlineListResponse deadlineListResponse = deadlineService.setAllDeadlines(newDeadlines);

        assertAll(
                () -> assertEquals(5, deadlineListResponse.getCount()),
                () -> assertEquals(DeadlineActivity.FILL_COMPANY_DATA, deadlineListResponse.getDeadlines().get(0).getActivity())
        );
    }

    @Test
    @DisplayName("Save deadlines - too less - throw Exception")
    void saveDeadlines_throwException() {

        DeadlineListResponse newDeadlines = DeadlineListResponse.builder()
                .deadlines(Collections.singletonList(Deadline.builder()
                        .activity(DeadlineActivity.FILL_COMPANY_DATA)
                        .deadlineDate("2020-11-01T10:00:00.000Z")
                        .orderNumber(1)
                        .build())
                ).build();

        assertThrows(InvalidDeadlinesException.class, () -> deadlineService.setAllDeadlines(newDeadlines));

    }
}