package org.example.dto;
import lombok.*;
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

}
