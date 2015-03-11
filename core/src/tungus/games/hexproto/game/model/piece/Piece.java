package tungus.games.hexproto.game.model.piece;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tungus.games.hexproto.Assets.Tex;
import tungus.games.hexproto.game.model.board.BoardBuilder;
import tungus.games.hexproto.game.model.board.BoardRenderer;
import tungus.games.hexproto.game.model.board.Field;
import tungus.games.hexproto.game.model.hexmath.HexCoord;
import tungus.games.hexproto.game.model.hexmath.HexDirection;

import java.util.EnumSet;
import java.util.Set;

/**
 * Playing piece storing its position on the board and shield status.
 */
public class Piece extends Field {

    private static final float SPEED = 600;
    private static final float SIZE = 40f;

    private static final Vector2 temp = new Vector2();

    public final HexCoord pos;
    private final Vector2 graphicGoal = new Vector2();
    private final Vector2 graphicPos = new Vector2();
    private final Tex pieceTex;

    private final PieceStore store;
    private final Set<HexDirection> shields = EnumSet.noneOf(HexDirection.class);

    public final int player;
    final int group;

    public Piece(HexCoord coord, int player, int group, BoardBuilder boardBuilder, PieceStore pieceStore) {
        super(Tex.EMPTY_HEX); // Make it render the free hex underneath
        this.pos = coord;
        this.player = player;
        this.group = group;
        this.store = pieceStore;
        boardBuilder.addPiece(this);
        pos.toPixel(BoardRenderer.HEX_SIZE, graphicGoal);
        graphicGoal.x += BoardRenderer.topLeft.x;
        graphicGoal.y = BoardRenderer.topLeft.y - graphicGoal.y;
        graphicPos.set(graphicGoal);
        pieceTex = Tex.PIECES[player][group];
    }

    public void addShield(HexDirection dir) {
        shields.add(dir);
    }

    public boolean deadOnHitFrom(HexDirection direction) {
        if (!shields.contains(direction)) {
            removeFromBoard();
            return true;
        } else {
            shields.remove(direction);
            return false;
        }
    }

    public void removeFromBoard() {
        store.remove(this);
        pos.free();
    }

    @Override
    public void render(SpriteBatch batch, Vector2 pos, float hexSize, float delta) {
        super.render(batch, pos, hexSize, delta);
        graphicGoal.set(pos);
        if (graphicGoal.dst2(graphicPos) > SPEED * SPEED * delta * delta) {
            temp.set(graphicGoal).sub(graphicPos).nor().scl(SPEED * delta);
            graphicPos.add(temp);
        } else {
            graphicPos.set(graphicGoal);
        }
    }

    public void renderPiece(SpriteBatch batch, boolean selected) {
        batch.draw(pieceTex.t, graphicPos.x - SIZE / 2, graphicPos.y - SIZE / 2, SIZE, SIZE);
        for (HexDirection dir : shields) {
            batch.draw(Tex.SHIELD.t, graphicPos.x - SIZE / 2, graphicPos.y - SIZE / 2, SIZE / 2, SIZE / 2, SIZE, SIZE, 1, 1, dir.angle);
        }
        if (selected) {
            batch.draw(Tex.SELECTION.t, graphicPos.x - SIZE / 2, graphicPos.y - SIZE / 2, SIZE, SIZE);
        }
    }

    public boolean hurts(Piece other) {
        return other.player != this.player;
    }
}
