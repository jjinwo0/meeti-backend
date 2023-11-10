package yjhb.meeti.service.approval;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.domain.reservation.Reservation;
import yjhb.meeti.domain.reservation.Status;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.dto.approval.ApprovalDto;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.dto.user.UserInfoDto;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.repository.approval.ApprovalRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.service.calender.CalenderService;
import yjhb.meeti.service.file.S3Service;
import yjhb.meeti.service.reservation.ReservationService;
import yjhb.meeti.service.user.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final S3Service s3Service;
    private final UserService userService;
    private final ReservationService reservationService;
    private final CalenderService calenderService;


    public Approval findApprovalById(Long id){
        return approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXISTS));
    }

    @Transactional
    public Long regApproval(ApprovalDto.Request dto, User user, MultipartFile file) throws IOException {

        String uploadFile = s3Service.upload(file, "approvalFile");

        Approval approval = Approval.builder()
                .user(user)
                .adminUsername(dto.getAdminUsername())
                .requestDetail(dto.getRequestDetail())
                .decision(Decision.WAIT)
                .file(uploadFile)
                .build();

        approvalRepository.save(approval);

        return approval.getId();
    }

    @Transactional
    public Long regReservationApproval(ApprovalDto.ReservationRequest dto, User user, MultipartFile file) throws IOException {

        String uploadFile = s3Service.upload(file, "approvalFile");

        Approval approval = Approval.builder()
                .user(user)
                .adminUsername(dto.getAdminUsername())
                .requestDetail(dto.getRequestDetail())
                .decision(Decision.WAIT)
                .file(uploadFile)
                .placeName(dto.getPlaceName())
                .build();

        approvalRepository.save(approval);

        return approval.getId();
    }


    public void proceed(Long id, Decision decision){

        Approval findApproval = findApprovalById(id);

        findApproval.updateDecision(decision);
    }

    @Transactional
    public void update(Long id, ApprovalDto.ReservationRequest dto, MultipartFile file) throws IOException {

        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_APPROVAL));

        approval.update(dto.getAdminUsername(), dto.getRequestDetail(), s3Service.upload(file, "approvalFile"));
    }

    // 같은 Office 내 결재 리스트 조회e
    public List<ApprovalDto.Response> approvalListForOffice(Long userId){

        String email = userService.findUserByUserId(userId).getEmail().split("@")[1];

        List<ApprovalDto.Response> findList = approvalRepository.findAll().stream()
                .filter(approval -> approval.getUser().getEmail().split("@")[1].equals(email))
                .map(ApprovalDto.Response::from)
                .collect(Collectors.toList());

        return findList;
    }

    @Transactional
    public void approvalDecisionByAdmin(Long approvalId, ApprovalDto.Admin dto){

        // 결재 정보 확인
        Approval findApproval = findApprovalById(approvalId);

        Reservation findReservation =
                reservationService.findReservationByPlaceNameNameAndUserId(findApproval.getPlaceName().substring(6, findApproval.getPlaceName().length()), findApproval.getUser().getId());

        User regUser = userService.findUserByUserId(findApproval.getUser().getId());

        // Admin Decision
        if (dto.getDecision().equals(Decision.CONFIRM.toString())) {

            findApproval.adminUpdate(dto.getDecisionDetail(), Decision.CONFIRM);
            findReservation.updateStatus(Status.CONFIRM);
            calenderService.updateCalenderByApprovalDecision(findReservation, regUser);
        }
        if (dto.getDecision().equals(Decision.REJECT.toString())) {

            findApproval.adminUpdate(dto.getDecisionDetail(), Decision.REJECT);
            findReservation.updateStatus(Status.REJECT);
            calenderService.updateCalenderByApprovalDecision(findReservation, regUser);
        }

    }

    @Transactional
    public void deleteApproval(Long userId, Long approvalId){

        Approval findApproval = findApprovalById(approvalId);

        if (!findApproval.getUser().getId().equals(userId))
            throw new BusinessException(ErrorCode.NOT_VALID_USER);

        approvalRepository.delete(findApproval);
    }

    public List<UserInfoDto> findAdminByUserEmail(String email) {

        String domain = email.split("@")[1];

        return userService.findAll().stream()
                .filter(user -> user.getEmail().split("@")[1].equals(domain))
                .filter(user -> user.getRole().equals(Role.ADMIN_OFFICE))
                .map(UserInfoDto::from)
                .collect(Collectors.toList());
    }
}
