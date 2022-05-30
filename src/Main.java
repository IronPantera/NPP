import view.BackgroundMusic;
import view.ChessGameFrame;
import view.GameCoverFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameCoverFrame startFrame = new GameCoverFrame(601,935);
            startFrame.setVisible(true);
            BackgroundMusic.MenuBgm.PlayMenuBgm();
        });
    }
}
