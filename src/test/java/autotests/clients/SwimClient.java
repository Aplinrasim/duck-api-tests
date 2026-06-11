package autotests.clients;

import com.consol.citrus.TestCaseRunner;

public class SwimClient extends DuckClient {

    public void duckSwim(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/swim?id=" + id;
        sendGetMethod(runner, path, duckService);
    }
}