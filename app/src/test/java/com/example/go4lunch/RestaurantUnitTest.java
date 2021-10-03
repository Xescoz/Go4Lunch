package com.example.go4lunch;

import com.example.go4lunch.model.Geometry;
import com.example.go4lunch.model.Location;
import com.example.go4lunch.model.OpenNow;
import com.example.go4lunch.model.Photo;
import com.example.go4lunch.model.Restaurant;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class RestaurantUnitTest {

    @Test
    public void RestaurantModel() {

        Restaurant restaurant = new Restaurant(
                "ChefoejfµEfegEght",
                "Pizza Roméo",
                "4 rue test",
                "picture test",
                new Geometry(new Location(123456789, 123456789)),
                new OpenNow(true),
                5,
                3,
                new ArrayList<>(Collections.singletonList(new Photo("photoRef"))),
                "0630226695",
                "httpss//test.com"
        );

        assertEquals("ChefoejfµEfegEght", restaurant.getPlaceId());
        assertEquals("Pizza Roméo", restaurant.getName());
        assertEquals("4 rue test", restaurant.getAddress());
        assertEquals("picture test", restaurant.getImage());
        assertEquals(123456789, restaurant.getGeometry().getLocation().getLat(),0);
        assertEquals(123456789, restaurant.getGeometry().getLocation().getLng(),0);
        assertEquals(true, restaurant.getOpeningTime().isOpenNow());
        assertEquals(5, restaurant.getRating(),0);
        assertEquals(3, restaurant.getNumberPersons());
        assertEquals("photoRef",restaurant.getPhotos().get(0).getPhotoReference());
        assertEquals("0630226695", restaurant.getPhoneNumber());
        assertEquals("httpss//test.com", restaurant.getWebsite());
    }

}
