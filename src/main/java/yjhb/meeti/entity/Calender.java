package yjhb.meeti.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.dto.CalenderDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @Builder
    public Calender(CalenderDTO dto){
        this.color = dto.getColor();
        this.start = dto.getStart();
        this.end = dto.getEnd();
    }
}
