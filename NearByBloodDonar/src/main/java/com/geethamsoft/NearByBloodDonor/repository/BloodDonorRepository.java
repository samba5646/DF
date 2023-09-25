package com.geethamsoft.NearByBloodDonor.repository;

import com.geethamsoft.NearByBloodDonor.model.BloodDonor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodDonorRepository extends MongoRepository<BloodDonor, String> {
    List<BloodDonor> findByBloodType(String bloodType);

    List<BloodDonor> findByLocation(String location);

    List<BloodDonor> findByAvailability(String availability);

    List<BloodDonor> findByLastDonationDateAfter(String lastDonationDate);

    List<BloodDonor> findByAgeBetween(int minAge, int maxAge);

    List<BloodDonor> findByGender(String gender);

    List<BloodDonor> findBySmokingHabit(String smokingHabit);

    List<BloodDonor> findByDrinkingHabit(String drinkingHabit);

    List<BloodDonor> findByDonationCost(String donationCost);

    List<BloodDonor> findByDonationAmountBetween(double costRangeMin, double costRangeMax);

    // Add more custom query methods as needed

    // Example: Find blood donors with a specific blood type
    List<BloodDonor> findByBloodTypeAndLocationAndAvailabilityAndLastDonationDateAfterAndAgeBetweenAndGenderAndSmokingHabitAndDrinkingHabitAndDonationCostAndDonationAmountBetween(
            String bloodType, String location, String availability, String lastDonationDate,
            int minAge, int maxAge, String gender, String smokingHabit, String drinkingHabit,
            String donationCost, double costRangeMin, double costRangeMax
    );

    // Example: Find blood donors with specific languages spoken
    // List<BloodDonor> findByLanguagesSpokenIn(List<String> languages);

    // Example: Find blood donors with a website URL
    // List<BloodDonor> findByWebsiteUrlNotNull();

    // Example: Find blood donors by preferred contact method
    // List<BloodDonor> findByPreferredContactMethod(String preferredContactMethod);
}
