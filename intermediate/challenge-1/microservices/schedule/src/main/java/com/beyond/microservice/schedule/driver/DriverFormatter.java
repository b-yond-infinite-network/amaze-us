package com.beyond.microservice.schedule.driver;

import java.text.ParseException;
import java.util.Locale;

import lombok.RequiredArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverFormatter implements Formatter<Driver> {
    private DriverRepository customerRepository;
    
    @Override
    public Driver parse(final String text, final Locale locale) throws ParseException {
        return null;
    }
    
    @Override
    public String print(final Driver object, final Locale locale) {
        return null;
    }
}
