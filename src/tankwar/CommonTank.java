package tankwar;

import java.awt.Point;


import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import enumtype.Direction;
import enumtype.TankType;
import panel.GamePanel;
import path.AudioPlayer;
import path.AudioPath;
import path.ImagePath;
import path.AudioPlayer.AudioThread;
import wall.GrassWall;
import wall.IronWall;
import wall.Wall;

/**
 * 普通电脑坦克类
 * 
 */
@SuppressWarnings("unused")
public class CommonTank extends QuickTank {
	GamePanel gamePanel;// 游戏面板
	Direction direction;// 移动方向
	protected boolean alive = true;// 是否存活
	protected int speed = 1;// 移动速度
	private boolean attackCoolDown = true;// 攻击冷却状态
	private int attackCoolDownTime = 600;// 攻击冷却时间，毫秒
	TankType type;// 坦克类型
	private String upImage;// 向上移动时的图片
	private String downImage;// 向下移动时的图片
	private String rightImage;// 向右移动时的图片
	private String leftImage;// 向左移动时的图片
	
	private int life=3;//生命数
	private int starNum=0;//吃到的道具星星，吃一颗就加快攻击速度，累积三颗就可以打掉钢墙
	private Random random = new Random();// 随机数对象
	private Direction dir;// 移动方向
	private int freshTime = GamePanel.FRESHTIME;// 刷新时间，采用游戏面板的刷新时间
	private int moveTimer = 0;// 移动计时器

	private boolean pause=false;//电脑坦克暂停状态
	/**
	 * 获取电脑坦克暂停状态
	 */
	public boolean isPause() {
		return pause;
	}
	/**
	 * 设置电脑坦克暂停状态
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * 
	 * 电脑坦克构造方法
	 * 
	 * @param x
	 *             横坐标
	 * @param y
	 *             纵坐标
	 * @param url
	 *             电脑坦克默认图片
	 * @param gamePanel
	 *             游戏面板
	 * @param type
	 *             坦克类型
	 */
	
	public CommonTank(int x, int y, String url,GamePanel gamePanel, TankType type,int speed) {
		super(x, y, ImagePath.SLOW_DOWN_IMAGE_URL, gamePanel, type,3);
		dir = Direction.DOWN;// 移动方向默认向下
		setAttackCoolDownTime(1000);// 设置攻击冷却时间
		// setSpeed(2);//设置电脑坦克移动速度
	}

	/**
	 * 电脑坦克展开行动的方法
	 */
	public void go(){
		if(isAttackCoolDown()){// 如果冷却结束，就可以攻击
			attack();// 攻击
		}
		if(moveTimer>random.nextInt(1000)+500){// 如果移动计时器超过随机的0.5~1.5秒，则随机一个方向
			dir=randomDirection();
			moveTimer=0;// 重置计时器
		}else{
			moveTimer+=freshTime;// 计时器按照刷新时间递增
		}
		switch (dir) {// 根据方向选择朝着哪个方向移动
		case UP:
			upWard();
			break;
		case DOWN:
			downWard();
			break;
		case LEFT:
			leftWard();
			break;
		case RIGHT:
			rightWard();
			break;
		}	
	}

	/**
	 * 获取随机方向
	 * 
	 * @return
	 */
	private Direction randomDirection() {
		Direction [] dirs=Direction.values();// 获取出方向的枚举值
		Direction oldDir=dir;// 保存原来的方向
		Direction newDir=dirs[random.nextInt(4)];
		if(oldDir==newDir||newDir==Direction.UP) {// 如果电脑坦克原来的方向和本次随机的方向相同，或者电脑坦克新的方向是向上，那么重新随机一次方向
			return dirs[random.nextInt(4)];
		}
		return newDir;
	}

	/**
	 * 重写移动到面板的边界事件
	 */
	public void moveToBorder() {
		if (x < 0) {// 如果坦克横坐标小于0
			x = 0;// 让坦克横坐标等于0
			dir = randomDirection();// 随机调整移动方向
		} else if (x > gamePanel.getWidth() - width) {// 如果坦克横坐标超出了最大范围
			x = gamePanel.getWidth() - width;// 让坦克横坐标保持最大值
			dir = randomDirection();// 随机调整移动方向
		}
		if (y < 0) {// 如果坦克纵坐标小于0
			y = 0;// 让坦克纵坐标等于0
			dir = randomDirection();// 随机调整移动方向
		} else if (y > gamePanel.getHeight() - height) {// 如果坦克纵坐标超出了最大范围
			y = gamePanel.getHeight() - height;// 让坦克纵坐标保持最大值
			dir = randomDirection();// 随机调整移动方向
		}
	}

	/**
	 * 重写碰到坦克方法
	 */
	@Override
	public boolean hitTank(int x, int y) {
		Rectangle next = new Rectangle(x, y, width, height);// 创建碰撞位置
		List<AbstractTank> tanks = gamePanel.getTanks();// 获取所有坦克集合
		for (int i = 0, lengh = tanks.size(); i < lengh; i++) {// 遍历坦克集合
			AbstractTank t = tanks.get(i);// 获取坦克对象
			if (!this.equals(t)) {// 如果此坦克对象与本对象不是同一个
				if (t.isAlive() && ((DisplayableImage) t).hit(next)) {// 如果对方说是存活的，并且与本对象发生碰撞
					if (t instanceof CommonTank) {// 如果对方也是电脑
						dir = randomDirection();// 随机调整移动方向
					}
					return true;// 发生碰撞
				}
			}
		}
		return false;// 未发生碰撞
	}

	/**
	 * 重写攻击方法，每次攻击只有4%概率会触发父类攻击方法
	 */
	@Override
	public void attack() {
		int rnum = random.nextInt(100);// 创建随机数，范围在0-99
		if (rnum < 4) {// 如果随机数小于4
			if (attackCoolDown) {// 如果攻击功能完成冷却
				Point p = getHeadPoint();// 获取坦克头点对象
				Bullet b = new Bullet(p.x - Bullet.LENGTH / 2, p.y - Bullet.LENGTH / 2, direction, gamePanel, type);// 在坦克头位置发射与坦克角度相同的子弹
				gamePanel.addBullet(b);// 游戏面板添加子弹
				AudioPlayer fire=new AudioPlayer(AudioPath.FIRE);
				fire.new AudioThread().start();
				new AttackCD().start();// 攻击功能开始冷却
			}
		}
	}
	
	/**
	 * 攻击冷却时间线程
	 */
	private class AttackCD extends Thread {
		public void run() {// 线程主方法
			attackCoolDown = false;// 将攻击功能设为冷却状态
			try {
				Thread.sleep(attackCoolDownTime);// 休眠0.5秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			attackCoolDown = true;// 将攻击功能解除冷却状态
		}
	}
	
	/**
	 * 同PlayerTank类————————————————————————————————————————————————————
	 */
	@Override
	public void leftWard() {
		if (direction != Direction.LEFT) {// 如果移动之前的方向不是左移
			setImage(leftImage);// 更换左移图片
		}
		direction = Direction.LEFT;// 移动方向设为左
		if (!hitWall(x - speed, y) && !hitTank(x - speed, y)) {// 如果左移之后的位置不会撞到墙块和坦克
			x -= speed;// 横坐标递减
			moveToBorder();// 判断是否移动到面板的边界
		}
	}
	
	@Override
	public void rightWard() {
		if (direction != Direction.RIGHT) {// 如果移动之前的方向不是左移
			setImage(rightImage);// 更换右移图片
		}
		direction = Direction.RIGHT;// 移动方向设为右
		if (!hitWall(x + speed, y) && !hitTank(x + speed, y)) {// 如果右移之后的位置不会撞到墙块和坦克
			x += speed;// 横坐标递增
			moveToBorder();// 判断是否移动到面板的边界
		}
	}
	
	@Override
	public void upWard() {
		if (direction != Direction.UP) {// 如果移动之前的方向不是上移
			setImage(upImage);// 更换上移图片
		}
		direction = Direction.UP;// 移动方向设为上
		if (!hitWall(x, y - speed) && !hitTank(x, y - speed)) {// 如果上移之后的位置不会撞到墙块和坦克
			y -= speed;// 纵坐标递减
			moveToBorder();// 判断是否移动到面板的边界
		}
	}
	
	@Override
	public void downWard() {
		if (direction != Direction.DOWN) {// 如果移动之前的方向不是下移
			setImage(downImage);// 更换下移图片
		}
		direction = Direction.DOWN;// 移动方向设为下
		if (!hitWall(x, y + speed) && !hitTank(x, y + speed)) {// 如果下移之后的位置不会撞到墙块和坦克
			y += speed;// 纵坐标递增
			moveToBorder();// 判断是否移动到面板的边界
		}
	}

	@Override
	public void addBomb() {
		List<AbstractTank> botTanks=gamePanel.getBotTanks();
		for(int i=0;i<botTanks.size();i++) {
			CommonTank b=(CommonTank) botTanks.get(i);
			b.setAlive(false);
		}
	}
	
	@Override
	public void addTimer() {
		List<AbstractTank> botTanks=gamePanel.getBotTanks();
		for(int i=0;i<botTanks.size();i++) {
			CommonTank b=(CommonTank) botTanks.get(i);
			b.setPause(true);
		}
	}

	@Override
	public boolean isAlive() {
		return this.alive;
	}
	
	@Override
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
		
	@Override
	public int getLife() {
		return this.life;
	}
	
	@Override
	public void setLife() {
		if(this.life>0) {
			this.life--;
		} else {
			return;
		}
	}
	
	@Override
	public TankType getTankType() {
		return type;
	}
}
