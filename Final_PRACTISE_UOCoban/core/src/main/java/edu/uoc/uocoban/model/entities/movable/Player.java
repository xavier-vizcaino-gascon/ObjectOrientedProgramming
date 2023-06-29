package edu.uoc.uocoban.model.entities.movable;

import edu.uoc.uocoban.model.Level;
import edu.uoc.uocoban.model.entities.MapItem;
import edu.uoc.uocoban.model.entities.pathable.Destination;
import edu.uoc.uocoban.model.entities.pathable.Path;
import edu.uoc.uocoban.model.utils.Direction;
import edu.uoc.uocoban.model.utils.Position;
import edu.uoc.uocoban.model.utils.Sprite;
import edu.uoc.uocoban.model.entities.MapItemFactory;

public class Player extends MovableEntity{
    public Player(Position position, Level level) {
        super(position, Sprite.PLAYER, level);
    }

    @Override
    public boolean isPathable() {
        return false;
    }

    @Override
    public boolean move(Direction direction) {
        // Actual player position
        int px = this.getPosition().getX();
        int py = this.getPosition().getY();

        // New player position from actual + movement
        int npx = px + direction.getX();
        int npy = py + direction.getY();
        Position pos = new Position(npx, npy);

        // Item in new player position
        MapItem item = getLevel().getMapItem(pos);

        if (item instanceof Path){
            // Original player position is replaced with a Path
            MapItem mpi = MapItemFactory.getItemMapInstance(px, py, '.', getLevel());
            getLevel().addMapItem(mpi);
            // Path item to which the player is moving is removed
            getLevel().removeMapItem(getLevel().getMapItem(pos));
            // Player position is updated
            this.setPosition(pos);
            return true;
        }
        if (item instanceof Box) {
            // Cast item to Box
            Box boxItem = (Box) item;
            // Player tries to move the box
            if (boxItem.move(direction)) {
                // Original player position is replaced with a Path
                MapItem mpi = MapItemFactory.getItemMapInstance(px, py, '.', getLevel());
                getLevel().addMapItem(mpi);
                // Player position is updated
                this.setPosition(pos);
                return true;
            }
        }
        return false;
    }
}
