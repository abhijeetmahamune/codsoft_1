import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessTheNum implements ActionListener {
    JFrame frame;
    JButton[] numberButtons;
    JButton clear, check, ok, gridCheck;
    JTextField tf;
    JPanel panelNumbers, panelTop, panelControls;
    JLabel title, statement, statement2;
    JComboBox<String> jb;
    JDialog dialog;
    Random r = new Random();
    int num = 100, rand, checknum, turns;

    GuessTheNum() {
        frame = new JFrame("Guess The Number");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        rand = r.nextInt(num);

        // Top panel (Title and ComboBox for number range)
        panelTop = new JPanel();
        panelTop.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelTop.setBackground(new Color(230, 230, 250));

        title = new JLabel("Guess The Number");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
        title.setForeground(new Color(72, 61, 139));

        String[] levels = { "0-10", "0-100", "0-1000", "0-10000" };
        jb = new JComboBox<>(levels);
        jb.setSelectedIndex(1);
        jb.setFont(new Font("Arial", Font.PLAIN, 16));
        jb.setBackground(new Color(224, 255, 255));
        jb.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 235), 2));

        ok = new JButton("OK");
        ok.setFont(new Font("Arial", Font.BOLD, 18));
        ok.setBackground(new Color(135, 206, 235));
        ok.setForeground(Color.WHITE);
        ok.setFocusPainted(false);
        ok.setBorder(BorderFactory.createLineBorder(new Color(0, 191, 255), 2));
        ok.addActionListener(this);

        panelTop.add(title);
        panelTop.add(new JLabel("Range:"));
        panelTop.add(jb);
        panelTop.add(ok);
        frame.add(panelTop, BorderLayout.NORTH);

        // Middle panel (Game statement and text field)
        JPanel panelMiddle = new JPanel();
        panelMiddle.setLayout(new GridLayout(3, 1, 10, 10));
        panelMiddle.setBackground(new Color(240, 248, 255));

        statement = new JLabel("Guess the number ### ?");
        statement.setFont(new Font("Verdana", Font.BOLD, 22));
        statement.setHorizontalAlignment(SwingConstants.CENTER);
        statement.setForeground(new Color(72, 61, 139));

        statement2 = new JLabel("");
        statement2.setFont(new Font("Verdana", Font.PLAIN, 16));
        statement2.setHorizontalAlignment(SwingConstants.CENTER);
        statement2.setForeground(new Color(105, 105, 105));

        tf = new JTextField();
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        tf.setFont(new Font("Arial", Font.BOLD, 22));
        tf.setBackground(new Color(255, 248, 220));
        tf.setForeground(new Color(25, 25, 112));
        tf.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 235), 2));
        tf.setEditable(false);

        panelMiddle.add(statement);
        panelMiddle.add(statement2);
        panelMiddle.add(tf);
        frame.add(panelMiddle, BorderLayout.CENTER);

        // Bottom panel (Number buttons, Clear, and Check button)
        panelNumbers = new JPanel();
        panelNumbers.setLayout(new GridLayout(4, 3, 10, 10));
        panelNumbers.setBackground(new Color(240, 255, 255));

        numberButtons = new JButton[10];
        for (int i = 1; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            numberButtons[i].setBackground(new Color(224, 255, 255));
            numberButtons[i].setForeground(new Color(25, 25, 112));
            numberButtons[i].setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
            numberButtons[i].addActionListener(this);
            panelNumbers.add(numberButtons[i]);
        }

        numberButtons[0] = new JButton("0");
        numberButtons[0].setFont(new Font("Arial", Font.BOLD, 20));
        numberButtons[0].setBackground(new Color(224, 255, 255));
        numberButtons[0].setForeground(new Color(25, 25, 112));
        numberButtons[0].setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        numberButtons[0].addActionListener(this);
        panelNumbers.add(numberButtons[0]);

        // Add Clear and Check buttons to the grid
        clear = new JButton("Clear");
        clear.setFont(new Font("Arial", Font.BOLD, 20));
        clear.setBackground(new Color(255, 99, 71));
        clear.setForeground(Color.WHITE);
        clear.setBorder(BorderFactory.createLineBorder(new Color(220, 20, 60), 2));
        clear.addActionListener(this);
        panelNumbers.add(clear);

        gridCheck = new JButton("Check");
        gridCheck.setFont(new Font("Arial", Font.BOLD, 20));
        gridCheck.setBackground(new Color(144, 238, 144));
        gridCheck.setForeground(Color.BLACK);
        gridCheck.setBorder(BorderFactory.createLineBorder(new Color(60, 179, 113), 2));
        gridCheck.addActionListener(this);
        panelNumbers.add(gridCheck);

        frame.add(panelNumbers, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < numberButtons.length; i++) {
            if (ae.getSource() == numberButtons[i]) {
                tf.setText(tf.getText() + numberButtons[i].getText());
                return;
            }
        }

        if (ae.getSource() == clear) {
            tf.setText("");
        }

        if (ae.getSource() == gridCheck || ae.getSource() == ok) {
            if (tf.getText().isEmpty()) {
                statement2.setText("Please enter a number.");
                return;
            }
            checknum = Integer.parseInt(tf.getText());
            turns++;
            if (rand > checknum) {
                statement2.setText(checknum + " is smaller. Try a larger number.");
            } else if (rand < checknum) {
                statement2.setText(checknum + " is larger. Try a smaller number.");
            } else if (rand == checknum) {
                statement.setText("YOU WON! You took " + turns + " chances to guess the correct number.");
                showDialog("Congratulations! You guessed the number correctly in " + turns + " attempts.");
                turns = 0;
                rand = r.nextInt(num);
                tf.setText("");
            }
        }

        if (ae.getSource() == ok) {
            if (jb.getSelectedItem() == "0-10") {
                num = 11;
            } else if (jb.getSelectedItem() == "0-100") {
                num = 101;
            } else if (jb.getSelectedItem() == "0-1000") {
                num = 1001;
            } else if (jb.getSelectedItem() == "0-10000") {
                num = 10001;
            }
            rand = r.nextInt(num);
            turns = 0;
            statement.setText("Guess the number in the range.");
            tf.setText("");
            statement2.setText("");
        }
    }

    public void showDialog(String message) {
        dialog = new JDialog(frame, "Congratulations", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);

        JLabel d_text = new JLabel(message, SwingConstants.CENTER);
        d_text.setFont(new Font("Arial", Font.BOLD, 18));
        d_text.setForeground(new Color(25, 25, 112));
        dialog.add(d_text);

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new GuessTheNum();
    }
}
