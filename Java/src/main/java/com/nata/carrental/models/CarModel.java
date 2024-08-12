package com.nata.carrental.models;

import java.util.HashMap;

import com.nata.framework.Model;

public class CarModel extends Model{
    protected String _name = "car";
    public CarModel(){};
    public CarModel(Integer id){
        HashMap<String,String> car = this.browse(id.toString());
        if(car != null){
            this.id = id;
            this.name = car.get("name");
            this.image = car.get("image");
            this.price = car.get("price");
            this.available = car.get("available").equals("true");
        }
    }
    public String name;
    public String image;
    public String price;
    public Boolean available;
}
