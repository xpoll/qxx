package cn.blmdz.book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

public class G618 extends JFrame {

	private static final long serialVersionUID = 1L;
	JPopupMenu menubar = new JPopupMenu();
	
	public G618() {
		super("快捷菜单的使用");
		JMenu[] m = {new JMenu("文件(F)"), new JMenu("编辑(E)")};
		char[][] mc = {{'F', 'E'}, {'O', 'S'}, {'C', 'V'}};
		JMenuItem[][] mi = {
				{new JMenuItem("打开(O)"), new JMenuItem("保存(S)")},
				{new JMenuItem("拷贝(C)"), new JMenuItem("粘贴(V)")}
		};
		for (int i = 0; i < m.length; i++) {
			menubar.add(m[i]);
			m[i].setMnemonic(mc[0][i]);
			for (int j = 0; j < mi[i].length; j++) {
				m[i].add(mi[i][j]);
				mi[i][j].setMnemonic(mc[i+1][j]);
				mi[i][j].setAccelerator(KeyStroke.getKeyStroke("Ctrl" + mc[i + 1][j]));
				mi[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JMenuItem jmi = (JMenuItem) e.getSource();
						System.out.println("运行菜单项：" + jmi.getText());
					}
				});
			}
		}
		m[0].insertSeparator(1);
		addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					menubar.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			@Override
		    public void mouseReleased(MouseEvent e) {
				mousePressed(e);
			}
		});
	}
	
	public static void main(String[] args) {
		G618 app = new G618();
		app.setSize(260, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
