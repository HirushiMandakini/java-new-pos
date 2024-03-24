package org.example.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

public class CustomerTm {
    private String mobile;
    private String f_name;
    private  String l_name;
    private String Email;
    private  String address;
    private String date;
    private Button update;
    private Button delete;
}
