import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.PixelGrabber;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLine;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.PngjException;

public class Login  {
  Display display = new Display();
  Shell shell = new Shell(display);
  Label label1,label2;
  Text username;
  Text password;
  Text text;
  String pathTest,numTest = null;
  HashMap<String, String> usernames;
  HashMap<String, String> imgDatabase;
  Image img,imgScaled;
  Canvas canvasImage;
 
  public Login() {
  shell.setLayout(new GridLayout(2, false));
  shell.setText("Login form");
  label1=new Label(shell, SWT.NULL);
  label1.setText("User Name: ");
  
  username = new Text(shell, SWT.SINGLE | SWT.BORDER);
  username.setText("");
  username.setTextLimit(30);
  
  label2=new Label(shell, SWT.NULL);
  label2.setText("Password: ");
  
  password = new Text(shell, SWT.SINGLE | SWT.BORDER);
  System.out.println(password.getEchoChar());
  password.setEchoChar('*');
  password.setTextLimit(30);

  Button button=new Button(shell,SWT.PUSH);
  button.setText("Login");
  button.addListener(SWT.Selection, new Listener() {
  public void handleEvent(Event event) {
  String selected=username.getText();
  String selected1=password.getText();
  
  if(selected==""){ 
  MessageBox messageBox = new MessageBox(shell, SWT.OK |
  SWT.ICON_WARNING);
  messageBox.setMessage("Enter the User Name");
 messageBox.open();
  }
  if(selected1==""){
  MessageBox messageBox = new MessageBox(shell, SWT.OK |
   SWT.ICON_WARNING);
  messageBox.setMessage("Enter the Password");
  messageBox.open();
  }
  else{
	  FileInputStream fis = null;
	  ObjectInputStream in = null;
	  String path = "usernames.db";
	  try
   	     {
		 fis = new FileInputStream(path);
   	     in = new ObjectInputStream(fis);
   	     usernames = (HashMap)in.readObject();
   	     in.close();
   	     
   	     }
   	  catch(IOException ex)
   	     {
   	           ex.printStackTrace();
   	     }
	  catch(ClassNotFoundException ex1){
		  ex1.printStackTrace();
	  }
      if(usernames.containsKey(selected)&& usernames.get(selected).equals(selected1)){
    	
		FileDialog dialog = new FileDialog(shell, SWT.NULL);
	     String ext[] = {"*.*;*.png;*.jpg;*.bmp;*.gif;*.ico"};
	     dialog.setFilterExtensions(ext);
	     String imgPath = dialog.open();
	     if(imgPath!=null){
	    	 File file = new File(imgPath);
	     
	     if (file.isFile()){
	        openNewShell1(shell,file);
	     }
		
	     }
	     
	     
	     String[] args = new String[]{pathTest,numTest};
	     if(Compare.processImage("test.png", "combined\\"+numTest+".png")){
	    	 File file2 = new File("test.png");
	    	 openNewShell2(shell,file2);
	    	 shell.close();
	   	     shell.dispose();
	   	     display.dispose();
	   	     IrisRecognition.main(args);
	   	     
	     }
	     else{

	    	  MessageBox messageBox = new MessageBox(shell, SWT.OK |
	    	  SWT.ICON_WARNING);
	    	  messageBox.setMessage("Share is incorrect");
	    	  messageBox.open();
	     }
	     }
      else{

    	  MessageBox messageBox = new MessageBox(shell, SWT.OK |
    	  SWT.ICON_WARNING);
    	  messageBox.setMessage("Username/Password is incorrect");
    	  messageBox.open();
      }
    	  
    	  
  }
  }
  });
  Button button2=new Button(shell,SWT.PUSH);
  button2.setText("Register");
  button2.addListener(SWT.Selection, new Listener() {
  public void handleEvent(Event event) {
  String selected2=username.getText();
  String selected12=password.getText();
  
  if(selected2==""){ 
  MessageBox messageBox = new MessageBox(shell, SWT.OK |
  SWT.ICON_WARNING);
  messageBox.setMessage("Enter the User Name");
 messageBox.open();
  }
  if(selected12==""){
  MessageBox messageBox = new MessageBox(shell, SWT.OK |
   SWT.ICON_WARNING);
  messageBox.setMessage("Enter the Password");
  messageBox.open();
  }
  else{
	  FileInputStream fis = null;
	  ObjectInputStream in = null;
	  String path = "usernames.db";
	  usernames = new HashMap<String,String>();
	  File yourFile = new File(path);
	  if(!yourFile.exists()) {
	      try {
			yourFile.createNewFile();
			FileOutputStream fos = null;
	   	    ObjectOutputStream out = null;
	   	    usernames.put(selected2,selected12);
	   	    fos = new FileOutputStream(path);
	   	    out = new ObjectOutputStream(fos);
	   	    out.writeObject(usernames);
	   	    out.close();
	   	    System.out.println("Database saved at " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  
	  else{
		  
	  
	  try
   	     {
		 fis = new FileInputStream(yourFile);
   	     in = new ObjectInputStream(fis);
   	     usernames = (HashMap)in.readObject();
   	     in.close();
   	     usernames.put(selected2,selected12);
   	     FileOutputStream fos = null;
   	     ObjectOutputStream out = null;
   	     fos = new FileOutputStream(path);
   	     out = new ObjectOutputStream(fos);
   	     out.writeObject(usernames);
   	     out.close();
   	     System.out.println("Database saved at " + path);
   	     
      
   	     }
   	     
   	     
   	  catch(IOException  ex)
   	     {
   	           ex.printStackTrace();
   	     }
	  catch(ClassNotFoundException ex1){
		  ex1.printStackTrace();
	  }
	  }
	  
  }

     FileDialog dialog = new FileDialog(shell, SWT.NULL);
     String ext[] = {"*.*;*.png;*.jpg;*.bmp;*.gif;*.ico"};
     dialog.setFilterExtensions(ext);
     String imgPath = dialog.open();
     if(imgPath!=null){
    	 File file = new File(imgPath);
     
     if (file.isFile()){
    	 

         Random generator = new Random();

         generator.setSeed(System.currentTimeMillis());

         

         int num = generator.nextInt(900000)+100000;
    	 FileInputStream fis = null;
   	  	 ObjectInputStream in = null;
   	  	 String path = "imgDatabase.db";
   	  	 imgDatabase = new HashMap<String,String>();
   	  File yourFile = new File(path);
   	  if(yourFile.exists()) {
  
   		
   		  
   	  
   	  try
      	     {
   		  	 fis = new FileInputStream(yourFile);
      	     in = new ObjectInputStream(fis);
      	     imgDatabase = (HashMap)in.readObject();
      	     in.close();
      	     while(imgDatabase.containsKey(Integer.toString(num))){
      	    	num = generator.nextInt(900000)+100000;
      	     }
      	     }
      	     
      	     
      	  catch(IOException ex)
      	     {
      	           ex.printStackTrace();
      	     }

   	  catch(ClassNotFoundException ex1){
   		  ex1.printStackTrace();
   	  }
   	  }

         
        try {
			openNewShell(shell,file,num);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        MessageBox messageBox = new MessageBox(shell, SWT.OK |
        SWT.ICON_INFORMATION);
        

		BufferedImage img1 = new BufferedImage(256, 256,

		BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = img1.createGraphics();

		g2.setColor(Color.white);

		g2.fillRect(0, 0, 256, 256);

		g2.setColor(Color.black);

		g2.setFont(g2.getFont().deriveFont(30f));

		g2.drawString(Integer.toString(num), 70, 134);

		File outputfile = new File("numbers\\"+Integer.toString(num)
				+ ".png");

		if (!outputfile.exists()) {

			try {

				outputfile.createNewFile();

				OutputStream os = null;
				try {
					os = new FileOutputStream("numbers\\"+Integer.toString(num)
							+ ".png");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				writeARGB(img1, os);
				// javax.imageio.ImageIO.write(img1, "png",
				// outputfile);

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		BufferedImage img2 = new BufferedImage(256, 256,
				BufferedImage.TYPE_INT_ARGB);

		BufferedImage share1 = new BufferedImage(512, 512,
				BufferedImage.TYPE_INT_ARGB);

		BufferedImage share2 = new BufferedImage(512, 512,
				BufferedImage.TYPE_INT_ARGB);

		int white = Color.white.getRGB();

		int black = Color.black.getRGB();

		img2 = img1;

		for (int i = 0; i < img2.getHeight(); i++) {

			for (int j = 0; j < img2.getWidth(); j++) {

				Random gen = new Random();

				gen.setSeed(System.currentTimeMillis());

				int n1 = generator.nextInt(900000) + 100000;

				int color = img2.getRGB(j, i);

				if (color == white && n1 % 2 == 0) {

					share1.setRGB(2 * j, 2 * i, 0);

					share1.setRGB(2 * j + 1, 2 * i, black);

					share1.setRGB(2 * j, 2 * i + 1, black);

					share1.setRGB(2 * j + 1, 2 * i + 1, 0);

					share2.setRGB(2 * j, 2 * i, 0);

					share2.setRGB(2 * j + 1, 2 * i, black);

					share2.setRGB(2 * j, 2 * i + 1, black);

					share2.setRGB(2 * j + 1, 2 * i + 1, 0);

				}

				else if (color == black && n1 % 2 == 0) {

					share1.setRGB(2 * j, 2 * i, 0);

					share1.setRGB(2 * j + 1, 2 * i, black);

					share1.setRGB(2 * j, 2 * i + 1, black);

					share1.setRGB(2 * j + 1, 2 * i + 1, 0);

					share2.setRGB(2 * j, 2 * i, black);

					share2.setRGB(2 * j + 1, 2 * i, 0);

					share2.setRGB(2 * j, 2 * i + 1, 0);

					share2.setRGB(2 * j + 1, 2 * i + 1, black);

				}

				else if (color == black && n1 % 2 == 1) {

					share1.setRGB(2 * j, 2 * i, black);

					share1.setRGB(2 * j + 1, 2 * i, 0);

					share1.setRGB(2 * j, 2 * i + 1, 0);

					share1.setRGB(2 * j + 1, 2 * i + 1, black);

					share2.setRGB(2 * j, 2 * i, 0);

					share2.setRGB(2 * j + 1, 2 * i, black);

					share2.setRGB(2 * j, 2 * i + 1, black);

					share2.setRGB(2 * j + 1, 2 * i + 1, 0);

				}

				else if (color == white && n1 % 2 == 1) {

					share1.setRGB(2 * j, 2 * i, black);

					share1.setRGB(2 * j + 1, 2 * i, 0);

					share1.setRGB(2 * j, 2 * i + 1, 0);

					share1.setRGB(2 * j + 1, 2 * i + 1, black);

					share2.setRGB(2 * j, 2 * i, black);

					share2.setRGB(2 * j + 1, 2 * i, 0);

					share2.setRGB(2 * j, 2 * i + 1, 0);

					share2.setRGB(2 * j + 1, 2 * i + 1, black);

				}

				else {

					System.out.println("Color cannot be");

				}

			}

		}

		File outputfile1 = new File("shares\\"+Integer.toString(num)
				+ "share1.png");

		try {

			outputfile1.createNewFile();

			OutputStream os = null;
			try {
				os = new FileOutputStream("shares\\"+Integer.toString(num)+"share1.png");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			writeARGB(share1, os);

			// javax.imageio.ImageIO.write(share1, "png",
			// outputfile1);

		} catch (IOException e) {

			e.printStackTrace();

		}

		File share21 = new File("shares\\"+Integer.toString(num)+"share2.png");

		try {

			outputfile.createNewFile();

			OutputStream os = null;
			try {
				os = new FileOutputStream("shares\\"+Integer.toString(num)+"share2.png");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			writeARGB(share2, os);

			// javax.imageio.ImageIO.write(share2, "png",
			// share21);

		} catch (IOException e) {

			e.printStackTrace();

		}
		
		

		BufferedImage image = share1;

		BufferedImage overlay = share2;

		int w = Math.max(image.getWidth(), overlay.getWidth());

		int h = Math.max(image.getHeight(), overlay.getHeight());

		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		Graphics g = combined.getGraphics();

		g.drawImage(image, 0, 0, null);

		g.drawImage(overlay, 0, 0, null);
		OutputStream os = null;
		try {
			os = new FileOutputStream("combined\\"+Integer.toString(num)+".png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		writeARGB(combined, os);

  	  	fis = null;
  	    in = null;
  	  	path = "secret.db";
  	  	HashMap<String,String> secret = new HashMap<String,String>();
  	  	yourFile = new File(path);
  	  if(!yourFile.exists()) {
	      try {
			yourFile.createNewFile();
			FileOutputStream fos = null;
	   	    ObjectOutputStream out = null;
	   	    secret.put(selected2,Integer.toString(num));
	   	    fos = new FileOutputStream(path);
	   	    out = new ObjectOutputStream(fos);
	   	    out.writeObject(secret);
	   	    out.close();
	   	    System.out.println("Database saved at " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  
	  else{
		  
	  
	  try
   	     {
		 fis = new FileInputStream(yourFile);
   	     in = new ObjectInputStream(fis);
   	     secret = (HashMap)in.readObject();
   	     in.close();
   	     secret.put(selected2,Integer.toString(num));
   	     FileOutputStream fos = null;
   	     ObjectOutputStream out = null;
   	     fos = new FileOutputStream(path);
   	     out = new ObjectOutputStream(fos);
   	     out.writeObject(secret);
   	     out.close();
   	     System.out.println("Database saved at " + path);
   	     
      
   	     }
   	     
   	     
   	  catch(IOException ex)
   	     {
   	           ex.printStackTrace();
   	     }

	  catch(ClassNotFoundException ex1){
		  ex1.printStackTrace();
	  }
	  }
        
     }
     
     }
  	 
  }
  });
  username.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
  password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
  
  shell.pack();
  shell.open();
  
  while (!shell.isDisposed()) {
  if (!display.readAndDispatch()) {
  display.sleep();
  }
  }
 display.dispose();
  }

  public void openNewShell(final Shell shell,final File file, int num) throws IOException 
  {
      
      Shell child = new Shell(shell, SWT.TITLE|SWT.SYSTEM_MODAL| SWT.CLOSE | SWT.MAX);
      img = new Image(Display.getDefault(),file.getPath());
      child.setSize(800, 600);
      child.setLayout(new FillLayout());

      canvasImage = new Canvas(child, SWT.NONE);
      canvasImage.addPaintListener(new PaintListener()
      {
    	  public void paintControl(PaintEvent e){
    		  
    		  e.gc.drawImage(img,0,0);
      
    	  }
      });
      

      child.open();
      
      Button button=new Button(child,SWT.PUSH);
      button.setText("OK");
	  imgDatabase = new HashMap<String,String>();
      
	  MessageBox messageBox = new MessageBox(shell, SWT.OK |SWT.CANCEL|
	  SWT.ICON_QUESTION);
	  messageBox.setMessage("Choose this image");
	  if (messageBox.open()==SWT.OK){
	  
		  File destfile = new File("irisimages\\"+Integer.toString(num)+".png");
		  
		  FileUtils.copyFile(file, destfile);
      
    	  FileInputStream fis = null;
    	  ObjectInputStream in = null;
    	  String path = "imgDatabase.db";
    	  imgDatabase = new HashMap<String,String>();
    	  File yourFile = new File(path);
    	  if(!yourFile.exists()) {
    	      try {
    			yourFile.createNewFile();
    			FileOutputStream fos = null;
    	   	    ObjectOutputStream out = null;
    	   	    imgDatabase.put(Integer.toString(num),file.getName());
    	   	    fos = new FileOutputStream(path);
    	   	    out = new ObjectOutputStream(fos);
    	   	    out.writeObject(imgDatabase);
    	   	    out.close();
    	   	    System.out.println("Database saved at " + path);
    	   	    child.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	  }
    	  
    	  else{
    		  
    	  
    	  try
       	     {
    		 fis = new FileInputStream(yourFile);
       	     in = new ObjectInputStream(fis);
       	     imgDatabase = (HashMap)in.readObject();
       	     in.close();
       	     FileOutputStream fos = null;
       	     ObjectOutputStream out = null;
       	     imgDatabase.put(Integer.toString(num),file.getName());
       	     fos = new FileOutputStream(path);
       	     out = new ObjectOutputStream(fos);
       	     out.writeObject(imgDatabase);
       	     out.close();
       	     System.out.println("Database saved at " + path);
       	     
       	     child.close();
       	     }
       	     
       	     
       	  catch(IOException ex)
       	     {
       	           ex.printStackTrace();
       	     }

    	  catch(ClassNotFoundException ex1){
    		  ex1.printStackTrace();
    	  }
      }
	  }
	  else{
		  child.close();

		     FileDialog dialog = new FileDialog(shell, SWT.NULL);
		     String ext[] = {"*.*;*.png;*.jpg;*.bmp;*.gif;*.ico"};
		     dialog.setFilterExtensions(ext);
		     String imgPath = dialog.open();
		     if(imgPath!=null){
		    	 File file1 = new File(imgPath);
		     
		     if (file1.isFile()){
		        openNewShell(shell,file1,num);
		     }
		     
		     }
		  	 
		  
	  }
	
  }
  public static void writeARGB(BufferedImage bi, OutputStream os) {
		if (bi.getType() != BufferedImage.TYPE_INT_ARGB)
			throw new PngjException(
					"This method expects  BufferedImage.TYPE_INT_ARGB");
		ImageInfo imi = new ImageInfo(bi.getWidth(), bi.getHeight(), 8, true);
		PngWriter pngw = new PngWriter(os, imi);
		// pngw.setCompLevel(6); // tuning
		// pngw.setFilterType(FilterType.FILTER_PAETH); // tuning
		DataBufferInt db = ((DataBufferInt) bi.getRaster().getDataBuffer());
		if (db.getNumBanks() != 1)
			throw new PngjException("This method expects one bank");
		SinglePixelPackedSampleModel samplemodel = (SinglePixelPackedSampleModel) bi
				.getSampleModel();
		ImageLine line = new ImageLine(imi);
		int[] dbbuf = db.getData();
		for (int row = 0; row < imi.rows; row++) {
			int elem = samplemodel.getOffset(0, row);
			for (int col = 0, j = 0; col < imi.cols; col++) {
				int sample = dbbuf[elem++];
				line.scanline[j++] = (sample & 0xFF0000) >> 16; // R
				line.scanline[j++] = (sample & 0xFF00) >> 8; // G
				line.scanline[j++] = (sample & 0xFF); // B
				line.scanline[j++] = (((sample & 0xFF000000) >> 24) & 0xFF); // A
			}
			pngw.writeRow(line, row);
		}
		pngw.end();
	}

  public static String stripNonDigits(
          final CharSequence input /* inspired by seh's comment */){
  final StringBuilder sb = new StringBuilder(
          input.length() /* also inspired by seh's comment */);
  for(int i = 0; i < input.length(); i++){
      final char c = input.charAt(i);
      if(c > 47 && c < 58){
          sb.append(c);
      }
  }
  return sb.toString();
}
  public void openNewShell1(final Shell shell,final File file) 
  {
      
      Shell child = new Shell(shell, SWT.TITLE|SWT.SYSTEM_MODAL| SWT.CLOSE | SWT.MAX);
      final Image img = new Image(Display.getDefault(),file.getPath());
      child.setSize(800, 600);
      child.setLayout(new FillLayout());

      canvasImage = new Canvas(child, SWT.NONE);
      canvasImage.addPaintListener(new PaintListener()
      {
    	  public void paintControl(PaintEvent e){
    		  
    		  e.gc.drawImage(img,0,0);
      
    	  }
      });
      

      child.open();
      
      
	  MessageBox messageBox = new MessageBox(shell, SWT.OK |SWT.CANCEL|
	  SWT.ICON_QUESTION);
	  messageBox.setMessage("Choose your share");
	  if (messageBox.open()==SWT.OK){
	  
		  child.close();
		  
		  String num = stripNonDigits(file.getName());
		  num = num.substring(0, num.length()-1);
		  
		    FileInputStream fis = null;
	  	    ObjectInputStream in = null;
	  	  	String path = "secret.db";
	  	  	HashMap<String,String> secret = new HashMap<String,String>();
	  	  	File yourFile = new File(path);
	  	  
		  try
	   	     {
			 fis = new FileInputStream(yourFile);
	   	     in = new ObjectInputStream(fis);
	   	     secret = (HashMap)in.readObject();
	   	     in.close();
	      
	   	     }
	   	     
	   	     
	   	  catch(IOException ex)
	   	     {
	   	           ex.printStackTrace();
	   	     }

		  catch(ClassNotFoundException ex1){
			  ex1.printStackTrace();
		  }

		  java.awt.Image image1 = Toolkit.getDefaultToolkit().getImage(file.getPath());
		  java.awt.Image image2 = Toolkit.getDefaultToolkit().getImage("shares\\"+secret.get(username.getText())+"share2.png");
		  
		  BufferedImage image = toBufferedImage(image1);
		  BufferedImage overlay = toBufferedImage(image2);
		  
		  /*try{
		  
		  image = ImageIO.read(file);

			overlay = ImageIO.read(new File("shares\\"+num+"share2.png"));
		  }
		  catch (IOException e){
			  e.printStackTrace();
		  }*/

			int w = Math.max(image.getWidth(), overlay.getWidth());

			int h = Math.max(image.getHeight(), overlay.getHeight());

			BufferedImage combined = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_ARGB);

			Graphics g = combined.getGraphics();

			g.drawImage(image, 0, 0, null);

			g.drawImage(overlay, 0, 0, null);
			
			File f = new File("test.png");
			try {
				f.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			OutputStream os = null;
			try {
				os = new FileOutputStream("test.png");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			writeARGB(combined, os);
			
			numTest = num; 
			
			
			pathTest = "irisimages\\"+num+".png";
		  
		  
      
	  }
	  else{
		  child.close();

		     FileDialog dialog = new FileDialog(shell, SWT.NULL);
		     String ext[] = {"*.*;*.png;*.jpg;*.bmp;*.gif;*.ico"};
		     dialog.setFilterExtensions(ext);
		     String imgPath = dialog.open();
		     if(imgPath!=null){
		    	 File file1 = new File(imgPath);
		     
		     if (file1.isFile()){
		        openNewShell1(shell,file1);
		     }
		     
		     }
		  	 
		  
	  }
	
  }

  public static BufferedImage toBufferedImage(java.awt.Image image) {
		 
      if (image instanceof BufferedImage) {
          return (BufferedImage) image;
      }

      // This code ensures that all the pixels in the image are loaded
      image = new ImageIcon(image).getImage();

      // Determine if the image has transparent pixels
      boolean hasAlpha = hasAlpha(image);

      // Create a buffered image with a format that's compatible with the
      // screen
      BufferedImage bimage = null;
      GraphicsEnvironment ge = GraphicsEnvironment
              .getLocalGraphicsEnvironment();
      try {
          // Determine the type of transparency of the new buffered image
          int transparency = Transparency.OPAQUE;
          if (hasAlpha == true) {
              transparency = Transparency.BITMASK;
          }

          // Create the buffered image
          GraphicsDevice gs = ge.getDefaultScreenDevice();
          GraphicsConfiguration gc = gs.getDefaultConfiguration();
          bimage = gc.createCompatibleImage(image.getWidth(null), image
                  .getHeight(null), transparency);
      } catch (HeadlessException e) {
      } // No screen

      if (bimage == null) {
          // Create a buffered image using the default color model
          int type = BufferedImage.TYPE_INT_RGB;
          if (hasAlpha == true) {
              type = BufferedImage.TYPE_INT_ARGB;
          }
          bimage = new BufferedImage(image.getWidth(null), image
                  .getHeight(null), type);
      }

      // Copy image to buffered image
      Graphics g = bimage.createGraphics();

      // Paint the image onto the buffered image
      g.drawImage(image, 0, 0, null);
      g.dispose();

      return bimage;
  }

  public static boolean hasAlpha(java.awt.Image image) {
      // If buffered image, the color model is readily available
      if (image instanceof BufferedImage) {
          return ((BufferedImage) image).getColorModel().hasAlpha();
      }

      // Use a pixel grabber to retrieve the image's color model;
      // grabbing a single pixel is usually sufficient
      PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
      try {
          pg.grabPixels();
      } catch (InterruptedException e) {
      }

      // Get the image's color model
      return pg.getColorModel().hasAlpha();
  }
  
  public void openNewShell2(final Shell shell,final File file)  
  {
      
      Shell child = new Shell(shell, SWT.TITLE|SWT.SYSTEM_MODAL| SWT.CLOSE | SWT.MAX);
      img = new Image(Display.getDefault(),file.getPath());
      child.setSize(800, 600);
      child.setLayout(new FillLayout());

      canvasImage = new Canvas(child, SWT.NONE);
      canvasImage.addPaintListener(new PaintListener()
      {
    	  public void paintControl(PaintEvent e){
    		  
    		  e.gc.drawImage(img,0,0);
      
    	  }
      });
      

      child.open();
      
      Button button=new Button(child,SWT.PUSH);
      button.setText("OK");
	  imgDatabase = new HashMap<String,String>();
      
	  MessageBox messageBox = new MessageBox(shell, SWT.OK |SWT.CANCEL|
	  SWT.ICON_QUESTION);
	  messageBox.setMessage("Share is correct");
	  if (messageBox.open()==SWT.OK){}
  }
  
 public static void main(String[] args) {
 new Login();
  }
}