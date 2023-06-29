package edu.uoc.uocoban.model.entities.movable;

import edu.uoc.uocoban.model.Level;
import edu.uoc.uocoban.model.entities.MapItem;
import edu.uoc.uocoban.model.entities.pathable.BigBoxDestination;
import edu.uoc.uocoban.model.entities.pathable.Destination;
import edu.uoc.uocoban.model.entities.pathable.Path;
import edu.uoc.uocoban.model.entities.pathable.SmallBoxDestination;
import edu.uoc.uocoban.model.utils.Direction;
import edu.uoc.uocoban.model.utils.Position;
import edu.uoc.uocoban.model.utils.Sprite;

public abstract class Box extends MovableEntity {
    private boolean delivered;
    protected Box(Position position, Sprite sprite, Level level) {
        super(position, sprite, level);
        this.delivered=false;
    }

    public boolean isDelivered() {
        return delivered;
    }

    private void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public boolean isPathable() {
        return false;
    }

    @Override
    public boolean move(Direction direction) {
        int px = this.getPosition().getX();
        int py = this.getPosition().getY();
        int dx = direction.getX();
        int dy = direction.getY();

        int npx = px+dx;
        int npy = py+dy;
        Position pos = new Position(npx, npy);
        MapItem item = getLevel().getMapItem(pos);

        if (item instanceof Path){
            getLevel().removeMapItem(item);
            this.setPosition(pos);
            return true;
        }
        if (this instanceof BigBox & item instanceof BigBoxDestination){
            getLevel().removeMapItem(this);
            setDelivered(true);
            Destination ditem = (Destination) item;
            ditem.setBox(this);
            return true;
        }
        if (this instanceof SmallBox & item instanceof SmallBoxDestination){
            getLevel().removeMapItem(this);
            setDelivered(true);
            Destination ditem = (Destination) item;
            ditem.setBox(this);
            return true;
        }
        return false;
    }
}
