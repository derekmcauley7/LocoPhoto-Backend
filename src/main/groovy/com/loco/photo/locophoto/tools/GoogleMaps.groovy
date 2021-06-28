package com.loco.photo.locophoto.tools


import com.google.code.geocoder.model.LatLng
import org.json.JSONObject


class GoogleMaps {

   static String getCity(String lat, String lng) {

       String API_KEY = GoogleCredentials.API_KEY

       String cityName
       LatLng latLng = new LatLng()
       latLng.setLat(new BigDecimal(lat))
       latLng.setLng(new BigDecimal(lng))

        String uri = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "2&key=" + API_KEY
       
        try {
            StringBuffer response = sendPlacesRequest(uri)
            cityName = getCityFromResponse(new JSONObject(response.toString()))
            
        } catch (Exception e) {
            return null
        }

       return cityName
    }

    private static StringBuffer sendPlacesRequest(String uri) {
        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(uri)
            HttpURLConnection con = (HttpURLConnection) obj.openConnection()

            con.setRequestMethod("GET")

            con.setRequestProperty("User-Agent", "Mozilla/5.0")

            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))
            String inputLine

            while ((inputLine = bf.readLine()) != null) {
                response.append(inputLine)
            }
           bf.close()

        } catch (Exception e) {
            e.printStackTrace()
        }
        return response
    }

    static String getCityFromResponse(JSONObject jsonObj) {
        JSONObject array2 = jsonObj.getJSONObject("plus_code")
        String city = array2.get("compound_code").toString()
        city = city.substring(city.substring(0, city.lastIndexOf(",")).lastIndexOf(",") + 1)
        city
    }
}

