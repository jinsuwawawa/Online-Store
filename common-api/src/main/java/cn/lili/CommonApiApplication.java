package cn.lili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 基础API
 */
@EnableCaching
@SpringBootApplication
public class CommonApiApplication {

    public static void main(String[] args) {
        System.setProperty("rocketmq.client.logUseSlf4j","true");
        SpringApplication.run(CommonApiApplication.class, args);
    }

}
