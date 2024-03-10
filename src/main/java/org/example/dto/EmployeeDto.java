package org.example.dto;
import lombok.*;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

public class EmployeeDto {
    private String emp_email;
    private String emp_pword;
    private String position;
    private String f_name;
    private String l_name;
    private String nic;
    private String address;
    private String gender;
    private String contact_num;
    private String date;
}
