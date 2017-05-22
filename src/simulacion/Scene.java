package simulacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

public class Scene extends JPanel
{
	private ArrayList <Actor> _actors = new ArrayList<Actor>();
	private Date _lastFrameDate;
	private Boolean _runningSimulation;
	private JFrame _simulationWindow;
	
	public Scene(int windowWidth, int windowHeight)
	{
		Toolkit t = Toolkit.getDefaultToolkit(); 
		Image icono = t.getImage("src/Imagenes/zombie1.png"); 
		
		_simulationWindow = new JFrame();	
		_simulationWindow.setSize(windowWidth, windowHeight);
		_simulationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_simulationWindow.setVisible(true);
		_simulationWindow.setLocationRelativeTo(null);
		//_simulationWindow.setResizable(false);
		_simulationWindow.setTitle("Zombies vs Humanos");
		_simulationWindow.setIconImage(icono);
		
		setBackground(Color.DARK_GRAY);
				
		createActors();
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem stop = new JMenuItem("Stop");
		stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		menu.add(stop);
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int opcion = 0;
				opcion = JOptionPane.showConfirmDialog(null, "¿Desea detener el juego?", "Detener", JOptionPane.YES_NO_OPTION, 0);
				if (opcion == JOptionPane.YES_OPTION) {
					_runningSimulation = false;
				}
			}});
		JMenuItem pause = new JMenuItem("Pause");
		pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		menu.add(pause);
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					   Thread.sleep(2500);
					} catch (Exception exc) {
					   exc.printStackTrace();
					}
			}});
		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		menu.add(exit);
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}});
		JMenuItem reset = new JMenuItem("Reset");
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		menu.add(reset);
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
				for (int i = 0; i < _actors.size(); i++) {
					for (int j = 0; j < 100; j++) {
					_actors.remove(i);
					}
				}
				createActors();
				
				}catch(IndexOutOfBoundsException exc){
				}
				try{
					for (int i = 0; i < _actors.size(); i++) {
						for (int j = 0; j < 100; j++) {
						_actors.remove(i);
						}
					}
					createActors();
				}catch(IndexOutOfBoundsException exc){
				}
				
			}});
		
		
		
		add(menu);
		this.setComponentPopupMenu(menu);
		_simulationWindow.add(this);
	}
	
	public void execute()
	{
		_runningSimulation = true;
		
		while (_runningSimulation)
		{
			
			for (int i = 0; i < _actors.size(); i++) 
			{
				_actors.get(i).updateLogic(getDeltaTime(), _simulationWindow.getMousePosition(),_actors);	
			}
			
			repaint();
		}
	}
	
	public void createActors(){
		int amountOfActors = 0;
		
		while (amountOfActors < 30){
			Random rnd = new Random();
			amountOfActors = rnd.nextInt(40);
		}
		for (int i = 0; i < amountOfActors; i++)
		{
			Random rnd = new Random();
	        int vx = rnd.nextInt(750);
	        int vy = rnd.nextInt(500);
			_actors.add(i,new Actor(vx, vy, 2500 + (int)rnd.nextInt(100)));
		}
		
		for (int i = 0; i<(int)(2);i++){
			_actors.get(i).isZombie();
			_actors.get(i)._speed = 500;
		}
	}
	
	public void paintComponent(Graphics g) 
	{
	      super.paintComponent(g);
	      for (Actor actor : _actors) 
	      {
	    	  actor.updateVisuals(g);
	      }
	      

	  }
		
	private float getDeltaTime()
	{
		Calendar c = Calendar.getInstance();
		
		if(_lastFrameDate == null)
		{
			_lastFrameDate = c.getTime();
			return 0f;
		}			
		
		float deltaTime = (c.getTime().getTime() - _lastFrameDate.getTime()) / 1000f;
		_lastFrameDate = c.getTime();
		return deltaTime;
	}
	
	
}
