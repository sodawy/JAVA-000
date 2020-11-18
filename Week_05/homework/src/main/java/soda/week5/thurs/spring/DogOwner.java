package soda.week5.thurs.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("dogOwner")
public class DogOwner {
    @Autowired
    @Qualifier("dog")
    Dog dog;

    public DogOwner() {
    }

    @Override
    public String toString() {
        return "DogOwner{" +
                "dog=" + dog +
                '}';
    }
}
