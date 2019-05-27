package com.tuzserik.github.shorties.background;

import static java.lang.Math.random;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@CsvDataType()
public class Shorty extends Person implements Comparable {

    @CsvField(pos = 1)
    private String name;
    @CsvField(pos = 2)
    private int strength;
    @CsvField(pos = 3)
    private int perception;
    @CsvField(pos = 4)
    private int endurance;
    @CsvField(pos = 5)
    private int charisma;
    @CsvField(pos = 6)
    private int intelligence;
    @CsvField(pos = 7)
    private int agility;
    @CsvField(pos = 8)
    private int luck;
    @CsvField(pos = 9)
    private double priority;
    @CsvField(pos = 10)
    private LocalDateTime creationDate;

    private double power;
    private double accuracy;
    private double hp;
    private double confusionChance;
    private double regenRate;
    private double evasionChance;
    private double criticalChance;

    private ObjectOutputStream output;

    private int foodCount = 0;
    private boolean isBeaten = false;



    Shorty(String name, int strength, int perception, int endurance,
           int charisma, int intelligence, int agility, int luck,
           ObjectOutputStream output, boolean isCreation){
        this.name = name;
        this.strength = strength;
        this.perception = perception;
        this.endurance = endurance;
        this.charisma = charisma;
        this.intelligence = intelligence;
        this.agility = agility;
        this.luck = luck;

        priority = agility+2*luck*random()/10-luck*random()/10;
        power = 2.5*strength;
        accuracy = 0.45+0.05*perception;
        hp = 10*endurance;
        confusionChance = 0.02*charisma;
        regenRate = 5*intelligence;
        evasionChance = 0.02*agility;
        criticalChance = 0.025*luck;

        this.output = output;

        if(isCreation){
            creationDate = LocalDateTime.now();
        }
    }



    private String getName(){
        return this.name;
    }
    private double getConfusionChance(){return this.confusionChance;}
    private double getEvasionChance(){return evasionChance;}
    LocalDateTime getDate() {
        return creationDate;
    }
    void setDate(LocalDateTime date) {this.creationDate = date;}
    int getFoodCount(){
        return foodCount;
    }
    boolean isBeaten() {
        return isBeaten;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.getFoodCount(), ((Shorty) o).getFoodCount());
    }



    void takeFood(Furnace furnace) throws IOException {
        furnace.foodTaken();
        foodCount++;
        output.writeUTF(this.getName()+" взял "+furnace.getName());
    }

    boolean beat(Shorty shorty) throws IOException {
        if (!shorty.isBeaten()) {
            if (!(shorty.getConfusionChance() >= random())) {
                if (accuracy >= random()) {
                    if (!(shorty.getEvasionChance() >= random())) {
                        if (criticalChance >= random()) {
                            shorty.receiveDamage(2 * power);
                            output.writeUTF(this.getName() + " ударил по " + shorty.getName() + " с двойной силой!");
                        } else {
                            shorty.receiveDamage(power);
                            output.writeUTF(this.getName() + " ударил по " + shorty.getName() + ".");
                        }
                    } else output.writeUTF(shorty.getName() + " уклонился от " + this.getName() + ".");
                } else output.writeUTF(this.getName() + " промахнулся по " + shorty.getName() + ".");
            } else {
                output.writeUTF(shorty.getName() + " отговорил " + this.getName() + " бить.");
                return false;
            }
            return true;
        }   else return false;
    }

    private void receiveDamage(double power){
        this.hp-=power;
        if (hp <= 0){
            hp = 0;
        }
        isBeaten = true;
    }

    void heal() throws IOException {
        if(isBeaten && hp<10*endurance){
            hp+=regenRate;
        }
        if(isBeaten && hp==10*endurance){
            isBeaten = false;
            output.writeUTF(this.getName()+" излечился!");
        }
    }

    void laugh(Human human) throws IOException{
        output.writeUTF(this.getName()+" засмеялся над "+human.getName()+".");
    }
}
//+