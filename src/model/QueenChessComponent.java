package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class QueenChessComponent extends ChessComponent{

    private static Image Queen_WHITE;
    private static Image Queen_BLACK;


    private Image queenImage;

    public void loadResource() throws IOException {
        if (Queen_WHITE == null) {
            Queen_WHITE = ImageIO.read(new File("images/queen-white.png"));
        }

        if (Queen_BLACK == null) {
            Queen_BLACK = ImageIO.read(new File("images/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = Queen_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = Queen_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();


        if(Math.abs(destination.getY()-source.getY()) == Math.abs((destination.getX()-source.getX()))){
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
        }else{
            if (source.getX() == destination.getX()) {
                int row = source.getX();
                for (int col = Math.min(source.getY(), destination.getY()) + 1;
                     col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (source.getY() == destination.getY()) {
                int col = source.getY();
                for (int row = Math.min(source.getX(), destination.getX()) + 1;
                     row < Math.max(source.getX(), destination.getX()); row++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else { // Not on the same row or the same column.
                return false;
            }
        }
        return true;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
    }



