package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import enumtype.PropType;
import panel.GamePanel;
import path.ImagePath;
/**
 * 
 * 道具类
 *
 */
public class Prop extends DisplayableImage{
	
	private static String[] imgURL= {
			ImagePath.ADD_TANK_URL,
			ImagePath.BOMB_URL,
			ImagePath.SPADE_URL,
			ImagePath.TIMER_URL,
			ImagePath.STAR_URL,
			ImagePath.GUN_URL
	};
	
	private static Image [] toolImgs= {
			Toolkit.getDefaultToolkit().createImage(imgURL[0]),
			Toolkit.getDefaultToolkit().createImage(imgURL[1]),
			Toolkit.getDefaultToolkit().createImage(imgURL[2]),
			Toolkit.getDefaultToolkit().createImage(imgURL[3]),
			Toolkit.getDefaultToolkit().createImage(imgURL[4]),
			Toolkit.getDefaultToolkit().createImage(imgURL[5])
	};
	
	private int timer=0;//计时器
	private int aliveTime=4500;//道具存活的时间
	private  Random r=new Random();//随机数对象
	private static  int height=20,width=20;
	PropType type;//道具类型
	private boolean alive=true;//存活状态
	
	/**
	 * 获取道具实例
	 * @param x 道具第一次出现的横坐标
	 * @param y 道具第一次出现的纵坐标
	 * @return 一个道具对象
	 */
	public static Prop getToolInstance(int x,int y) {
		return new Prop(x,y);
	}
	private Prop(int x,int y) {
		super(x,y,width,height);
		type=PropType.values()[r.nextInt(6)];
	}
	
	public void changeToolType() {
		type=PropType.values()[r.nextInt(6)];
		x=r.nextInt(550);
		y=r.nextInt(500);
		this.alive=true;
	}
	
	/**
	 * 画出道具
	 * @param g 画布
	 */
	public void draw(Graphics g) {
		if(timer>aliveTime) {
			timer=0;
			setAlive(false);
			//changeToolType();
		}else {
			g.drawImage(toolImgs[type.ordinal()], x, y, null);
			//System.out.println("是否画成功，"+b+",type="+type+",x="+x+",y="+y);
			timer+=GamePanel.FRESHTIME;
		}
	}
	/**
	 * 设置存活状态
	 * @param alive 存活状态
	 */
	public void setAlive(boolean alive) {
		this.alive=alive;
		timer=0;
	}
	/**
	 * 道具的存活状态
	 * @return
	 */
	public boolean getAlive() {
		return this.alive;
	}
}
