package fr.laerce.thymesecurity.security.dao;

import fr.laerce.thymesecurity.security.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  21/03/2017.
 *
 * @author fred
 */
@Repository
public interface GroupDao extends JpaRepository<Group, Long>{
    Group findByRole (String group);
}
