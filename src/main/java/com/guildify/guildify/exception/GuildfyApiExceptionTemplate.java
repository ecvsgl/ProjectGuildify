package com.guildify.guildify.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuildfyApiExceptionTemplate {
        private int statusCode;
        private String exceptionMessage;
        private LocalDateTime exceptionDate;

}
