package lazzy.web.service;

import lazzy.web.entity.UserEntity;

import java.util.List;

public interface AdminService {

    UserEntity addUser(UserEntity user);

    UserEntity updateUser(UserEntity user);

    void deleteUser(long id);

    UserEntity getUser(long id);

    List<UserEntity> getAllUsers();

}
