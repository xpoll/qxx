package cn.blmdz.gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MouseInfo extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    JLabel value_x = null;
    JLabel value_y = null;
    JLabel value_rgb = null;
    JLabel value_color = null;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            final MouseInfo info_frame = new MouseInfo();
            info_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            info_frame.setVisible(true);
            info_frame.setAlwaysOnTop(true);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
                    info_frame.value_x.setText("" + point.x);
                    info_frame.value_y.setText("" + point.y);
                    Color color = getScreenPixel(point.x, point.y);
                    info_frame.value_rgb.setText(String.format("R: %s, G: %s, B: %s, A: %s", color.getRed(),
                            color.getGreen(), color.getBlue(), color.getAlpha()));
                    info_frame.value_color.setOpaque(true);
                    info_frame.value_color.setBackground(color);
                }
            }, 100, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Color getScreenPixel(int x, int y) { // 函数返回值为颜色的RGB值。
        Robot rb = null; // java.awt.image包中的类，可以用来抓取屏幕，即截屏。
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Toolkit tk = Toolkit.getDefaultToolkit(); // 获取缺省工具包
        Dimension di = tk.getScreenSize(); // 屏幕尺寸规格
        Rectangle rec = new Rectangle(0, 0, di.width, di.height);
        BufferedImage bi = rb.createScreenCapture(rec);
        int pixelColor = bi.getRGB(x, y);
        Color color = new Color(16777216 + pixelColor);
        return color; // pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
    }

    /**
     * Create the dialog.
     */
    public MouseInfo() {
        setTitle("鼠标颜色捕捉");
        setBounds(100, 150, 400, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblx = new JLabel("X:");
        lblx.setFont(new Font("宋体", Font.PLAIN, 15));
        lblx.setBounds(22, 27, 66, 31);
        contentPanel.add(lblx);

        JLabel lbly = new JLabel("Y:");
        lbly.setFont(new Font("宋体", Font.PLAIN, 15));
        lbly.setBounds(22, 68, 66, 31);
        contentPanel.add(lbly);

        JLabel lblrgb = new JLabel("RGB:");
        lblrgb.setFont(new Font("宋体", Font.PLAIN, 15));
        lblrgb.setBounds(22, 109, 66, 31);
        contentPanel.add(lblrgb);

        value_x = new JLabel("0");
        value_x.setForeground(Color.BLUE);
        value_x.setFont(new Font("宋体", Font.PLAIN, 20));
        value_x.setBounds(82, 27, 66, 31);
        contentPanel.add(value_x);

        value_y = new JLabel("0");
        value_y.setForeground(Color.BLUE);
        value_y.setFont(new Font("宋体", Font.PLAIN, 20));
        value_y.setBounds(82, 68, 66, 31);
        contentPanel.add(value_y);

        value_rgb = new JLabel("0");
        value_rgb.setForeground(Color.BLUE);
        value_rgb.setFont(new Font("宋体", Font.PLAIN, 15));
        value_rgb.setBounds(82, 109, 350, 31);
        contentPanel.add(value_rgb);

        value_color = new JLabel("");
        value_color.setForeground(Color.BLUE);
        value_color.setBounds(220, 27, 70, 70);
        contentPanel.add(value_color);
    }
}
