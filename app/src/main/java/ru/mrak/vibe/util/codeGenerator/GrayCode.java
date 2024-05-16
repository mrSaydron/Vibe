package ru.mrak.vibe.util.codeGenerator;

public class GrayCode implements CodeGenerator {

    @Override
    public int getCode(int index) {
        return getGreyCode(index);
    }

    public static int getGreyCode(int index) {
        if (index == 0) return 0;
        if (index == 1) return 1;

        int pow = (int) Math.ceil(Math.log(index + 1) / Math.log(2));
        int newIndex = (int) Math.pow(2, pow) - index - 1;
        int previousNumber = getGreyCode(newIndex);
        return (int) (previousNumber + Math.pow(2, pow - 1));
    }
}
