package bookingcare.security.Jwt.userprincal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bookingcare.entity.User;




public class UserPrinciple implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> roles;
    
    
    
    public UserPrinciple(Integer id, String username, String password, Collection<? extends GrantedAuthority> roles) {
    	this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public static UserPrinciple build(User user){
		
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role->
        new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		
          return new UserPrinciple(
        		  user.getId(),
                  user.getUsername(),
                  user.getPassword(),
                  authorities
        		  );
      }
   


	public Integer getId() {
		return id;
	}


	@Override
	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}



	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
    
    
}
