package tankwar;

import java.awt.Point;

import enumtype.TankType;
/**
 * 抽象坦克接口
 * 
 */
public interface AbstractTank {
	public void leftWard();//向左移动
	public void rightWard();//向右移动
	public void upWard();//向上移动
	public void downWard();//向下移动
	boolean hitWall(int x, int y);//判断是否撞到墙块
	public boolean hitTank(int x, int y);//判断是否撞到其他坦克
	public boolean hitTool();//判断是否吃到了道具
	void addBomb();//吃到爆炸道具
	void addTimer();//坦克吃到定时器道具
	public void addStar();//坦克吃到星星道具
	void addSpade();//坦克吃到钢撬道具
	void moveToBorder();//移动到面板的边界
	Point getHeadPoint();//获取坦克头点
	public void attack();//攻击
	public boolean isAlive();//坦克是否存活
	public void setAlive(boolean alive);//设置存活状态
	public boolean isAttackCoolDown();//获取攻击功能是否处于冷却
	public void setAttackCoolDownTime(int attackCoolDownTime);//攻击冷却时间线程
	public int getLife();//获取坦克生命数量
	public void setLife();//减少坦克生命数量
	public TankType getTankType();//获取坦克类型
}
