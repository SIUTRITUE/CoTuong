package cotuong;

import java.util.ArrayList;
import java.util.List;

public class Ma extends QuanCo {

	public Ma(int hang, int cot, boolean laMauTrang) {
		super(hang, cot, laMauTrang);
	}

	public String getKyTuTrungQuoc() {
		return laMauTrang ? "傌" : "馬"; // Mã trắng và Mã đen
	}

	@Override
	public List<int[]> layNuocDiHopLe(BanCo banCo) {
		List<int[]> nuocDi = new ArrayList<>();
		int[][] huongDi = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 }, { 2, 1 } };

		for (int[] huong : huongDi) {
			int hangMoi = hang + huong[0];
			int cotMoi = cot + huong[1];

			// Kiểm tra nước đi hợp lệ và không bị cản
			if (banCo.viTriHopLe(hangMoi, cotMoi) && banCo.viTriTrong(hang + huong[0] / 2, cot + huong[1] / 2)) {
				if (banCo.viTriTrong(hangMoi, cotMoi) || banCo.coQuanDoiThu(hangMoi, cotMoi, laMauTrang)) {
					// Thêm kiểm tra seBiChieuSauKhiDiChuyen
					if (!banCo.seBiChieuSauKhiDiChuyen(this, hangMoi, cotMoi)) {
						nuocDi.add(new int[] { hangMoi, cotMoi });
					}
				}
			}
		}
		loaiBoNuocDiGayChieu(nuocDi, banCo);

		return nuocDi;
	}
}
