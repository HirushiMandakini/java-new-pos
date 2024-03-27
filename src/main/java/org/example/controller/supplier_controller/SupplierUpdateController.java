package org.example.controller.supplier_controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.controller.employee_controller.EmployeeFormController;
import org.example.dto.EmployeeDto;
import org.example.dto.SupplierDto;
import org.example.model.EmployeeModel;
import org.example.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SupplierUpdateController implements Initializable {
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtAddress;
    public TextField txtMobile;
    public ComboBox comboCompany;
    private String mobile = SupplierFormController.StaticMobile;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        //set textfields from table data to popup update window
        try {
            SupplierDto dto = SupplierModel.getSupplier(mobile);
            txtMobile.setText(dto.getMobile());
            txtFname.setText(dto.getFname());
            txtLname.setText(dto.getLname());
            txtEmail.setText(dto.getEmail());
            comboCompany.setValue(dto.getCompany());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void btnUpdatedSupplierOnAction(ActionEvent actionEvent) {
        String mobile = txtMobile.getText();
        String fnameText = txtFname.getText();
        String lnameText = txtLname.getText();
        String emailText = txtEmail.getText();
        String company= comboCompany.getValue().toString();

        var dto =new SupplierDto(mobile,fnameText,lnameText,emailText,company);

        try {
            boolean isUpdated = SupplierModel.updateSupplier(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
//                loadAllEmployee();
                clearFields();
            }  else {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier not updated!").show();
            }
        }catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtMobile.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtFname.setText("");
        txtLname.setText("");
        comboCompany.setValue("");

    }
}
