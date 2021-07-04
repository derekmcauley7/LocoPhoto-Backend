package com.loco.photo.locophoto.tools;

import com.amazonaws.util.IOUtils;
import org.json.JSONObject;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleMapsTest {
    
    @Test
    public void shouldReturnCityDublinFromJson() throws Exception {
        //given
        InputStream is = new FileInputStream("src/test/groovy/com/loco/photo/locophoto/tools/googlemapsDublin.json");
        String jsonTxt = IOUtils.toString(is);
        JSONObject jsonObj = new JSONObject(jsonTxt);
        
        //when
        String cityName = GoogleMaps.getCityFromResponse(jsonObj);
        
        //then
        assertEquals("Dublin", cityName);
    }

    @Test
    public void shouldReturnCityLondonFromJson() throws Exception {
        //given
        InputStream is = new FileInputStream("src/test/groovy/com/loco/photo/locophoto/tools/googlemapsLondon.json");
        String jsonTxt = IOUtils.toString(is);
        JSONObject jsonObj = new JSONObject(jsonTxt);

        //when
        String cityName = GoogleMaps.getCityFromResponse(jsonObj);

        //then
        assertEquals("London", cityName);
    }

}