package org.example.dto.tm;
import lombok.*;

import java.awt.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductTm {
    private String barcode;
    private String productName;
    private String category;
    private double costPrice;
    private double sellingPrice;
    private String inQty;
    private String freeQty;
    private String totQty;
    private String minStockAlert;
    private String stockBin;
    private String invNum;
    private String supplierName;
    private String expDate;
    private String mfdDate;
    private double totalPayment;
    private double paidAmount;
    private double dueBalance;
    private String invDate;
    private String dueDate;
    private javafx.scene.control.Button update;
    private javafx.scene.control.Button delete;

}
