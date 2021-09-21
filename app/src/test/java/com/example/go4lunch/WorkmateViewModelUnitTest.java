package com.example.go4lunch;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.repository.WorkmateRepository;
import com.example.go4lunch.viewmodel.WorkmateViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class WorkmateViewModelUnitTest extends SampleBaseTestCase{
    private Workmate workmate;

    private String uid;

    private WorkmateViewModel workmateViewModel;

    @Mock
    private WorkmateRepository workmateRepository;

    @Before
    public void setup(){
        uid = "0000";
        workmate = new Workmate("Test","","","",null,null);
        workmateViewModel = new WorkmateViewModel(workmateRepository);
    }

    @Test
    public void test(){
        workmateViewModel.writeNewUser(workmate,uid);
        assertNotNull(workmateViewModel.getCurrentUserFromDB(uid).getValue());
    }
}
