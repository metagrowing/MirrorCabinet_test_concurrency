import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

public class Cabinet 
{
	static int[] size;
	int[][][][]panes;
	int[][]objects;
	int level;
	Shape[] shape;
	static int colordepth=1,
			width=1080,height=1080,
			maxdepth=201;
	static double vt=0.1,perm=0.65;
	static Color[] colors= {Color.BLUE, Color.black, Color.cyan,Color.green};
	public Cabinet(int x, int y, int z, int wall, int mirror, int window)
	{
		level=wall+mirror+window;
		Random rand=new Random();
		size=new int[] {x,y,z};
		shape=new Shape[] {Shape.mid, new Shape(rand.nextInt(size[0]),rand.nextInt(size[1]),0)};
		shape[1].color=Color.orange;
		panes=new int[2][size[0]][size[1]][2];
		objects=new int[size[0]][size[1]];
		
		for(int i=0;i<wall;i++) 
		{
			
			int dir=rand.nextInt(2);
			int[]step=new int[2];
			step[1-dir]=1;
			int length=rand.nextInt(size[dir]-1)+1;
			int x1=rand.nextInt(size[0]-step[0]*length),
					y1=rand.nextInt(size[1]-step[1]*length),
					c=rand.nextInt(colors.length);System.out.println(dir+", "+length+","+x1+","+y1);
			for(int j=0;j<length;j++)
			{
				panes[dir][x1+step[0]*j][y1+step[1]*j][0]=1;
				panes[dir][x1+step[0]*j][y1+step[1]*j][1]=c;
			}
		}
		for(int i=0;i<mirror;i++) 
		{
			int dir=rand.nextInt(2);
			int[]step=new int[2];
			step[1-dir]=1;
			int length=rand.nextInt(size[dir]-1)+1;
			int x1=rand.nextInt(size[0]-step[0]*length),
					y1=rand.nextInt(size[1]-step[1]*length),
							c=rand.nextInt(colors.length);
			for(int j=0;j<length;j++)
			{
				panes[dir][x1+step[0]*j][y1+step[1]*j][0]=2;
				panes[dir][x1+step[0]*j][y1+step[1]*j][1]=c;
			}
			System.out.println(dir+", "+length+","+x1+","+y1);
		}
		for(int i=0;i<window;i++) 
		{
			int dir=rand.nextInt(2);
			int[]step=new int[2];
			step[1-dir]=1;
			int length=rand.nextInt(size[dir]-1)+1;
			int x1=rand.nextInt(size[0]-step[0]*length),
					y1=rand.nextInt(size[1]-step[1]*length),
							c=rand.nextInt(colors.length);
			for(int j=0;j<length;j++)
			{
				panes[dir][x1+step[0]*j][y1+step[1]*j][0]=-1;
				panes[dir][x1+step[0]*j][y1+step[1]*j][1]=c;
			}
		}
		for(int i=0;i<shape.length;i++)
		{
			objects[shape[i].loc[0]][shape[i].loc[1]]=i+1;
		}
	}

	public Cabinet(int x, int y, int z) {
		size=new int[] {x,y,z};
		shape=new Shape[] {Shape.mid};
		panes=new int[2][size[0]][size[1]][2];	

		objects=new int[size[0]][size[1]];
		
		for(int i=0;i<shape.length;i++)
		{
			objects[shape[i].loc[0]][shape[i].loc[1]]=i+1;
		}
		}
public void reset(int l)
{
	Random rand=new Random();
	level=l;
	size[0]=rand.nextInt((int) (10*Math.sqrt(l+1)))+1;
	size[1]=rand.nextInt((int) (10*Math.sqrt(l+1)))+1;
    System.out.println("level "+level);
	objects=new int[size[0]][size[1]];
	
	shape=new Shape[] {new Shape(size[0]/2,size[1]/2,0), new Shape(rand.nextInt(size[0]),rand.nextInt(size[1]),0)};
	shape[1].color=new Color(255*rand.nextInt(colordepth+1)/colordepth,255*rand.nextInt(colordepth+1)/colordepth,255*rand.nextInt(colordepth+1)/colordepth);
	panes=new int[2][size[0]][size[1]][2];
	for(int i=0;i<shape.length;i++)
	{
		objects[shape[i].loc[0]][shape[i].loc[1]]=i+1;
	}
	for(int i=0;i<l;i++)
	{
		int dir=rand.nextInt(2),
				kind=rand.nextInt(3);
		if(kind==0)kind=-1;
		int[]step=new int[2];
		step[1-dir]=1;
		int length=size[1-dir]-(int)Math.sqrt( rand.nextInt(size[1-dir]*size[1-dir])-1);
		System.out.println("size0="+size[0]+", size1="+size[1]+"dir="+dir+", length="+length);
		int x1=rand.nextInt(size[0]-step[0]*(length-1)),
				y1=rand.nextInt(size[1]-step[1]*(length-1)),
						c=rand.nextInt(colors.length);
		System.out.println("x="+x1+", y="+y1);
		for(int j=0;j<length;j++)
		{
			panes[dir][x1+step[0]*j][y1+step[1]*j][0]=kind;
			panes[dir][x1+step[0]*j][y1+step[1]*j][1]=c;
		}
		System.out.println(dir+", "+length+","+x1+","+y1);
	}
}


	public void draw(Graphics g, int pov, int state) 
	{
		int[]origin=shape[pov].loc;
		int k=0;
		int dir=shape[pov].orientation;
		double min=0,max=1;
		boolean mirror=false;
		System.out.println(shape[0].loc[0]+","+shape[0].loc[1]);
		boolean success=(state>0);
		if(state<1)
		{ 
			int s=shape[1].move(1,this);
			success=(s>0);
			if(!success)
			{
			draw(g,origin,dir,k,min,max,mirror,colors[0],1);
			
			if(state<0)
			{ 
				g.setColor(Color.pink);
				g.setFont(new Font("SansSerif", Font.BOLD, 55));
			    if(state==-1)    g.drawString("BOINK", 100, height/2);
			    else if(state==-2 )  g.drawString("BOINK",width- 300, height/2);
			    else   g.drawString("BOINK", width/2-100, height/2);
			   
			}
			}
		}
		if( success)	{
			g.setColor(shape[1].color);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.white);
			g.setFont(new Font("SansSerif", Font.BOLD, 55));
			
		    g.drawString("SUCCESS!", width/2-100, height/2);
		        g.setColor(Color.black);g.setFont(new Font("SansSerif", Font.BOLD, 55));
		        g.drawString("SUCCESS!", width/2-90, height/2-10);
		        
		        g.setFont(new Font("SansSerif", Font.BOLD, 30));
		        g.drawString("On to Level "+(level+1), width/2-90, height/2+50);
		       this.reset(level+1);
		      
		}
		
		
		Interface.state=0;
	//	 Graphics2D g2= (Graphics2D)g;
	//	 g2.fill(new Ellipse2D.Double(0,0,720,720));
	}

	private void draw(Graphics g, int[] origin,int dir, int level, double min, double max, boolean mirror,Color col, double visibility) 
	{
		ArrayList<double[]>objloc=new ArrayList<double[]>();
		ArrayList<int[]>obj=new ArrayList<int[]>();
		ArrayList<double[]>regions=new ArrayList<double[]>();
		regions.add(new double[] {min,max});
		int k=level+1,or=dir%2,d=(int) Math.pow(-1,dir/2),objcounter;
		int[]along=new int[2],left=new int[2];
		along[or]=d;
		left[1-or]=(int) (Math.pow(-1, or)*d);//System.out.print("along then left:");
	//	print(along);
	//	print(left);
		if(mirror)left[1-or]*=-1;//or is it?
		while(!regions.isEmpty()&&k<maxdepth)//
		{
			
			int r=0;
			while(r<regions.size())
			{
				//System.out.println("r="+r);
				double mi=regions.get(r)[0],ma=regions.get(r)[1];
				boolean done=false;
			int counter=0, last=0,color=-1, i=(int)(mi*k);
			if(k%2==1)//horizontal walls
			{
				
				while(i<ma*k&&i>mi*k-1)
				{
					int x=mod((2*origin[0]+1+(k)*along[0]-(1+2*i-k)*left[0])/2,size[0]),y=mod((2*origin[1]+1+(k)*along[1]-(1+2*i-k)*left[1])/2,size[1]);
					if(panes[or][x][y][0]!=0)
					{
						//System.out.print("to draw:"+x+","+y+", i="+i);
						if(last==panes[or][x][y][0]&&color==panes[or][x][y][1])
						counter++;
						else
						{
							if(last!=0)
							{
							//	System.out.println("draw the one before");
							done=drawPane(last, color, col, visibility, g, mi, ma, i, mirror, counter, k, dir, origin,regions,r);
							if(r<regions.size())
							{
								mi=regions.get(r)[0];ma=regions.get(r)[1];
							}
							else {mi=1;ma=1;}
							}
							counter=1;
							last=panes[or][x][y][0];
							color=panes[or][x][y][1];
						}
					}
					else if(counter>0)
					{
						done=drawPane(last, color, col, visibility, g, mi, ma, i, mirror, counter, k,dir, origin,regions, r);
						if(r<regions.size()){
							mi=regions.get(r)[0];ma=regions.get(r)[1];
						}
						else {mi=1;ma=1;}
						
						counter=0;
						last=0;
						color=-1;
					}
					
					i++;//System.out.println("i="+i);
				}
				if(counter>0)
					done=drawPane(last, color, col, visibility, g, mi, ma, i, mirror, counter, k,dir, origin,regions, r);
					if(r<regions.size()){
						mi=regions.get(r)[0];ma=regions.get(r)[1];
					}
					else {mi=1;ma=1;}
			}
			else//vertical walls&& objects
			{
				int[][]objik=new int[k+1][3];
				double[][]objl=new double[k+1][2];
				//objects
				i=0;
				while(i<k+1)
				{
					int x=mod((origin[0]+k/2*along[0]+(k/2-i)*left[0]),size[0]),
							y=mod((origin[1]+k/2*along[1]+(k/2-i)*left[1]),size[1]);
					if(objects[x][y]!=0)
					{
						//System.out.println("object detected for i="+i);
						objik[i]=new int[] {k,i,objects[x][y]};
						objl[i]=new double[] {mi,ma};
					}
					i++;
				}
				
				
				//vertical walls
				i=(int) Math.min((k+1)*mi,(k-1)*mi+1);
				while((i<(k-1)*ma || i<(k+1)*ma-1)&& !done)
				{
					int x=mod((2*origin[0]+1+k*along[0]+(k-1-2*i)*left[0])/2,size[0]),
						y=mod((2*origin[1]+1+k*along[1]+(k-1-2*i)*left[1])/2,size[1]);
				//System.out.print("x,y="+x+","+y);
					if(panes[1-or][x][y][0]!=0)
					{//	System.out.print("to draw:"+x+","+y);
						if(i<k/2&&objik[i][0]==k)
						{
							objl[i][1]=Math.min(objl[i][1], i/(k-1.0));
						}
						else if(i>k/2-1&&objik[i+1][0]==k)
						{
							objl[i+1][0]=Math.max(objl[i+1][1], (i+1)/(k-1.0));
						}
							
									done=drawPane(panes[1-or][x][y][0], panes[1-or][x][y][1], col, visibility, g, mi, ma, i, mirror, 1, k,dir, origin,regions, r);
									if(r<regions.size()&&!done){
										mi=regions.get(r)[0];ma=regions.get(r)[1];
									}
							
					}
		if(mi>ma)System.out.println("MI>MA");
					i++;//System.out.println("i="+i);
				}
				int sign=-1,c=0;
				for(int j=k/2;j<k+1;j+=sign*c)
				{
					//System.out.println("j="+j);
					c++;sign*=-1;
					if(objik[j][0]==k)
					{
						obj.add(objik[j]);
						objloc.add(objl[j]);
					}
				}
			}
				
			if(!done)r++;
		}
			k++;
			//System.out.println("k="+k);
		}
	//draw empty space
		for(int i=0;i<regions.size();i++)
		{
			g.setColor(mix(Color.white,col,visibility));
			int x0=(int)(width*regions.get(i)[0]),
					x1=(int)(width*regions.get(i)[1]);
			
			g.fillRect(x0,0,x1-x0,height/2);
			g.setColor(mix(Color.yellow,col,visibility));
			g.fillRect(x0,height/2,x1-x0,height/2);
		}
	//draw objects
		for(int i=obj.size()-1;i>-1;i--)
		{
			shape[obj.get(i)[2]-1].draw(obj.get(i),objloc.get(i),col,visibility,origin[2],g);
		}
		
		//System.out.println("done");
	}
	
	private void print(int[] left) 
	{
		System.out.print("{");
		for(int i=0;i<left.length;i++)
			System.out.print(left[i]+",");
		System.out.print("}");
	}

	static int mod(int i, int j) 
	{
		return (i%j+j)%j;
	}

	private boolean drawPane(int last,int color, Color col, double visibility, Graphics g, double mi, double ma, int i,boolean mir, int counter, int k,int dir, int[]origin, ArrayList<double[]> regions,int r)
	{if(mi<ma){
		int y0,y1;
		//System.out.println("drawPane");
		
		double 	k0=k,k1=k,i0=i-counter,i1=i;
		if(k%2==0)
		{
			if(2*i>k-2)
			{
				k0=k+1;
				k1=k-1;
				i0=i+1;
				i1=i;
			}
			else
			{
				k0=k-1;
				k1=k+1;
				i0=i;
				i1=i+1;
			}
		}
		double left=i0/k0,
				right=i1/k1;
		left=Math.max(mi,left);
		right=Math.min(ma, right);
		if(left<right) {
		int x0=(int) (width*left),
				x1=(int)(width*right);	//System.out.println("type="+last+",color="+color+",counter="+counter+",i0="+i0+",i1="+i1+",k0="+k0+",k1="+k1+"left="+left+",right="+right);
		if(last==-1&&visibility>vt)
		{
			//System.out.println("draw between "+left+" and "+right+", depth="+k);
			int d=dir;
			draw(g,origin,d,k,left,right,mir,mix(colors[color],col,visibility),visibility*perm);
		/*	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		else if(last>1&&visibility>vt)
		{
			int d=dir;
			if(k%2==1) {d=(d+2)%4;}
			int[]or=mirror(origin,k,i,dir,mir);
			draw(g,or,d,k,left,right,!mir,mix(colors[color],col,visibility),visibility*perm);

		}
		else
		{
	
			g.setColor(mix(colors[color],col,visibility));
			
				
			g.fillRect(x0,0,x1-x0,height);
		}
double leftfactor=(left-0.5)/(i0/k0-0.5),rightfactor=(right-0.5)/(i1/k1-0.5);
			//ceiling
			g.setColor(mix(Color.white,col,visibility));
			if(k%2==0)
			{
				
				y0=(int)((height*0.5-width*(size[2]-origin[2]-0.5)/k0*leftfactor));
				y1=(int)((height*0.5-width*(size[2]-origin[2]-0.5)/k1*rightfactor));
				int[]x= {x0,x0,x1,x1},
						y= {0,y0,y1,0};
				g.drawPolygon(x,y,4);
				g.fillPolygon(x,y,4);
			}
			else
			{	
				y0=(int)((height*0.5-width*(size[2]-origin[2]-0.5)/k0));	//System.out.println("ceiling"+x0+","+x1+","+y0+";"+g.getColor().getGreen());
				
				g.fillRect(x0,0,x1-x0+1,y0);
				
			}
			//floor
			g.setColor(mix(Color.yellow,col,visibility));
			if(k%2==0)
			{
				y0=(int)((height*0.5+width*(origin[2]+0.5)/k0*leftfactor));
				y1=(int)((height*0.5+width*(origin[2]+0.5)/k1*rightfactor));
				int[]x= {x0,x0,x1,x1},
						y= {height-1,y0,y1,height-1};
				g.drawPolygon(x,y,4);
				g.fillPolygon(x,y,4);
			}
			else
			{
				y0=(int)((height*0.5+width*(origin[2]+0.5)*1.0/k));
				g.fillRect(x0,y0,x1-x0+1,height-y0);
			}
		
		if(mi<left)
		{
			regions.get(r)[1]=left;
			
			if(ma>right)
			{
				regions.add(r+1,new double[] {right,ma});
			}
			 return false;
		}
		if(ma>right) {regions.get(r)[0]=right;return false;}
		else 
		{
			regions.remove(r);
			return true;
		}}
		else return false;
		
	}System.out.println("mi>ma");regions.remove(r);return true;
	}

	private int[] mirror(int[] origin, int k,int i,int dir, boolean mir) 
	{
		int[]out=origin.clone();
		int d=dir%2,or=(int) Math.pow(-1, dir/2);
		if(k%2==1)
		out[d]+=k*or;
		else
		{
			int p=1;
			if(mir)p=-1;
			out[1-d]+=or*Math.pow(-1, d)*(k-1-2*i)*p;
		}
		for(int i1=0;i1<2;i1++)
		out[i1]=mod(out[i1],size[i1]);
		
		//System.out.println(out[0]+","+out[1]);
		return out;
	}

	public static Color mix(Color c1, Color c2, double r) 
	{
		return new Color((int) (c1.getRed()*r+c2.getRed()*(1-r)),(int) (c1.getGreen()*r+c2.getGreen()*(1-r)),(int) (c1.getBlue()*r+c2.getBlue()*(1-r)));
	}

	public void addHor(int x, int y, int l,int col) 
	{
		for(int i=y;i<y+l;i++)
			panes[0][x][i]=new int[] {1,col};
	}

	public void addVer(int x, int y, int l, int col) {
		for(int i=x;i<x+l;i++)
			panes[1][i][y]=new int[] {2,col};
	}
	
}
