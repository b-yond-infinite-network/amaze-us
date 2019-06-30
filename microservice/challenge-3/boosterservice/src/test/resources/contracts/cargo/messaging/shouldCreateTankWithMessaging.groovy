package contracts.cargo.messaging

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label("create_booster_tank") // Unique label for each contract as a reference
    input {
        triggeredBy('tankCreator()') // triggered to mock incoming message
    }
    outputMessage {
        sentTo("booster_tanks") // send a response to this queue 
        body(
            "title" : "reverseTank",
            uuid : "e2a8b899-6b62-4010-81f1-9faed24fed2b"
        )
        headers {
            header("contentType", applicationJsonUtf8())
        }
    }
}