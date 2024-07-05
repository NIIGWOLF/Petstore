package model;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Pat {
    public BigDecimal id;
    public Category category;
    public String name;
    public ArrayList<String> photoUrls;
    public ArrayList<Tag> tags;
    public String status;

    public Pat(){
        FakeValuesService fake = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        Random random = new Random();

        id = new BigDecimal(-1);
        category = new Category();
        name = fake.regexify("[a-z1-9]{10}");
        status = fake.regexify("[a-z1-9]{10}");

        int countPhoto = random.nextInt(1,3);
        var listPhoto = new ArrayList<String>();
        for (int i = 0; i<countPhoto; i++){
            listPhoto.add(fake.regexify("[a-z1-9]{10}"));
        }
        photoUrls = listPhoto;

        int countTags = random.nextInt(1,3);
        var listTags = new ArrayList<Tag>();
        for (int i = 0; i<countTags; i++){
            listTags.add(new Tag());
        }
        tags = listTags;
    }
}
