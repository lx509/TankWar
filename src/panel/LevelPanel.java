package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import enumtype.GameType;

/**
 * 显示关卡面板
 * 
 */
public class LevelPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int level;// 关卡值
	private GameType type;// 游戏模式
	private MainFrame frame;// 主窗体
	private String levelStr;// 面板中央闪烁的关卡字符串
	private String ready = "";// 准备提示

	/**
	 * 关卡面板构造方法
	 * 
	 * @param level
	 *              关卡值
	 * @param frame
	 *              主窗体
	 * @param type
	 *              游戏模式
	 */
	public LevelPanel(int level, MainFrame frame, GameType type) {
		this.frame = frame;
		this.level = level;
		this.type = type;
		levelStr = "关卡 " + level;// 初始化关卡字符串
		Thread t = new LevelPanelThread();// 创建关卡面板动画线程
		t.start();// 开启线程
	}

	/**
	 * 重写绘图方法
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);// 使用黑色
		g.fillRect(0, 0, getWidth(), getHeight());// 填充一个覆盖整个面板的黑色矩形
		g.setFont(new Font("黑体", Font.BOLD, 50));// 设置绘图字体
		g.setColor(Color.ORANGE);// 使用橙色
		g.drawString(levelStr, 335, 300);// 绘制关卡字符串
	}

	/**
	 * 跳转游戏面板
	 */
	private void gotoGamePanel() {
		System.gc();
		frame.setPanel(new GamePanel(frame, level, type));// 主窗体跳转到此关卡游戏面板
	}

	/**
	 * 关卡面板动画线程
	 *
	 */
	private class LevelPanelThread extends Thread {
		public void run() {
			for (int i = 0; i < 3; i++) {// 循环6次
				if (i % 2 == 0) {// 如果循环变量是偶数
					levelStr = "关卡" + level;// 关卡字符串正常显示
				} else {
					levelStr = "";// 关卡字符串不显示任何内容
				}
				repaint();// 重绘组件
				try {
					Thread.sleep(500);// 休眠0.5秒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gotoGamePanel();// 跳转到游戏面板
		}
	}
}
