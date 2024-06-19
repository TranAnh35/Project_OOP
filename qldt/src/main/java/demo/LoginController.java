package demo;

/*
 * Class này dùng để xử lý các sự kiện liên quan đến việc đăng nhập
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import demo.Account.LecturerAccount;
import demo.Account.StudentAccount;
import demo.DAO.AccountDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

    private double x = 0;
    private double y = 0;

    @FXML
    private ComboBox<String> SignIn_Type;

    @FXML
    private Button cLose;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signUpBtn;

    private String[] type = {"Student", "Lecturer"};

    public void selectPosition(){
        List<String> list = new ArrayList<>();

        for(String s : type){
            list.add(s);
        }

        ObservableList<String> obList = FXCollections.observableArrayList(list);

        SignIn_Type.setItems(obList);
    }
    
    public void login(){
        String user = username.getText();
        String pass = password.getText();
        
        try{
            boolean result = new AccountDAO().loginAccount(user, pass);

            Alert alert;

            if(user.isEmpty() || pass.isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields");
                alert.showAndWait();
            }else if(SignIn_Type.getValue() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a position");
                alert.showAndWait();
            }else{
                if(result){
                    AccountDAO accountDAO = new AccountDAO();
                    String userType = accountDAO.checkTypeAccount(user);
                    if(userType.equals("Student"))
                        accountDAO.addCurrentAccount( (StudentAccount) new AccountDAO().getAccount(user, pass));
                    else if(userType.equals("Lecturer"))
                        accountDAO.addCurrentAccount( (LecturerAccount) new AccountDAO().getAccount(user, pass));
                    if(checkType(userType)){
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Login Successful");
                        alert.showAndWait();
                        if(userType.equals("Student"))
                            switchToDashBoard();
                        else
                            switchToLecturerDashBoard();
                    }else{
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("No account found with corresponding position");
                        alert.showAndWait();
                    }
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){    e.printStackTrace();}    
    }

    public boolean checkType(String type){
        if(SignIn_Type.getValue().equals(type))
            return true;
        return false;
    }

    @FXML
    private void switchToSignUp() throws IOException {
        signUpBtn.getScene().getWindow().hide();
        Parent root = App.loadFXML("SignUp");

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

    @FXML
    private void switchToLecturerDashBoard() throws IOException {
        loginBtn.getScene().getWindow().hide();
        Parent root = App.loadFXML("LecturerDashBoard");
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

    @FXML
    private void switchToDashBoard() throws IOException {
        loginBtn.getScene().getWindow().hide();
        Parent root = App.loadFXML("Dashboard");
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
    
    @FXML
    private void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        selectPosition();
    }
}
