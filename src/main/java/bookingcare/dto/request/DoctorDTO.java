package bookingcare.dto.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bookingcare.entity.Clinic;
import bookingcare.entity.Doctor;
import bookingcare.entity.Role;
import bookingcare.entity.Specialization;
import bookingcare.entity.User;
import jakarta.persistence.Column;

public class DoctorDTO {
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String avatar;
    private String gender;
    private String description;
    private Integer roleId;
    private Integer clinicId;
    private Integer specializationId;

    public DoctorDTO() {
    }

    public DoctorDTO(String name, String email, String password, String address, String phone, String avatar, String gender, String description, Integer roleId, Integer clinicId, Integer specializationId) {
        this.name = name;
        this.email = email;
        this.password = password;
     
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.gender = gender;
        this.description = description;
        this.roleId = roleId;
        this.clinicId = clinicId;
        this.specializationId = specializationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }


    public User convertToUser() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setPhone(phone);
        user.setAvatar(avatar);
        
        Set<Role> roles = new HashSet<Role>();
        Role role = new Role();
        role.setId(roleId);
        roles.add(role);
        user.setRoles(roles);
   
        Clinic clinic = new Clinic();
        clinic.setId(clinicId);
        
        Specialization specialization = new Specialization();
        specialization.setId(specializationId);

        return user;
    }
}
