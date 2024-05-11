package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class World {
    public TETile[][] world;
    public final int WIDTH;
    public final int HEIGHT;
    public ArrayList<Room> rooms;

    public World(int width, int height){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.world = new TETile[width][height];
        rooms = new ArrayList<>();
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                this.world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private boolean canRoombePlaced(Room room){
        boolean tag = true;
        for(int i = room.position.x;i < room.position.x + room.width;i++){
            for(int j = room.position.y;j < room.position.y + room.height;j++){
                if(world[i][j] != Tileset.NOTHING){
                    tag = false;
                }
            }
        }
        return tag;
    }

    public void roomGenerate(Random random){
        int num = 49;
        while(num > 0){
            int width = RandomUtils.uniform(random, 3, 8);
            int height = RandomUtils.uniform(random, 3, 8);
            int x = RandomUtils.uniform(random, 1, WIDTH - width-1);
            int y = RandomUtils.uniform(random, 1, HEIGHT - height-1);
            if(canRoombePlaced(new Room(height, width, new Position(x, y)))){
                rooms.add(new Room(height, width, new Position(x, y)));
                for(int i = x;i < x + width;i++){
                    for(int j = y;j < y + height;j++){
                        world[i][j] = Tileset.FLOOR;
                    }
                }
            }
            num--;
        }
    }

    public void roomsSort(){
        Collections.sort(rooms, (o1, o2) -> o1.position.x - o2.position.x);
    }

    public void hallwayGenerate(Random random){
        roomsSort();
        for(int i = 0;i < rooms.size() - 2;i++){
            Position start = new Position(0, 0);
            Position end = new Position(0, 0);
            start.x = RandomUtils.uniform(random, rooms.get(i).position.x, rooms.get(i).position.x + rooms.get(i).width);
            start.y = RandomUtils.uniform(random, rooms.get(i).position.y, rooms.get(i).position.y + rooms.get(i).height);
            end.x = RandomUtils.uniform(random, rooms.get(i + 1).position.x, rooms.get(i + 1).position.x + rooms.get(i + 1).width);
            end.y = RandomUtils.uniform(random, rooms.get(i + 1).position.y, rooms.get(i + 1).position.y + rooms.get(i + 1).height);
            if (start.x < end.x){
                for(int j = start.x;j <= end.x;j++){
                    world[j][start.y] = Tileset.FLOOR;
                }
            }
            if (start.x > end.x){
                for(int j = end.x;j <= start.x;j++){
                    world[j][start.y] = Tileset.FLOOR;
                }
            }
            if (start.y < end.y){
                for(int j = start.y;j <= end.y;j++){
                    world[end.x][j] = Tileset.FLOOR;
                }
            }
            if (start.y > end.y){
                for(int j = end.y;j <= start.y;j++){
                    world[end.x][j] = Tileset.FLOOR;
                }
            }
        }
    }

    public void wallGenerate(){
        for(int i = 1;i < WIDTH - 1;i++){
            for(int j = 1;j < HEIGHT - 1;j++){
                if (world[i][j] == Tileset.NOTHING){
                    if (world[i - 1][j] == Tileset.FLOOR || world[i + 1][j] == Tileset.FLOOR || world[i][j - 1] == Tileset.FLOOR || world[i][j + 1] == Tileset.FLOOR
                    || world[i - 1][j - 1] == Tileset.FLOOR || world[i + 1][j + 1] == Tileset.FLOOR || world[i - 1][j + 1] == Tileset.FLOOR || world[i + 1][j - 1] == Tileset.FLOOR){
                        world[i][j] = Tileset.WALL;
                    }
                }
            }
        }
        for(int i = 1;i < WIDTH -1;i++){
            if (world[i][0] == Tileset.NOTHING){
                if (world[i - 1][1] == Tileset.FLOOR || world[i + 1][1] == Tileset.FLOOR || world[i][1] == Tileset.FLOOR){
                    world[i][0] = Tileset.WALL;
                }
            }
            if (world[i][HEIGHT - 1] == Tileset.NOTHING){
                if (world[i - 1][HEIGHT - 2] == Tileset.FLOOR || world[i + 1][HEIGHT - 2] == Tileset.FLOOR || world[i][HEIGHT - 2] == Tileset.FLOOR){
                    world[i][HEIGHT - 1] = Tileset.WALL;
                }
            }
        }
        for(int i = 1;i < HEIGHT -1;i++){
            if (world[0][i] == Tileset.NOTHING){
                if (world[1][i - 1] == Tileset.FLOOR || world[1][i + 1] == Tileset.FLOOR || world[1][i] == Tileset.FLOOR){
                    world[0][i] = Tileset.WALL;
                }
            }
            if (world[WIDTH - 1][i] == Tileset.NOTHING){
                if (world[WIDTH - 2][i - 1] == Tileset.FLOOR || world[WIDTH - 2][i + 1] == Tileset.FLOOR || world[WIDTH - 2][i] == Tileset.FLOOR){
                    world[WIDTH - 1][i] = Tileset.WALL;
                }
            }
        }
    }
    public void printWorld(){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }
}
