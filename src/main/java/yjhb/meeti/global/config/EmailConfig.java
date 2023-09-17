package yjhb.meeti.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.properties.mail.smtp.socketFactory.port}")
    private int socketPort;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private boolean startlls_required;
    @Value("${spring.mail.properties.mail.smtp.socketFactory.fallback}")
    private boolean fallback;
    @Value("${spring.mail.username}")
    private String id;
    @Value("${spring.mail.password}")
    private String password;
}
