package tungus.games.hexproto.game.model.board;

import tungus.games.hexproto.game.model.hexmath.HexCoord;

/**
 * Created by Peti on 2015.03.09..
 */
class CoordValidator {
    public final int qMax, rMax;
    public final int minSum, maxSum;

    public CoordValidator(int left, int right, int vert) {
        qMax = left + right - 2;
        rMax = left + vert - 2;
        minSum = left - 1;
        maxSum = left + right + vert - 3;
    }

    public boolean isValid(HexCoord coord) {
        return isValid(coord.q(), coord.r());
    }

    public boolean isValid(int q, int r) {
        return (r + q >= minSum) && (r + q <= maxSum) &&
                (q <= qMax) && (q >= 0) && (r <= rMax) && (r >= 0);
    }
}
