package com.SocialWorkerJob.Socialworker.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="socialWorkerjob")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name should not be blank")
    private String name;
    @NotBlank(message = "Location should not be blank")
    private String location;

    private String availability;

    private String experience_Level;
    @NotBlank(message = "Specialisation should not be blank")
    private String specialisation;

    private String workType;
    @NotNull(message = "Charge Amount should not be null")
    private Double chargeAmount;
    @Min(value = 18, message = "Age should not be less than 18")
    private int age;
    @NotBlank(message = "Gender should not be blank")
    private String gender;
    private String language_known;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date_Created;

}
