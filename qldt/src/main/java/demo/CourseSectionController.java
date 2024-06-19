package demo;

/*
 * Class này dùng để hiển thị thông tin của một lớp học phần
 */

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import demo.Course.ClassSection;
import demo.DAO.AccountDAO;
import demo.DAO.ClassSectionDAO;
import demo.DAO.CourseDAO;
import demo.DAO.LecturerDAO;
import demo.DAO.PaymentDAO;
import demo.DAO.StudentClassSectionDAO;
import demo.DAO.StudentCourseProgressDAO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class CourseSectionController implements Initializable{

    @FXML
    private Label CourseName;

    @FXML
    private Label Enrolled;

    @FXML
    private Label LecturerName;

    @FXML
    private Button register_btn;

    @FXML
    private Button Unregister_btn;

    // Hàm này dùng để hiển thị thông tin của một lớp học phần
    public void setData(ClassSection classSection){
        CourseName.setText(classSection.getClassSectionName());
        Enrolled.setText(String.valueOf(classSection.getEnrolledStudents()));
        LecturerName.setText(new LecturerDAO().getNameLecturer(classSection.getLecturerID()));
    }

    // Hàm này dùng để đăng ký một lớp học phần
    public void registerCousreSection(){

        String studentID = new AccountDAO().getCurrentAccount().getStudentID();
        String classSectionID = new ClassSectionDAO().getClassSectionID(CourseName.getText());
        String courseID = new ClassSectionDAO().getCourseID(classSectionID);
        
        Alert alert;

        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you will sign up for this class?");
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get().equals(ButtonType.OK)){
            if(!(new StudentClassSectionDAO().checkRegistrationConditions(classSectionID, studentID)) && !(new StudentClassSectionDAO().isEnrolled(classSectionID, studentID))){
                String registeredClassSectionID = new StudentClassSectionDAO().getStudentClassSectionID(classSectionID, studentID);
                String classSectionName = new ClassSectionDAO().getClassSectionName(registeredClassSectionID);

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Registered for the class section \"" + classSectionName + "\" already.");
                alert.showAndWait();
            }else if(new StudentClassSectionDAO().isEnrolled(classSectionID, studentID)){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The class section has been registered.");
                alert.showAndWait();
            }else{
                new StudentClassSectionDAO().addStudentClassSection(classSectionID, studentID);
                if(!(new StudentCourseProgressDAO().checkCourseProgress(studentID, courseID))){
                    new StudentCourseProgressDAO().addStudentCourseProgress(studentID, courseID);
                    double tuitionFee = new CourseDAO().getTuitionFee(courseID);
                    new PaymentDAO().addPayment(studentID, courseID, tuitionFee);
                }
                new ClassSectionDAO().updateEnrolled(classSectionID);
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("The class section has been registered successfully.");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
