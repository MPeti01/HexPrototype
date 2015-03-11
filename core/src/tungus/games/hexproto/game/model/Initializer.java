package tungus.games.hexproto.game.model;

import com.badlogic.gdx.files.FileHandle;
import tungus.games.hexproto.game.model.board.Board;
import tungus.games.hexproto.game.model.board.BoardBuilder;
import tungus.games.hexproto.game.model.hexmath.HexCoord;
import tungus.games.hexproto.game.model.hexmath.HexDirection;
import tungus.games.hexproto.game.model.piece.PieceScanner;
import tungus.games.hexproto.game.model.piece.PieceStore;

import java.util.Scanner;

/**
 * Created by Peti on 2015.03.08..
 */
public class Initializer {

    private static final String WALL_TOKEN = "#";
    private FileHandle file;

    public Board board;
    public PieceStore[] stores = new PieceStore[2];

    public Initializer(FileHandle file) {
        this.file = file;
    }

    public void load() {
        Scanner sc = new Scanner(file.read());
        int left = sc.nextInt();
        int right = sc.nextInt();
        int vert = sc.nextInt();
        BoardBuilder boardBuilder = new BoardBuilder(left, right, vert);
        loadWalls(sc, boardBuilder, left, right, vert);

        // Load and add pieces
        PieceScanner pieceSc = new PieceScanner(sc);
        stores[0] = new PieceStore(pieceSc, boardBuilder, 0);
        pieceSc = new PieceScanner(sc);
        stores[1] = new PieceStore(pieceSc, boardBuilder, 1);

        board = boardBuilder.build();

    }

    private void loadWalls(Scanner sc, BoardBuilder board, int left, int right, int vert) {
        // The number of different Y coordinates when rendering, the number of rows in the text representation
        int rows = left + (vert - 1) * 2 + right - 1;

        HexCoord leftEdge = HexCoord.axial(left - 1, 0);
        HexCoord rightEdge = leftEdge.cpy();
        String token = sc.next();
        if (sc.equals(WALL_TOKEN)) {
            board.addWall(leftEdge);
        }

        HexCoord rightStep = HexDirection.UP_RIGHT.add(HexDirection.DOWN_RIGHT);

        // One row (top hex) done, rows-1 left
        int rowsDone = 1;
        while (rowsDone < rows) {
            if (rowsDone < left                             // If we're on the first edge on the left half, going left-down
                    || (rowsDone < left + (vert - 1) * 2      // Or we're on the vertical edge on the left half..
                    && (rowsDone - left) % 2 == 1)) {   // ..and we're zigzagging to the left this time as we follow the vertical
                leftEdge.add(HexDirection.DOWN_LEFT);
            } else {
                leftEdge.add(HexDirection.DOWN_RIGHT);
            }

            if (rowsDone < right                             // If we're on the first edge on the right half, going right-down
                    || (rowsDone < right + (vert - 1) * 2      // Or we're on the vertical edge on the right half..
                    && (rowsDone - right) % 2 == 1)) {       // ..and we're zigzagging to the right this time as we follow the vertical
                rightEdge.add(HexDirection.DOWN_RIGHT);
            } else {
                rightEdge.add(HexDirection.DOWN_LEFT);
            }
            HexCoord current = leftEdge.cpy();
            HexCoord pastTheEnd = rightEdge.cpy().add(rightStep);
            while (!current.equals(pastTheEnd)) {
                token = sc.next();
                if (token.equals(WALL_TOKEN)) {
                    board.addWall(current);
                }
                current.add(rightStep);
            }
            current.free();
            pastTheEnd.free();
            rowsDone++;
        }
        rightStep.free();
        rightEdge.free();
        leftEdge.free();
    }
}
