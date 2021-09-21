package com.example.go4lunch;

import androidx.lifecycle.ViewModelStoreOwner;

import com.example.go4lunch.repository.RestaurantDetailRepository;
import com.example.go4lunch.repository.RestaurantsRepository;
import com.example.go4lunch.viewmodel.RestaurantViewModel;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class RestaurantViewModelUnitTest extends SampleBaseTestCase {

    private RestaurantViewModel restaurantViewModel;
    private LatLng defaultLatLng;
    @Mock
    RestaurantsRepository restaurantsRepository;
    @Mock
    RestaurantDetailRepository restaurantDetailRepository;
    @Mock
    ViewModelStoreOwner viewModelStoreOwner;

    @Before
    public void setup(){
        defaultLatLng = new LatLng(48.6602, 2.2428);
        //restaurantViewModel = new ViewModelProvider(mainActivity).get(RestaurantViewModel.class);
    }

    @Test
    public void test() {
        //assertNotNull(restaurantViewModel.getRestaurants(defaultLatLng).getValue());
        assertNotNull(restaurantViewModel.getRestaurantDetail("ChIJXQqXrJ_X5UcR6lchPmz9jls"));
    }
}