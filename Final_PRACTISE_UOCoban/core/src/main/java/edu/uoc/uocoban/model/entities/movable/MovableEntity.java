package edu.uoc.uocoban.model.entities.movable;

import edu.uoc.uocoban.model.Level;
import edu.uoc.uocoban.model.entities.MapItem;
import edu.uoc.uocoban.model.utils.Direction;
import edu.uoc.uocoban.model.utils.Position;
import edu.uoc.uocoban.model.utils.Sprite;

public abstract class MovableEntity extends MapItem {
    private Level level;

    protected MovableEntity(Position position, Sprite sprite, Level level) {
        super(position, sprite);
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    private void setLevel(Level level) {
        this.level = level;
    }

    public abstract boolean move(Direction direction);
}
