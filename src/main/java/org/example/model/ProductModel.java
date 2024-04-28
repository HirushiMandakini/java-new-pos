package org.example.model;

import org.example.db.DbConnection;
import org.example.dto.CompanyDto;
import org.example.dto.CustomerDto;
import org.example.dto.ProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {


    public static List<ProductDto> getAllProduct() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM product";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet= pstm.executeQuery();

        ArrayList<ProductDto> dtoArrayList= new ArrayList<>();

        while (resultSet.next()){
            dtoArrayList.add(
                    new ProductDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9),
                            resultSet.getString(10),
                            resultSet.getString(11),
                            resultSet.getString(12),
                            resultSet.getString(13),
                            resultSet.getString(14),
                            resultSet.getDouble(15),
                            resultSet.getDouble(16),
                            resultSet.getDouble(17),
                            resultSet.getString(18),
                            resultSet.getString(19)
                    )
            );
        }
        return dtoArrayList;
    }
    public static ProductDto getproduct(String barcode) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM product WHERE barcode =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,barcode);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            ProductDto productDto=new ProductDto();
            productDto.setBarcode(resultSet.getString(1));
            productDto.setProductName(resultSet.getString(2));
            productDto.setCategory(resultSet.getString(3));
            productDto.setCostPrice(resultSet.getDouble(4));
            productDto.setSellingPrice(resultSet.getDouble(5));
            productDto.setInQty(resultSet.getString(6));
            productDto.setFreeQty(resultSet.getString(7));
            productDto.setTotQty(resultSet.getString(8));
            productDto.setMinStockAlert(resultSet.getString(9));
            productDto.setStockBin(resultSet.getString(10));
            productDto.setInvNum(resultSet.getString(11));
            productDto.setSupplierName(resultSet.getString(12));
            productDto.setExpDate(resultSet.getString(13));
            productDto.setMfdDate(resultSet.getString(14));
            productDto.setTotalPayment(resultSet.getDouble(15));
            productDto.setPaidAmount(resultSet.getDouble(16));
            productDto.setDueBalance(resultSet.getDouble(17));
            productDto.setInvDate(resultSet.getString(18));
            productDto.setDueDate(resultSet.getString(19));

            return productDto;
        }
        return null;
    }
    public static boolean saveProduct (ProductDto productDto) throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO product (barcode,productName,category,costPrice,sellingPrice,inQty,freeQty,totQty,minStockAlert,stockBin,invNum,supplierName,expDate,mfdDate,totalPayment,paidAmount,dueBalance,invDate,dueDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, productDto.getBarcode());
        pstm.setString(2, productDto.getProductName());
        pstm.setString(3, productDto.getCategory());
        pstm.setDouble(4, productDto.getCostPrice());
        pstm.setDouble(5, productDto.getSellingPrice());
        pstm.setString(6, productDto.getInQty());
        pstm.setString(7, productDto.getFreeQty());
        pstm.setString(8, productDto.getTotQty());
        pstm.setString(9, productDto.getMinStockAlert());
        pstm.setString(10, productDto.getStockBin());
        pstm.setString(11, productDto.getInvNum());
        pstm.setString(12, productDto.getSupplierName());
        pstm.setString(13, productDto.getExpDate());
        pstm.setString(14, productDto.getMfdDate());
        pstm.setString(15, String.valueOf(productDto.getTotalPayment()));
        pstm.setString(16, String.valueOf(productDto.getPaidAmount()));
        pstm.setString(17, String.valueOf(productDto.getDueBalance()));
        pstm.setString(18, productDto.getInvDate());
        pstm.setString(19, productDto.getDueDate());

        boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;

    }
    public static boolean deleteProduct(String barcode) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM product WHERE barcode=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,barcode);

        boolean isDeleted = pstm.executeUpdate()>0;
        return isDeleted;
    }
    public static boolean updateProduct(ProductDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE product SET productName=?,category=?,costPrice=?,sellingPrice=?,inQty=?,freeQty=?,totQty=?,minStockAlert=?,stockBin=?,invNum=?,supplierName=?,expDate=?,mfdDate=?,totalPayment=?,paidAmount=?,dueBalance=?,invDate=?,dueDate=? WHERE barcode=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getProductName());
        pstm.setString(2, dto.getCategory());
        pstm.setDouble(3, dto.getCostPrice());
        pstm.setDouble(4, dto.getSellingPrice());
        pstm.setString(5, dto.getInQty());
        pstm.setString(6, dto.getFreeQty());
        pstm.setString(7, dto.getTotQty());
        pstm.setString(8, dto.getMinStockAlert());
        pstm.setString(9, dto.getStockBin());
        pstm.setString(10, dto.getInvNum());
        pstm.setString(11, dto.getSupplierName());
        pstm.setString(12, dto.getExpDate());
        pstm.setString(13, dto.getMfdDate());
        pstm.setString(14, dto.getBarcode());
        pstm.setDouble(15, dto.getTotalPayment());
        pstm.setDouble(16, dto.getPaidAmount());
        pstm.setDouble(17, dto.getDueBalance());
        pstm.setString(18, dto.getInvDate());
        pstm.setString(19, dto.getDueDate());


        boolean isUpdated = pstm.executeUpdate() >0;
        return isUpdated;
    }
    public ArrayList<ProductDto> searchByBarcode(String searchText) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM product WHERE barcode LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, searchText + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<ProductDto> productList = new ArrayList<>();

        while (resultSet.next()) {
            ProductDto productDto = new ProductDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getString(13),
                    resultSet.getString(14),
                    resultSet.getDouble(15),
                    resultSet.getDouble(16),
                    resultSet.getDouble(17),
                    resultSet.getString(18),
                    resultSet.getString(19)
            );
            productList.add(productDto);
        }

        return productList;
    }

}
