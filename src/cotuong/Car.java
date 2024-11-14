package cotuong;

import java.util.ArrayList;
import java.util.List;

public class Car extends ChessPieces {

	public Car(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}

	public String getChina() {
		return isWhite ? "俥" : "車"; // Xe trắng và Xe đen
	}

	@Override
	public List<int[]> getMoveTrue(Board board) {
		List<int[]> move = new ArrayList<>();

		// Di chuyển theo hàng và cột
		for (int[] direction : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }) {
			for (int i = 1;; i++) {
				int rowNew = row + direction[0] * i;
				int colNew = col + direction[1] * i;

				if (!board.indexTrue(rowNew, colNew))
					break;

				if (board.indexNull(rowNew, colNew)) {
					move.add(new int[] { rowNew, colNew });
				} else {
					if (board.chessPiecesCompetitor(rowNew, colNew, isWhite)) {
						move.add(new int[] { rowNew, colNew });
					}
					break; // Dừng lại khi gặp quân cờ khác (đồng đội hoặc đối thủ)
				}
			}
		}
		removeCheckmate(move, board);
		return move;
	}
}
