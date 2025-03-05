package com.laundrygo.shorturl.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@Table(name = "url_mappings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlMapping {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 2083) // 일반적인 브라우저 URL 최대 길이
    private String oriUrl;

    @Column(nullable = false, unique = true, length = 8)
    private String shortUrl;

    private int requestCount = 0;

    @Builder
    private UrlMapping(String oriUrl, String shortUrl) {
        this.oriUrl = oriUrl;
        this.shortUrl = shortUrl;
    }

    public static UrlMapping createShortUrl(String oriUrl, String shortUrl) {
        return UrlMapping.builder()
                .oriUrl(oriUrl)
                .shortUrl(shortUrl)
                .build();
    }

    public void increaseRequestCount() {
        this.requestCount+=1;
    }
}
