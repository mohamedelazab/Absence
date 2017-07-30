package com.example.mohamed.absence;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by mohamed on 11/02/17.
 */

public class ExtractJsonData {

    String jsonStr,name,username,password;

    public ExtractJsonData(String jsonStr){
        this.jsonStr =jsonStr;
    }

    public String getData(){


        Map jsonJavaRootObject =new Gson().fromJson(jsonStr,Map.class);

         name = (String) jsonJavaRootObject.get("Name");
         username = (String) jsonJavaRootObject.get("Username");
         password = (String) jsonJavaRootObject.get("Password");

        return name+","+username+","+password+"\n";

    }
}
