package tungus.games.hexproto.game.model.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import tungus.games.hexproto.game.model.hexmath.HexCoord;
import tungus.games.hexproto.game.model.hexmath.HexDirection;
import tungus.games.hexproto.game.model.piece.Piece;

import java.util.LinkedList;
import java.util.List;

/**
 * Six sided hexagonal grid board.
 * The sides are not necessarily the same length.
 */
public class Board {

    private final Field[][] map;
    private final List<Piece> pieceList = new LinkedList<Piece>();
    private final CoordValidator validator;

    private final BoardRenderer renderer;

    /**
     * Create a hexagonal board with given side lengths.
     *
     * @param left  The length of the vertical (left and right) sides
     * @param right The length of the top right and bottom left sides
     * @param vert  The length of the top left and bottom right sides
     */
    Board(int left, int right, int vert, Field[][] map) {
        this.map = map;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] instanceof Piece) {
                    pieceList.add((Piece) map[i][j]);
                }
            }
        }
        validator = new CoordValidator(left, right, vert);
        renderer = new BoardRenderer(left, right, vert);
    }

    /**
     * Get the piece at a given coordinate if there is one, null otherwise.
     */
    public Piece pieceAt(HexCoord coord) {
        Field f = field(coord);
        if (f instanceof Piece) {
            return (Piece) f;
        } else {
            return null;
        }
    }

    public void move(List<Piece> pieces, HexDirection dir) {
        List<Piece> toRemove = new LinkedList<Piece>();
        List<Piece> noTwice = new LinkedList<Piece>();
        for (Piece pieceMoved : pieces) {
            removePiece(pieceMoved);
            while (validator.isValid(pieceMoved.pos) && field(pieceMoved.pos) == Field.FREE) {
                pieceMoved.pos.add(dir);
            }
            if (validator.isValid(pieceMoved.pos) && field(pieceMoved.pos) instanceof Piece) {
                Piece pieceHit = (Piece) field(pieceMoved.pos);
                if (pieceMoved.hurts(pieceHit) && pieceHit.deadOnHitFrom(dir.opposite())) {
                    removePiece(pieceHit);
                    pieceMoved.pos.add(dir);
                }
            }
            pieceMoved.pos.sub(dir);
            if (pieceMoved.player == 1 && pieceMoved.pos.equals(HexCoord.axial(5, 0)) ||
                    pieceMoved.player == 0 && pieceMoved.pos.equals(HexCoord.axial(5, 14))) {
                toRemove.add(pieceMoved);
                continue;
            }
        }
        for (Piece p : toRemove) {
            p.removeFromBoard();
            removePiece(p);
        }
    }

    private void addPiece(Piece piece) {
        HexCoord coord = piece.pos;
        if (validator.isValid(coord) && map[coord.q()][coord.r()] == Field.FREE) {
            map[coord.q()][coord.r()] = piece;
            pieceList.add(piece);
        } else {
            throw new GdxRuntimeException("Attempted to add piece to occupied pos");
        }
    }

    private void removePiece(Piece piece) {
        map[piece.pos.q()][piece.pos.r()] = Field.FREE;
        pieceList.remove(piece);
    }

    private Field field(HexCoord c) {
        return map[c.q()][c.r()];
    }

    public void render(SpriteBatch batch, float delta, List<Piece> selected) {
        renderer.render(batch, map, selected, delta);
        for (Piece p : pieceList) {
            p.renderPiece(batch, selected.contains(p));
        }
    }
}
