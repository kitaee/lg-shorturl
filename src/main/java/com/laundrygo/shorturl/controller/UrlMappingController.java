package com.laundrygo.shorturl.controller;


import com.laundrygo.shorturl.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/url")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
}
