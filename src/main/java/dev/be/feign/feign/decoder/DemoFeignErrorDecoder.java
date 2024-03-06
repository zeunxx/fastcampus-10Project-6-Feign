package dev.be.feign.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class DemoFeignErrorDecoder  implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus httpStatus = HttpStatus.resolve(response.status());

        // 외부 컴포넌트와 통신때 정의해놓은 에러일 경우 적절한 핸들링!
        if(httpStatus == HttpStatus.NOT_FOUND){
            System.out.println("[DemoFeignErrorDecoder] HttpStatus = " + httpStatus);
            throw new RuntimeException(String.format("[RuntimeException] Http Status is %s", httpStatus ));
        }

        // 정의하지 않았던 에러의 경우: default로 선언되어있는 메소드 사용
        return errorDecoder.decode(methodKey, response);
    }
}
