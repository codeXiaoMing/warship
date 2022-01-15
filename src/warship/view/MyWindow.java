package warship.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.applet.*;

public class MyWindow extends JFrame {

    MyPanel p;
    JPanel home;
    File file = new File("music/飞机飞过特效.wav");        //不能是mp3格式
    URL url = null;
    URI uri = null;
    AudioClip clip = null;

    public MyWindow() {
        home = new JPanel();                                            //开始画面
		home.setBounds(0, 0, 800, 600);
        add(home);

        ImageIcon icon = new ImageIcon("图片/开始画面.gif");
        icon.setImage(icon.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT));
        JLabel gameStart = new JLabel(icon);
		gameStart.setBounds(0, 0, 800, 600);
        home.add(gameStart);

        try {
            uri = file.toURI();
            url = uri.toURL();
        } catch (MalformedURLException e1) {
        	System.out.println(e1);
        }
        clip = Applet.newAudioClip(url);
        clip.loop();                                            //循环播放，加play只会放一遍

        gameStart.addMouseListener(new MouseAdapter()                //单击开始游戏
        {
            public void mouseClicked(MouseEvent e) {
                clip.stop();
                home.setVisible(false);                        //开始画面不可视，否则看不到游戏画面
                startGame();                                //添加游戏面板
                remove(home);                                //将开始画面移除
            }
        });

        setUndecorated(true);                                    //取消标题看起来酷一点
        setBounds(500, 200, 800, 600);
        setVisible(true);
        validate();
        setDefaultCloseOperation(MyWindow.EXIT_ON_CLOSE);
    }

    void startGame()                                            //创建游戏面板
    {
        p = new MyPanel();
        p.setFocusable(true);
        p.setVisible(true);
        Thread t = new Thread(p);
        t.start();
        add(p);
        addKeyListener(p);
    }
}
