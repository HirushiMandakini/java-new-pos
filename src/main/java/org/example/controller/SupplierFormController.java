package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class SupplierFormController {
    public TextField txtSearch;
    public TableView tblSup;
    public TableColumn colMobile;
    public TableColumn colFname;
    public TableColumn colLname;
    public TableColumn colEmail;
    public TableColumn colCompany;
    public TableColumn colEditbtn;
    public TableColumn colDeletebtn;

    public void searchOnAction(MouseEvent mouseEvent) {
    }

    public void txtSearchOnAction(KeyEvent keyEvent) {
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorpane = FXMLLoader.load(getClass().getResource("/view/supplier_adding.fxml"));
        Scene scene = new Scene(anchorpane);

        Stage stage = new Stage();
        stage.setTitle("Supplier Adding Form");
        stage.setScene(scene);

        stage.centerOnScreen();
        stage.show();
    }

    public void btnAddCompanyOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorpane = FXMLLoader.load(getClass().getResource("/view/supplier_company_registration.fxml"));
        Scene scene = new Scene(anchorpane);

        Stage stage = new Stage();
        stage.setTitle("Company register Form");
        stage.setScene(scene);

        stage.centerOnScreen();
        stage.show();
    }
}
