package tungus.games.hexproto.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tungus.games.hexproto.BaseScreen;
import tungus.games.hexproto.game.model.Game;

/**
 * Created by Peti on 2015.03.07..
 */
public class GameScreen extends BaseScreen {

    private final Game game = new Game();
    private final SpriteBatch batch;

    public GameScreen(com.badlogic.gdx.Game game) {
        super(game);
        OrthographicCamera cam = new OrthographicCamera(480, 800);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        game.render(batch, delta);
        batch.end();
    }
}
