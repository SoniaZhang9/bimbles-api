package com.bimbles.security;


import java.io.Serial;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.bimbles.entities.NormalUser;
import com.bimbles.entities.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public CustomUserDetails(User user) {
		String role;
		
		this.id = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		if (user.getClass().isAssignableFrom(NormalUser.class)) {
			role = "NORMAL";
		} else role = "ADMIN";
		this.authorities = List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public List<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
