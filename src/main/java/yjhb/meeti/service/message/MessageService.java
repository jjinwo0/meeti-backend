package yjhb.meeti.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.message.Message;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.dto.message.MessageDto;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.repository.message.MessageRepository;
import yjhb.meeti.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    public Message findMessageById(Long messageId){

        return messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MESSAGE));
    }

    @Transactional
    public Long write(Long senderId, Long receiverId, MessageDto messageDto){

        User sender = userService.findUserByUserId(senderId);
        User receiver = userService.findUserByUserId(receiverId);

        Message message = Message.builder()
                .title(messageDto.getTitle())
                .content(messageDto.getContent())
                .sender(sender)
                .receiver(receiver)
                .build();

        messageRepository.save(message);

        return message.getId();
    }

    /**
     * 받은 편지함 조회
     */
    public List<MessageDto> receiveMessageList(Long receiverId){

        User receiver = userService.findUserByUserId(receiverId);

        return messageRepository.findByReceiver(receiver).stream()
                .filter(message -> !message.isDeletedByReceiver())
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 받은 편지 삭제
     */
    @Transactional
    public void deleteMessageByReceiver(Long messageId, Long receiverId){

        User receiver = userService.findUserByUserId(receiverId);

        Message findMessage = findMessageById(messageId);

        if (receiver.equals(findMessage.getReceiver())) {
            findMessage.deleteByReceiver();

            if (findMessage.isDeleted()) // 둘 다 삭제했다면,
                messageRepository.delete(findMessage); // 양쪽 모두 삭제
        }
        else return throw new IllegalStateException(ErrorCode.USER_NOT_EXISTS.getMessage());
    }

    /**
     * 보낸 편지함 조회
     */
    public List<MessageDto> sentMessage(Long senderId){

        User sender = userService.findUserByUserId(senderId);

        return messageRepository.findBySender(sender).stream()
                .filter(message -> !message.isDeletedBySender())
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 보낸 편지 삭제
     */
    public void deleteMessageBySender(Long messageId, Long senderId){

        User sender = userService.findUserByUserId(senderId);

        Message findMessage = findMessageById(messageId);

        if (sender.equals(findMessage.getSender())){
            findMessage.deleteBySender();

            if (findMessage.isDeleted())
                messageRepository.delete(findMessage);
        } else throw new IllegalStateException(ErrorCode.USER_NOT_EXISTS.getMessage());
    }
}
