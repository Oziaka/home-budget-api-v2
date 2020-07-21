package pl.user.friend_ship.invitation;

import lombok.Builder;
import lombok.Getter;
import pl.user.User;

@Getter
public class InvitationDto {

    @Builder
    public InvitationDto(Long id, User inviter, User invited){
        this.id = id;
        this.inviter = inviter;
        this.invited = invited;
    }

    private Long id;

    private User inviter;

    private User invited;
}