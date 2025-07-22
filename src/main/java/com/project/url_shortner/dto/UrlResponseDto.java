package com.project.url_shortner.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlResponseDto {
    private boolean isActive;
    private String longUrl;

}
