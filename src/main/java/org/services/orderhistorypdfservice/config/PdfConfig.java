package org.services.orderhistorypdfservice.config;

import org.services.orderhistorypdfservice.util.PdfGeneratorUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PdfConfig {

    @Bean
    public PdfGeneratorUtil pdfGeneratorUtil() {
        return new PdfGeneratorUtil();
    }
}
