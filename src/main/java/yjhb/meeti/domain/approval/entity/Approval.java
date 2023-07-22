package yjhb.meeti.domain.approval.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Approval {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String requestDetail;
    private File file;
    private String decisionDetail;
    private String decision;

    @Builder
    public Approval(String username, String requestDetail, File file, String decisionDetail, String decision) {
        this.username = username;
        this.requestDetail = requestDetail;
        this.file = file;
        this.decisionDetail = decisionDetail;
        this.decision = decision;
    }
}
