package edu.uoc.uocoban.model.entities;

import edu.uoc.uocoban.model.utils.Position;
import edu.uoc.uocoban.model.utils.Sprite;

public abstract class MapItem extends Object {
    private Position position;
    private Sprite sprite;

    protected MapItem(Position position, Sprite sprite) {
        this.position = position;
        this.sprite = sprite;
    }

    public Position getPosition() {
        return position;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract boolean isPathable();

    public void setPosition(Position position) {
        if (position != null){
            this.position = position;
        }
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
