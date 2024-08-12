package com.nata.carrental.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.nata.framework.Model;
import com.nata.framework.UserModel;


public class BookingModel extends Model {
    protected String _name = "booking";
    public BookingModel(){};
    public BookingModel(Integer id){
        HashMap<String,String> booking = this.browse(id.toString());
        if(booking != null){
            this.id = id;
            this.user_id = new UserModel(Integer.valueOf(booking.get("user_id")));
            this.car_id = new CarModel(Integer.valueOf(booking.get("car_id")));
            this.state = booking.get("state");
            this.start_date = booking.get("start_date");
            this.end_date = booking.get("end_date");
            this.total = booking.get("total");
        }
    };
    public UserModel user_id;
    public CarModel car_id;
    public String state;
    public String start_date;
    public String end_date;
    public String total;


    @Override
    public Boolean write(HashMap<String, String> value){
        Boolean result = super.write(value);
        if(value.containsKey("state") && value.getOrDefault("state", "").equals("accept")&&result){
            if(this.car_id != null) this.car_id.write(new HashMap<String,String>(){{
                put("available","false");
            }});
        }
        return result;
    }

    public Integer calculateTotal(String startDate,String endDate, String total){
        Integer price = 0;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_date = sdf.parse(startDate);
            Date end_date = sdf.parse(endDate);
            long diffInMillies = Math.abs(start_date.getTime() - end_date.getTime());
            Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            price = Integer.valueOf(total) * (diff.intValue() + 1);
        }catch(ParseException e){
            System.out.println(e.getMessage());
        }
        return price;
    }
}
