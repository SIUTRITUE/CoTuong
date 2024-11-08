package cotuong;

import java.util.ArrayList;
import java.util.List;

public class BanCo {
	private QuanCo[][] viTri; // Ma trận 10x9 đại diện cho bàn cờ
	private List<String> lichSuNuocDi; // Lưu lịch sử các nước đi

	public BanCo() {
		viTri = new QuanCo[10][9];
		lichSuNuocDi = new ArrayList<>();
		khoiTaoBanCo();
	}

	public void khoiTaoBanCo() {
		// Khởi tạo các quân cờ
		viTri[0][4] = new QuanTuong(0, 4, true); // Tướng trắng
		viTri[9][4] = new QuanTuong(9, 4, false); // Tướng đen

		viTri[0][3] = new Si(0, 3, true);
		viTri[0][5] = new Si(0, 5, true);
		viTri[9][3] = new Si(9, 3, false);
		viTri[9][5] = new Si(9, 5, false);

		viTri[0][2] = new Tuong(0, 2, true);
		viTri[0][6] = new Tuong(0, 6, true);
		viTri[9][2] = new Tuong(9, 2, false);
		viTri[9][6] = new Tuong(9, 6, false);

		viTri[0][1] = new Ma(0, 1, true);
		viTri[0][7] = new Ma(0, 7, true);
		viTri[9][1] = new Ma(9, 1, false);
		viTri[9][7] = new Ma(9, 7, false);

		viTri[0][0] = new Xe(0, 0, true);
		viTri[0][8] = new Xe(0, 8, true);
		viTri[9][0] = new Xe(9, 0, false);
		viTri[9][8] = new Xe(9, 8, false);

		viTri[2][1] = new Phao(2, 1, true);
		viTri[2][7] = new Phao(2, 7, true);
		viTri[7][1] = new Phao(7, 1, false);
		viTri[7][7] = new Phao(7, 7, false);

		viTri[3][0] = new Tot(3, 0, true);
		viTri[3][2] = new Tot(3, 2, true);
		viTri[3][4] = new Tot(3, 4, true);
		viTri[3][6] = new Tot(3, 6, true);
		viTri[3][8] = new Tot(3, 8, true);
		viTri[6][0] = new Tot(6, 0, false);
		viTri[6][2] = new Tot(6, 2, false);
		viTri[6][4] = new Tot(6, 4, false);
		viTri[6][6] = new Tot(6, 6, false);
		viTri[6][8] = new Tot(6, 8, false);
	}

	public QuanCo layQuanCo(int hang, int cot) {
		if (hang >= 0 && hang < 10 && cot >= 0 && cot < 9) {
			return viTri[hang][cot];
		}
		return null;
	}

	public boolean viTriHopLe(int hang, int cot) {
		return hang >= 0 && hang < 10 && cot >= 0 && cot < 9;
	}

	public boolean viTriTrong(int hang, int cot) {
		return layQuanCo(hang, cot) == null;
	}

	public boolean coQuanDoiThu(int hang, int cot, boolean laMauTrang) {
		QuanCo quan = layQuanCo(hang, cot);
		return quan != null && quan.laMauTrang() != laMauTrang;
	}

	public boolean diChuyenQuanCo(int hangCu, int cotCu, int hangMoi, int cotMoi) {
		QuanCo quanCo = layQuanCo(hangCu, cotCu);
		if (quanCo != null) {
			List<int[]> nuocDiHopLe = quanCo.layNuocDiHopLe(this);
			for (int[] nuoc : nuocDiHopLe) {
				if (nuoc[0] == hangMoi && nuoc[1] == cotMoi) {
					// Cập nhật vị trí quân cờ
					viTri[hangMoi][cotMoi] = quanCo;
					viTri[hangCu][cotCu] = null;
					quanCo.capNhatViTri(hangMoi, cotMoi);

					lichSuNuocDi.add(quanCo.getTen() + " từ (" + hangCu + ", " + cotCu + ") đến (" + hangMoi + ", "
							+ cotMoi + ")");
					inBanCo();
					return true;
				}
			}
		}
		System.out.println("Vị trí quân cần đến không hợp lệ.");
		return false;
	}

	public void inBanCo() {
		System.out.println("Trạng thái bàn cờ hiện tại:");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				if (viTri[i][j] != null) {
					String tenQuanCo = viTri[i][j].getTen();
					String kyTuTrungQuoc;

					// Phân biệt giữa quân trắng và quân đen
					if (viTri[i][j].laMauTrang()) {
						switch (tenQuanCo) {
						case "QuanTuong": // Tướng trắng
							kyTuTrungQuoc = "帥";
							break;
						case "Si": // Sĩ trắng
							kyTuTrungQuoc = "仕";
							break;
						case "Tuong": // Tượng trắng
							kyTuTrungQuoc = "相";
							break;
						case "Ma": // Mã trắng
							kyTuTrungQuoc = "傌";
							break;
						case "Xe": // Xe trắng
							kyTuTrungQuoc = "俥  ";
							break;
						case "Phao": // Pháo trắng
							kyTuTrungQuoc = "炮 ";
							break;
						case "Tot": // Tốt trắng
							kyTuTrungQuoc = "兵 ";
							break;
						default:
							kyTuTrungQuoc = ". ";
							break;
						}
					} else {
						switch (tenQuanCo) {
						case "QuanTuong": // Tướng đen
							kyTuTrungQuoc = "將";
							break;
						case "Si": // Sĩ đen
							kyTuTrungQuoc = "士";
							break;
						case "Tuong": // Tượng đen
							kyTuTrungQuoc = "象";
							break;
						case "Ma": // Mã đen
							kyTuTrungQuoc = "馬";
							break;
						case "Xe": // Xe đen
							kyTuTrungQuoc = "車  ";
							break;
						case "Phao": // Pháo đen
							kyTuTrungQuoc = "砲 ";
							break;
						case "Tot": // Tốt đen
							kyTuTrungQuoc = "卒 ";
							break;
						default:
							kyTuTrungQuoc = ".";
							break;
						}
					}
					// Tướng (King): Tướng hoặc Soái, thường được ký hiệu là 帥 (Soái) hoặc 將
					// (Tướng), tùy theo phe Đỏ hoặc Đen.

					// Sĩ (Guard/Advisor): Sĩ, thường ký hiệu là 仕 (Sĩ) cho Đỏ và 士 (Sĩ) cho Đen. Sĩ
					// chỉ di chuyển trong cung và bảo vệ Tướng.

					// Tượng (Elephant/Bishop): Tượng, ký hiệu là 相 (Tướng) cho Đỏ và 象 (Tượng) cho
					// Đen. Tượng di chuyển theo đường chéo và không được qua sông.

					// Mã (Horse/Knight): Mã, ký hiệu là 傌 (Mã) cho Đỏ và 馬 (Mã) cho Đen, có thể di
					// chuyển hình chữ "L" như trong cờ vua.

					// Xe (Rook/Chariot): Xe, ký hiệu là 俥 (Xe) cho Đỏ và 車 (Xe) cho Đen, di chuyển
					// ngang dọc theo các hàng cột.

					// Pháo (Cannon): Pháo, ký hiệu là 炮 (Pháo) cho Đỏ và 砲 (Pháo) cho Đen, có thể
					// nhảy qua một quân khi ăn.

					// Tốt (Pawn/Soldier): Tốt, ký hiệu là 兵 (Binh) cho Đỏ và 卒 (Tốt) cho Đen, di
					// chuyển từng bước và không lùi sau khi qua sông.

					System.out.printf("%-2s", kyTuTrungQuoc); // Căn trái với độ rộng 3 ký tự
				} else {
					System.out.printf("%-3s", "."); // Căn trái dấu chấm
				}
			}
			System.out.println(); // Xuống dòng sau mỗi hàng
		}
		System.out.println();
	}

	public void inLichSuNuocDi() {
		System.out.println("Lịch sử các nước đi:");
		for (String nuocDi : lichSuNuocDi) {
			System.out.println(nuocDi);
		}
	}

	public String kiemTraThangCuoc() {
		boolean coTuongTrang = false;
		boolean coTuongDen = false;

		// Duyệt qua bàn cờ để kiểm tra xem mỗi Tướng còn tồn tại hay không
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				QuanCo quanCo = viTri[i][j];
				if (quanCo instanceof QuanTuong) {
					if (quanCo.laMauTrang()) {
						coTuongTrang = true;
					} else {
						coTuongDen = true;
					}
				}
			}
		}

		// Kiểm tra kết quả dựa trên sự tồn tại của các Tướng
		if (!coTuongTrang) {
			return "Bên đen thắng!";
		} else if (!coTuongDen) {
			return "Bên trắng thắng!";
		}

		// Nếu cả hai Tướng đều còn, không có bên nào thắng
		return null;
	}

}
