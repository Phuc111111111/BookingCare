package bookingcare.dto.request;

import java.util.Set;

public class SignUpForm {

    private String username;
    private String email;
    private String address;
    private String password;
    private Set<String> roles;

    public SignUpForm() {
    }

    public SignUpForm(String username,String address, String email, String password, Set<String> roles) {

        this.username = username;
        this.address = address;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
