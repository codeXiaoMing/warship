package warship;

import java.awt.event.*;
import warship.view.MyWindow;

public class Main {

    public static void main(String[] args) {
        MyWindow window = new MyWindow();
		window.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e)                //不要用keyTyped容易监听不到
            {
                if (e.getKeyCode()
                        == KeyEvent.VK_ESCAPE)                        //按Esc退出程序
                {
                    System.exit(0);                                //不能用dispose会一直出现对话框
                }
            }
        });
    }
}
