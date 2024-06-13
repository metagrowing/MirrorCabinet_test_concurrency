import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Interface extends JFrame  
{
	public static Cabinet cabinet;
	public static int pov,state;
	JPanel panel;
	 
	public static void main(String[] args)
	{
		Random rand=new Random();
		Cabinet room=new Cabinet(rand.nextInt(10)+1,rand.nextInt(10)+1,5,0,0,0);//18
		//room.addHor(5,8,3,1);
		//room.addHor(5, 5,3, 2);
        Interface frame=new Interface(room,0);
        
        
        
        frame.setVisible(true); 
    /*  while(true){// if(false){// 
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Cabinet.maxdepth++;
        if(room.shape[1].moveForward(1,room))
        frame.repaint();
        else room.shape[1].turn(0);
        }*/
	}
	
	public Interface(Cabinet room, int p)  
	{
		pov=p;
		cabinet=room;
		
		setTitle("Mirror Cabinet");
		setSize(1080,1100);//1080, 1080);//
		setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
		panel=new MirrorPanel();
		add(panel);
		
	    addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                move(e);
            }

            private void move(KeyEvent e)
            {boolean eligble=false;
            	 switch (e.getKeyCode()) 
            	 {
                 case KeyEvent.VK_UP:
                 state=    room.shape[0].moveForward(0, room);
                 eligble=true;
                     break;
                 case KeyEvent.VK_DOWN:
                state=	  room.shape[0].moveBack(0, room);
                eligble=true;
                     break;
                 case KeyEvent.VK_RIGHT:
                	state=  room.shape[0].moveRight(0, room);
                	 eligble=true;
                      break;
                 case KeyEvent.VK_LEFT:
               	state=  room.shape[0].moveLeft(0, room);
                eligble=true; 
                     break;
                 case KeyEvent.VK_X:
                	 room.reset(room.level);
                	 eligble=true;
                	 break;
                 case KeyEvent.VK_1:
                	 room.shape[0].turn(2);
                	 eligble=true;
                	 break;
                 case KeyEvent.VK_3:
                	 room.shape[0].turn(1);
                	 eligble=true;
                	 break;
                
            	 }  
            	// if(eligble)
            	 repaint();
            	 
			}

			@Override
            public void keyReleased(KeyEvent e) {
            }
			
	    });
	     setVisible(true);
		
		   
	}
	
	class MirrorPanel extends JPanel
	{
		Cabinet cabinet=Interface.cabinet;
		int pov=Interface.pov;
		 @Override
 	    protected void paintComponent(Graphics g) 
		 {
 	        super.paintComponent(g);
 	        
 	  	 Graphics2D g2d = (Graphics2D)g;
	        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
 	      //  setBackground(Color.black);
 	        //if(state==0)
 	        cabinet.draw(g,pov,state);
 	      /*  else if(state==-1)
 	        	g.drawChars(new char[] {'B','A','M'}, 0, 3, 100, 100);
 	        else
 	        {
 	        	g.setColor(Color.black);
 	        	setBackground(cabinet.shape[state-1].color);
 	        	g.drawChars(new char[] {'F','O','U','N','D'}, 0, 5, 100, 100);
 	        	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

 	        }*/
		 }
		 
		 
	}
}
