package byog.Core;

public class Room {
    public int width;
    public int height;
    public final Position position;

    public Room(int height, int width, Position position){
        this.height = height;
        this.width = width;
        this.position = position;
    }


}
