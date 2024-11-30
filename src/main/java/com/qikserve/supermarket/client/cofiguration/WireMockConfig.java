package com.qikserve.supermarket.client.cofiguration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WireMockConfig {

    //@Bean(initMethod = "start", destroyMethod = "stop")
//    public WireMockServer wireMockServer() {
//        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));
//
//        wireMockServer.stubFor(WireMock.get("/mocked-endpoint")
//                .willReturn(WireMock.aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{\"message\": \"Mocked response!\"}")));
//        return wireMockServer;
//    }
}
