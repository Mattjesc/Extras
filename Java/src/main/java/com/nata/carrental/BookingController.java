package com.nata.carrental;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import com.nata.carrental.models.BookingModel;
import com.nata.carrental.models.CarModel;
import com.nata.framework.Session;
import com.nata.framework.UserModel;
import com.nata.framework.ViewsController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class BookingController extends ViewsController{
    private BookingModel Booking = new BookingModel();
    private CarModel Car = new CarModel();
    private UserModel User = new UserModel();

    private ArrayList<HashMap<String,String>> users;
    private ArrayList<HashMap<String,String>> cars;

    @FXML private TableView<BookingModel> tableView;
    @FXML private TableColumn<BookingModel,String> userColumn;
    @FXML private TableColumn<BookingModel,String> carColumn;
    @FXML private TableColumn<BookingModel,String> stateColumn;
    @FXML private TableColumn<BookingModel,String> startDateColumn;
    @FXML private TableColumn<BookingModel,String> endDateColumn;
    @FXML private TableColumn<BookingModel,String> totalColumn;
    @FXML private TableColumn<BookingModel,String> actionsColumn;

    @FXML private ComboBox<String> userField;
    @FXML private ComboBox<String> carField;
    @FXML private DatePicker endDateField;
    @FXML private DatePicker startDateField;

    @FXML private HBox formView;
    @FXML private Button createButton;
    @FXML private Button writeButton;
    @FXML private Button cancelEditButton;

    @FXML private void initialize() throws IOException{
        tableView.setEditable(true);
        
        userColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookingModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BookingModel, String> p) {
                return new SimpleStringProperty(p.getValue().user_id.name);
            }
        });
        carColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookingModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BookingModel, String> p) {
                return new SimpleStringProperty(p.getValue().car_id.name);
            }
        });
        stateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookingModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BookingModel, String> p) {
                return new SimpleStringProperty(p.getValue().state);
            }
        });
        startDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookingModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BookingModel, String> p) {
                return new SimpleStringProperty(p.getValue().start_date);
            }
        });
        endDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookingModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BookingModel, String> p) {
                return new SimpleStringProperty(p.getValue().end_date);
            }
        });
        totalColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookingModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BookingModel, String> p) {
                return new SimpleStringProperty(p.getValue().total);
            }
        });
        
        actionsColumn.setCellFactory(new Callback<TableColumn<BookingModel, String>, TableCell<BookingModel, String>>() {
            @Override
            public TableCell<BookingModel, String>  call(final TableColumn<BookingModel, String> param) {
                return new TableCell<BookingModel, String>() {
                    final Button deleteButton = new Button("Delete");
                    final Button acceptButton = new Button("Accept");
                    final Button doneButton = new Button("Done");
                    final Button editButton = new Button("Edit");
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            BookingModel booking = getTableView().getItems().get(getIndex());
                            final HBox actionButtons = Session.getUser().role.equals("admin") &&
                                booking.state.equals("draft")? 
                                new HBox(acceptButton,editButton):
                                Session.getUser().role.equals("admin") &&
                                booking.state.equals("accept")? 
                                new HBox(doneButton):
                                Session.getUser().role.equals("admin")?
                                new HBox(deleteButton):
                                new HBox();
                            deleteButton.setOnAction(event -> unlink(booking));
                            acceptButton.setOnAction(event -> accept(booking));
                            doneButton.setOnAction(event -> done(booking));
                            editButton.setOnAction(event->edit(booking));
                            setGraphic(actionButtons);
                            setText(null);
                        }
                    }
                };
            }
        });
        updateViews();
    } 

    @FXML private void create() throws ParseException{
        ArrayList<HashMap<String,String>> values = new ArrayList<>();
        HashMap<String,String> car = cars.get(carField.getSelectionModel().getSelectedIndex());
        values.add(new HashMap<String,String>(){{
            put("user_id",users.get(userField.getSelectionModel().getSelectedIndex()).getOrDefault("id", ""));
            put("car_id",car.getOrDefault("id", ""));
            put("state","draft");
            put("start_date",startDateField.getValue().toString());
            put("end_date",endDateField.getValue().toString());
            put("total",Booking.calculateTotal(
                startDateField.getValue().toString(), 
                endDateField.getValue().toString(), 
                car.get("price")
            ).toString());
        }});
        ArrayList<HashMap<String,String>> result = Booking.create(values);
        if(result.size() > 0){
            System.out.println("success");
            updateViews();
        }else{
            System.out.println("fail"); 
        }
    }

    @FXML private void write(){
        HashMap<String,String> car = cars.get(carField.getSelectionModel().getSelectedIndex());
        Booking.write(new HashMap<String,String>(){{
            put("user_id",users.get(userField.getSelectionModel().getSelectedIndex()).getOrDefault("id", ""));
            put("car_id",car.getOrDefault("id", ""));
            put("state","draft");
            put("start_date",startDateField.getValue().toString());
            put("end_date",endDateField.getValue().toString());
            put("total",Booking.calculateTotal(
                startDateField.getValue().toString(), 
                endDateField.getValue().toString(), 
                car.get("price")
            ).toString());
        }});
        this.Booking = new BookingModel();
        clearFields();
        alert("Success",alertInformation);
        updateViews();
    }
    
    @FXML private void cancelEdit(){
        this.Booking = new BookingModel();
        clearFields();
        updateViews();
    }

    private void clearFields(){
        userField.getItems().clear();
        carField.getItems().clear();
        endDateField.getEditor().clear();
        startDateField.getEditor().clear();
    }
    
    private void updateViews() {
        final ObservableList<BookingModel> items = FXCollections.observableArrayList();
        ArrayList<String> clause = new ArrayList<String>(){{
            add("user_id");add("=");add(Session.getUID());
        }};
        ArrayList<HashMap<String,String>> bookings = Booking.search(
            Session.getUser().role.equals("admin")?
            null:clause
            , 0);
        for(HashMap<String,String> booking : bookings){
            items.add(new BookingModel(Integer.valueOf(booking.get("id"))));
        }
        tableView.setItems(items);
        // items in userField (ComboBox) depend on role user
        ArrayList<String> clauseUserField = Session.getUser().role.equals("admin")? 
            new ArrayList<String>(){{add("role");add("=");add("customer");}}:
            new ArrayList<String>(){{add("id");add("=");add(Session.getUID());}};
        users = User.search(clauseUserField, 0);
        cars =  Car.search(new ArrayList<String>(){{add("available");add("=");add("true");}}, 0);
        userField.getItems().clear();
        carField.getItems().clear();
        // add items in ComboBox
        for(HashMap<String,String> user:users){
            userField.getItems().add(user.getOrDefault("name", ""));
        }
        for(HashMap<String,String> car:cars){
            carField.getItems().add(car.getOrDefault("name", ""));
        }

        formView.getChildren().removeAll(createButton,cancelEditButton,writeButton);
        if(this.Booking.id == null){
            formView.getChildren().addAll(createButton);
        }else if(Session.getUser().role.equals("admin")&&this.Booking.id != null){
            formView.getChildren().addAll(writeButton,cancelEditButton);
        }
    }
    
    private void accept(BookingModel booking){
        // change state to accept
        if(booking.write(new HashMap<String,String>(){{put("state","accept");}})){
            updateViews();
        }else{
            System.out.println("fail");
        }
    }

    private void done(BookingModel booking){
        // change state booking to done
        if(booking.write(new HashMap<String,String>(){{put("state","done");}})){
            // change available car to true
            booking.car_id.write(new HashMap<String,String>(){{put("available","true");}});
            updateViews();
        }else{
            System.out.println("fail");
        }
    }

    private void unlink(BookingModel booking) {
        if(booking.unlink()){
            updateViews();
        }else{
            System.out.println("fail");
        }
    }

    private void edit(BookingModel booking){
        this.Booking = booking;
        updateViews();
        int i = 0;
        int user_id = 0;
        int car_id = 0;
        for(HashMap<String,String> user : users){
            if(user.get("id").equals(booking.user_id.id.toString())){
                user_id = i;
                break;
            }
            i=i+1;
        }
        i =0 ;
        for(HashMap<String,String> car : cars){
            if(car.get("id").equals(booking.car_id.id.toString())){
                car_id = i;
                break;
            }
            i=i+1;
        }
        userField.getSelectionModel().select(user_id);
        carField.getSelectionModel().select(car_id);
        startDateField.getEditor().setText(booking.start_date);
        endDateField.getEditor().setText(booking.end_date);
    }
}
