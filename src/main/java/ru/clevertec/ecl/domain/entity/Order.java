package ru.clevertec.ecl.domain.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.clevertec.ecl.domain.listener.OrderEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "orders")
@EntityListeners(OrderEntityListener.class)
public class Order implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant purchaseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_certificate_id", nullable = false)
    private GiftCertificate giftCertificate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
