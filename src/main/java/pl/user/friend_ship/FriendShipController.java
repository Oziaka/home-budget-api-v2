package pl.user.friend_ship;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.exception.CanNotAddYourselfToFriendException;
import pl.exception.CanNotInviteAlreadyFriend;
import pl.exception.CanOnlySndOneInviteException;
import pl.exception.YourAreNotInvitedException;
import pl.user.User;
import pl.user.UserService;
import pl.user.friend_ship.friend.FriendDto;
import pl.user.friend_ship.friend.FriendMapper;
import pl.user.friend_ship.invitation.Invitation;
import pl.user.friend_ship.invitation.InvitationDto;
import pl.user.friend_ship.invitation.InvitationMapper;
import pl.user.friend_ship.invitation.InvitationService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class FriendShipController {

    private InvitationService invitationService;
    private UserService userService;
    private FriendShipService friendShipService;


    FriendShipDto add(Principal principal, Long invitationId) {
        User user = userService.getUser(principal);
        Invitation invitation = invitationService.getByInvited(user, invitationId);
        invitationService.remove(invitation);
        FriendShip friendShip = FriendShip.builder().user(invitation.getInvited()).user2(invitation.getInviter()).dateOfAdding(LocalDateTime.now()).build();
        FriendShip friendShip2 = FriendShip.builder().user(invitation.getInviter()).user2(invitation.getInvited()).dateOfAdding(LocalDateTime.now()).build();
        FriendShip savedFriendShip = friendShipService.save(friendShip);
        friendShipService.save(friendShip2);
        return FriendShipMapper.toDto(savedFriendShip);
    }

    List<FriendDto> getFriends(Principal principal) {
        User user = userService.getUser(principal);
        List<FriendShip> friendShips = friendShipService.getFriendShips(user);
        return friendShips.stream().map(FriendMapper::toDto).collect(Collectors.toList());
    }

    void remove(Principal principal, Long friendShipId) {
        User user = userService.getUser(principal);
        FriendShip friendShip = friendShipService.getFriendShip(user, friendShipId);
        FriendShip friendShip2 = friendShipService.getFriendShip(friendShip.getUser2(), user);
        friendShipService.remove(friendShip);
        friendShipService.remove(friendShip2);
//        TODO notification to second user
    }


}
