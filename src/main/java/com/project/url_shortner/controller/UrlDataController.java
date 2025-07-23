package com.project.url_shortner.controller;

import com.project.url_shortner.dto.ResponseDto;
import com.project.url_shortner.dto.UrlResponseDto;
import com.project.url_shortner.dto.UrlRequestDto;
import com.project.url_shortner.service.UrlDataService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins={"https://url-shortner-theta-weld.vercel.app/","https://url-shortner-react-lake.vercel.app/"})
@RestController
@RequestMapping("short")
@Slf4j
public class UrlDataController {

    @Value("${app.base.url}")
    private String baseUrl;
    private final UrlDataService urlDataService;

    public UrlDataController(UrlDataService urlDataService){
        this.urlDataService = urlDataService;
    }

    @PostMapping("shorten")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto shortenUrl(@RequestBody UrlRequestDto requestDto){
        log.info("Shorten Url Method was triggered");
        ResponseDto responseDto = urlDataService.shortenUrlMethod(requestDto.getLongUrl());
        responseDto.setShortUrl(baseUrl+responseDto.getShortUrl());
        return responseDto;
    }


    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode,HttpServletResponse httpResponse) throws IOException {
        final UrlResponseDto response = urlDataService.getLongUrl(shortCode);
        try{
            if(response.isActive()){
                System.out.println(response.getLongUrl());
                String longUrl = response.getLongUrl();
                httpResponse.sendRedirect(longUrl);
        }else{
            httpResponse.sendError(HttpStatus.GONE.value(),"Link Expired or Doesn't Exist!!!");
        }
        }catch(IllegalArgumentException e){
            httpResponse.sendError(HttpStatus.BAD_REQUEST.value(),"Something went wrong");
            log.warn(e.getMessage());
        }
    }
}
