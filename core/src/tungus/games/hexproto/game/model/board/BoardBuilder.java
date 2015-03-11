package tungus.games.hexproto.game.model.board;

import tungus.games.hexproto.game.model.hexmath.HexCoord;
import tungus.games.hexproto.game.model.piece.Piece;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Peti on 2015.03.08..
 */
public class BoardBuilder {

    private final int left, right, vert;
    private final Field[][] map;
    private final List<Piece> pieceList = new LinkedList<Piece>();
    private final CoordValidator validator;

    public BoardBuilder(int left, int right, int vert) {
        this.left = left;
        this.right = right;
        this.vert = vert;
        int qLength = left + right - 1;
        int rLength = left + vert - 1;
        map = new Field[qLength][rLength];
        int minSum = left - 1;
        int maxSum = left + right + vert - 3;
        for (int i = 0; i < qLength; i++) {
            for (int j = 0; j < rLength; j++) {
                if (i + j <= maxSum && i + j >= minSum) {
                    map[i][j] = Field.FREE;
                }
            }
        }
        validator = new CoordValidator(left, right, vert);
    }

    public void addWall(HexCoord coord) {
        map[coord.q()][coord.r()] = Field.WALL;
    }

    public void addPiece(Piece piece) {
        map[piece.pos.q()][piece.pos.r()] = piece;
    }

    public Board build() {
        return new Board(left, right, vert, map);
    }
}
