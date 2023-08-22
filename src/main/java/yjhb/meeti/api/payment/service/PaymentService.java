package yjhb.meeti.api.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final UserService userService;


}
