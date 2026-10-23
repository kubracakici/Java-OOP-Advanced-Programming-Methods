package model;

public class Pear implements Tree {
    private int age;

    public Pear(int age) {
        this.age = age;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getType() {
        return "Pear";
    }
}