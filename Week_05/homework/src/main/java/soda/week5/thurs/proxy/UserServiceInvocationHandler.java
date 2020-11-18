package soda.week5.thurs.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserServiceInvocationHandler implements InvocationHandler {

    IUserService userService;

    public UserServiceInvocationHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("user service invoke before");
        Object returnValue = method.invoke(userService, args);
        System.out.println("user service invoke after");
        return returnValue;
    }
}
