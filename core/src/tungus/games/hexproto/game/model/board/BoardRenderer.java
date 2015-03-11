package tungus.games.hexproto.game.model.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tungus.games.hexproto.game.model.hexmath.HexCoord;
import tungus.games.hexproto.game.model.piece.Piece;

import java.util.List;

/**
 * Created by Peti on 2015.03.07..
 */
public class BoardRenderer {

    public static final float HEX_SIZE = 25f;
    public static final Vector2 topLeft = new Vector2(50, 820);

    private static final Vector2 res = new Vector2();

    public BoardRenderer(int left, int right, int top) {

    }

    public void render(SpriteBatch batch, Field[][] map, List<Piece> selected, float delta) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != null) {
                    HexCoord coord = HexCoord.axial(i, j);
                    coord.toPixel(HEX_SIZE, res);
                    coord.free();
                    res.x += topLeft.x;
                    res.y = topLeft.y - res.y;
                    map[i][j].render(batch, res, HEX_SIZE, delta);
                }
            }
        }
    }
}
