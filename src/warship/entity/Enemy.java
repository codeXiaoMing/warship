package warship.entity;

import java.util.Vector;

public class Enemy extends Warship implements Runnable {

    public boolean islive = true;                                    //���Ƶн��Ƿ����
    public int direction;                                            //�����ƶ�����
    public int count = 0;                                            //���Ƶн������ӵ�
    public Vector<Shot> bullets = new Vector<Shot>();                //ͨ����̬����洢�ӵ��ﵽ��������

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
            switch (direction) {                                //�������Ҷ�Ҫ�У���Ȼ�޷��ƶ�
                case 7: {                                            //�����ƶ�
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
                case 2: {                                            //�����ƶ�
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
                case 5: {                                            //�����ƶ�
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
                case 3: {                                            //�����ƶ�
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
                    break;                                //����������ƶ�
            }
            count++;
            if (count % 2 == 0 && islive && bullets.size() < 4)        //���Ƶн������ӵ�
            {
                Shot shot = new Shot(x + 8, y - 4, 2);
                bullets.addElement(shot);
                Thread t = new Thread(shot);
                t.start();
            }
            if (count % 10 == 0 && islive)                            //�н����ʱ��Խ���ƶ��ٶ�Խ��
            {
                speed++;
				if (speed >= 14) {
					speed = 14;
				}
            }
            direction = (int) (Math.random() * 10);
			if (islive == false)                                //�н������˳��߳�
			{
				break;
			}
        }
    }
}
