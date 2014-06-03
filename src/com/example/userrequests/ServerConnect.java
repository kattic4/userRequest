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
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ServerConnect {
    public int responseCode = 200;
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_PATTERN);

    public String content = "";
    public  String Connect(final String URLpass)
    {
        class Synchronize extends AsyncTask<Void, Void, Void > {

            protected Void doInBackground(Void... urls) {
                HttpURLConnection connect = null;

                try
                {
                    connect = (HttpURLConnection) new URL(URLpass).openConnection();
                    connect.setDoInput(true);
                    connect.setDoOutput(true);
                    connect.setConnectTimeout(10000);
                    connect.setReadTimeout(10000);
                    //connect.setRequestMethod("POST");
                    //connect.setChunkedStreamingMode(0);
                    //connect.setRequestProperty("Content-Length", String.valueOf(URLpass.length()));
                    connect.connect();
                    responseCode = connect.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream IS = connect.getInputStream();
                        BufferedReader BR = new BufferedReader( new InputStreamReader(IS));
                        content = BR.readLine();
                        BR.close();

                    }

                } catch (MalformedURLException ex) {
                    content = "";
                    responseCode = -404;
                    Log.e("connection", ex.getMessage());
                } catch (IOException ex) {
                    content = "";
                    responseCode = -404;
                    Log.d("connection",  " does not exists");
                } catch (OutOfMemoryError e) {
                    content = "";
                    responseCode = -404;
                    Log.w("connection", "Out of memory!!!");
                }
                connect.disconnect();
                //if (sc.responseCode != HttpURLConnection.HTTP_OK)
                //    syncSuccessful = false;
                return null;
            }

            protected void onProgressUpdate(Integer... progress) {
            }

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }
        }

        Synchronize sync = new Synchronize();
        sync.execute();
        try {
            sync.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return content;
    }

    private static Date parseDate(final String stringDate) throws ParseException {
        return FORMAT.parse(stringDate);
    }

    public Response login(String startUrl, String login, String pass){
        Response response = new Response();

        String reqUrl = startUrl + "/api/Auth/login?"
                + "login=" + login
                + "&pass=" + pass;
        String content = Connect(reqUrl);
        //Log.d("myDebugTest", "reqUrl=" + reqUrl);
        //Log.d("myDebugTest", "content=" + content);
        JSONObject subcontent2;
        try {
            subcontent2 = new JSONObject(content);
            response.sessionKey = subcontent2.getString("authkey");
            response.response = Integer.parseInt(subcontent2.getString("response"));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return response;
    }

    public Response signup(String startUrl, String login, String pass, String FIOShort, Boolean isAdmin){
        Response response = new Response();

        String reqUrl = null;
        try {
            reqUrl = startUrl + "/api/Auth/signup?"
                    + "login=" + URLEncoder.encode(login, "utf-8")
                    + "&pass=" + URLEncoder.encode(pass, "utf-8")
                    + "&FIOShort=" + URLEncoder.encode(FIOShort, "utf-8")
                    + "&isAdmin=" + URLEncoder.encode(isAdmin.toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String content = Connect(reqUrl);
        //Log.d("myDebugTest", "reqUrl=" + reqUrl);
        //Log.d("myDebugTest", "content=" + content);
        JSONObject subcontent2;
        try {
            subcontent2 = new JSONObject(content);
            response.sessionKey = subcontent2.getString("authkey");
            response.response = Integer.parseInt(subcontent2.getString("response"));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return response;
    }

    /**добавление заявки
     * startUrl - имя хостинга (брать из настроек пользователя)
     * SessionKey - ключ доступа пользователя
     * Text- текст заявки
     * Tema
     * SourceSoftware
     * SoftwareName
     * NetworkOrInventoryNumber
     * Workstation
     * return int response  - id добавленной записи
     */
    public Integer addBid(String startUrl, String SessionKey, String roomName, String text, String tema,
                              String sourceSoftware, String softwareName, String networkOrInventoryNumber,
                              String workstation)
    {
        String setUrl  = null;
        try {
            setUrl = startUrl + "/api/bids/addBid?" +
                    "SessionKey=" + SessionKey +
                    "&RoomName=" + URLEncoder.encode(roomName, "utf-8") +
                    "&Text=" + URLEncoder.encode(text, "utf-8") +
                    "&Tema=" + URLEncoder.encode(tema, "utf-8") +
                    "&SourceSoftware=" + URLEncoder.encode(sourceSoftware, "utf-8") +
                    "&SoftwareName=" + URLEncoder.encode(softwareName, "utf-8") +
                    "&NetworkOrInventoryNumber=" + URLEncoder.encode(networkOrInventoryNumber, "utf-8") +
                    "&Workstation=" + URLEncoder.encode(workstation, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //Log.d("myDebugTest", "reqUrl=" + setUrl);
        String content = Connect(setUrl);
        //Log.d("myDebugTest", "content=" + content);
        if(responseCode == -404){
            return responseCode;
        }
        return Integer.parseInt(content);
    }

    /**получение заявки по id
     * startUrl - имя хостинга (брать из настроек пользователя)
     * IdBid - id записи из БД Bid
     * return List <Bid> - запись по ID
     */
    public Bid getBidTold(String startUrl, int IdBid)
    {
        String url = startUrl + "/api/bids/getBidToId?IdBid=" + IdBid;
        //Log.d("myDebugTest", "reqUrl=" + url);

        String content = Connect(url);
        //Log.d("myDebugTest", "content=" + content);

        Bid requestClass = null;
        try{
            {
                JSONObject json_data = new JSONObject(content);// jArray.getJSONObject(i);
                Date date = new Date();
                try {
                    date = parseDate(json_data.getString("DataPost"));
                } catch (ParseException e) {
                    e.printStackTrace();
                    //Здесь ошибка, в случае если макска для даты не подошла или не была правильной
                }

                requestClass = new Bid(
                        json_data.getInt("Id"),
                        json_data.isNull("BidText") ? "" : json_data.getString("BidText"),
                        date,
                        json_data.getInt("UserId"),
                        json_data.isNull("Status") ? "" : json_data.getString("Status"),
                        json_data.isNull("AdminId") ? -1 : json_data.getInt("AdminId"),
                        json_data.isNull("RoomName") ? "" : json_data.getString("RoomName"),
                        json_data.isNull("Tema") ? "" : json_data.getString("Tema"),
                        json_data.isNull("SourceSoftware") ? "" : json_data.getString("SourceSoftware"),
                        json_data.isNull("SoftwareName") ? "" : json_data.getString("SoftwareName"),
                        json_data.isNull("NetworkOrInventoryNumber") ? "" : json_data.getString("NetworkOrInventoryNumber"),
                        json_data.isNull("Workstation") ? "" : json_data.getString("Workstation"),
                        json_data.isNull("FIOShort") ? "" : json_data.getString("FIOShort")
                        );
            }
        }
        catch (JSONException e) {
            Log.e("myDebugTest", "JSON getBidTold() error!");
        }

        return requestClass;
    }


    /**получение сех заявок
     * startUrl - имя хостинга (брать из настроек пользователя)
     * SessionKey - ключ доступа пользователя
     * return List <Bid>
     */
    public List<Bid> getAllBid(String startUrl, String SessionKey)
    {
        String url = startUrl + "/api/bids/getBids?SessionKey=" + SessionKey;
        //Log.d("myDebugTest", "reqUrl=" + url);

        String content = Connect(url);
        //Log.d("myDebugTest", "content=" + content);

        List<Bid> resultBids = new ArrayList<Bid>();
        try{
            JSONArray jArray = new JSONArray(content);
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                //тут так обрабатываем дату
                Date date = new Date();
                try {
                    date = parseDate(json_data.getString("DataPost"));
                } catch (ParseException e) {
                    e.printStackTrace();
                    //Здесь ошибка, в случае если макска для даты не подошла или не была правильной
                }
                Bid RequestClass = new Bid(
                        json_data.getInt("Id"),
                        json_data.isNull("BidText") ? "" : json_data.getString("BidText"),
                        date,
                        json_data.getInt("UserId"),
                        json_data.isNull("Status") ? "" : json_data.getString("Status"),
                        json_data.isNull("AdminId") ? -1 : json_data.getInt("AdminId"),
                        json_data.isNull("RoomName") ? "" : json_data.getString("RoomName"),
                        json_data.isNull("Tema") ? "" : json_data.getString("Tema"),
                        json_data.isNull("SourceSoftware") ? "" : json_data.getString("SourceSoftware"),
                        json_data.isNull("SoftwareName") ? "" : json_data.getString("SoftwareName"),
                        json_data.isNull("NetworkOrInventoryNumber") ? "" : json_data.getString("NetworkOrInventoryNumber"),
                        json_data.isNull("Workstation") ? "" : json_data.getString("Workstation"),
                        json_data.isNull("FIOShort") ? "" : json_data.getString("FIOShort")
                );
                resultBids.add(0, RequestClass);
            }
        }
        catch (JSONException e) {
            Log.e("JSON", "JSON getAllRequestClass() error!");
        }

        return resultBids;
    }

    /**удаление заявки
     * startUrl - имя хостинга (брать из настроек пользователя)
     * SessionKey - ключ доступа пользователя
     * IdRecord - id удаляемой записи
     *
     * return Boolean true  - если у нас удаление произошло успешно
     *                false - если с удалением возникли проблемы
     */
    public boolean removeBid(String startUrl, String SessionKey, Integer IdRecord){
        String ids = IdRecord.toString();
        try {
            ids = URLEncoder.encode(ids, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String reqUrl = startUrl + "/api/bids/removeBid?SessionKey="
                + SessionKey + "&idRecord=" + ids;
        //Log.d("myDebugTest", "reqUrl=" + reqUrl);

        String content = Connect(reqUrl);
        //Log.d("myDebugTest", "content=" + content);

        return Boolean.parseBoolean(content);
    }

    public Integer editBid(String startUrl, String SessionKey, int idRecord, String roomName, String text, String tema,
                          String sourceSoftware, String softwareName, String networkOrInventoryNumber,
                          String workstation)
    {
        String setUrl  = null;
        try {
            setUrl = startUrl + "/api/bids/editBid?" +
                    "SessionKey=" + SessionKey +
                    "&IdRecord=" +  idRecord +
                    "&RoomName=" + URLEncoder.encode(roomName, "utf-8") +
                    "&Text=" + URLEncoder.encode(text, "utf-8") +
                    "&Tema=" + URLEncoder.encode(tema, "utf-8") +
                    "&SourceSoftware=" + URLEncoder.encode(sourceSoftware, "utf-8") +
                    "&SoftwareName=" + URLEncoder.encode(softwareName, "utf-8") +
                    "&NetworkOrInventoryNumber=" + URLEncoder.encode(networkOrInventoryNumber, "utf-8") +
                    "&Workstation=" + URLEncoder.encode(workstation, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //Log.d("myDebugTest", "reqUrl=" + setUrl);
        String content = Connect(setUrl);
        //Log.d("myDebugTest", "content=" + content);
        if(responseCode == -404){
            return responseCode;
        }
        return Integer.parseInt(content);
    }

    public Integer setStatus(String startUrl, String SessionKey, int idRecord, String status){
        String setUrl  = null;
        try {
            setUrl = startUrl + "/api/bids/setStatus?" +
                    "SessionKey=" + SessionKey +
                    "&IdRecord=" +  idRecord +
                    "&Status=" + URLEncoder.encode(status, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //Log.d("myDebugTest", "reqUrl=" + setUrl);
        String content = Connect(setUrl);
        //Log.d("myDebugTest", "content=" + content);
        if(responseCode == -404){
            return responseCode;
        }
        return Integer.parseInt(content);
    }

    public boolean getIsAdmin(String startUrl, String SessionKey)
    {
        String reqUrl = startUrl + "/api/users/getIsAdmin?SessionKey=" + SessionKey;
        String content = Connect(reqUrl);
        return Boolean.parseBoolean(content);
    }

    public List<Spravochnik> getSpravochniki(String startUrl){
        String setUrl = startUrl + "/api/Sprav/getSpravochniki";
        Log.d("myDebugTest", "reqUrl=" + setUrl);
        String content = Connect(setUrl);
        Log.d("myDebugTest", "content=" + content);

        List<Spravochnik> resultSprav = new ArrayList<Spravochnik>();
        try{
            JSONArray jArray = new JSONArray(content);
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);

                Spravochnik spravochnik = new Spravochnik(
                        json_data.getInt("Id"),
                        json_data.isNull("SpravochnicValue") ? "" : json_data.getString("SpravochnicValue"),
                        json_data.isNull("SpravochnicType") ? "" : json_data.getString("SpravochnicType")
                );
                resultSprav.add(spravochnik);
            }
        }
        catch (JSONException e) {
            Log.e("JSON", "JSON getSpravochniki() error!");
        }
        return resultSprav;
    }
}
