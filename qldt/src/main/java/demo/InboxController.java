package demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InboxController implements Initializable{
    public static Stage currentStage;

    @FXML
    private Button Inbox_read;

    @FXML
    private Label sender_name;

    @FXML
    private Label tiltle_inbox;

    public void setData(String name, String tiltle){
        sender_name.setText(name);
        tiltle_inbox.setText(tiltle);
    }

    public String getTiltle(){
        return tiltle_inbox.getText();
    }

    private double x = 0;
    private double y = 0;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Inbox_read.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadInbox.fxml"));
                    AnchorPane root = loader.load();
                    ReadInboxController controller = loader.getController();
                    controller.setReadForm(tiltle_inbox.getText(), sender_name.getText());
                    
                    root.setOnMousePressed((MouseEvent mouseEvent) -> {
                        x = mouseEvent.getSceneX();
                        y = mouseEvent.getSceneY();
                    });
                    
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                        stage.setX(mouseEvent.getScreenX() - x);
                        stage.setY(mouseEvent.getScreenY() - y);

                        stage.setOpacity(0.8);
                    });

                    root.setOnMouseReleased((MouseEvent mouseEvent) -> {
                        stage.setOpacity(1.0);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);

                    currentStage = (Stage) Inbox_read.getScene().getWindow();
                    currentStage.hide();

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
