package cotuong;

import java.util.ArrayList;
import java.util.List;

public class Board {
	public ChessPieces[][] index; // Ma trận 10x9 đại diện cho bàn cờ
	private List<String> history; // Lưu lịch sử các nước đi

	public Board() {
		index = new ChessPieces[10][9];
		history = new ArrayList<>();
		constructorBoard();
	}

	public void constructorBoard() {
		// Khởi tạo các quân cờ
		index[0][4] = new General(0, 4, true); // Tướng trắng
		index[9][4] = new General(9, 4, false); // Tướng đen

		index[0][3] = new Advisor(0, 3, true);
		index[0][5] = new Advisor(0, 5, true);
		index[9][3] = new Advisor(9, 3, false);
		index[9][5] = new Advisor(9, 5, false);

		index[0][2] = new Elephant(0, 2, true);
		index[0][6] = new Elephant(0, 6, true);
		index[9][2] = new Elephant(9, 2, false);
		index[9][6] = new Elephant(9, 6, false);

		index[0][1] = new Horse(0, 1, true);
		index[0][7] = new Horse(0, 7, true);
		index[9][1] = new Horse(9, 1, false);
		index[9][7] = new Horse(9, 7, false);

		index[0][0] = new Car(0, 0, true);
		index[0][8] = new Car(0, 8, true);
		index[9][0] = new Car(9, 0, false);
		index[9][8] = new Car(9, 8, false);

		index[2][1] = new Cannon(2, 1, true);
		index[2][7] = new Cannon(2, 7, true);
		index[7][1] = new Cannon(7, 1, false);
		index[7][7] = new Cannon(7, 7, false);

		index[3][0] = new Soldier(3, 0, true);
		index[3][2] = new Soldier(3, 2, true);
		index[3][4] = new Soldier(3, 4, true);
		index[3][6] = new Soldier(3, 6, true);
		index[3][8] = new Soldier(3, 8, true);
		index[6][0] = new Soldier(6, 0, false);
		index[6][2] = new Soldier(6, 2, false);
		index[6][4] = new Soldier(6, 4, false);
		index[6][6] = new Soldier(6, 6, false);
		index[6][8] = new Soldier(6, 8, false);
	}

	// lấy quân cờ
	public ChessPieces getChessPieces(int row, int col) {
		if (row >= 0 && row < 10 && col >= 0 && col < 9) {
			return index[row][col];
		}
		return null;
	}

	// vị trí hợp lệ
	public boolean indexTrue(int row, int col) {
		return row >= 0 && row < 10 && col >= 0 && col < 9;
	}

	// Vị trí trống
	public boolean indexNull(int row, int col) {
		return getChessPieces(row, col) == null;
	}

	// Quan cờ đối thủ
	public boolean chessPiecesCompetitor(int row, int col, boolean isWhite) {
		ChessPieces pieces = getChessPieces(row, col);
		return pieces != null && pieces.isWhite() != isWhite;
	}

	// di chuyen quân cờ
	public boolean moveChessPieces(int rowOld, int colOld, int rowNew, int colNew) {
		ChessPieces pieces = getChessPieces(rowOld, colOld);
		if (pieces != null) {
			List<int[]> moveTrue = pieces.getMoveTrue(this);
			for (int[] move : moveTrue) {
				if (move[0] == rowNew && move[1] == colNew) {
					// Cập nhật vị trí quân cờ
					index[rowNew][colNew] = pieces;
					index[rowOld][colOld] = null;
					pieces.updateIndex(rowNew, colNew);

					history.add(pieces.getName() + " từ (" + rowOld + ", " + colOld + ") đến (" + rowNew + ", " + colNew
							+ ")");
					printBoard();
					return true;
				}
			}
		}
		System.out.println("Vị trí quân cần đến không hợp lệ.");
		return false;
	}

	public void printBoard() {
		System.out.println("Trạng thái bàn cờ hiện tại:");

		// In số cột
		System.out.print("    ");
		for (int j = 0; j < 9; j++) {
			System.out.printf("  %2d   ", j);
		}
		System.out.println();

		// In hàng và các quân cờ
		for (int i = 0; i < 10; i++) {
			// In đường kẻ ngang
			System.out.print("   ");
			for (int j = 0; j < 9; j++) {
				System.out.print("-------");
			}
			System.out.println("-");

			// In chỉ số hàng và các quân cờ
			System.out.printf("%2d |", i);
			for (int j = 0; j < 9; j++) {
				String kyTuTrungQuoc = "      "; // Khoảng trống mặc định cho ô trống

				if (index[i][j] != null) {
					String tenQuanCo = index[i][j].getName();

					if (index[i][j].isWhite()) {
						switch (tenQuanCo) {
							case "General":
								kyTuTrungQuoc = "  帥  ";
								break;
							case "Advisor":
								kyTuTrungQuoc = "  仕  ";
								break;
							case "Elephant":
								kyTuTrungQuoc = "  相  ";
								break;
							case "Horse":
								kyTuTrungQuoc = "  傌  ";
								break;
							case "Car":
								kyTuTrungQuoc = "   俥  ";
								break;
							case "Cannon":
								kyTuTrungQuoc = "  炮  ";
								break;
							case "Soldier":
								kyTuTrungQuoc = "  兵  ";
								break;
						}
					} else {
						switch (tenQuanCo) {
							case "General":
								kyTuTrungQuoc = "  將  ";
								break;
							case "Advisor":
								kyTuTrungQuoc = "  士  ";
								break;
							case "Elephant":
								kyTuTrungQuoc = "  象  ";
								break;
							case "Horse":
								kyTuTrungQuoc = "  馬  ";
								break;
							case "Car":
								kyTuTrungQuoc = "   車  ";
								break;
							case "Cannon":
								kyTuTrungQuoc = "  砲  ";
								break;
							case "Soldier":
								kyTuTrungQuoc = "  卒  ";
								break;
						}
					}
				}

				// In quân cờ với khoảng trống đều hai bên
				System.out.print(kyTuTrungQuoc + "|");
			}
			System.out.println();
		}

		// In đường kẻ ngang cuối cùng
		System.out.print("   ");
		for (int j = 0; j < 9; j++) {
			System.out.print("-------");
		}
		System.out.println("-");
	}

	public void printHistory() {
		System.out.println("Lịch sử các nước đi:");
		for (String move : history) {
			System.out.println(move);
		}
	}

	public String checkEnd() {
		boolean generalWhite = false;
		boolean generalBlack = false;
		boolean whiteHaveMove = false;
		boolean blackHaveMove = false;

		// Duyệt qua bàn cờ để kiểm tra xem mỗi Tướng còn tồn tại hay không và có quân
		// nào có nước đi hợp lệ không
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				ChessPieces quan = index[i][j];
				if (quan instanceof General) {
					if (quan.isWhite()) {
						generalWhite = true;
					} else {
						generalBlack = true;
					}
				}

				if (quan != null) {
					List<int[]> moveTrue = quan.getMoveTrue(this);
					if (quan.isWhite() && !moveTrue.isEmpty()) {
						whiteHaveMove = true; // Trắng còn nước đi hợp lệ
					} else if (!quan.isWhite() && !moveTrue.isEmpty()) {
						blackHaveMove = true; // Đen còn nước đi hợp lệ
					}
				}
			}
		}

		if (!generalWhite) {
			return "Đen thắng!"; // Nếu Tướng trắng không còn trên bàn
		} else if (!generalBlack) {
			return "Trắng thắng!"; // Nếu Tướng đen không còn trên bàn
		} else if (!whiteHaveMove) {
			return "Đen thắng!"; // Nếu Trắng không còn nước đi hợp lệ
		} else if (!blackHaveMove) {
			return "Trắng thắng!"; // Nếu Đen không còn nước đi hợp lệ
		}
		return "Chưa kết thúc!"; // Vẫn chưa kết thúc
	}

	// kiểm tra có phải tướng không
	public boolean checkGeneral(int row, int col) {
		ChessPieces pieces = index[row][col];
		if (pieces instanceof General) {
			return true;
		}
		return false;
	}

	private boolean checkCheckmate = false;

	public boolean moveTemporary(ChessPieces chessPieces, int rowNew, int colNew) {
		if (checkCheckmate)
			return true; // Bỏ qua kiểm tra đệ quy

		int rowOld = chessPieces.getRow();
		int colOld = chessPieces.getCol();
		ChessPieces quanCoBiAn = getChessPieces(rowNew, colNew);

		// Di chuyển tạm thời
		index[rowNew][colNew] = chessPieces;
		index[rowOld][colOld] = null;
		chessPieces.updateIndex(rowNew, colNew);

		// Đánh dấu đang kiểm tra chiếu
		checkCheckmate = true;
		boolean hopLe = !isCheckmate(chessPieces.isWhite());
		checkCheckmate = false;

		// Hoàn tác nước đi tạm thời
		index[rowOld][colOld] = chessPieces;
		index[rowNew][colNew] = quanCoBiAn;
		chessPieces.updateIndex(rowOld, colOld);

		return hopLe;
	}

	public boolean isCheckmate(boolean isWhite) {
		int rowGeneral = -1, colGeneral = -1;

		// Tìm vị trí của Tướng
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				ChessPieces pieces = index[i][j];
				if (pieces instanceof General && pieces.isWhite() == isWhite) {
					rowGeneral = i;
					colGeneral = j;
					break;
				}
			}
		}

		if (rowGeneral == -1 || colGeneral == -1) {
			return false; // Không tìm thấy Tướng
		}

		// Kiểm tra các quân đối thủ xem có thể đánh vào Tướng không
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				ChessPieces piecesCompetitor = index[i][j];
				if (piecesCompetitor != null && piecesCompetitor.isWhite() != isWhite) {
					List<int[]> moveCompetitor = piecesCompetitor.getMoveTrue(this);
					for (int[] move : moveCompetitor) {
						// Nếu có nước đi đến Tướng, thì Tướng bị chiếu
						if (move[0] == rowGeneral && move[1] == colGeneral) {
							return true;
						}
					}
				}
			}
		}
		return false; // Không bị chiếu
	}

	public boolean checkCheckmate(boolean isWhite) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				ChessPieces chessPieces = index[i][j];
				// Kiểm tra tất cả quân của người chơi hiện tại
				if (chessPieces != null && chessPieces.isWhite() == isWhite) {
					List<int[]> moveTrue = chessPieces.getMoveTrue(this);

					// Loại bỏ các nước đi gây chiếu
					chessPieces.removeCheckmate(moveTrue, this);

					// Nếu còn bất kỳ nước đi nào giúp thoát khỏi chiếu
					if (!moveTrue.isEmpty()) {
						return false; // Không bị chiếu bí
					}
				}
			}
		}
		return true; // Bị chiếu bí
	}

}
