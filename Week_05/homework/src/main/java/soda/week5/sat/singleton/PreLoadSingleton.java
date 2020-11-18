package soda.week5.sat.singleton;

public class PreLoadSingleton {
    //create singleton instance when class load, thread-safe
    final static private PreLoadSingleton instance = new PreLoadSingleton();

    public static PreLoadSingleton getInstance() {
        return instance;
    }
}
