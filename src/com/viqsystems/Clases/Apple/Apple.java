package com.viqsystems.Clases.Apple;

import com.viqsystems.Enums.Colors;

import java.awt.*;

public class Apple {

   private Colors color;
   private Integer weight;
   public Apple(){}

    public Apple(int weight, Colors color) {
     this.weight=weight;
     this.color=color;
    }

    public Apple(int weight){
        this.weight=weight;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Colors getColor(){
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }
}
