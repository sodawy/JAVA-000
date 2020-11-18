package soda.week5.thurs.proxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        UserServiceInvocationHandler userServiceInvocationHandler = new UserServiceInvocationHandler(userService);

        ClassLoader classLoader = userService.getClass().getClassLoader();
        Class<?>[] interfaces = userService.getClass().getInterfaces();

        IUserService proxyUserService = (IUserService) Proxy.newProxyInstance(classLoader, interfaces, userServiceInvocationHandler);

        proxyUserService.saveUser(new User("Soda", 3));

    }
}
