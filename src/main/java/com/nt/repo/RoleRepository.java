package com.nt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
