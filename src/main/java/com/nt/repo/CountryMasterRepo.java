package com.nt.repo;


import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.CountryMasterEntity;

public interface CountryMasterRepo extends JpaRepository<CountryMasterEntity, Serializable> {
}
