import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.*;
import java.util.List;

public class Java {
    MyPannel p;
    JFrame f1,f2;
    SwingWorker<Void,Void> gamePane;
    JLabel l1,l2,l3;
    JButton b1,b2,b3;
    int gridSize = 20;
    int i =0;
    List<Point> snake;
    char direction = 'R';
    private boolean running = true;
    int sl;
    int Foodx,Foody;
    Random rand = new Random();

    Java() {
        f1 = new JFrame("Snake Game ");
        l1 = new JLabel("Snake game Using java");
        l1.setBounds(180,30,360,60);
        l1.setForeground(Color.BLACK);
        l1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        l2 = new JLabel("Choose the dificulty level");
        l2.setBounds(230, 180, 360, 30);
        l2.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
        b1 = new JButton("Easy level");
        b2 = new JButton("Medium level");
        b3 = new JButton("Hard level");
        b1.setBounds(230,230,160,30);
        b2.setBounds(230,330,160,30);
        b3.setBounds(230,430,160,30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.setVisible(false);
                f2.setVisible(true);
                sl = 350;
                startGame();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.setVisible(false);
                f2.setVisible(true);
                sl = 200;
                startGame();
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.setVisible(false);
                f2.setVisible(true);
                sl = 100;
                startGame();
            }
        });
        BackgroundPanel1 backgroundPanel1 = new BackgroundPanel1(new ImageIcon("C:/Users/ngkv2/Downloads/OIP.jpg").getImage());
        backgroundPanel1.setLayout(null);
        f1.setLocationRelativeTo(null);
        backgroundPanel1.add(b1);
        backgroundPanel1.add(b2);
        backgroundPanel1.add(b3);
        backgroundPanel1.add(l1);
        backgroundPanel1.add(l2);
        f1.add(backgroundPanel1);
        f1.setVisible(true);
        f1.setSize(650,650);
        f1.setLocationRelativeTo(null);
        f2 = new JFrame("Snake Game");
        l3 = new JLabel("Score"+i);
        l3.setForeground(Color.white);
        l3.setFont(new Font("Arial",Font.BOLD, 18));
        snake = new ArrayList<>();
        snake.add(new Point(100,100));
        p = new MyPannel();
        p.setOpaque(false);
        p.setSize(600,600);
        BackgroundPanel1 gamePannel = new BackgroundPanel1(new ImageIcon("C:/Users/ngkv2/Downloads/th.jpg").getImage());
        gamePannel.setLayout(new BorderLayout());
        gamePannel.add(l3,BorderLayout.NORTH);
        gamePannel.add(p,BorderLayout.CENTER);
        f2.add(gamePannel);
        f2.setSize(650,650);
        f2.setLocationRelativeTo(null);
        f2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP:
                        if(direction != 'D') direction = 'U';
                        break;
                    case KeyEvent.VK_DOWN:
                        if(direction  !='U') direction = 'D';
                        break;
                    case KeyEvent.VK_LEFT:
                        if(direction != 'R') direction = 'L';
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(direction != 'L') direction = 'R';
                        break;

                }
            }
        });


    }

    public void startGame() {
        gamePane = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (running) {
                    moveSnake();
                    p.repaint();
                    Thread.sleep(sl);
                }
                return null;
            }
        };
        gamePane.execute();
    }
    public void foodGenerator() {
        int maxX = p.getWidth()/gridSize;
        int maxY = p.getHeight()/gridSize;
         Foodx = rand.nextInt(maxX)*gridSize;
         Foody = rand.nextInt(maxY)*gridSize;
    }
    public void moveSnake () {
        Point head = snake.get(0);
        Point newHead = new Point(head);
        switch (direction) {
            case 'U': newHead.y-=gridSize;break;
            case 'D': newHead.y+=gridSize;break;
            case 'R': newHead.x+=gridSize;break;
            case 'L': newHead.x-=gridSize;break;
        }
        if(newHead.x < 0|| newHead.x +gridSize > p.getWidth() || newHead.y < 0 || newHead.y > p.getHeight() ) {
            gameOver();
            return;
        }
        snake.add(0,newHead);
        if(!ateFood()) {
            snake.remove(snake.size()-1);
        }else {
            foodGenerator();
            i++;
            l3.setText("Score"+i);
        }
    }
    private void gameOver() {
        running = false;
        // Show a game over dialog
        JOptionPane.showMessageDialog(f2, "Game Over! Your score is: " + i, "Game Over", JOptionPane.INFORMATION_MESSAGE);


        System.exit(0);
    }
    private boolean ateFood() {
        Point head = snake.get(0);
        return (head.x == Foodx && head.y == Foody);
    }
    class  MyPannel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setForeground(Color.red);
            g.fillOval(Foodx, Foody, gridSize, gridSize);
            g.setColor(Color.YELLOW);
            for(Point segment:snake) {
                g.fillRect(segment.x, segment.y, gridSize, gridSize);
            }
        }
    }
    class BackgroundPanel1 extends JPanel {
        private Image image;
       public BackgroundPanel1(Image image) {
           this.image = image;
       }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(image != null) {
                g.drawImage(image,0,0,getWidth(),getHeight(),this);
            }
        }
    }

    public static void main(String[] args) {
        new Java();
    }

}