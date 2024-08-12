package com.nata.framework;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

// PlainText class : this class interact with file
public class PlainText {
    // return String data from .txt file
    public static String getPlainText(String path){
        String res = "";
        try(FileInputStream fis = new FileInputStream(path)){
            BufferedInputStream bis = new BufferedInputStream(fis);
            int i = bis.read();
            while(i!=-1){
                res = res + (char) i;
                i = bis.read();
            }
        }catch(Exception e){
            // create new plain_text.txt if plain_text.txt not found
            if(e instanceof FileNotFoundException){
                try(FileOutputStream fos = new FileOutputStream(path)){
                }catch(Exception er){
                    System.out.println(er.getMessage());
                }
            }
        }
        return res;
    }

    // return ListMap from getPlainText
    public static ArrayList<HashMap<String,String>> getListMap(String path){
        String string_values = getPlainText(path);
        ArrayList<HashMap<String,String>> listmap_values = stringToListMap(string_values);
        return listmap_values;
    }

    // this method handle file output stream
    private static Boolean storePlainText(String data,String path){
        try(FileOutputStream fos = new FileOutputStream(path)){
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(data.getBytes());
            bos.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    // create data in plain text
    public static Boolean createPlainText(String data,String path){
        return storePlainText(data, path);
    }

    // convert string to list map
    public static ArrayList<HashMap<String,String>> stringToListMap(String str){
        ArrayList<HashMap<String,String>> result = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        String[] rows = str.split("\n");
        int i = 0;
        for(String row : rows){
            String[] columns = row.split(";");
            HashMap<String,String> record = new HashMap<>();
            int j = 0;
            for(String column : columns){
                if(i == 0){
                    keys.add(column);
                }else{
                    column.replaceAll("\\{\\;\\}", "\\;");
                    column.replaceAll("\\{n\\}","\n");
                    record.put(keys.get(j), column);
                }
                j = j + 1;
            }
            if(!record.isEmpty()){

                result.add(record);
            }
            i = i + 1;
        }
        return result;
    }

    public static String listMapToString(ArrayList<HashMap<String, String>> values,ArrayList<String> sorts){
        String result = "";
        int i = 0;
        for(HashMap<String, String> row : values){
            int j = 0;
            for(String key : sorts){
                result = result.concat(row.getOrDefault(key,"").replaceAll("\\;", "{;}").replaceAll("\n", "{n}"));
                j = j + 1;
                if(j < sorts.size()){
                    result = result.concat(";");
                }
            }
            result = result.concat("\n");
            i = i + 1;
        }
        return result;
    }
    public static String listMapToString(ArrayList<HashMap<String, String>> values){
        String result = "";
        int i = 0;
        for(HashMap<String, String> row : values){
            int j = 0;
            for(String v : row.values()){
                result = result.concat(v.replaceAll("\\;", "{;}").replaceAll("\n", "{n}"));
                j = j + 1;
                if(j != row.size()){
                    result = result.concat(";");
                }
            }
            result = result.concat("\n");
            i = i + 1;
        }
        return result;
    }

    public static Integer browsePlainText(ArrayList<HashMap<String, String>> values,String id){
        Integer i = 0;
        for(HashMap<String, String> value : values){
            String dmId = value.getOrDefault("id", "");
            if( dmId.equals(id)){
                return i;
            }
            i = i + 1;
        }
        return -1;
    }

    public static Boolean writePlainText(HashMap<String, String> value,String path,String id){
        // prev plain_text
        String plainText = getPlainText(path);
        String header = plainText.split("\n")[0];
        ArrayList<HashMap<String, String>> dataMap = stringToListMap(plainText);
        Integer index = browsePlainText(dataMap, id);
        if(index == -1) return false;
        dataMap.get(index).putAll(value);
        ArrayList<String> headers = new ArrayList<>();
        for(String h : header.split("\\;")){
            headers.add(h);
        }
        plainText = header.concat("\n".concat(listMapToString(dataMap,headers)));
        return storePlainText(plainText, path);
    }

    public static Boolean unlinkPlainText(String path,String id){
        String plainText = getPlainText(path);
        String header = plainText.split("\n")[0];
        ArrayList<HashMap<String, String>> dataMap = stringToListMap(plainText);
        int index = browsePlainText(dataMap, id);
        if(index == -1) return false;
        dataMap.remove(index);
        ArrayList<String> headers = new ArrayList<>();
        for(String h : header.split("\\;")){
            headers.add(h);
        }
        plainText = header.concat("\n".concat(listMapToString(dataMap,headers)));
        return storePlainText(plainText, path);
    }
}
