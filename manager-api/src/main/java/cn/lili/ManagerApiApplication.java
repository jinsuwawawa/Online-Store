package cn.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 运营后台 API
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class ManagerApiApplication {

    @Primary
    @Bean
    public TaskExecutor primaryTask() {
        return new ThreadPoolTaskExecutor();
    }

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        System.setProperty("rocketmq.client.logUseSlf4j","true");
        SpringApplication.run(ManagerApiApplication.class, args);
    }

}
