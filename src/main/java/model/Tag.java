package model;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class Tag {
    public int id;
    public String name;

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {
        FakeValuesService fake = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        Random random = new Random();

        this.id = random.nextInt(1, 10);
        this.name = fake.regexify("[a-z1-9]{10}");
    }
}