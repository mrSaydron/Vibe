package ru.mrak.vibe.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.mrak.vibe.util.codeGenerator.GrayCode;

public class UserName {

    private static final Set<Character> vowel = new HashSet() {{
        add('а');
        add('е');
        add('ё');
        add('и');
        add('о');
        add('у');
        add('ю');
        add('я');
        add('ь');
        add('э');
        add('ы');
    }};

    public static String getUserName(int userIndex, List<String> animals, List<String> personalities) {
        int greyCode = GrayCode.getGreyCode(userIndex);
        int animalIndex = greyCode / animals.size();
        int personalitiesIndex = greyCode % animals.size();
        String animal = animals.get(animalIndex);
        char lastChar = animal.charAt(animal.length() - 1);
        String personal;
        if (vowel.contains(lastChar)) {
            personal = personalities.get(personalitiesIndex).split(",")[1];
        } else {
            personal = personalities.get(personalitiesIndex).split(",")[0];
        }
        return personal + " " + animal;
    }
}
