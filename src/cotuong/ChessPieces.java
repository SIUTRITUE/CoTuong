package cotuong;

import java.util.Iterator;
import java.util.List;

public abstract class ChessPieces {
	protected int row, col; // vị trí hàng và cột trên bàn cờ
	protected boolean isWhite; // true nếu quân cờ là màu trắng, false nếu là màu đen

	public ChessPieces(int row, int col, boolean isWhite) {
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
	}

	// Phương thức trừu tượng lấy các nước đi hợp lệ
	public abstract List<int[]> getMoveTrue(Board board);

	public boolean isWhite() {
		return isWhite;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void updateIndex(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	protected abstract String getChina();

	public void removeCheckmate(List<int[]> move, Board board) {
		Iterator<int[]> iterator = move.iterator();
		while (iterator.hasNext()) {
			int[] nuoc = iterator.next();
			int hangMoi = nuoc[0];
			int cotMoi = nuoc[1];

			// Kiểm tra nước đi có hợp lệ mà không gây chiếu
			if (!board.moveTemporary(this, hangMoi, cotMoi)) {
				iterator.remove(); // Xóa nước đi nếu làm tướng bị chiếu
			}
		}
	}
}
