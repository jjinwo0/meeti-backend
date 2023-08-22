package yjhb.meeti.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.payment.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByPaymentKeyAndUserEmail(String paymentKey, String email);
}
