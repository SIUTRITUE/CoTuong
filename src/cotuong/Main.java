package cotuong;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		Scanner scanner = new Scanner(System.in);
		boolean white = true; // true nếu lượt trắng, false nếu lượt đen
		boolean endGame = false;

		System.out.println("Trò chơi Cờ Tướng bắt đầu!");

		while (!endGame) {
			String colorPieces = white ? "trắng" : "đen";
			System.out.println("\nLượt của người chơi " + colorPieces);

			board.printBoard();
			if (board.isCheckmate(white)) {
				System.out.println("\nĐang bị chiếu\n");
			}
			System.out.print("Nhập hàng và cột của quân cờ muốn chọn (ví dụ: 0 0 (xe trắng)): ");
			int selectRow = scanner.nextInt();
			int selectCol = scanner.nextInt();

			ChessPieces selectPieces = board.getChessPieces(selectRow, selectCol);
			if (selectPieces == null || selectPieces.isWhite() != white) {
				System.out.println("Không có quân cờ hợp lệ tại vị trí này hoặc không phải quân của bạn!");
				continue;
			}

			List<int[]> moveTrue = selectPieces.getMoveTrue(board);
			if (moveTrue.isEmpty()) {
				System.out.println("Quân " + selectPieces.getClass().getSimpleName() + " không có nước đi hợp lệ.");
				continue;
			}

			System.out.println("Các vị trí có thể đi:");
			for (int[] nuocDi : moveTrue) {
				System.out.print("(" + nuocDi[0] + "," + nuocDi[1] + ") ");
			}
			System.out.println();

			System.out.print("Nhập hàng và cột của vị trí muốn đi đến: ");
			int hangMoi = scanner.nextInt();
			int cotMoi = scanner.nextInt();

			// Kiểm tra nước đi có hợp lệ không
			boolean moveTrueFlag = false;
			for (int[] nuocDi : moveTrue) {
				if (nuocDi[0] == hangMoi && nuocDi[1] == cotMoi) {
					moveTrueFlag = true;
					break;
				}
			}

			if (!moveTrueFlag) {
				System.out.println("Vị trí quân " + selectPieces.getClass().getSimpleName() + " cần đến không hợp lệ.");
				continue;
			}

			// Di chuyển quân cờ
			if (board.moveChessPieces(selectRow, selectCol, hangMoi, cotMoi)) {
				// Chuyển lượt chơi
				white = !white;
			}

			board.printHistory();

			String result = board.checkEnd();
			if (!result.equals("Chưa kết thúc!")) {
				result = white ? "Đen thắng!" : "Trắng thắng!";
				System.out.println("Trò Chơi Kết Thúc " + result);
				endGame = true;
			}
			// Kiểm tra chiếu bí sau khi di chuyển
			if (board.checkCheckmate(!white)) {
				result = white ? "Đen thắng!" : "Trắng thắng!";
				System.out.println("Trò Chơi Kết Thúc " + result);
				endGame = true;
			}

		}
		scanner.close();
	}
}