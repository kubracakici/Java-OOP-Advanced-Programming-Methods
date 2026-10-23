package model;

public class Apple implements Tree {
    private int age;
    public Apple(int age)
    {
        this.age = age;
    }

    //We override the code because we are using this function for all the trees so this is why we override
    @Override
    public int getAge()
    {return age;}

    @Override
    public String getType() {
        return "Apple";
    }
}
