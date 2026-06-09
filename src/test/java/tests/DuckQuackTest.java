package tests;

import autotests.clients.QuackClient;
import autotests.payloads.SoundResponse;
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
@Feature("Команда крякать уточки")
@Story("GET /api/duck/action/quack + проверка в БД")
public class DuckQuackTest extends QuackClient {

    @Test(description = "Четный id, валидация по String. Ответ четных 'moo'")
    @CitrusTest
    @Step("Тест: Кряканье утки с чётным ID кол-во повторений и кол-во звуков равно 2")
    public void testQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        runner.variable("duckId", "6");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, 'yellow', 5.0, 'wood', 'quack', 'FIXED')");
        duckQuack(runner, "6", "2", "2");
        validateResponse(runner, "{\"sound\": \"moo-moo, moo-moo\"}");
    }

    @Test(description = "Нечетный id, звук 'quack', валидация из файла")
    @CitrusTest
    @Step("Тест: Кряканье утки с нечётным ID кол-во повторений 3 (валидация из файла)")
    public void testQuackWithOddIdFromFile(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        runner.variable("duckId", "3");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, 'yellow', 5.0, 'wood', 'quack', 'FIXED')");
        duckQuack(runner, "3", "3", "1");
        validateResponseFromFile(runner, "responses/quackResponse.json");
    }

    @Test(description = "Нечетный id, звук 'quack', валидация из payloads")
    @CitrusTest
    @Step("Тест: Кряканье утки с нечётным ID (валидация через модель)")
    public void testQuackWithOddIdPayload(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        runner.variable("duckId", "3");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, 'yellow', 5.0, 'wood', 'quack', 'FIXED')");
        duckQuack(runner, "3", "1", "1");
        SoundResponse expectedResponse = new SoundResponse("quack");
        validateResponseFromPayload(runner, expectedResponse);
    }
}