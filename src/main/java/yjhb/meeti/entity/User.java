package yjhb.meeti.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.dto.UserDTO;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty @Email
    private String email;
    @OneToMany(mappedBy = "user")
    List<Calender> calenders;
    @OneToMany(mappedBy = "user")
    List<Reservation> reservations;

    @Builder
    public User(UserDTO dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
    }

    public User update(UserDTO dto){
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();

        return this;
    }
}
