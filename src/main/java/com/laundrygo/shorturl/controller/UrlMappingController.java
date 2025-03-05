package com.laundrygo.shorturl.controller;


import com.laundrygo.shorturl.dto.request.UrlMappingRequest;
import com.laundrygo.shorturl.dto.response.UrlMappingResponse;
import com.laundrygo.shorturl.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/url")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingResponse> shortenUrl(@RequestBody UrlMappingRequest urlMappingRequest) {
        return new ResponseEntity<>(urlMappingService.shortenUrl(urlMappingRequest.getOriUrl()), HttpStatus.OK);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<UrlMappingResponse> getOriUrl(@PathVariable("shortUrl") String shortUrl) {
        return new ResponseEntity<>(urlMappingService.getOriUrl(shortUrl), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UrlMappingResponse>> getAllUrls() {
        return new ResponseEntity<>(urlMappingService.getAllUrlMappings(), HttpStatus.OK);
    }
}
