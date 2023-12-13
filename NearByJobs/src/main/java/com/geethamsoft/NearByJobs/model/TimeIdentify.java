package com.geethamsoft.NearByJobs.model;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TimeIdentify {
    public static String formatTimeAgo(LocalDateTime pastTime) {
        if (pastTime == null) {
            return "Unknown";
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(pastTime, currentTime);

        StringBuilder result = new StringBuilder();

        if (duration.toDays() > 365) {
            long years = duration.toDays() / 365;
            result.append(years).append(" year").append(years > 1 ? "s" : "");
        } else if (duration.toDays() > 30) {
            long months = duration.toDays() / 30;
            result.append(months).append(" month").append(months > 1 ? "s" : "");
        } else if (duration.toDays() > 7) {
            long weeks = duration.toDays() / 7;
            result.append(weeks).append(" week").append(weeks > 1 ? "s" : "");
        } else if (duration.toDays() > 1) {
            result.append(duration.toDays()).append(" day").append(duration.toDays() > 1 ? "s" : "");
        } else if (duration.toHours() > 1) {
            result.append(duration.toHours()).append(" hr").append(duration.toHours() > 1 ? "s" : "");
        } else if (duration.toMinutes() > 1) {
            result.append(duration.toMinutes()).append(" min").append(duration.toMinutes() > 1 ? "s" : "");
        } else {
            return "just now";
        }

        return result.append(" ago").toString();
    }}
