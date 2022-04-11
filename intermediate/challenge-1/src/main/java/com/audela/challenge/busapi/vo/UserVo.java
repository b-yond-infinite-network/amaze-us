package com.audela.challenge.busapi.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {
    Integer id;
    String firstName;
    String lastName;
    String ssn;
    boolean isDriver;

}
