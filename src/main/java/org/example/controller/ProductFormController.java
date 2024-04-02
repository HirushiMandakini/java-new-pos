package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.security.SecureRandom;

public class ProductFormController {
    public TextField barcord;
    public TextField txtInvoiceNum;
    public AnchorPane productPane;
    public TextField txtSearch;
    public TableView tblProduct;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TableColumn col6;
    public TableColumn col7;
    public TableColumn col8;
    public TableColumn col9;
    public TableColumn col10;
    public TableColumn col11;
    public TableColumn col12;


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

    public void btnAddProductOnAction(ActionEvent actionEvent) {
    }

    public void btnAddNewSup(ActionEvent actionEvent) {
    }

    public void btnGenerateInvOnAction(ActionEvent actionEvent) {
        int length = 10; // Specify the length of the random string

        // Generate a random alphanumeric string of specified length
        String randomString = generateRandomString(length);

        // Fill the barcode text field with the generated random string
        txtInvoiceNum.setText(randomString);
    }

    public void btnAddNewProductOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorpane = FXMLLoader.load(getClass().getResource("/view/product_adding.fxml"));
        Scene scene = new Scene(anchorpane);

        Stage stage = new Stage();
        stage.setTitle("Product Adding Form");
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

    public void txtSearchOnAction(KeyEvent keyEvent) {
    }

    public void searchOnAction(MouseEvent mouseEvent) {
    }
}
