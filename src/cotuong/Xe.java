package cotuong;

import java.util.ArrayList;
import java.util.List;

public class Xe extends QuanCo {

	public Xe(int hang, int cot, boolean laMauTrang) {
		super(hang, cot, laMauTrang);
	}

	@Override
	public List<int[]> layNuocDiHopLe(BanCo banCo) {
		List<int[]> nuocDi = new ArrayList<>();

		// Di chuyển theo hàng
		for (int i = hang + 1; i < 10; i++) { // Xuống dưới
			if (banCo.viTriTrong(i, cot)) {
				// Thêm kiểm tra seBiChieuSauKhiDiChuyen
				if (!banCo.seBiChieuSauKhiDiChuyen(this, i, cot)) {
					nuocDi.add(new int[] { i, cot });
				}
			} else {
				if (banCo.coQuanDoiThu(i, cot, laMauTrang)) {
					if (!banCo.seBiChieuSauKhiDiChuyen(this, i, cot)) {
						nuocDi.add(new int[] { i, cot });
					}
				}
				break; // Dừng lại khi gặp quân cờ
			}
		}

		for (int i = hang - 1; i >= 0; i--) { // Lên trên
			if (banCo.viTriTrong(i, cot)) {
				if (!banCo.seBiChieuSauKhiDiChuyen(this, i, cot)) {
					nuocDi.add(new int[] { i, cot });
				}
			} else {
				if (banCo.coQuanDoiThu(i, cot, laMauTrang)) {
					if (!banCo.seBiChieuSauKhiDiChuyen(this, i, cot)) {
						nuocDi.add(new int[] { i, cot });
					}
				}
				break; // Dừng lại khi gặp quân cờ
			}
		}

		// Di chuyển theo cột
		for (int j = cot + 1; j < 9; j++) { // Sang phải
			if (banCo.viTriTrong(hang, j)) {
				if (!banCo.seBiChieuSauKhiDiChuyen(this, hang, j)) {
					nuocDi.add(new int[] { hang, j });
				}
			} else {
				if (banCo.coQuanDoiThu(hang, j, laMauTrang)) {
					if (!banCo.seBiChieuSauKhiDiChuyen(this, hang, j)) {
						nuocDi.add(new int[] { hang, j });
					}
				}
				break; // Dừng lại khi gặp quân cờ
			}
		}

		for (int j = cot - 1; j >= 0; j--) { // Sang trái
			if (banCo.viTriTrong(hang, j)) {
				if (!banCo.seBiChieuSauKhiDiChuyen(this, hang, j)) {
					nuocDi.add(new int[] { hang, j });
				}
			} else {
				if (banCo.coQuanDoiThu(hang, j, laMauTrang)) {
					if (!banCo.seBiChieuSauKhiDiChuyen(this, hang, j)) {
						nuocDi.add(new int[] { hang, j });
					}
				}
				break; // Dừng lại khi gặp quân cờ
			}
		}

		return nuocDi;
	}
}
