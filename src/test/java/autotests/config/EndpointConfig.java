package autotests.config;

import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.http.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@Configuration
public class EndpointConfig {

    @Bean
    public HttpClient duckService() {
        return new HttpClientBuilder()
                .requestUrl("http://localhost:2222")
                .build();
    }

    @Bean("testDb")
    public SingleConnectionDataSource db() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        // Используем embedded режим - НЕ через TCP, а напрямую в памяти
        dataSource.setUrl("jdbc:h2:tcp://localhost:9092/mem:ducks");
        dataSource.setUsername("dev");
        dataSource.setPassword("dev");
        dataSource.setSuppressClose(true);  // важно!
        return dataSource;
    }

    @Autowired
    protected SingleConnectionDataSource testDb;

}