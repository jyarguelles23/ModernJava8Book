package com.viqsystems.Clases.Apple;

import com.viqsystems.Enums.Colors;

import java.awt.*;

public class Apple {

   private Color color;
   private double weight;

    public Apple(int i, Colors green) {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
