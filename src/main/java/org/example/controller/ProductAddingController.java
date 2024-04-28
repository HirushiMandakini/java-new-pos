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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.codehaus.stax2.ri.SingletonIterator;
import org.example.controller.supplier_controller.SupplierAddingController;
import org.example.dto.CompanyDto;
import org.example.dto.ProductDto;
import org.example.dto.SupplierDto;
import org.example.dto.tm.ProductTm;
import org.example.dto.tm.SupplierTm;
import org.example.model.ProductModel;
import org.example.model.SupplierModel;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductAddingController {
    public TextField txtProductName;
    public TextField txtCostPrice;
    public TextField txtCategory;
    public TextField txtMrp;
    public TextField txtInQty;
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
    public DatePicker dateExp;
    public Label lblTot;
    public TableColumn colProductName;
    public TableColumn colCategory;
    public TableColumn colInQty;
    public TableColumn colCost;
    public TableColumn colMRP;
    public TableColumn colStockbin;
    public TableColumn colMFD;
    public TableColumn colEXP;
    public TableColumn colMinStock;
    public TableColumn colBarcode;
    public TableColumn colSupplier;
    public TableColumn colTotPayment;
    public TableColumn colInvNum;
    public TableColumn colPaidAmount;
    public TableColumn colDuebalance;
    public TableColumn colInvDate;
    public TableColumn colDueDate;
    public TableColumn colUpdate;
    public TableColumn colRemove;
    public TableColumn colFreeQty;
    public TableColumn colTotQty;
    public TableView tblProduct;

    static String StaticBarcode=null;

    private ProductModel productModel = new ProductModel();

    private ObservableList<ProductTm> obList = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
    loadCmb();
    loadSupplierNames();
    setCellValueFactory();
    }
    public void setCellValueFactory() {
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colInQty.setCellValueFactory(new PropertyValueFactory<>("inQty"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        colMRP.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colStockbin.setCellValueFactory(new PropertyValueFactory<>("stockBin"));
        colMFD.setCellValueFactory(new PropertyValueFactory<>("mfdDate"));
        colEXP.setCellValueFactory(new PropertyValueFactory<>("expDate"));
        colMinStock.setCellValueFactory(new PropertyValueFactory<>("minStockAlert"));
        colBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colInvNum.setCellValueFactory(new PropertyValueFactory<>("invNum"));
        colTotPayment.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        colDuebalance.setCellValueFactory(new PropertyValueFactory<>("dueBalance"));
        colInvDate.setCellValueFactory(new PropertyValueFactory<>("invDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colFreeQty.setCellValueFactory(new PropertyValueFactory<>("freeQty"));
        colTotQty.setCellValueFactory(new PropertyValueFactory<>("totQty"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }

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
        DatePicker dateExpire = new DatePicker(); // Initialize dateExpire

        String expire1 = "";
        if (dateExpire != null && dateExpire.getValue() != null) {
            expire1 = dateExpire.getValue().toString();
        } else {
            // Handle the case where dateExpire is null or its value is null
            System.err.println("dateExpire is null or its value is null");
        }

        String barcode =barcord.getText();
        String productName = txtProductName.getText();
        String category = txtCategory.getText();
        double costPrice = Double.parseDouble(txtCostPrice.getText());
        double mrp = Double.parseDouble(txtMrp.getText());
        String inQty = txtInQty.getText();
        String freeQty = txtFreeQty.getText();
        String totQty= lblTot.getText();
        String stockAlert = txtStockAlert.getText();
        String stockBin = txtStockBin.getText();
        String invoiceNum = txtInvoiceNum.getText();
        String supplier = comboSupplier.getValue().toString();
        String expire = dateExpire.getValue().toString();
        String manufacture = DateManufacture.getValue().toString();
        double totalPayment = Double.parseDouble(lblInvoiceAmount.getText());
        double paidAmount = Double.parseDouble(txtPaidAmount.getText());
        double dueBalance = Double.parseDouble(lblBalance.getText());
        String invDate = invoiceDate.getValue().toString();
        String dueDate = invoiceDueDate.getValue().toString();

        var dto =new ProductDto(barcode,productName,category,costPrice,mrp,inQty,freeQty,totQty,stockAlert,stockBin,invoiceNum,supplier,expire,manufacture,totalPayment,paidAmount,dueBalance,invDate,dueDate);

        try{
            boolean isSaved = ProductModel.saveProduct(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully saved!!!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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
                //loadAllProduct();
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


    private void loadCmb() throws SQLException {
        comboSupplier.getItems().clear();
        List<SupplierDto> allSupplier = new SupplierModel().getAllSupplier();
        for (SupplierDto supplierDto : allSupplier) {
            comboSupplier.getItems().add(supplierDto.getFname());
            loadSupplierNames();
        }
    }
    private void loadSupplierNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> idList = SupplierModel.getAllSupplier();

            for (SupplierDto dto : idList) {
                obList.add(dto.getFname()+" "+dto.getLname());
            }
            comboSupplier.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void comboSupplierOnAction(ActionEvent actionEvent) {
        loadSupplierNames();
    }
    public void loadAllProduct(){
        var model = new ProductModel();

        ObservableList<ProductTm> obList = FXCollections.observableArrayList();

        try{
            List<ProductDto> dtoList = model.getAllProduct();
            for (ProductDto dto : dtoList){

                ////////// Update button////////////////////
                Button update = new Button();
                setUpdateBtnOnAction(update, dto.getBarcode());
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
                setDeleteBtnOnAction(delete, dto.getBarcode());
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
                        new ProductTm(
                                dto.getBarcode(),
                                dto.getProductName(),
                                dto.getCategory(),
                                dto.getCostPrice(),
                                dto.getSellingPrice(),
                                dto.getInQty(),
                                dto.getFreeQty(),
                                dto.getTotQty(),
                                dto.getMinStockAlert(),
                                dto.getStockBin(),
                                dto.getInvNum(),
                                dto.getSupplierName(),
                                dto.getExpDate(),
                                dto.getMfdDate(),
                                dto.getTotalPayment(),
                                dto.getPaidAmount(),
                                dto.getDueBalance(),
                                dto.getInvDate(),
                                dto.getDueDate(),
                                update,
                                delete
                        )
                );
            }
            tblProduct.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void setDeleteBtnOnAction(Button delete, String barcode) {
        delete.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        obList.removeIf(ProductTm -> ProductTm.getBarcode().equals(barcode));
                        tblProduct.refresh();
                        deleteProduct(barcode);
                    }
                }
        );
    }
    private void deleteProduct(String barcode) {
        try {
            boolean isDeleted = ProductModel.deleteProduct(barcode);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "product not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        tblProduct.refresh();
        loadAllProduct();
    }

    private void setUpdateBtnOnAction(Button update, String barcode) {
        update.setOnAction((e) -> {
                    StaticBarcode=barcode;

                    obList.removeIf(ProductTm -> ProductTm.getBarcode().equals(barcode));
                    tblProduct.refresh();
                    loadAllProduct();

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
                            loadAllProduct();
                        }
                    });

                }
        );
    }

}
