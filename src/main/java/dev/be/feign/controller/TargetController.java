package dev.be.feign.controller;

import dev.be.feign.common.dto.BaseRequestInfo;
import dev.be.feign.common.dto.BaseResponseInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/target_server")
public class TargetController {

    @GetMapping("/get")
    public BaseResponseInfo demoGet(@RequestHeader("CustomHeaderName") String header,
                                    @RequestParam("name") String name,
                                    @RequestParam("age") Long age){
        return BaseResponseInfo.builder()
                .header(header)
                .name(name)
                .age(age)
                .build();
    }

    @PostMapping("/post")
    public BaseResponseInfo demoPost(@RequestHeader("CustomHeaderName") String header,
                                     @RequestBody BaseRequestInfo body) {
            return BaseResponseInfo.builder()
                                    .header(header)
                                    .name(body.getName())
                                    .age(body.getAge())
                                    .build();
    }

    @GetMapping("/error")
    public ResponseEntity<BaseResponseInfo> demoError(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
