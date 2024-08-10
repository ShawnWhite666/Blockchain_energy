package com.blockchainenergy.security.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @descriptions:
 * @data: 2021/10/21 8:31
 */
public class UserDto {

    private String account_number;
    private String password;
    private String real_name;
    private String person_id_number;
    private String contact_number;
    private String company_name;
    private String company_address;
    private String location;
    private String contact_email;
    private String personal_description;

    private Integer gender;
    private Integer age;

    public UserDto(String account_number, String password, String real_name, String person_id_number, String contact_number, String company_name, String company_address, String location, String contact_email, String personal_description, Integer gender, Integer age) {
        this.account_number = account_number;
        this.password = password;
        this.real_name = real_name;
        this.person_id_number = person_id_number;
        this.contact_number = contact_number;
        this.company_name = company_name;
        this.company_address = company_address;
        this.location = location;
        this.contact_email = contact_email;
        this.personal_description = personal_description;
        this.gender = gender;
        this.age = age;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPerson_id_number() {
        return person_id_number;
    }

    public void setPerson_id_number(String person_id_number) {
        this.person_id_number = person_id_number;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getPersonal_description() {
        return personal_description;
    }

    public void setPersonal_description(String personal_description) {
        this.personal_description = personal_description;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
