package yjhb.meeti.domain.external.oauth.google.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yjhb.meeti.domain.user.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    private final UserRepository userRepository;

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.google.redirect-url}")
    private String REDIRECT_URL;

    public ResponseEntity<String> getGoogleAccessToken(String accessCode){

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_url", REDIRECT_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return responseEntity;

        return null;
    }
}
