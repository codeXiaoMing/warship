package warship.entity;

public class Warship {

    public int x = 0;                                        //ͨ������ȷ��ս����λ��
    public int y = 0;
    public int speed = 8;                                    //����ս���ƶ����ٶ�

    Warship(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSpeed(int speed)                    //�����ƶ��ٶ�
    {
        this.speed = speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
