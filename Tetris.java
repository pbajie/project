package smallGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



import java.awt.event.ActionEvent;



public class Tetris {
    /** game size*/
	 private final int ROW = 20;
	 private final int COL = 10;
	 private final int LEN = 35;
	 /** game limited*/
	 private final int LEFT_MARGIN = LEN*2;
	 private final int UP_MARGIN = LEN;  
	 /** frame size*/
	 private final int AREA_WIDTH = LEN*22;
	 private final int AREA_HEIGHT = LEN*22;
	 
	 private boolean show = true;
	 
	 private boolean isColor = true;
	 
	 /**score*/
	 private int score = 0;
	 
	 private panel drawArea = new panel();
	 
	//window
	 private JFrame f=new JFrame("Tetris");
	 
	 //public JFrame getframe() {
	//	 return f;
	// }
	//image
	 private BufferedImage image = new BufferedImage(LEN*24, LEN*24, BufferedImage.TYPE_INT_RGB);
	 private Graphics g = image.createGraphics(); 
	 
	 /**use array to store the background*/
	 private int[][] map= new int[COL][ROW];
	 
	 /**store color*/
	 private Color[] color = new Color[]{Color.BLUE, Color.cyan, Color.pink, Color.GREEN, Color.black, Color.yellow, Color.cyan, Color.gray};
	 private final int DEFAULT=7;
	 
	 private Color[][] mapColor=new Color[COL][ROW];
	 //coordinate
	 int wordX=LEN*14;
	 int wordY=LEN*9;
	 //shape
	 private int type, state,nextType, nextState,x,y;
	 
	 //if new begin, give a random type 
	 private boolean NBegin=true;
	 
	 //use array（four-dimensional)to show different shapes (type, State for rotate, corrdinate x and y)
	 private int [][][][] shape= new int [][][][] {
		// s:
	        { { {0,1,0,0}, {1,1,0,0}, {1,0,0,0}, {0,0,0,0} }, 
	        { {0,0,0,0}, {1,1,0,0}, {0,1,1,0}, {0,0,0,0} }, 
	        { {0,1,0,0}, {1,1,0,0}, {1,0,0,0}, {0,0,0,0} }, 
	        { {0,0,0,0}, {1,1,0,0}, {0,1,1,0}, {0,0,0,0} } }, 
	    //L:
	        { { {0,0,0,0}, {1,1,1,0}, {0,0,1,0}, {0,0,0,0} }, 
	          { {0,0,0,0}, {0,1,1,0}, {0,1,0,0}, {0,1,0,0} }, 
	          { {0,0,0,0}, {0,1,0,0}, {0,1,1,1}, {0,0,0,0} }, 
	          { {0,0,1,0}, {0,0,1,0}, {0,1,1,0}, {0,0,0,0} } },
	   //J:
	        { { {0,0,0,0}, {0,0,1,0}, {1,1,1,0}, {0,0,0,0} }, 
	          { {0,0,0,0}, {0,1,1,0}, {0,0,1,0}, {0,0,1,0} }, 
	          { {0,0,0,0}, {0,1,1,1}, {0,1,0,0}, {0,0,0,0} }, 
	          { {0,1,0,0}, {0,1,0,0}, {0,1,1,0}, {0,0,0,0} } },
	   //I:
	        { { {0,1,0,0}, {0,1,0,0}, {0,1,0,0}, {0,1,0,0} }, 
	          { {0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0} }, 
	          { {0,1,0,0}, {0,1,0,0}, {0,1,0,0}, {0,1,0,0} }, 
	          { {0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0} } },
	   //o
	       
	        { { {0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0} }, 
	          { {0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}  }, 
	          { {0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}  }, 
	          { {0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}  } },
	   //z:
	        { { {1,0,0,0}, {1,1,0,0}, {0,1,0,0}, {0,0,0,0} }, 
	          { {0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0} }, 
	          { {1,0,0,0}, {1,1,0,0}, {0,1,0,0}, {0,0,0,0} }, 
	          { {0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0} } },
	  //T
	        { { {0,1,0,0}, {1,1,0,0}, {0,1,0,0}, {0,0,0,0} }, 
	          { {0,0,0,0}, {1,1,1,0}, {0,1,0,0}, {0,0,0,0} }, 
	          { {0,1,0,0}, {0,1,1,0}, {0,1,0,0}, {0,0,0,0} }, 
	          { {0,1,0,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0} } },  
	 };
	 /**
	  * default init
	  */
	 private void init(){
		 drawArea.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
		 
		 f.add(drawArea);
		 JCheckBox gridCB = new JCheckBox("show grid",true);
		 JCheckBox colorCB = new JCheckBox("color block", false);
		 gridCB.setBounds(wordX, wordY-LEN,LEN,LEN);
		 colorCB.setBounds(wordX, wordY-2*LEN,LEN,LEN);
		//Add keyboard monitor
		 f.addKeyListener(new KeyAdapter(){
	            public void keyPressed(KeyEvent e){
	                switch (e.getKeyCode()) {
	                    case KeyEvent.VK_UP:
	                        turn();
	                        break;
	                    case KeyEvent.VK_LEFT:
	                        left();
	                        break;
	                    case KeyEvent.VK_RIGHT:
	                        right();
	                        break;
	                    case KeyEvent.VK_DOWN:
	                        down();
	                        break;
	                }                
	            }
	        }); 
		 Timer timer = new Timer(1000, new timerListener());
	     newShape();
	     timer.start();
		 //get window in the middle
	     f.pack();
	     int screenSizeX = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	     int screenSizeY = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	     int fSizeX = (int)f.getSize().getWidth();
	     int fSizeY = (int)f.getSize().getHeight();
	     f.setResizable(false);   
	     f.setBounds((screenSizeX-fSizeX)/2, (screenSizeY-fSizeY)/2, fSizeX,fSizeY );    
	     f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	     f.setVisible(true);
	 }
	
	
	 //	start paint area
	 private void paintArea(){
	        //  default is black , use white
	        g.setColor(Color.white);
	        g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);
	        //  block line
	        //  paint out
	        g.setColor(Color.gray);
	        for (int offset = 0; offset <= 2; offset++){
	            g.drawRect(LEFT_MARGIN-offset, UP_MARGIN-offset, COL*LEN+offset*2, ROW*LEN+offset*2);
	        }
	        
	        //  如果showGrid为true则显示网格
	        if(show){
	            g.setColor(Color.gray);
	            //9 column
	            for (int i = 1 ; i <= 9; i++){
	                g.drawLine(LEFT_MARGIN+LEN*i, UP_MARGIN, LEFT_MARGIN+LEN*i, UP_MARGIN+ROW*LEN);
	            }
	            // 19'
	            for(int i = 1; i <= 19; i++){
	                g.drawLine(LEFT_MARGIN, UP_MARGIN+LEN*i, LEFT_MARGIN+COL*LEN, UP_MARGIN+LEN*i);
	            }
	        }
	        // right show next      
	        int offset2 = 3;
	        int col = 4;// columns
	        int row = 4;// rows
	        g.setColor(Color.gray);
	        g.setFont(new Font("Microsoft YaHei Mono", Font.BOLD, 20));
	        g.drawString("next:", wordX, LEN*2);
	        int nextX = wordX;
	        int nextY = LEN*2;
	        
	        //next block 
	        g.setColor(isColor?color[nextType]:color[DEFAULT]);
	        for(int i = 0; i < 4; i++){
	            for(int j = 0; j < 4; j++){
	                if (shape[nextType][nextState][i][j]==1) {
	                    g.fill3DRect(nextX+10+i*LEN, nextY+10+j*LEN, LEN, LEN,true);               
	                }
	            }
	        }
	        g.setColor(Color.gray);
	        g.setFont(new Font("Times", Font.BOLD, 15));      
	        g.drawString("Play：", wordX, wordY+LEN*2);
	        g.drawString("up arrow：turn", wordX, wordY+LEN*3);
	        g.drawString("left arrow：left turn", wordX, wordY+LEN*4);
	        g.drawString("right arrow ：right turn ", wordX, wordY+LEN*5);
	        g.drawString("down arrow ：fall", wordX, wordY+LEN*6);
	        g.setFont(new Font("Times", Font.BOLD, 25));
	        g.drawString("score：" + score, wordX, wordY+LEN*8);
	        //draw falling shape
	        g.setColor(isColor?color[type]:color[DEFAULT]);
	        for(int i = 0; i < 4; i++){
	            for(int j = 0; j < 4; j++){
	                if (shape[type][state][i][j]==1) {
	                    g.fill3DRect(LEFT_MARGIN+(x+i)*LEN, UP_MARGIN+(y+j)*LEN, LEN, LEN,true);               
	                }
	            }
	        }
	        //draw background map
	        for(int i = 0; i < COL; i++){
	            for(int j = 0; j < ROW; j++){
	                if (map[i][j] == 1) {
	                    g.setColor(mapColor[i][j]);
	                    g.fill3DRect(LEFT_MARGIN+i*LEN, UP_MARGIN+j*LEN, LEN, LEN,true);
	                }
	            }
	        }
	 
	        drawArea.repaint();
	    }
	 private class panel extends JPanel{
	        public void paint(Graphics g){
	            g.drawImage(image, 0, 0, null);
	        }
	        //public int getWidth() {
	      //  	return WIDTH;
	      //  }
	     //   public int getHight() {
	     //   	return HEIGHT;
	     //   }
	 }
	 //check location 
	 private boolean check(int type, int state, int x, int y){
	        for(int i = 0; i < 4; i++){
	            for(int j = 0; j < 4; j++){
	                if ( (shape[type][state][i][j] == 1) && ( (x+i>=COL) || (x+i<0 ) || (y+j>=ROW) || (map[x+i][y+j]==1) ) ) {
	                    return false;
	                }
	            }
	        }
	        return true;
	    }
	 //check game is over or not
	 private boolean isGameOver(int type, int state, int x, int y){
	        return !check(type, state, x, y);
	 }
	 
	 // create new shape
	 private void newShape(){
	        Random rand = new Random();
	        if(NBegin){
	            type = rand.nextInt(7); 
	            state = rand.nextInt(4); 
	            NBegin = false;
	        }
	        else{
	            type = nextType;        
	            state = nextState;
	        }        
	        nextType = rand.nextInt(7); 
	        nextState = rand.nextInt(4);        
	        x = 3;
	        y = 0;
	        // if game is over ，begin again
	        if(isGameOver(type, state, x, y)){            
	            JOptionPane.showMessageDialog(f, "GAME OVER!");
	            newGame();
	        }
	        paintArea();
	        
	    }

     /**
      * reate new game
      */
	 private void newGame(){
	        newMap();
	        score = 0;
	        NBegin = true;
	  }
	  /**
	   * clean background
	   */
	 private void newMap(){
	        for(int i = 0; i < COL; i++){
	            Arrays.fill(map[i],0);
	        }        
	    }
     //delete line 
	 private void deleteLine(){
	        boolean flag = true;
	        int addScore = 0;
	        for(int j = 0; j < ROW; j++){
	            flag = true;
	            for( int i = 0; i < COL; i++){
	                if (map[i][j]==0){
	                    flag = false;
	                    break;
	                }
	            }
	            if(flag){
	                addScore += 10;
	                for(int t = j; t > 0; t--){
	                    for(int i = 0; i <COL; i++){
	                        map[i][t] = map[i][t-1];                        
	                    }
	                }
	            }        
	        }
	        score += addScore*addScore/COL;
	    }
	
	 private class timerListener implements ActionListener{
	        public void actionPerformed(ActionEvent e){
	            if(check(type, state , x, y+1) ){
	                y = y +1;
	            }
	            else{
	               add(type, state, x, y);
	               deleteLine();
	               newShape();
	            }
	            paintArea();
	        }
	    }
	 private void add(int type, int state, int x, int y){
	        for(int i = 0; i < 4; i++){
	            for(int j = 0; j < 4 ; j++){
	                if((y+j<ROW)&&(x+i<COL)&&(x+i>=0)&&(map[x+i][y+j]==0)){
	                    map[x+i][y+j]=shape[type][state][i][j];
	                    mapColor[x+i][y+j]=color[isColor?type:DEFAULT];
	                }
	            }
	        }
	 }
	 
	 private void turn(){
	        int tmpState = state;
	        state = (state + 1)%4;
	        if (!check(type,state, x, y )) {
	            state = tmpState;           
	        }
	        paintArea();
	 }
	 private void left(){
	        if(check(type,state, x-1, y)){
	            --x;
	        }
	        paintArea();
	 }
	 private void right(){
	        if (check(type,state, x+1, y)) {
	            ++x;
	        }
	        paintArea();
	 }
	 private void down(){
	        if (check(type,state, x, y+1)) {
	            ++y;
	        }
	        //fix 
	        else{
	            add(type, state, x, y);
	            deleteLine();
	            newShape();
	        }
	        paintArea();
	 }

	
	 
	 
	 
	 
	 public static void main(String[] args) {
		 new Tetris().init();
	
	 }
	 
}
