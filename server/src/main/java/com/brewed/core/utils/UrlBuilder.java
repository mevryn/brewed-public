package com.brewed.core.utils;

import com.brewed.core.config.ConfigConstants;

public class UrlBuilder {
    private UrlBuilder() {
    }


    public static String buildUrl(String serverAddress, String serverPort, String requestPath) {
        return ConfigConstants.currentRequestProtocol +
                "://" +
                serverAddress +
                ":" +
                serverPort +
                "/" +
                requestPath;
    }
}
