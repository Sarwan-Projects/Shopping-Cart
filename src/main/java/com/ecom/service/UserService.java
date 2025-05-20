package com.ecom.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.UserDtls;

public interface UserService 
{
	public UserDtls saveUser(UserDtls userDetails);
	
	public UserDtls saveAdmin(UserDtls userDetails);
	
	public UserDtls getUserByEmail(String email);
	
	public List<UserDtls> getUser(String role);
	
	public Boolean updateAccountStatus(Boolean status, Integer id);
	
	public void increaseFailedAttempt(UserDtls user);
	
	public void userAccountLock(UserDtls user);
	
	public Boolean unlockAccountTimeExpired(UserDtls user);
	
	public void resetAttempt(Integer userId);
	
	public void updateUserResetToken(String email, String resetToken);
	
	public UserDtls getUserByToken(String token);
	
	public UserDtls updateUser(UserDtls user);
	
	public UserDtls updateProfile(UserDtls user, MultipartFile img);
	
	public Boolean existEmail(String email);
}
