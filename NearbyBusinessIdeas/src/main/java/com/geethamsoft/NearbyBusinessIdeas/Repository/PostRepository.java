package com.geethamsoft.NearbyBusinessIdeas.Repository;

import com.geethamsoft.NearbyBusinessIdeas.Entity.PostBusinessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostBusinessModel,Long> {
}
