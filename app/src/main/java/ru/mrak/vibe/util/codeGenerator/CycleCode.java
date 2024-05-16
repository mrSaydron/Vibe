package ru.mrak.vibe.util.codeGenerator;

public class CycleCode implements CodeGenerator {

    private int step;
    private int length;

    public CycleCode(int step, int length) {
        this.step = step;
        this.length = length;
    }

    @Override
    public int getCode(int index) {
        int newIndex = index + step;
        if (newIndex >= length) newIndex -= length;
        return newIndex;
    }
}
