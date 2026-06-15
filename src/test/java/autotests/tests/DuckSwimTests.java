package autotests.tests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckActionsClient;

public class DuckSwimTests extends DuckActionsClient {

    @Test(description = "Existing id - returns 404")
    @CitrusTest
    public void testSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "10");
        validateStatus(runner, HttpStatus.NOT_FOUND);
    }

    @Test(description = "Non-existing id - returns 404")
    @CitrusTest
    public void testSwimWithNonExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "999999");
        validateStatus(runner, HttpStatus.NOT_FOUND);
    }
}