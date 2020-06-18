package pl.com.muca.server.service;

import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import org.springframework.stereotype.Component;
import pl.com.muca.server.dao.UserDao;
import pl.com.muca.server.entity.User;

@Component
public class UserServiceImpl implements UserService {

  @Resource
  UserDao userDao;

  @Override
  public List<User> findAll() {
    return userDao.findAll();
  }

  @Override
  public void insertUser(User user) {
    userDao.insertUser(user);
  }

  @Override
  public void updateUser(User user) {
    userDao.updateUser(user);
  }

  @Override
  public void executeUpdateUser(User user) {
    userDao.executeUpdateUser(user);
  }

  @Override
  public void deleteUser(User user) {
    userDao.deleteUser(user);
  }

  @Override
  public User login(User userCredentials) throws LoginException {
    // TODO (Damian Muca): 5/30/20 add find method.
    Optional<User> optionalUser = userDao.findAll().stream()
        .filter(u -> u.getEmail().equals(userCredentials.getEmail().trim()))
        .filter(u -> u.getPassword().equals(userCredentials.getPassword()))
        .findAny();

    if (optionalUser.isEmpty()){
      throw new LoginException("Incorrect username or password.");
    }
    else{
      User user = optionalUser.get();
      userDao.createSession(user);
      String sessionToken = userDao.getLastSessionToken(user.getId());
      user.setToken(sessionToken);
      System.out.printf("New session token %s\n", sessionToken);
      user.setPassword("not-visible");
      return user;
    }
  }
}
