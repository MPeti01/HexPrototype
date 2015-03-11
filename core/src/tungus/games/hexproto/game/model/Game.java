package tungus.games.hexproto.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tungus.games.hexproto.game.model.board.Board;
import tungus.games.hexproto.game.model.hexmath.HexDirection;
import tungus.games.hexproto.game.model.piece.PieceStore;

/**
 * Created by Peti on 2015.03.05..
 */
public class Game {
    private final Board board;
    private final PieceStore p1selector, p2selector;
    private PieceStore activeSelector;
    private boolean p1 = true;
    private final InputProcessor input = new InputAdapter() {
        @Override
        public boolean keyDown(int keyCode) {
            switch (keyCode) {
                case Keys.Q:
                    board.move(activeSelector.getSelected(), HexDirection.UP_LEFT);
                    break;
                case Keys.W:
                    board.move(activeSelector.getSelected(), HexDirection.UP);
                    break;
                case Keys.E:
                    board.move(activeSelector.getSelected(), HexDirection.UP_RIGHT);
                    break;
                case Keys.A:
                    board.move(activeSelector.getSelected(), HexDirection.DOWN_LEFT);
                    break;
                case Keys.S:
                    board.move(activeSelector.getSelected(), HexDirection.DOWN);
                    break;
                case Keys.D:
                    board.move(activeSelector.getSelected(), HexDirection.DOWN_RIGHT);
                    break;
                case Keys.SPACE:
                    activeSelector.rotateSelection();
                    return true;
                default:
                    return false;
            }
            if (activeSelector == p1selector)
                activeSelector = p2selector;
            else
                activeSelector = p1selector;
            return true;
        }
    };

    public Game() {
        Initializer init = new Initializer(Gdx.files.internal("maps/map.txt"));
        init.load();
        board = init.board;
        p1selector = init.stores[0];
        p2selector = init.stores[1];
        activeSelector = p1selector;
        if (Gdx.input.getInputProcessor() == null) {
            Gdx.input.setInputProcessor(input);
        } else {
            Gdx.input.setInputProcessor(new InputMultiplexer(input, Gdx.input.getInputProcessor()));
        }
    }

    public void render(SpriteBatch batch, float delta) {
        board.render(batch, delta, activeSelector.getSelected());
    }
}
