package warship;

import java.awt.event.*;
import warship.view.MyWindow;

public class Main {

    public static void main(String[] args) {
        MyWindow window = new MyWindow();
		window.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e)                //��Ҫ��keyTyped���׼�������
            {
                if (e.getKeyCode()
                        == KeyEvent.VK_ESCAPE)                        //��Esc�˳�����
                {
                    System.exit(0);                                //������dispose��һֱ���ֶԻ���
                }
            }
        });
    }
}
