package warship.entity;

import java.util.Vector;

public class Enemy extends Warship implements Runnable {

    public boolean islive = true;                                    //控制敌舰是否存在
    public int direction;                                            //控制移动方向
    public int count = 0;                                            //控制敌舰发射子弹
    public Vector<Shot> bullets = new Vector<Shot>();                //通过动态数组存储子弹达到连续发射

    public Enemy(int x, int y) {
        super(x, y);
        speed = 8;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
            switch (direction) {                                //上下左右都要有，不然无法移动
                case 7: {                                            //向下移动
                    for (int i = 0; i < 3; i++) {
                        if (y < 450) {
                            y += speed;
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                            }
                        }
                    }
                    break;
                }
                case 2: {                                            //向上移动
                    for (int i = 0; i < 3; i++) {
                        if (y > 0) {
                            y -= speed;
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                            }
                        }
                    }
                    break;
                }
                case 5: {                                            //向左移动
                    for (int i = 0; i < 3; i++) {
                        if (x > 20) {
                            x -= speed;
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                            }
                        }
                    }
                    break;
                }
                case 3: {                                            //向右移动
                    for (int i = 0; i < 3; i++) {
                        if (x < 730) {
                            x += speed;
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                            }
                        }
                    }
                    break;
                }
                default:
                    break;                                //其他情况不移动
            }
            count++;
            if (count % 2 == 0 && islive && bullets.size() < 4)        //控制敌舰发射子弹
            {
                Shot shot = new Shot(x + 8, y - 4, 2);
                bullets.addElement(shot);
                Thread t = new Thread(shot);
                t.start();
            }
            if (count % 10 == 0 && islive)                            //敌舰存活时间越长移动速度越快
            {
                speed++;
				if (speed >= 14) {
					speed = 14;
				}
            }
            direction = (int) (Math.random() * 10);
			if (islive == false)                                //敌舰死亡退出线程
			{
				break;
			}
        }
    }
}
