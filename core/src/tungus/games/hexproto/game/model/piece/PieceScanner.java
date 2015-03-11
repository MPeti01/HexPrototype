package tungus.games.hexproto.game.model.piece;

import tungus.games.hexproto.game.model.board.BoardBuilder;
import tungus.games.hexproto.game.model.hexmath.HexCoord;
import tungus.games.hexproto.game.model.hexmath.HexDirection;

import java.util.Scanner;

/**
 * Created by Peti on 2015.03.08..
 */
public class PieceScanner {
    private final Scanner sc;
    private int piecesLeft;
    public final int typeCount;

    public PieceScanner(Scanner sc) {
        this.sc = sc;
        piecesLeft = sc.nextInt();
        typeCount = sc.nextInt();
    }

    public boolean hasNextPiece() {
        return piecesLeft > 0;
    }

    public Piece nextPiece(int playerNum, PieceStore store, BoardBuilder boardBuilder) {
        piecesLeft--;
        int q = sc.nextInt();
        int r = sc.nextInt();
        int group = sc.nextInt();
        int shieldCount = sc.nextInt();
        Piece piece = new Piece(HexCoord.axial(q, r), playerNum, group, boardBuilder, store);
        for (int i = 0; i < shieldCount; i++) {
            String dirName = sc.next();
            HexDirection dir = HexDirection.valueOf(HexDirection.class, dirName);
            piece.addShield(dir);
        }

        return piece;
    }
}
