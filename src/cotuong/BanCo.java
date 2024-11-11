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

				if (viTri[i][j] != null) {
					String tenQuanCo = viTri[i][j].getTen();

					if (viTri[i][j].laMauTrang()) {
						switch (tenQuanCo) {
							case "QuanTuong":
								kyTuTrungQuoc = "  帥  ";
								break;
							case "Si":
								kyTuTrungQuoc = "  仕  ";
								break;
							case "Tuong":
								kyTuTrungQuoc = "  相  ";
								break;
							case "Ma":
								kyTuTrungQuoc = "  傌  ";
								break;
							case "Xe":
								kyTuTrungQuoc = "  俥  ";
								break;
							case "Phao":
								kyTuTrungQuoc = "  炮  ";
								break;
							case "Tot":
								kyTuTrungQuoc = "  兵  ";
								break;
						}
					} else {
						switch (tenQuanCo) {
							case "QuanTuong":
								kyTuTrungQuoc = "  將  ";
								break;
							case "Si":
								kyTuTrungQuoc = "  士  ";
								break;
							case "Tuong":
								kyTuTrungQuoc = "  象  ";
								break;
							case "Ma":
								kyTuTrungQuoc = "  馬  ";
								break;
							case "Xe":
								kyTuTrungQuoc = "  車  ";
								break;
							case "Phao":
								kyTuTrungQuoc = "  砲  ";
								break;
							case "Tot":
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

	// Trong BanCo.java
	public boolean kiemTraChieuBi(boolean laMauTrang) {
		QuanTuong tuong = timQuanTuong(laMauTrang);
		if (tuong == null) {
			return false;
		}

		List<int[]> nuocDiHopLe = tuong.layNuocDiHopLe(this);
		if (nuocDiHopLe.isEmpty()) {
			return true; // Không có nước đi nào để thoát chiếu, tức là chiếu bí
		}

		return false;
	}

	// Hàm tìm tướng
	public QuanTuong timQuanTuong(boolean laMauTrang) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				QuanCo quanCo = viTri[i][j];
				if (quanCo instanceof QuanTuong && quanCo.laMauTrang() == laMauTrang) {
					return (QuanTuong) quanCo;
				}
			}
		}
		return null;
	}

	public boolean seBiChieuSauKhiDiChuyen(QuanCo quanCo, int hangMoi, int cotMoi) {
		// Lưu vị trí và trạng thái ban đầu
		int hangCu = quanCo.hang;
		int cotCu = quanCo.cot;
		QuanCo quanCoDich = viTri[hangMoi][cotMoi];

		// Thực hiện giả lập nước đi
		viTri[hangMoi][cotMoi] = quanCo;
		viTri[hangCu][cotCu] = null;
		quanCo.capNhatViTri(hangMoi, cotMoi);

		// Kiểm tra nếu tướng bị chiếu
		boolean biChieu = kiemTraChieuBi(quanCo.laMauTrang());

		// Hoàn tác nước đi
		viTri[hangCu][cotCu] = quanCo;
		viTri[hangMoi][cotMoi] = quanCoDich;
		quanCo.capNhatViTri(hangCu, cotCu);

		return biChieu;
	}

}
