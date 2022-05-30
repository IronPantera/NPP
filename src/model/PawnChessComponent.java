package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class PawnChessComponent extends ChessComponent{

    private static Image Pawn_WHITE;
    private static Image Pawn_BLACK;


    public static int pawnLol ;

    public static int pawnIOUW = 0;
    public static int pawnIOUB = 0;


    public static void setPawnIOUW(int pawnIOU) {
        PawnChessComponent.pawnIOUW = pawnIOU;
    }

    public static void setPawnIOUB(int pawnIOU) {
        PawnChessComponent.pawnIOUB = pawnIOU;
    }

    public static void setPawnLol(int pawnLol) {
        PawnChessComponent.pawnLol = pawnLol;
    }

    private Image pawnImage;

    public void loadResource() throws IOException {
        if (Pawn_WHITE == null) {
            Pawn_WHITE = ImageIO.read(new File("images/pawn-white.png"));
        }

        if (Pawn_BLACK == null) {
            Pawn_BLACK = ImageIO.read(new File("images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = Pawn_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = Pawn_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        ChessColor color = getChessColor();


        //if(source.getY()+1 <8)
        if(chessComponents[source.getX()][source.getY()].getChessColor() == ChessColor.BLACK &&source.getX() == 4 && destination.getX() == 5 && pawnIOUW !=0
                && source.getY() != destination.getY()){

            if(destination.getY() == (source.getY()+1) || destination.getY() ==  source.getY()-1){
                if(source.getY()+1 <8 || source.getY() -1 >=0) {
                    if (source.getY() + 1 < 8 && source.getY() - 1 >= 0) {
                        if (chessComponents[source.getX()][source.getY() + 1] instanceof PawnChessComponent
                                || chessComponents[source.getX()][source.getY() - 1] instanceof PawnChessComponent) {
                            if (chessComponents[source.getX()][source.getY() + 1].getChessColor() == ChessColor.WHITE ||
                                    (chessComponents[source.getX()][source.getY() - 1].getChessColor() == ChessColor.WHITE)) {
                                setPawnLol(1);
                                return true;
                            } else return false;
                        } else return false;

                    } else if (source.getY() - 1 < 0 && chessComponents[source.getX()][source.getY() + 1] instanceof PawnChessComponent) {//左1列
                        if (chessComponents[source.getX()][source.getY() + 1].getChessColor() == ChessColor.WHITE) {
                            setPawnLol(1);
                            return true;
                        } else return false;

                    } else if (source.getY() + 1 > 7 && chessComponents[source.getX()][source.getY() - 1] instanceof PawnChessComponent) {//右1列
                        if (chessComponents[source.getX()][source.getY() - 1].getChessColor() == ChessColor.WHITE) {
                            setPawnLol(1);
                            return true;
                        } else return false;
                    } else return false;
                }else return false;
            }else return false;//过路兵黑
        }else if(chessComponents[source.getX()][source.getY()].getChessColor() == ChessColor.WHITE &&source.getX() == 3 && destination.getX() == 2 && pawnIOUB !=0){
            //过路兵白
            System.out.println(pawnIOUW);
            if(destination.getY() == (source.getY()+1) || destination.getY() ==  source.getY()-1){
                if(source.getY()+1 <8 || source.getY() -1 >=0) {
                    if (source.getY() + 1 < 8 && source.getY() - 1 >= 0) {
                        if (chessComponents[source.getX()][source.getY() + 1] instanceof PawnChessComponent
                                || chessComponents[source.getX()][source.getY() - 1] instanceof PawnChessComponent) {
                            if (chessComponents[source.getX()][source.getY() + 1].getChessColor() == ChessColor.BLACK||
                                    (chessComponents[source.getX()][source.getY() - 1].getChessColor() == ChessColor.BLACK)) {
                                setPawnLol(1);
                                return true;
                            } else return false;
                        } else return false;

                    } else if (source.getY() - 1 < 0 && chessComponents[source.getX()][source.getY() + 1] instanceof PawnChessComponent) {//左1列
                        if (chessComponents[source.getX()][source.getY() + 1].getChessColor() == ChessColor.BLACK) {
                            setPawnLol(1);
                            return true;
                        } else return false;

                    } else if (source.getY() + 1 > 7 && chessComponents[source.getX()][source.getY() - 1] instanceof PawnChessComponent) {//右1列
                        if (chessComponents[source.getX()][source.getY() - 1].getChessColor() == ChessColor.BLACK) {
                            setPawnLol(1);
                            return true;
                        } else return false;
                    } else return false;
                }else return false;
            }else return false;//过路兵白
        }//吃过路兵判定对方棋子是否在第一时间被吃掉
        else if(source.getY() == destination.getY()){
            if(color == ChessColor.WHITE){
                if(destination.getX() < source.getX()){
                    if(source.getX() == 6){
                        if(Math.abs(destination.getX() - source.getX()) == 2
                                && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE){
                            setPawnIOUW(1);
                        }
                        return Math.abs(destination.getX() - source.getX()) <= 2 && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE;
                    }else return source.getX() - destination.getX() == 1 && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE;
                }else{
                    return  false;
                }
            }else if(color == ChessColor.BLACK){
                if(destination.getX() > source.getX()){
                    if(source.getX() == 1){
                        if(Math.abs(destination.getX() - source.getX()) == 2
                                && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE){
                            setPawnIOUB(2);
                        }
                        return destination.getX() - source.getX() <= 2 && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE;
                    }else return destination.getX() - source.getX() == 1 && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE;
                }else{
                    return false;
                }
            }else return false;
        }else if(chessComponents[destination.getX()][destination.getY()].chessColor != color
                && chessComponents[destination.getX()][destination.getY()].chessColor != ChessColor.NONE ) {
            if(color == ChessColor.WHITE){
                if((source.getX() - 1 >=0 && source.getY() -1 >=0 || source.getX() -1 >= 0 && source.getY() +1 <8)
                         ) {

                        if ( source.getY() + 1 >= 8) {
                            if(chessComponents[source.getX() - 1][source.getY() - 1].chessColor == ChessColor.BLACK)
                            return source.getX() - 1 == destination.getX() && source.getY() - 1 == destination.getY();
                        } else if (  source.getY() - 1 < 0) {
                            if(chessComponents[source.getX() - 1][source.getY() + 1].chessColor == ChessColor.BLACK)
                            return source.getX() - 1 == destination.getX() && source.getY() + 1 == destination.getY();
                        } else
                            return ((source.getX() - 1 == destination.getX() && source.getY() - 1 == destination.getY())
                                    || source.getX() - 1 == destination.getX() && source.getY() + 1 == destination.getY());

                }
            }else if(color == ChessColor.BLACK){
                if((source.getX() + 1 <8 && source.getY() -1 >=0 || source.getX() +1 <8 && source.getY() +1 <8)
                         ) {
                        if ( source.getY() + 1 >= 8) {
                            if(chessComponents[source.getX() + 1][source.getY() - 1].chessColor == ChessColor.WHITE)
                            return source.getX() + 1 == destination.getX() && source.getY() - 1 == destination.getY();
                        } else if (  source.getY() - 1 < 0) {
                            if(chessComponents[source.getX() + 1][source.getY() + 1].chessColor == ChessColor.WHITE)
                            return source.getX() + 1 == destination.getX() && source.getY() + 1 == destination.getY();
                        } else
                            return ((source.getX() + 1 == destination.getX() && source.getY() - 1 == destination.getY())
                                    || source.getX() + 1 == destination.getX() && source.getY() + 1 == destination.getY());
                }
            }//常规行棋
        }else return  false;

        return false;
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
