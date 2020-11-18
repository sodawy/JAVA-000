package soda.week5.sat.singleton;

public class InnerStaticClassSingleton {
    private static class InnerInstance {
        private static final InnerStaticClassSingleton INSTANCE = new InnerStaticClassSingleton();
    }

    private InnerStaticClassSingleton() {
    }

    public static InnerStaticClassSingleton getInstance() {
        return InnerInstance.INSTANCE;
    }
}
