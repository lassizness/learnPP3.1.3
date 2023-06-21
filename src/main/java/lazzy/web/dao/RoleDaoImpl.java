package lazzy.web.dao;

import lazzy.web.entity.RoleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoleEntity> getAllRoles() {
        TypedQuery<RoleEntity> query = entityManager.createQuery("SELECT r FROM RoleEntity r", RoleEntity.class);
        return query.getResultList();
    }
    @Override
    public RoleEntity getRoleById(long roleId) {
        return entityManager.find(RoleEntity.class, roleId);
    }

    @Override
    @Transactional
    public void createRole(RoleEntity role) {
        entityManager.persist(role);
    }
    @Override
    @Transactional
    public void delete(Long roleId) {
        entityManager.remove(roleId);
    }
}