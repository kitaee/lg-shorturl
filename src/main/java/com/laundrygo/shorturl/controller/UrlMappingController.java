package com.laundrygo.shorturl.controller;


import com.laundrygo.shorturl.dto.request.UrlMappingRequest;
import com.laundrygo.shorturl.dto.response.UrlMappingResponse;
import com.laundrygo.shorturl.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/url")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingResponse> shortenUrl(@RequestBody UrlMappingRequest urlMappingRequest) {
        return new ResponseEntity<>(urlMappingService.shortenUrl(urlMappingRequest.getOriUrl()), HttpStatus.OK);
    }
}
