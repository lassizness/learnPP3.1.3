package lazzy.web.service;


import lazzy.web.entity.RoleEntity;

import java.util.List;

public interface RoleService {
    List<RoleEntity> getAllRoles();

    void assignRole(Long userId, Long roleId);

    void removeRole(Long userId, Long roleId);

    void createRole(String roleName);

    void deleteRole(Long roleId);
}
