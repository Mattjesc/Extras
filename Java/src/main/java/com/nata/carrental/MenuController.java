package com.nata.carrental;

import java.io.IOException;

import com.nata.framework.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuController {
    @FXML private Menu userMenu;
    @FXML public void initialize() throws IOException{
        userMenu.setText(Session.getUser().name);
    }
    @FXML private void changeRoot(ActionEvent ev) throws IOException {
        MenuItem item = (MenuItem)ev.getSource();
        App.setRoot((String)item.getUserData());
    }
    @FXML private void logout() throws IOException{
        Session.logout();
        App.setRoot("login");
    }
}
