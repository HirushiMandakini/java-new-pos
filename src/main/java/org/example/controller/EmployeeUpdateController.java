package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.dto.EmployeeDto;
import org.example.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmployeeUpdateController implements Initializable {
    public AnchorPane updatePane;
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtPword;
    public TextField txtNic;
    public ComboBox comboPosition;
    public TextField txtCon;
    public ComboBox comboGender;
    public TextField txtAdd;
    public javafx.scene.control.DatePicker DatePicker;
    private String[] gender = {"Male","Female"};
    private String[] position={"Admin","Cashier"};

    private String email = EmployeeFormController.StaticEmail;
@Override
    public void initialize(URL url,ResourceBundle resourceBundle){
        comboGender.getItems().addAll(gender);
        comboPosition.getItems().addAll(position);


        //set textfields from table data
    try {
        EmployeeDto dto = EmployeeModel.getEmployee(email);

        txtEmail.setText(dto.getEmp_email());
        txtPword.setText(dto.getEmp_pword());
        comboPosition.setValue(dto.getPosition());
        txtFname.setText(dto.getF_name());
        txtLname.setText(dto.getL_name());
        txtNic.setText(dto.getNic());
        txtAdd.setText(dto.getAddress());
        comboGender.setValue(dto.getGender());
        txtCon.setText(dto.getContact_num());
        DatePicker.setValue(LocalDate.parse(dto.getDate()));

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}
    public void cmbPositionOnAction(ActionEvent actionEvent) {
    }

    public void cmbGenderOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateAccOnAction(ActionEvent actionEvent) {

        String emailText = txtEmail.getText();
        String pwordText = txtPword.getText();
        String positionCombo= (String) comboPosition.getValue();
        String fnameText = txtFname.getText();
        String lnameText = txtLname.getText();
        String nicText = txtNic.getText();
        String addressText = txtAdd.getText();
        String genderCombo = (String) comboGender.getValue();
        String conNumText = txtCon.getText();
        String dateText = String.valueOf(DatePicker.getValue());

        var dto =new EmployeeDto(emailText,pwordText,positionCombo,fnameText,lnameText,nicText,addressText,genderCombo,conNumText,dateText);

        try {
            boolean isUpdated = EmployeeModel.updateEmployee(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
//                loadAllEmployee();
                clearFields();
            }  else {
                new Alert(Alert.AlertType.CONFIRMATION, "employee not updated!").show();
            }
        }catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtEmail.setText("");
        txtPword.setText("");
        txtFname.setText("");
        txtLname.setText("");
        txtAdd.setText("");
        txtNic.setText("");
        txtCon.setText("");
        comboGender.setValue("");
        comboPosition.setValue("");
        DatePicker.setValue((null));
    }
}
