package VJames.Development.NingnenCo_BE.Domain.Repositories;

import VJames.Development.NingnenCo_BE.Domain.Entities.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
     Optional<User> findByUsername(String username);
     Optional<User> findById(String userId);
     void updatePassword(String newPassword, String userId);
     User save(User user);
     List<User> findAllIfNotFollowingByCurrenUserId(String userId);
//     User save(User user);


}
