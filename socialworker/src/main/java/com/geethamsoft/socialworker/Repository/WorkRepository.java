package com.geethamsoft.socialworker.Repository;

import com.geethamsoft.socialworker.Model.WorkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository  extends JpaRepository<WorkModel,Long> {

}
