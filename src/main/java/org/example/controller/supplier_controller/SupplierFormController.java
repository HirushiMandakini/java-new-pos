package org.example.controller.supplier_controller;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.controller.employee_controller.EmployeeAddingFormController;
import org.example.dto.EmployeeDto;
import org.example.dto.SupplierDto;
import org.example.dto.tm.EmployeeTm;
import org.example.dto.tm.SupplierTm;
import org.example.model.EmployeeModel;
import org.example.model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    static String StaticMobile=null;

    private SupplierModel supplierModel = new SupplierModel();

    private SupplierAddingController supplierAddingController=new SupplierAddingController();
    private ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllSupplier();

    }

    public void setCellValueFactory() {
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colFname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        colLname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEditbtn.setCellValueFactory(new PropertyValueFactory<>("update"));
        colDeletebtn.setCellValueFactory(new PropertyValueFactory<>("Remove"));

    }

    public void loadAllSupplier(){
        var model = new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try{
            List<SupplierDto> dtoList = model.getAllSupplier();
            for (SupplierDto dto : dtoList){

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
                        new SupplierTm(
                                dto.getMobile(),
                                dto.getFname(),
                                dto.getLname(),
                                dto.getEmail(),
                                dto.getCompany(),
                                update,
                                delete

                        )
                );
            }
            tblSup.setItems(obList);
        }catch (SQLException | IOException e){
            throw new RuntimeException(e);
        }
    }

    private  void setUpdateBtnOnAction( Button update,String mobile) throws IOException {

        update.setOnAction((e) -> {
                    StaticMobile=mobile;

                    obList.removeIf(supplierTm -> supplierTm.getMobile().equals(mobile));
                    tblSup.refresh();
                    loadAllSupplier();

                    Parent anchorpane = null;
                    try {
                        anchorpane = FXMLLoader.load(getClass().getResource("/view/supplier_update.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Scene scene = new Scene(anchorpane);

                    Stage stage = new Stage();
                    stage.setTitle("Supplier Updating Form");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            loadAllSupplier();
                        }
                    });

                }
        );

    }

    private void setDeleteBtnOnAction(Button btn,String mobile){
        btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        obList.removeIf(supplierTm -> supplierTm.getMobile().equals(mobile));
                        tblSup.refresh();
                        deleteSupplier(mobile);
                    }
                }
        );
    }
    private void deleteSupplier(String mobile) {
        try {
            boolean isDeleted = SupplierModel.deleteSupplier(mobile);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        tblSup.refresh();
        loadAllSupplier();
    }





    public void searchOnAction(MouseEvent mouseEvent) {

    }

    public void txtSearchOnAction(KeyEvent keyEvent) {
        String mobile = txtSearch.getText();
        ArrayList<SupplierDto> supplierDtos = null;
        try {
            supplierDtos = supplierModel.searchByMobile(mobile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tblSup.getItems().clear();

        for (SupplierDto dto : supplierDtos){

            ////////// Update button////////////////////
            Button update = new Button();
            try {
                setUpdateBtnOnAction(update, dto.getMobile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
                    new SupplierTm(
                            dto.getMobile(),
                            dto.getFname(),
                            dto.getLname(),
                            dto.getEmail(),
                            dto.getCompany(),
                            update,
                            delete

                    )
            );
        }
        tblSup.setItems(obList);
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
