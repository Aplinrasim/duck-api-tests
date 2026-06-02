package tests;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import clients.DuckActionsClient;

public class DuckFlyTests extends DuckActionsClient {

    @Test(description = "Active wings - duck flies")
    @CitrusTest
    public void testFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner, "10");
        validateResponse(runner, "{\"message\": \"I am flying :)\"}");
    }

    @Test(description = "Undefined wings - error")
    @CitrusTest
    public void testFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner, "13");
        validateResponse(runner, "{\"message\": \"Wings are not detected :(\"}");
    }

    @Test(description = "Fixed wings - duck cannot fly")
    @CitrusTest
    public void testFlyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner, "14");
        validateResponse(runner, "{\"message\": \"I can not fly :C\"}");
    }
}