package org.example.controller.supplier_controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SupplierAddingController {
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtAddress;
    public TextField txtMobile;
    public ComboBox comboCompany;

    public void btnAddedSupplierOnAction(ActionEvent actionEvent) {
    }
    void clearFields(){
        txtFname.clear();
        txtLname.clear();
        txtEmail.clear();
        txtAddress.clear();
        txtMobile.clear();
        comboCompany.setValue(null);
    }
}
