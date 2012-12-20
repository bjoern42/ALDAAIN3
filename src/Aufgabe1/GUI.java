package Aufgabe1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener{
private JTextField tfInput = new JTextField();
private JButton btSearch = new JButton("Suchen");
private JTextArea taOutput = new JTextArea();
private JPanel pNorth = new JPanel();
private FileHandler handler = new FileHandler(this);
private DictionaryMenuBar menuBar = null;

	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI(){
		setTitle("Wörterbuch");
		menuBar = new DictionaryMenuBar(this,handler);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pNorth.setLayout(new BorderLayout());
        pNorth.add(BorderLayout.CENTER,tfInput);
        pNorth.add(BorderLayout.EAST,btSearch);
        add(BorderLayout.NORTH,pNorth);
        add(BorderLayout.CENTER,new JScrollPane(taOutput));
        setSize(500,500);
        
        setLocationRelativeTo(null);
        
        setVisible(true);
        btSearch.addActionListener(this);
	}
	
	public void setText(String text){
		taOutput.setText(text);
		long cpuTime = handler.getCpuTime();
		if(cpuTime != 0){
			taOutput.append("\n\n "+cpuTime+" Nanosekunden");
		}
	}
	
	public int getDictType(){
		return menuBar.getType();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(tfInput.getText().isEmpty()){
			setText(handler.getDictionary().toString());
		}else{
			String search = handler.search(tfInput.getText());
			if(search != null){
				setText(search);
			}else{
				setText("Konnte nicht gefunden werden");
			}
		}
	}
}