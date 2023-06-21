package lazzy.web.service;

import lazzy.web.dao.RoleDao;
import lazzy.web.dao.UserDao;
import lazzy.web.entity.RoleEntity;
import lazzy.web.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final UserDao userDao;
    private final RoleDao roleDao;

    public RoleServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<RoleEntity> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public void assignRole(Long userId, Long roleId) {
        UserEntity user = userDao.getUser(userId);
        RoleEntity role = roleDao.getRoleById(roleId);
        user.addRole(role);
        userDao.updateUser(user);
    }

    @Override
    public void removeRole(Long userId, Long roleId) {
        UserEntity user = userDao.getUser(userId);
        RoleEntity role = roleDao.getRoleById(roleId);
        user.removeRole(role);
        userDao.updateUser(user);
    }

    @Override
    public void createRole(String roleName) {
        RoleEntity role = new RoleEntity(roleName);
        roleDao.createRole(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleDao.delete(roleId);
    }
}
