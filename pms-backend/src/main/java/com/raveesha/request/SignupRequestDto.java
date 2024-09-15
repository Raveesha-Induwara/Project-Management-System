package com.raveesha.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    private String fullName;
    private String email;
    private String password;
}
