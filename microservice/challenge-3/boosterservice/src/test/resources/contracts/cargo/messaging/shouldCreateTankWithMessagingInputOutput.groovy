package contracts.cargo.messaging

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label("booster_tank_input_output") // Unique label for each contract as a reference
    input {
        messageFrom('booster_tank_requests') // receive a request on this queue 
        messageBody(
                title : "reverseTank",
                uuid : $(c(regex(uuid())), p("e2a8b899-6b62-4010-81f1-9faed24fed2b"))
        )
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