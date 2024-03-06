package dev.be.feign.feign.logger;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Iterator;

@RequiredArgsConstructor
public class FeignCustomLogger extends Logger {

    private static final int DEFAULT_SLOW_API_TIME = 3_000;
    private static final String SLOW_API_NOTICE = "slow API";

    @Override
    protected void log(String configKey, String format, Object... args) {
        // log를 어떤 형식으로 남길지 정해준다.
        System.out.println(String.format(methodTag(configKey)+ format,args));
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        // request handling 가능
        System.out.println("[logRequest] "+request);
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        // request 와 response handling 가능
        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
        String reason = response.reason() != null && logLevel.compareTo(Logger.Level.NONE) > 0 ? " " + response.reason() : "";
        int status = response.status();
        this.log(configKey, "<--- %s %s%s (%sms)", protocolVersion, status, reason, elapsedTime);
        if (logLevel.ordinal() >= Logger.Level.HEADERS.ordinal()) {
            Iterator var9 = response.headers().keySet().iterator();

            while(true) {
                String field;
                do {
                    if (!var9.hasNext()) {
                        int bodyLength = 0;
                        if (response.body() != null && status != 204 && status != 205) {
                            if (logLevel.ordinal() >= Logger.Level.FULL.ordinal()) {
                                this.log(configKey, "");
                            }

                            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                            bodyLength = bodyData.length;
                            if (logLevel.ordinal() >= Logger.Level.FULL.ordinal() && bodyLength > 0) {
                                this.log(configKey, "%s", Util.decodeOrDefault(bodyData, Util.UTF_8, "Binary data"));
                            }

                            // custom code : api 응답이 내가 정한 시간(3s)보다 오래 걸리면 log 띄움
                            if(elapsedTime> DEFAULT_SLOW_API_TIME){
                                log(configKey, "[%s] elaspsedTIme : %s",SLOW_API_NOTICE, elapsedTime );
                            }

                            this.log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
                            return response.toBuilder().body(bodyData).build();
                        }

                        this.log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
                        return response;
                    }

                    field = (String)var9.next();
                } while(!this.shouldLogResponseHeader(field));

                Iterator var11 = Util.valuesOrEmpty(response.headers(), field).iterator();

                while(var11.hasNext()) {
                    String value = (String)var11.next();
                    this.log(configKey, "%s: %s", field, value);
                }
            }
        } else {
            return response;
        }
    }
}
