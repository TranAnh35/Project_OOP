package demo.DAO;

/*
 * Class này dùng để lưu trữ thông tin tiến trình học tập của sinh viên
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import demo.Course.ClassSection;
import demo.Data.DataBase;
import demo.dashBoardController;
import demo.dashBoardController.CourseInfo;

public class StudentCourseProgressDAO {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    // Hàm tạo một ID mới cho tiến trình học tập của sinh viên
    public int generateUniqueStudentCourseProgressID(){
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT COUNT(*) FROM StudentCourseProgress");
            result = prepare.executeQuery();
            if(result.next()){
                return result.getInt(1) + 1;
            }
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 1;
        }
    }

    // Hàm thêm một tiến trình học tập mới cho sinh viên
    public void addStudentCourseProgress(String studentID, String courseID){
        int progressID = generateUniqueStudentCourseProgressID();
        try{
            connect = DataBase.connecDb();
            String sql = "INSERT INTO StudentCourseProgress (ProgressID, studentID, courseID) VALUES (?, ?, ?)";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, progressID);
            prepare.setString(2, studentID);
            prepare.setString(3, courseID);

            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getProgressID(String studentID, String courseID){
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT ProgressID FROM StudentCourseProgress WHERE studentID = ? AND courseID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, studentID);
            prepare.setString(2, courseID);

            result = prepare.executeQuery();
            if(result.next()){
                return result.getInt("ProgressID");
            }
            return -1;
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public void removeStudentCourseProgress(String studentID, String courseID){
        int id = getProgressID(studentID, courseID);
        try{
            connect = DataBase.connecDb();
            String sql = "DELETE FROM StudentCourseProgress WHERE ProgressID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, id);

            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Hàm cập nhật trạng thái học tập của sinh viên
    public void updateStatus(String studentID, String courseID, double grade){
        String status = (grade > 4.0) ? "Completed" : "Failed";
        try{
            connect = DataBase.connecDb();
            String sql = "UPDATE StudentCourseProgress SET status = ? WHERE studentID = ? AND courseID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, status);
            prepare.setString(2, studentID);
            prepare.setString(3, courseID);

            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkCourseProgress(String studentID, String courseID){
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM StudentCourseProgress WHERE studentID = ? AND courseID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, studentID);
            prepare.setString(2, courseID);

            result = prepare.executeQuery();
            return result.next();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // Hàm lấy danh sách các lớp học phần mà sinh viên đã đăng ký
    public List<ClassSection> getStudentClassSections(String studentID, String status){
        List<ClassSection> classSections = new ArrayList<>();
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM StudentCourseProgress WHERE studentID = ? AND status = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, studentID);
            prepare.setString(2, status);

            result = prepare.executeQuery();
            while(result.next()){
                String classSectionID = result.getString("classSectionID");
                ClassSectionDAO classSectionDAO = new ClassSectionDAO();
                ClassSection classSection = classSectionDAO.getClassSection(classSectionID);
                classSections.add(classSection);
            }
            return classSections;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CourseInfo> getCourses(){
        String id = new AccountDAO().getUserTypeID(new AccountDAO().getCurrentAccount().getAccountID());

        List<CourseInfo> courses = new ArrayList<>();
        dashBoardController x = new dashBoardController();

        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM StudentCourseProgress WHERE studentID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, id);

            result = prepare.executeQuery();
            int stt = 0;
            while(result.next()){
                String courseID = result.getString("courseID");
                String courseName = new CourseDAO().getCourseName(courseID);
                String status = result.getString("status");
                double grade = result.getDouble("finalgrade");
                int times = result.getInt("times");
                CourseInfo course = x.new CourseInfo(++stt, courseID, courseName, times, grade, status);
                courses.add(course);
            }
            return courses;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CourseInfo> setCourseCompletedData(){
        String id = new AccountDAO().getUserTypeID(new AccountDAO().getCurrentAccount().getAccountID());

        List<CourseInfo> courses = new ArrayList<>();
        dashBoardController x = new dashBoardController();

        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM StudentCourseProgress WHERE studentID = ? AND status = 'Completed'";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, id);

            result = prepare.executeQuery();
            int stt = 0;
            while(result.next()){
                String courseID = result.getString("courseID");
                String courseName = new CourseDAO().getCourseName(courseID);
                String status = result.getString("status");
                double grade = result.getDouble("finalgrade");
                int times = result.getInt("times");
                CourseInfo course = x.new CourseInfo(++stt, courseID, courseName, times, grade, status);
                courses.add(course);
            }
            return courses;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CourseInfo> setCourseFailedData(){
        String id = new AccountDAO().getUserTypeID(new AccountDAO().getCurrentAccount().getAccountID());

        List<CourseInfo> courses = new ArrayList<>();
        dashBoardController x = new dashBoardController();

        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM StudentCourseProgress WHERE studentID = ? AND status = 'Failed'";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, id);

            result = prepare.executeQuery();
            int stt = 0;
            while(result.next()){
                String courseID = result.getString("courseID");
                String courseName = new CourseDAO().getCourseName(courseID);
                String status = result.getString("status");
                double grade = result.getDouble("finalgrade");
                int times = result.getInt("times");
                CourseInfo course = x.new CourseInfo(++stt, courseID, courseName, times, grade, status);
                courses.add(course);
            }
            return courses;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CourseInfo> setCourseInProgressData(){
        String id = new AccountDAO().getUserTypeID(new AccountDAO().getCurrentAccount().getAccountID());

        List<CourseInfo> courses = new ArrayList<>();
        dashBoardController x = new dashBoardController();

        try{
            connect = DataBase.connecDb();
            String sql = "SELECT * FROM StudentCourseProgress WHERE studentID = ? AND status = 'InProgress'";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, id);

            result = prepare.executeQuery();
            int stt = 0;
            while(result.next()){
                String courseID = result.getString("courseID");
                String courseName = new CourseDAO().getCourseName(courseID);
                String status = result.getString("status");
                double grade = result.getDouble("finalgrade");
                int times = result.getInt("times");
                CourseInfo course = x.new CourseInfo(++stt, courseID, courseName, times, grade, status);
                courses.add(course);
            }
            return courses;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void updateTimes(String studentID, String courseID){
        try{
            connect = DataBase.connecDb();
            String sql = "UPDATE StudentCourseProgress SET times = times + 1 WHERE studentID = ? AND courseID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, studentID);
            prepare.setString(2, courseID);

            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getNumberCourse(String studentId){
        try{
            connect = DataBase.connecDb();
            String sql = "SELECT COUNT(*) FROM StudentCourseProgress WHERE studentID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, studentId);

            result = prepare.executeQuery();
            if(result.next()){
                return result.getInt(1);
            }
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void updateScore(String studentID, String courseID, double grade){
        try{
            connect = DataBase.connecDb();
            String sql = "UPDATE StudentCourseProgress SET finalgrade = ? WHERE studentID = ? AND courseID = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setDouble(1, grade);
            prepare.setString(2, studentID);
            prepare.setString(3, courseID);

            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
