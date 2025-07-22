package com.project.url_shortner.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {
    private String shortUrl;
    private LocalDateTime expiration;
    private boolean isActive;
}
