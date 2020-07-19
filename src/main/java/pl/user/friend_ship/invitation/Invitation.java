package pl.user.friend_ship.invitation;

import lombok.Builder;
import lombok.Data;
import pl.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "invitation")
public class Invitation {

    @Id
    @Column(name = "invitation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User inviter;

    @OneToOne
    private User invited;

    @Column(name = "\"key\"")
    private String key;

    @Builder
    public Invitation(User inviter, User invited, @NotNull String key) {
        this.inviter = inviter;
        this.invited = invited;
        this.key = key;
    }
}