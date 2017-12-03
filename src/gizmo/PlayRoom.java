package gizmo;

import physics.geometry.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayRoom extends JFrame {
    public static PlayRoom playRoom;
    private JPanel operatePanel;
    private GamePanel gamePanel;
    private JButton start, stop, addAbsorb, end, addFlipper, addTriangle, addRectangle, addCircle, addBall;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                playRoom = new PlayRoom();
            }
        });
    }

    public void setEditMode(boolean mode) {
        addAbsorb.setEnabled(mode);
        addFlipper.setEnabled(mode);
        addTriangle.setEnabled(mode);
        addBall.setEnabled(mode);
        addCircle.setEnabled(mode);
        addRectangle.setEnabled(mode);
    }

    public void startGame() {
        start.setEnabled(false);
        stop.setEnabled(true);
        setEditMode(false);
        gamePanel.startGame();
    }

    public void endGame() {
        start.setEnabled(true);
        stop.setEnabled(false);
        setEditMode(true);
        gamePanel.endGame();
    }

    public PlayRoom() {
        this.setTitle("弹球测试样例");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);

        //添加弹球窗口
        gamePanel = new GamePanel(this);
        this.add(gamePanel);

        //添加操作窗口
        operatePanel = new JPanel();
        operatePanel.setLayout(null);
        operatePanel.setSize(200, 800);
        operatePanel.setBackground(Color.BLUE);
        this.add(operatePanel);

        //设置操作按钮
        start = new JButton("开始");
        start.setBounds(850, 50, 100, 30);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == start) {
                    startGame();
                }
            }
        });
        stop = new JButton("停止");
        stop.setBounds(850, 100, 100, 30);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == stop) {
                    endGame();
                }
            }
        });

        end = new JButton("结束游戏");
        end.setBounds(850, 150, 100, 30);
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == end) {
                    System.exit(0);
                }
            }
        });

        addAbsorb = new JButton("添加吸收器");
        addAbsorb.setBounds(850, 200, 100, 30);
        addAbsorb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addAbsorb) {
                    setEditMode(false);
                    gamePanel.addObstacle(new Absorber(PointGeometry.origin, PointGeometry.origin));
                }
            }
        });

        addFlipper = new JButton("添加挡板");
        addFlipper.setBounds(850, 250, 100, 30);
        addFlipper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addFlipper) {
                    setEditMode(false);
                    gamePanel.addObstacle(new Flipper(PointGeometry.origin, PointGeometry.origin));
                }
            }
        });

        addTriangle = new JButton("添加三角形障碍物");
        addTriangle.setBounds(850, 300, 100, 30);
        addTriangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addTriangle) {
                    setEditMode(false);
                    gamePanel.addObstacle(new TriangleGeometry(PointGeometry.origin, PointGeometry.origin, PointGeometry.origin));
                }
            }
        });

        addRectangle = new JButton("添加矩形障碍物");
        addRectangle.setBounds(850, 350, 100, 30);
        addRectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addRectangle) {
                    setEditMode(false);
                    gamePanel.addObstacle(new RectangleGeometry(PointGeometry.origin, PointGeometry.origin));
                }
            }
        });

        addCircle = new JButton("添加球形障碍物");
        addCircle.setBounds(850, 400, 100, 30);
        addCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addCircle) {
                    setEditMode(false);
                    gamePanel.addObstacle(new CircleGeometry(PointGeometry.origin, PointGeometry.origin));
                }
            }
        });

        addBall = new JButton("添加小球");
        addBall.setBounds(850, 450, 100, 30);
        addBall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addBall) {
                    setEditMode(false);
                    gamePanel.addBall(new CircleGeometry(PointGeometry.origin, PointGeometry.origin));
                }
            }
        });


        //将按钮添加到操作侧栏
        operatePanel.add(start);
        operatePanel.add(stop);
        operatePanel.add(end);
        operatePanel.add(addAbsorb);
        operatePanel.add(addFlipper);
        operatePanel.add(addTriangle);
        operatePanel.add(addRectangle);
        operatePanel.add(addCircle);
        operatePanel.add(addBall);

        //显示窗口
        this.setVisible(true);
    }
}
