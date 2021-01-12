package ehu.isad.utils;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Sarea {

    private static Sarea nireSarea = null;

    private Sarea(){}

    public static Sarea getNireSarea(){
        if(nireSarea==null){
            nireSarea = new Sarea();
        }
        return nireSarea;
    }

    public String urlarenTextuaLortu(String url) throws IOException {
        URL phpMyAdmin = new URL(url);
        URLConnection yc = phpMyAdmin.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        String inputText="";
        while ((inputLine = in.readLine()) != null) {
            inputText = inputText + inputLine +"\n";
        }
        in.close();
        return inputText;
    }
}
