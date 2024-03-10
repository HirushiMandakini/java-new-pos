package org.example.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

public class CustomerDto {
    private String mobile;
    private String f_name;
    private String l_name;
    private String address;
    private Date date;

}
