package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.domain.UrlMapping;
import com.laundrygo.shorturl.dto.response.UrlMappingResponse;
import com.laundrygo.shorturl.exception.UrlNotFoundException;
import com.laundrygo.shorturl.repository.UrlMappingRepository;
import com.laundrygo.shorturl.utils.Base62Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public UrlMappingResponse getOriUrl(String shortUrl) {
        return urlMappingRepository.findByShortUrl(shortUrl)
                .map(urlMapping -> {
                    urlMapping.increaseRequestCount();
                    return UrlMappingResponse.of(urlMapping);
                })
                .orElseThrow(() -> new UrlNotFoundException("존재하지 않는 URL 입니다."));
    }

    public List<UrlMappingResponse> getAllUrlMappings() {
        List<UrlMapping> urlMappings = urlMappingRepository.findAll();
        return urlMappings.stream()
                .map(UrlMappingResponse::of)
                .collect(Collectors.toList());
    }

    private UrlMapping createShortUrl(String oriUrl) {
        return urlMappingRepository.save(UrlMapping.createShortUrl(oriUrl, base62Util.generateShortUrlFromUuid()));
    }
}