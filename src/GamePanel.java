import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.tools.javac.util.Pair;
import physics.geometry.Geometry;
import physics.geometry.PointGeometry;
import physics.geometry.TwoPointGeometry;
import physics.interfaces.PrintInterface;
import physics.math.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {
    private static int FRAMES_PER_SECOND = 100;
    private static int INDEX_BLOCK_NUMBER = 20;
    private PlayRoom playRoom;
    private Timer timer;
    private Geometry obstacle;
    private List<Geometry> obstacles;
    private AnimationEventListener eventListener;
    private Map<Pair<Integer, Integer>, List<Geometry>> obstacleIndex;

    public GamePanel(PlayRoom playRoom) {
        //设置弹球窗口大小和背景
        this.playRoom = playRoom;
        this.setSize(800, 800);
        this.setBackground(Color.WHITE);

        obstacles = Lists.newArrayList();

        obstacleIndex = Maps.newHashMap();
        for (int i = 0; i < INDEX_BLOCK_NUMBER; i++) {
            for (int j = 0; j < INDEX_BLOCK_NUMBER; j++) {
                Pair<Integer, Integer> pair = new Pair<>(i, j);
                obstacleIndex.put(pair, Lists.newArrayList());
            }
        }
    }

    public void addObstacle(Geometry newObstacle) {
        obstacle = newObstacle;
        EditEventListener editEventListener = getEditEventListener(newObstacle);
        addMouseListener(editEventListener);
        addMouseMotionListener(editEventListener);
        timer = new Timer(1000 / FRAMES_PER_SECOND, editEventListener);
        timer.start();
    }

    public void endAddObstacle() {
        timer.stop();
        obstacles.add(obstacle);

        obstacle = null;
        playRoom.setEditMode(true);
    }

    public void startGame() {
        eventListener = new AnimationEventListener();
        addMouseListener(eventListener);
        addMouseMotionListener(eventListener);
        addKeyListener(eventListener);
        requestFocus(); // make sure keyboard is directed to us
        timer = new Timer(1000 / FRAMES_PER_SECOND, eventListener);
        timer.start();
    }

    public void endGame() {
        removeMouseListener(eventListener);
        removeMouseMotionListener(eventListener);
        removeKeyListener(eventListener);
        timer.stop();
    }

    private void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Geometry geometry : obstacles) {
            if (geometry instanceof PrintInterface) {
                ((PrintInterface) geometry).print(Color.BLACK, g);
            }
        }
        if (obstacle != null && obstacle instanceof PrintInterface) {
            ((PrintInterface) obstacle).drawing(Color.GREEN, g);
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
            //
            int keynum = e.getKeyCode();

            if ((keynum >= 65) && (keynum <= 74)) {
                System.out.println("keypress " + e.getKeyCode());

            }
        }

        public void keyReleased(KeyEvent e) {

        }


        public void keyTyped(KeyEvent e) {

        }


        public void actionPerformed(ActionEvent e) {
            update();
        }
    }

    private EditEventListener getEditEventListener(Geometry newObstacle) {
        if (newObstacle instanceof TwoPointGeometry) {
            return new EditTwoPointEventListener();
        } else {
            return new EditPolygonEventListener(3);
        }
    }

    abstract class EditEventListener extends MouseAdapter implements MouseMotionListener, ActionListener {
        protected int count;
        protected int currentCount;
        protected List<PointGeometry> pointGeometries;
        //private Image image;

        EditEventListener() {

        }

        protected void end() {
            endAddObstacle();
            removeMouseListener(this);
            removeMouseMotionListener(this);
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
            if (pointGeometries.size() == 1) {
                pointGeometries.add(new PointGeometry(e.getPoint()));
            } else {
                pointGeometries.remove(1);
                pointGeometries.add(new PointGeometry(e.getPoint()));
            }
            obstacle.reset(pointGeometries);
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
}
