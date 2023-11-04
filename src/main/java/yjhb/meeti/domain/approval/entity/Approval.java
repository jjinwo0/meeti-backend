package yjhb.meeti.domain.approval.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;
import java.io.File;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Approval {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String adminUsername;
    private String requestDetail;
    private String decisionDetail;
    @Enumerated(value = EnumType.STRING)
    private Decision decision;
    private String file;
    private String placeName;

    @Builder
    public Approval(User user, String adminUsername, String requestDetail, String decisionDetail, Decision decision, String file, String placeName) {
        this.user = user;
        this.adminUsername = adminUsername;
        this.requestDetail = requestDetail;
        this.decisionDetail = decisionDetail;
        this.decision = decision;
        this.file = file;
        this.placeName = placeName;
    }

    public void updateDecision(Decision decision){
        this.decision = decision;
    }

    // update by Admin
    public void adminUpdate(String decisionDetail, Decision decision) {
        this.decisionDetail = decisionDetail;
        this.decision = decision;
    }

    // update by User
    public void update(String adminUsername, String requestDetail, String file) {
        this.adminUsername = adminUsername;
        this.requestDetail = requestDetail;
        this.file = file;
    }
}
