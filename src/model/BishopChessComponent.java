package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent{

    private static Image Bishop_WHITE;
    private static Image Bishop_BLACK;


    private Image bishopImage;

    public void loadResource() throws IOException {
        if (Bishop_WHITE == null) {
            Bishop_WHITE = ImageIO.read(new File("images/bishop-white.png"));
        }

        if (Bishop_BLACK == null) {
            Bishop_BLACK = ImageIO.read(new File("images/bishop-black.png"));
        }
    }

            private void initiateBishopImage(ChessColor color) {
                try {
                    loadResource();
                    if (color == ChessColor.WHITE) {
                        bishopImage = Bishop_WHITE;
                    } else if (color == ChessColor.BLACK) {
                        bishopImage = Bishop_BLACK;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
                super(chessboardPoint, location, color, listener, size);
                initiateBishopImage(color);
            }

            @Override
            public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
                ChessboardPoint source = getChessboardPoint();

                if(source.getY() == destination.getY() || source.getX() == destination .getX()) {
                    return false;
                }else if(Math.abs(destination.getY()-source.getY()) != Math.abs((destination.getX()-source.getX()))){
                    return false;
                }else{
                    int cnt1 = 0;
                    int cnt2 = 0;
                    int cnt3 = 0;
                    int cnt4 = 0;
                    for (int i = 1; i < 8; i++) {
                        if(source.getX() < destination.getX() && source.getY() < destination.getY()){
                        if(((source.getX() + i) <= destination.getX() && (source.getY()+i) <= destination.getY())) {
                            if (!((chessComponents[source.getX() + i][source.getY() + i] instanceof EmptySlotComponent))) {
                                cnt1++;
                            }
                            if(cnt1 ==1 ) return ((destination.getX() == source.getX() + i) && (destination.getY() == source.getY() + i));
                        }
                        }else if(source.getX() < destination.getX() && source.getY() > destination.getY()) {
                            if(((source.getX() + i) <= destination.getX() && (source.getY() - i) >= destination.getY()) ) {
                                if (!((chessComponents[source.getX() + i][source.getY() - i] instanceof EmptySlotComponent))) {
                                    cnt2++;
                                }
                                if(cnt2 ==1) return  ((destination.getX() == source.getX() + i) && (destination.getY() == source.getY() - i));
                            }
                        }else if(source.getX() > destination.getX() && source.getY() > destination.getY()){
                            if(((source.getX() - i) >= destination.getX() && (source.getY()-i) >= destination.getY()) ) {
                                if (!((chessComponents[source.getX() - i][source.getY() - i] instanceof EmptySlotComponent))) {
                                    cnt3++;
                                }
                                if(cnt3 == 1) return  ((destination.getX() == source.getX() - i) && (destination.getY() == source.getY() - i));
                            }
                        }else if(source.getX() > destination.getX() && source.getY() < destination.getY()){
                            if(((source.getX() - i) >= destination.getX() && (source.getY() + i) <= destination.getY()) ) {
                                if (!((chessComponents[source.getX() - i][source.getY() + i] instanceof EmptySlotComponent))) {
                                    cnt4++;
                                }
                                if(cnt4 ==1) return  ((destination.getX() == source.getX() - i) && (destination.getY() == source.getY() + i));
                            }
                        }
                    }
                    return true;
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
                g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
                g.setColor(Color.BLACK);
                if (isSelected()) { // Highlights the model if selected.
                    g.setColor(Color.RED);
                    g.drawOval(0, 0, getWidth() , getHeight());
                }
            }
        }
