package Aufgabe1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DictionaryMenuBar extends JMenuBar implements ActionListener{
public final static int HASH_MAP = 1, TREE_MAP = 2, SORTED_ARRAY = 3, TREE = 4, HASH = 5; 
private JFileChooser fileChooser = new JFileChooser();
private JMenu mFile, mOptions;
private JMenuItem mLesen, mSpeichern, mHinzufuegen, mLoeschen, mBeenden, mPerformanceSuccess, mPerformanceFail;
private JCheckBoxMenuItem mCpuTime;
private JRadioButtonMenuItem mTreeMap, mHashMap, mSortedArray, mHash, mTree;
private ButtonGroup group = new ButtonGroup();
private FileHandler handler = null;
private GUI gui = null;
private InputFrame input = null;

	public DictionaryMenuBar(GUI pGUI, FileHandler pHandler){
		handler = pHandler;
		gui = pGUI;
		input = new InputFrame(gui);
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File(System.getProperty("user.dir")));
        
		mFile = new JMenu("Datei");
		mOptions = new JMenu("Optionen");
        mLesen = new JMenuItem("Wörterbuch lesen...");
        mLesen.addActionListener(this);
        mSpeichern = new JMenuItem("Wörterbuch speichern...");
        mSpeichern.addActionListener(this);
        mHinzufuegen = new JMenuItem("Wort hinzufügen");
        mHinzufuegen.addActionListener(this);
        mLoeschen = new JMenuItem("Wort löschen");
        mLoeschen.addActionListener(this);
        mBeenden  = new JMenuItem("Wörterbuch beenden");
        mBeenden.addActionListener(this);
        mPerformanceSuccess = new JMenuItem("Performance Test Success");
        mPerformanceSuccess.addActionListener(this);
        mPerformanceFail = new JMenuItem("Performance Test Failure");
        mPerformanceFail.addActionListener(this);
        
        mTreeMap = new JRadioButtonMenuItem("TreeMap",true);
        mHashMap = new JRadioButtonMenuItem("HashMap",false);
        mSortedArray = new JRadioButtonMenuItem("SortedArray",false);
        mHash = new JRadioButtonMenuItem("Hash",false);
        mTree = new JRadioButtonMenuItem("Tree",false);
        
        mCpuTime = new JCheckBoxMenuItem("CPU Zeit messen");
        mCpuTime.addActionListener(this);
        
        group.add(mTreeMap);
        group.add(mHashMap);
        group.add(mSortedArray);
        group.add(mHash);
        group.add(mTree);
        
        mFile.add(mLesen);
        mFile.add(mSpeichern);
        mFile.addSeparator();
        mFile.add(mHinzufuegen);
        mFile.add(mLoeschen);
        mFile.addSeparator();
        mFile.add(mBeenden);
        
        mOptions.add(mTreeMap);
        mOptions.add(mHashMap);
        mOptions.add(mSortedArray);
        mOptions.add(mHash);
        mOptions.add(mTree);
        mOptions.addSeparator();
        mOptions.add(mCpuTime);
        mOptions.addSeparator();
        mOptions.add(mPerformanceSuccess);
        mOptions.add(mPerformanceFail);
        
        add(mFile);
        add(mOptions);
	}

	public int getType(){
		if(mTreeMap.isSelected()){
			return TREE_MAP;
		}else if(mHashMap.isSelected()){
			return HASH_MAP;
		}else if(mSortedArray.isSelected()){
			return SORTED_ARRAY;
		}else if(mHash.isSelected()){
			return HASH;
		}else{
			return TREE;
		}
	}
	
	private void fillTextArea(Dictionary<String, String> dic){
		gui.setText(dic.toString());
	}
	
	private void searchTime(){
		String tmp[] = handler.getDictionary().toString().split("\n");
		int i;
		for(i=0;i<tmp.length;i++){
			tmp[i] = tmp[i].substring(0,tmp[i].indexOf("="));
		}
		long start = System.nanoTime();
		for(String s:tmp){
			handler.search(s);
		}
		long stop = System.nanoTime();
		gui.setText(""+i+" keys wurden in "+(stop-start)+" Nanosekunden gefunden!");
	}
	
	private void searchFailedTime(){
		String tmp[] = handler.getDictionary().toString().split("\n");
		int i;
		for(i=0;i<tmp.length;i++){
			tmp[i] = tmp[i].substring(tmp[i].indexOf("="),tmp[i].length());
		}
		long start = System.nanoTime();
		for(String s:tmp){
			handler.search(s);
		}
		long stop = System.nanoTime();
		gui.setText(""+i+" keys wurden in "+(stop-start)+" Nanosekunden nicht gefunden!");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == mLesen){
			fileChooser.showOpenDialog(this);
			handler.read(fileChooser.getSelectedFile());
			fillTextArea(handler.getDictionary());
		}else if (source == mSpeichern){
			fileChooser.showSaveDialog(this);
			handler.save(fileChooser.getSelectedFile());
		}else if (source == mHinzufuegen){
			input.showInputFrame();
		}else if(source == mLoeschen){
			String key = JOptionPane.showInputDialog(this, "Bitte das zu löschende Wort eingeben", "Löschen", JOptionPane.OK_CANCEL_OPTION);
			if(key != null && !key.isEmpty()){
				handler.remove(key);
			}else{
				JOptionPane.showMessageDialog(this, "Löschen wurde abgebrochen", "Abbruch", JOptionPane.ERROR_MESSAGE);
			}
		}else if(source == mCpuTime){
			handler.evaluateTime(mCpuTime.isSelected());
		}else if(source == mPerformanceSuccess){
			searchTime();
		}else if(source == mPerformanceFail){
			searchFailedTime();
		}else{
			System.exit(0);
		}
	}
	
	private class InputFrame extends JFrame implements ActionListener{
	private JLabel lbGer = new JLabel("Deutsch"), lbEng = new JLabel("Englisch");
	private JTextField tfGer = new JTextField(),tfEng = new JTextField();
	private JButton btOK = new JButton("OK"), btCancel = new JButton("Cancel");
	private JPanel pCenter = new JPanel(), pSouth = new JPanel(), pNorth = new JPanel();

		public InputFrame(JFrame gui){
			add(BorderLayout.CENTER,pCenter);
			add(BorderLayout.SOUTH,pSouth);
			add(BorderLayout.NORTH,pNorth);
			pNorth.setLayout(new GridLayout(1, 2));
			pNorth.add(lbGer);
			pNorth.add(lbEng);
			pCenter.setLayout(new GridLayout(1, 2));
			pCenter.add(tfGer);
			pCenter.add(tfEng);
			pSouth.add(btOK);
			pSouth.add(btCancel);
			btOK.addActionListener(this);
			btCancel.addActionListener(this);
			pack();
			setLocationRelativeTo(gui.getContentPane());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btOK){
				String ger = tfGer.getText(), eng = tfEng.getText();
				if(!ger.isEmpty() && !eng.isEmpty()){
					handler.insert(ger, eng);
					gui.setText(ger+"="+eng+" erfolgreich hinzugefügt");
				}else{
					JOptionPane.showMessageDialog(this, "Falsche Eingabe", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
			setVisible(false);
		}
		
		public void showInputFrame(){
			tfGer.setText("");
			tfEng.setText("");
			setVisible(true);
		}
	}
}