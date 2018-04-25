import jdk.management.resource.internal.inst.FileChannelImplRMHooks;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class FinalProject {
    private static JComboBox chooseColor;
    public static boolean fillCir = true;
    public static boolean randGen = false;
    public static boolean randColor = false;
    public static File curFile = null;
    public static JTextField filenameTextField;
    public static JTextField authorTextField;
    public static Color curColor = Color.red;
    public static JPanel colorPanel;
    public static JTextArea colorIndi;

    public static void main(String[] args){

        JFrame window = new JFrame("FinalProject");

        MouseDrawCircle drawWidget2 = new MouseDrawCircle();
        drawWidget2.drawingColor = Color.BLUE;

        JButton clear = new JButton("Clear");
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(0,1));

        JPanel userInputPanel = new JPanel();
        userInputPanel.setLayout(new GridLayout(0,2));

        JPanel authorTextAreaPanel = new JPanel();

        JTextArea authorTextArea = new JTextArea("Enter your name:");
        authorTextAreaPanel.add(authorTextArea);
        userInputPanel.add(authorTextAreaPanel);

        authorTextField = new JTextField();
        authorTextField.setBackground(Color.lightGray);

        userInputPanel.add(authorTextField);

        JPanel filenameTextAreaPanel = new JPanel();
        JTextArea filenameTextArea = new JTextArea("Enter file name");
        filenameTextAreaPanel.add(filenameTextArea);

        userInputPanel.add(filenameTextAreaPanel);

        filenameTextField = new JTextField();
        filenameTextField.setBackground(Color.lightGray);
        userInputPanel.add(filenameTextField);

        center.add(userInputPanel);

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new GridLayout(0,2));

        FileChooser chooseFile = new FileChooser("open file", drawWidget2);
        JButton chooseFileButton = chooseFile.open;
        filePanel.add(chooseFileButton);
        FileChooser chooseDir = new FileChooser("save file", drawWidget2);
        JButton chooseDirButton = chooseDir.open;
        filePanel.add(chooseDirButton);



        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curFile = chooseFile.getFile();
            }
        });

        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        center.add(filePanel);

        JCheckBox checkbox1 = new JCheckBox("Random generate");
        checkbox1.setBounds(150,100, 50,50);
        checkbox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                randGen = !randGen;
                System.out.println("e = [" + randGen + "]");
            }
        });
        center.add(checkbox1);

        JToggleButton toggleButton = new JToggleButton("Fill Circle ON");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleButton.isSelected()) {
                    toggleButton.setText("Fill Circle OFF");
                    fillCir = false;
                }
                else {
                    toggleButton.setText("Fill Circle ON");
                    fillCir = true;
                }
            }
        });
        center.add(toggleButton);

        colorPanel = new JPanel();

        chooseColor = new JComboBox(new String[]{"Red", "Cyan","Yellow","Green", "Random", "Custom"});
        chooseColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curColor = convertColor(getColor());
                colorIndi.setBackground(curColor);
            }
        });
        colorIndi = new JTextArea("      ");
        colorIndi.setBackground(curColor);
        colorPanel.add(colorIndi);
        colorPanel.add(chooseColor);
        center.add(colorPanel);


        JButton withdraw2 = new JButton("Withdraw");

        center.add(withdraw2);

        center.add(clear);

        content.add(center, BorderLayout.LINE_START);

        content.add(drawWidget2, BorderLayout.CENTER);
        System.out.println("args = [" + "" + "]");
        window.setContentPane(content);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(120,70);
        window.setSize(1080,720);
        window.setVisible(true);

        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                drawWidget2.circles.clear();
                drawWidget2.repaint();
                authorTextField.setText("");
                filenameTextField.setText("");
            }
        });

        withdraw2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawWidget2.withdraw();
            }
        });

    }
    public static String getColor(){
        String value = chooseColor.getSelectedItem().toString();
        return value;
    }
    public static void writeCirclesToFile(ArrayList<MouseDrawCircle.MyCircle> circles, String filename, String author){
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println(author);

            for(int i = 0; i < circles.size(); i++){
                writer.println(i + "|" + circles.get(i));
            }
            writer.close();
        } catch (Exception e){
            System.out.println("Problem writing to file: "+e);
        }
    }
    private static Color convertColor(String s) {

        randColor = s.equals("Random");
        switch (s){
            case "Custom": {
                Color c = JColorChooser.showDialog(null, "Custom Color", Color.red);
                if (c != null)
                    return c;
                else return Color.red;

            }
            case "Random": {
                return MouseDrawCircle.genRandColor();
            }
            case "Red": {

                return Color.PINK;
            }
            case "Yellow":{
                return Color.YELLOW;
            }
            case "Cyan": {
                return Color.CYAN;
            }
            case "Green":{
                return Color.GREEN;
            }
            case "ORANGE":{
                return Color.ORANGE;
            }
            default:{
                return Color.CYAN;
            }
        }
    }
}
