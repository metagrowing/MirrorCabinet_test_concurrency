import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Shape 
{
	int[] loc;
	int orientation;
	int type; //0=sphere
	Color color=Color.red;
	
	static Shape mid=new Shape(Cabinet.size[0]/2,Cabinet.size[1]/2,0);
	
	public Shape(int x, int y, int or)
	{
		loc=new int[]{x,y,0};
		orientation=or;
		type=0;
		
	}

	public int moveForward(int i,Cabinet room) 
	{
		
		int[]move=new int[2];
		move[orientation%2]=(int)Math.pow(-1, orientation/2);
		int njux=Cabinet.mod((loc[0]*2+1+move[0])/2,Cabinet.size[0]),
				njuy=Cabinet.mod((loc[1]*2+1+move[1])/2,Cabinet.size[1]);
		if(room.panes[orientation%2][njux][njuy][0]==0)
	{
		
		room.objects[loc[0]][loc[1]]-=i+1;	
		loc[orientation%2]+=Math.pow(-1, orientation/2);
		loc[orientation%2]=(loc[orientation%2]+Cabinet.size[orientation%2])%Cabinet.size[orientation%2];	
		if(room.objects[loc[0]][loc[1]]>0)return room.objects[loc[0]][loc[1]];
		room.objects[loc[0]][loc[1]]+=i+1;
		return 0;
	}
		return -3;
	}

	public void draw(int[] is, double[] ds, Color col, double visibility, int z,Graphics g) 
	{
	
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Cabinet.mix(color,col,visibility));
		g.setColor(Cabinet.mix(color,col,visibility));
		
		if(type==0)
		{
		//	System.out.println("let's draw the shape with k= "+is[0]+" and i="+is[1]+"from "+ds[0]+" to "+ds[1]);
			int x=Cabinet.width*is[1]/is[0],
					y=Cabinet.height/2-Cabinet.width*((loc[2]-z))/is[0]/2,
					r=Cabinet.width/(2*is[0]);
			//g.fillOval(x-r, y-r, 2*r, 2*r);
			Ellipse2D ellipse=new Ellipse2D.Double(x-r, y-r, r*2, r*2);
			Rectangle2D bound=new Rectangle2D.Double(Cabinet.width*ds[0]-1, 0, Cabinet.width*(ds[1]-ds[0])+1, Cabinet.height);
			Area area=new Area(ellipse);
			area.intersect(new Area(bound));
			g2d.fill(area);
		}
		
	}

	public void turn(int i) 
	{
		if(i==0)
		{
			Random rand=new Random();
			orientation+=Math.pow(-1, rand.nextInt(2));
		}
		else if(i==1)
		{
			orientation--;
		}
		else if(i==2) 
		{
			orientation++;
		}
		
		orientation=Cabinet.mod(orientation, 4);
	}

	public int moveBack(int i, Cabinet room) 
	{
		int[]move=new int[2];
		move[orientation%2]=(int)Math.pow(-1, orientation/2+1);
		int njux=Cabinet.mod((loc[0]*2+1+move[0])/2,Cabinet.size[0]),
				njuy=Cabinet.mod((loc[1]*2+1+move[1])/2,Cabinet.size[1]);
		if(room.panes[orientation%2][njux][njuy][0]==0)
	{
		room.objects[loc[0]][loc[1]]-=i+1;	
		loc[orientation%2]+=Math.pow(-1, orientation/2+1);
		loc[orientation%2]=(loc[orientation%2]+Cabinet.size[orientation%2])%Cabinet.size[orientation%2];	
		if(room.objects[loc[0]][loc[1]]>0)return room.objects[loc[0]][loc[1]];
		room.objects[loc[0]][loc[1]]+=i+1;
		return 0;
	}
		return -3;
	}

	public int moveRight(int i, Cabinet room) 
	{
		int[]move=new int[2];
		int or=orientation+3;
		move[(or)%2]=(int)Math.pow(-1, or/2);
		int njux=Cabinet.mod((loc[0]*2+1+move[0])/2,Cabinet.size[0]),
				njuy=Cabinet.mod((loc[1]*2+1+move[1])/2,Cabinet.size[1]);
		if(room.panes[or%2][njux][njuy][0]==0)
	{
		room.objects[loc[0]][loc[1]]-=i+1;	
		loc[or%2]+=Math.pow(-1, or/2);
		loc[or%2]=(loc[or%2]+Cabinet.size[or%2])%Cabinet.size[or%2];	
		if(room.objects[loc[0]][loc[1]]>0)return room.objects[loc[0]][loc[1]];
		room.objects[loc[0]][loc[1]]+=i+1;
		return 0;
	}
		return -2;
	}

	public int moveLeft(int i, Cabinet room) 
	{
		int[]move=new int[2];
		int or=orientation+1;
		move[(or)%2]=(int)Math.pow(-1, or/2);
		int njux=Cabinet.mod((loc[0]*2+1+move[0])/2,Cabinet.size[0]),
				njuy=Cabinet.mod((loc[1]*2+1+move[1])/2,Cabinet.size[1]);
		if(room.panes[or%2][njux][njuy][0]==0)
	{
		room.objects[loc[0]][loc[1]]-=i+1;	
		loc[or%2]+=Math.pow(-1, or/2);
		loc[or%2]=(loc[or%2]+Cabinet.size[or%2])%Cabinet.size[or%2];	
		if(room.objects[loc[0]][loc[1]]>0)
				{
			return room.objects[loc[0]][loc[1]];
				}
		room.objects[loc[0]][loc[1]]+=i+1;
		return 0;
	}
		return -1;
	}

	public int move(int i,Cabinet room) 
	{
		Random rand=new Random();
		int t=rand.nextInt(4);
		if(t==0)return moveForward(i,room);
		if(t==1)return moveRight(i,room);
		if(t==2)return moveLeft(i,room);
		return moveBack(i,room);
	}}
