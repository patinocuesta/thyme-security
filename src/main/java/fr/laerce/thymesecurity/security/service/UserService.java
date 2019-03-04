package fr.laerce.thymesecurity.security.service;

import fr.laerce.thymesecurity.security.dao.GroupDao;
import fr.laerce.thymesecurity.security.dao.UserDao;
import fr.laerce.thymesecurity.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
@Service
public class UserService {
    private UserDao userDao;
    private GroupDao groupDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Autowired
    public void setGroupDao(GroupDao groupDao){
        this.groupDao = groupDao;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAll () {
        return userDao.findAllByOrderById();
    }

    public User getById (Long id) {
        return userDao.findById(id);
    }

    public void save(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setGroups(new HashSet<>(groupDao.findAll()));
        userDao.save(user);
    }

    public User findByUserName(String userName){
        return userDao.findByName(userName);
    }
    public User findById (Long id) {return userDao.findById(id);}
}
