import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return customer information"
    request {
        method POST()
        url("/api/v1/customers") {
            body(
                    firstName:"JP",
                    lastName: "LM",
                    email: "customer@service.com"
            )
        }
    }
    response {
        status 200

    }
}