
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ColorChooser extends JFrame {
    private JTextArea sampleText = new JTextArea("     ");
    private JButton chooseButton = new JButton("Choose Color");


    public ColorChooser() {
        this.setSize(300, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel1 = new JPanel();
        sampleText.setBackground(null);
        panel1.add(sampleText);

        chooseButton.addActionListener(new ButtonListener());
        panel1.add(chooseButton);

        this.add(panel1);
        this.setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Custom Color", sampleText.getForeground());
            if (c != null)
                sampleText.setBackground(c);
        }
    }
}