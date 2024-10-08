package id.co.bca.intra.training.rest.dto.userDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsertUserRequestDTO {
    private Long id;
    private String username;
    private String password;
}
