package com.hbk.corefw.test.dto;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.validation.annotation.NotEmpty;

public class UserDTO extends CoreDTO {
    private Long id;
    @NotEmpty(maxLength = 2)
    private String firstName;
    @NotEmpty(maxLength = 3)
    private String lastName;
    @NotEmpty(maxLength = 4)
    private String username;
    @NotEmpty
    private String password;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
