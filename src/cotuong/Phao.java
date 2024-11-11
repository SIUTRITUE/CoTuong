package cotuong;

import java.util.ArrayList;
import java.util.List;

public class Phao extends QuanCo {

	public Phao(int hang, int cot, boolean laMauTrang) {
		super(hang, cot, laMauTrang);
	}

	@Override
	public List<int[]> layNuocDiHopLe(BanCo banCo) {
		List<int[]> nuocDi = new ArrayList<>();

		// Di chuyển theo hàng và cột
		for (int[] huong : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }) {
			boolean nhayQua = false;

			for (int i = 1;; i++) {
				int hangMoi = hang + huong[0] * i;
				int cotMoi = cot + huong[1] * i;

				if (!banCo.viTriHopLe(hangMoi, cotMoi))
					break;

				if (banCo.viTriTrong(hangMoi, cotMoi)) {
					if (!nhayQua)
						// Thêm kiểm tra seBiChieuSauKhiDiChuyen
						if (!banCo.seBiChieuSauKhiDiChuyen(this, hangMoi, cotMoi)) {
							nuocDi.add(new int[] { hangMoi, cotMoi });
						}
				} else {
					if (nhayQua && banCo.coQuanDoiThu(hangMoi, cotMoi, laMauTrang)) {
						// Thêm kiểm tra seBiChieuSauKhiDiChuyen
						if (!banCo.seBiChieuSauKhiDiChuyen(this, hangMoi, cotMoi)) {
							nuocDi.add(new int[] { hangMoi, cotMoi });
						}
						break;
					}
					nhayQua = true;
				}
			}
		}

		return nuocDi;
	}
}
