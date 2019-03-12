package com.decathlon.finance.taxreport;

import com.decathlon.finance.taxreport.config.CustomConfigUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.decathlon.finance.taxreport.mapper")
//@ImportResource(locations={"classpath:/security/securityContext.xml"})
public class TaxReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxReportApplication.class, args);
    }
}
