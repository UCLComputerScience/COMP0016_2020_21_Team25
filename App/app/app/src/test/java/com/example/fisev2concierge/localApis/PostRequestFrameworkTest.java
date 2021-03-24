package com.example.fisev2concierge.localApis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class PostRequestFrameworkTest {

    @Test
    public void requestTest(){
        PostRequestFramework postRequestFramework = new PostRequestFramework("http://localhost:8000/");
        ArrayList<String> responeArrayList = postRequestFramework.request("query", "message=\"" + "open snapchat" +"\"&sender=\"concierge\"");
        assertTrue(responeArrayList.size()>0);
    }

    @Test
    public void errorRequestTest(){
        PostRequestFramework postRequestFramework = new PostRequestFramework("localhost:8000/");
        assertTrue(postRequestFramework.request("random", "random").isEmpty());
    }

}