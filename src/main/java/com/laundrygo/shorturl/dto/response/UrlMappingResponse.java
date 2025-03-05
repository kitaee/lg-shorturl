package com.laundrygo.shorturl.dto.response;

import com.laundrygo.shorturl.domain.UrlMapping;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UrlMappingResponse {
    private Long id;
    private String oriUrl;
    private String shortUrl;
    private int requestCount;

    @Builder
    private UrlMappingResponse(Long id, String oriUrl, String shortUrl, int requestCount) {
        this.id = id;
        this.oriUrl = oriUrl;
        this.shortUrl = shortUrl;
        this.requestCount = requestCount;
    }

    public static UrlMappingResponse of(UrlMapping urlMapping) {
        return UrlMappingResponse.builder()
                .id(urlMapping.getId())
                .oriUrl(urlMapping.getOriUrl())
                .shortUrl(urlMapping.getShortUrl())
                .requestCount(urlMapping.getRequestCount())
                .build();
    }
}
