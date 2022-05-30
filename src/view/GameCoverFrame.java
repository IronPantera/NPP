package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GameCoverFrame extends JFrame {
    private  int WIDTH ;
    private  int HEIGTH ;
//    public  int CHESSBOARD_SIZE;
//    private GameController gameController;

    public GameCoverFrame(int width, int height){
        setTitle("2022 CS102A Project Demo Cover"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
//        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addStartButton();
        addBackground();
    }

    private void addStartButton(){
        JButton button = new JButton("Start");
        button.setLocation(HEIGTH/2, HEIGTH/2 );
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            BackgroundMusic.MainMusic.PlayMainMusic();
            BackgroundMusic.MenuBgm.Stop();
            System.out.println("Start");
            ChessGameFrame mainFrame = new ChessGameFrame(1300, 710);
            mainFrame.setVisible(true);
            this.setVisible(false);
        });
    }

    private void addBackground(){
        ImageIcon background;
        JPanel myPanel;
        JLabel label;

        background = new ImageIcon("images/St.Jiaran.png");	//创建一个背景图片
        label = new JLabel(background);		//把背景图片添加到标签里
        label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());	//把标签设置为和图片等高等宽
        myPanel = (JPanel)this.getContentPane();		//把我的面板设置为内容面板
        myPanel.setOpaque(false);					//把我的面板设置为不可视
        myPanel.setLayout(new FlowLayout());		//把我的面板设置为流动布局
//        this.getLayeredPane().setLayout(null);		//把分层面板的布局置空
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));		//把标签添加到分层面板的最底层
//        this.setBounds(300, 300, background.getIconWidth(), background.getIconHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }



}
