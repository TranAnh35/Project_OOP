package demo;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import demo.Course.ClassSection;
import demo.DAO.AccountDAO;
import demo.DAO.ClassSectionDAO;
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

public class CourseSectionUnRegisterController implements Initializable{

    @FXML
    private Label CourseName;

    @FXML
    private Label Enrolled;

    @FXML
    private Label LecturerName;

    @FXML
    private Button Unregister_btn;

    public void setData(ClassSection classSection){
        CourseName.setText(classSection.getClassSectionName());
        Enrolled.setText(String.valueOf(classSection.getEnrolledStudents()));
        LecturerName.setText(new LecturerDAO().getNameLecturer(classSection.getLecturerID()));
    }

    public void unRegisterCousreSection(){
        ClassSectionDAO classSection = new ClassSectionDAO();
        StudentClassSectionDAO studentClassSection = new StudentClassSectionDAO();

        String studentID = new AccountDAO().getCurrentAccount().getStudentID();
        String classSectionID = classSection.getClassSectionID(CourseName.getText());
        String courseID = classSection.getCourseID(classSectionID);

        Alert alert;

        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to unregister this course section?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            studentClassSection.removeStudentClassSection(classSectionID, studentID);
            if(studentClassSection.getNumberOfClassSectionByCourseID(courseID) == 0){
                new StudentCourseProgressDAO().removeStudentCourseProgress(studentID, courseID);
                new PaymentDAO().deletePayment(studentID, courseID);
            }
            classSection.updateEnrolled(classSectionID);
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Unregister successfully!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
    
}
