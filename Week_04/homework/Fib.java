package homework;

import com.sun.tools.javac.util.Assert;

public class Fib {
    final int TIMES = 36;
    final int ANSWER = 14930352;

    final public int sum() {
        int i = 0; //f(0)
        int j = 1; //f(1)
        int ret = 0;
        for (int count = 2; count <= TIMES; count++) {
            ret = i + j;    //f(n) = f(n-2) + f(n-1)
            i = j;
            j = ret;
        }

        Assert.check(ANSWER == ret);

        return ret;
    }
}
