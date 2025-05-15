package com.ecom.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecom.model.UserDtls;

public class CustomUser implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDtls dtls;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(dtls.getRole());
		return Arrays.asList(authority);
	}

	public CustomUser(UserDtls dtls) 
	{
		super();
		this.dtls = dtls;
	}

	@Override
	public String getPassword() 
	{
		
		return dtls.getPassword();
	}

	@Override
	public String getUsername() 
	{
		return dtls.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked() 
	{
		
		return dtls.getAccountNonLocked();
	}
	
	@Override
	public boolean isCredentialsNonExpired() 
	{
		
		return true;
	}
	
	@Override
	public boolean isEnabled() 
	{
		
		return dtls.getIsEnable();
	}
}
