package com.MusicManager.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@TestConfiguration
public class MongoTestConfig {
    
    @Bean
    public MongodConfig embeddedMongoConfiguration() throws Exception {
        return MongodConfig.builder()
                .version(Version.Main.V4_0)
                .net(new Net(27017, Network.localhostIsIPv6()))
                .build();
    }
} 