package contracts.cargo.http

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/tanks'
        body (
            "title" : "fakeTank",
            "requestedDate" : anyDate(),
            "archived": true,
            "fuel": [title:"part1", priority:"1", "done": true, "deadline":"2020-01-02T15:04:05Z"],
            "uuid" : $(consumer(anyUuid()), producer("66ce29f3-ae87-4097-94e8-60b3b10c3855"))
        )
        headers {
            contentType applicationJson()
        }
    }
    response {
        status 200
        body (
            "title" : "fakeTank",
            "uuid" : $(consumer(fromRequest().body('$.uuid')), producer(anyUuid()))
        )
        headers {
            contentType applicationJson()
        }
    }
}