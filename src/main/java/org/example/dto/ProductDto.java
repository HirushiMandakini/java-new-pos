package org.example.dto;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductDto {
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
}
