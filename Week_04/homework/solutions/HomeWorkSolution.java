package homework.solutions;

import java.util.concurrent.Callable;

public interface HomeWorkSolution extends Callable {
    @Override
    Object call() throws Exception;
}
