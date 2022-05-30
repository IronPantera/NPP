package controller;


import model.*;
import view.BackgroundMusic;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ClickController extends JFrame{
    private final Chessboard chessboard;
    private ChessComponent first;


    public ArrayList<Integer> ifWillLose = new ArrayList<>();
    private ChessColor color ;
    private ChessboardPoint firstHandled;
    private ChessComponent First;
    private ChessboardPoint kingLocation;
    private ChessboardPoint pawnLocation;
    public static int steps = 0;


    private double a =  0.0;

    private double b = 0.0;

    private ChessboardPoint destination;
//    private ChessComponent source;

    public static void setSteps(int S){
        steps = S;
    }


    public void setDestination(ChessboardPoint destination) {
        this.destination = destination;
    }

    public void setKingLocation(ChessboardPoint kingLocation) {
        this.kingLocation = kingLocation;
    }


    public ChessboardPoint getKingLocation(ChessComponent[][] chessComponents) {
        for(int i= 0; i<8 ;i++){
            for(int j = 0 ; j<8;j++){
                if(chessComponents[i][j] instanceof KingChessComponent
                        && chessboard.getCurrentColor() != chessComponents[i][j].getChessColor()){
                    setKingLocation(chessComponents[i][j].getChessboardPoint());
                }
            }
        }
        return kingLocation;
    }



    public void setFirstHandled(ChessboardPoint firstHandled) {
        this.firstHandled = firstHandled;
    }

    public ChessColor getColor() {
        return color;
    }

    public void setColor(ChessColor color) {
        this.color = color;
    }

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.

                if(PawnChessComponent.pawnLol == 1) {
                    if (PawnChessComponent.pawnIOUW == 1 && chessboard.getCurrentColor() == ChessColor.BLACK) {
                        chessboard.swapChessComponents(first, getIOIPawnLocation(chessboard.getChessComponents()));
                        PawnChessComponent.setPawnLol(0);

                    } else if (PawnChessComponent.pawnIOUB == 2 && chessboard.getCurrentColor() == ChessColor.WHITE) {
                        chessboard.swapChessComponents(first, getIOIPawnLocation(chessboard.getChessComponents()));
                        PawnChessComponent.setPawnLol(0);

                    }
                }
                    a++;

                b = a%2;
                System.out.println(a);
                System.out.println(b);
                if( b == 0){
                    PawnChessComponent.setPawnIOUB(0);
                }else PawnChessComponent.setPawnIOUW(0);//过路兵

                chessboard.swapChessComponents(first, chessComponent);
               if(first.canMoveTo(chessboard.getChessComponents(), getKingLocation(chessboard.getChessComponents()))){
                   JOptionPane.showMessageDialog(null,"CHECK!!!");
                   System.out.println("CHECK!!!");//将军
               }
               first = first;
               blackGaiYa();
               whiteGaiYa();


                chessboard.swapColor();
                steps++;
                BackgroundMusic.ClickSound.PlayClickSound();
                chessboard.autoSave();
                first.setSelected(false);
                first = null;

            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */
//点击 和 将军
    private boolean handleFirst(ChessComponent chessComponent) {
        First = chessComponent;
        if(chessComponent instanceof EmptySlotComponent) return false;
         setFirstHandled(chessComponent.getChessboardPoint());//选中

        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        System.out.println(First.getClass());
        System.out.println(chessboard.getCurrentColor());
        System.out.println(PawnChessComponent.pawnIOUW+"W");
        System.out.println(PawnChessComponent.pawnIOUB+"B");


        setDestination(chessComponent.getChessboardPoint());//移动
        ifWillLose.add(chessComponent.getX() + chessComponent.getY());
        setColor(chessboard.getCurrentColor());
        if(chessComponent.getChessColor() != chessboard.getCurrentColor()
                && first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint())
                && chessComponent instanceof KingChessComponent){

            System.out.println("Game over. The winner is:   "+ chessboard.getCurrentColor());
            JOptionPane.showMessageDialog(null,"Game over. The winner is:   "+ chessboard.getCurrentColor());


            return true;
        }

            return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
        }


    public ChessComponent getIOIPawnLocation(ChessComponent[][] chessComponents) {
        if (chessboard.getCurrentColor() == ChessColor.BLACK) {
              return   chessComponents[destination.getX()-1][destination.getY()];
        }else return chessComponents[destination.getX()+1][destination.getY()];
    }

    //底线升变
    public void blackGaiYa(){
        JFrame blackOption = new JFrame();
        if(chessboard.getCurrentColor() == ChessColor.BLACK
                && First instanceof PawnChessComponent
                && First.getChessboardPoint().getX() == 7){
            blackOption.setSize(800,57);
            blackOption.setLocationRelativeTo(null);
            blackOption.setLayout(null);
            System.out.println("BLACK is coming");
            blackOption.setVisible(true);
            JButton rook = new JButton("Rook");
            rook.setLocation(0, 0 );
            rook.setSize(200, 20);
            rook.setFont(new Font("Rockwell", Font.BOLD, 15));
            blackOption.add(rook);
            rook.addActionListener((e) -> {
                ChessComponent chessComponent = new RookChessComponent(First.getChessboardPoint(),
                        calculatePoint(First.getX(),First.getY()),ChessColor.BLACK,chessboard.getClickController(),75);
                chessComponent.setVisible(true);
                chessboard.swapChessComponents(chessComponent,First);
                chessboard.putChessOnBoard(chessComponent);
                blackOption.setVisible(false);
            });
            System.out.println("rook");

            JButton queen = new JButton("Queen");
            queen.setLocation(200, 0 );
            queen.setSize(200, 20);
            queen.setFont(new Font("Rockwell", Font.BOLD, 15));
            blackOption.add(queen);
            queen.addActionListener((e) -> {
                ChessComponent chessComponent = new QueenChessComponent(First.getChessboardPoint(),
                        calculatePoint(First.getX(),First.getY()),ChessColor.BLACK,chessboard.getClickController(),75);
                chessComponent.setVisible(true);
                chessboard.swapChessComponents(chessComponent,First);
                chessboard.putChessOnBoard(chessComponent);
                blackOption.setVisible(false);
            });
            System.out.println("queen");

            JButton bishop = new JButton("Bishop");
            bishop.setLocation(400, 0 );
            bishop.setSize(200, 20);
            bishop.setFont(new Font("Rockwell", Font.BOLD, 15));
            blackOption.add(bishop);
            bishop.addActionListener((e) -> {
                ChessComponent chessComponent = new BishopChessComponent(First.getChessboardPoint(),
                        calculatePoint(First.getX(),First.getY()),ChessColor.BLACK,chessboard.getClickController(),75);
                chessComponent.setVisible(true);
                chessboard.swapChessComponents(chessComponent,First);
                chessboard.putChessOnBoard(chessComponent);
                blackOption.setVisible(false);
            });
            System.out.println("bishop");

            JButton knight = new JButton("Knight");
            knight.setLocation(600, 0 );
            knight.setSize(200, 20);
            knight.setFont(new Font("Rockwell", Font.BOLD, 15));
            blackOption.add(knight);
            knight.addActionListener((e) -> {
                ChessComponent chessComponent = new KnightChessComponent(First.getChessboardPoint(),
                        calculatePoint(First.getX(),First.getY()),ChessColor.BLACK,chessboard.getClickController(),75);
                chessComponent.setVisible(true);
                chessboard.swapChessComponents(chessComponent,First);
                chessboard.putChessOnBoard(chessComponent);
                blackOption.setVisible(false);
            });
            System.out.println(knight);
        }
    }

    public void whiteGaiYa(){
        JFrame whiteOption = new JFrame();
        if(chessboard.getCurrentColor() == ChessColor.WHITE
                && First instanceof PawnChessComponent
                && First.getChessboardPoint().getX() == 0){
            whiteOption.setSize(800,57);
            whiteOption.setLocationRelativeTo(null);
            whiteOption.setLayout(null);
            System.out.println("White is coming");
            whiteOption.setVisible(true);
            JButton rook = new JButton("Rook");
            rook.setLocation(0, 0 );
            rook.setSize(200, 20);
            rook.setFont(new Font("Rockwell", Font.BOLD, 15));
            whiteOption.add(rook);
            rook.addActionListener((e) -> {
                ChessComponent chessComponent = new RookChessComponent(First.getChessboardPoint(),
                        calculatePoint(First.getX(),First.getY()),ChessColor.WHITE,chessboard.getClickController(),75);
                chessComponent.setVisible(true);
                chessboard.swapChessComponents(chessComponent,First);
                chessboard.putChessOnBoard(chessComponent);
                whiteOption.setVisible(false);
            });
            System.out.println("rook");

            JButton queen = new JButton("Queen");
            queen.setLocation(200, 0 );
            queen.setSize(200, 20);
            queen.setFont(new Font("Rockwell", Font.BOLD, 15));
            whiteOption.add(queen);
            queen.addActionListener((e) -> {
                ChessComponent chessComponent = new QueenChessComponent(First.getChessboardPoint(),
                        calculatePoint(First.getX(),First.getY()),ChessColor.WHITE,chessboard.getClickController(),75);
                chessComponent.setVisible(true);
                chessboard.swapChessComponents(chessComponent,First);
                chessboard.putChessOnBoard(chessComponent);
                whiteOption.setVisible(false);
            });
            System.out.println("queen");

            JButton bishop = new JButton("Bishop");
            bishop.setLocation(400, 0 );
            bishop.setSize(200, 20);
            bishop.setFont(new Font("Rockwell", Font.BOLD, 15));
            whiteOption.add(bishop);
            bishop.addActionListener((e) -> {
                ChessComponent chessComponent = new BishopChessComponent(First.getChessboardPoint(),
                        calculatePoint(First.getX(),First.getY()),ChessColor.WHITE,chessboard.getClickController(),75);
                chessComponent.setVisible(true);
                chessboard.swapChessComponents(chessComponent,First);
                chessboard.putChessOnBoard(chessComponent);
                whiteOption.setVisible(false);
            });
            System.out.println("bishop");

            JButton knight = new JButton("Knight");
            knight.setLocation(600, 0 );
            knight.setSize(200, 20);
            knight.setFont(new Font("Rockwell", Font.BOLD, 15));
            whiteOption.add(knight);
            knight.addActionListener((e) -> {
                ChessComponent chessComponent = new KnightChessComponent(First.getChessboardPoint(),
                        calculatePoint(First.getX(),First.getY()),ChessColor.WHITE,chessboard.getClickController(),75);
                chessComponent.setVisible(true);
                chessboard.swapChessComponents(chessComponent,First);
                chessboard.putChessOnBoard(chessComponent);
                whiteOption.setVisible(false);
            });
            System.out.println(knight);
        }

    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * 125, row * 125);
    }








    //遮挡保护

//斜线式 直线式 跳跃式
    //x+y =0 (斜线） x=0或y = 0(直线）

}
