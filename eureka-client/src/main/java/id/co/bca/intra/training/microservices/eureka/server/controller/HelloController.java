package id.co.bca.intra.training.microservices.eureka.server.controller;

import id.co.bca.intra.training.microservices.eureka.server.dto.formDTO.FormRequestDto;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
public class HelloController {
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public HelloController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
    }

    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    private String printHelloWorld() {
        return "Hello world!";
    }

    @PostMapping("/helloEureka")
    @ResponseStatus(HttpStatus.OK)
    private String helloForm(@RequestBody FormRequestDto request) {
        ServiceInstance serviceInstance = discoveryClient.getInstances("serviceClientA").getFirst();
        return restClient.post()
                .uri(serviceInstance.getUri() + "/form/input")
                .body(request)
                .retrieve()
                .body(String.class);
    }
}
