package demo.DAO;

/*
 * Class này dùng để thao tác với bảng classsection trong database
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import demo.lecturerDashBoardController;
import demo.lecturerDashBoardController.CourseInfo;
import demo.Course.ClassSection;
import demo.Data.DataBase;

public class ClassSectionDAO {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    // Hàm lấy thông tin của tất cả các lớp học phần của một môn học
    public List<ClassSection> getClassSections(String courseID){
        List<ClassSection> classSectionList = new ArrayList<>();
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT * FROM classsection WHERE courseID = ?");
            prepare.setString(1, courseID);
            result = prepare.executeQuery();
            while (result.next()) {
                ClassSection classSection = new ClassSection(result.getString("classSectionID"), result.getString("classSectionName"),
                        result.getString("courseID"), result.getString("lecturerID"), result.getInt("enrolled"));
                classSectionList.add(classSection);
            }
            return classSectionList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getClassSectionName(String classSectionID){
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT classSectionName FROM classsection WHERE classSectionID = ?");
            prepare.setString(1, classSectionID);
            result = prepare.executeQuery();
            if(result.next()){
                return result.getString("classSectionName");
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy thông tin của tất cả các lớp học phần trong ds
    public List<ClassSection> getAllClassSections(){
        List<ClassSection> classSectionList = new ArrayList<>();
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT * FROM classsection");
            result = prepare.executeQuery();
            while (result.next()) {
                ClassSection classSection = new ClassSection(result.getString("classSectionID"), result.getString("classSectionName"),
                        result.getString("courseID"), result.getString("lecturerID"), result.getInt("enrolled"));
                classSectionList.add(classSection);
            }
            return classSectionList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy thông tin của 1 lớp học phần
    public ClassSection getClassSection(String classSectionID){
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT * FROM classsection WHERE classSectionID = ?");
            prepare.setString(1, classSectionID);
            result = prepare.executeQuery();
            if(result.next()){
                return new ClassSection(result.getString("classSectionID"), result.getString("classSectionName"),
                        result.getString("courseID"), result.getString("lecturerID"), result.getInt("enrolled"));
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy ID của một lớp học phần
    public String getClassSectionID(String classSectionName){
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT classSectionID FROM classsection WHERE classSectionName = ?");
            prepare.setString(1, classSectionName);
            result = prepare.executeQuery();
            if(result.next()){
                return result.getString("classSectionID");
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getCourseIDByClassSectionId(String classSectionId){
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT courseID FROM classsection WHERE classSectionID = ?");
            prepare.setString(1, classSectionId);
            result = prepare.executeQuery();
            if(result.next()){
                return result.getString("courseID");
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Hàm lấy ID của một môn học
    public String getCourseID(String classSectionID){
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT courseID FROM classsection WHERE classSectionID = ?");
            prepare.setString(1, classSectionID);
            result = prepare.executeQuery();
            if(result.next()){
                return result.getString("courseID");
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Hàm cập nhật số lượng sinh viên đã đăng ký vào một lớp học phần
    public void updateEnrolled(String classSectionID){
        int enrolled = new StudentClassSectionDAO().getEnrolledStudents(classSectionID);
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("UPDATE classsection SET enrolled = ? WHERE classSectionID = ?");
            prepare.setInt(1, enrolled);
            prepare.setString(2, classSectionID);
            prepare.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getStudentIdByClassSectionId(String classSectionId) {
        List<String> studentIds = new ArrayList<>();
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT studentID FROM classsection WHERE classSectionID = ?");
            prepare.setString(1, classSectionId);
            result = prepare.executeQuery();
            while (result.next()) {
                studentIds.add(result.getString("studentID"));
            }
            return studentIds;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ClassSection> getClassSectionsByStudentId(String studentId) {
        List<ClassSection> classSections = new ArrayList<>();
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT * FROM classsection WHERE studentID = ?");
            prepare.setString(1, studentId);
            result = prepare.executeQuery();
            while (result.next()) {
                ClassSection classSection = new ClassSection(result.getString("classSectionID"), result.getString("classSectionName"),
                        result.getString("courseID"), result.getString("lecturerID"), result.getInt("enrolled"));
                classSections.add(classSection);
            }
            return classSections;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getClassSectionNamebyLecturerID(String lecturerID) {
        List<String> classSectionNames = new ArrayList<>();
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT classSectionName FROM classsection WHERE lecturerID = ?");
            prepare.setString(1, lecturerID);
            result = prepare.executeQuery();
            while (result.next()) {
                classSectionNames.add(result.getString("classSectionName"));
            }
            return classSectionNames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLecturerName(String classSectionId) {
        try {
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT lecturerID FROM classsection WHERE classSectionID = ?");
            prepare.setString(1, classSectionId);
            result = prepare.executeQuery();
            if (result.next()) {
                String lecturerId = result.getString("lecturerID");
                return new LecturerDAO().getNameLecturer(lecturerId);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CourseInfo> getCoursesData(){
        String id = new AccountDAO().getUserTypeID(new AccountDAO().getCurrentAccount().getAccountID());

        List<CourseInfo> courses = new ArrayList<>();
        lecturerDashBoardController x = new lecturerDashBoardController();

        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT * FROM classsection WHERE lecturerID = ?");
            prepare.setString(1, id);
            result = prepare.executeQuery();
            int stt = 0;
            while(result.next()){
                String classSectionID = result.getString("classSectionID");
                int enrolled = new StudentClassSectionDAO().getEnrolledStudents(classSectionID);
                String classSectionName = result.getString("classSectionName");
                CourseInfo course = x.new CourseInfo(++stt, classSectionID, classSectionName, enrolled);
                courses.add(course);
            }
            return courses;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getNumberCourses(String lecturerID){
        try{
            connect = DataBase.connecDb();
            prepare = connect.prepareStatement("SELECT COUNT(*) FROM classsection WHERE lecturerID = ?");
            prepare.setString(1, lecturerID);
            result = prepare.executeQuery();
            result.next();
            return result.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
