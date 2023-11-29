package com.geethamsoft.SocialWorker.Repository;

import com.geethamsoft.SocialWorker.Model.WorkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository  extends JpaRepository<WorkModel,Long> {

}
