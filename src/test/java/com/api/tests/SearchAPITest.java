package com.api.tests;

import static com.api.constant.Role.*;
import com.api.request.model.Search;
import com.api.services.JobService;
import com.api.utils.SpecUtil;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(com.listeners.APITestListener.class)
public class SearchAPITest {
    private JobService jobService ;
    private static final String JOB_NUMBER = "JOB_150960";
    private Search searchPayload;

    @BeforeMethod(description = "Initializing Job Service and creating a search payload")
    public void setup(){
        jobService = new JobService();
        searchPayload = new Search(JOB_NUMBER);
    }

    @Test(description = "Verify if search job api is working", groups = {"api", "smoke", "regression"})
    public void searchAPITest(){
        jobService.search(FD, searchPayload)
                .then()
                .spec(SpecUtil.responseSpec_OK())
                .body("message", Matchers.equalTo("Success"));
    }
}
