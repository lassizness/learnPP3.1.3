package lazzy.web.service;

import lazzy.web.dao.UserDao;
import lazzy.web.entity.RoleEntity;
import lazzy.web.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {// UserDetailsService определяет метод loadUserByUsername

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(lazzy.web.dao.UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserEntity findUser(String username) {
        return userDao.findUser(username);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        userDao.updateUser(user);
        return user;
    }

    @Override
    public UserEntity getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userDao.getUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userModel = userDao.findUser(username);
        if (userModel == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RoleEntity role : userModel.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(
                userModel.getUsername(),
                userModel.getPassword(),
                authorities
        );
    }

}
