import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PaintFrame extends JFrame implements MouseMotionListener {

	//INICJALIZACJA----------------------------------------------
	
	//Panele
	JPanel upperPanel;
	JPanel mainPanel;
	
	//Menu
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem itemSave;
	JMenuItem itemRead;
	JMenuItem itemErase;
	
	//Podpisy
	JLabel label1;
	JLabel label2;
	
	//Guziki
	JButton colorButton;
	
	//Combo
	JComboBox<String> brushBox; 
	
	//Suwak
	JSlider widthSlider;
	static final int SLIDER_MIN = 1;
	static final int SLIDER_MAX = 48;
	static final int SLIDER_INIT = 4;
	
	
	//Zmienne
	Color lineColor = Color.black;
	int lineWidth;
	BufferedImage image;

	//Stale
	private int x = 0;
	private int y = 0;
	
	
	//KONSTRUKTOR--------------------------------------------------
	public PaintFrame() {
		
		super("Paint Olgi");
		this.setSize(800,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		//Komponenty----------------------------------
		
		//menu
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		itemSave = new JMenuItem("Zapisz");
		itemRead = new JMenuItem("Wczytaj");
		itemErase = new JMenuItem("Wyczysc");
		menu.add(itemSave);
        menu.add(itemRead);
        menu.add(itemErase);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        this.setLayout(new BorderLayout());
        
        //panel narzedzi
        label1 = new JLabel("Opcje rysowania");
		colorButton = new JButton("Kolor");
		
		brushBox = new JComboBox<String>();
		brushBox.addItem("Olowek");
		brushBox.addItem("Linia");
		
		
		label2 = new JLabel("Grubosc linii");
		widthSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
		widthSlider.setPreferredSize(new Dimension(400,50));//rozmiar suwaka
		widthSlider.setMajorTickSpacing(11); //gestosc przedzialow glownych
		widthSlider.setMinorTickSpacing(1); //gestosc podzialek
        widthSlider.setPaintTicks(true);//podzialki na pasku
        widthSlider.setPaintLabels(true); //przedzialy glowne
        
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Color newColor = JColorChooser.showDialog
                (null, "Wybierz kolor", Color.black);
                lineColor = newColor;
            }
		});
        
        
        //Menu - oprogramowanie ale nie dziala
        /*
       
        public void actionPerformed(ActionEvent e) {
        	JFileChooser chooser = new JFilerChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        	FileNameExtensionFilter extension = new FileNameExtensionFilter("PNG images", "png");
        	chooser.setFileFilter(extension);
        	
        	//opcje
        	if (e.getActionCommand() == "Wczytaj") {
        		//Tworzenie obiektu BufferedImage
        		BufferedImage image = null;
        							
        		//Tworzenie pliku wejsciowego 
        		File inputFile = new File("old-pic.png");
        							
        		//Odczytywanie pliku wejsciowego 
        		try {
        			image = ImageIO.read(inputFile);
        		} catch(IOException ex) {
        			System.out.println(ex.getMessage());
        		}
        		
        		//paintPanel.setBackgroudImage(image);
        	}
        	else if (e.getActionCommand() == "Zapisz") {
        		BufferedImage image = new BufferedImage(paintPanel.getWidth(), paintPanel.getHeight(),BufferedImage.TYPE_INT_ARGB);
        		Graphics2D g2d = image.createGraphics();
        		paintPanel.paintAll(g2d);
        									
        		//Tworzenie pliku zapisu w folderze projektu
        		File outputfile = new File("new-pic.png");
        									
        		//Zapis do pliku
        		try {
        			ImageIO.write(image, "png", outputfile);
        		} catch (IOException e) {
        			System.out.println(e.getMessage());
        		}
        	}
        	else if (e.getActionCommand() == "Wyczysc") {
        		this.clear();
        	}
        }
		*/
        

		//Layout-------------------------------------
		upperPanel = new JPanel();
		mainPanel = new JPanel();
		
		upperPanel.setLayout(new FlowLayout());

		upperPanel.add(label1);
		upperPanel.add(colorButton);
		upperPanel.add(label2);
		upperPanel.add(widthSlider);
		upperPanel.add(brushBox);
		mainPanel.addMouseMotionListener(this);
		
		//Dodaje panele
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
	}
	


	
	//MAIN----------------------------------------------------
	public static void main(String[] args) {
		//Tworze RAMKE
		PaintFrame frame = new PaintFrame();
		frame.setVisible(true);

	}
	

	
	//METODY---------------------------------------------------
	
	//rysowanie
	public void paint(Graphics g) {
		
		g.setColor(lineColor);
		g.fillOval(x, y, widthSlider.getValue(), widthSlider.getValue());	
	}
	
	//sluchanie slidera
	public class SliderChangeListener implements ChangeListener{
		@Override
			public void stateChanged(ChangeEvent arg0) {
			lineWidth = widthSlider.getValue();
		}

	}

	//czyszczenie
	public void clear() {
		// TODO Auto-generated method stub
	    repaint();
	}


	//OPERACJE MYSZY--------------------------------------------
	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		paint(getGraphics());
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
