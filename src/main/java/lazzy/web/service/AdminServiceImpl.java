package lazzy.web.service;

import lazzy.web.dao.UserDao;
import lazzy.web.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao;

    @Autowired
    public AdminServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserEntity addUser(UserEntity user) {
        userDao.saveUser(user);
        return user;
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        userDao.updateUser(user);
        return user;
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    public UserEntity getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userDao.getUsers();
    }

}
