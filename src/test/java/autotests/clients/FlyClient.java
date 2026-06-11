package autotests.clients;

import com.consol.citrus.TestCaseRunner;

public class FlyClient extends DuckClient {

    public void duckFly(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/fly?id=" + id;
        sendGetMethod(runner, path, duckService);
    }
}