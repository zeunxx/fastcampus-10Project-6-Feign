package dev.be.feign.feign.config;

import dev.be.feign.feign.decoder.DemoFeignErrorDecoder;
import dev.be.feign.feign.interceptor.DemoFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.zip.DeflaterOutputStream;

@Configuration
public class DemoFeignConfig { // 특정 feign client를 위한 config 파일

    @Bean // interceptor bean 등록
    public DemoFeignInterceptor feignInterceptor(){
        return DemoFeignInterceptor.of();
    }

    @Bean // errorDecoder bean 등록
    public DemoFeignErrorDecoder feignErrorDecoder(){
        return new DemoFeignErrorDecoder();
    }
}
