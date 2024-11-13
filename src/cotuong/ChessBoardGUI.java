package cotuong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChessBoardGUI extends JFrame {
	private static long v1;
	/**
	 * 
	 */
	private static final long serialVersionUID = v1;
	private BanCo banCo;
	private boolean laLuotTrang = true; // Quản lý lượt chơi
	private JButton[][] buttons = new JButton[10][9];
	private QuanCo quanCoDaChon = null; // Quân cờ hiện đang chọn

	public ChessBoardGUI() {
		banCo = new BanCo();
		initUI();
	}

	private void initUI() {
		setTitle("Cờ Tướng");
		setSize(1000, 700); // Tăng kích thước cửa sổ để có không gian cho bảng luật
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Panel chứa bàn cờ
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(10, 9));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				JButton btn = new JButton();
				btn.setFont(new Font("Serif", Font.BOLD, 20));
				btn.setBackground(Color.WHITE);
				btn.setOpaque(true);

				int hang = i;
				int cot = j;
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						handleButtonClick(hang, cot);
					}
				});

				buttons[i][j] = btn;
				boardPanel.add(btn);
			}
		}

		// Cập nhật bàn cờ ban đầu
		capNhatBanCo(banCo);

		// Panel chứa bảng luật chơi
		JPanel rulesPanel = new JPanel();
		rulesPanel.setLayout(new BorderLayout());

		// Tạo một JTextArea để hiển thị các quy tắc
		JTextArea rulesTextArea = new JTextArea();
		rulesTextArea.setEditable(false); // Không cho phép chỉnh sửa
		rulesTextArea.setFont(new Font("Serif", Font.PLAIN, 14));

		// Các quy tắc trò chơi
		String rules = "Luật chơi Cờ Tướng:\n" + "- Quân Tướng đi 1 ô mỗi lượt, trong phạm vi cung điện của mình.\n"
				+ "- Các quân khác (Xe, Mã, Tượng, Sĩ, Pháo, Tốt) có cách di chuyển riêng.\n"
				+ "- Quân cờ phải tuân theo luật di chuyển và không được đi qua quân của mình.\n"
				+ "- Cờ bị chiếu nếu Tướng đối phương có thể tấn công Tướng của mình.\n" + "\n" + "Cơ chế trò chơi:\n"
				+ "- Không cho phép người chơi di chuyển làm bản thân bị chiếu\n"
				+ "- Không cho phép người chơi thể để hai Tướng đối mặt nhau\n"
				+ "- Trò chơi kết thúc khi một Tướng bị chiếu bí.\n";

		rulesTextArea.setText(rules);
		JScrollPane scrollPane = new JScrollPane(rulesTextArea);
		rulesPanel.add(scrollPane, BorderLayout.CENTER);

		// Thêm panel bàn cờ và panel luật chơi vào cửa sổ
		add(boardPanel, BorderLayout.CENTER);
		add(rulesPanel, BorderLayout.EAST); // Đặt bảng luật chơi bên phải bàn cờ

		setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
	}

	private void handleButtonClick(int hang, int cot) {
		QuanCo quanCo = banCo.layQuanCo(hang, cot);

		// Nếu đã chọn một quân cờ
		if (quanCoDaChon != null) {
			// Nếu người chơi chọn một quân cờ khác cùng màu
			if (quanCo != null && quanCo.laMauTrang() == laLuotTrang && quanCo != quanCoDaChon) {
				quanCoDaChon = quanCo; // Cập nhật quân cờ đã chọn
				xoaDanhDauNuocDi();
				List<int[]> nuocDiHopLe = quanCo.layNuocDiHopLe(banCo);
				danhDauNuocDiHopLe(nuocDiHopLe); // Đánh dấu nước đi hợp lệ mới
			}
			// Nếu chọn ô hợp lệ để di chuyển
			else if (quanCoDaChon.layNuocDiHopLe(banCo).stream().anyMatch(nuoc -> nuoc[0] == hang && nuoc[1] == cot)) {
				banCo.diChuyenQuanCo(quanCoDaChon.layHang(), quanCoDaChon.layCot(), hang, cot);
				laLuotTrang = !laLuotTrang; // Chuyển lượt chơi
				quanCoDaChon = null; // Hủy chọn quân cờ sau khi di chuyển
				xoaDanhDauNuocDi();
				capNhatBanCo(banCo);
				banCo.inLichSuNuocDi();
			}
		}
		// Nếu chưa chọn quân cờ nào và chọn quân đúng màu
		else if (quanCo != null && quanCo.laMauTrang() == laLuotTrang) {
			List<int[]> nuocDiHopLe = quanCo.layNuocDiHopLe(banCo);

			// Kiểm tra nếu có ít nhất một nước đi hợp lệ
			if (!nuocDiHopLe.isEmpty()) {
				quanCoDaChon = quanCo;
				danhDauNuocDiHopLe(nuocDiHopLe);
			}
		} else {
			quanCoDaChon = null;
			xoaDanhDauNuocDi();
		}

		// Kiểm tra chiến thắng
		String ketQua = banCo.kiemTraThangCuoc();
		if (!ketQua.equals("Chưa kết thúc!")) {
			ketQua = laLuotTrang ? "Đen thắng!" : "Trắng thắng!";
			System.out.println("Trò Chơi Kết Thúc " + ketQua);
			JOptionPane.showMessageDialog(this, ketQua, "Kết thúc trò chơi", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0); // Dừng trò chơi
		}

		// Kiểm tra chiếu bí sau khi di chuyển
		if (banCo.kiemTraChieuBi(!laLuotTrang)) {
			ketQua = laLuotTrang ? "Đen thắng!" : "Trắng thắng!";
			ketQua = laLuotTrang ? "Đen thắng!" : "Trắng thắng!";
			System.out.println("Trò Chơi Kết Thúc " + ketQua);
			JOptionPane.showMessageDialog(this, "Chiếu bí! " + ketQua, "Kết thúc trò chơi",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	private void danhDauNuocDiHopLe(List<int[]> nuocDiHopLe) {
		xoaDanhDauNuocDi(); // Xóa các đánh dấu cũ

		for (int[] nuoc : nuocDiHopLe) {
			JButton btn = buttons[nuoc[0]][nuoc[1]];

			// Lưu màu nền cũ và màu chữ của quân cờ trước khi thay đổi nền
			Color oldBackground = btn.getBackground();
			Color oldForeground = btn.getForeground();

			// Đánh dấu ô hợp lệ bằng màu vàng
			btn.setBackground(Color.YELLOW);

			// Khôi phục màu chữ cũ sau khi thay đổi màu nền
			btn.setForeground(oldForeground);

			// Khôi phục lại màu nền cũ nếu nó là một quân cờ
			if (oldBackground != Color.WHITE) {
				btn.setBackground(oldBackground);
			}
		}
	}

	private void xoaDanhDauNuocDi() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				JButton btn = buttons[i][j];

				// Nếu ô có quân cờ, khôi phục lại màu nền ban đầu và màu chữ
				QuanCo quanCo = banCo.layQuanCo(i, j);
				if (quanCo != null) {
					if (quanCo.laMauTrang()) {
						btn.setBackground(Color.RED); // Màu nền quân trắng
						btn.setForeground(Color.BLACK); // Màu chữ quân trắng
					} else {
						btn.setBackground(Color.GREEN); // Màu nền quân đen
						btn.setForeground(Color.BLACK); // Màu chữ quân đen
					}
				} else {
					btn.setBackground(Color.WHITE); // Ô trống
					btn.setText(""); // Xóa chữ
				}
			}
		}
	}

	public void capNhatBanCo(BanCo banCo) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				QuanCo quanCo = banCo.layQuanCo(i, j);
				if (quanCo != null) {
					buttons[i][j].setText(quanCo.getKyTuTrungQuoc()); // Sử dụng ký tự Trung Quốc

					// Tô màu nền theo màu quân cờ
					if (quanCo.laMauTrang()) {
						buttons[i][j].setBackground(Color.RED); // Tô màu đỏ cho quân trắng
					} else {
						buttons[i][j].setBackground(Color.GREEN); // Tô màu xanh lá cho quân đen
						buttons[i][j].setForeground(Color.BLACK); // Chữ màu đen cho quân đen
					}
				} else {
					buttons[i][j].setText(""); // Nếu ô trống
					buttons[i][j].setBackground(Color.WHITE); // Tô màu trắng cho ô trống
				}
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			ChessBoardGUI gui = new ChessBoardGUI();
			gui.setVisible(true);
		});
	}
}
