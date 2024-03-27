package org.example.controller.employee_controller;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.dto.EmployeeDto;
import org.example.dto.tm.EmployeeTm;
import org.example.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeFormController {
    public AnchorPane empPane;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col4;
    public TableColumn col3;
    public TableColumn col5;
    public TableColumn col6;
    public TableColumn col7;
    public TableColumn col8;
    public TableColumn col9;
    public TableColumn col10;
    public TableView tblEmp;
    public TableColumn col11;
    public TableColumn col12;
    public AnchorPane expirePane;
    public TableView tblExpire;
    public DatePicker comboBoxFromDate;
    public DatePicker comboBoxToDate;

    static String StaticEmail=null;
    public TextField txtSearch;

    private EmployeeModel employeeModel = new EmployeeModel();

    private EmployeeAddingFormController employeeAddingFormController=new EmployeeAddingFormController();
    private ObservableList<EmployeeTm>obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllEmployee();

    }

    public void setCellValueFactory() {
        col1.setCellValueFactory(new PropertyValueFactory<>("emp_email"));
        col2.setCellValueFactory(new PropertyValueFactory<>("emp_pword"));
        col3.setCellValueFactory(new PropertyValueFactory<>("position"));
        col4.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        col5.setCellValueFactory(new PropertyValueFactory<>("l_name"));
        col6.setCellValueFactory(new PropertyValueFactory<>("nic"));
        col7.setCellValueFactory(new PropertyValueFactory<>("address"));
        col8.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col9.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
        col10.setCellValueFactory(new PropertyValueFactory<>("date"));
        col11.setCellValueFactory(new PropertyValueFactory<>("update"));
        col12.setCellValueFactory(new PropertyValueFactory<>("Remove"));

    }

    public void loadAllEmployee(){
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try{
            List<EmployeeDto> dtoList = model.getAllEmployee();
            for (EmployeeDto dto : dtoList){

    ////////// Update button////////////////////
                Button update = new Button();
                setUpdateBtnOnAction(update, dto.getEmp_email());
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
                setDeleteBtnOnAction(delete, dto.getEmp_email());
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
                        new EmployeeTm(
                                dto.getEmp_email(),
                                dto.getEmp_pword(),
                                dto.getPosition(),
                                dto.getF_name(),
                                dto.getL_name(),
                                dto.getNic(),
                                dto.getAddress(),
                                dto.getGender(),
                                dto.getContact_num(),
                                dto.getDate(),
                                update,
                                delete

                        )
                );
            }
            tblEmp.setItems(obList);
        }catch (SQLException | IOException e){
            throw new RuntimeException(e);
        }
    }

    private  void setUpdateBtnOnAction( Button update,String email) throws IOException {

        update.setOnAction((e) -> {
            StaticEmail=email;

                        obList.removeIf(employeeTm -> employeeTm.getEmp_email().equals(email));
                        tblEmp.refresh();
                        loadAllEmployee();

                        Parent anchorpane = null;
                        try {
                            anchorpane = FXMLLoader.load(getClass().getResource("/view/employee_update.fxml"));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        Scene scene = new Scene(anchorpane);

                        Stage stage = new Stage();
                        stage.setTitle("Employee Updating Form");
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.show();
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            loadAllEmployee();
                        }
                    });

                }
              );

    }

    private void setDeleteBtnOnAction(Button btn,String email){
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                obList.removeIf(employeeTm -> employeeTm.getEmp_email().equals(email));
                tblEmp.refresh();
                deleteEmployee(email);
            }
        }
        );
    }
    private void deleteEmployee(String id) {
        try {
            boolean isDeleted = EmployeeModel.deleteEmployee(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        tblEmp.refresh();
        loadAllEmployee();
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorpane = FXMLLoader.load(getClass().getResource("/view/employee_adding_form.fxml"));
        Scene scene = new Scene(anchorpane);

        Stage stage = new Stage();
        stage.setTitle("Employee Adding Form");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loadAllEmployee();
            }
        });
        stage.centerOnScreen();
        stage.show();
    }

    public void searchOnAction(MouseEvent mouseEvent) {

    }

    public void txtSearchOnAction(KeyEvent keyEvent) throws SQLException, IOException {
        String text = txtSearch.getText();
        ArrayList<EmployeeDto> employeeDtos = employeeModel.searchByEmail(text);
        tblEmp.getItems().clear();

        for (EmployeeDto dto : employeeDtos){

            ////////// Update button////////////////////
            Button update = new Button();
            setUpdateBtnOnAction(update, dto.getEmp_email());
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
            setDeleteBtnOnAction(delete, dto.getEmp_email());
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
                    new EmployeeTm(
                            dto.getEmp_email(),
                            dto.getEmp_pword(),
                            dto.getPosition(),
                            dto.getF_name(),
                            dto.getL_name(),
                            dto.getNic(),
                            dto.getAddress(),
                            dto.getGender(),
                            dto.getContact_num(),
                            dto.getDate(),
                            update,
                            delete

                    )
            );
        }
        tblEmp.setItems(obList);


    }
}
