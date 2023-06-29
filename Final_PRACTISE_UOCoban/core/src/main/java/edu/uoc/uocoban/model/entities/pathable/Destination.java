package edu.uoc.uocoban.model.entities.pathable;

import edu.uoc.uocoban.model.entities.MapItem;
import edu.uoc.uocoban.model.entities.movable.Box;
import edu.uoc.uocoban.model.utils.Position;
import edu.uoc.uocoban.model.utils.Sprite;

public abstract class Destination extends MapItem implements Mutable {
    private Box box;

    protected Destination(Position position, Sprite sprite) {
        super(position, sprite);
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {

        this.box = box;
        if (this instanceof BigBoxDestination bigBoxDestination){
            bigBoxDestination.mutate();}
        else if (this instanceof SmallBoxDestination smallBoxDestination){
            smallBoxDestination.mutate();}
    }

    public boolean isEmpty(){
        return this.getBox() == null;
    }

    @Override
    public boolean isPathable() {
        return isEmpty();
    }
}
