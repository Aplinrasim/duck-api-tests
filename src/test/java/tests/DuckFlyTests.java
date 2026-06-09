package tests;

import autotests.clients.FlyClient;
import autotests.payloads.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-action-controller")
@Feature("Команда лететь уточки")
@Story("GET /api/duck/action/fly + проверка в БД")
public class DuckFlyTests extends FlyClient {

    @Test(description = "Активные крылья, валидация из responses")
    @CitrusTest
    @Step("Тест: Полёт утки с активными крыльями (ACTIVE)")
    public void testFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        createDuckInDatabase(runner, "orange", 3.0, "cheese", "hrum", "ACTIVE");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        duckFly(runner, "${duckId}");
        validateResponseFromFile(runner, "responses/getDuckFlyTest/flyActiveWings.json");
    }

    @Test(description = "Неопределенные крылья, валидация из файла")
    @CitrusTest
    @Step("Тест: Полёт утки с неопределёнными крыльями (UNDEFINED)")
    public void testFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        createDuckInDatabase(runner, "orange", 3.0, "cheese", "hrum", "UNDEFINED");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        duckFly(runner, "${duckId}");
        MessageResponse expectedResponse = new MessageResponse("Wings are not detected :(");
        validateResponseFromPayload(runner, expectedResponse);
    }

    @Test(description = "Фиксированные крылья, валидация по String")
    @CitrusTest
    @Step("Тест: Полёт утки с фиксированными крыльями (FIXED)")
    public void testFlyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        createDuckInDatabase(runner, "orange", 3.0, "cheese", "hrum", "FIXED");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\"message\": \"I can not fly :C\"}");
    }
}