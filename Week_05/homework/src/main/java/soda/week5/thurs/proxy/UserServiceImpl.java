package soda.week5.thurs.proxy;

public class UserServiceImpl implements IUserService {
    @Override
    public void saveUser(User user) {
        System.out.println("save user: " + user.toString());
    }
}
