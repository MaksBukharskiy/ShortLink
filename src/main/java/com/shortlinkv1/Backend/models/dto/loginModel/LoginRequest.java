package com.shortlinkv1.Backend.models.dto.loginModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    public String email;
    public String password;

}
