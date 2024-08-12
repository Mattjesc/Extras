package com.nata.carrental;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.nata.carrental.models.BookingModel;
import com.nata.carrental.models.CarModel;
import com.nata.framework.Session;
import com.nata.framework.ViewsController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.control.TableCell;

public class CarController extends ViewsController{
    private CarModel Car = new CarModel();
    private BookingModel Booking = new BookingModel();

    @FXML private TableView<CarModel> tableView;
    @FXML private TableColumn<CarModel, String> nameColumn;
    @FXML private TableColumn<CarModel, String> priceColumn;
    @FXML private TableColumn<CarModel, String> availableColumn;
    @FXML private TableColumn<CarModel, String> actionsColumn;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private HBox formView;
    @FXML private Button createButton;
    @FXML private Button writeButton;
    @FXML private Button cancelEditButton;

    @FXML private void initialize() throws IOException{
        
        if(Session.getUser().role.equals("customer")) formView.getChildren().clear();

        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CarModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CarModel, String> p) {
                return new SimpleStringProperty(p.getValue().name);
            }
        });
        priceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CarModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CarModel, String> p) {
                return new SimpleStringProperty(p.getValue().price);
            }
        });
        availableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CarModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CarModel, String> p) {
                return new SimpleStringProperty(p.getValue().available?"Yes":"No");
            }
        });
        actionsColumn.setCellFactory(new Callback<TableColumn<CarModel, String>, TableCell<CarModel, String>>() {
            @Override
            public TableCell<CarModel, String>  call(final TableColumn<CarModel, String> param) {
                return new TableCell<CarModel, String>() {
                    final Button deleteButton = new Button("Delete");
                    final Button bookingButton = new Button("Booking");
                    final Button editButton = new Button("Edit");
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {

                            CarModel car = getTableView().getItems().get(getIndex());

                            deleteButton.setOnAction(event -> unlink(car));
                            bookingButton.setOnAction(event -> booking(car));
                            editButton.setOnAction(event -> edit(car));

                            final HBox actionButtons = Session.getUser().role.equals("admin")? 
                            new HBox(deleteButton,editButton):
                            car.available?
                            new HBox(bookingButton):new HBox();

                            setGraphic(actionButtons);
                            setText(null);
                        }
                    }
                };
            }
        });
        updateViews();
    } 

    @FXML private void create(){
        // prepre data to store
        ArrayList<HashMap<String,String>> values = new ArrayList<>();
        values.add(new HashMap<String,String>(){{
            put("name",nameField.getText());
            put("price",priceField.getText());
            put("available","true");
        }});
        // store the data
        ArrayList<HashMap<String,String>> result = Car.create(values);

        if(result.size() > 0){
            clearFields();
            alert("Success",alertInformation);
            updateViews();
        }else{
            alert("Failed",alertError);
        }
    }

    @FXML private void cancelEdit(){
        this.Car = new CarModel();
        clearFields();
        updateViews();
    }

    @FXML private void write(){
        this.Car.write(new HashMap<String,String>(){{
            put("name",nameField.getText());
            put("price",priceField.getText());
        }});
        this.Car = new CarModel();
        clearFields();
        alert("Success",alertInformation);
        updateViews();
    }

    private void updateViews(){
        final ObservableList<CarModel> items = FXCollections.observableArrayList();
        ArrayList<HashMap<String,String>> cars = Car.search(null, 0);
        for(HashMap<String,String> car : cars){
            items.add(new CarModel(Integer.valueOf(car.get("id"))));
        }
        formView.getChildren().removeAll(writeButton,createButton,cancelEditButton);
        if(Session.getUser().role.equals("admin")&&this.Car.id == null){
            formView.getChildren().addAll(createButton);
        }else if(Session.getUser().role.equals("admin")&&this.Car.id != null){
            formView.getChildren().addAll(writeButton,cancelEditButton);
        }
        tableView.setItems(items);
    }

    private void unlink(CarModel car){
        if(car.unlink()){
            updateViews();
        }else{
            System.out.println("fail");
        }
    }

    private void booking(CarModel car){
        ArrayList<HashMap<String,String>> values = new ArrayList<>();
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Booking "+car.name);
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        DatePicker startDateField = new DatePicker();
        DatePicker endDateField = new DatePicker();
        Label startDateLabel = new Label("Start Date");
        Label endDateLabel = new Label("End Date");
        HBox hbox = new HBox();
        Button bookingButton = new Button("Booking");
        bookingButton.setOnAction(event  -> {
            values.add(new HashMap<String,String>(){{
                put("car_id",car.id.toString());
                put("user_id",Session.getUID());
                put("state","draft");
                put("start_date",startDateField.getValue().toString());
                put("end_date",endDateField.getValue().toString());
                put("total",Booking.calculateTotal(startDateField.getValue().toString(), endDateField.getValue().toString(), car.price).toString());
            }});
            if(Booking.create(values).size()>0){
                try{
                    alert.close();
                    App.setRoot("booking");
                }catch(Exception e){
                    alert("Fail",alertError);
                    System.out.println(e.getMessage());
                }
            }
        });
        hbox.getChildren().addAll(startDateLabel,startDateField,endDateLabel,endDateField,bookingButton);
        hbox.setSpacing(20.0);
        hbox.setAlignment(Pos.CENTER);
        alert.setWidth(800);
        alert.setGraphic(hbox);
        alert.show();
    }

    private void edit(CarModel car){
        this.Car = car;
        nameField.setText(car.name);
        priceField.setText(car.price);
        updateViews();
    }

    private void clearFields(){
        nameField.clear();
        priceField.clear();
    }

}

