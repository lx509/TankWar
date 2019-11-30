package wall;

import path.ImagePath;

/**
 * 河流
 *
 */
public class RiverWall extends Wall {
	/**
	 * 
	 * 河流构造方法
	 * 
	 * @param x
	 *              初始化横坐标
	 * @param y
	 *              初始化纵坐标
	 */
	public RiverWall(int x, int y) {
		super(x, y, ImagePath.RIVERWALL_IMAGE_URL);// 调用父类构造方法，使用默认河流图片
	}

}
