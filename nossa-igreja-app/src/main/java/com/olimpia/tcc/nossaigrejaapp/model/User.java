package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "USUARIO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hashcode"})
@SequenceGenerator(name = "USUARIO_ID_USER_SEQ", allocationSize = 1)
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "USUARIO_ID_USER_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_USER")
    private Long id;

    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "ACCEPT_TERMS")
    private Boolean acceptTerms = false;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "ID_USER"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))
    private Set<Role> roles;
}
