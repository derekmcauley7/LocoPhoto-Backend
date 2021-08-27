package com.loco.photo.locophoto.tools

import ch.qos.logback.classic.Logger
import org.apache.http.util.TextUtils
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.LoggerFactory

class GoogleMaps {
    
    private static Logger logger = LoggerFactory.getLogger(GoogleMaps.class)
    
    static String getCity(String lat, String lng) {
        String API_KEY = GoogleCredentials.API_KEY
        String cityName
        String uri = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "2&key=" + API_KEY
        try {
            StringBuffer response = sendPlacesRequest(uri)
            cityName = getCityFromResponse(new JSONObject(response.toString()))

        } catch (Exception e) {
            logger.error(e.toString())
            return null
        }
        return cityName
    }

    private static StringBuffer sendPlacesRequest(String uri) {
        StringBuffer response = new StringBuffer();
        try {
            BufferedReader bf = sendGetRequest(uri)
            convertResponse(bf, response)
        } catch (Exception e) {
            logger.error(e.printStackTrace())
        }
        return response
    }

    private static void convertResponse(BufferedReader bf, StringBuffer response) {
        String inputLine
        while ((inputLine = bf.readLine()) != null) {
            response.append(inputLine)
        }
        bf.close()
    }

    private static BufferedReader sendGetRequest(String uri) {
        URL obj = new URL(uri)
        HttpURLConnection con = (HttpURLConnection) obj.openConnection()
        con.setRequestMethod("GET")
        con.setRequestProperty("User-Agent", "Mozilla/5.0")
        BufferedReader bf = new BufferedReader(
                new InputStreamReader(con.getInputStream()))
        bf
    }
    
    private static String setCityName(String longName, String city) {
        city = longName
        cleanName(city)
    }
    
    protected static String getCityFromResponse(JSONObject jsonObj) {
        String cityName = ""
        JSONArray addressComponents = jsonObj.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");

        for (int i = 0; i < addressComponents.length(); i++) {
            JSONObject address = addressComponents.getJSONObject(i)
            String longName = address.getString("long_name")
            JSONArray types = address.getJSONArray("types")
            String type = types.getString(0)
            if (!TextUtils.isEmpty(longName)) {
                if (type.equalsIgnoreCase("administrative_area_level_1") && cityName.isEmpty()) {
                    cityName = setCityName(longName, cityName)
                }
                if (type.equalsIgnoreCase("locality")) {
                    cityName = setCityName(longName, cityName)
                }
                if (type.equalsIgnoreCase("postal_town")) {
                    cityName = setCityName(longName, cityName)
                }
                if (type.equalsIgnoreCase("country") && cityName.isEmpty()) {
                    cityName = setCityName(longName, cityName)
                }
            }
        }
        cityName
    }

    protected static String cleanName(String s) {
        return s.replaceAll("[0-9]", "").trim()
    }
}

