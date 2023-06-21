package lazzy.web.dao;

import lazzy.web.entity.RoleEntity;
import lazzy.web.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> getUsers() {
        List<UserEntity> users = entityManager.createQuery("from UserEntity", UserEntity.class).getResultList();
        return users;
    }

    @Override
    public UserEntity getUser(long id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        return user;
    }

    @Override
    public UserEntity findUser(String username) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.username = :username",
                UserEntity.class);
        query.setParameter("username", username);
        List<UserEntity> results = query.getResultList();
        UserEntity user = null;
        if (!results.isEmpty()) {
            user = results.get(0);
            user.setUserRoles(getUserRoles(username));
        }
        return user;
    }

    public Set<RoleEntity> getUserRoles(String username) {
        TypedQuery<RoleEntity> query = entityManager.createQuery(
                "SELECT ur.role FROM UserRoleEntity ur JOIN ur.user u WHERE u.username = :username",
                RoleEntity.class);
        query.setParameter("username", username);
        List<RoleEntity> resultList = query.getResultList();
        Set<RoleEntity> resultSet = new HashSet<>(resultList);//Конвертировали в set
        return resultSet;
    }

    @Override
    public void saveUser(UserEntity user) {
        UserEntity userEntity = new UserEntity(user.getName(), user.getAge(), user.getUsername(), user.getPassword());
        entityManager.persist(userEntity);
    }

    @Override
    public void updateUser(UserEntity user) {
        UserEntity userEntity = entityManager.find(UserEntity.class, user.getId());
        if (userEntity != null) {
            userEntity.setName(user.getName());
            userEntity.setAge(user.getAge());
            userEntity.setUsername(user.getUsername());
            userEntity.setPassword(user.getPassword());
            entityManager.merge(userEntity);
        }
    }

    @Override
    public void deleteUser(long id) {
        UserEntity userEntity = entityManager.find(UserEntity.class, id);
        if (userEntity != null) {
            entityManager.remove(userEntity);
        }
    }
}
