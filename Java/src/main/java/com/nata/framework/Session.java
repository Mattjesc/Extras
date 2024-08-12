package com.nata.framework;

import java.util.HashMap;

public class Session{
    private static String _path = "src/main/resources/data/session.txt";

    public static boolean auth(Integer id){
        return PlainText.createPlainText(id.toString(), _path);
    }
    public static boolean auth(HashMap<String,String> user){
        return PlainText.createPlainText(user.getOrDefault("id", ""), _path);
    }
    public static UserModel getUser(){
        return new UserModel(Integer.valueOf(getUID()));
    }
    public static String getUID(){
        return PlainText.getPlainText(_path);
    }
    public static boolean logout(){
        return PlainText.createPlainText("", _path);
    }

}
