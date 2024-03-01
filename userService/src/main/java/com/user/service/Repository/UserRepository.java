package com.user.service.Repository;

import com.user.service.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    List<User> findByNameContaining(String string);
}
