package org.example.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyDto {
    private String company_name;
    private String hotline;
    private String location;
    private String date;
}
