package cotuong;

import java.util.ArrayList;
import java.util.List;

public class Tuong extends QuanCo {

	public Tuong(int hang, int cot, boolean laMauTrang) {
		super(hang, cot, laMauTrang);
	}

	@Override
	public List<int[]> layNuocDiHopLe(BanCo banCo) {
		List<int[]> nuocDi = new ArrayList<>();
		int[][] huongDi = { { 2, 2 }, { 2, -2 }, { -2, 2 }, { -2, -2 } };
		int hangMin = laMauTrang ? 0 : 5, hangMax = laMauTrang ? 4 : 9;

		for (int[] huong : huongDi) {
			int hangMoi = hang + huong[0];
			int cotMoi = cot + huong[1];

			if (banCo.viTriHopLe(hangMoi, cotMoi) && hangMoi >= hangMin && hangMoi <= hangMax
					&& banCo.viTriTrong(hang + huong[0] / 2, cot + huong[1] / 2)) {
				if (banCo.viTriTrong(hangMoi, cotMoi) || banCo.coQuanDoiThu(hangMoi, cotMoi, laMauTrang)) {
					// Thêm kiểm tra seBiChieuSauKhiDiChuyen
					if (!banCo.seBiChieuSauKhiDiChuyen(this, hangMoi, cotMoi)) {
						nuocDi.add(new int[] { hangMoi, cotMoi });
					}
				}
			}
		}

		return nuocDi;
	}
}
