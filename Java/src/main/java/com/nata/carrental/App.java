package com.nata.carrental;

import java.io.IOException;

import com.nata.framework.Session;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;  

public class App extends Application {  

   private static Scene scene;

   private static String[] publicRoot = {"signup","login"};

   @Override 
   public void start(Stage stage) throws IOException {  
      scene = new Scene(guard("car"), 600, 500);  
      stage.setScene(scene);
      stage.show(); 
   }

   static void setRoot(String fxml) throws IOException {
      // jika root public maka bisa root bisa di akses tanpa login (load fxml using method loadFxml)
      // jika tidak public user harus login (load fxml using method guard : harus dicek sesi user jika tidak ada sesi user maka akan diarahkan ke root login)
      scene.setRoot(isPublicRoot(fxml)?loadFXML(fxml):guard(fxml));
   }
   private static Boolean isPublicRoot(String root){
      for(String item:publicRoot){
         if(root.equals(item))return true;
      }
      return false;
   }
   private static Parent guard(String fxml) throws IOException{
      return Session.getUID().equals("")?loadFXML("login"):new VBox(loadFXML("header"),loadFXML(fxml));
   }
   private static Parent loadFXML(String fxml) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
      return fxmlLoader.load();
   }
   public static void main(String args[]) { 
      launch(); 
   } 
}