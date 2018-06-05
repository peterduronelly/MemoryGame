import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class MemoryGame extends JFrame implements ActionListener {
  
  private Timer stopper = new Timer(0, this);
  int idő = 0;
  int match = 0, steps =0, selectedButtons =0;
  String choice1, choice2;
  Font myfont = new Font("Arial", Font.BOLD, 30);
  JButton btFirstClick = new JButton();
  JButton btSecondClick = new JButton();
  ArrayList<JButton> matchedButtons = new ArrayList<>();
  ArrayList<JButton> unmatchedButtons = new ArrayList<>();

  public MemoryGame()  {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 450);
    setLocationRelativeTo(this);
    setLayout(new GridLayout(4, 5));
    ArrayList<Integer> feliratok = new ArrayList<>();
    for (int i = 0; i < 20; i++) 
      feliratok.add((i+2)/2);
    System.out.println(feliratok);
    JButton bt;
    for (int i = 0; i < 20; i++) {
      bt = new JButton();
      int j = (int)(Math.random()*feliratok.size());
      bt.setActionCommand(""+feliratok.get(j));
//      bt.setText(""+feliratok.get(j));
      feliratok.remove(j);
      bt.setFont(myfont);
      bt.addActionListener(this);
      unmatchedButtons.add(bt);
      add(bt);
    }
    setVisible(true);
    stopper.start();
    stopper.setDelay(1000);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()==stopper){
      idő++;
      int min = idő/60;
      int sec = idő%60;
      if (sec < 10) {
        setTitle("Memory game ("+min+":0"+sec+")");
      } else {
        setTitle("Memory game ("+min+":"+sec+")");
      }
    }
    else{
        if(selectedButtons == 0){
          for (JButton btn : unmatchedButtons) {
            btn.setText("");
            btn.setEnabled(true);
          }
          choice1 = e.getActionCommand();
          btFirstClick = (JButton)(e.getSource());
          btFirstClick.setEnabled(false);
          btFirstClick.setText(e.getActionCommand());
          selectedButtons = 1;
        } else if (selectedButtons == 1){
          choice2 = e.getActionCommand();
          btSecondClick = (JButton)(e.getSource());
          btSecondClick.setEnabled(false);
          btSecondClick.setText(choice2);
          if (Integer.parseInt(choice2) == Integer.parseInt(choice1)) {
            match++;
            matchedButtons.add(btFirstClick);
            matchedButtons.add(btSecondClick);
            unmatchedButtons.remove(btFirstClick);
            unmatchedButtons.remove(btSecondClick);
            for (JButton btm : matchedButtons) {
              btm.setText(btm.getActionCommand());
              btm.setEnabled(false);
            }
          } 
          selectedButtons = 0;
          steps++;
          }
      if(match == 10){
      stopper.stop();
        JOptionPane.showMessageDialog(this, "The memory game ended in\n   "+steps+" steps in "+
                idő+" seconds.",
                "  Result", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }
  
  public static void main(String[] args) {
    new MemoryGame();
  }
  
  
}
