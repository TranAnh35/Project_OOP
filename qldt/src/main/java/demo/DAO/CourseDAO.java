package demo.DAO;

/*
 * Class này dùng để lấy thông tin về các khóa học từ database
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import demo.Course.Course;
import demo.Data.DataBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseDAO {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    // Hàm lấy thông tin của tất cả các khóa học
    public ObservableList<Course> getCourses() {
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT * FROM course");
            result = prepare.executeQuery();
            while (result.next()) {
                Course course = new Course(result.getString("courseID"), result.getString("nameCourse"),
                        result.getDouble("tuitionFee"), result.getInt("credits"));
                courseList.add(course);
            }
            return courseList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy thông tin của một khóa học
    public <T> Object getCourseInfo(String courseName){
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT * FROM course WHERE nameCourse = ?");
            prepare.setString(1, courseName);
            result = prepare.executeQuery();
            while (result.next()) {
                Course course = new Course(result.getString("courseID"), result.getString("nameCourse"),
                        result.getDouble("tuitionFee"), result.getInt("credits"));
                courseList.add(course);
            }
            return courseList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy ID của 1 khóa học
    public String getCourseID(String courseName){
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT courseID FROM course WHERE nameCourse = ?");
            prepare.setString(1, courseName);
            result = prepare.executeQuery();
            if(result.next()){
                return result.getString("courseID");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy tên của 1 khóa học
    public String getCourseName(String courseID){
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT nameCourse FROM course WHERE courseID = ?");
            prepare.setString(1, courseID);
            result = prepare.executeQuery();
            if(result.next()){
                return result.getString("nameCourse");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy học phí của 1 khóa học
    public double getTuitionFee(String courseID){
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT tuitionFee FROM course WHERE courseID = ?");
            prepare.setString(1, courseID);
            result = prepare.executeQuery();
            if(result.next()){
                return result.getDouble("tuitionFee");
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
