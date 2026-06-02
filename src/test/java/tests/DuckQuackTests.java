package tests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckActionsClient;
import autotests.payloads.SoundResponse;

public class DuckQuackTests extends DuckActionsClient {

    @Test(description = "Четный id, валидация по String. Ответ четных 'moo'")
    @CitrusTest
    public void testQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "6", "1", "1");  // id=6, sound=quack в БД, но по заданию meow
        validateResponse(runner, "{\"sound\": \"moo\"}");
    }

    @Test(description = "Нечетный id, звук 'quack', валидация из файла")
    @CitrusTest
    public void testQuackWithOddIdFromFile(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "3", "1", "1");
        validateResponseFromFile(runner, "responses/quackResponse.json");
    }

    @Test(description = "Нечетный id, звук 'quack', валидация из payloads")
    @CitrusTest
    public void testQuackWithOddIdPayload(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "3", "1", "1");
        SoundResponse expectedResponse = new SoundResponse("quack");
        validateResponseFromPayload(runner, expectedResponse);
    }
}