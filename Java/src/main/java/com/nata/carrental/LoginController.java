package com.nata.carrental;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.nata.framework.Session;
import com.nata.framework.UserModel;
import com.nata.framework.ViewsController;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends ViewsController{

    private UserModel User = new UserModel();

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        ArrayList<String> clauses = new ArrayList<>(
            Arrays.asList("username","=",username,"&","password","=",password)
        );
        ArrayList<HashMap<String,String>> users = User.search(clauses, 0);
        if(users.size() == 0){
            alert("Username or password is incorrect!",alertWarning);
        }else{
            Session.auth(users.get(0));
            App.setRoot("car");
            System.out.println("Success logged in as " + users.get(0).getOrDefault("name","nameless"));
        }
    }

    @FXML private void signup() throws IOException {
        App.setRoot("signup");
    }
}
