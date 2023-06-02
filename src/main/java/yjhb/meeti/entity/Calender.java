package yjhb.meeti.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.dto.CalenderDTO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@Getter
public class Calender {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String color;
    @NotEmpty
    private String start;
    @NotEmpty
    private String end;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private User user;

    @Builder
    public Calender(CalenderDTO dto){
        this.color = dto.getColor();
        this.start = dto.getStart();
        this.end = dto.getEnd();
    }
}
