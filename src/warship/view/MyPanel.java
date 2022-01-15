package warship.view;

import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Vector;
import warship.Main;
import warship.entity.Enemy;
import warship.entity.PlayerShip;
import warship.entity.Shot;

public class MyPanel extends JPanel implements KeyListener, Runnable {

    File file = new File("music/ս����������.wav");
    File topGoal = new File("top/��߷�.txt");
    URL url = null;
    URI uri = null;
    AudioClip clip = null;
    PlayerShip hero = null;
    Vector<Enemy> enemy = new Vector<Enemy>();            //��Ϊ�н������������ö�̬����
    int num = 7;                                            //�н�����
    int goal = 0;                                            //��¼���ен�������
    JLabel grade;                                        //��¼�ɼ�

    MyPanel() {
        try {
            uri = file.toURI();
            url = uri.toURL();
        } catch (MalformedURLException e1) {
            System.out.println(e1);
        }
        clip = Applet.newAudioClip(url);
        clip.loop();

        setFocusable(true);
        setLayout(null);                                //�����޷����Ʊ�ǩ��λ��

        grade = new JLabel("0");
        grade.setBounds(700, 500, 100, 100);
        grade.setFont(new java.awt.Font("24", 20, 50));
        grade.setForeground(Color.WHITE);
        add(grade);

        hero = new PlayerShip(400, 500);                    //����һ�����ս��
        for (int i = 0; i < num; i++) {
            Enemy enemys = new Enemy((i + 1) * 100, 0);        //����ÿ���н���ľ���
            Thread t = new Thread(enemys);
            t.start();
            enemy.add(enemys);                            //���н���ӵ���̬������
        }
    }

    public void paintComponent(Graphics g)                //����һ������
    {
        ImageIcon background = new ImageIcon("ͼƬ/��Ϸ����.gif");    // ��ȡ����ͼƬ��·��
        ImageIcon image1 = new ImageIcon("ͼƬ/ս��.png");            // ��ȡս��ͼƬ��·��
        ImageIcon image2 = new ImageIcon("ͼƬ/�н�.png");            // ��ȡ�н�ͼƬ��·��
        g.drawImage(background.getImage(), 0, 0, 800, 600, null);    // ͼƬ����
        for (int i = 0; i < enemy.size(); i++)                    //���н�
        {
            Enemy em = enemy.get(i);
            if (em.islive) {
                g.drawImage(image2.getImage(), enemy.get(i).getX(), enemy.get(i).getY(), 70, 70, null);//�����ս��
                for (int j = 0; j < em.bullets.size(); j++) {
                    Shot s = em.bullets.get(j);
                    if (s.islive) {
                        g.setColor(Color.green);
                        g.fillRect(s.x, s.y + 50, 5, 5);                //һ��Ҫ���ӵ�������
                    } else {
                        em.bullets.remove(s);
                    }
                }
            }
        }

        if (hero.islive) {
            g.drawImage(image1.getImage(), hero.getX(), hero.getY(), 70, 70, null);        //�����ս��
        }
        for (int i = 0; i < hero.bullets.size(); i++)                        //������ӵ�
        {
            Shot myShot = hero.bullets.get(i);
            if (myShot.islive == true) {
                g.setColor(Color.WHITE);
                g.fillRect(myShot.x, myShot.y, 5, 5);    //һ��Ҫ��myShot������Ҫ��Ȼֻ����ʾһ���ӵ�
            }
            if (myShot.islive == false) {
                hero.bullets.remove(myShot);
            }
        }
    }

    public void hitEnemy(Shot shot, Enemy enemy)                            //�ж��ӵ��Ƿ���ен�
    {
        if (shot.x >= enemy.x - 16 && shot.x <= enemy.x + 35 && shot.y <= enemy.y + 35) {
            shot.islive = false;
            enemy.islive = false;
            goal++;                                                        //ÿ����һ���н��͵�һ��
            grade.setText("" + goal);
        }
    }

    public void hitHero(Shot shot, PlayerShip hero)                            //�ж��ӵ��Ƿ�������ս��
    {
        if (shot.x >= hero.x + 10 && shot.x <= hero.x + 50 && shot.y >= hero.y - 50 && shot.y <= hero.y + 20) {
            shot.islive = false;
            hero.islive = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e)                            //ͨ�������������ƶ��������ӵ�����
    {
        if (e.getKeyCode() == KeyEvent.VK_UP)                        //�ƶ�����
        {
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hero.moveRight();
        }
        repaint();                                                //�ı������Ҫ���»�һ��
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1)                        //��F1�رձ�������
        {
            clip.stop();
        }
        if (e.getKeyCode() == KeyEvent.VK_F2)                        //��F2������������
        {
            clip.loop();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE)                    //�ӵ�����,���ո���
        {
            hero.OpenFire();
        }
        repaint();
    }

    public void run()                                    //ÿ��һ��ʱ�����»�ͼ�����ӵ��˶�״̬
    {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            for (int i = 0; i < hero.bullets.size(); i++)            //��ΪҪ���ϸ�������run�����м���Ƿ����
            {
                Shot shot = hero.bullets.get(i);
                if (shot.islive) {
                    for (int j = 0; j < enemy.size(); j++) {
                        Enemy e = enemy.get(j);
                        if (e.islive) {
                            hitEnemy(shot, e);
                            if (e.islive == false)                        //����н�������
                            {
                                enemy.remove(e);
                                if (enemy.size() < 4)                //�н���������4�ͻ��Զ�����
                                {
                                    for (int k = 0; k < 4; k++) {
                                        Enemy et = new Enemy((k + 2) * 100, 0);
                                        enemy.add(et);
                                        Thread t1 = new Thread(et);
                                        t1.start();
                                    }
                                }
                            }
                        }

                    }
                }
            }
            loop:
            for (int i = 0; i < enemy.size(); i++)                    //�жϵн��������ս��
            {
                Enemy em = enemy.get(i);
                for (int j = 0; j < em.bullets.size(); j++) {
                    Shot shot = em.bullets.get(j);
                    if (shot.islive) {
                        hitHero(shot, hero);
                        if (hero.islive == false)                        //���ս�������о�����
                        {
                            break loop;
                        }
                    }
                }
            }
            repaint();
            if (hero.islive == false)                                    //ֱ��break����
            {
                clip.stop();                                        //��Ϸ��������ͣ
                try {
                    FileReader in1 = new FileReader(topGoal);            //�Ƚ���ʷ��ѳɼ������³ɼ�
                    BufferedReader in2 = new BufferedReader(in1);
                    String s = in2.readLine();
                    int top = Integer.parseInt(s);                    //���ļ�����ѳɼ�������ת��Ϊint����
                    if (goal > top) {
                        FileWriter out1 = new FileWriter(topGoal);
                        BufferedWriter out2 = new BufferedWriter(out1);
                        out2.write("" + goal);                        //�����³ɼ��ʹ��浽�ļ���
                        out2.flush();
                        out2.close();
                        out1.close();
                        JOptionPane.showMessageDialog(this, "������ʷ��ѳɼ���" + goal, "�÷�", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "���ճɼ�Ϊ��" + goal + "    ��ʷ��ѳɼ���" + top, "�÷�",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    in2.close();
                    in1.close();
                } catch (Exception e) {
                }
                Main.main(null);
                break;
            }
        }
    }
}
