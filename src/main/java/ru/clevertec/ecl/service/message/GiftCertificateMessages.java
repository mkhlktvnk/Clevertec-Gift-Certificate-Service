package ru.clevertec.ecl.service.message;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:messsage/message.yaml")
public class GiftCertificateMessages {
    @Value("${gift-certificate.not-found}")
    private String notFound;
}
