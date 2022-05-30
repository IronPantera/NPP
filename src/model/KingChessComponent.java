package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KingChessComponent extends ChessComponent{

    private static Image King_WHITE;
    private static Image King_BLACK;

    private Image kingImage;

    public void loadResource() throws IOException {
        if (King_WHITE == null) {
            King_WHITE = ImageIO.read(new File("images/king-white.jpg"));
        }

        if (King_BLACK == null) {
            King_BLACK = ImageIO.read(new File("images/king-black.png"));
        }
        System.out.println("i am down");
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = King_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = King_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKingImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();

        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                if(source.getX() +i == destination.getX() && source.getY() +j == destination.getY()){
                    return true;
                }
                }
            }
        return false;
        }

        //将军
    public boolean killingTime() {
        ChessboardPoint source = getChessboardPoint();
        ArrayList<Integer> kingStepX = new ArrayList<>();
        ArrayList<Integer> kingStepY = new ArrayList<>();

        for(int i = -1; i<2; i++) {
            for (int j = -1; j < 2; j++) {
                 kingStepX.add(source.getX()+i);
                 kingStepY.add(source.getY()+j);
            }
        }

        System.out.println(kingStepX+","+kingStepY);
        return true;
            }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}

