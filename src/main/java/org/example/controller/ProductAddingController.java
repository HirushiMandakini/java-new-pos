package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.security.SecureRandom;

public class ProductAddingController {
    public TextField txtProductName;
    public TextField txtCostPrice;
    public TextField txtCategory;
    public TextField txtMrp;
    public TextField txtQty;
    public TextField txtFreeQty;
    public TextField txtStockBin;
    public TextField txtStockAlert;
    public TextField barcord;
    public DatePicker DateManufacture;
    public DatePicker dateExpire;
    public TextField txtInvoiceNum;
    public TextField txtPaidAmount;
    public ComboBox comboSupplier;
    public Label lblBalance;
    public DatePicker invoiceDate;
    public DatePicker invoiceDueDate;
    public Label lblInvoiceAmount;
    private String generateRandomString(int length) {
        String characters = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }
        return sb.toString();
    }

    public void btnGenerateBidOnAction(ActionEvent actionEvent) {
        int length = 15; // Specify the length of the random string

        // Generate a random alphanumeric string of specified length
        String randomString = generateRandomString(length);

        // Fill the barcode text field with the generated random string
        barcord.setText(randomString);
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void btnAddNewSup(ActionEvent actionEvent) throws IOException {
        Parent anchorpane = FXMLLoader.load(getClass().getResource("/view/supplier_adding.fxml"));
        Scene scene = new Scene(anchorpane);

        Stage stage = new Stage();
        stage.setTitle("Supplier Adding Form");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                //loadAllCustomer();
            }
        });
        stage.centerOnScreen();
        stage.show();
    }

    public void btnGenerateInvOnAction(ActionEvent actionEvent) {
        int length = 10; // Specify the length of the random string

        // Generate a random alphanumeric string of specified length
        String randomString = generateRandomString(length);

        // Fill the barcode text field with the generated random string
        txtInvoiceNum.setText(randomString);
    }
}
