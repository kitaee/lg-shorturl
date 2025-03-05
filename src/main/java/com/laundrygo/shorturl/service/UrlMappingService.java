package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.domain.UrlMapping;
import com.laundrygo.shorturl.dto.response.UrlMappingResponse;
import com.laundrygo.shorturl.repository.UrlMappingRepository;
import com.laundrygo.shorturl.utils.Base62Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlMappingService {

    private final Base62Util base62Util;
    private final UrlMappingRepository urlMappingRepository;

    @Transactional
    public UrlMappingResponse shortenUrl(String oriUrl) {
        return urlMappingRepository.findByOriUrl(oriUrl)
                .map(UrlMappingResponse::of)
                .orElseGet(() -> UrlMappingResponse.of(createShortUrl(oriUrl)));
    }

    private UrlMapping createShortUrl(String oriUrl) {
        return urlMappingRepository.save(UrlMapping.createShortUrl(oriUrl, base62Util.generateShortUrlFromUuid()));
    }
}