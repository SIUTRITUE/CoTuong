package cotuong;

import java.util.ArrayList;
import java.util.List;

// sĩ
public class Advisor extends ChessPieces {

	public Advisor(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}

	public String getChina() {
		return isWhite ? "仕" : "士"; // Sĩ trắng và Sĩ đen
	}

	@Override
	public List<int[]> getMoveTrue(Board board) {
		List<int[]> move = new ArrayList<>();
		int[][] directionMove = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
		int rowMin = isWhite ? 0 : 7, colMax = isWhite ? 2 : 9;

		for (int[] direction : directionMove) {
			int rowNew = row + direction[0];
			int colNew = col + direction[1];

			if (board.indexTrue(rowNew, colNew) && rowNew >= rowMin && rowNew <= colMax && colNew >= 3 && colNew <= 5) {
				if (board.indexNull(rowNew, colNew) || board.chessPiecesCompetitor(rowNew, colNew, isWhite)) {
					move.add(new int[] { rowNew, colNew });
				}
			}
		}
		// Kiểm tra các nước đi hợp lệ, loại bỏ các nước đi làm Tướng bị chiếu
		removeCheckmate(move, board);
		return move;
	}
}
