package com.example.sae;

import android.content.Context;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonReader {
    public static String loadJSONFromRaw(Context context, int resId) {
        String json;
        try {
            InputStream is = context.getResources().openRawResource(resId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
