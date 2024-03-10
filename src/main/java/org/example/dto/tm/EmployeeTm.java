package org.example.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeTm {
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
    private Button update;
    private Button remove;
}
