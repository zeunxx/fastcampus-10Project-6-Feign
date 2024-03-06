package dev.be.feign.config;

import dev.be.feign.feign.interceptor.DemoFeignInterceptor;
import dev.be.feign.feign.logger.FeignCustomLogger;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig { // 글로벌 feign config 파일 == 모든 client에게 일괄적 적용

    @Bean
    public Logger feignLogger(){
        // 모든 client가 공통적으로 사용해야 하므로 이곳에 bean 설정
        return new FeignCustomLogger();
    }


}
