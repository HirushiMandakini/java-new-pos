package org.example.dto.tm;
import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplierTm {
    private String mobile;
    private String fname;
    private String lname;
    private String email;
    private String company;
    private Button update;
    private Button remove;
}
