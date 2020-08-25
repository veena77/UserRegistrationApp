package com.nt.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.constant.ConstantValues;
import com.nt.domain.UserAccount;
import com.nt.entity.CityMasterEntity;
import com.nt.entity.CountryMasterEntity;
import com.nt.entity.Role;
import com.nt.entity.StateMasterEntity;
import com.nt.entity.UserEntity;
import com.nt.repo.CityMasterRepo;
import com.nt.repo.CountryMasterRepo;
import com.nt.repo.StateMasterRepo;
import com.nt.repo.UserRepository;
import com.nt.util.EmailUtils;
import com.nt.util.PasswordUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repo;

	@Autowired
	private CountryMasterRepo cmrepo;
	
	@Autowired
	private StateMasterRepo smrepo;
	
	@Autowired
	private CityMasterRepo cityrepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public boolean saveUserAcc(UserAccount useracc) {
		useracc.setStatus(ConstantValues.LOCKED_STR);
		useracc.setPassword(PasswordUtil.encryptPwd(PasswordUtil.generateTempPassword(ConstantValues.LENGTH_OF_PWD)) );
		
		Arrays.asList(new Role("USER"));
		UserEntity entity=new UserEntity();
		BeanUtils.copyProperties(useracc, entity);
		UserEntity savedEntity=repo.save(entity);
		
		if(savedEntity.getUserId()!=0) {		
		return emailUtils.sendUserAccUnlockEmail(useracc);
		}
		return false;
	}

	
	@Override
	public Map<Integer, String> getAllCountries() {
	
		Map<Integer,String> map=new LinkedHashMap<Integer, String>();
		List<CountryMasterEntity> countryList=cmrepo.findAll();
		countryList.forEach(countryMaster->{
			map.put(countryMaster.getCountryId(), countryMaster.getCountryName());
		});
		
		return map;
	}

	public Map<Integer,String> getStateByCountryId(int countryId){
		
		Map<Integer,String> map=new LinkedHashMap<Integer, String>();
		List<StateMasterEntity> states=smrepo.findAllByCountryId(countryId);
		states.forEach(state->{
			map.put(state.getStateId(), state.getStateName());
		});
		return map;
	}
	
	public Map<Integer,String> getCityByStateId(int stateId){
		Map<Integer,String> map=new LinkedHashMap<Integer, String>();
		List<CityMasterEntity> cities=cityrepo.findAllByStateId(stateId);
		cities.forEach(city->{
			map.put(city.getCityId(), city.getCityName());
		});
		return map;
		}
	@Override
	public UserAccount getUserAccByTempPwd(String tempPwd,String email) {
		UserEntity userEntity=repo.findByPasswordAndEmailId(tempPwd,email);
		UserAccount userAcc=null;
		if(userEntity!=null) {
			userAcc=new UserAccount();
			BeanUtils.copyProperties(userEntity, userAcc);
		}
		
		return userAcc;
	}
	@Override
	public boolean updateUserAccount(UserAccount useracc) {

UserEntity entity=new UserEntity();
BeanUtils.copyProperties(useracc, entity);
UserEntity savedEntity=repo.save(entity);
		return savedEntity!=null;
	}


	@Override
	public UserAccount findByEmailId(String email) {
		// TODO Auto-generated method stub
		UserEntity userEntity= repo.findByEmailId(email);
		UserAccount userAcc=null;
		if(userEntity!=null) {
			userAcc=new UserAccount();
			BeanUtils.copyProperties(userEntity, userAcc);
		}
		
		return userAcc;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user= repo.findByEmailId(username);
		// TODO Auto-generated method stub
		if(user==null)
			throw new UsernameNotFoundException("Invalid Username and Password");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        /*grantedAuthorities.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())*/
		return new org.springframework.security.core.userdetails.User(user.getEmailId(),
	            user.getPassword(),grantedAuthorities);
	   
	}

	
}
 