package cn.blmdz.book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class G617 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public G617() {
		super("常规菜单的使用");
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
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
		
	}
	
	public static void main(String[] args) {
		G617 app = new G617();
		app.setSize(260, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
