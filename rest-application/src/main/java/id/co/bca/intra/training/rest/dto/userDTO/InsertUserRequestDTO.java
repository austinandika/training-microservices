package id.co.bca.intra.training.rest.dto.userDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsertUserRequestDTO {
    private String userId;
    private String name;
}
