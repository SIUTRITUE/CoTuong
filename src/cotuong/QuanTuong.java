package cotuong;

import java.util.ArrayList;
import java.util.List;

public class QuanTuong extends QuanCo {

	public QuanTuong(int hang, int cot, boolean laMauTrang) {
		super(hang, cot, laMauTrang);
	}

	@Override
	public String getKyTuTrungQuoc() {
		return laMauTrang ? "帥" : "將"; // Tướng trắng (帥) và Tướng đen (將)
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

		// Thêm nước đi cho quân tướng đối thủ nếu nằm trên cùng một cột và không bị
		// chặn
		int cotDoiThu = cot; // Cùng cột với quân tướng hiện tại
		int hangDoiThu = laMauTrang ? 9 : 0; // Vị trí của quân tướng đối thủ

		// Kiểm tra xem có quân tướng đối thủ trên cùng cột không
		if (banCo.coQuanDoiThu(hangDoiThu, cotDoiThu, laMauTrang)) {
			boolean biChan = false;
			int buoc = hang < hangDoiThu ? 1 : -1; // Xác định hướng (lên hay xuống)

			// Kiểm tra xem đường đi đến quân tướng đối thủ có bị chặn không
			for (int i = hang + buoc; i != hangDoiThu; i += buoc) {
				if (!banCo.viTriTrong(i, cot)) {
					biChan = true;
					break;
				}
			}

			// Nếu không bị chặn, thêm quân tướng đối thủ vào nước đi hợp lệ
			if (!biChan) {
				nuocDi.add(new int[] { hangDoiThu, cotDoiThu });
			}
		}
		loaiBoNuocDiGayChieu(nuocDi, banCo);
		return nuocDi;
	}
}
