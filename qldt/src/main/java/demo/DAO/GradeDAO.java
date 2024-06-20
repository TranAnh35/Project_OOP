package demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import demo.Data.DataBase;

public class GradeDAO {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void add(String classSectionID, String studentId, float cc, float gk, float ck){
        if(!check(classSectionID, studentId)){
            try{
                connect = DataBase.connecDb();
                String sql = "INSERT INTO Grade (classSectionID, studentId, Grade_CC, Grade_Midterm, Grade_Endterm) VALUES (?, ?, ?, ?, ?);";
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, classSectionID);
                prepare.setString(2, studentId);
                prepare.setFloat(3, cc);
                prepare.setFloat(4, gk);
                prepare.setFloat(5, ck);

                prepare.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            connect = DataBase.connecDb();
            String sql = "UPDATE Grade SET Grade_CC = ?, Grade_Midterm = ?, Grade_Endterm = ? WHERE classSectionID = ? AND studentId = ?;";
            try{
                prepare = connect.prepareStatement(sql);
                prepare.setFloat(1, cc);
                prepare.setFloat(2, gk);
                prepare.setFloat(3, ck);
                prepare.setString(4, classSectionID);
                prepare.setString(5, studentId);

                prepare.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean check(String classSectionID, String studentId){
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM Grade WHERE classSectionID = ? AND studentId = ?;";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, classSectionID);
            prepare.setString(2, studentId);
            result = prepare.executeQuery();
            if(result.next())
                return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public float[] getGrade(String classSectionID, String studentId){
        float[] grade = new float[3];
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM Grade WHERE classSectionID = ? AND studentId = ?;";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, classSectionID);
            prepare.setString(2, studentId);
            result = prepare.executeQuery();
            if(result.next()){
                grade[0] = result.getFloat("Grade_CC");
                grade[1] = result.getFloat("Grade_Midterm");
                grade[2] = result.getFloat("Grade_Endterm");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return grade;
    }
}
