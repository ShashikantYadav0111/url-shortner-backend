package com.project.url_shortner.service;

import com.project.url_shortner.dto.ResponseDto;
import com.project.url_shortner.dto.UrlResponseDto;
import com.project.url_shortner.model.UrlData;
import com.project.url_shortner.repository.UrlDataRepository;
import com.project.url_shortner.utils.Base62Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UrlDataService {
    private final UrlDataRepository urlDataRepository;

    public UrlDataService(UrlDataRepository urlDataRepository){
        this.urlDataRepository = urlDataRepository;
    }

    public ResponseDto shortenUrlMethod(String longUrl) {
        UrlData urlData;
        do {
            UUID id = UUID.randomUUID();
            long idValue = Math.abs(id.getMostSignificantBits());
            String shortCode = Base62Encoder.encode(idValue);

            if (urlDataRepository.findByShortUrl(shortCode).isEmpty()) {
                urlData = UrlData.builder()
                        .uuid(id)
                        .longUrl(longUrl)
                        .createdAt(LocalDateTime.now())
                        .expiration(LocalDateTime.now().plusDays(7))
                        .shortUrl(shortCode)
                        .isActive(true)
                        .build();
                UrlData urlData1 = urlDataRepository.save(urlData);
                return ResponseDto.builder()
                        .isActive(urlData1.isActive())
                        .expiration(urlData1.getExpiration())
                        .shortUrl(urlData1.getShortUrl())
                        .build();
            }

            log.info("Collision detected for short code: {}. Retrying...", shortCode);

        } while (true);
    }

    public UrlResponseDto getLongUrl(String shortCode) {
        Optional<UrlData> urlDataOptional = urlDataRepository.findByShortUrl(shortCode);
        UrlResponseDto dto;
        if(urlDataOptional.isPresent()){
            dto = UrlResponseDto.builder()
                    .isActive(urlDataOptional.get().isActive())
                    .longUrl(urlDataOptional.get().getLongUrl())
                    .build();
        }else{
            dto = UrlResponseDto.builder()
                    .isActive(false)
                    .longUrl("xxxxxxxxxxxxxxxxxxxxxxxx")
                    .build();
        }
        log.info("Here in service layer");
        return dto;
    }
}
