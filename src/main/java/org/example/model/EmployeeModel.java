package org.example.model;

import org.example.db.DbConnection;
import org.example.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static ResultSet checkCredential(String enteredUname,String enteredPword) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM employee WHERE emp_email=?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, enteredUname);
            ResultSet resultSet = pstm.executeQuery();
            return resultSet;

        }

        public static List<EmployeeDto> getAllEmployee() throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM employee";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet =pstm.executeQuery();

            ArrayList<EmployeeDto> dtoList = new ArrayList<>();

            while (resultSet.next()){
                dtoList.add(
                        new EmployeeDto(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9),
                                resultSet.getString(10)
                        )
                );

            }return dtoList;
        }

        //fetching the employee details for popup window
    public static EmployeeDto getEmployee(String email) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE emp_email =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,email );
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmp_email(resultSet.getString(1));
            employeeDto.setEmp_pword(resultSet.getString(2));
            employeeDto.setPosition(resultSet.getString(3));
            employeeDto.setF_name(resultSet.getString(4));
            employeeDto.setL_name(resultSet.getString(5));
            employeeDto.setNic(resultSet.getString(6));
            employeeDto.setAddress(resultSet.getString(7));
            employeeDto.setGender(resultSet.getString(8));
            employeeDto.setContact_num(resultSet.getString(9));
            employeeDto.setDate(resultSet.getString(10));
            return employeeDto;
        }
        return null;
    }

        public static boolean saveEmployee (EmployeeDto employeeDto) throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee (emp_email,emp_pword,position,f_name,l_name,nic,address,gender,contact_num,date) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, employeeDto.getEmp_email());
        pstm.setString(2, employeeDto.getEmp_pword());
        pstm.setString(3, employeeDto.getPosition());
        pstm.setString(4, employeeDto.getF_name());
        pstm.setString(5, employeeDto.getL_name());
        pstm.setString(6, employeeDto.getNic());
        pstm.setString(7, employeeDto.getAddress());
        pstm.setString(8, employeeDto.getGender());
        pstm.setString(9, employeeDto.getContact_num());
        pstm.setString(10, employeeDto.getDate());

        boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;

         }

         public static boolean deleteEmployee(String emp_email) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE emp_email=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,emp_email);

        boolean isDeleted = pstm.executeUpdate()>0;
        return isDeleted;
         }

         public static boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET emp_pword=?,position=?,f_name=?,l_name=?,nic=?,address=?,gender=?,contact_num=?,date=? WHERE emp_email=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_pword());
        pstm.setString(2, dto.getPosition());
        pstm.setString(3, dto.getF_name());
        pstm.setString(4, dto.getL_name());
        pstm.setString(5,dto.getNic());
        pstm.setString(6, dto.getAddress());
        pstm.setString(7, dto.getGender());
        pstm.setString(8, dto.getContact_num());
        pstm.setString(9, dto.getDate());
        pstm.setString(10, dto.getEmp_email());

        boolean isUpdated = pstm.executeUpdate() >0;
        return isUpdated;
    }

    public ArrayList<EmployeeDto> searchByEmail(String text) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE emp_email LIKE ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, text + "%");

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<EmployeeDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new EmployeeDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9),
                            resultSet.getString(10)
                    )
            );

        }return dtoList;
    }

    }

