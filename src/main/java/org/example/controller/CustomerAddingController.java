package org.example.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.dto.CustomerDto;
import org.example.dto.EmployeeDto;
import org.example.model.CustomerModel;
import org.example.model.EmployeeModel;

import java.sql.SQLException;

public class CustomerAddingController {
    public AnchorPane cusAddPane;
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtPword;
    public TextField txtNic;
    public TextField txtCon;


    private CustomerModel customerModel = new CustomerModel();

    public TextField txtAdd;
    public javafx.scene.control.DatePicker DatePicker;

    public void btnAddedCustomerOnAction(ActionEvent actionEvent) {
        String conNumText = txtCon.getText();
        String fnameText = txtFname.getText();
        String lnameText = txtLname.getText();
        String addressText = txtAdd.getText();
        String dateText = DatePicker.getValue().toString();

        var dto =new CustomerDto(conNumText,fnameText,lnameText,addressText,dateText);

        try{
            boolean isSaved = CustomerModel.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully saved!!!").show();

                clearFields();
//              tblEmp.refresh();

            }
        } catch (SQLException e) {
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
