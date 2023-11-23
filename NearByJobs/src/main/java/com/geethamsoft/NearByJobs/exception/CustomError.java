package com.geethamsoft.NearByJobs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomError {
    private String message;
    private String field;
}
