package com.laundrygo.shorturl.service;

import com.laundrygo.shorturl.dto.response.UrlMappingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class UrlMappingServiceTest {

    @Autowired
    private UrlMappingService urlMappingService;

    private final int MAX_URL_LENGTH = 8;
    private final String ORI_URL_CASE1 = "www.laundrygo.com";
    private final String ORI_URL_CASE2 = "https://www.google.com/search?sca_esv=b36f55d2b2433671&sxsrf=AHTn8zqy3IVIPnXkdxT92tdhrs-rI-WGLQ:1741162188656&q=%EB%8D%B0%EB%A6%AD%EB%A1%9C%EC%A6%88&udm=2&fbs=ABzOT_CZsxZeNKUEEOfuRMhc2yCI6hbTw9MNVwGCzBkHjFwaK53DgNHTMxn53_XGiUHS2MuRVDP60KUbqm2OQomhT296Q8j4L9BFGBcQ0mIumXcz3DgnMKIN4okONZJWPQBn6qu6-f5xKhuWNQuih9pjdwrNxkE8JHLZEUrgZby4Wb03L9vR-od6tS-BHqThHLL_jbs4_Fis&sa=X&ved=2ahUKEwie9YDOvvKLAxVgj68BHcWOKF0QtKgLegQIGxAB&biw=1920&bih=1078#vhid=uXQ3JohJIhIlCM&vssid=mosaic"; // 매우 긴 케이스

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

}