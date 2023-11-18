package bookingcare.dto.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import bookingcare.entity.Role;
import bookingcare.entity.User;

public class UserDTO {

    private String name;
    private String userName;
    private String email;
    private String password;
    private String rePassword;
    private String address;
    private String phone;
    private String gender;
    private Integer roleId;

    public UserDTO() {
    }

    public UserDTO(String name, String userName, String email, String password,  String address, String phone, String gender, Integer roleId) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public User toUser() {
        User user = new User();
        user.setName(this.name);
        user.setUsername(userName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setAddress(this.address);
        user.setPhone(this.phone);

        Set<Role> roles = new HashSet<Role>();
        Role role = new Role();
        role.setId(roleId);
        roles.add(role);
        user.setRoles(roles);

        return user;
    }
}
