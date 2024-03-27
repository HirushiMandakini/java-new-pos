package org.example.controller.supplier_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.controller.employee_controller.EmployeeAddingFormController;
import org.example.dto.CompanyDto;
import org.example.dto.EmployeeDto;
import org.example.dto.tm.EmployeeTm;
import org.example.model.CompanyModel;
import org.example.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SupplierCompanyRegistration {
    public TextField txtCompany;
    public TextField txtHotline;
    public TextField txtLocation;
    public DatePicker datePicker;
    public TextField txtSearch;

    private ObservableList<CompanyDto> obList = FXCollections.observableArrayList();

    public void initialize(){
    }

    private void clearFields() {
        txtCompany.setText("");
        txtLocation.setText("");
        txtHotline.setText("");
        txtSearch.setText("");
        datePicker.setValue(null);
    }

    public void btnAddedCompanyOnAction(ActionEvent actionEvent) {
        String company_name = txtCompany.getText();
        String location = txtLocation.getText();
        String hotline = txtHotline.getText();
        String date = datePicker.getValue().toString();

        var dto =new CompanyDto(company_name,location,hotline,date);

        try{
            boolean isSaved = CompanyModel.saveCompany(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully saved!!!").show();

                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    public void btnUpdatedCompanyOnAction(ActionEvent actionEvent) {
        String company_name = txtCompany.getText();
        String location = txtLocation.getText();
        String hotline = txtHotline.getText();
        String date = datePicker.getValue().toString();

        var dto =new CompanyDto(company_name,location,hotline,date);

        try {
            boolean isUpdated = CompanyModel.updateCompany(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Company updated!").show();
//                loadAllEmployee();
                clearFields();
            }  else {
                new Alert(Alert.AlertType.CONFIRMATION, "company not updated!").show();
            }
        }catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeletedCompanyOnAction(ActionEvent actionEvent) {
        String name = txtCompany.getText();

        try {
            boolean isDeleted = CompanyModel.deleteCompany(name);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "company deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "company not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void btnSearchCompanyOnAction(ActionEvent actionEvent) {
        String name = txtSearch.getText();

        try {
            CompanyDto dto = CompanyModel.searchCompany(name);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "companynot found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void nameSearchOnAction(ActionEvent actionEvent) {
        String name = txtSearch.getText();

        try {
            CompanyDto dto = CompanyModel.searchCompany(name);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "company not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void setFields(CompanyDto dto) {
        txtCompany.setText(dto.getCompany_name());
        txtHotline.setText(dto.getHotline());
        txtLocation.setText(dto.getLocation());
        datePicker.setValue(LocalDate.parse(dto.getDate()));
    }
}
