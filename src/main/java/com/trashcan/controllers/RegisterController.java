package com.trashcan.controllers;

import com.trashcan.account.User;
import com.trashcan.database.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField idTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private PasswordField registerPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button enterButton;
    @FXML
    private Button backButton;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private CheckBox showPassCheckBox;

    private User user;
    private final DB db = new DB();

    public void back() {
        ((StackPane) mainBorderPane.getParent()).getChildren().remove(mainBorderPane);
    }

    public void checkPassword() throws SQLException {
        if(registerPasswordField.getText().equals(confirmPasswordField.getText())) {
            if(db.registerCheck(idTextField.getText(), mailTextField.getText())) {
                db.setRegisteredUser(idTextField.getText(), mailTextField.getText(), registerPasswordField.getText());
            }
            else {
                messageLabel.setText("Something is Wong");
            }
        }
        else {
            messageLabel.setText("Password no match");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
