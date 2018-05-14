package com.sourcecodelab.reminders.rest.controller;

import com.sourcecodelab.reminders.service.exceptions.ErrorCode;
import com.sourcecodelab.reminders.service.exceptions.ReminderServiceException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestControllerTestUtil {
	@GetMapping("/reminder-service-exception")
	public void reminderServiceException() {
		throw new ReminderServiceException(ErrorCode.RESOURCE_NOT_FOUND);
	}
}
