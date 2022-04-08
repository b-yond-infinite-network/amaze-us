package com.audela.challenge.busapi.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * A driver has a first name, last name, social security number and an email
 */
@Data
@Table(name="DRIVER")
@Entity
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Integer id;

    @Column(name="first_name",nullable = false, length = 70)
    private String firstName;

    @Column(name="last_name",nullable = false, length = 70)
    private String lastName;

    @Column(name="ssn",nullable = false, length = 70)
    private String ssn;

    @Column(name="email",nullable = false, length = 70)
    private String email;

}
