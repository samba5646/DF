package com.geethamsoft.NearByTechniciansAndNonTechnicians.repository;

import com.geethamsoft.NearByTechniciansAndNonTechnicians.model.NearByTechniciansAndNonTechnicians;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NearByTechniciansAndNonTechniciansRepository extends MongoRepository<NearByTechniciansAndNonTechnicians, String> {


}
