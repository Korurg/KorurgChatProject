/*
 * Copyright © 2023 KorurgChat author or authors. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package korurg.korurgchat.exception;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex, NativeWebRequest request) {

        String path = Optional.ofNullable(request)
                .map(r -> r.getNativeRequest(HttpServletRequest.class))
                .map(HttpServletRequest::getRequestURI)
                .orElse("");

        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
                .filter(e -> e.getClassName().startsWith("korurg"))
                .map(StackTraceElement::toString)
                .toList();


        ErrorMessage errorMessage = ErrorMessage.builder()
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(path)
                .stacktrace(stacktrace)
                .build();


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name());
        return new ResponseEntity<>(errorMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorMessage {
        private List<String> stacktrace;
        private String error;
        private String message;
        private String path;
        private Integer status;
    }
}