package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends BaseTest {


    @Step("Создание утки в БД с автогенерацией ID и извлечением ID")
    public void createDuckInDatabase(TestCaseRunner runner, String color, double height,
                                     String material, String sound, String wingsState) {
        String sql = String.format(Locale.US,
                "INSERT INTO DUCK (color, height, material, sound, wings_state) VALUES ('%s', %f, '%s', '%s', '%s')",
                color, height, material, sound, wingsState);
        executeSql(runner, sql);
        runner.$(query(testDb)
                .statement("SELECT MAX(id) as id FROM DUCK")
                .extract("id", "duckId"));
    }
    @Step("Удаление утки из БД по id={id}")
    public void deleteDuckFromDb(TestCaseRunner runner, String id) {
        String sql = String.format("DELETE FROM DUCK WHERE id = %s", id);
        executeSql(runner, sql);
    }
    @Step("Проверка, что утка удалена из БД по id={id}")
    public void validateDuckDeletedFromDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement(String.format("SELECT COUNT(*) as count FROM DUCK WHERE id = %s", id))
                .validate("COUNT", "0"));
    }
    @Step("Проверка данных утки в БД")
    public void validateDuckInDb(TestCaseRunner runner, String id, String color, String height,
                                 String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement(String.format("SELECT * FROM DUCK WHERE id = %s", id))
                .validate("ID", id)
                .validate("COLOR", color)
                .validate("HEIGHT", height)
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }
    public void createDuckTable(TestCaseRunner runner) {
        executeSql(runner, "DROP TABLE IF EXISTS DUCK");
        String sql = "CREATE TABLE DUCK (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "color VARCHAR(255), " +
                "height DOUBLE, " +
                "material VARCHAR(255), " +
                "sound VARCHAR(255), " +
                "wings_state VARCHAR(255)" +
                ")";
        executeSql(runner, sql);
    }

}
