package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class   NavigationbarFormController {
    public AnchorPane root;
    public AnchorPane pane;//replacing pane

    public void btnHistoryOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/history_form.fxml")));

    }

    public void btnExpDateOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/expire_date_form.fxml")));
    }

    public void btnStockOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/stock_form.fxml")));
    }

    public void btnReturnInvoiceOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/return_invoice_form.fxml")));
    }

    public void btnGRNOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/product_form.fxml")));
    }

    public void btnSupOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml")));
    }

    public void btnCusOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));
    }

    public void btnEmpOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml")));
    }

    public void btnInvoiceOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/invoice_form.fxml")));
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
    this.pane.getChildren().clear();
    this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/home_form.fxml")));
    }
}
