package fr.laerce.thymesecurity.security.dao;

import fr.laerce.thymesecurity.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  21/03/2017.
 *
 * @author fred
 */
@Repository
public interface UserDao extends JpaRepository<User, Long>{
    User findByName(String name);
    User findByMail (String mail);
    User findById(Long id);
    List<User> findAllByOrderById();
}
