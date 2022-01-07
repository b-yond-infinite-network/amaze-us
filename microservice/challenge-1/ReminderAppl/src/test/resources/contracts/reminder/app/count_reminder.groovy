import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "Should return number of reminders saved in the app"

	request {
		url "/v1/reminderapp/count"
		method GET()
	}

	response {
		status OK()
		headers {
			contentType('text/plain')
		}
		body(
				0
		)
	}
}