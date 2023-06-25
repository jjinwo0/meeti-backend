package yjhb.meeti.calender.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.calender.dto.CalenderDTO;
import yjhb.meeti.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@Getter
public class Calender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_id")
    private Long id;

    @NotEmpty
    private String color;
    @NotEmpty
    private String start;
    @NotEmpty
    private String end;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Calender(CalenderDTO dto, User user){
        this.color = dto.getColor();
        this.start = dto.getStart();
        this.end = dto.getEnd();
        this.user = user;
    }

    public Calender update(CalenderDTO dto, User user){
        this.color = dto.getColor();
        this.start = dto.getStart();
        this.end = dto.getEnd();
        this.user = user;

        return this;
    }
}
