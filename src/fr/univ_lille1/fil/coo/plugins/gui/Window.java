package fr.univ_lille1.fil.coo.plugins.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.univ_lille1.fil.coo.plugins.Plugin;
import fr.univ_lille1.fil.coo.plugins.listener.PluginListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class Window extends JFrame implements PluginListener {
	

	private JPanel contentPane;
	private JMenu mnPlugins;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JMenuItem mntmPropos;


	public static void main(String[] args) {
		new Window();
	}
	/**
	 * Create the frame.
	 */
	public Window() {
		try {
			// donne à l'interface graphique le thème associé au système d'exploitation
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle("TP Plugin");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 100, 100);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		mnPlugins = new JMenu("Tools");
		menuBar.add(mnPlugins);
		
		JMenuItem menuItem = new JMenu("?");
		
		menuBar.add(menuItem);
		
		mntmPropos = new JMenuItem("À propos");
		mntmPropos.addActionListener((event) -> {
			showHelp();
		});
		menuItem.add(mntmPropos);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		textArea = new JTextArea();
		textArea.setTabSize(4);
		
		scrollPane.setViewportView(textArea);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		
		
		setVisible(true);
	}

	@Override
	public void pluginHasChanged(List<Plugin> pls) {
		mnPlugins.removeAll();
		for (Plugin p : pls) {
			JMenuItem it = new JMenuItem(p.getName());
			it.addActionListener((event) -> {
				textArea.setText(p.transformText(textArea.getText()));
			});
		}
	}
	
	
	
	
	private void showHelp() {
		JOptionPane.showMessageDialog(this,
				  "TP Plugins\n"
				+ "Licence 3, Semestre 5, 2015-2016\n"
				+ "\n"
				+ "- Maxime Maroine\n"
				+ "- Veïs Oudjail\n"
				+ "- Kevin Gamelin\n"
				+ "- Marc Baloup");
	}

}
