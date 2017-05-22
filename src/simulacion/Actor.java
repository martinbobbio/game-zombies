package simulacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Actor
{
	public float _posX;
	public float _posY;
	public float _speed;
	public boolean _zombies = true;
	public Actor target;
	private Vector position;
	
	private Image zombie;
	private Image actor;

	public Actor(int x, int y, float pSpeed)
	{
		
		position = new Vector(0,0);
		position.posicionRandom(x, y);
		_posX = position.posX;
		_posY = position.posY;
		_speed = pSpeed;
	}
	
	public void isZombie(){
		 _zombies=false;
	}
	
	public Vector getPosition(){
		position= new Vector(_posX,_posY);
		
		return position;
	}
	
	public void updateLogic(float deltaTime, Point mousePos, ArrayList<Actor> _actors)
	{
		if(mousePos==null){
			mousePos = new Point();
			}
		if(_zombies){		
			Vector targetPosition = new Vector(mousePos.x, mousePos.y);
			Vector dirToTarget = Vector.substract(targetPosition, position);
			position = Vector.add(position, Vector.multiply(dirToTarget.normalized(), _speed * deltaTime));
			Infectado(_actors);
		}else{
			try{
			target(_actors);
			Vector targetPosition = new Vector(target.position.posX, target.position.posY);
			Vector dirToTarget = Vector.substract(targetPosition, position);
			position = Vector.add(position, Vector.multiply(dirToTarget.normalized(), _speed * deltaTime));
			_posX += _speed * deltaTime * (target._posX-_posX);
			_posY += _speed * deltaTime * (target._posY-_posY);
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(null, "La raza humana se ha extinguido!","Fin!",2);
				System.exit(0);
			}
			
		}
		
	}
	
	public void Infectado(ArrayList<Actor> _actors){
			
			for(int i =0; i<_actors.size();i++){
				if(!_actors.get(i)._zombies)
				{
					Vector vectorToTarget = Vector.substract(_actors.get(i).position, position);
				
					if(vectorToTarget.getLength() <= 10)
					{
						isZombie();
						_actors.get(i)._speed = 500;
						target(_actors);
						
					}
				}
			}
		}

	private void target(ArrayList<Actor> _actors) {
		
		if(!_zombies){
			for(int i =0; i<_actors.size();i++){
				if(_actors.get(i)._zombies){
					target=_actors.get(i);
				}
			}
		}
		else{
			target=null;
			}
		
	}
	
	public void updateVisuals(Graphics g){
		if(_zombies){
	
			g.setColor(Color.RED);
			g.fillOval((int)position.posX, (int)position.posY, 10, 10);
			
			/*try{
				actor = ImageIO.read(new File("src/imagenes/actor1.png"));
			}catch(IOException e){
				System.out.println("La imagen no existe");
			}
			g.drawImage(actor,(int)position.posX,(int)position.posY,null);*/
			
			}
			else{
			
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.CYAN);
			Rectangle2D z = new Rectangle2D.Double((int)position.posX, (int)position.posY, 12, 12);
			g2.fill(z);
			
				
			/*try{
				zombie = ImageIO.read(new File("src/imagenes/zombie2.png"));
			}catch(IOException e){
				System.out.println("La imagen no existe");
			}
			g.drawImage(zombie,(int)position.posX,(int)position.posY,null);*/
			
			}

	}

}