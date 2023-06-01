package yjhb.meeti.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender sender;
    private String authNum;
    @Value("${spring.username}")
    private String fromEmail;

    // 무작위 8자리 인증번호 생성
    public void createCode(){
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i=0; i<8; i++){
            int idx = random.nextInt(3);

            switch (idx){
                case 0:
                    key.append((char)(random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char)(random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }

        authNum = key.toString();
    }

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException {

        createCode();

        MimeMessage message = sender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject("meeti 인증번호");
        message.setFrom(fromEmail);
        message.setText(authNum, "utf-8", "html");

        return message;
    }

    // 실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException {

        // 전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);

        sender.send(emailForm);

        return authNum; //인증 코드 리턴
    }
}
