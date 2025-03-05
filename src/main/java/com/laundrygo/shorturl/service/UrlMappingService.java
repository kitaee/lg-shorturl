package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.dto.response.UrlMappingResponse;
import com.laundrygo.shorturl.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    @Transactional
    public UrlMappingResponse shortenUrl(String oriUrl) {
        return null;
    }
}
