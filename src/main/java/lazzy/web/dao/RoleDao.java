package lazzy.web.dao;

import lazzy.web.entity.RoleEntity;

import java.util.List;

public interface RoleDao {

    List<RoleEntity> getAllRoles();

    void createRole(RoleEntity role);

    RoleEntity getRoleById(long roleId);

    void delete(Long roleId);
}

