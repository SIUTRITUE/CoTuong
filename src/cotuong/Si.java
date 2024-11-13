package cotuong;

import java.util.ArrayList;
import java.util.List;

public class Si extends QuanCo {

	public Si(int hang, int cot, boolean laMauTrang) {
		super(hang, cot, laMauTrang);
	}

	public String getKyTuTrungQuoc() {
		return laMauTrang ? "仕" : "士"; // Sĩ trắng và Sĩ đen
	}

	@Override
	public List<int[]> layNuocDiHopLe(BanCo banCo) {
		List<int[]> nuocDi = new ArrayList<>();
		int[][] huongDi = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };
		int hangMin = laMauTrang ? 0 : 7, hangMax = laMauTrang ? 2 : 9;

		for (int[] huong : huongDi) {
			int hangMoi = hang + huong[0];
			int cotMoi = cot + huong[1];

			if (banCo.viTriHopLe(hangMoi, cotMoi) && hangMoi >= hangMin && hangMoi <= hangMax && cotMoi >= 3
					&& cotMoi <= 5) {
				if (banCo.viTriTrong(hangMoi, cotMoi) || banCo.coQuanDoiThu(hangMoi, cotMoi, laMauTrang)) {
					nuocDi.add(new int[] { hangMoi, cotMoi });
				}
			}
		}
		// Kiểm tra các nước đi hợp lệ, loại bỏ các nước đi làm Tướng bị chiếu
		loaiBoNuocDiGayChieu(nuocDi, banCo);
		return nuocDi;
	}

}
