package tests;

import autotests.clients.PropertiesClient;
import autotests.payloads.DuckPropertiesResponse;
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
    @Feature("Получение характеристик уточки")
    @Story("GET /api/duck/action/properties + проверка в БД")
    public class DuckPropertiesTest extends PropertiesClient {

        @Test(description = "Характеристики уточки с четным id и материалом wood - проверка в БД")
        @CitrusTest
        @Step("Тест: Получение характеристик утки с чётным ID и материалом wood")
        public void testPropertiesEvenIdWood(@Optional @CitrusResource TestCaseRunner runner) {
            createDuckTable(runner);
            runner.variable("duckId", "8");
            runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
            executeSql(runner,
                    "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                            "VALUES (${duckId}, 'brown', 5.0, 'wood', 'creak', 'FIXED')");
            duckProperties(runner, "${duckId}");
            validateResponse(runner, "{}");
            validateDuckInDb(runner, "${duckId}", "brown", "5.0", "wood", "creak", "FIXED");
        }

        @Test(description = "Характеристики уточки с нечетным id и материалом rubber, валидация из файла")
        @CitrusTest
        @Step("Тест: Получение характеристик утки с нечётным ID и материалом rubber (валидация из файла)")
    public void testPropertiesOddIdRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        runner.variable("duckId", "7");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, 'yellow', 10.0, 'rubber', 'quack', 'UNDEFINED')");
        duckProperties(runner, "7");
        validateResponseFromFile(runner, "responses/getDuckPropertiesTest/duckPropertiesRubber.json");
    }
        @Test(description = "Характеристики уточки с нечетным id и материалом rubber, валидация из payloads")
        @CitrusTest
        @Step("Тест: Получение характеристик утки с нечётным ID и материалом rubber (валидация через модель)")
    public void testPropertiesOddIdRubberMaterialPayload(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        runner.variable("duckId", "7");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, 'yellow', 10.0, 'rubber', 'quack', 'UNDEFINED')");
        duckProperties(runner, "7");
        DuckPropertiesResponse expectedResponse = new DuckPropertiesResponse(
                "yellow", 1000.0, "rubber", "quack", "UNDEFINED"
        );
        validateResponseFromPayload(runner, expectedResponse);
    }
}