package yjhb.meeti.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.kakaopay.KakaoPay;

public interface KakaoPayRepository extends JpaRepository<KakaoPay, Long> {

}
