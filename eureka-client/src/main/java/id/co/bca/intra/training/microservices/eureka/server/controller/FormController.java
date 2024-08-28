package id.co.bca.intra.training.microservices.eureka.server.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import id.co.bca.intra.training.microservices.eureka.server.dto.formDTO.FormRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("form")
public class FormController {
    private Logger logger = LoggerFactory.getLogger(FormController.class);

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @PostMapping("/input")
    private String inputForm(@RequestBody FormRequestDto request) {
        InstanceInfo service = eurekaClient
                .getApplication("serviceClientA")
                .getInstances()
                .getFirst();

        String hostName = service.getHostName();
        int port = service.getPort();

        logger.atDebug()
                .setMessage("Client hostname: " + hostName + ", port: " + port)
                .log();

        return "Form input successfully recorded";
    }
}
