package ru.sber.cards.service;

import ru.sber.cards.dao.UserDao;
import ru.sber.cards.dao.models.LoginRequest;
import ru.sber.cards.dao.models.RegistrationRequest;
import ru.sber.cards.dao.models.User;


public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void registration(RegistrationRequest request) {
      User user = new User();
      user.setName(request.getName());
      user.setLastName(request.getLastName());
      user.setPassword(request.getPassword());

      userDao.saveNewUser(user);
    }

    @Override
    public void login(LoginRequest loginRequest)throws Exception {
        if(userDao.loginChek(loginRequest)){
            userDao.setLoginTrue(loginRequest);
        }else{
            throw new Exception("Неверный логин или пароль");
        }
    }
}