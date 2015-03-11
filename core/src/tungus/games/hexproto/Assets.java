package tungus.games.hexproto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Peti on 2015.03.07..
 */
public class Assets {
    public static enum Tex {
        EMPTY_HEX,
        WALL,
        SELECTION,
        BLUE_CIRCLE, BLUE_SQUARE, BLUE_TRIANGLE,
        RED_CIRCLE, RED_SQUARE, RED_TRIANGLE,
        SHIELD;
        public static final Tex[][] PIECES = {{BLUE_CIRCLE, BLUE_SQUARE, BLUE_TRIANGLE},
                {RED_CIRCLE, RED_SQUARE, RED_TRIANGLE}};

        private String filename;
        public TextureRegion t = null;

        Tex(String path) {
            this.filename = path;
        }

        Tex() {
            filename = name().replace("_", "").toLowerCase();
        }

        private static void load(TextureAtlas atlas) {
            for (Tex tex : values()) {
                tex.t = atlas.findRegion(tex.filename);
            }
        }
    }

    public static void load() {
        Tex.load(new TextureAtlas(Gdx.files.internal("textures/game.atlas")));
    }
}
