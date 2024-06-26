package demo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import demo.DAO.StudentDAO;
import demo.DAO.ClassSectionDAO;
import demo.Entity.Student;
import demo.Course.ClassSection;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class StudentController implements Initializable{

    @FXML
    private ListView<String> studentListView;

    @FXML
    private ListView<String> lectureListView;

    private StudentDAO studentDAO;
    private ClassSectionDAO classSectionDAO;

    public StudentController() {
        studentDAO = new StudentDAO();
        classSectionDAO = new ClassSectionDAO();
    }

    // Hàm này dùng để hiển thị danh sách sinh viên trong một lớp học phần
    public void setStudentsByClassSection(String classSectionId) {
        List<Student> students = studentDAO.getStudentsByClassSectionId(classSectionId);
        studentListView.getItems().clear();
        for (Student student : students) {
            studentListView.getItems().add(student.getID() + " - " + student.getName());
        }
    }

    // Hàm này dùng để hiển thị danh sách lớp học phần của một sinh viên
    public void setLecturesByStudent(String studentId) {
        List<ClassSection> classSections = classSectionDAO.getClassSectionsByStudentId(studentId);
        lectureListView.getItems().clear();
        for (ClassSection classSection : classSections) {
            lectureListView.getItems().add(classSection.getClassSectionID() + " - " + classSection.getClassSectionName());
        }
    }

    @FXML
    private void handleStudentClick(MouseEvent event) {
        String selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            String studentId = selectedStudent.split(" - ")[0];
            setLecturesByStudent(studentId);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentListView.setOnMouseClicked(this::handleStudentClick);
    }
}
