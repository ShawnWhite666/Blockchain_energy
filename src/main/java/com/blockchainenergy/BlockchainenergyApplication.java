package com.blockchainenergy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ServletComponentScan //启动器启动时，扫描本目录以及子目录带有的webservlet注解的
@MapperScan({"com.blockchainenergy.*.*.dao"})
public class BlockchainenergyApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlockchainenergyApplication.class, args);
    }
}
