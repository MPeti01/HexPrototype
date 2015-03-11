package tungus.games.hexproto.game.model.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tungus.games.hexproto.Assets.Tex;

/**
 * Created by Peti on 2015.03.07..
 */
public class Field {
    static final Field WALL = new Field(Tex.WALL);
    static final Field FREE = new Field(Tex.EMPTY_HEX);

    private final Tex tex;

    public Field(Tex tex) {
        this.tex = tex;
    }

    public void render(SpriteBatch batch, Vector2 pos, float hexSize, float delta) {
        batch.draw(tex.t, pos.x - hexSize, pos.y - hexSize * (float) Math.sqrt(3) / 2, 2 * hexSize, (float) Math.sqrt(3) * hexSize);
    }
}
