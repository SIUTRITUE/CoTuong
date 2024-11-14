package cotuong;

import java.util.ArrayList;
import java.util.List;

// tướng
public class General extends ChessPieces {

	public General(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}

	public String getChina() {
		return isWhite ? "帥" : "將"; // Tướng trắng và Tướng đen
	}

	@Override
	public List<int[]> getMoveTrue(Board board) {
		List<int[]> move = new ArrayList<>();
		int[][] directionMove = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		int rowMin = isWhite ? 0 : 7, rowMax = isWhite ? 2 : 9;

		for (int[] direction : directionMove) {
			int rowNew = row + direction[0];
			int colNew = col + direction[1];

			if (board.indexTrue(rowNew, colNew) && rowNew >= rowMin && rowNew <= rowMax && colNew >= 3 && colNew <= 5) {
				if (board.indexNull(rowNew, colNew) || board.chessPiecesCompetitor(rowNew, colNew, isWhite)) {
					move.add(new int[] { rowNew, colNew });
				}
			}
		}
		// tướng đối mặt tướng
		for (int i = 1;; i++) {
			int rowNew = row + 1 * i;

			if (!board.indexTrue(rowNew, col))
				break;

			if (board.indexNull(rowNew, col)) {
				continue;
			} else {
				if (board.chessPiecesCompetitor(rowNew, col, isWhite) && board.checkGeneral(rowNew, col)) {
					move.add(new int[] { rowNew, col });
				}
				break; // Dừng lại khi gặp quân cờ khác (đồng đội hoặc đối thủ)
			}
		}
		removeCheckmate(move, board);
		return move;
	}
}
