package tungus.games.hexproto.game.model.hexmath;

public enum HexDirection {
    UP_RIGHT(30, +1, -1), UP(90, 0, -1), UP_LEFT(150, -1, 0), DOWN_LEFT(210, -1, +1), DOWN(270, 0, +1), DOWN_RIGHT(330, +1, 0);
    public final float angle;
    final HexCoord coord;

    HexDirection(float angle, int q, int r) {
        this.angle = angle;
        coord = HexCoord.axial(q, r);
    }

    public HexCoord add(HexDirection other) {
        return coord.cpy().add(other.coord);
    }

    public HexDirection opposite() {
        // 3 steps around the array rotate 180 degrees (elements are ordered anticlockwise)
        return values()[(ordinal() + 3) % 6];
    }
}
