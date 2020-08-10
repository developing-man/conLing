package cn.tedu.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
@SpringBootApplication
@MapperScan("cn.tedu.store.mapper")
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Bean
    public MultipartConfigElement fileSize() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        //设置文件大小最大上限
        DataSize dataSize = DataSize.ofMegabytes(2);
        factory.setMaxFileSize(dataSize);
        factory.setMaxRequestSize(dataSize);

        return factory.createMultipartConfig();
    }
}
