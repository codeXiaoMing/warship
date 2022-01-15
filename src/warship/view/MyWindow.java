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
    File file = new File("music/�ɻ��ɹ���Ч.wav");        //������mp3��ʽ
    URL url = null;
    URI uri = null;
    AudioClip clip = null;

    public MyWindow() {
        home = new JPanel();                                            //��ʼ����
		home.setBounds(0, 0, 800, 600);
        add(home);

        ImageIcon icon = new ImageIcon("ͼƬ/��ʼ����.gif");
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
        clip.loop();                                            //ѭ�����ţ���playֻ���һ��

        gameStart.addMouseListener(new MouseAdapter()                //������ʼ��Ϸ
        {
            public void mouseClicked(MouseEvent e) {
                clip.stop();
                home.setVisible(false);                        //��ʼ���治���ӣ����򿴲�����Ϸ����
                startGame();                                //�����Ϸ���
                remove(home);                                //����ʼ�����Ƴ�
            }
        });

        setUndecorated(true);                                    //ȡ�����⿴������һ��
        setBounds(500, 200, 800, 600);
        setVisible(true);
        validate();
        setDefaultCloseOperation(MyWindow.EXIT_ON_CLOSE);
    }

    void startGame()                                            //������Ϸ���
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
