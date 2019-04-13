package Main;

class Human extends Person {
    private String name;

    Human(String name, int heigth, int weigth, String CName, Integer hp){
        super(heigth, CName, hp);
        this.name = name;
    }

    String getName(){
        return name;
    }

    @Override
    void takeFood(Furnace furnace){
        throw new RuntimeException();
    }
}
//+