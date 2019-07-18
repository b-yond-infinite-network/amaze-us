package com.kepler.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server/*")
public class ServerController {

    
    private static final String STATUS_PARAM = "status";
	private static final String TIMESTAMP_PARAM = "timestamp";
	private static final String TIMESTAMP_UNIT_PARAM = "timestamp_unit";

    @GetMapping("/ping")
    public @ResponseBody Map<String, String> ping() {
    	
    	Map<String, String> status = new LinkedHashMap<String, String>();
		
		status.put(STATUS_PARAM, "OK");
		status.put(TIMESTAMP_PARAM, Long.toString(System.currentTimeMillis()));
		status.put(TIMESTAMP_UNIT_PARAM, "ms");
		
		return status;
    }
}

