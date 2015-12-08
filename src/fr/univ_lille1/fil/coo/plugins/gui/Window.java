package fr.univ_lille1.fil.coo.plugins.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import fr.univ_lille1.fil.coo.plugins.Plugin;
import fr.univ_lille1.fil.coo.plugins.listener.PluginListener;

@SuppressWarnings("serial")
public class Window extends JFrame implements PluginListener {
	
	private List<Plugin> plugins;

	private JPanel contentPane;
	private JMenu mnPlugins;
	private JTextArea textArea;


	/**
	 * Create the frame.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		mnPlugins = new JMenu("Tools");
		menuBar.add(mnPlugins);
		
		// il faut boucler ici
		JMenuItem mntmPluginItem = new JMenuItem("Plugin item");
		mnPlugins.add(mntmPluginItem);
		
		textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.CENTER);
		this.setVisible(true);
	}

	@Override
	public void pluginHasChanged(List<Plugin> pls) {
		plugins = pls;
		mnPlugins.removeAll();
		for (Plugin p : pls) {
			JMenuItem it = new JMenuItem(p.getName());
			it.addActionListener((event) -> {
				textArea.setText(p.transformText(textArea.getText()));
			});
		}
	}

}
