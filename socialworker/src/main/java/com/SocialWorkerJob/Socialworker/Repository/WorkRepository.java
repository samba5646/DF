package com.SocialWorkerJob.Socialworker.Repository;

import com.SocialWorkerJob.Socialworker.Model.WorkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository  extends JpaRepository<WorkModel,Long> {

}
