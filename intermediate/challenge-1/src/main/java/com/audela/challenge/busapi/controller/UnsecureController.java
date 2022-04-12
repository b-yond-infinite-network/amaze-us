package com.audela.challenge.busapi.controller;

import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Use this controller to get tokens for accessing the api for testing.
 */
@RestController
@RequestMapping("/unsecure")
public class UnsecureController {

    @Autowired
    JwtUtils jwtUtils;
    @GetMapping(value = "/get-employee-token")
    public ResponseEntity<String> getEmployeeToken(){

        Map<String,Object> payload = new HashMap<>();
        payload.put("firstName","First");
        payload.put("lastName","Employee");
        payload.put("role","ROLE_EMPLOYEE");

        return new ResponseEntity<>(jwtUtils.createJWT(payload), HttpStatus.OK);
    }
    @GetMapping(value = "/get-manager-token")
    public ResponseEntity<String> getManagerToken(){

        Map<String,Object> payload = new HashMap<>();
        payload.put("firstName","First");
        payload.put("lastName","Manager");
        payload.put("role","ROLE_MANAGER");

        return new ResponseEntity<>(jwtUtils.createJWT(payload), HttpStatus.OK);
    }
}
