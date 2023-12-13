package com.geethamsoft.NearByJobs.model;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class TimeIdentify {

    public static String formatTimeAgo(LocalDateTime pastTime) {
        if (pastTime == null) {
            return "Unknown";
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(pastTime, currentTime);

        StringBuilder result = new StringBuilder();

        if (duration.toDays() > 1) {
            result.append(duration.toDays()).append(" days");
        } else if (duration.toDays() == 1) {
            result.append("1 day");
        } else if (duration.toHours() > 1) {
            result.append(duration.toHours()).append(" hrs");
        } else if (duration.toHours() == 1) {
            result.append("1 hr");
        } else if (duration.toMinutes() > 1) {
            result.append(duration.toMinutes()).append(" mins");
        } else if (duration.toMinutes() == 1) {
            result.append("1 min");
        } else {
            return "just now";
        }

        return result.append(" ago").toString();
    }
}
