package com.github.ser.service;

import com.github.ser.enums.Role;
import com.github.ser.model.database.User;
import com.github.ser.model.lists.SponsorshipPackageListResponse;
import com.github.ser.model.lists.UserListResponse;
import com.github.ser.model.response.SponsorshipPackageResponse;
import com.github.ser.model.statistics.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class StatisticsService {

    private final CompanyService companyService;
    private final EquipmentService equipmentService;
    private final SponsorshipPackageService sponsorshipPackageService;
    private final UserService userService;

    public StatisticsService(CompanyService companyService, EquipmentService equipmentService, SponsorshipPackageService sponsorshipPackageService, UserService userService) {
        this.companyService = companyService;
        this.equipmentService = equipmentService;
        this.sponsorshipPackageService = sponsorshipPackageService;
        this.userService = userService;
    }

    public Statistics getStatistics() {
        return Statistics.builder()
                .userStatistics(getUserStatistics())
                .sponsorshipPackageStatistics(getSponsorshipPackageStatistics())
                .build();
    }

    public UserStatistics getUserStatistics() {
        UserListResponse userListResponse = userService.getAllUsers();

        List<OccurrenceInfo<Role>> roleOccurrenceList = userListResponse.getUsers().stream()
                .map(User::getRole)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream().map(
                        roleLongEntry -> new OccurrenceInfo<>(roleLongEntry.getKey(), roleLongEntry.getValue())
                )
                .sorted(Comparator.comparingLong(OccurrenceInfo::getOccurrences))
                .collect(Collectors.toList());

        return UserStatistics.builder()
                .allUsersCount(userListResponse.getCount())
                .roleOccurrenceList(roleOccurrenceList)
                .build();
    }

    public SponsorshipPackageStatistics getSponsorshipPackageStatistics() {
        SponsorshipPackageListResponse sponsorshipPackageListResponse = sponsorshipPackageService.getAllSponsorshipPackages();

        List<PercentageProgress<SponsorshipPackageResponse>> progressList = sponsorshipPackageListResponse.getSponsorshipPackageList()
                .stream()
                .sorted(Comparator.comparing(SponsorshipPackageResponse::getMaxCompanies))
                .map(sponsorshipPackageResponse -> PercentageProgress.<SponsorshipPackageResponse>builder()
                        .object(sponsorshipPackageResponse)
                        .currentProgress(sponsorshipPackageResponse.getCurrentCompanies())
                        .maxProgress(sponsorshipPackageResponse.getMaxCompanies())
                        .build())
                .collect(Collectors.toList());

        return SponsorshipPackageStatistics.builder()
                .allSPCount(sponsorshipPackageListResponse.getCount())
                .percentageProgressesSP(progressList)
                .build();

    }
}
