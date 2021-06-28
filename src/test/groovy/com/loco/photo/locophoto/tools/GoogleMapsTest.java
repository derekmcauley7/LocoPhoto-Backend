package com.loco.photo.locophoto.tools;

import com.amazonaws.util.IOUtils;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleMapsTest {
    
    @Test
    public void shouldReturnCityFromJson() throws Exception {
        //given
        InputStream is = new FileInputStream("src/test/groovy/com/loco/photo/locophoto/tools/googlemaps.json");
        String jsonTxt = IOUtils.toString(is);
        System.out.println(jsonTxt);
        JSONObject jsonObj = new JSONObject(jsonTxt);
        
        //when
        String cityName = GoogleMaps.getCityFromResponse(jsonObj);
        
        //then
        assertEquals(" County Dublin, Ireland", cityName);
    }

}