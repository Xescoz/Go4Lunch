package com.example.go4lunch;

import com.example.go4lunch.model.Workmate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;

public class WorkmateUnitTest {


    @Test
    public void WorkmateModel(){
        Workmate workmate = new Workmate(
                "Jean John",
            "ChefoejfµEfegEght",
            "testpicture",
            "Pizza Roméo",
            new ArrayList<>(Arrays.asList("aaaaa","bbbb")),
            true
        );

        assertEquals("Jean John", workmate.getName());
        assertEquals("ChefoejfµEfegEght", workmate.getCurrentRestaurant());
        assertEquals("Pizza Roméo", workmate.getCurrentRestaurantName());
        assertEquals("testpicture", workmate.getPicture());
        assertEquals(true, workmate.getNotification());
        assertTrue(new ArrayList<>(Arrays.asList("aaaaa", "bbbb")).containsAll(workmate.getLikes()));
    }
}
