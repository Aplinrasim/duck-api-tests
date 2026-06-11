package autotests.clients;

import com.consol.citrus.TestCaseRunner;

public class QuackClient extends DuckClient {

    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        String path = "/api/duck/action/quack?id=" + id
                + "&repetitionCount=" + repetitionCount
                + "&soundCount=" + soundCount;
        sendGetMethod(runner, path, duckService);
    }
}