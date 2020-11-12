package homework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Proxy {
    private List<Callable> list = new ArrayList<>();

    public void add(Callable callable) {
        list.add(callable);
    }

    public void runAll() throws Exception {
        System.out.println("start");
        for (Callable callable : list) {
            long l = System.nanoTime();
            String simpleName = callable.getClass().getSimpleName();
            int ret = (int) callable.call();
            System.out.printf("duration: %s nano, solution: %s, ret: %d", System.nanoTime() - l, simpleName, ret);
            System.out.println("");
        }
    }
}
