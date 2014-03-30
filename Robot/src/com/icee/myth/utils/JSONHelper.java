/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icee.myth.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author chencheng
 */
public class JSONHelper {    

    public static <T extends Object> T parseFile(String filePath, Class<T> classOfT) throws JsonSyntaxException, IOException {
        Gson gson = new Gson();
        String str = FileUtils.readFileToString(new File(filePath), "UTF8");
        return gson.fromJson(str, classOfT);
    }

    public static <T extends Object> T parseFileNoException(String filePath, Class<T> classOfT) {
        try {
            Gson gson = new Gson();
            String str = FileUtils.readFileToString(new File(filePath), "UTF8");
            return gson.fromJson(str, classOfT);
        } catch (JsonSyntaxException ex) {
            Logger.getLogger(JSONHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSONHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static <T extends Object> T parseString(String str, Class<T> classOfT) throws JsonSyntaxException {
        Gson gson = new Gson();        
        return gson.fromJson(str, classOfT);
    }

    public static String toJSON(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
    
}
