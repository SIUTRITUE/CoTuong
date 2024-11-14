package cotuong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainChessBoardGUI extends JFrame {
	private static final long serialVersionUID = 1;
	private Board board;
	private boolean white = true;
	private JButton[][] buttons = new JButton[10][9];
	private ChessPieces selectChessPieces = null;

	public MainChessBoardGUI() {
		board = new Board();
		initUI();
	}

	private void initUI() {
		setTitle("Cờ Tướng");
		setSize(1000, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Panel chứa bàn cờ
		JPanel boardPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Image img = new ImageIcon("src/cotuong/banco.png").getImage();
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		};
		boardPanel.setLayout(new GridLayout(10, 9));
		System.out.print("chọn một quân cờ (ví dụ: 0 0 (xe trắng)): ");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				JButton btn = createRoundButton(); // Đổi chỗ tạo nút theo cách mới
				btn.setFont(new Font("Serif", Font.BOLD, 20));
				btn.setBackground(Color.WHITE);
				btn.setOpaque(false);

				int row = i;
				int col = j;
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ChessPieces selectPieces = board.getChessPieces(row, col);
						if (selectPieces != null) {
							if (selectPieces.isWhite == white) {
								List<int[]> moveTrue = selectPieces.getMoveTrue(board);
								if (moveTrue.isEmpty()) {
									System.out.println("Quân " + selectPieces.getClass().getSimpleName()
											+ " không có nước đi hợp lệ.");
								}

								System.out.println("Các vị trí có thể đi:");
								for (int[] nuocDi : moveTrue) {
									System.out.print("(" + nuocDi[0] + "," + nuocDi[1] + ") ");
								}
								System.out.println();
							}
						}
						handleButtonClick(row, col);
					}
				});

				buttons[i][j] = btn;
				boardPanel.add(btn);
			}
		}

		capNhatBanCo(board);

		JPanel rulesPanel = new JPanel();
		rulesPanel.setLayout(new BorderLayout());

		JTextArea rulesTextArea = new JTextArea();
		rulesTextArea.setEditable(false);
		rulesTextArea.setFont(new Font("Serif", Font.PLAIN, 20));

		String rules = "Luật chơi Cờ Tướng:\n" + "- Trắng đi trước khai cuôc. \n" + "- Quân Tướng đi 1 ô mỗi lượt,\n"
				+ " trong phạm vi cung điện của mình.\n" + "- Hai Tướng không đối mặt nhau\n"
				+ "- Các quân khác (Xe, Mã, Tượng, Sĩ,\n" + " Pháo, Tốt) có cách di chuyển riêng.\n"
				+ "- Quân cờ phải tuân theo luật di chuyển\n" + " và không được đi qua quân của mình.\n"
				+ "- Cờ bị chiếu nếu Tướng khi \n" + "đối phương có thể tấn công Tướng của mình.\n" + "\n"
				+ "Cơ chế trò chơi:\n" + "- Di chuyển theo luật của từng quân.\n"
				+ "- Không cho phép di chuyển làm bản thân bị chiếu\n"
				+ "- Trò chơi kết thúc khi một Tướng bị chiếu bí.\n";

		rulesTextArea.setText(rules);
		JScrollPane scrollPane = new JScrollPane(rulesTextArea);
		rulesPanel.add(scrollPane, BorderLayout.CENTER);

		add(boardPanel, BorderLayout.CENTER);
		add(rulesPanel, BorderLayout.EAST);
		setLocationRelativeTo(null);
	}

	private JButton createRoundButton() {
		JButton button = new JButton() {
			@Override
			protected void paintComponent(Graphics g) {
				if (getModel().isArmed()) {
					g.setColor(Color.LIGHT_GRAY);
				} else {
					g.setColor(getBackground());
				}
				g.fillOval(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}

			@Override
			protected void paintBorder(Graphics g) {
				// Lấy quân cờ tại vị trí tọa độ của nút
				ChessPieces chessPieces = board.getChessPieces(getYCoordinate(), getXCoordinate());
				if (chessPieces != null) {
					g.setColor(getForeground());
					g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
				}
			}

			@Override
			public boolean contains(int x, int y) {
				return new Ellipse2D.Float(0, 0, getWidth(), getHeight()).contains(x, y);
			}

			// Phương thức mới để lấy tọa độ cột của nút
			private int getXCoordinate() {
				return this.getX();
			}

			// Phương thức mới để lấy tọa độ hàng của nút
			private int getYCoordinate() {
				return this.getY();
			}
		};
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		return button;
	}

	private void handleButtonClick(int row, int col) {
		ChessPieces chessPieces = board.getChessPieces(row, col);

		// Nếu đã chọn một quân cờ
		if (selectChessPieces != null) {
			// Nếu người chơi chọn một quân cờ khác cùng màu
			if (chessPieces != null && chessPieces.isWhite() == white && chessPieces != selectChessPieces) {
				selectChessPieces = chessPieces; // Cập nhật quân cờ đã chọn
				removeMove();
				List<int[]> nuocDiHopLe = chessPieces.getMoveTrue(board);
				danhDauNuocDiHopLe(nuocDiHopLe); // Đánh dấu nước đi hợp lệ mới

			}
			// Nếu chọn ô hợp lệ để di chuyển
			else if (selectChessPieces.getMoveTrue(board).stream().anyMatch(nuoc -> nuoc[0] == row && nuoc[1] == col)) {

				board.moveChessPieces(selectChessPieces.getRow(), selectChessPieces.getCol(), row, col);
				white = !white; // Chuyển lượt chơi
				selectChessPieces = null; // Hủy chọn quân cờ sau khi di chuyển
				removeMove();
				capNhatBanCo(board);
				board.printHistory();
			} else {
				JOptionPane.showMessageDialog(this,
						"Vị trí quân " + selectChessPieces.getClass().getSimpleName() + " cần đến không hợp lệ.",
						"Nước đi không hợp lệ", JOptionPane.WARNING_MESSAGE);
			}
		}
		// Nếu chưa chọn quân cờ nào và chọn quân đúng màu
		else if (chessPieces != null && chessPieces.isWhite() == white) {
			List<int[]> moveTrue = chessPieces.getMoveTrue(board);

			// Kiểm tra nếu có ít nhất một nước đi hợp lệ
			if (!moveTrue.isEmpty()) {
				selectChessPieces = chessPieces;
				danhDauNuocDiHopLe(moveTrue);
			}
		} else {
			selectChessPieces = null;
			removeMove();
		}
		// Kiểm tra chiến thắng
		String ketQua = board.checkEnd();
		if (!ketQua.equals("Chưa kết thúc!")) {
			ketQua = white ? "Đen thắng!" : "Trắng thắng!";
			System.out.println("Trò Chơi Kết Thúc " + ketQua);
			JOptionPane.showMessageDialog(this, ketQua, "Kết thúc trò chơi", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0); // Dừng trò chơi
		}

		// Kiểm tra chiếu bí sau khi di chuyển
		if (board.checkCheckmate(!white)) {
			ketQua = white ? "Đen thắng!" : "Trắng thắng!";
			ketQua = white ? "Đen thắng!" : "Trắng thắng!";
			System.out.println("Trò Chơi Kết Thúc " + ketQua);
			JOptionPane.showMessageDialog(this, "Chiếu bí! " + ketQua, "Kết thúc trò chơi",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		if (selectChessPieces == null) {
			if (board.isCheckmate(white)) {
				JOptionPane.showMessageDialog(this, "Đang bị chiếu! ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	private void danhDauNuocDiHopLe(List<int[]> moveTrue) {
		removeMove(); // Xóa các đánh dấu cũ

		for (int[] nuoc : moveTrue) {
			JButton btn = buttons[nuoc[0]][nuoc[1]];

			// Lưu màu chữ của quân cờ trước khi thay đổi nền
			Color oldForeground = btn.getForeground();

			// Đánh dấu ô hợp lệ bằng màu vàng
			btn.setBackground(Color.YELLOW);

			// Khôi phục màu chữ cũ sau khi thay đổi màu nền
			btn.setForeground(oldForeground);

			// Khôi phục lại màu nền cũ nếu nó là một quân cờ

		}
	}

	private void removeMove() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				JButton btn = buttons[i][j];

				// Nếu ô có quân cờ, khôi phục lại màu nền ban đầu và màu chữ
				ChessPieces chessPieces = board.getChessPieces(i, j);
				if (chessPieces != null) {
					if (chessPieces.isWhite()) {
						btn.setBackground(Color.WHITE); // Màu nền quân trắng
						btn.setForeground(Color.BLACK); // Màu chữ quân trắng
					} else {
						btn.setBackground(Color.BLACK); // Màu nền quân đen
						btn.setForeground(Color.WHITE); // Màu chữ quân đen
					}
				} else {
					btn.setBackground(new Color(0, 0, 0, 0)); // Giữ nền trong suốt cho ô trống
					btn.setText(""); // Xóa chữ
				}
			}
		}
	}

	private void capNhatBanCo(Board banCo) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				ChessPieces quanCo = banCo.getChessPieces(i, j);
				if (quanCo != null) {
					buttons[i][j].setText(quanCo.getChina());
					if (quanCo.isWhite()) {
						buttons[i][j].setBackground(Color.WHITE);
						buttons[i][j].setForeground(Color.BLACK);
					} else {
						buttons[i][j].setBackground(Color.BLACK);
						buttons[i][j].setForeground(Color.WHITE);
					}
				} else {
					buttons[i][j].setText("");
					buttons[i][j].setBackground(new Color(0, 0, 0, 0)); // Transparent background for empty cells
				}
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			MainChessBoardGUI gui = new MainChessBoardGUI();
			gui.setVisible(true);
		});
	}
}
