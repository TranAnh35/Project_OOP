package demo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import demo.Course.StudentCourseProgress;
import demo.DAO.ClassSectionDAO;
import demo.DAO.GradeDAO;
import demo.DAO.StudentClassSectionDAO;
import demo.DAO.StudentCourseProgressDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.FloatStringConverter;

public class CourseDetailController implements Initializable{
    @FXML
    private TableColumn<courseDetail, Float> CourseDetail_Diem_CC;

    @FXML
    private TableColumn<courseDetail, Float> CourseDetail_Diem_Ck;

    @FXML
    private TableColumn<courseDetail, Float> CourseDetail_Diem_Gk;

    @FXML
    private TableColumn<courseDetail, String> CourseDetail_MSSV;

    @FXML
    private TableColumn<courseDetail, String> CourseDetail_Name;

    @FXML
    private TableColumn<courseDetail, Integer> CourseDetail_STT;

    @FXML
    private Button CourseDetail_Save_btn;

    @FXML
    private TableView<courseDetail> CourseDetail_table;

    @FXML
    private AnchorPane CourseDetail_Form;

    @FXML
    private Button close;

    public class courseDetail {
        private int STT;
        private String MSSV;
        private String Name;
        private float Diem_CC;
        private float Diem_GK;
        private float Diem_CK;

        public courseDetail(int STT, String MSSV, String Name, float Diem_CC, float Diem_GK, float Diem_CK) {
            this.STT = STT;
            this.MSSV = MSSV;
            this.Name = Name;
            this.Diem_CC = Diem_CC;
            this.Diem_GK = Diem_GK;
            this.Diem_CK = Diem_CK;
        }

        public int getSTT() {
            return STT;
        }

        public void setSTT(int STT) {
            this.STT = STT;
        }

        public String getMSSV() {
            return MSSV;
        }

        public void setMSSV(String MSSV) {
            this.MSSV = MSSV;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public float getDiem_CC() {
            return Diem_CC;
        }

        public void setDiem_CC(float Diem_CC) {
            this.Diem_CC = Diem_CC;
        }

        public float getDiem_GK() {
            return Diem_GK;
        }

        public void setDiem_GK(float Diem_GK) {
            this.Diem_GK = Diem_GK;
        }

        public float getDiem_CK() {
            return Diem_CK;
        }

        public void setDiem_CK(float Diem_CK) {
            this.Diem_CK = Diem_CK;
        }
    }

    public void initData(List<String> studentIdList, List<String> StudentNameList){
        List<courseDetail> courseDetailList = new ArrayList<>();
        String classSectionId = SharedData.getInstance().getClassSectionID();

        for(int i = 0; i < studentIdList.size(); i++){
            float[] grade = new GradeDAO().getGrade(classSectionId, studentIdList.get(i));
            courseDetailList.add(new courseDetail(i + 1, studentIdList.get(i), StudentNameList.get(i), grade[0], grade[1], grade[2]));
        }

        CourseDetail_table.setEditable(true);

        CourseDetail_STT.setCellValueFactory(new PropertyValueFactory<>("STT"));
        CourseDetail_MSSV.setCellValueFactory(new PropertyValueFactory<>("MSSV"));
        CourseDetail_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        CourseDetail_Diem_CC.setCellValueFactory(new PropertyValueFactory<>("Diem_CC"));
        CourseDetail_Diem_Gk.setCellValueFactory(new PropertyValueFactory<>("Diem_GK"));
        CourseDetail_Diem_Ck.setCellValueFactory(new PropertyValueFactory<>("Diem_CK"));

        CourseDetail_Diem_CC.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        CourseDetail_Diem_Gk.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        CourseDetail_Diem_Ck.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        CourseDetail_Diem_CC.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<courseDetail, Float>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<courseDetail, Float> t) {
                    ((courseDetail) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDiem_CC(t.getNewValue());
                }
            }
        );

        CourseDetail_Diem_Gk.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<courseDetail, Float>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<courseDetail, Float> t) {
                    ((courseDetail) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDiem_GK(t.getNewValue());
                }
            }
        );

        CourseDetail_Diem_Ck.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<courseDetail, Float>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<courseDetail, Float> t) {
                    ((courseDetail) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDiem_CK(t.getNewValue());
                }
            }
        );

        ObservableList<courseDetail> listData = FXCollections.observableArrayList(courseDetailList);
        CourseDetail_table.setItems(listData);
    }

    public void saveGrade(){
        ObservableList<courseDetail> listData = CourseDetail_table.getItems();
        GradeDAO gradeDAO = new GradeDAO();
        for(courseDetail c: listData){
            String MSSV = c.getMSSV();
            float Diem_CC = c.getDiem_CC();
            float Diem_GK = c.getDiem_GK();
            float Diem_CK = c.getDiem_CK();
            String classSectionId = SharedData.getInstance().getClassSectionID();

            gradeDAO.add(classSectionId, MSSV, Diem_CC, Diem_GK, Diem_CK);
            float finalScore = Diem_CC * 0.1f + Diem_GK * 0.4f + Diem_CK * 0.5f;

            String courseID = new ClassSectionDAO().getCourseIDByClassSectionId(classSectionId);
            new StudentCourseProgressDAO().updateScore(MSSV, courseID, finalScore);
        }

        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lecturerDashBoardController.currentStage.show();
                CourseDetail_Form.getScene().getWindow().hide();
            }
        });
        CourseDetail_Save_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to save the grade?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get() == ButtonType.OK)
                    saveGrade();
            }
        });
    }

}
