package com.olimpia.tcc.nossaigrejaapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SequenceGenerator(name="role_id_role_seq", allocationSize=1)
public class Role implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_role_seq")
    @Column(name = "ID_ROLE")
    private Long id;

    @Column(name = "NAME")
    private String name;
    
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    
    @Version
    @Column(name = "versao")
    private Integer versao;

}
