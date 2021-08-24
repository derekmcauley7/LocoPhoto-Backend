package com.loco.photo.locophoto.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.awt.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @MockBean
    private ImageController imageController;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    public void getAllImagesList() throws Exception {

        String uri = "/images";
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        Image[] images = imageController.index().toArray(new Image[10]);
        assertTrue(images.length > 0);
    }

    @Test
    public void getAllUserImagesByEmail() throws Exception {

        String uri = "/userImages/derekmcauley7@gmail.com";
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        Image[] images = imageController.index().toArray(new Image[10]);
        assertTrue(images.length > 0);
    }

    @Test
    public void getImagesForLocation() throws Exception {

        String uri = "/allImages/-6.353800280291502/53.3495283197085";
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        Image[] images = imageController.index().toArray(new Image[10]);
        assertTrue(images.length > 0);
    }
    
}