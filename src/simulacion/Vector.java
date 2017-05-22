package simulacion;


import java.util.Random;

public class Vector {
public float posX;
public float posY;

public Vector(float posX, float posY){
	this.posX=posX;
	this.posY=posY;
}

public void posicionRandom (int windowWidth,int windowHeight){
	Random rnd = new Random();

		this.posX = rnd.nextInt(windowWidth);
		this.posY = rnd.nextInt(windowHeight);
	
}



public static Vector add(Vector a, Vector b)
{
	return new Vector(a.posX + b.posX, a.posY + b.posY);
}


public Vector normalized()
{
	return new Vector(posX / getLength(), posY/getLength());
}

public static Vector substract(Vector a, Vector b)
{
	return new Vector(a.posX  - b.posX , a.posY - b.posY);
}

public static Vector multiply(Vector v, float scalar)
{
	return new Vector(v.posX * scalar, v.posY * scalar);
}

public float getLength()
{
	return (float) Math.sqrt((double)(posX * posX) + (double)(posY * posY)); 
}


}
