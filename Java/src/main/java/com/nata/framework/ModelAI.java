package com.nata.framework;

import java.util.ArrayList;
import java.util.HashMap;

// this class handle auto increament of each model
public class ModelAI {
    private static String path = "src/main/resources/data/model_ai.txt";
    //  model_ai's id is the name of model
    private static String[] fields = {"id","last_id"};
    public static Integer getId(String model_name){
        Integer id = 1;
        // get all data in model_ai.txt
        String prevPlainText = PlainText.getPlainText(path);
        // first time only : write header 
        if(prevPlainText == ""){
            int i = 0 ;
            String header = "";
            for(String field : fields){
                header = header.concat(field);
                i = i + 1;
                if(i < fields.length){
                    header = header.concat(";");
                }
            }
            header = header.concat("\n");
            PlainText.createPlainText(header, path);
        }
        ArrayList<HashMap<String, String>> dataMap = PlainText.stringToListMap(prevPlainText);
        for(HashMap<String, String> dm : dataMap){
            String dmId = dm.getOrDefault("id", "");
            // if(dmId.equals("")){
            //     continue;
            // }
            if(dmId.equals(model_name)){
                String sid = dm.get("last_id");
                id = Integer.valueOf(sid) + 1;
                dm.put("last_id", id.toString());
                PlainText.writePlainText(dm, path, dmId);
            }
        }

        if(id == 1){
            prevPlainText = PlainText.getPlainText(path);
            prevPlainText = prevPlainText.concat(model_name.concat(";").concat(id.toString()).concat("\n"));
            PlainText.createPlainText( prevPlainText, path);
        }
        return id;
    }
}
