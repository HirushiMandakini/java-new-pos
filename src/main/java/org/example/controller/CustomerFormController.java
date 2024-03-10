package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.dto.CustomerDto;
import org.example.dto.EmployeeDto;
import org.example.dto.tm.CustomerTm;
import org.example.dto.tm.EmployeeTm;
import org.example.model.CustomerModel;
import org.example.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    public AnchorPane custPane;
    public TableView tblCus;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableColumn col5;
    public TextField txtSearchCustomer;
    public TableColumn colEdit;
    public TableColumn colDelete;

    static String StaticMobile=null;

    private CustomerModel customerModel = new CustomerModel();

    private CustomerAddingController employeeAddingController=new CustomerAddingController();
    private ObservableList<CustomerTm>obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllCustomer();
    }

    public void setCellValueFactory() {
        col1.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        col2.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        col4.setCellValueFactory(new PropertyValueFactory<>("address"));
        col5.setCellValueFactory(new PropertyValueFactory<>("date"));

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

    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
    }

    public void searchOnAction(MouseEvent mouseEvent) {
    }
}
