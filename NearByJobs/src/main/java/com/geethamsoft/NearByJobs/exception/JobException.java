package com.geethamsoft.NearByJobs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class JobException {
    private final String message;
    private final Throwable trowable;
    private final HttpStatus httpStatus;

}
