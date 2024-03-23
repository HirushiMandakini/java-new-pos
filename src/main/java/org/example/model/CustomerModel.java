package org.example.model;

import org.example.db.DbConnection;
import org.example.dto.CustomerDto;
import org.example.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static List<CustomerDto> getAllCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet= pstm.executeQuery();

        ArrayList<CustomerDto> dtoArrayList= new ArrayList<>();

        while (resultSet.next()){
            dtoArrayList.add(
                    new CustomerDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return dtoArrayList;
    }
    public static CustomerDto getCustomer(String mobile) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE mobile =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,mobile );
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setMobile(resultSet.getString(1));
            customerDto.setF_name(resultSet.getString(2));
            customerDto.setL_name(resultSet.getString(3));
            customerDto.setAddress(resultSet.getString(4));
            customerDto.setDate(resultSet.getString(5));

            return customerDto;
        }
        return null;
    }
    public static boolean saveCustomer (CustomerDto customerDto) throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer (mobile,f_name,l_name,address,date) VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, customerDto.getMobile());
        pstm.setString(2, customerDto.getF_name());
        pstm.setString(3, customerDto.getL_name());
        pstm.setString(4, customerDto.getAddress());
        pstm.setString(5, String.valueOf(customerDto.getDate()));

        boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;

    }
    public static boolean deleteCustomer(String mobile) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE mobile=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,mobile);

        boolean isDeleted = pstm.executeUpdate()>0;
        return isDeleted;
    }
    public static boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET f_name=?,l_name=?,address=?,date=? WHERE mobile=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getF_name());
        pstm.setString(2, dto.getL_name());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, String.valueOf(dto.getDate()));
        pstm.setString(5,dto.getMobile());

        boolean isUpdated = pstm.executeUpdate() >0;
        return isUpdated;
    }
    public ArrayList<CustomerDto> searchByMobile(String text) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE mobile LIKE ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, text + "%");

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<CustomerDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new CustomerDto(
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
