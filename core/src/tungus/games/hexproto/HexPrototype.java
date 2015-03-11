package tungus.games.hexproto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import tungus.games.hexproto.game.GameScreen;

public class HexPrototype extends Game {
    @Override
    public void create() {
        Assets.load();
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }
}
