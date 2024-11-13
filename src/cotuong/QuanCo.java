package cotuong;

import java.util.Iterator;
import java.util.List;

public abstract class QuanCo {
	protected int hang, cot; // vị trí hàng và cột trên bàn cờ
	protected boolean laMauTrang; // true nếu quân cờ là màu trắng, false nếu là màu đen

	public QuanCo(int hang, int cot, boolean laMauTrang) {
		this.hang = hang;
		this.cot = cot;
		this.laMauTrang = laMauTrang;
	}

	// Phương thức trừu tượng lấy các nước đi hợp lệ
	public abstract List<int[]> layNuocDiHopLe(BanCo banCo);

	public boolean laMauTrang() {
		return laMauTrang;
	}

	public int layHang() {
		return hang;
	}

	public int layCot() {
		return cot;
	}

	public void capNhatViTri(int hang, int cot) {
		this.hang = hang;
		this.cot = cot;
	}

	public String getTen() {
		return this.getClass().getSimpleName();
	}

	protected abstract String getKyTuTrungQuoc();

	public void loaiBoNuocDiGayChieu(List<int[]> nuocDi, BanCo banCo) {
		Iterator<int[]> iterator = nuocDi.iterator();
		while (iterator.hasNext()) {
			int[] nuoc = iterator.next();
			int hangMoi = nuoc[0];
			int cotMoi = nuoc[1];

			// Kiểm tra nước đi có hợp lệ mà không gây chiếu
			if (!banCo.diChuyenTamThoi(this, hangMoi, cotMoi)) {
				iterator.remove(); // Xóa nước đi nếu làm tướng bị chiếu
			}
		}
	}

}
