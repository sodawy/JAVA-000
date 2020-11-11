import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        HelloNIO helloNIO = new HelloNIO();


        Class<HelloNIO> helloNIOClazz = HelloNIO.class;

        Field f = helloNIOClazz.getField("a");
        System.out.println(f.get(null));


    }
}
