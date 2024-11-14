package cotuong;

import java.util.ArrayList;
import java.util.List;

// tốt
public class Soldier extends ChessPieces {
	public Soldier(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}

	public String getChina() {
		return isWhite ? "兵" : "卒"; // Tốt trắng và Tốt đen
	}

	@Override
	public List<int[]> getMoveTrue(Board board) {
		List<int[]> move = new ArrayList<>();

		int buoc = isWhite ? 1 : -1;
		int rowNew = row + buoc;

		if (board.indexTrue(rowNew, col)
				&& (board.indexNull(rowNew, col) || board.chessPiecesCompetitor(rowNew, col, isWhite))) {
			move.add(new int[] { rowNew, col });
		}

		if ((isWhite && row >= 5) || (!isWhite && row <= 4)) {
			for (int[] direction : new int[][] { { 0, 1 }, { 0, -1 } }) {
				int colNew = col + direction[1];
				if (board.indexTrue(row, colNew)
						&& (board.indexNull(row, colNew) || board.chessPiecesCompetitor(row, colNew, isWhite))) {
					move.add(new int[] { row, colNew });
				}
			}
		}
		// Kiểm tra các nước đi hợp lệ, loại bỏ các nước đi làm Tướng bị chiếu
		removeCheckmate(move, board);

		return move;
	}
}
