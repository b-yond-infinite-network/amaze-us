package com.audela.challenge.busapi.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserVo {
    Integer id;
    String firstName;
    String lastName;
    String ssn;
    boolean isDriver;
    String role;
}
