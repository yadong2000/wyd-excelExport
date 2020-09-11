package com.convenient.excel.util;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.springframework.data.relational.core.query.Criteria.where;

public class ControllerUtil {
    public static final String UNDERLINE = "_";
    public static final Function<ServerRequest, Flux<String>> GET_REQUEST_BODY = (request -> request
            .exchange()
            .getRequest()
            .getBody()
            .map(dataBuffer -> decodeDataBuffer(dataBuffer))
    );

    protected static String decodeDataBuffer(DataBuffer dataBuffer) {
        Charset charset = StandardCharsets.UTF_8;
        CharBuffer charBuffer = charset.decode(dataBuffer.asByteBuffer());
        DataBufferUtils.release(dataBuffer);
        String value = charBuffer.toString();
        return value;
    }

    public static final ServerResponse.BodyBuilder BODYBUILDER = ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON);

    public static final BiFunction<String, Object, Query> QUERY = (field, value) -> Query.query(where(field).is(value));


}
