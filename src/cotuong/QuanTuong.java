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
					nuocDi.add(new int[] { hangMoi, cotMoi });
				}
			}
		}

		return nuocDi;
	}
}
