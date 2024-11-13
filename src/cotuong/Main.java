package cotuong;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		BanCo banCo = new BanCo();
		Scanner scanner = new Scanner(System.in);
		boolean laLuotTrang = true; // true nếu lượt trắng, false nếu lượt đen
		boolean troChoiKetThuc = false;

		System.out.println("Trò chơi Cờ Tướng bắt đầu!");

		while (!troChoiKetThuc) {
			String mauQuanCo = laLuotTrang ? "trắng" : "đen";
			System.out.println("\nLượt của người chơi " + mauQuanCo);

			banCo.inBanCo();
			System.out.print("Nhập hàng và cột của quân cờ muốn chọn (ví dụ: 0 0 (xe trắng)): ");
			int hangChon = scanner.nextInt();
			int cotChon = scanner.nextInt();

			QuanCo quanCoChon = banCo.layQuanCo(hangChon, cotChon);
			if (quanCoChon == null || quanCoChon.laMauTrang() != laLuotTrang) {
				System.out.println("Không có quân cờ hợp lệ tại vị trí này hoặc không phải quân của bạn!");
				continue;
			}

			List<int[]> nuocDiHopLe = quanCoChon.layNuocDiHopLe(banCo);
			if (nuocDiHopLe.isEmpty()) {
				System.out.println("Quân " + quanCoChon.getClass().getSimpleName() + " không có nước đi hợp lệ.");
				continue;
			}

			System.out.println("Các vị trí có thể đi:");
			for (int[] nuocDi : nuocDiHopLe) {
				System.out.print("(" + nuocDi[0] + "," + nuocDi[1] + ") ");
			}
			System.out.println();

			System.out.print("Nhập hàng và cột của vị trí muốn đi đến: ");
			int hangMoi = scanner.nextInt();
			int cotMoi = scanner.nextInt();

			// Kiểm tra nước đi có hợp lệ không
			boolean nuocDiHopLeFlag = false;
			for (int[] nuocDi : nuocDiHopLe) {
				if (nuocDi[0] == hangMoi && nuocDi[1] == cotMoi) {
					nuocDiHopLeFlag = true;
					break;
				}
			}

			if (!nuocDiHopLeFlag) {
				System.out.println("Vị trí quân " + quanCoChon.getClass().getSimpleName() + " cần đến không hợp lệ.");
				continue;
			}

			// Di chuyển quân cờ
			if (banCo.diChuyenQuanCo(hangChon, cotChon, hangMoi, cotMoi)) {
				// Chuyển lượt chơi
				laLuotTrang = !laLuotTrang;
			}

			banCo.inLichSuNuocDi();

			// Kiểm tra kết thúc trò chơi
			String ketQua = banCo.kiemTraThangCuoc();
			if (!ketQua.equals("Chưa kết thúc!")) {
				ketQua = laLuotTrang ? "Đen thắng!" : "Trắng thắng!";
				System.out.println("Trò Chơi Kết Thúc " + ketQua);
				troChoiKetThuc = true;
			}
			// Kiểm tra chiếu bí sau khi di chuyển
			if (banCo.kiemTraChieuBi(!laLuotTrang)) {
				ketQua = laLuotTrang ? "Đen thắng!" : "Trắng thắng!";
				System.out.println("Trò Chơi Kết Thúc " + ketQua);
				troChoiKetThuc = true;
			}
		}
		scanner.close();
	}
}
