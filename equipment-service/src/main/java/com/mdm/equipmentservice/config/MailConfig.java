/*
package com.mdm.equipmentservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@Configuration
public class MailConfig {
    private final Environment env;
    private static final String prefix = "spring.mail.";

    @Autowired
    public MailConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty(prefix + "host"));
        mailSender.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty(prefix + "port"))));
        mailSender.setUsername(env.getProperty(prefix + "username"));
        mailSender.setPassword(env.getProperty(prefix + "password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
*/
