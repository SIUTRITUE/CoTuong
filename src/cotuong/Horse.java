package cotuong;

import java.util.ArrayList;
import java.util.List;

// mã
public class Horse extends ChessPieces {
	public Horse(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}

	public String getChina() {
		return isWhite ? "傌" : "馬"; // Mã trắng và Mã đen
	}

	@Override
	public List<int[]> getMoveTrue(Board board) {
		List<int[]> move = new ArrayList<>();
		int[][] directionMove = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 },
				{ 2, 1 } };

		for (int[] direction : directionMove) {
			int rowNew = row + direction[0];
			int colNew = col + direction[1];

			// Kiểm tra nước đi hợp lệ và không bị cản
			if (board.indexTrue(rowNew, colNew) && board.indexNull(row + direction[0] / 2, col + direction[1] / 2)) {
				if (board.indexNull(rowNew, colNew) || board.chessPiecesCompetitor(rowNew, colNew, isWhite)) {
					move.add(new int[] { rowNew, colNew });
				}
			}
		}
		removeCheckmate(move, board);

		return move;
	}
}
