package com.nt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

public UserEntity findByPasswordAndEmailId(String tempPwd,String emailId);

public UserEntity findByEmailId(String email); //for login module
} 
