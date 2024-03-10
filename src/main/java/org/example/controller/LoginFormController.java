package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.model.EmployeeModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUname;
    public PasswordField txtPword;
    public AnchorPane rootlogin;

    public void btnSignInOnAction(ActionEvent actionEvent) {
            String enteredUname = txtUname.getText();
            String enteredPword = txtPword.getText();

        if(enteredUname.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "username required..!!", ButtonType.OK).show();
        }else if(enteredPword.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "password required..!!", ButtonType.OK).show();
        }else{
            try {
                ResultSet resultSet = EmployeeModel.checkCredential(enteredUname,enteredPword);

                if (resultSet.next()) {
                    String name = resultSet.getString(1);
                    String pass = resultSet.getString(2);


                    if (pass.equals(enteredPword) & name.equals(enteredUname)) {

                        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/navigationbar_form.fxml"));
                        Scene scene = new Scene(anchorPane);
                        Stage stage = (Stage) rootlogin.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("Admin Form");
                        stage.centerOnScreen();
                        stage.show();


                    } else {
                        new Alert(Alert.AlertType.ERROR, "Invalid username or password").show();
                    }
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    }

