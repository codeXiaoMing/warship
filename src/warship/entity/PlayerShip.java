package warship.entity;

import java.util.Vector;

public class PlayerShip extends Warship {

    public Vector<Shot> bullets = new Vector<Shot>();                //ͨ����̬����洢�ӵ��ﵽ��������
    public Shot bullet = null;
    public boolean islive = true;

    public PlayerShip(int x, int y) {
        super(x, y);
    }

    public void OpenFire()                                    //�������
    {
        this.bullet = new Shot(x + 33, y - 2, 1);                    //���ӵ������λ�ô���
        bullets.add(bullet);
        Thread t = new Thread(bullet);                        //�����߳̿����ӵ�
        t.start();
    }

    //ս��ͨ������ĸı�����ƶ�
    public void moveUp()                            //��
    {
		if (y > 5) {
			y -= speed;
		}
    }

    public void moveDown()                            //��
    {
		if (y < 530) {
			y += speed;
		}
    }

    public void moveLeft()                            //��
    {
		if (x > 0) {
			x -= speed;
		}
    }

    public void moveRight()                            //��
    {
		if (x < 730) {
			x += speed;
		}
    }
}
