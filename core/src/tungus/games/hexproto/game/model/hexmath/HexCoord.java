package tungus.games.hexproto.game.model.hexmath;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Represents hexagonal coordinates on a grid. <br>
 * Coordinate representation using cubic coordinates as described <a href="http://www.redblobgames.com/grids/hexagons/">here</a>.
 * Constraint: x + y + z == 0.<br>
 * Methods with axial coordinates also supported.
 * In all axial coordinate methods, Q is "right-down", R is "down".
 */
public class HexCoord {
    private static Pool<HexCoord> pool = new Pool<HexCoord>(300) {
        @Override
        protected HexCoord newObject() {
            return new HexCoord();
        }
    };

    private HexCoord() {
    } // Limit constructor access, only use pool through static factory methods

    public void free() {
        pool.free(this);
    }

    private int x, y, z;

    public static HexCoord cubic(int x, int y, int z) {
        HexCoord hc = pool.obtain();
        hc.x = x;
        hc.y = y;
        hc.z = z;
        return hc;
    }

    /**
     * Create a HexCoord from the pool with given axial coordinates.
     */
    public static HexCoord axial(int q, int r) {
        return cubic(q, -q - r, r);
    }

    public HexCoord cpy() {
        return cubic(x, y, z);
    }

    public void setCubic(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setAxial(int q, int r) {
        setCubic(q, -q - r, r);
    }

    public void set(HexCoord coord) {
        setCubic(coord.x, coord.y, coord.z);
    }

    public HexCoord add(HexCoord hc) {
        x += hc.x;
        y += hc.y;
        z += hc.z;
        return this;
    }

    public HexCoord add(HexDirection dir) {
        return this.add(dir.coord);
    }

    public HexCoord sub(HexCoord hc) {
        x -= hc.x;
        y -= hc.y;
        z -= hc.z;
        return this;
    }

    public HexCoord sub(HexDirection dir) {
        return sub(dir.coord);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    public int q() {
        return x;
    }

    public int r() {
        return z;
    }

    public Vector2 toPixel(float hexSize, Vector2 target) {
        target.x = hexSize * 3 / 2 * q();
        target.y = hexSize * (float) Math.sqrt(3) * (r() + q() / 2f);
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HexCoord) {
            HexCoord hc = (HexCoord) o;
            return hc.x == x && hc.y == y;  // This also implies hc.z == z because x + y + z == 0
        }
        return false;
    }
}
