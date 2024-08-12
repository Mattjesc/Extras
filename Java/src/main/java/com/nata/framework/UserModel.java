package com.nata.framework;

import java.util.HashMap;

public class UserModel extends Model {
    protected String _name = "user";
    public UserModel(){};
    public UserModel(Integer id){
        HashMap<String,String> user = this.browse(id.toString());
        if(user != null){
            this.id = id;
            this.name = user.get("name");
            this.username = user.get("username");
            this.password = user.get("password");
            this.role = user.get("role");
        }
    };
    public String name;
    public String username;
    public String password;
    public String role;
}
