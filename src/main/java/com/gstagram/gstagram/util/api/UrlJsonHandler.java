package com.gstagram.gstagram.util.api;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
// url을 통해 json을 받아올 때 사용하기 위한 함수들을 정의 해놓은 class
@Slf4j
public class UrlJsonHandler {
    public static String jsonReadAll(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();

        int cp;
        while ((cp = reader.read()) != -1) {
            sb.append((char) cp);
        }

        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = jsonReadAll(br);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
