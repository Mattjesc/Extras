package com.nata.framework;

import java.util.ArrayList;
import java.util.HashMap;

import java.lang.reflect.Field;
import java.lang.Class;

// * Reuseable model
public abstract class Model{
    // _name adalah variable nama model, nama ini akan diguanakan untuk 
    // nama file penyimpanan data model ber extensi txt
    protected String _name;
    // Setiap model bisa di instantiate tanpa id ataupun menggunakan id
    // variable id ini akan menyimpan id sebuah record yang ada di data file
    public Integer id = null;
    // _path adalah private variable yang menyimpan path ke folder data file
    private String _path = "src/main/resources/data/";
    // method getter untuk variable _name
    private String getName(){
        try{
            Class<?> o = this.getClass();
            Field n = o.getDeclaredField("_name");
            n.setAccessible(true);
            String v = (String) n.get(this);
            return v;
        }catch(Exception e){
            System.out.println(e);
            return "fail";
        }
    }
    // method getter untuk mendapatkan fields model yang mempunyai modifier public
    private ArrayList<String> getFields(){
        ArrayList<String> result = new ArrayList<>();
        result.add("id");
        try{
            Class<?> o = this.getClass();
            Field[] fields = o.getDeclaredFields();
            int i = 0;
            for(Field field : fields){
                i = i + 1;
                if(field.getModifiers()==1){
                    String column = field.getName();
                    result.add(column);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    // method getter untuk variable _path/model_name.txt
    private String getPath(){
        return _path.concat(getName().concat(".txt"));        
    }
    // * Idea : string expression clauses
    // expression ('name','=','hello','&','last_name','=','world')
    private boolean check_clause(ArrayList<String> clause){
        boolean result = false;
        switch(clause.get(1)){
            case "=":
            result = clause.get(0).equals(clause.get(2));
            break;
            case "!=":
            result = !clause.get(0).equals(clause.get(2));
            break;
        }
        if(clause.size() > 3){
            ArrayList<String> next_clause = new ArrayList<>(clause.subList(4, 7));
            switch(clause.get(3)){
                case "&":
                result = result && check_clause(next_clause);
                break;
                case "|":
                result = result || check_clause(next_clause);
                break;
            }
        }
        return result ;
    }
    
    // TODO : make the parameter String (not ArrayList<String>)
    // mencari data menggunakan field yang di inginkan, 
    // ArrayList String clause expression
    // expression format ('field_name','operator ( = , != ) ','value')
    // contoh expression ('name','=','hello','&','last_name','=','world')
    // untuk saait ini limit belum diperlukan
    public ArrayList<HashMap<String,String>> search(ArrayList<String> clauses,int limit){
        String data_string = PlainText.getPlainText(getPath());
        ArrayList<HashMap<String,String>> values = PlainText.stringToListMap(data_string);
        if(clauses==null){
            return values;
        }
        ArrayList<HashMap<String,String>> result = new ArrayList<>();
        int i = 0;
        for(HashMap<String,String> value : values){
            int i_item = 0;
            int i_operator = 0;
            ArrayList<String> final_clauses = new ArrayList<>(clauses);
            while(i_item + i_operator < clauses.size()){
                if(i_item%3==0){
                    final_clauses.set(i_item+i_operator, value.getOrDefault(clauses.get(i_item+i_operator),""));
                    i_operator = i_operator + 1;
                }
                i_item = i_item + 1;
            }
            boolean match = check_clause(final_clauses);
            if(match){
                result.add(value);
            };
            i = i + 1;
        }
        return result;
    }

    // mencari record menggunakan field id
    public HashMap<String,String> browse(String id){
        HashMap<String,String> result = new HashMap<>();
        String string_values = PlainText.getPlainText(getPath());
        ArrayList<HashMap<String,String>> listmap_values = PlainText.stringToListMap(string_values);
        int index = PlainText.browsePlainText(listmap_values, id);
        if(index == -1) return null;
        result = listmap_values.get(index);
        return result;
    }

    // method untuk menambahkan record baru
    public ArrayList<HashMap<String,String>> create(ArrayList<HashMap<String, String>> values){
        // add id for each values
        for(HashMap<String, String> value : values){
            Integer id = ModelAI.getId(getName());
            value.put("id", id.toString());
        }
        // convert list map to string, ordered by field
        String stringData = PlainText.listMapToString(values,getFields());
        String path = getPath();
        String prevPlainText = PlainText.getPlainText(path);

        // if the prevPlainText empty or (""), write header
        if(prevPlainText == ""){
            ArrayList<String> fields = getFields();
            int i = 0;
            for(String field : fields){
                prevPlainText = prevPlainText.concat(field);
                i = i + 1;
                if(i != fields.size()){
                    prevPlainText = prevPlainText.concat(";");
                }
            }
            prevPlainText = prevPlainText.concat("\n");
        }

        stringData = prevPlainText.concat(stringData);
        PlainText.createPlainText(stringData, path);
        return values;
    }

    // method untuk mengupdate record
    public Boolean write(HashMap<String, String> value, String id){
        return PlainText.writePlainText(value, getPath(), id);
    }
    // instantiate menggunakan id diperlukan
    // method untuk mengupdate record
    public Boolean write(HashMap<String, String> value){
        if(this.id == null) return false;
        HashMap<String,String> finalValue = browse(this.id.toString());
        finalValue.putAll(value);
        return PlainText.writePlainText(finalValue, getPath(),this.id.toString());
    }
    // method untuk menghapus record
    public Boolean unlink(ArrayList<String> ids){
        for(String id : ids){
            PlainText.unlinkPlainText(getPath(), id);
        }
        return true;
    }
    // instantiate menggunakan id diperlukan
    // method untuk menghapus record
    public Boolean unlink(){
        if(this.id == null) return false;
        return PlainText.unlinkPlainText(getPath(), this.id.toString());
    }
}
