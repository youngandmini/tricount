package goorm.tricount.repository.user;

import goorm.tricount.domain.User;

public interface UserRepository {

    Long save(User user);
}
