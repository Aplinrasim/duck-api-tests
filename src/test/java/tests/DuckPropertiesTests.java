package tests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import clients.DuckActionsClient;

public class DuckPropertiesTests extends DuckActionsClient {

    @Test(description = "ID - even number. Duck with material = wood")
    @CitrusTest
    public void testPropertiesEvenIdWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "2");
        validateResponse(runner, "{}");
    }

    @Test(description = "ID - odd number. Duck with material = rubber")
    @CitrusTest
    public void testPropertiesOddIdRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "1");
        validateResponse(runner, "{\"color\":\"yellow\",\"height\":500.0,\"material\":\"rubber\",\"sound\":\"quack\",\"wingsState\":\"ACTIVE\"}");
    }
}