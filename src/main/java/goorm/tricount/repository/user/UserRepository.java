package goorm.tricount.repository.user;

import goorm.tricount.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

public interface UserRepository {

    Long joinUser(User user);
}
