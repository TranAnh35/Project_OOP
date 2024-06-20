package demo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import demo.Account.CurrentAccount;
import demo.DAO.AccountDAO;
import demo.DAO.ClassSectionDAO;
import demo.DAO.InboxDAO;
import demo.DAO.LecturerDAO;
import demo.DAO.StudentClassSectionDAO;
import demo.DAO.StudentDAO;
import demo.Entity.Inbox;
import demo.Entity.Lecturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class lecturerDashBoardController implements Initializable{

    protected CurrentAccount currentAccount;
    protected Lecturer lecturer;

    public static Stage currentStage;

    @FXML
    private Button ChangePass_btn;

    @FXML
    private Button Course_btn;

    @FXML
    private TableColumn<CourseInfo, String> Course_col_ID;

    @FXML
    private TableColumn<CourseInfo, Integer> Course_col_STT;

    @FXML
    private TableColumn<CourseInfo, Integer> Course_col_enrolled;

    @FXML
    private TableColumn<CourseInfo, String> Course_col_name;

    @FXML
    private AnchorPane Course_form;

    @FXML
    private TextField Course_search;

    @FXML
    private TableView<CourseInfo> Course_table;

    @FXML
    private Button DashBoard_btn;

    @FXML
    private Button InboxForm_btn;

    @FXML
    private ComboBox<String> Inbox_Course;

    @FXML
    private AnchorPane Inbox_InboxForm;

    @FXML
    private HBox Inbox_Inbox_Table_Tiltle;

    @FXML
    private VBox Inbox_Inbox_table;

    @FXML
    private ComboBox<String> Inbox_Receiver;

    @FXML
    private AnchorPane Inbox_SentForm;

    @FXML
    private VBox Inbox_Sent_Table;

    @FXML
    private HBox Inbox_Sent_Table_Tiltle;

    @FXML
    private TextField Inbox_Tiltle;

    @FXML
    private AnchorPane Inbox_WriteForm;

    @FXML
    private TextArea Inbox_body;

    @FXML
    private Button Inbox_btn;

    @FXML
    private AnchorPane Inbox_form;

    @FXML
    private ComboBox<String> Inbox_position;

    @FXML
    private Button Inbox_send_btn;

    @FXML
    private Button Info_btn;

    @FXML
    private Button Schedule_btn;

    @FXML
    private AnchorPane Schedule_form;

    @FXML
    private Button Send_btn;

    @FXML
    private TextField Setting_ChangeInfo_City;

    @FXML
    private TextField Setting_ChangeInfo_District;

    @FXML
    private TextField Setting_ChangeInfo_Email;

    @FXML
    private TextField Setting_ChangeInfo_FName;

    @FXML
    private ComboBox<String> Setting_ChangeInfo_Gender;

    @FXML
    private TextField Setting_ChangeInfo_LName;

    @FXML
    private TextField Setting_ChangeInfo_Phone;

    @FXML
    private Button Setting_ChangeInfo_Save_btn;

    @FXML
    private TextField Setting_ChangeInfo_Street;

    @FXML
    private TextField Setting_ChangeInfo_Ward;

    @FXML
    private TextField Setting_ChangePass_ComfirmNewPass;

    @FXML
    private TextField Setting_ChangePass_NewPass;

    @FXML
    private TextField Setting_ChangePass_OldPass;

    @FXML
    private Button Setting_ChangePass_Save_btn;

    @FXML
    private Label Setting_InFo_Email;

    @FXML
    private Label Setting_InFo_FName;

    @FXML
    private AnchorPane Setting_InFo_Form;

    @FXML
    private Label Setting_InFo_Gender;

    @FXML
    private Label Setting_InFo_ID;

    @FXML
    private Label Setting_InFo_LName;

    @FXML
    private Label Setting_InFo_PN;

    @FXML
    private Button Setting_btn;

    @FXML
    private AnchorPane Setting_changeInFo_form;

    @FXML
    private AnchorPane Setting_changePass_form;

    @FXML
    private AnchorPane Setting_form;

    @FXML
    private Button Write_btn;

    @FXML
    private Button changeInfo_btn;

    @FXML
    private Button close;

    @FXML
    private Label dashBoard_Fines;

    @FXML
    private Label dashBoard_NOC;

    @FXML
    private Label dashBoard_Noti;

    @FXML
    private AnchorPane dashBoard_form;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Label userName;

    @FXML
    void Course_search(ActionEvent event) {

    }

    // Dashboard
    public void displayNumberCourse(){
        int numberCourse = new ClassSectionDAO().getNumberCourses(this.lecturer.getID());
        dashBoard_NOC.setText(String.valueOf(numberCourse));
    }

    public void displayNotification(){
        int numberNotification = new InboxDAO().getNumberNotification(currentAccount.getAccountID());
        dashBoard_Noti.setText(String.valueOf(numberNotification));
    }

    //COURSE
    public class CourseInfo{
        private int STT;
        private String ID;
        private String name;
        private int enrolled;

        public CourseInfo(int STT, String ID, String name, int enrolled){
            this.STT = STT;
            this.ID = ID;
            this.name = name;
            this.enrolled = enrolled;
        }

        public int getSTT(){
            return STT;
        }

        public String getID(){
            return ID;
        }

        public String getName(){
            return name;
        }

        public int getEnrolled(){
            return enrolled;
        }
    }

    public void setCourseData(){
        List<CourseInfo> listCourse = new ClassSectionDAO().getCoursesData();

        
        Course_col_STT.setCellValueFactory(new PropertyValueFactory<>("STT"));
        Course_col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Course_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Course_col_enrolled.setCellValueFactory(new PropertyValueFactory<>("enrolled"));
        
        ObservableList<CourseInfo> listData = FXCollections.observableArrayList(listCourse);
        Course_table.setItems(listData);
    }

    public void Course_search(){
        FilteredList<CourseInfo> filteredData = new FilteredList<>(Course_table.getItems(), p -> true);

        Course_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(course -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (course.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (course.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else{
                    return false;
                }
            });

            SortedList<CourseInfo> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(Course_table.comparatorProperty());
            Course_table.setItems(sortedData);
        });
    }

    public void clickCourse(){
        Course_table.setOnMouseClicked(event -> {
                CourseInfo course = Course_table.getSelectionModel().getSelectedItem();
                String classSectionID = course.getID();
                List<String> studentIDList = new StudentClassSectionDAO().getStudents(classSectionID);
                List<String> studentNameList = new ArrayList<>();
                for(String studentID : studentIDList){
                    studentNameList.add(new StudentDAO().getStudentName(studentID));
                }
                if (course != null) {
                    SharedData.getInstance().setClassSectionID(classSectionID);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseDetail.fxml"));
                    Parent root;
                    try {
                        root = loader.load();
                        CourseDetailController controller = loader.getController();
                        controller.initData(studentIDList, studentNameList); 

                        Stage classListStage = new Stage();
                        root.setOnMousePressed((MouseEvent mouseEvent) -> {
                            x = mouseEvent.getSceneX();
                            y = mouseEvent.getSceneY();
                        });
                        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                            classListStage.setX(mouseEvent.getScreenX() - x);
                            classListStage.setY(mouseEvent.getScreenY() - y);
    
                            classListStage.setOpacity(0.8);
                        });
    
                        root.setOnMouseReleased((MouseEvent mouseEvent) -> {
                            classListStage.setOpacity(1.0);
                        });
                        
                        classListStage.initStyle(StageStyle.TRANSPARENT);

                        currentStage = (Stage) Course_table.getScene().getWindow();
                        currentStage.hide();

                        classListStage.setScene(new Scene(root));
                        classListStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        });
    }

    //INBOX

    private String[] inboxPosition = {"All", "Student", "Lecturer"};
    private List<String> inboxReceiverID = new ArrayList<>();
    private List<String> studentNameList = new ArrayList<>();

    public void Inbox_position(){
        List<String> listPosition = new ArrayList<>();

        for(String s : inboxPosition){
            listPosition.add(s);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listPosition);

        Inbox_position.setItems(listData);
    }

    public void Inbox_Course(){
        List<String> listData = new ClassSectionDAO().getClassSectionNamebyLecturerID(this.lecturer.getID());

        ObservableList<String> list = FXCollections.observableArrayList(listData);

        Inbox_Course.setItems(list);
    }

    private List<String> getInboxReceiver(String classSectionName, String position){
        List<String> listReceiver = new ArrayList<>();

        String classSectionID = new ClassSectionDAO().getClassSectionID(classSectionName);
        List<String> studentList = new StudentClassSectionDAO().getStudents(classSectionID);
        
        inboxReceiverID.clear();
        studentNameList.clear();

        for(String studentID : studentList){
            studentNameList.add(new StudentDAO().getStudentName(studentID));
            inboxReceiverID.add(studentID);
        }
        String lecturer = new ClassSectionDAO().getLecturerName(classSectionID);

        if(position.equals("All")){
            listReceiver.add(lecturer);
            listReceiver.addAll(studentNameList);
        }else if(position.equals("Student")){
            listReceiver.addAll(studentNameList);
        }else if(position.equals("Lecturer")){
            listReceiver.add(lecturer);
        }

        return listReceiver;
    }

    public void Inbox_Receiver(String classSectionName, String position){
        List<String> listData = getInboxReceiver(classSectionName, position);

        ObservableList<String> list = FXCollections.observableArrayList(listData);

        Inbox_Receiver.setItems(list);
    }

    public void setInbox(){
        Inbox_Receiver.setVisible(false);
        Inbox_position();
        Inbox_Course();
        Inbox_Course.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String classSectionName = newValue;
            String position = Inbox_position.getSelectionModel().getSelectedItem();
            if(classSectionName != null && position != null){
                Inbox_Receiver.setVisible(true);
                Inbox_Receiver(classSectionName, position);
            }
        });
        
        Inbox_position.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String classSectionName = Inbox_Course.getSelectionModel().getSelectedItem();
            String position = newValue;
            if(classSectionName != null && position != null){
                Inbox_Receiver.setVisible(true);
                Inbox_Receiver(classSectionName, position);
            }
        });
    }

    private String getReceiverID(String receiverName){
        int index = studentNameList.indexOf(receiverName);
        if(index == -1){
            return null;
        }else{
            return inboxReceiverID.get(index);
        }
    }

    public void sendInbox(){
        int senderAccountID = currentAccount.getAccountID();
        String receiverID = getReceiverID(Inbox_Receiver.getSelectionModel().getSelectedItem());
        int receiverAccountID = new AccountDAO().getAccountId(receiverID);
        String tiltle = Inbox_Tiltle.getText();
        String body = Inbox_body.getText();
        String classSectionId = new ClassSectionDAO().getClassSectionID(Inbox_Course.getSelectionModel().getSelectedItem());

        if(receiverID.isEmpty() || tiltle.isEmpty() || body.isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
        }else{
            new InboxDAO().addInbox(senderAccountID, receiverAccountID, tiltle, body, classSectionId);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Message sent successfully");
            alert.showAndWait();
            clearWriteInbox();
        }
    }

    public void setInboxData(){
        List<Inbox> listInbox = new InboxDAO().getReceivedInboxs(currentAccount.getAccountID());

        for(Inbox inbox : listInbox){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inbox.fxml"));
            String senderName = new InboxDAO().getSenderName(inbox.getSenderID());
            String tiltle = inbox.getTiltle();
            try {
                HBox hbox = loader.load();
                InboxController controller = loader.getController();
                controller.setData(senderName, tiltle);
                Inbox_Inbox_table.getChildren().add(hbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSentData(){
        List<Inbox> listInbox = new InboxDAO().getSentInboxs(currentAccount.getAccountID());

        for(Inbox inbox : listInbox){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inbox.fxml"));
            String receiverName = new InboxDAO().getReceiverName(inbox.getReceiverID());
            String tiltle = inbox.getTiltle();
            try {
                HBox hbox = loader.load();
                InboxController controller = loader.getController();
                controller.setData(receiverName, tiltle);
                Inbox_Sent_Table.getChildren().add(hbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearWriteInbox(){
        Inbox_Tiltle.clear();
        Inbox_body.clear();
        Inbox_position.getSelectionModel().clearSelection();
        Inbox_Course.getSelectionModel().clearSelection();
        Inbox_Receiver.getSelectionModel().clearSelection();
    }

    // SETTING
    private String[] genderStatus = {"Male", "Female", "Other"};

    public void ChangeInfoGender_status(){
        List<String> listGender = new ArrayList<>();

        for(String s : genderStatus){
            listGender.add(s);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listGender);
        Setting_ChangeInfo_Gender.setItems(listData);
    }

    public void setSettingChangeInfoData(){
        Setting_ChangeInfo_FName.setText(this.lecturer.getFirstName());
        Setting_ChangeInfo_LName.setText(this.lecturer.getLastName());
        Setting_ChangeInfo_Email.setText(this.lecturer.getEmail());
        Setting_ChangeInfo_Phone.setText(this.lecturer.getPhoneNumber());
        Setting_ChangeInfo_Gender.setValue(this.lecturer.getGender());
    }

    public void saveSettingChangeInfoData(){

        Alert alert;
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to change the information?");
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get().equals(ButtonType.OK)){
            String lecturerId = this.lecturer.getID();
            String fName = Setting_ChangeInfo_FName.getText();
            String lName = Setting_ChangeInfo_LName.getText();
            String email = Setting_ChangeInfo_Email.getText();
            String phone = Setting_ChangeInfo_Phone.getText();
            String gender = Setting_ChangeInfo_Gender.getValue();

            this.lecturer.setFirstName(fName);
            this.lecturer.setLastName(lName);
            this.lecturer.setEmail(email);
            this.lecturer.setPhoneNumber(phone);
            this.lecturer.setGender(gender);

            new LecturerDAO().updateLecturer(this.lecturer, lecturerId);

            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Information changed successfully");
            alert.showAndWait();

            displayUserName();
        }
    }

    public void setSettingChangePassData(){
        Setting_ChangePass_OldPass.clear();
        Setting_ChangePass_NewPass.clear();
        Setting_ChangePass_ComfirmNewPass.clear();
    }

    public void changePassword(){
        setSettingChangePassData();
        currentAccount = new AccountDAO().getCurrentAccount();
        String oldPass = Setting_ChangePass_OldPass.getText();
        String newPass = Setting_ChangePass_NewPass.getText();
        String comfirmPass = Setting_ChangePass_ComfirmNewPass.getText();
        String currPass = currentAccount.getPassword();

        Alert alert;
        if(oldPass.isEmpty() || newPass.isEmpty() || comfirmPass.isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
        }else if(!(new AccountDAO().checkPassword(oldPass, currPass))){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Old password is incorrect");
            alert.showAndWait();
        }else if(!(newPass.equals(comfirmPass))){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("New password and confirm password do not match");
            alert.showAndWait();
        }else{
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to change the password?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){
                new AccountDAO().changePassword(currentAccount.getAccountID(), newPass);
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Password changed successfully");
                alert.showAndWait();
                setSettingChangePassData();
            }
        }
    }

    public void setInfoData(){
        Setting_InFo_Email.setText(this.lecturer.getEmail());
        Setting_InFo_FName.setText(this.lecturer.getFirstName());
        Setting_InFo_LName.setText(this.lecturer.getLastName());
        Setting_InFo_PN.setText(this.lecturer.getPhoneNumber());
        Setting_InFo_Gender.setText(this.lecturer.getGender());
        Setting_InFo_ID.setText(this.lecturer.getID());
    }

    public void switchForm(ActionEvent e){
        setStyleButton(DashBoard_btn);
        if(e.getSource() == DashBoard_btn){
            Course_form.setVisible(false);
            Schedule_form.setVisible(false);
            Setting_form.setVisible(false);
            Inbox_form.setVisible(false);
            dashBoard_form.setVisible(true);
            setStyleButton(DashBoard_btn);
        }else if(e.getSource() == Course_btn){
            Course_form.setVisible(true);
            Schedule_form.setVisible(false);
            Setting_form.setVisible(false);
            Inbox_form.setVisible(false);
            dashBoard_form.setVisible(false);
            setStyleButton(Course_btn);
            setCourseData();
        }else if(e.getSource() == Schedule_btn){
            Course_form.setVisible(false);
            Schedule_form.setVisible(true);
            Setting_form.setVisible(false);
            Inbox_form.setVisible(false);
            dashBoard_form.setVisible(false);
            setStyleButton(Schedule_btn);

        }else if(e.getSource() == Setting_btn){
            Course_form.setVisible(false);
            Schedule_form.setVisible(false);
            Setting_form.setVisible(true);
            Inbox_form.setVisible(false);
            dashBoard_form.setVisible(false);
            setStyleButton(Setting_btn);

            switchSettingForm(e);
            setInfoData();
        }else if(e.getSource() == InboxForm_btn){
            Course_form.setVisible(false);
            Schedule_form.setVisible(false);
            Setting_form.setVisible(false);
            Inbox_form.setVisible(true);
            dashBoard_form.setVisible(false);
            setStyleButton(InboxForm_btn);

            switchInboxForm(e);
            setInbox();
        }
    }

    public void switchSettingForm(ActionEvent e){
        if(e.getSource() == changeInfo_btn){
            Setting_changeInFo_form.setVisible(true);
            Setting_changePass_form.setVisible(false);
            Setting_InFo_Form.setVisible(false);
            setStyleSettingButton(changeInfo_btn);
        }else if(e.getSource() == ChangePass_btn){
            Setting_changeInFo_form.setVisible(false);
            Setting_changePass_form.setVisible(true);
            Setting_InFo_Form.setVisible(false);
            setStyleSettingButton(ChangePass_btn);
        }else if(e.getSource() == Info_btn){
            Setting_changeInFo_form.setVisible(false);
            Setting_changePass_form.setVisible(false);
            Setting_InFo_Form.setVisible(true);
            setStyleSettingButton(Info_btn);
            setInfoData();
        }
    }

    public void switchInboxForm(ActionEvent e){
        if(e.getSource() == Inbox_btn){
            Inbox_InboxForm.setVisible(true);
            Inbox_SentForm.setVisible(false);
            Inbox_WriteForm.setVisible(false);
            setStyleInboxButton(Inbox_btn);
            Inbox_Inbox_table.getChildren().clear();
            Inbox_Inbox_table.getChildren().add(Inbox_Inbox_Table_Tiltle);
            setInboxData();
            SharedData.getInstance().setSelectedButton("inbox");
        }else if(e.getSource() == Send_btn){
            Inbox_InboxForm.setVisible(false);
            Inbox_SentForm.setVisible(true);
            Inbox_WriteForm.setVisible(false);
            setStyleInboxButton(Send_btn);

            Inbox_Sent_Table.getChildren().clear();
            Inbox_Sent_Table.getChildren().add(Inbox_Sent_Table_Tiltle);
            setSentData();
            SharedData.getInstance().setSelectedButton("sent");
        }else if(e.getSource() == Write_btn){
            Inbox_InboxForm.setVisible(false);
            Inbox_SentForm.setVisible(false);
            Inbox_WriteForm.setVisible(true);
            setStyleInboxButton(Write_btn);
            clearWriteInbox();
        }
    }
    
    public void setStyleButton(Button buttonName){
        String choseStyle = "-fx-background-color: #3796a7; background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px; border-width: 0px;";
        String unChoseStyle = "-fx-background-color: transparent; background-color: transparent; -fx-font-size: 14px; font-size: 14px; -fx-font-family: Arial; font-family: Arial; -fx-cursor:hand; cursor:hand; -fx-text-fill: #000; -fx-border-color: #ddd; border-color: #ddd; -fx-border-width: 1px; border-width: 1px;";
        Button[] buttons = {DashBoard_btn, Course_btn, Schedule_btn, Setting_btn, InboxForm_btn};

        for(Button b : buttons){
            if(b == buttonName){
                b.setStyle(choseStyle);
            }else{
                b.setStyle(unChoseStyle);
            }
        }
        
    }

    public void setStyleSettingButton(Button buttonName){
        String choseStyle = "-fx-background-color: #3796a7; background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px; border-width: 0px;";
        String unChoseStyle = "-fx-background-color: transparent; background-color: transparent; -fx-font-size: 14px; font-size: 14px; -fx-font-family: Arial; font-family: Arial; -fx-cursor:hand; cursor:hand; -fx-text-fill: #000; -fx-border-color: #ddd; border-color: #ddd; -fx-border-width: 1px; border-width: 1px;";
        Button[] buttons = {changeInfo_btn, ChangePass_btn, Info_btn};

        for(Button b : buttons){
            if(b == buttonName){
                b.setStyle(choseStyle);
            }else{
                b.setStyle(unChoseStyle);
            }
        }
    }

    public void setStyleInboxButton(Button buttonName){
        String choseStyle = "-fx-background-color: #3796a7; background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px; border-width: 0px;";
        String unChoseStyle = "-fx-background-color: transparent; background-color: transparent; -fx-font-size: 14px; font-size: 14px; -fx-font-family: Arial; font-family: Arial; -fx-cursor:hand; cursor:hand; -fx-text-fill: #000; -fx-border-color: #ddd; border-color: #ddd; -fx-border-width: 1px; border-width: 1px;";
        Button[] buttons = {Inbox_btn, Send_btn, Write_btn};

        for(Button b : buttons){
            if(b == buttonName){
                b.setStyle(choseStyle);
            }else{
                b.setStyle(unChoseStyle);
            }
        }
    }

    private double x = 0;
    private double y = 0;

    @FXML
    private void switchToLogin() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
        Parent root = loader.load();

        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(0.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1.0);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void displayUserName(){
        this.currentAccount = new AccountDAO().getCurrentAccount();
        this.lecturer = (Lecturer) new AccountDAO().getInfoPerson(this.currentAccount.getAccountID());
        String name = lecturer.getFirstName() + " " + lecturer.getLastName();
        userName.setText(name);
    }

    @FXML
    private void close(){
        new AccountDAO().removeCurrentAccount(currentAccount.getAccountID());
        currentAccount = null;
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void logout(){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();
                new AccountDAO().removeCurrentAccount(currentAccount.getAccountID());
                currentAccount = null;
                switchToLogin();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        displayUserName();
        displayNumberCourse();
        displayNotification();

        setSettingChangeInfoData();
        setInfoData();

        currentAccount = new AccountDAO().getCurrentAccount();
    }
    

}
