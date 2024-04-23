package org.example.controller.customer_controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.dto.CustomerDto;
import org.example.model.CustomerModel;

import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerAddingController {
    public AnchorPane cusAddPane;
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtAddress;
    public TextField txtMobile;
    private CustomerModel customerModel = new CustomerModel();

    public javafx.scene.control.DatePicker DatePicker;
    public void initialize(){

        DatePicker.setValue(LocalDate.now());
    }

    public void btnAddedCustomerOnAction(ActionEvent actionEvent) {
        String mobile = txtMobile.getText();
        String f_name = txtFname.getText();
        String l_name= txtLname.getText();
        String email= txtEmail.getText();
        String address = txtAddress.getText();
        String date = DatePicker.getValue().toString();

        var dto =new CustomerDto(mobile,f_name,l_name,email,address,date);

        try{
            boolean isSaved = CustomerModel.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully saved!!!").show();

                clearFields();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtMobile.setText("");
        txtFname.setText("");
        txtLname.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        DatePicker.setValue((null));
    }
}
