package warship.entity;

import java.util.Vector;

public class PlayerShip extends Warship {

    public Vector<Shot> bullets = new Vector<Shot>();                //通过动态数组存储子弹达到连续发射
    public Shot bullet = null;
    public boolean islive = true;

    public PlayerShip(int x, int y) {
        super(x, y);
    }

    public void OpenFire()                                    //射击方法
    {
        this.bullet = new Shot(x + 33, y - 2, 1);                    //将子弹发射的位置传入
        bullets.add(bullet);
        Thread t = new Thread(bullet);                        //创建线程控制子弹
        t.start();
    }

    //战舰通过坐标的改变进行移动
    public void moveUp()                            //上
    {
		if (y > 5) {
			y -= speed;
		}
    }

    public void moveDown()                            //下
    {
		if (y < 530) {
			y += speed;
		}
    }

    public void moveLeft()                            //左
    {
		if (x > 0) {
			x -= speed;
		}
    }

    public void moveRight()                            //右
    {
		if (x < 730) {
			x += speed;
		}
    }
}
