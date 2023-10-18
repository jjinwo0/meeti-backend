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
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String requestDetail;
    private String proceeding;
    private String decisionDetail;
    private Decision decision;
    private String file;

    @Builder
    public Approval(User user, String requestDetail, String proceeding, String decisionDetail, Decision decision, String file) {
        this.user = user;
        this.requestDetail = requestDetail;
        this.proceeding = proceeding;
        this.decisionDetail = decisionDetail;
        this.decision = decision;
        this.file = file;
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
    public void update(String requestDetail, String proceeding, String file) {
        this.requestDetail = requestDetail;
        this.proceeding = proceeding;
        this.file = file;
    }
}
