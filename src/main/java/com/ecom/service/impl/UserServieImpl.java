package com.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.UserDtls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

@Service
public class UserServieImpl implements UserService 
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDtls saveUser(UserDtls userDetails) 
	{
		userDetails.setRole("ROLE_USER");
		userDetails.setIsEnable(true);
		userDetails.setAccountNonLocked(true);
		userDetails.setFailedAttempt(0);
		String encode = passwordEncoder.encode(userDetails.getPassword());
		userDetails.setPassword(encode);
		UserDtls save = userRepository.save(userDetails);
		return save;
	}

	@Override
	public UserDtls getUserByEmail(String email) 
	{
		
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDtls> getUser(String role) 
	{
		List<UserDtls> byRole = userRepository.findByRole(role);
		return byRole;
	}

	@Override
	public Boolean updateAccountStatus(Boolean status, Integer id) 
	{
		Optional<UserDtls> byId = userRepository.findById(id);
		if(byId.isPresent())
		{
			UserDtls userDtls = byId.get();
			userDtls.setIsEnable(status);
			userRepository.save(userDtls);
			return true;
		}
		return false;
	}

	@Override
	public void increaseFailedAttempt(UserDtls user) 
	{
		int attempt = user.getFailedAttempt()+1;
		user.setFailedAttempt(attempt);
		userRepository.save(user);
		
	}

	@Override
	public void userAccountLock(UserDtls user)
	{
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userRepository.save(user);
	}

	@Override
	public Boolean unlockAccountTimeExpired(UserDtls user) 
	{
		long lockTime = user.getLockTime().getTime();
		long unlockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;
		
		long currentTime = System.currentTimeMillis();
		if(unlockTime < currentTime)
		{
			user.setAccountNonLocked(true);
			user.setFailedAttempt(0);
			user.setLockTime(null);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public void resetAttempt(Integer userId) 
	{
		
	}

	@Override
	public void updateUserResetToken(String email, String resetToken) 
	{
		UserDtls byEmail = userRepository.findByEmail(email);
		byEmail.setResetToken(resetToken);
		userRepository.save(byEmail);
	}

	@Override
	public UserDtls getUserByToken(String token) 
	{
		return userRepository.findByResetToken(token);	
	}

	@Override
	public UserDtls updateUser(UserDtls user) 
	{
		return userRepository.save(user);
	}

	@Override
	public UserDtls updateProfile(UserDtls user, MultipartFile img) 
	{
		UserDtls dbUser = userRepository.findById(user.getId()).get();
		
		if(!img.isEmpty())
		{
			dbUser.setProfileImage(img.getOriginalFilename());
		}
		
		if(!ObjectUtils.isEmpty(dbUser))
		{
			dbUser.setName(user.getName());
			dbUser.setMobile(user.getMobile());
			dbUser.setAddress(user.getAddress());
			dbUser.setCity(user.getCity());
			dbUser.setState(user.getState());
			dbUser.setPincode(user.getPincode());
			dbUser = userRepository.save(dbUser);
		}
		
		if(!img.isEmpty())
		{
			try {
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
					+ img.getOriginalFilename());

			// System.out.println(path);
			
				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return dbUser;
	}

	@Override
	public UserDtls saveAdmin(UserDtls userDetails) 
	{
		userDetails.setRole("ROLE_ADMIN");
		userDetails.setIsEnable(true);
		userDetails.setAccountNonLocked(true);
		userDetails.setFailedAttempt(0);
		String encode = passwordEncoder.encode(userDetails.getPassword());
		userDetails.setPassword(encode);
		UserDtls save = userRepository.save(userDetails);
		return save;
	}

	@Override
	public Boolean existEmail(String email) 
	{
		return userRepository.existsByEmail(email);
	}
	
}