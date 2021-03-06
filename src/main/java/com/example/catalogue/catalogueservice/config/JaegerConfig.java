package com.example.catalogue.catalogueservice.config;

import io.jaegertracing.internal.samplers.ProbabilisticSampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaegerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public io.opentracing.Tracer jaegerTracer() {

        io.jaegertracing.Configuration.SamplerConfiguration samplerConfiguration =
                io.jaegertracing.Configuration.SamplerConfiguration.fromEnv().withType(
                        ProbabilisticSampler.TYPE).withParam(1);
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfiguration =
                io.jaegertracing.Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        io.jaegertracing.Configuration
                configuration = new io.jaegertracing.Configuration(appName).withSampler(samplerConfiguration)
                                                                           .withReporter(reporterConfiguration);

        return configuration.getTracer();
    }

}
