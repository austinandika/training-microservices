package id.co.bca.intra.training.microservices.eureka.server.dto.formDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormRequestDto {
    private String id;
    private String name;
    private String address;
}
