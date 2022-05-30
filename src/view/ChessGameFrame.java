package view;



import controller.ClickController;
import controller.GameController;
import model.ChessColor;
import model.ChessComponent;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private  int WIDTH ;
    private  int HEIGTH ;
    public  int CHESSBOARD_SIZE;
    private GameController gameController;
    JLabel statusLabel;


    public ChessGameFrame(){
    }
    public JLabel getStatusLabel(){
        return statusLabel;
    }

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);



        addLabel();
        addChessboard();
        addWithdrawButton();
        addLoadButton();
        addRestartButton();
        addSaveButton();

        addBackground("images/Background1.png");
//        addChangeBackgroundButton();
    }

    private void addBackground(String path) {
        ImageIcon background;
        JPanel myPanel;
        JLabel label;
        background = new ImageIcon("images/Background1.png");	//创建一个背景图片
        background = new ImageIcon(path);	//创建一个背景图片
        label = new JLabel(background);		//把背景图片添加到标签里

//切换背景图片
        label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());	//把标签设置为和图片等高等宽
        myPanel = (JPanel)this.getContentPane();		//把我的面板设置为内容面板
        myPanel.setOpaque(false);					//把我的面板设置为不可视
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));		//把标签添加到分层面板的最底层
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
//    Partly copy from CSDN


//    private void changeBackground(int I){
//        String path1 = "images/Background1.png";
//        String path2 = "images/Background2.png";
//        String path;
//        if (I%2 == 0){
//            path = path1;
//        }
//        else {
//            path = path2;
//        }
//        addBackground(path);
//    }



    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setStatusLabel(statusLabel);
        chessboard.setLocation(HEIGTH / 2, HEIGTH / 10);
        add(chessboard);

    }
    //换封面


    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGTH/8, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

//    private void addChangeBackgroundButton() {
//        JButton button = new JButton("Change");
////        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
//        button.setLocation(HEIGTH/8, HEIGTH / 10 + 440);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            for (int i = 0; ; ) {
//                changeBackground(i);
//                i = i + 1;
//                break;
//            }
//        });
//    }


    private void addWithdrawButton() {
        JButton button = new JButton("Withdraw");
//        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH/8, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            if (ClickController.steps == 0){
                Frame frame = new JFrame("frame");
                JOptionPane.showMessageDialog(frame, "Can't Withdraw!", "Archive", JOptionPane.INFORMATION_MESSAGE);
            }else {
            int a = ClickController.steps - 1;
            System.out.println("Click withdraw");
            String path = "resource/saveboard" + a + ".txt";
            gameController.loadGameFromFile(path);
            if (ClickController.steps%2 == 0 ){
                statusLabel.setText(ChessColor.BLACK + "  " + a);
            }else {
                statusLabel.setText(ChessColor.WHITE + "  " + a);
            }

            ClickController.setSteps(a);

            }
            //Lable 要修一下
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH/8, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
           if (gameController.loadGameFromFile(path)) {
               gameController.loadGameFromFile(path);
               ClickController.setSteps(0);

           }
           else {
               Frame frame = new JFrame("frame");
               JOptionPane.showMessageDialog(frame, "Can't load!", "Archive", JOptionPane.INFORMATION_MESSAGE);
           }
        });
    }
    public void addSaveButton(){

        JButton button = new JButton("Save");
        button.setLocation(HEIGTH/8, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click Save");
            String name = JOptionPane.showInputDialog(this,"Input name here");
            gameController.saveGameFile(name);
            Frame frame = new JFrame("Frame");
            JOptionPane.showMessageDialog(frame, "Save archive successfully!", "Archive", JOptionPane.INFORMATION_MESSAGE);
        });

    }

    public void addRestartButton(){

        JButton button = new JButton("Restart");
        button.setLocation(HEIGTH/8,HEIGTH/10 + 360);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell",Font.BOLD,20));
        add(button);

        button.addActionListener(e -> {
            setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    ChessGameFrame mainFrame2 = new ChessGameFrame(1300, 710);
                    mainFrame2.setVisible(true);
                    ClickController.setSteps(0);
                });
        });
    }

}

