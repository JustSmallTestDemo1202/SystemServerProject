/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author rachel
 */
public class JSONHelper {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
        private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonPrimitive(format.format(src));
        }
    }).registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
        private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return json == null ? null : format.parse(json.getAsString());
            } catch (ParseException ex) {
                throw new JsonParseException(ex);
            }
        }
    }).create();

    public static <T extends Object> T parseFile(String filePath, Class<T> classOfT) throws JsonSyntaxException, IOException {
        String str = FileUtils.readFileToString(new File(filePath), "UTF8");
        return gson.fromJson(str, classOfT);
    }

    public static <T extends Object> T parseFileNoException(String filePath, Class<T> classOfT) {
        try {
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
        return gson.fromJson(str, classOfT);
    }

    public static String toJSON(Object obj) {
        return gson.toJson(obj);
    }
}
