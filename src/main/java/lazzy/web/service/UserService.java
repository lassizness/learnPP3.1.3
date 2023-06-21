package lazzy.web.service;


import lazzy.web.entity.UserEntity;

import java.util.List;

public interface UserService{// UserDetailsService определяет метод loadUserByUsername

    UserEntity findUser(String username);
    UserEntity updateUser(UserEntity user) ;
    UserEntity getUser(long id);
    List<UserEntity> getAllUsers();

}
