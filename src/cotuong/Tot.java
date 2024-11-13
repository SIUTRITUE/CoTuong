package cotuong;

import java.util.ArrayList;
import java.util.List;

public class Tot extends QuanCo {

	public Tot(int hang, int cot, boolean laMauTrang) {
		super(hang, cot, laMauTrang);
	}

	public String getKyTuTrungQuoc() {
		return laMauTrang ? "兵" : "卒"; // Tốt trắng và Tốt đen
	}

	@Override
	public List<int[]> layNuocDiHopLe(BanCo banCo) {
		List<int[]> nuocDi = new ArrayList<>();

		int buoc = laMauTrang ? 1 : -1;
		int hangMoi = hang + buoc;

		if (banCo.viTriHopLe(hangMoi, cot)
				&& (banCo.viTriTrong(hangMoi, cot) || banCo.coQuanDoiThu(hangMoi, cot, laMauTrang))) {
			nuocDi.add(new int[] { hangMoi, cot });
		}

		if ((laMauTrang && hang >= 5) || (!laMauTrang && hang <= 4)) {
			for (int[] huong : new int[][] { { 0, 1 }, { 0, -1 } }) {
				int cotMoi = cot + huong[1];
				if (banCo.viTriHopLe(hang, cotMoi)
						&& (banCo.viTriTrong(hang, cotMoi) || banCo.coQuanDoiThu(hang, cotMoi, laMauTrang))) {
					nuocDi.add(new int[] { hang, cotMoi });
				}
			}
		}
		// Kiểm tra các nước đi hợp lệ, loại bỏ các nước đi làm Tướng bị chiếu
		loaiBoNuocDiGayChieu(nuocDi, banCo);

		return nuocDi;
	}
}
