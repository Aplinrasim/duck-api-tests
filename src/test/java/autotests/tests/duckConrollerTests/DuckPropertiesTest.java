package autotests.tests.duckConrollerTests;

import autotests.clients.PropertiesClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;


@Epic("Тесты duck-action-controller")
@Feature("Получение характеристик уточки")
@Story("GET /api/duck/action/properties + проверка в БД")
public class DuckPropertiesTest extends PropertiesClient {

    DuckProperties evenDuck1 = new DuckProperties()
            .id(2L)
            .color("yellow")
            .height(3.0)
            .material("wood")
            .sound("squeak")
            .wingsState("ACTIVE");
    DuckProperties evenDuck2 = new DuckProperties()
            .id(4L)
            .color("green")
            .height(2.5)
            .material("wood")
            .sound("clank")
            .wingsState("UNDEFINED");
    DuckProperties evenDuck3 = new DuckProperties()
            .id(6L)
            .color("blue")
            .height(4.0)
            .material("wood")
            .sound("quack")
            .wingsState("FIXED");
    DuckProperties evenDuck4 = new DuckProperties()
            .id(8L)
            .color("brown")
            .height(5.0)
            .material("wood")
            .sound("creak")
            .wingsState("FIXED");
    DuckProperties evenDuck5 = new DuckProperties()
            .id(10L)
            .color("orange")
            .height(6.0)
            .material("wood")
            .sound("creak")
            .wingsState("ACTIVE");

    @DataProvider(name = "evenIdDucks")
    public Object[][] evenIdDuckProvider() {
        return new Object[][]{
                {evenDuck1, null},
                {evenDuck2, null},
                {evenDuck3, null},
                {evenDuck4, null},
                {evenDuck5, null}
        };
    }

    @Test(description = "Четные ID - ожидаем пустой ответ", dataProvider = "evenIdDucks")
    @CitrusTest
    @CitrusParameters({"duck", "runner"})
    public void testEvenIdProperties(DuckProperties duck, @Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        String id = String.valueOf(duck.id());
        runner.variable("duckId", id);
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, '" + duck.color() + "', " + duck.height() +
                        ", '" + duck.material() + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");
        duckProperties(runner, id);
        validateResponse(runner, "{}");
        validateDuckInDb(runner, id, duck.color(), String.valueOf(duck.height()),
                duck.material(), duck.sound(), duck.wingsState());
    }

    DuckProperties oddDuck1 = new DuckProperties()
            .id(1L)
            .color("yellow")
            .height(3.0)
            .material("rubber")
            .sound("meow")
            .wingsState("ACTIVE");
    DuckProperties oddDuck2 = new DuckProperties()
            .id(3L)
            .color("green")
            .height(2.5)
            .material("rubber")
            .sound("moo")
            .wingsState("UNDEFINED");
    DuckProperties oddDuck3 = new DuckProperties()
            .id(5L)
            .color("blue")
            .height(4.0)
            .material("rubber")
            .sound("quack")
            .wingsState("FIXED");
    DuckProperties oddDuck4 = new DuckProperties()
            .id(7L)
            .color("brown")
            .height(5.0)
            .material("rubber")
            .sound("creak")
            .wingsState("FIXED");
    DuckProperties oddDuck5 = new DuckProperties()
            .id(9L)
            .color("orange")
            .height(6.0)
            .material("rubber")
            .sound("creak")
            .wingsState("ACTIVE");

    @DataProvider(name = "oddIdDucks")
    public Object[][] oddIdDuckProvider() {
        return new Object[][]{
                {oddDuck1, null},
                {oddDuck2, null},
                {oddDuck3, null},
                {oddDuck4, null},
                {oddDuck5, null}
        };
    }

    @Test(description = "Нечетные ID - ожидаем характеристики с высотой, увеличенной в 100 раз", dataProvider = "oddIdDucks")
    @CitrusTest
    @CitrusParameters({"duck", "runner"})
    public void testOddIdProperties(DuckProperties duck, @Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        String id = String.valueOf(duck.id());
        runner.variable("duckId", id);
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, '" + duck.color() + "', " + duck.height() +
                        ", '" + duck.material() + "', '" + duck.sound() + "', '" + duck.wingsState() + "')");
        duckProperties(runner, id);
        double expectedHeight = duck.height() * 100;
        String expectedResponse = String.format(java.util.Locale.US,
                "{\"color\":\"%s\",\"height\":%f,\"material\":\"%s\",\"sound\":\"%s\",\"wingsState\":\"%s\"}",
                duck.color(), expectedHeight, duck.material(), duck.sound(), duck.wingsState());
        validateResponse(runner, expectedResponse);
        validateDuckInDb(runner, id, duck.color(), String.valueOf(duck.height()),
                duck.material(), duck.sound(), duck.wingsState());
    }
}