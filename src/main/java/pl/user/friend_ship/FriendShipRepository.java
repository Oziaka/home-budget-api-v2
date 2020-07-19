package pl.user.friend_ship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.user.User;

import java.util.Optional;

@Repository
public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {

    Optional<FriendShip> findAllByUserAndUser2(User user, User user2);
}
