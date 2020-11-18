import org.junit.Assert;
import org.junit.Test;
import soda.week5.sat.singleton.DoubleCheckSingleton;
import soda.week5.sat.singleton.EmunSingleton;
import soda.week5.sat.singleton.InnerStaticClassSingleton;
import soda.week5.sat.singleton.PreLoadSingleton;

public class SingletonTest {

    @Test
    public void doubleCheckSingletonTest() {
        DoubleCheckSingleton instance = DoubleCheckSingleton.getInstance();
        DoubleCheckSingleton instance2 = DoubleCheckSingleton.getInstance();
        Assert.assertTrue(instance == instance2);
    }

    @Test
    public void preloadSingletonTest() {
        PreLoadSingleton instance = PreLoadSingleton.getInstance();
        PreLoadSingleton instance2 = PreLoadSingleton.getInstance();
        Assert.assertTrue(instance == instance2);
    }

    @Test
    public void innerStaticClassSingletonTest() {
        InnerStaticClassSingleton instance = InnerStaticClassSingleton.getInstance();
        InnerStaticClassSingleton instance2 = InnerStaticClassSingleton.getInstance();
        Assert.assertTrue(instance == instance2);
    }

    @Test
    public void emunSingletonTest() {
        EmunSingleton instance = EmunSingleton.INSTANCE;
        EmunSingleton instance2 = EmunSingleton.INSTANCE;
        Assert.assertTrue(instance == instance2);
    }


}
