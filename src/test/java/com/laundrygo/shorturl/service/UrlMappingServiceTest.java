package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.dto.response.UrlMappingResponse;
import com.laundrygo.shorturl.exception.UrlNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class UrlMappingServiceTest {

    @Autowired
    private UrlMappingService urlMappingService;

    private final int MAX_URL_LENGTH = 8;
    private final String ORI_URL_CASE1 = "www.laundrygo.com";
    private final String ORI_URL_CASE2 = "https://www.google.com/search?sca_esv=b36f55d2b2433671&sxsrf=AHTn8zqy3IVIPnXkdxT92tdhrs-rI-WGLQ:1741162188656&q=%EB%8D%B0%EB%A6%AD%EB%A1%9C%EC%A6%88&udm=2&fbs=ABzOT_CZsxZeNKUEEOfuRMhc2yCI6hbTw9MNVwGCzBkHjFwaK53DgNHTMxn53_XGiUHS2MuRVDP60KUbqm2OQomhT296Q8j4L9BFGBcQ0mIumXcz3DgnMKIN4okONZJWPQBn6qu6-f5xKhuWNQuih9pjdwrNxkE8JHLZEUrgZby4Wb03L9vR-od6tS-BHqThHLL_jbs4_Fis&sa=X&ved=2ahUKEwie9YDOvvKLAxVgj68BHcWOKF0QtKgLegQIGxAB&biw=1920&bih=1078#vhid=uXQ3JohJIhIlCM&vssid=mosaic"; // 매우 긴 케이스
    private static final String NON_EXISTING_SHORT_URL = "nonExistUrl";

    @Test
    @DisplayName("oriUrl을 입력하면 8글자 이내의 shortUrl이 생성된다.")
    void shortenUrlWithin8Characters() {
        // given,when
        UrlMappingResponse urlMappingResponse1 = urlMappingService.shortenUrl(ORI_URL_CASE1);
        UrlMappingResponse urlMappingResponse2 = urlMappingService.shortenUrl(ORI_URL_CASE2);

        // then
        assertThat(urlMappingResponse1.getShortUrl()).hasSizeLessThanOrEqualTo(MAX_URL_LENGTH);
        assertThat(urlMappingResponse2.getShortUrl()).hasSizeLessThanOrEqualTo(MAX_URL_LENGTH);
    }

    @Test
    @DisplayName("같은 oriUrl에 대해 동일한 shortUrl이 반환된다.")
    void shortenSameShortUrlForSameOriUrl() {
        // given, when
        UrlMappingResponse urlMappingResponse1 = urlMappingService.shortenUrl(ORI_URL_CASE1);
        UrlMappingResponse urlMappingResponse2 = urlMappingService.shortenUrl(ORI_URL_CASE1);

        // then
        assertThat(urlMappingResponse1.getShortUrl()).isEqualTo(urlMappingResponse2.getShortUrl());
    }

    @Test
    @DisplayName("shortUrl을 입력하면 oriUrl이 반환된다.")
    void returnOriUrlForGivenShortUrl() {
        // given
        UrlMappingResponse urlMappingResponse = urlMappingService.shortenUrl(ORI_URL_CASE1);
        String shortUrl = urlMappingResponse.getShortUrl();

        // when
        UrlMappingResponse oriUrlResponse = urlMappingService.getOriUrl(shortUrl);

        // then
        assertThat(oriUrlResponse.getOriUrl()).isEqualTo(ORI_URL_CASE1);
    }

    @Test
    @DisplayName("shortUrl을 요청할 때마다 요청 수가 증가한다.")
    void increaseRequestCountWhenShortUrlIsRequested() {
        // given
        UrlMappingResponse urlMappingResponse = urlMappingService.shortenUrl(ORI_URL_CASE1);
        String shortUrl = urlMappingResponse.getShortUrl();

        // when
        int firstRequestCount = urlMappingService.getOriUrl(shortUrl).getRequestCount();
        int secondRequestCount = urlMappingService.getOriUrl(shortUrl).getRequestCount();

        // then
        assertThat(secondRequestCount).isEqualTo(firstRequestCount + 1);
    }

    @Test
    @DisplayName("존재하지 않는 shortUrl을 요청하면 404 오류를 반환한다.")
    void shouldThrowNotFoundForNonExistingShortUrl() {
        // given, when, then
        assertThatThrownBy(() -> urlMappingService.getOriUrl(NON_EXISTING_SHORT_URL))
                .isInstanceOf(UrlNotFoundException.class)
                .hasMessage("존재하지 않는 URL 입니다.");
    }

    @Test
    @DisplayName("등록된 모든 URL을 조회할 수 있다.")
    void returnAllUrls() {
        // given
        urlMappingService.shortenUrl(ORI_URL_CASE1);
        urlMappingService.shortenUrl(ORI_URL_CASE2);

        // when
        List<UrlMappingResponse> allUrlMappings = urlMappingService.getAllUrlMappings();

        // then
        assertThat(allUrlMappings).hasSize(2)
                .extracting(UrlMappingResponse::getOriUrl)
                .containsExactlyInAnyOrder(ORI_URL_CASE1, ORI_URL_CASE2);
    }
}