package cotuong;

import java.util.ArrayList;
import java.util.List;

// pháo
public class Cannon extends ChessPieces {

	public Cannon(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
	}

	public String getChina() {
		return isWhite ? "炮" : "砲"; // Pháo trắng và Pháo đen
	}

	@Override
	public List<int[]> getMoveTrue(Board board) {
		List<int[]> move = new ArrayList<>();

		// Di chuyển theo hàng và cột
		for (int[] direction : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }) {
			boolean nhayQua = false;

			for (int i = 1;; i++) {
				int rowNew = row + direction[0] * i;
				int colNew = col + direction[1] * i;

				if (!board.indexTrue(rowNew, colNew))
					break;

				if (board.indexNull(rowNew, colNew)) {
					if (!nhayQua)
						move.add(new int[] { rowNew, colNew });
				} else {
					if (nhayQua && board.chessPiecesCompetitor(rowNew, colNew, isWhite)) {
						move.add(new int[] { rowNew, colNew });
						break;
					}
					if (nhayQua && !board.chessPiecesCompetitor(rowNew, colNew, isWhite)) {
						break;
					}
					nhayQua = true;
				}
			}
		}
		// Kiểm tra các nước đi hợp lệ, loại bỏ các nước đi làm Tướng bị chiếu
		removeCheckmate(move, board);
		return move;
	}
}
