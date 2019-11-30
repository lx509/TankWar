package panel;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import enumtype.GameType;
import path.ImagePath;

/**
 * 登陆面板（选择游戏模式）
 * 
 */
public class LoginPanel extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameType type;// 游戏模式
	private Image backgroud;// 背景图片
	private Image singlePlayer;
	private Image doublePlayer;
	private Image tank;// 坦克图标
	private int y1 = 270, y2=390;// 坦克图标可选择的四个Y坐标
	private int tankY = 270;// 坦克图标Y坐标
	private MainFrame frame;
	
	/**
	 * 登陆面板构造方法
	 * 
	 * @param frame
	 *              主窗体
	 */
	public LoginPanel(MainFrame frame) {
		this.frame=frame;
		addListener();// 添加组件监听
//		//this.requestFocusInWindow();
//		//默认获取到焦点
//		if(!this.isFocusable()) {
//			//System.out.println("设置允许获取到焦点");
//			this.setFocusable(true);
//		}
//		if(!this.isFocusOwner()) {
//			//System.out.println("设置获取到焦点");
//			this.requestFocusInWindow();
//		}
		try {
			backgroud = ImageIO.read(new File(ImagePath.LOGIN_BACKGROUD_IMAGE_URL));// 读取背景图片
			singlePlayer = ImageIO.read(new File(ImagePath.SINGLEPLAYER_URL));// 读取单人游戏图片
			doublePlayer = ImageIO.read(new File(ImagePath.DOUBLEPLAYER_URL));// 读取双人游戏图片
			tank = ImageIO.read(new File(ImagePath.PLAYER1_RIGHT_IMAGE_URL));// 读取坦克图标
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重写绘图方法
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(backgroud, 0, 0, getWidth(), getHeight(), this);// 绘制背景图片，填满整个面板
		g.drawImage(singlePlayer, 300, 265, 200, 50, this);// 绘制单人游戏图片
		g.drawImage(doublePlayer, 300, 380, 200, 50, this);// 绘制双人游戏图片
		
		g.drawImage(tank, 260, tankY, this);// 绘制坦克图标
	}

	/**
	 * 跳转关卡面板
	 */
	private void gotoLevelPanel() {
		frame.removeKeyListener(this);// 主窗体删除键盘监听
		frame.setPanel(new LevelPanel(1, frame, type));// 主窗体跳转至关卡面板
	}

	/**
	 * 添加组件监听
	 */
	private void addListener() {
		frame.addKeyListener(this);// 主窗体载入键盘监听，本类已实现KeyListener接口
	}

	/**
	 * 当按键按下时
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();// 获取按下的按键值
		switch (code) {// 判断按键值
		case KeyEvent.VK_UP:// 如果按下的是“↑”
			if(tankY == y1) {
				tankY = y2;
			}else if(tankY == y2){
				tankY=y1;
			}
			repaint();// 按键按下之后，需要重新绘图
			break;
		case KeyEvent.VK_DOWN:// 如果按下的是“↓”
			if (tankY == y1) {
				tankY = y2;
			}else if(tankY ==y2){
				tankY = y1;
			}
			repaint();// 按键按下之后，需要重新绘图
			break;
		case KeyEvent.VK_ENTER:// 如果按下的是“Enter”
			if (tankY == y1) {// 如果坦克图标在第一个位置
				type=GameType.ONE_PLAYER;
				gotoLevelPanel();// 跳转关卡面板
			}
			if(tankY == y2){
				type = GameType.TWO_PLAYER;// 游戏模式为双人模式
				gotoLevelPanel();// 跳转关卡面板
			}
		}
		
	}

	/**
	 * 按键抬起时
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// 不实现此方法，但不可删除
	}

	/**
	 * 键入某按键事件
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// 不实现此方法，但不可删除
	}
	
}
