package edu.uoc.uocoban.model.utils;

import java.util.Objects;

public class Position extends Object{
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position){
            //Casting
            Position obj = (Position) o;
            return (obj.x== this.x && obj.y==this.y);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.valueOf(getX()) + "," + String.valueOf(getY());
    }
}
