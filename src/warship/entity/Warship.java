package warship.entity;

public class Warship {

    public int x = 0;                                        //通过坐标确定战舰的位置
    public int y = 0;
    public int speed = 8;                                    //控制战舰移动的速度

    Warship(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSpeed(int speed)                    //设置移动速度
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
