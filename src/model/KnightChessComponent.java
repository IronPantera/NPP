package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KnightChessComponent extends  ChessComponent{

    private static Image Knight_WHITE;
    private static Image Knight_BLACK;

    private Image knightImage;



    public void loadResource() throws IOException {
        if (Knight_WHITE == null) {
            Knight_WHITE = ImageIO.read(new File("images/knight-white.png"));
        }

        if (Knight_BLACK == null) {
            Knight_BLACK = ImageIO.read(new File("images/knight-black.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = Knight_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = Knight_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();


                if(source.getX() +1 == destination.getX() && (source.getY() +2 == destination.getY() || source.getY()-2 == destination.getY())){
                    return true;
                }else if(source.getX() +2 == destination.getX() && (source.getY() +1 == destination.getY() || source.getY() - 1 == destination.getY())){
                    return  true;
                }else if(source.getX() - 1 == destination.getX() &&(source.getY()+2 == destination.getY() || source.getY()-2 == destination.getY())){
                    return true;
                }else return source.getX() - 2 == destination.getX() && (source.getY() + 1 == destination.getY() || source.getY() - 1 == destination.getY());
    }

//    public ArrayList<String> getCanMovePoint(){
//        ChessboardPoint source = getChessboardPoint();
//        ArrayList<String> knight = new ArrayList<>();
//        knight.add("("+source.getX()+1+","+ source.getY()+2+")" );
//        return knight;
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
    public static void writeTxtFile(String Text,String path) throws Exception {
        try {
            File file = new File(path);
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(Text);
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

