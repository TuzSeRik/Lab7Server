package Background;

import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class Person {

    private Status status = Status.NONE;
    Cloth cloth;
    private int height;

    Person(){}
    Person(int height, String CName, Integer hp){
        this.height = height;
        this.cloth = new Cloth(CName, hp);
    }

    public void repairCloth(){
        cloth.Repair();
        if (cloth.hp>=cloth.hpMax){
            cloth.hp = cloth.hpMax;
            this.setStatus(Status.NONE);
        }
    }

    public int getHeight(){
        return height;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    abstract void takeFood(Furnace furnace, ObjectOutputStream os) throws IOException;





    public class Cloth implements Repairable{

        private String name;
        private int hpMax;
        private int hp;
        private boolean isBroke = false;

        Cloth(String name, int hp){
            this.name = name;
            this.hpMax = hp;
            this.hp = this.hpMax;
        }

        String getName(){
            return name;
        }

        boolean getIsBroke(){
            return isBroke;
        }

        @Override
        public void Break() {
            hp--;
            if(hp == 0){
                isBroke = true;
            }
        }

        @Override
        public void Repair(){
            hp++;
            if(hp == hpMax) isBroke = false;
        }
    }
}
//+