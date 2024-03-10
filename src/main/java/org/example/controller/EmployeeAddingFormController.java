package org.example.controller;

import com.ctc.wstx.shaded.msv_core.datatype.xsd.datetime.IDateTimeValueType;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.dto.EmployeeDto;
import org.example.dto.tm.EmployeeTm;
import org.example.model.EmployeeModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeAddingFormController implements Initializable {
    public AnchorPane rootAdd;
    public TextField txtFname;
    public TextField txtLname;
    public TextField txtEmail;
    public TextField txtPword;
    public TextField txtNic;
    public ComboBox comboPosition;
    public TextField txtCon;
    public ComboBox<String> comboGender;
    public TextField txtAdd;
    public javafx.scene.control.DatePicker DatePicker;

    private String[] gender = {"Male","Female"};
    private String[] position={"Admin","Cashier"};

    EmployeeFormController employeeFormController =null;


    private EmployeeModel employeeModel = new EmployeeModel();

    public void cmbGenderOnAction(ActionEvent actionEvent) {
    }

    public void cmbPositionOnAction(ActionEvent actionEvent) {
    }
    public void initialize(){

    }
    public void setController(EmployeeFormController employeeFormController){
        this.employeeFormController = employeeFormController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboGender.getItems().addAll(gender);
        comboPosition.getItems().addAll(position);

    }

    public void btnCreateAccOnAction(ActionEvent actionEvent) {

        String emailText = txtEmail.getText();
        String pwordText = txtPword.getText();
        String positionCombo= comboPosition.getValue().toString();
        String fnameText = txtFname.getText();
        String lnameText = txtLname.getText();
        String nicText = txtNic.getText();
        String addressText = txtAdd.getText();
        String genderCombo = comboGender.getValue();
        String conNumText = txtCon.getText();
        String dateText = DatePicker.getValue().toString();

        var dto =new EmployeeDto(emailText,pwordText,positionCombo,fnameText,lnameText,nicText,addressText,genderCombo,conNumText,dateText);

        try{
            boolean isSaved = EmployeeModel.saveEmployee(dto);
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
