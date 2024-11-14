package cotuong;

import java.util.ArrayList;
import java.util.List;

// tượng
public class Elephant extends ChessPieces {
	public Elephant(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}

	public String getChina() {
		return isWhite ? "相" : "象"; // Tượng trắng và Tượng đen
	}

	@Override
	public List<int[]> getMoveTrue(Board board) {
		List<int[]> move = new ArrayList<>();
		int[][] directionMove = { { 2, 2 }, { 2, -2 }, { -2, 2 }, { -2, -2 } };
		int rowMin = isWhite ? 0 : 5, rowMax = isWhite ? 4 : 9;

		for (int[] direction : directionMove) {
			int rowNew = row + direction[0];
			int colNew = col + direction[1];

			if (board.indexTrue(rowNew, colNew) && rowNew >= rowMin && rowNew <= rowMax
					&& board.indexNull(row + direction[0] / 2, col + direction[1] / 2)) {
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
