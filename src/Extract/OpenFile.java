package Extract;

import java.awt.Desktop;
import java.io.*;
public class OpenFile {
    public void showFile(String file1) {
        try {
            File file = new File(file1);
            if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if (file.exists())
                desktop.open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}