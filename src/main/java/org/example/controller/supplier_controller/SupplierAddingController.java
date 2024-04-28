package org.example.controller.supplier_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.dto.CompanyDto;
import org.example.dto.SupplierDto;
import org.example.model.CompanyModel;
import org.example.model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class SupplierAddingController {
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtMobile;
    public ComboBox comboCompany;

    CompanyModel companyModel=new CompanyModel();
    SupplierModel supplierModel=new SupplierModel();
    public void initialize() {
        loadCompanyNames();


    }
    void clearFields(){
        txtFname.clear();
        txtLname.clear();
        txtEmail.clear();
        txtMobile.clear();
        comboCompany.setValue(null);
    }

    private void loadCompanyNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CompanyDto> idList = companyModel.getAllCompany();

            for (CompanyDto dto : idList) {
                obList.add(dto.getCompany_name());
            }

            comboCompany.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void comboCompanyNameOnAction(ActionEvent actionEvent) {
        String companyName = (String) comboCompany.getValue();
        try {
            CompanyDto customerDto = companyModel.searchCompany(companyName);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddedSupplierOnAction(ActionEvent actionEvent) {
        String mobile=txtMobile.getText();
        String fnameText = txtFname.getText();
        String lnameText = txtLname.getText();
        String emailText = txtEmail.getText();
        String company= comboCompany.getValue().toString();


        var dto =new SupplierDto(mobile,fnameText,lnameText,emailText,company);

        try{
            boolean isSaved = SupplierModel.saveSupplier(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully saved!!!").show();

                clearFields();
                refreshTable();

                
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void refreshTable() {
    }
}
