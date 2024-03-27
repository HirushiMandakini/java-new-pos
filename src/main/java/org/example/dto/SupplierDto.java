package org.example.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplierDto {
    private String mobile;
    private String fname;
    private String lname;
    private String email;
    private String company;
}
