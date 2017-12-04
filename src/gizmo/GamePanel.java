package gizmo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.tools.javac.util.Pair;
import physics.Vector;
import physics.geometry.*;
import physics.interfaces.CollisionInterface;
import physics.interfaces.MotionInterface;
import physics.interfaces.OperateInterface;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {
    public static int FRAMES_PER_SECOND = 100;
    private static int INDEX_BLOCK_NUMBER = 20;
    public static List<SegmentGeometry> segmentGeometries = Lists.newArrayList(
            new SegmentGeometry(new PointGeometry(10, 10), new PointGeometry(10, 650), false),
            new SegmentGeometry(new PointGeometry(10, 650), new PointGeometry(690, 650), true),
            new SegmentGeometry(new PointGeometry(690, 650), new PointGeometry(690, 10), false),
            new SegmentGeometry(new PointGeometry(690, 10), new PointGeometry(10, 10), true)
    );

    private PlayRoom playRoom;
    private Timer timer;
    private Geometry obstacle;
    private OperateInterface target = null;
    public static Ball ball, tempBall;
    private List<Geometry> obstacles;
    private List<Flipper> flippers;
    private List<MotionInterface> motionInterfaces;
    private List<CollisionInterface> collisionInterfaces;
    private List<PrintInterface> printInterfaces;
    private AnimationEventListener eventListener;
    private ReEditEventListener reEditEventListener;
    private Map<Pair<Integer, Integer>, List<Geometry>> obstacleIndex;
    public static int status;

    public GamePanel(PlayRoom playRoom) {
        //设置弹球窗口大小和背景
        this.playRoom = playRoom;
        status = 1;
        this.setSize(700, 700);
        this.setBackground(Color.WHITE);

        obstacles = Lists.newArrayList();
        flippers = Lists.newArrayList();
        motionInterfaces = Lists.newArrayList();
        collisionInterfaces = Lists.newArrayList();
        printInterfaces = Lists.newArrayList();
        //   obstacleIndex = Maps.newHashMap();
//        for (int i = 0; i < INDEX_BLOCK_NUMBER; i++) {
//            for (int j = 0; j < INDEX_BLOCK_NUMBER; j++) {
//                Pair<Integer, Integer> pair = new Pair<>(i, j);
//                obstacleIndex.put(pair, Lists.newArrayList());
//            }
//        }
    }

    public void addObstacle(Geometry newObstacle) {
        obstacle = newObstacle;
        if (reEditEventListener != null) {
            removeReEditListener();
        }
        EditEventListener editEventListener = getEditEventListener(newObstacle);
        addMouseListener(editEventListener);
        addMouseMotionListener(editEventListener);
        timer = new Timer(1000 / FRAMES_PER_SECOND, editEventListener);
        timer.start();
    }

    public void addBall(Ball ball) {
        this.ball = ball;
        if (reEditEventListener != null) {
            removeReEditListener();
        }
        EditEventListener editEventListener = getEditEventListener(ball);
        addMouseListener(editEventListener);
        addMouseMotionListener(editEventListener);
        timer = new Timer(1000 / FRAMES_PER_SECOND, editEventListener);
        timer.start();
    }


    public void endAddObstacle() {
        if (!obstacles.contains(obstacle)) {
            timer.stop();
            obstacles.add(obstacle);
            obstacle = null;
            playRoom.setEditMode(true);
        }
    }

    public void startGame() {
        if (ball == null) {
            throw new RuntimeException("ball has not place");
        }
        try {
            tempBall = ball.clone();
            tempBall.setConstantAcceleration(new Vector(0, 200));
            tempBall.setVelocity(new Vector(0, 0));
            motionInterfaces.add(tempBall);
            printInterfaces.add(tempBall);
            for (Geometry geometry : obstacles) {
                Geometry clone = (Geometry) geometry.clone();
                if (clone instanceof Flipper) {
                    flippers.add((Flipper) clone);
                }
                if (clone instanceof MotionInterface) {
                    motionInterfaces.add((MotionInterface) clone);
                }
                if (clone instanceof PrintInterface) {
                    printInterfaces.add((PrintInterface) clone);
                }
                if (clone instanceof CollisionInterface) {
                    collisionInterfaces.add((CollisionInterface) clone);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        collisionInterfaces.addAll(segmentGeometries);
        status = 2;
        if (reEditEventListener != null) {
            removeReEditListener();
        }
        eventListener = new AnimationEventListener();
        addMouseListener(eventListener);
        addMouseMotionListener(eventListener);
        addKeyListener(eventListener);
        requestFocus();
        timer = new Timer(1000 / FRAMES_PER_SECOND, eventListener);
        timer.start();
    }

    public void endGame() {
        flippers = Lists.newArrayList();
        motionInterfaces = Lists.newArrayList();
        collisionInterfaces = Lists.newArrayList();
        printInterfaces = Lists.newArrayList();
        status = 1;
        removeMouseListener(eventListener);
        removeMouseMotionListener(eventListener);
        removeKeyListener(eventListener);
        timer.stop();
        update();
        addReEditListener();
    }

    private void update() {
        for (MotionInterface motionInterface : motionInterfaces) {
            motionInterface.update();
            motionInterface.updateVelocity();
        }
        if (collisionInterfaces.size() > 0 && tempBall.isCanCollision()) {
            for (CollisionInterface collisionInterface : collisionInterfaces) {
                collisionInterface.onCollision(tempBall);
            }
        }
        for (int i = 0; i < collisionInterfaces.size() - 1; i++) {
            for (int j = i + 1; j < collisionInterfaces.size(); j++) {
                if (collisionInterfaces.get(i).onCollisionObstacle(collisionInterfaces.get(j))) {
                    //System.out.println(collisionInterfaces.get(i));
                    //System.out.println(collisionInterfaces.get(j));
                }
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(10, 10, 680, 640);
        switch (status) {
            case 1:
                for (Geometry geometry : obstacles) {
                    if (geometry instanceof PrintInterface) {
                        if (target == geometry) {
                            ((PrintInterface) geometry).print(Color.GREEN, g);
                            target.printEditBound(g);
                        } else {
                            ((PrintInterface) geometry).print(Color.BLUE, g);
                        }
                    }
                }
                if (obstacle != null && obstacle instanceof PrintInterface) {
                    ((PrintInterface) obstacle).drawing(Color.GREEN, g);
                }
                if (ball != null) {
                    if (ball == target) {
                        ball.drawing(Color.GREEN, g);
                        ball.printEditBound(g);
                    } else {
                        ball.drawing(Color.pink, g);
                    }
                }
                break;
            case 2:
                for (PrintInterface printInterface : printInterfaces) {
                    printInterface.print(Color.cyan, g);
                }
                break;
        }
    }

    class AnimationEventListener extends MouseAdapter implements MouseMotionListener, KeyListener, ActionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("mouseClicked");
        }

        public void mouseDragged(MouseEvent e) {

        }


        public void mouseMoved(MouseEvent e) {

        }

        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            System.out.println(keyCode);
            switch (keyCode) {
                case 37://方向键左
                    setFlippers(new Vector(-1, 0));
                    break;
                case 38://方向键上
                    setFlippers(new Vector(0, -1));
                    break;
                case 39://方向键右
                    setFlippers(new Vector(1, 0));
                    break;
                case 40://方向键下
                    setFlippers(new Vector(0, 1));
                    break;
                case 90://Z
                    for (Flipper flipper : flippers) {
                        flipper.rotateFlipper(-1);
                    }
                    break;
                case 88://X
                    for (Flipper flipper : flippers) {
                        flipper.rotateFlipper(1);
                    }
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
            for (Flipper flipper : flippers) {
                flipper.setVelocity(new Vector(Vector.ZERO));
            }
        }


        public void keyTyped(KeyEvent e) {

        }

        public void setFlippers(Vector acceleration) {
            for (Flipper flipper : flippers) {
                flipper.setInstantaneousAcceleration(acceleration);
            }
        }


        public void actionPerformed(ActionEvent e) {
            update();
        }
    }

    private EditEventListener getEditEventListener(Geometry newObstacle) {
        if (newObstacle instanceof TwoPointGeometry) {
            return new EditTwoPointEventListener();
        } else if (newObstacle instanceof Track) {
            return new EditPolygonLineEventListener();
        } else if (newObstacle instanceof QuadrilateralGeometry) {
            return new EditPolygonEventListener(4);
        } else {
            return new EditPolygonEventListener(3);
        }
    }

    private void addReEditListener() {
        reEditEventListener = new ReEditEventListener();
        addMouseListener(reEditEventListener);
        addMouseMotionListener(reEditEventListener);
        addMouseWheelListener(reEditEventListener);
        addKeyListener(reEditEventListener);
        requestFocus();
        timer = new Timer(1000 / FRAMES_PER_SECOND, reEditEventListener);
        timer.start();
    }

    private void removeReEditListener() {
        timer.stop();
        removeMouseListener(reEditEventListener);
        removeMouseMotionListener(reEditEventListener);
        removeMouseWheelListener(reEditEventListener);
        removeKeyListener(reEditEventListener);
        reEditEventListener = null;
    }

    abstract class EditEventListener extends MouseAdapter implements MouseMotionListener, ActionListener {
        protected int count;
        protected int currentCount;
        protected List<PointGeometry> pointGeometries;
        //private Image image;

        EditEventListener() {

        }

        protected void end() {
            if (obstacle != null) {
                endAddObstacle();
            } else {
                timer.stop();
                playRoom.setEditMode(true);
            }
            removeMouseListener(this);
            removeMouseMotionListener(this);
            addReEditListener();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        public void mouseDragged(MouseEvent e) {
        }


        public void mouseMoved(MouseEvent e) {

        }

        public void actionPerformed(ActionEvent e) {
            update();
        }
    }

    class EditTwoPointEventListener extends EditEventListener {

        EditTwoPointEventListener() {
            this.count = 2;
            currentCount = 0;
            pointGeometries = Lists.newArrayList();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("clickTwo" + e.getSource());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            pointGeometries.add(new PointGeometry(e.getPoint()));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            end();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //System.out.println("draggingTwo" + e.getSource());
            if (pointGeometries.size() == 1) {
                pointGeometries.add(new PointGeometry(e.getPoint()));
            } else {
                pointGeometries.remove(1);
                pointGeometries.add(new PointGeometry(e.getPoint()));
            }
            if (obstacle != null) {
                obstacle.reset(pointGeometries);
            } else {
                ball.reset(pointGeometries);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    class EditPolygonEventListener extends EditEventListener {

        EditPolygonEventListener(int count) {
            this.count = count;
            currentCount = 0;
            pointGeometries = Lists.newArrayList();
            //image = createImage(getWidth(), getHeight());
        }

        private void check() {
            if (currentCount == count + 1) {
                if (MathUtils.isApproximateEqual(pointGeometries.get(0), pointGeometries.get(count))) {
                    pointGeometries.remove(count);
                    end();
                } else {
                    pointGeometries.remove(count);
                    currentCount--;
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            //pointGeometries.add(new PointGeometry(e.getPoint()));
            currentCount++;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            check();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //System.out.println("draggingPoly" + e.getSource());
            while (pointGeometries.size() > currentCount) {
                pointGeometries.remove(currentCount);
            }
            pointGeometries.add(new PointGeometry(e.getPoint()));
            obstacle.reset(pointGeometries);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseDragged(e);
        }
    }

    class EditPolygonLineEventListener extends EditEventListener {

        EditPolygonLineEventListener() {
            pointGeometries = Lists.newArrayList();
        }


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            pointGeometries.add(new PointGeometry(e.getPoint()));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pointGeometries.add(new PointGeometry(e.getPoint()));
            end();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            pointGeometries.add(new PointGeometry(e.getPoint()));
            obstacle.reset(pointGeometries);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }


    class ReEditEventListener extends MouseAdapter implements MouseMotionListener, ActionListener, KeyListener {
        protected PointGeometry startPoint = null;

        ReEditEventListener() {
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 8://delete
                    if (target != null) {
                        obstacles.remove(target);
                    }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("click");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println("press" + e.getX());
            /*if (target != null) {

            }*/
            target = null;
            PointGeometry point = new PointGeometry(e.getX(), e.getY());
            if (ball != null && ball.isInside(point)) {
                target = ball;
                startPoint = point;
            } else {
                for (Geometry o : obstacles) {
                    if (o instanceof OperateInterface && ((OperateInterface) o).isInside(point)) {
                        target = (OperateInterface) o;
                        startPoint = point;
                    } else {
                        System.out.println("该组件无法重编辑");
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            startPoint = null;
            //System.out.println("release " + e.getX());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (startPoint != null) {
                //System.out.println(obstacles.size());
                PointGeometry point = new PointGeometry(e.getX(), e.getY());
                //System.out.println("drag from " + startPoint.getX() + " to " + point.getX());
                target.move(point.getX() - startPoint.getX(), point.getY() - startPoint.getY());
                startPoint.setX(point.getX());
                startPoint.setY(point.getY());
                //update();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            //System.out.println("滚动了" + e.getScrollAmount());
            if (target != null) {
                switch (e.getWheelRotation()) {
                    case -1:
                        target.zoom(e.getScrollAmount() * 1.1);
                        break;
                    case 1:
                        target.zoom(e.getScrollAmount() * 0.9);
                        break;
                }
            }
        }

        public void actionPerformed(ActionEvent e) {
            update();
        }
    }

}
