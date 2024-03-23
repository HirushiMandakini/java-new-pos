package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.dto.CustomerDto;
import org.example.dto.EmployeeDto;
import org.example.model.CustomerModel;
import org.example.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerUpdateController implements Initializable {
    public AnchorPane UpdCusPane;
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
    private String mobile = CustomerFormController.StaticMobile;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //set textfields from table data
        try {
            CustomerDto dto = CustomerModel.getCustomer(mobile);
            txtCon.setText(dto.getMobile());
            txtFname.setText(dto.getF_name());
            txtLname.setText(dto.getL_name());
            txtAdd.setText(dto.getAddress());
            DatePicker.setValue(LocalDate.parse(dto.getDate()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnUpdateCusOnAction(ActionEvent actionEvent) {
        String conNumText = txtCon.getText();
        String fnameText = txtFname.getText();
        String lnameText = txtLname.getText();
        String addressText = txtAdd.getText();
        String dateText = String.valueOf(DatePicker.getValue());

        var dto =new CustomerDto(conNumText,fnameText,lnameText,addressText,dateText);

        try {
            boolean isUpdated = CustomerModel.updateCustomer(dto);
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
        txtCon.setText("");
        txtFname.setText("");
        txtLname.setText("");
        txtAdd.setText("");
        DatePicker.setValue((null));
    }
}
