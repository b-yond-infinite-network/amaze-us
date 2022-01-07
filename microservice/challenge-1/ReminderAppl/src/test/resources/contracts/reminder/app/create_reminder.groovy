import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "Should create a new reminder in the app"

	request {
		url "/v1/reminderapp"
		method POST()
		body(file("createReminderRequest.json"))
		
		headers{
			contentType 'application/json'
		}
		
	}

	response {
		status CREATED()
		headers{
			contentType 'application/json'
		}
		body(file("createReminderrResponse.json"))
	}
}