package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static class Position {
        public int x, y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Position shift(int dx , int dy) {
            return new Position(x + dx, y + dy);
        }
    }

    public void drawHexagon(Position pos, TETile[][] world, TETile tile, int s) {
        if (s <= 1) return;
        int height = 2 * s;
        int worldWidth = world.length;
        int worldHeight = world[0].length;

        for (int row = 0; row < height; row++) {
            int eff = row < s ? row : (2 * s - 1 - row);
            int rowWidth = s + 2 * eff;
            int xStart = pos.x - eff;
            int y = pos.y + row;
            for (int x = xStart; x < xStart + rowWidth; x++) {
                if (x >= 0 && x < worldWidth && y >= 0 && y < worldHeight) {
                    world[x][y] = tile;
                }
            }
        }
    }
}
