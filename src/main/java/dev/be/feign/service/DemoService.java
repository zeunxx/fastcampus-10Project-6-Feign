package dev.be.feign.service;

import dev.be.feign.common.dto.BaseRequestInfo;
import dev.be.feign.common.dto.BaseResponseInfo;
import dev.be.feign.feign.client.DemoFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final DemoFeignClient demoFeignClient;
    public String get(){
        ResponseEntity<BaseResponseInfo> response = demoFeignClient.callGet("CustomHeader", "customName", 1L);
        System.out.println("name: "+response.getBody().getName());
        System.out.println("age: "+response.getBody().getName());
        System.out.println("header = " + response.getBody().getHeader());
        return "get";
    }

    public String post(){
        BaseRequestInfo baseRequestInfo = BaseRequestInfo.builder()
                                                        .name("customName")
                                                        .age(2L)
                                                        .build();

        ResponseEntity<BaseResponseInfo> response = demoFeignClient.callPost("CustomHeader", baseRequestInfo);
        System.out.println("name: "+response.getBody().getName());
        System.out.println("age: "+response.getBody().getName());
        System.out.println("header = " + response.getBody().getHeader());
        return "post";
    }

    public String error(){
        demoFeignClient.callErrorDecoder();
        return "error";
    }
}
