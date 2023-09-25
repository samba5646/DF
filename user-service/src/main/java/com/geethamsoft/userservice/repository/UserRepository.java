package com.geethamsoft.userservice.repository;


import com.geethamsoft.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    User findByGoogleId(String googleId);
    User findByFacebookId(String facebookId);
}
