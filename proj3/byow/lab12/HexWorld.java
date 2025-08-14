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

    private void addHexColumn(TETile[][] world, Position pos, int s, int numHexes, TETile tile) {
        for (int i = 0; i < numHexes; i++) {
            Position hexPos = new Position(pos.x, pos.y + i * (2 * s));
            drawHexagon(hexPos, world, tile, s);
        }
    }

    public void intializeWorld (TETile[][] world){
        int worldWidth = world.length;
        int worldHeight = world[0].length;
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                if (world[x][y] == null) {
                    world[x][y] = Tileset.NOTHING;
                }
            }
        }
    }
    public void drawWorld (TETile[][] world, int hexSize, int tessSize) {
        if (hexSize <= 1 || tessSize <= 0) return;
        int worldWidth = world.length;
        int worldHeight = world[0].length;

        intializeWorld(world);

        int colCount = 2 * tessSize - 1;
        int colGap = 2 * hexSize - 1;
        int maxHexesInCol = 2 * tessSize - 1;
        int maxColumnPixelHeight = maxHexesInCol * 2 * hexSize;

        int maxRowWidth = 3 * hexSize - 2;

        int totalWidthUsed = (colCount - 1) * colGap + maxRowWidth;
        int startX = Math.max(0, (worldWidth - totalWidthUsed) / 2);
        int startY = Math.max(0, (worldHeight - maxColumnPixelHeight) / 2);

        TETile[] palette = new TETile[] {
                Tileset.FLOWER, Tileset.GRASS, Tileset.WALL, Tileset.SAND, Tileset.MOUNTAIN , Tileset.AVATAR
        };

        for (int col = 0; col < colCount; col++) {
            int hexesInCol = tessSize + (col < tessSize ? col : (2 * tessSize - 2 - col));
            int xPos = startX + col * colGap;
            int columnPixelHeight = hexesInCol * 2 * hexSize;
            int yOffsetToCenter = (maxColumnPixelHeight - columnPixelHeight) / 2;
            int baseY = startY + yOffsetToCenter;
            TETile tile = palette[col % palette.length];
            Position colBottomPos = new Position(xPos, baseY);
            addHexColumn(world, colBottomPos, hexSize, hexesInCol, tile);
        }
    }
}
