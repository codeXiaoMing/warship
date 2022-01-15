package warship.entity;

public class Shot implements Runnable {

    public int x;
    public int y;
    public int direction;                                //判断子弹来自玩家还是敌舰
    public int speed = 5;                                //子弹移动速度
    public boolean islive = true;                        //判断子弹是否超出屏幕范围

    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(50);                //通过暂停防止子弹直接飞出
            } catch (Exception e) {
            }
            switch (direction) {
                case 1:                            //1为玩家战舰子弹的方向
                {
                    y -= speed;
                    break;                        //没有break子弹原地不动
                }
                case 2:                            //2为敌舰子弹的方向
                {
                    y += speed;
                    break;
                }
            }
            if (y < 0 || y > 600)                                //如果子弹超出屏幕就结束
            {
                this.islive = false;
                break;
            }
        }
    }
}
