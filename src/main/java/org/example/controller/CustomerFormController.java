package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.dto.CustomerDto;
import org.example.dto.tm.CustomerTm;
import org.example.model.CustomerModel;
import org.example.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.model.CustomerModel.deleteCustomer;


public class CustomerFormController {
    public AnchorPane custPane;
    public TableView tblCus;

    public TextField txtSearchCustomer;

    static String StaticMobile="";
    public TableColumn colMobile;
    public TableColumn colFname;
    public TableColumn colLastname;
    public TableColumn colEmail;
    public TableColumn colAddress;
    public TableColumn colDate;
    public TableColumn colUpdatebtn;
    public TableColumn colDeletebtn;

    private CustomerModel customerModel = new CustomerModel();

    private CustomerAddingController customerAddingController=new CustomerAddingController();
    private ObservableList<CustomerTm>obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllCustomer();
    }

    public void setCellValueFactory() {
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colFname.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colUpdatebtn.setCellValueFactory(new PropertyValueFactory<>("update"));
        colDeletebtn.setCellValueFactory(new PropertyValueFactory<>("delete"));

    }
    public void loadAllCustomer(){
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try{
            List<CustomerDto> dtoList = model.getAllCustomer();
            for (CustomerDto dto : dtoList){

                ////////// Update button////////////////////
                Button update = new Button();
                setUpdateBtnOnAction(update, dto.getMobile());
                update.setOnMouseEntered(e -> {
                    update.setStyle("-fx-background-color:  #82CD47;");
                });
                update.setStyle("-fx-background-color:  #379237");
                update.setOnMouseExited(e -> {
                    update.setStyle("-fx-background-color:  #379237;");
                });
                ImageView updateImageView = new ImageView(new Image(getClass().getResourceAsStream("/assets/edit.png")));
                updateImageView.setFitHeight(15);
                updateImageView.setFitWidth(15);
                update.setGraphic(updateImageView);
                update.setCursor(Cursor.HAND);

                ///////////// Delete button///////////////////
                Button delete = new Button();
                setDeleteBtnOnAction(delete, dto.getMobile());
                delete.setOnMouseEntered(e -> {
                    delete.setStyle("-fx-background-color:  #FF6868");
                });
                delete.setStyle("-fx-background-color:  #DF2E38");
                delete.setOnMouseExited(e -> {
                    delete.setStyle("-fx-background-color:  #DF2E38");
                });
                ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/assets/trash-bin.png")));
                deleteImageView.setFitHeight(15);
                deleteImageView.setFitWidth(15);
                delete.setGraphic(deleteImageView);
                delete.setCursor(Cursor.HAND);

                obList.add(
                        new CustomerTm(
                                dto.getMobile(),
                                dto.getF_name(),
                                dto.getL_name(),
                                dto.getEmail(),
                                dto.getAddress(),
                                dto.getDate(),
                                update,
                                delete
                        )
                );
            }
            tblCus.setItems(obList);
        }catch (SQLException  e){
            throw new RuntimeException(e);
        }
    }

    private void setUpdateBtnOnAction(Button update, String mobile) {
        update.setOnAction((e) -> {
                    StaticMobile=mobile;

                    obList.removeIf(customerTm -> customerTm.getMobile().equals(mobile));
                    tblCus.refresh();
                    loadAllCustomer();

                    Parent anchorpane = null;
                    try {
                        anchorpane = FXMLLoader.load(getClass().getResource("/view/customer_update.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Scene scene = new Scene(anchorpane);

                    Stage stage = new Stage();
                    stage.setTitle("Customer Updating Form");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            loadAllCustomer();
                        }
                    });

                }
        );

    }

    private void setDeleteBtnOnAction(Button delete, String mobile) {
        delete.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        obList.removeIf(customerTm -> customerTm.getMobile().equals(mobile));
                        tblCus.refresh();
                        deleteCustomer(mobile);

                    }
                }
        );
    }
   private void deleteCustomer(String mobile) {
        try {
            boolean isDeleted = CustomerModel.deleteCustomer(mobile);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        tblCus.refresh();
        loadAllCustomer();
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorpane = FXMLLoader.load(getClass().getResource("/view/customer_adding .fxml"));
        Scene scene = new Scene(anchorpane);

        Stage stage = new Stage();
        stage.setTitle("Customer Adding Form");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllCustomer();
            }
        });
        stage.centerOnScreen();
        stage.show();    }

    public void searchOnAction(MouseEvent mouseEvent) {
    }

    public void txtSearchCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        String text = txtSearchCustomer.getText();
        ArrayList<CustomerDto> customerDtos = customerModel.searchByMobile(text);
        tblCus.getItems().clear();

        for (CustomerDto dto : customerDtos){

            ////////// Update button////////////////////
            Button update = new Button();
            setUpdateBtnOnAction(update, dto.getMobile());
            update.setOnMouseEntered(e -> {
                update.setStyle("-fx-background-color:  #82CD47;");
            });
            update.setStyle("-fx-background-color:  #379237");
            update.setOnMouseExited(e -> {
                update.setStyle("-fx-background-color:  #379237;");
            });
            ImageView updateImageView = new ImageView(new Image(getClass().getResourceAsStream("/assets/edit.png")));
            updateImageView.setFitHeight(15);
            updateImageView.setFitWidth(15);
            update.setGraphic(updateImageView);
            update.setCursor(Cursor.HAND);

            ///////////// Delete button///////////////////
            Button delete = new Button();
            setDeleteBtnOnAction(delete, dto.getMobile());
            delete.setOnMouseEntered(e -> {
                delete.setStyle("-fx-background-color:  #FF6868");
            });
            delete.setStyle("-fx-background-color:  #DF2E38");
            delete.setOnMouseExited(e -> {
                delete.setStyle("-fx-background-color:  #DF2E38");
            });
            ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/assets/trash-bin.png")));
            deleteImageView.setFitHeight(15);
            deleteImageView.setFitWidth(15);
            delete.setGraphic(deleteImageView);
            delete.setCursor(Cursor.HAND);

            obList.add(
                    new CustomerTm(
                            dto.getMobile(),
                            dto.getF_name(),
                            dto.getL_name(),
                            dto.getEmail(),
                            dto.getAddress(),
                            dto.getDate(),
                            update,
                            delete

                    )
            );
        }
        tblCus.setItems(obList);


    }
}
