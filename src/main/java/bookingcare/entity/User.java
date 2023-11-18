package bookingcare.entity;

import java.util.HashSet;
import java.util.Set;

import bookingcare.dto.enums.UserStatus;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    
    private String username;
    
    private String password;
    private String phone;
    private String address;
    private String email;
    
    @Column(name = "avatar")
    private String avatar;
    
    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "reset_password_code")
    private String resetPasswordCode;

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();
    
    public User() {

	}
    
	public User(Integer id, String username, String password, String address, String email, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.address = address;
		this.email = email;
		this.roles = roles;
	}
	
	 public User(String username,String address,String email,String encode) {
		 this.username = username;
		 this.email = email;
		 this.address = address;
		 this.password = encode;
}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	  public Set<Role> getRoles() {
	        return roles;
	    }

	    public void setRoles(Set<Role> roles) {
	        this.roles = roles;
	    }

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String getVerificationCode() {
			return verificationCode;
		}

		public void setVerificationCode(String verificationCode) {
			this.verificationCode = verificationCode;
		}

		public String getResetPasswordCode() {
			return resetPasswordCode;
		}

		public void setResetPasswordCode(String resetPasswordCode) {
			this.resetPasswordCode = resetPasswordCode;
		}

		@Override
		public String toString() {
			return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone
					+ ", address=" + address + ", email=" + email + ", avatar=" + avatar + ", enabled=" + enabled
					+ ", verificationCode=" + verificationCode + ", resetPasswordCode=" + resetPasswordCode + ", roles="
					+ roles + "]";
		}

	
    
    

}
