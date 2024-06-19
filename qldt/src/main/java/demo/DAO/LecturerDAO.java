package demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.Data.DataBase;
import demo.Entity.Lecturer;

public class LecturerDAO {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    // Hàm lấy tên của giảng viên
    public String getNameLecturer(String LecturerID){
        String firstName = "";
        String lastName = "";
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM Lecturer WHERE LecturerID = ?;";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, LecturerID);
            result = prepare.executeQuery();
            while(result.next()){
                firstName = result.getString("firstName");
                lastName = result.getString("lastName");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return firstName + " " + lastName;
    }

    public void updateLecturer(Lecturer lecturer, String LecturerID){
        try{
            connect = DataBase.connecDb();
            String sql = "UPDATE Lecturer SET firstName = ?, lastName = ?, phoneNumber = ?, email = ?, gender = ? WHERE LecturerID = ?;";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, lecturer.getFirstName());
            prepare.setString(2, lecturer.getLastName());
            prepare.setString(3, lecturer.getPhoneNumber());
            prepare.setString(4, lecturer.getEmail());
            prepare.setString(5, lecturer.getGender());
            prepare.setString(6, LecturerID);

            prepare.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}