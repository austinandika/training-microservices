package id.co.bca.intra.training.rest.dto.bookDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookRequestDTO {
    private Long id;
    private String title;
    private String author;
    private Integer publishYear;
}
