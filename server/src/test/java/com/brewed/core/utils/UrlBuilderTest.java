package com.brewed.core.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UrlBuilderTest {

    @Test
    void shouldBuildValidUrl() {
        String requestPath="test";
        String serverPort="4000";
        String serverAddress="address";
        String url = UrlBuilder.buildUrl(serverAddress, serverPort, requestPath);
        assertThat(url).isEqualTo("http://address:4000/test");
    }
}