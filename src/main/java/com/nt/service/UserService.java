package com.nt.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nt.domain.UserAccount;

public interface UserService extends UserDetailsService{

	boolean saveUserAcc(UserAccount useracc);

	Map<Integer, String> getAllCountries();
	public Map<Integer,String> getCityByStateId(int stateId);
	public Map<Integer,String> getStateByCountryId(int countryId);
	public UserAccount getUserAccByTempPwd(String tempPwd,String email);
	public UserAccount findByEmailId(String email);	
	public boolean updateUserAccount(UserAccount useracc);
}