package cotuong;

import java.util.ArrayList;
import java.util.List;

public class QuanTuong extends QuanCo {

	public QuanTuong(int hang, int cot, boolean laMauTrang) {
		super(hang, cot, laMauTrang);
	}

	@Override
	public List<int[]> layNuocDiHopLe(BanCo banCo) {
		List<int[]> nuocDi = new ArrayList<>();
		int[][] huongDi = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		int hangMin = laMauTrang ? 0 : 7, hangMax = laMauTrang ? 2 : 9;

		for (int[] huong : huongDi) {
			int hangMoi = hang + huong[0];
			int cotMoi = cot + huong[1];

			if (banCo.viTriHopLe(hangMoi, cotMoi) && hangMoi >= hangMin && hangMoi <= hangMax && cotMoi >= 3
					&& cotMoi <= 5) {
				if (banCo.viTriTrong(hangMoi, cotMoi) || banCo.coQuanDoiThu(hangMoi, cotMoi, laMauTrang)) {
					// Giả lập nước đi để kiểm tra nếu nó không bị chiếu
					if (!banCo.seBiChieuSauKhiDiChuyen(this, hangMoi, cotMoi)) {
						nuocDi.add(new int[] { hangMoi, cotMoi });
					}
				}
			}
		}
		return nuocDi;
	}

	public boolean dangBiChieu(BanCo banCo) {
		// Duyệt qua tất cả các quân cờ đối thủ để xem có quân nào có thể ăn tướng không
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				QuanCo quanCo = banCo.layQuanCo(i, j);
				if (quanCo != null && quanCo.laMauTrang() != this.laMauTrang()) {
					List<int[]> nuocDi = quanCo.layNuocDiHopLe(banCo);
					for (int[] nuoc : nuocDi) {
						if (nuoc[0] == this.hang && nuoc[1] == this.cot) {
							return true; // Tướng đang bị chiếu
						}
					}
				}
			}
		}
		return false; // Tướng không bị chiếu
	}
}
