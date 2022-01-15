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

    File file = new File("music/战斗背景音乐.wav");
    File topGoal = new File("top/最高分.txt");
    URL url = null;
    URI uri = null;
    AudioClip clip = null;
    PlayerShip hero = null;
    Vector<Enemy> enemy = new Vector<Enemy>();            //因为敌舰数量不定，用动态数组
    int num = 7;                                            //敌舰数量
    int goal = 0;                                            //记录击中敌舰的数量
    JLabel grade;                                        //记录成绩

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
        setLayout(null);                                //不加无法控制标签得位置

        grade = new JLabel("0");
        grade.setBounds(700, 500, 100, 100);
        grade.setFont(new java.awt.Font("24", 20, 50));
        grade.setForeground(Color.WHITE);
        add(grade);

        hero = new PlayerShip(400, 500);                    //创建一个玩家战舰
        for (int i = 0; i < num; i++) {
            Enemy enemys = new Enemy((i + 1) * 100, 0);        //控制每个敌舰间的距离
            Thread t = new Thread(enemys);
            t.start();
            enemy.add(enemys);                            //将敌舰添加到动态数组中
        }
    }

    public void paintComponent(Graphics g)                //设置一个画笔
    {
        ImageIcon background = new ImageIcon("图片/游戏背景.gif");    // 获取背景图片的路径
        ImageIcon image1 = new ImageIcon("图片/战舰.png");            // 获取战舰图片的路径
        ImageIcon image2 = new ImageIcon("图片/敌舰.png");            // 获取敌舰图片的路径
        g.drawImage(background.getImage(), 0, 0, 800, 600, null);    // 图片背景
        for (int i = 0; i < enemy.size(); i++)                    //画敌舰
        {
            Enemy em = enemy.get(i);
            if (em.islive) {
                g.drawImage(image2.getImage(), enemy.get(i).getX(), enemy.get(i).getY(), 70, 70, null);//画玩家战舰
                for (int j = 0; j < em.bullets.size(); j++) {
                    Shot s = em.bullets.get(j);
                    if (s.islive) {
                        g.setColor(Color.green);
                        g.fillRect(s.x, s.y + 50, 5, 5);                //一定要是子弹的坐标
                    } else {
                        em.bullets.remove(s);
                    }
                }
            }
        }

        if (hero.islive) {
            g.drawImage(image1.getImage(), hero.getX(), hero.getY(), 70, 70, null);        //画玩家战舰
        }
        for (int i = 0; i < hero.bullets.size(); i++)                        //画玩家子弹
        {
            Shot myShot = hero.bullets.get(i);
            if (myShot.islive == true) {
                g.setColor(Color.WHITE);
                g.fillRect(myShot.x, myShot.y, 5, 5);    //一定要是myShot的坐标要不然只会显示一个子弹
            }
            if (myShot.islive == false) {
                hero.bullets.remove(myShot);
            }
        }
    }

    public void hitEnemy(Shot shot, Enemy enemy)                            //判断子弹是否击中敌舰
    {
        if (shot.x >= enemy.x - 16 && shot.x <= enemy.x + 35 && shot.y <= enemy.y + 35) {
            shot.islive = false;
            enemy.islive = false;
            goal++;                                                        //每击中一个敌舰就得一分
            grade.setText("" + goal);
        }
    }

    public void hitHero(Shot shot, PlayerShip hero)                            //判断子弹是否击中玩家战舰
    {
        if (shot.x >= hero.x + 10 && shot.x <= hero.x + 50 && shot.y >= hero.y - 50 && shot.y <= hero.y + 20) {
            shot.islive = false;
            hero.islive = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e)                            //通过监视器处理移动、发射子弹问题
    {
        if (e.getKeyCode() == KeyEvent.VK_UP)                        //移动问题
        {
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hero.moveRight();
        }
        repaint();                                                //改变坐标后要重新画一遍
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1)                        //按F1关闭背景音乐
        {
            clip.stop();
        }
        if (e.getKeyCode() == KeyEvent.VK_F2)                        //按F2开启背景音乐
        {
            clip.loop();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE)                    //子弹问题,按空格发射
        {
            hero.OpenFire();
        }
        repaint();
    }

    public void run()                                    //每隔一段时间重新画图呈现子弹运动状态
    {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            for (int i = 0; i < hero.bullets.size(); i++)            //因为要不断更新则在run方法中检测是否击中
            {
                Shot shot = hero.bullets.get(i);
                if (shot.islive) {
                    for (int j = 0; j < enemy.size(); j++) {
                        Enemy e = enemy.get(j);
                        if (e.islive) {
                            hitEnemy(shot, e);
                            if (e.islive == false)                        //如果敌舰被击中
                            {
                                enemy.remove(e);
                                if (enemy.size() < 4)                //敌舰数量少于4就会自动补充
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
            for (int i = 0; i < enemy.size(); i++)                    //判断敌舰击中玩家战舰
            {
                Enemy em = enemy.get(i);
                for (int j = 0; j < em.bullets.size(); j++) {
                    Shot shot = em.bullets.get(j);
                    if (shot.islive) {
                        hitHero(shot, hero);
                        if (hero.islive == false)                        //玩家战舰被击中就跳出
                        {
                            break loop;
                        }
                    }
                }
            }
            repaint();
            if (hero.islive == false)                                    //直接break结束
            {
                clip.stop();                                        //游戏结束音乐停
                try {
                    FileReader in1 = new FileReader(topGoal);            //比较历史最佳成绩并更新成绩
                    BufferedReader in2 = new BufferedReader(in1);
                    String s = in2.readLine();
                    int top = Integer.parseInt(s);                    //将文件中最佳成绩导出并转化为int类型
                    if (goal > top) {
                        FileWriter out1 = new FileWriter(topGoal);
                        BufferedWriter out2 = new BufferedWriter(out1);
                        out2.write("" + goal);                        //创造新成绩就储存到文件中
                        out2.flush();
                        out2.close();
                        out1.close();
                        JOptionPane.showMessageDialog(this, "更新历史最佳成绩：" + goal, "得分", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "最终成绩为：" + goal + "    历史最佳成绩：" + top, "得分",
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
