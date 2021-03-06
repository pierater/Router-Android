package edu.csumb.cst438.router;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by pico on 10/15/16.
 */

public class Connector {

    URL url;
    HttpURLConnection conn;
    static String AWS = "http://router-dev.us-west-2.elasticbeanstalk.com";
    static String checkLogin = "/checkLogin/";
    static String downloadRoute = "/downloadRoute/";
    static String uploadRoute = "/uploadRoute/";
    static String createUser = "/createUser/";
    static String getNearMe = "/getNearMe/";
    static String getFriendRequests = "/getFriendRequests/";
    static String getFriends = "/getFriends/";
    static String getRoutesShared = "/getRoutesShared/";
    static String shareRoute = "/shareRoute/";
    static String processRequest = "/processRequest/";
    static String searchFriends = "/searchFriends/";
    static String removeFriend = "/removeFriend/";
    static String addFriend = "/addFriend/";
    static String updateUser = "/updateUser/";

    static ExecutorService executorService = Executors.newSingleThreadExecutor();



    public Connector() {

        try {
            conn = buildConnection("");
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "constructor completed");
    }

    public ArrayList<User> getFriendRequests() {
        Callable<ArrayList<User>> callable = new Callable<ArrayList<User>>() {
            @Override
            public ArrayList<User> call() throws Exception {
                return getFriendRequests_internal();
            }
        };
        Future<ArrayList<User>> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getFriendRequests completed");
        return null;
    }

    public ArrayList<User> getFriends() {
        Callable<ArrayList<User>> callable = new Callable<ArrayList<User>>() {
            @Override
            public ArrayList<User> call() throws Exception {
                return getFriends_internal();
            }
        };
        Future<ArrayList<User>> future = executorService.submit(callable);

        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getFriends completed");
        return null;
    }

    public ArrayList<Route> getRoutesShared() {
        Callable<ArrayList<Route>> callable = new Callable<ArrayList<Route>>() {
            @Override
            public ArrayList<Route> call() throws Exception {
                return getRoutesShared_internal();
            }
        };
        Future<ArrayList<Route>> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getRoutesShared completed");
        return null;
    }

    public void shareRoute(final int you_id, final Route route) {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                shareRoute_internal(you_id, route);
                return null;
            }
        };
        executorService.submit(callable);
        Log.d("Connector", "shareRoute completed");
    }

    public void processRequest(final String response, final int you_id) {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                processRequest_internal(response, you_id);
                return null;
            }
        };
        executorService.submit(callable);
        Log.d("Connector", "processRequest completed");
    }

    public ArrayList<User> searchFriends(final String name) {
        Callable<ArrayList<User>> callable = new Callable<ArrayList<User>>() {
            @Override
            public ArrayList<User> call() throws Exception {
                return searchFriends_internal(name);
            }
        };
        Future<ArrayList<User>> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "searchFriends completed");
        return null;
    }

    public void removeFriend(final int you_id) {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                removeFriend_internal(you_id);
                return null;
            }
        };
        Future<Void> future = executorService.submit(callable);
        Log.d("Connector", "removeFriend completed");
    }

    public void addFriend(final int you_id) {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addFriend_internal(you_id);
                return null;
            }
        };
        Future<Void> future = executorService.submit(callable);
        Log.d("Connector", "addFriend completed");
    }

    public HashMap<String, String> downloadRoute(final int routeId) {

        Callable<HashMap<String, String>> callable = new Callable<HashMap<String, String>>() {
            @Override
            public HashMap<String, String> call() throws Exception {
                return downloadRoute_internal(routeId);
            }
        };
        Future<HashMap<String, String>> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "downloadRoute completed");
        return null;
    }

    public int createUser(final String username, final String password, final String bio, final String email) {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return createUser_internal(username, password, bio, email, "PRIVATE");
            }
        };
        Future<Integer> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "createUser completed");
        return -1;
    }

    public int updateUser(final String username,final String bio, final String email, final String privacy) {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return updateUser_internal(username, bio, email, privacy);
            }
        };
        Future<Integer> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "updateUser completed");
        return -1;
    }

    public int uploadRoute(final int userId, final String lat, final String lon, final String name, final String path) {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return uploadRoute_internal(userId, lat, lon, name, path);
            }
        };
        Future<Integer> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "uploadRoute completed");
        return -1;
    }

    public ArrayList<Route> getNearMe(final String lat, final String lon, final double distance) {

        Callable<ArrayList<Route>> callable = new Callable<ArrayList<Route>>() {
            @Override
            public ArrayList<Route> call() throws Exception {
                return getNearMe_internal(lat, lon, distance);
            }
        };

        Future<ArrayList<Route>> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getNearMe completed");
        return null;
    }

    public User checkLogin(final String username, final String password) {

        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                return checkLogin_internal(username, password);
            }
        };
        Future<User> future = executorService.submit(callable);
        try {
            return future.get();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "checkLogin completed");
        return null;

    }

    private HttpURLConnection buildConnection(String endpoint) {
        try {
            url = new URL(AWS + endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "buildConnection completed");
        return conn;
    }

    private String getResponse(String json, String endpoint) {
        try {
            conn = buildConnection(endpoint);
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed to connect: Code " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = "";
            String temp = "";
            while ((temp = br.readLine()) != null)
            {
                response += temp;
            }
                // will keep appending the json response to response var

            conn.disconnect();
            if(response.contains("\"status\": \"failure\"")){
                return null;
            }
            return response;
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getResponse completed");
        return "";
    }

    public static String sha1(String item) {
        byte[] b = item.getBytes();
        String result = "";
        for(int i = 0; i < item.length(); i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        Log.d("Connector", "sha1 completed");
        return result;
    }


    private int uploadRoute_internal(int userId, String lat, String lon, String name, String path) {
        HashMap<String, String> result = new HashMap<String, String>();
        String json = "";

        try {
            json = (new JSONObject()
                    .put("userId", Integer.toString(userId))
                    .put("route", (new JSONObject()
                                .put("name", name)
                                .put("path", path)
                                .put("startingPoint", new JSONObject()
                                                    .put("lat", lat)
                                                    .put("lon", lon))))).toString();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }

        try {
            JSONObject response = new JSONObject(getResponse(json.toString(), uploadRoute));
            Iterator<?> keys = response.keys();

            while(keys.hasNext()) {
                String key = keys.next().toString();
                result.put(key, response.get(key).toString());
            }
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        try {
            return Integer.parseInt(result.get("idroutes"));
        }
        catch (Exception e) {
            Log.d("Connector", "uploadRoute_internal completed");
            return -1;
        }

    }


    private ArrayList<Route> getNearMe_internal(String lat, String lon, double distance) {
        ArrayList<Route> result = new ArrayList<Route>();
        String json = "";

        try {
            json = (new JSONObject()
                    .put("userLat", lat.toString())
                    .put("userLon", lon.toString())
                    .put("dist", Double.toString(distance))).toString();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }

        try {
            JSONArray response = new JSONArray(getResponse(json.toString(), getNearMe));
            Log.d("response", response.toString());

            if(response.length() != 0) {
                for (int i = 0; i < response.length(); i++) {
                    Route route = new Route();
                    JSONObject item = (JSONObject) response.get(i);
                    route.setStartPointLon(item.getString("startPointLon"));
                    route.setStartPointLat(item.getString("startPointLat"));
                    route.setRouteName(item.getString("routeName"));
                    route.setRouteIdRemote(item.getInt("idroutes"));
                    route.setRoute(item.getString("route"));
                    route.setDistance(Float.parseFloat(item.getString("distance")));
                    result.add(route);

                }
            }
            else {
                return result;
            }
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getNearMe_internal completed");
        return result;
    }

    private HashMap<String, String> downloadRoute_internal(int routeId) {
        HashMap<String, String> result = new HashMap<>();
        String json = "";
        try {
            json = (new JSONObject()
                    .put("routeId", Integer.toString(routeId))).toString();
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }

        try {
            JSONObject response = new JSONObject(getResponse(json.toString(), downloadRoute));
            Iterator<?> keys = response.keys();

            while(keys.hasNext()) {
                String key = keys.next().toString();
                result.put(key, response.get(key).toString());
            }
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "downloadRoute_internal completed");
        return result;
    }

    private int createUser_internal(String username, String password, String bio, String email, String privacy) {
        password = sha1(password);
        String json = "";
        try {
            json = (new JSONObject()
                    .put("username", username)
                    .put("password", password)
                    .put("bio", bio)
                    .put("privacy", privacy)
                    .put("email", email)).toString();
        }
        catch (Exception e) {
            Log.d("error", e.toString());
        }

        try {
            Log.d("TEST", json.toString());
            JSONObject response = new JSONObject(getResponse(json.toString(), createUser));
            Log.d("TEST", response.toString());
            return Integer.parseInt((response.get("userId").toString()));
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "_internal completed");
        return -1;
    }

    private int updateUser_internal(String username, String bio, String email, String privacy) {
        String json = "";
        try {
            json = (new JSONObject()
                    .put("username", username)
                    .put("bio", bio)
                    .put("privacy", privacy)
                    .put("user_id", UserServices.getUserId())
                    .put("email", email)).toString();
        }
        catch (Exception e) {
            Log.d("error", e.toString());
        }

        try {
            Log.d("TEST", json.toString());
            JSONObject response = new JSONObject(getResponse(json.toString(), updateUser));
            Log.d("TEST", response.toString());
            return 1;
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "updateUser_internal completed");
        return -1;
    }

    private User checkLogin_internal(String username, String password) {
        password = sha1(password);
        String json = "";
        try {
            json = (new JSONObject()
                    .put("username", username)
                    .put("password", password)).toString();
        }
        catch (Exception e) {
            Log.d("error", e.toString());
        }

        try {
             String responseValue = getResponse(json.toString(), checkLogin);
             if(responseValue == null){
                 return null;
             }
             JSONObject response;
             JSONArray jsonArray = new JSONArray(responseValue);
             response = jsonArray.getJSONObject(0);
             Log.d("CheckLogin: ", response.toString());
             return new User(response.get("username").toString(), response.get("bio").toString(),
                        response.get("email").toString(), response.get("idusers").toString(), response.get("privacy").toString());
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "checkLogin_internal completed");
        return null;
    }

    private ArrayList<User> getFriendRequests_internal() {
        String json = "";
        ArrayList<User> result = new ArrayList<>();
        try {
            json = (new JSONObject()
            .put("user_id", UserServices.getUserId())).toString();
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            JSONArray response = new JSONArray(getResponse(json.toString(), getFriendRequests));
            Log.d("response", response.toString());
            if(response.length() != 0) {


                for (int i = 0; i < response.length(); i++) {
                    JSONObject item = (JSONObject) response.get(i);
                    User temp = new User();
                    temp.username = item.get("username").toString();
                    temp.bio = item.get("bio").toString();
                    temp.id = item.get("idusers").toString();

                    result.add(temp);
                }
            }
            else {
                return result;
            }
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getFriendRequests_internal completed");
        return result;
    }

    private ArrayList<User> getFriends_internal() {
        String json = "";
        ArrayList<User> result = new ArrayList<>();
        try {
            json = (new JSONObject()
                    .put("userId", UserServices.getUserId())).toString();
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            JSONArray response = new JSONArray(getResponse(json.toString(), getFriends));
            if(response.length() != 0) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject item = (JSONObject) response.get(i);
                    User temp = new User();
                    temp.username = item.get("username").toString();
                    temp.bio = item.get("bio").toString();
                    temp.id = item.get("idusers").toString();

                    result.add(temp);
                }
            }
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getFriends_internal completed");
        return result;
    }

    private ArrayList<Route> getRoutesShared_internal() {
        String json = "";
        ArrayList<Route> result = new ArrayList<>();
        try {
            json = (new JSONObject()
                    .put("user_id", UserServices.getUserId())).toString();
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            JSONArray response = new JSONArray(getResponse(json.toString(), getRoutesShared));

            for(int i = 0; i < response.length(); i++) {
                JSONObject item = (JSONObject)response.get(i);
                Route temp = new Route();
                temp.setRoute(item.get("route").toString());
                temp.setRouteName(item.get("route_name").toString());
                temp.setStartPointLat(item.get("start_point_lat").toString());
                temp.setStartPointLon(item.get("start_point_lon").toString());

                result.add(temp);
            }
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "getRoutesShared_internal completed");
        return result;
    }

    private void shareRoute_internal(final int you_id, final Route route) {
        String json = "";
        try {
            json = (new JSONObject()
                    .put("sender_id", UserServices.getUserId())
                    .put("receiver_id", you_id)
                    .put("route", route.getRoute())
                    .put("routeName", route.getRouteName())
                    .put("route_id", route.getRouteIdRemote())
                    .put("startLatitude", route.getStartPointLat())
                    .put("startLongitude", route.getStartPointLon())).toString();
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            getResponse(json.toString(), shareRoute);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "shareRoute_internal completed");
    }

    private void processRequest_internal(final String response, final int you_id) {
        String json = "";
        try {
            json = (new JSONObject()
                    .put("friend_id", UserServices.getUserId())
                    .put("user_id", you_id)
                    .put("response", response)).toString();
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            getResponse(json.toString(), processRequest);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "processRequest_internal completed");
    }

    private ArrayList<User> searchFriends_internal(final String name) {
        String json = "";
        ArrayList<User> result = new ArrayList<>();
        try {
            json = (new JSONObject()
                    .put("username", name)).toString();
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            JSONArray response = new JSONArray(getResponse(json.toString(), searchFriends));
            if(response.length() != 0) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject item = (JSONObject) response.get(i);
                    User temp = new User();
                    temp.username = item.get("username").toString();
                    temp.bio = item.get("bio").toString();
                    temp.id = item.get("idusers").toString();

                    result.add(temp);
                }
            }
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "searchFriends_internal completed");
        return result;
    }

    private void removeFriend_internal(final int you_id) {
        String json = "";
        try {
            json = (new JSONObject()
                    .put("user_id", UserServices.getUserId())
                    .put("friend_id", you_id)).toString();
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            getResponse(json.toString(), removeFriend);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "removeFriend_internal completed");
    }

    private void addFriend_internal(final int you_id) {
        String json = "";
        try {
            json = (new JSONObject()
                    .put("user_id", UserServices.getUserId())
                    .put("friend_id", you_id)).toString();
            Log.d("TESTING", json);
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            Log.d("TESTING", getResponse(json.toString(), addFriend));
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
        Log.d("Connector", "addFriend_internal completed");
    }
}