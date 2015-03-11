package tungus.games.hexproto.game.model.piece;

import tungus.games.hexproto.game.model.board.Board;
import tungus.games.hexproto.game.model.board.BoardBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Peti on 2015.03.05..
 */
public class PieceStore {

    private final List<List<Piece>> groups;
    private int id = 0;

    public PieceStore(PieceScanner pieceSc, BoardBuilder boardBuilder, int player) {
        groups = new LinkedList<List<Piece>>();
        for (int i = 0; i < pieceSc.typeCount; i++) {
            groups.add(new LinkedList<Piece>());
        }

        while (pieceSc.hasNextPiece()) {
            Piece p = pieceSc.nextPiece(player, this, boardBuilder);
            groups.get(p.group).add(p);
        }
    }

    public void selectFromTappedPiece(Piece piece, Board board) {
    }

    public void rotateSelection() {
        do {
            id++;
            if (id == groups.size())
                id = 0;

        } while (groups.get(id).size() == 0);
    }

    public List<Piece> getSelected() {
        return groups.get(id);
    }

    void remove(Piece p) {
        groups.get(p.group).remove(p);
    }
}
