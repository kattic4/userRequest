package com.example.userrequests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ServerConnect {
    public int responseCode = 200;
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_PATTERN);

    public  String Connect(String URLpass)
    {
        HttpURLConnection connect = null;
        String content = "";
        try
        {
            Log.v("connection", "Starting check login & pass URL: " + URLpass);
            connect = (HttpURLConnection) new URL(URLpass).openConnection();
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setConnectTimeout(10000);
            connect.setReadTimeout(10000);
            //connect.setRequestMethod("POST");
            //connect.setChunkedStreamingMode(0);
            //connect.setRequestProperty("Content-Length", String.valueOf(URLpass.length()));
            connect.connect(); // вот это поворот =)
            responseCode = connect.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream IS = connect.getInputStream();
                BufferedReader BR = new BufferedReader( new InputStreamReader(IS));
                content = BR.readLine();
                BR.close();

            }

        } catch (MalformedURLException ex) {
            content = "";
            responseCode = -1;
            Log.e("connection", ex.getMessage());
        } catch (IOException ex) {
            content = "";
            responseCode = -1;
            Log.d("connection",  " does not exists");
        } catch (OutOfMemoryError e) {
            content = "";
            responseCode = -1;
            Log.w("connection", "Out of memory!!!");
        }
        connect.disconnect();
        return content;
    }


    /**получение сех заявок
     * startUrl - имя хостинга (брать из настроек пользователя)
     * SessionKey - ключ доступа пользователя
     * return List <RequestClass> - запись по ID
     */
    public List<RequestClass> getAllRequestsClass(String startUrl, String SessionKey)
    {
        String url = startUrl + "/api/bids/getBids?SessionKey=" + SessionKey;

        String content = Connect(url);

        List<RequestClass> ms = new ArrayList<RequestClass>();
        try{
            JSONArray jArray = new JSONArray(content);
//            Log.d("my", String.valueOf(jArray.length()));
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                //тут так обрабатываем дату
                Date date = new Date();
                try {
                    date = parseDate(json_data.getString("DatePost"));
                } catch (ParseException e) {
                    e.printStackTrace();
                    //Здесь ошибка, в случае если макска для даты не подошла или не была правильной
                }
                RequestClass RequestClass = new RequestClass(json_data.getInt("Id"),
                        json_data.getString("BidText"),
                        date,//сюда помещаем дату таким вот образом
                        json_data.getInt("UserId"),
                        json_data.getString("Status"),
                        json_data.getInt("AdminId"),
                        json_data.getString("RoomName"));
                ms.add(RequestClass);
            }
        }
        catch (JSONException e) {
            Log.e("JSON", "JSON getAllRequestClass() error!");
        }

        return ms;
    }


    /**получение заявки по id
     * startUrl - имя хостинга (брать из настроек пользователя)
     * IdBid - id записи из БД Bid
     * return List <RequestClass> - запись по ID
     */
    public List<RequestClass> getBidTold(String startUrl, int IdBid)
    {
        String url = startUrl + "/api/bids/getBidToId?IdBid=" + IdBid;

        String content = Connect(url);

        List<RequestClass> ms = new ArrayList<RequestClass>();
        try{
            JSONArray jArray = new JSONArray(content);
//            Log.d("my", String.valueOf(jArray.length()));
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                Date date = new Date();
                try {
                    date = parseDate(json_data.getString("DatePost"));
                } catch (ParseException e) {
                    e.printStackTrace();
                    //Здесь ошибка, в случае если макска для даты не подошла или не была правильной
                }
                RequestClass RequestClass = new RequestClass(json_data.getInt("Id"),
                        json_data.getString("BidText"),
                        date,
                        json_data.getInt("UserId"),
                        json_data.getString("Status"),
                        json_data.getInt("AdminId"),
                        json_data.getString("RoomName"));
                ms.add(RequestClass);
            }
        }
        catch (JSONException e) {
            Log.e("JSON", "JSON getBidTold() error!");
        }

        return ms;
    }


    /**добавление заявки
     * startUrl - имя хостинга (брать из настроек пользователя)
     * SessionKey - ключ доступа пользователя
     * Text- текст заявки
     *
     * return int response  - id добавленной записи
     */
    public Integer addRequest(String startUrl, String SessionKey, String RoomName, String Text)
    {
        String setUrl  = startUrl + "/api/bids/addBid?SessionKey=" + SessionKey +
                "&RoomName=" + RoomName +
                "&Text=" + Text ;

        String content = Connect(setUrl);
        return Integer.parseInt(content);
    }


    /**удаление заявки
     * startUrl - имя хостинга (брать из настроек пользователя)
     * SessionKey - ключ доступа пользователя
     * IdRecord - id удаляемой записи
     *
     * return Boolean true  - если у нас удаление произошло успешно
     *                false - если с удалением возникли проблемы
     */
    public boolean removeRequest(String startUrl, String SessionKey, Integer IdRecord)
    {
        String ids = IdRecord.toString();
        try {
            ids = URLEncoder.encode(ids, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String reqUrl = startUrl + "/api/bids/removeBid?SessionKey="
                + SessionKey + "&idRecord" + ids;

        String content = Connect(reqUrl);
        return Boolean.parseBoolean(content);
    }


    public boolean getIsAdmin(String startUrl, String SessionKey)
    {
        String reqUrl = startUrl + "/api/users/getIsAdmin?SessionKey="
                + SessionKey;
        String content = Connect(reqUrl);
        return Boolean.parseBoolean(content);
    }


    public Integer editRequest(String startUrl, String SessionKey, Integer IdRecord, String RoomName, String Text)
    {
        String setUrl  = startUrl + "/api/bids/addBid?SessionKey=" + SessionKey +
                "&RoomName=" + RoomName +
                "&Text=" + Text ;

        String content = Connect(setUrl);
        return Integer.parseInt(content);
    }

    private static Date parseDate(final String stringDate) throws ParseException {
        return FORMAT.parse(stringDate);
    }
}
