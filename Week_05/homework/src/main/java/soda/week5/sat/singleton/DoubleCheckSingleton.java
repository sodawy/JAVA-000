package soda.week5.sat.singleton;

public class DoubleCheckSingleton {
    static volatile DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) { //first check
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) { //double check
                    // memAdd = allocate mem
                    // initInstance
                    // instance = memAdd
                    // volatile prevent re-order instructments
                    instance = new DoubleCheckSingleton();
                }
                return instance;
            }
        }
        return instance;
    }
}
