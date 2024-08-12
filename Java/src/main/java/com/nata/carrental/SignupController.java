package com.nata.carrental;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.nata.framework.UserModel;
import com.nata.framework.ViewsController;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController extends ViewsController {
    UserModel User = new UserModel();

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField repasswordField;

    @FXML private void login() throws IOException {
        App.setRoot("login");
    }

    @FXML private void signup() throws IOException {
        boolean valid = true;
        // validate data
        // #validate username
        // username required
        if(usernameField.getText().equals("")) {
            valid = false; alert("Input your username",alertWarning);
        }
        // username uniq
        if(User.search(new ArrayList<String>(){{add("username");add("=");add(usernameField.getText());}}, 0).size()>0){
            valid = false; alert("Username not available",alertWarning);
        }
        // #end validate username
        // #validate password
        // password required
        if(passwordField.getText().equals("")) {
            valid = false; alert("Input your password", alertWarning);
        }
        // reenter password validation
        if(!passwordField.getText().equals(repasswordField.getText())){
            valid = false; alert("Password do not match", alertWarning);            
        }
        // #end validate password
        if(valid){
            User.create(new ArrayList<HashMap<String,String>>(){{add(new HashMap<String,String>(){{
                put("name",nameField.getText());
                put("username",usernameField.getText());
                put("password",passwordField.getText());
                put("role","customer");
            }});}});
            alert("Registered",alertInformation);
            App.setRoot("login");
        }
    }
}
