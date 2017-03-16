import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
public Color randColor(){
		
		Color[] colors;
		colors = new Color[5];
		colors[0]=Color.YELLOW;
		colors[1]=Color.MAGENTA;
		colors[2]=Color.BLACK;
		colors[3]=new Color(0x964B00);
		colors[4]=new Color(0xB57EDC);
		
		Color newColor = colors[generator.nextInt(5)];
		
		return newColor;
}
		
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						if ((gridX == 0) || (gridY == 0)) {
				
							Color newColor;
						
								if(!(gridX==0 && gridY==0)){
									if(gridX==0){
										for(int i =0; i<9; i++){
											newColor = randColor();
											myPanel.colorArray[i+1][myPanel.mouseDownGridY]=newColor;
											
										}
									}
									if(gridY == 0){
										for(int j = 1; j<10 ; j++){
											newColor = randColor();
											myPanel.colorArray[myPanel.mouseDownGridX][j] = newColor;
											
										}
									}
										
								}else{
									for(int i = 1; i<10 ; i++){
										newColor = randColor();
										myPanel.colorArray[i][i] = newColor;
									}
									
								}
							//On the left column and on the top row... do nothing
						
						} 
						
						else {
							//On the grid other than on the left column and on the top row:
							//I decided to create an array with all the given Colors
						
							Color newColor;
							//Afterwards I proceeded it with a do while, because it runs at least once
							do{
								
							newColor = randColor();
							
							//Here I added an if statement so that i can change the color it its the same as the one that was last saved
							if(newColor.equals(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY])){
								newColor = randColor();
								//I decided that for the new color it would be pick randomly
							}
							
							
							}while(newColor.equals(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]));
							
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							myPanel.repaint();
						}
						
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}