import javax.swing.*;
import java.awt.*;

public class RandProductMaker {
    public static void main(String[] args) {
        double dHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double dWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        int height = ((int)(dHeight * .75));
        int width = ((int)(dWidth * .75));

        JFrame frame = new RandProductFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
}
}
