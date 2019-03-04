package fr.laerce.thymesecurity.security.service;

import fr.laerce.thymesecurity.security.dao.UserDao;
import fr.laerce.thymesecurity.security.domain.Group;
import fr.laerce.thymesecurity.security.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  21/03/2017.
 *
 * @author fred
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    private UserDao userDao;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setUserDao (UserDao userDao){
        this.userDao=userDao;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDao.findByName(name);
        log.info("Recherche utilisateur: "+ name);
        if(user == null){
            throw new UsernameNotFoundException("Utilisateur introuvable : |"+name+"|");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Group grp: user.getGroups()){
            log.info("{username: "+name+"| grp: "+grp.getRole());
            authorities.add(new SimpleGrantedAuthority(grp.getRole()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                authorities);
    }


}
