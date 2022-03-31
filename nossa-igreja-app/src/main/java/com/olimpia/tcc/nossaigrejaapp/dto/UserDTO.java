package com.olimpia.tcc.nossaigrejaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String phoneNumber;

    private String email;

    private String nomeCompleto;

    public String getNomeCompleto(){
        return this.firstName.concat(" ").concat(this.lastName);
    }
}
