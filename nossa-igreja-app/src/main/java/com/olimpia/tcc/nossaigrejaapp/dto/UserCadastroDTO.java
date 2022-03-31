package com.olimpia.tcc.nossaigrejaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCadastroDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    private String phoneNumber;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Boolean acceptTerms;
}
