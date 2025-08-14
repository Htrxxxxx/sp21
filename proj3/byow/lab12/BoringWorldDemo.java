package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    public static void main(String[] args) {
       // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        // very important to initialize the tile with nothing
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        HexWorld hexWorld = new HexWorld();
        HexWorld.Position p = new HexWorld.Position(20, 20);
        hexWorld.drawWorld(world , 4 , 2);
        ter.renderFrame(world);
    }


}
