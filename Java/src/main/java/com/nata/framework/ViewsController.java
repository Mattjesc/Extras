package com.nata.framework;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class ViewsController {
    protected AlertType alertError = AlertType.ERROR;
    protected AlertType alertInformation = AlertType.INFORMATION;
    protected AlertType alertWarning = AlertType.WARNING;
    
    protected void alert(String message,AlertType type){
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }
}
