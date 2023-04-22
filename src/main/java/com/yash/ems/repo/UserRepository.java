package com.yash.ems.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yash.ems.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
}
