package org.example.model;

import org.example.db.DbConnection;
import org.example.dto.CompanyDto;
import org.example.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static List<SupplierDto> getAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet =pstm.executeQuery();

        ArrayList<SupplierDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new SupplierDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );

        }return dtoList;
    }

    //fetching the supplier details for popup window
    public static SupplierDto getSupplier(String mobile) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier WHERE mobile =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,mobile );
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            SupplierDto supplierDto = new SupplierDto();
            supplierDto.setMobile(resultSet.getString(1));
            supplierDto.setFname(resultSet.getString(2));
            supplierDto.setLname(resultSet.getString(3));
            supplierDto.setEmail(resultSet.getString(4));
            supplierDto.setCompany(resultSet.getString(5));

            return supplierDto;
        }
        return null;
    }

    public static boolean saveSupplier (SupplierDto supplierDto) throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier (mobile,fname,lname,email,company) VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, supplierDto.getMobile());
        pstm.setString(2, supplierDto.getFname());
        pstm.setString(3, supplierDto.getLname());
        pstm.setString(4, supplierDto.getEmail());
        pstm.setString(5, supplierDto.getCompany());

        boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;

    }

    public static boolean deleteSupplier(String mobile) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE mobile=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,mobile);

        boolean isDeleted = pstm.executeUpdate()>0;
        return isDeleted;
    }

    public static boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET fname=?,lname=?,email=?,company=? WHERE mobile=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getFname());
        pstm.setString(2, dto.getLname());
        pstm.setString(3, dto.getEmail());
        pstm.setString(4, dto.getCompany());
        pstm.setString(5, dto.getMobile());

        boolean isUpdated = pstm.executeUpdate() >0;
        return isUpdated;
    }

    public ArrayList<SupplierDto> searchByMobile(String text) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier WHERE mobile LIKE ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, text + "%");

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<SupplierDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new SupplierDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );

        }return dtoList;
    }
    
}
