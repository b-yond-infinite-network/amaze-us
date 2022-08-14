package com.beyond.microservice.schedule.bus;

import java.text.ParseException;
import java.util.Locale;

import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BusFormatter implements Formatter<Bus> {
    private BusRepository busRepository;
    
    
    @Override
    public Bus parse(String text, Locale locale) throws ParseException {
        return busRepository.findById(Long.parseLong(text)).get();
    }
    
    @Override
    public String print(final Bus bus, final Locale locale) {
        return bus.getBusId().toString();
    }
}
