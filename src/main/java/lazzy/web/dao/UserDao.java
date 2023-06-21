package lazzy.web.dao;

import lazzy.web.entity.RoleEntity;
import lazzy.web.entity.UserEntity;
import lazzy.web.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    List<UserEntity> getUsers();

    UserEntity getUser(long id);

    UserEntity findUser(String username);

    Set<RoleEntity> getUserRoles(String username);

    void saveUser(UserEntity user);

    void updateUser(UserEntity user);

    void deleteUser(long id);
}
