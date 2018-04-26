import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JFrame implements ActionListener{
    JButton open=null;
    String selectMode = "file";
    private JFileChooser jfc;
    private File curFile;
    private String author;
    private ArrayList<MouseDrawCircle.MyCircle> pCircles = new ArrayList<>();
    private Map<String,String> map;
    private MouseDrawCircle drawWidget2;
    private String choosertitle;
    private String filenameInfile;


    public FileChooser(String option, MouseDrawCircle drawWidget){
        drawWidget2 = drawWidget;
        switch (option) {
            case "open file": selectMode = "file";break;
            case "save file": selectMode = "dir";break;
        }

        open=new JButton(option);
        this.add(open);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        open.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jfc =new JFileChooser();
        switch (selectMode) {
            case "file": {
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new JLabel(), "Choose");

                curFile=jfc.getSelectedFile();
                System.out.println("Folder:"+curFile.getAbsolutePath());
                break;
            }
            case "dir": {
                jfc.setDialogTitle(choosertitle);
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.setAcceptAllFileFilterUsed(false);
                //
                if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): "
                            +  jfc.getCurrentDirectory());
                    System.out.println("getSelectedFile() : "
                            +  jfc.getSelectedFile());
                }
                else {
                    System.out.println("No Selection ");
                }
                curFile = jfc.getSelectedFile();
                break;
            }
        }

        if(curFile.isDirectory()){
            System.out.println("Folder:"+curFile.getAbsolutePath());
        }else if(curFile.isFile()){

        }
        System.out.println(jfc.getSelectedFile().getName());

        switch (selectMode) {
            case "file":openFile();break;
            case "dir": saveFile();break;
            default: break;
        }

    }

    public File getFile() {
        return curFile;
    }


    public void saveFile() {

        String path = curFile.getAbsolutePath();
//        System.out.println("path = [" + path + "]");
        String author = FinalProject.authorTextField.getText();
        String filename = FinalProject.filenameTextField.getText();
        if (filename == null || filename.length() == 0) {
            filename = "Untitled";
        }
        if (author == null || author.length() == 0) {
            author = "Unknown";
        }
        drawWidget2.map.put(author, filename);
        FinalProject.writeCirclesToFile(drawWidget2.circles, path + "/" + filename + ".cyc", author, drawWidget2);
    }
    private void openFile() {
        readFile();
        FinalProject.authorTextField.setText(author);

        String filename = curFile.getName();
        filename = filename.substring(0, filename.length() - 4);
        System.out.println("filename " + filename);
        drawWidget2.map.clear();
        drawWidget2.map.put(author, filename);
        FinalProject.filenameTextField.setText(filename);
        drawWidget2.circles.clear();

        drawWidget2.circles = new ArrayList<>(pCircles);
        drawWidget2.repaint();

    }
    private void readFile() {
        try{
            map = new TreeMap<String,String>();
            Scanner scanner = new Scanner(curFile);

            while (scanner.hasNext()){
                String line = scanner.nextLine();
                int delimiter = line.indexOf("|");
                if (delimiter == -1) {
                    int delimiter2 = line.indexOf("_");
                    author = line.substring(0,delimiter2);
                    filenameInfile = line.substring(delimiter2+1);
                    System.out.println("filename 136" + filenameInfile);

                    continue;
                }
                String key = line.substring(0,delimiter);
                String value = line.substring(delimiter+1);
                map.put(key,value);
            }
            scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("Problem reading map from file "+e);
        }
        convertToMyCircle();
    }

    private void convertToMyCircle() {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        pCircles.clear();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("map entry ");
//            System.out.println("entry.key=" + entry.getKey());
            String[] param = entry.getValue().split("_");
            double x = Double.parseDouble(param[0]);
            double y = Double.parseDouble(param[1]);
            double radius = Double.parseDouble(param[2]);
            boolean filled = Integer.parseInt(param[3]) == 1;

            String colorS = param[4];
            String pattern = "(\\D*)(\\d+)(\\D*)(\\d+)(\\D*)(\\d+)(\\D*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(colorS);
            int red = 0, g = 0, b =0;
            if (m.find( )) {

                red = Integer.parseInt(m.group(2));

                g = Integer.parseInt(m.group(4));

                b = Integer.parseInt(m.group(6));

            } else {
                System.out.println("NO MATCH");
            }

            MouseDrawCircle.MyCircle circle = new MouseDrawCircle.MyCircle(x,y,filled);
            circle.radius = radius;
            circle.color = new Color(red, g, b);
            pCircles.add(circle);
        }

    }
}