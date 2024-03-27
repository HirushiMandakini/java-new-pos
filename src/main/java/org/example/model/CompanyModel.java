package org.example.model;

import org.example.db.DbConnection;
import org.example.dto.CompanyDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyModel {
    public static List<CompanyDto> getAllCompany() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM company";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet =pstm.executeQuery();

        ArrayList<CompanyDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new CompanyDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );

        }return dtoList;
    }

    public static boolean saveCompany (CompanyDto companyDto) throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO company (company_name,hotline,location,date) VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, companyDto.getCompany_name());
        pstm.setString(2, companyDto.getHotline());
        pstm.setString(3, companyDto.getLocation());
        pstm.setString(4, companyDto.getDate());

        boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;

    }

    public static boolean deleteCompany(String company_name) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM company WHERE comany_name=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,company_name);

        boolean isDeleted = pstm.executeUpdate()>0;
        return isDeleted;
    }

    public static boolean updateCompany(CompanyDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE company SET hotline=?,location=?,date=? WHERE company_name=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getHotline());
        pstm.setString(2, dto.getLocation());
        pstm.setString(3, dto.getDate());
        pstm.setString(4, dto.getCompany_name());

        boolean isUpdated = pstm.executeUpdate() >0;
        return isUpdated;
    }

    //search company by name
    public static CompanyDto searchCompany(String name) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM company WHERE company_name = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();

        CompanyDto dto = null;

        if(resultSet.next()) {
            String company_name = resultSet.getString(1);
            String hotline = resultSet.getString(2);
            String location = resultSet.getString(3);
            String date = resultSet.getString(4);

            dto = new CompanyDto(company_name,hotline,location,date);
        }
        return dto;
    }
}
