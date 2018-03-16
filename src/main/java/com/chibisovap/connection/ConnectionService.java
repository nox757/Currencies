package com.chibisovap.connection;



import java.io.*;
import java.net.*;


public class ConnectionService {


    public final static String BASE_URL = "http://api.fixer.io/latest";

    public static String getLink(String from, String to) {

        if (from.equals("") || to.equals("")){
            return BASE_URL;
        } else {
            return BASE_URL + String.format("?base=%s&symbols=%s", from, to);
        }
    }


    public static String getRequest(String from, String to) {

        String response = "";
        BufferedReader reader = null;
        String link = getLink(from, to);

        try {

            URL url = new URL(link);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            connection.disconnect();
            response = stringBuilder.toString();

        } catch (MalformedURLException err_url) {

          System.err.println("Can't build url" + link);

        } catch (IOException e) {

            System.err.println("No connection with " + link);
            e.printStackTrace();

        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return response;
    }


}
