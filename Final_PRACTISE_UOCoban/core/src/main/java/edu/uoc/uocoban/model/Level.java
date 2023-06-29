package edu.uoc.uocoban.model;

import edu.uoc.uocoban.model.entities.MapItem;
import edu.uoc.uocoban.model.entities.MapItemFactory;
import edu.uoc.uocoban.model.entities.Wall;
import edu.uoc.uocoban.model.entities.movable.*;
import edu.uoc.uocoban.model.entities.pathable.*;
import edu.uoc.uocoban.model.exceptions.LevelException;
import edu.uoc.uocoban.model.utils.Direction;
import edu.uoc.uocoban.model.utils.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Represents a level in the game.
 *
 * @author Quim Motger
 * @version 1.0
 */
public class Level {
    /**
     * The minimum width that the map must have.
     */
    private static final int MIN_WIDTH = 10;
    /**
     * The minimum height that the map must have.
     */
    private static final int MIN_HEIGHT = 5;
    /**
     * The name of the level configuration file.
     */
    private String fileName;
    /**
     * The width of the map/labyrinth.
     */
    private int width;
    /**
     * The height of the map/labyrinth.
     */
    private int height;
    /**
     * Level's remaining number of movements
     */
    private int remainingMovements;
    /**
     * Stores the items which are part of the map/labyrinth.
     */
    private List<MapItem> mapItemList;
    /**
     * Reference to {@link Player} object, i.e. the player.
     */
    private Player player;
    /**
     * Boxes that are part of the level.
     */
    private List<Box> boxList;

    /**
     * Constructor with argument.
     *
     * @param fileName Value for the attribute {@code fileName}.
     * @throws LevelException If there are any problems while parsing the level configuration file.
     */
    public Level(String fileName) throws LevelException {
        setFileName(fileName);
        parse();
    }

    /**
     * Setter of the attribute {@code fileName}.
     *
     * @param fileName New value for the attribute {@code fileName}.
     */
    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter of the attribute {@code fileName}
     *
     * @return Current value of the attribute {@code fileName}
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Parses/Reads level's data from the given file.<br/>
     * It also checks if the board's requirements are met.
     *
     * @throws LevelException When there is any error while parsing the file
     *                        or some board's requirement is not satisfied.
     */
    private void parse() throws LevelException {
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(getFileName()));

        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            line = Objects.requireNonNull(getFirstNonEmptyLine(reader));
            setHeight(Integer.parseInt(line));

            line = Objects.requireNonNull(getFirstNonEmptyLine(reader));
            setWidth(Integer.parseInt(line));

            line = Objects.requireNonNull(getFirstNonEmptyLine(reader));
            setRemainingMovements(Integer.parseInt(line));

            mapItemList = new LinkedList<>();//getWidth() * getHeight());
            boxList = new ArrayList<>();

            for (int y = 0; y < getHeight(); y++) {
                char[] rowChar = Objects.requireNonNull(getFirstNonEmptyLine(reader)).toCharArray();
                for (int x = 0; x < getWidth(); x++) {
                    MapItem mapItem = MapItemFactory.getItemMapInstance(x, y, rowChar[x], this);
                    if (mapItem instanceof Player) {
                        if (this.player == null) {
                            this.player = (Player) mapItem;
                        }
                        else
                            throw new LevelException(LevelException.PLAYER_ERROR);
                    } else if (mapItem instanceof Box) {
                        boxList.add((Box) mapItem);
                    }
                    addMapItem(mapItem);
                }
            }

            if (boxList.isEmpty()) {
                throw new LevelException(LevelException.BOX_ERROR);
            }
            if (this.player == null) {
                throw new LevelException(LevelException.PLAYER_ERROR);
            }

            //Checks if there is one Destination of a valid type for each Box
            if (getMapItemList().stream().filter(bigBox -> bigBox instanceof BigBox).count() >
                    getMapItemList().stream().filter(bigBoxDestination -> bigBoxDestination instanceof BigBoxDestination).count() ||
                    getMapItemList().stream().filter(smallBox -> smallBox instanceof SmallBox).count() >
                            getMapItemList().stream().filter(smallBox -> smallBox instanceof SmallBoxDestination).count()) {
                throw new LevelException(LevelException.BOX_DESTINATION_ERROR);
            }

        } catch (IllegalStateException | IllegalArgumentException | IOException e) {
            throw new LevelException(LevelException.PARSING_LEVEL_FILE_ERROR + e.getMessage());
        }
    }

    /**
     * This is a helper method for {@link #parse()} which returns
     * the first non-empty and non-comment line from the reader.
     *
     * @param br BufferedReader object to read from.
     * @return First line that is a parsable line, or {@code null} there are no lines to read.
     * @throws IOException if the reader fails to read a line.
     */
    private String getFirstNonEmptyLine(final BufferedReader br) throws IOException {
        do {

            String s = br.readLine();

            if (s == null) {
                return null;
            }
            if (s.isBlank() || s.startsWith(";")) {
                continue;
            }

            return s;
        } while (true);
    }

    /**
     * Getter of the attribute {@code width}.
     *
     * @return The current value of the attribute {@code width}.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter of the attribute {@code width}.
     *
     * @param width New value for the attribute {@code width}.
     * @throws LevelException When {@code width} is less than {@code MIN_WIDTH}.
     */
    private void setWidth(int width) throws LevelException {
        if(width<MIN_WIDTH) {throw new LevelException(LevelException.SIZE_ERROR);}
        this.width=width;
    }

    /**
     * Getter of the attribute {@code height}.
     *
     * @return The current value of the attribute {@code height}.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter of the attribute {@code height}.
     *
     * @param height New value for the attribute {@code height}.
     * @throws LevelException When {@code height} is less than {@code MIN_HEIGHT}.
     */
    private void setHeight(int height) throws LevelException {
        if(height<MIN_HEIGHT) {throw new LevelException(LevelException.SIZE_ERROR);}
        this.height=height;
    }

    /**
     * Getter of the attribute {@code remainingMovements}.
     *
     * @return The current value of the attribute {@code remainingMovements}.
     */
    public int getRemainingMovements() {
        return remainingMovements;
    }

    /**
     * Setter of the attribute {@code remainingMovements}.
     *
     * @param remainingMovements New value for the attribute {@code remainingMovements}.
     * @throws LevelException When {@code remainingMovements} is less or equal than 0.
     */
    private void setRemainingMovements(int remainingMovements) throws LevelException {
        if(remainingMovements<=0) {throw new LevelException(LevelException.MAX_MOVEMENTS_ERROR);}
        this.remainingMovements=remainingMovements;
    }

    /**
     * Decreases in 1 the {@code remainingMovements} attribute value. If {@code remainingMovements} is less or equal than
     * 0, this method does nothing.
     */
    public void decRemainingMovements() {
        if (this.remainingMovements<=0) {return;}
        this.remainingMovements-=1;
    }

    /**
     * Getter of the attribute {@code mapItemList}.
     *
     * @return The current value of the attribute {@code mapItemList}.
     */
    public List<MapItem> getMapItemList() {
        return mapItemList;
    }

    /**
     * Returns an Iterator of the attribute {@code mapItemList}.
     *
     * @return Iterator of the attribute {@code mapItemList}.
     */
    public Iterator<MapItem> getMapItemListIterator() {
        return mapItemList.iterator();
    }

    /**
     * Returns the map/labyrinth item which is in the position (x,y).
     *
     * @param x Value of the position in the X axis.
     * @param y Value of the position in the Y axis.
     * @return If any, the item which is the position (x,y). Otherwise, {@code null}.
     */
    public MapItem getMapItem(int x, int y) {
        return getMapItem(new Position(x, y));
    }

    /**
     * Returns the map/labyrinth item which is in {@code position}.
     *
     * @param position The position where the item that we want to get is.
     * @return If any, the item which is {@code position}. Otherwise, {@code null}.
     * @throws NullPointerException When {@code position} is {@code null}.
     */
    public MapItem getMapItem(Position position) throws NullPointerException {
        Objects.requireNonNull(position);
        return getMapItemList().stream().filter(item -> item.getPosition().equals(position)).findFirst().orElse(null);
    }

    /**
     * Adds {@code item} to the {@code mapItemList} as long as {@code item} is not {@code null}.
     *
     * @param item Item which we want to add.
     */
    public void addMapItem(MapItem item) {
        if (item != null) mapItemList.add(item);
    }

    /**
     * Removes {@code item} from the {@code mapItemList}.
     *
     * @param item Item which we want to remove.
     */
    public void removeMapItem(MapItem item) {
        mapItemList.remove(item);
    }

    /**
     * Getter of the attribute {@code boxList}.
     *
     * @return The current value of the attribute {@code boxList}.
     */
    public List<Box> getBoxList() {
        return boxList;
    }

    /**
     * Getter ot the attribute {@code player}.
     *
     * @return The current value of the attribute {@code player}.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Checks if the level was finished. This happens when all the objects
     * from type {@link Destination} have a valid {@link Box}.
     *
     * @return {@code true} if none items of type {@link Destination} are empty. Otherwise, {@code false}.
     */
    public boolean hasWon() {
        boolean won = true;
        for (Object obj:getMapItemList()){
            if (obj instanceof Destination){
                won = won & !((Destination) obj).isEmpty();
                if (!won) {break;}
            }
        }
        return won;
    }

    /**
     * Checks if the level is deadlocked (i.e., there is at least one box that is deadlocked)
     *
     * @return {@code true} if the level is deadlocked. Otherwise, {@code false}.
     */
    public boolean isDeadlocked() {
        boolean deadLock = false;
        for (Object obj:getMapItemList()){
            if(obj instanceof Box){
                deadLock = isDeadlocked((Box) obj);
                if (deadLock) {return true;}
            }
        }
        return false;
    }

    /**
     * Checks if the {@code box} is deadlocked. A box is deadlocked if it is not delivered and
     * any of the following conditions is reached:
     *
     * <ul>
     *     <li>The {@code box} is cornered between non pathable and non movable items</li>
     *     <li>The {@code box} is next to another box and none of them can be moved if the other does not moves first.</li>
     * </ul>
     *
     * @param box The box object to which deadlock is evaluated.
     * @return {@code true} if deadlock condition for {@code box} is achieved; {@code false} otherwise.
     */
    private boolean isDeadlocked(Box box) {
        if (!box.isDelivered()) {
            // Box position
            int x = box.getPosition().getX();
            int y = box.getPosition().getY();

            // Case 1: Cornering
            boolean cornered = false;
            // Iterate through directions
            for (Direction dir : Direction.values()) {
                int npx1 = x + dir.getX();
                int npy1 = y + dir.getY();
                int npx2 = 0, npy2 = 0;
                // Get secondary direction for cornering
                if (dir == Direction.UP) {
                    npx2 = x + Direction.RIGHT.getX();
                    npy2 = y + Direction.RIGHT.getY();
                } else if (dir == Direction.RIGHT) {
                    npx2 = x + Direction.DOWN.getX();
                    npy2 = y + Direction.DOWN.getY();
                } else if (dir == Direction.DOWN) {
                    npx2 = x + Direction.LEFT.getX();
                    npy2 = y + Direction.LEFT.getY();
                } else if (dir == Direction.LEFT) {
                    npx2 = x + Direction.UP.getX();
                    npy2 = y + Direction.UP.getY();
                }
                boolean pathMove = isPathableOrMovable(npx1, npy1) | isPathableOrMovable(npx2, npy2);
                cornered = !pathMove;
                if (cornered) {
                    return true;
                }
            }

            // Case 2: Blocks
            for (Direction dir : Direction.values()) {
                if (dir == Direction.LEFT | dir == Direction.RIGHT) {
                    // Horizontal blocks
                    for (Direction subDir: EnumSet.of(Direction.UP, Direction.DOWN)) {
                        if ((!getMapItem(x + subDir.getX(), y + subDir.getY()).isPathable()) &
                                (!getMapItem(x + dir.getX() + subDir.getX(),
                                        y + dir.getY() + subDir.getY()).isPathable()) &
                                (getMapItem(x + dir.getX(), y + dir.getY()) instanceof Box)) {
                            return true;
                        }
                    }
                } else if ((dir == Direction.UP | dir == Direction.DOWN)){
                    // Vertical blocks
                    for (Direction subDir: EnumSet.of(Direction.LEFT, Direction.RIGHT)) {
                        if ((!getMapItem(x + subDir.getX(), y + subDir.getY()).isPathable()) &
                                (!getMapItem(x + dir.getX() + subDir.getX(),
                                        y + dir.getY() + subDir.getY()).isPathable()) &
                                (getMapItem(x + dir.getX(), y + dir.getY()) instanceof Box)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the map item in a given position represented by {@code x} and {@code y} is pathable or movable.
     * @param x The {@code x} coordinate of the given position
     * @param y The {@code y} coordinate of the given position
     * @return {@code true} if the map item for the given position is pathable or movable; otherwise, {@code false}
     */
    private boolean isPathableOrMovable(int x, int y) {
        MapItem item = getMapItem(x,y);
        return item.isPathable() | item instanceof MovableEntity;
    }

}
