package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @SequenceGenerator(name = "subjects_seq", sequenceName = "subjects_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_seq")
    private Long id;

    private String name;
    private String lastName;
    private String givenName;

    @OneToMany(mappedBy = "subject")
    private List<BankAccount> accounts;

}
