package warship.entity;

public class Shot implements Runnable {

    public int x;
    public int y;
    public int direction;                                //�ж��ӵ�������һ��ǵн�
    public int speed = 5;                                //�ӵ��ƶ��ٶ�
    public boolean islive = true;                        //�ж��ӵ��Ƿ񳬳���Ļ��Χ

    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(50);                //ͨ����ͣ��ֹ�ӵ�ֱ�ӷɳ�
            } catch (Exception e) {
            }
            switch (direction) {
                case 1:                            //1Ϊ���ս���ӵ��ķ���
                {
                    y -= speed;
                    break;                        //û��break�ӵ�ԭ�ز���
                }
                case 2:                            //2Ϊ�н��ӵ��ķ���
                {
                    y += speed;
                    break;
                }
            }
            if (y < 0 || y > 600)                                //����ӵ�������Ļ�ͽ���
            {
                this.islive = false;
                break;
            }
        }
    }
}
