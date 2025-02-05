package plotter;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;
import javax.swing.text.*;

import backtracking.*;
import io.KenKenFile;

import java.util.*;

public class KenKenSwingPlotter implements KenKenPlotter{
	private KenKen buffer;
	private java.util.List<KenKen> solutions;
	private KenKenUserSolver kkus;
	private KenkenBtSolver kkbs;
	private static Color white=Color.WHITE;
	//Panels
	private Menu menu;
	private Griglia grid;
	private PannelloAzioni azioni;
	private JLabel message;
	//Frames
	private JFrame jf;
	private SaveFrame svf;
	private OpenFrame opf;
	//Extras
	private static Font defaultf=new Font("FiraSans", Font.BOLD, 50);
	private static java.util.Scanner keyboard = new java.util.Scanner(System.in);
	public KenKenSwingPlotter(KenKen kk) {
		buffer=kk;
		kkus=new KenKenUserSolver(buffer);
		kkbs=new KenkenBtSolver(new KenKenImp((KenKenImp)buffer));
	}
	public void plot() {
		SwingUtilities.invokeLater(() -> {
			jf=new JFrame();
			jf.setSize(700, 500);
			jf.setTitle("KenKen");
			
			menu=this.new Menu();
			jf.setJMenuBar(menu);
			
			grid=new Griglia();
			azioni=new PannelloAzioni();
			message=new JLabel("");
			Font messagef=new Font("FiraSans", Font.BOLD, 20);
			message.setBorder(new EmptyBorder(10, 50, 50, 0));
			message.setFont(messagef);
			jf.add(grid,BorderLayout.CENTER);
			jf.add(azioni,BorderLayout.EAST);
			jf.add(message,BorderLayout.SOUTH);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			svf=new SaveFrame();
			opf=new OpenFrame();
			jf.setVisible(true);
			});
	}
	private void actionMessage() {
		Timer timer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				message.setText("");
				jf.repaint();
			}
		});
		timer.setRepeats(false); 
		timer.start();
	}
	
	private class SaveFrame extends JFrame{
		private static final long serialVersionUID = 774469289797054L;
		KenKenFile kkf=KenKenFile.getKKF();
		JTextField nameFile;
		JButton salva;
		JLabel nome;
		Font savef=new Font("FiraSans", Font.PLAIN, 20);
		public SaveFrame() {
			this.setFont(savef);
			this.setSize(400, 100);
			this.setTitle("Salva");
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.nameFile=new JTextField();
			this.add(nameFile,BorderLayout.CENTER);
			this.salva=new JButton("Salva");
			this.salva.setBackground(white);
			this.salva.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String fileName=nameFile.getText();
					if(fileName.length()!=0) { kkf.save("file//"+fileName, buffer);
					dispose();}
				}
			});
			this.add(salva,BorderLayout.SOUTH);
			this.nome=new JLabel("Nome file");
			this.add(nome,BorderLayout.WEST);
			this.setVisible(false);
			//jf.add(this);
		}
		public void reset() {
			this.nameFile.setText("");
		}
	}
	private class OpenFrame extends JFrame{
		private static final long serialVersionUID = -5289040948602027185L;
		KenKenFile kkf=KenKenFile.getKKF();
		JTextField nameFile;
		JButton open;
		JLabel nome;
		public OpenFrame() {
			this.setSize(400, 100);
			this.setTitle("Apri");
			this.nameFile=new JTextField();
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.add(nameFile,BorderLayout.CENTER);
			this.open=new JButton("Apri");
			this.open.setBackground(white);
			this.open.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String fileName=nameFile.getText();
					if(fileName.length()!=0) {
						try { 
							KenKen kk=kkf.load("file//"+fileName);
							if(kk!=null) {
								kk.reset();
								buffer=kk;
								jf.remove(grid);
								grid=new Griglia();
								kkus=new KenKenUserSolver(buffer);
								kkbs=new KenkenBtSolver(new KenKenImp((KenKenImp)buffer));
								azioni.hideArrows();
								jf.add(grid,BorderLayout.CENTER);
								grid.refresh();
								//System.out.println(buffer);
							}
						}
						catch(Exception ex) {
							message.setText("File non trovato");
							actionMessage();
						}
					dispose();
					}
				}
			});
			this.add(open,BorderLayout.SOUTH);
			this.nome=new JLabel("Nome file");
			this.add(nome,BorderLayout.WEST);
			this.setVisible(false);
			//jf.add(this);
		}
		public void reset() {
			this.nameFile.setText("");
		}
	}
	private class Menu extends JMenuBar{
		private static final long serialVersionUID = 8840208119809227220L;

		public Menu() {
			this.setSize(new Dimension(700,200));
			JMenu file=new JMenu("File");
			JMenuItem nuovo=new JMenuItem("Nuova partita");
			nuovo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buffer.reset();
					grid.refresh();
				}
			});
			file.add(nuovo);
			file.addSeparator();
			JMenuItem apri=new JMenuItem("Apri");
			apri.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					opf.setVisible(true);
					opf.reset();
				}
			});
			file.add(apri);
			JMenuItem salva=new JMenuItem("Salva");
			salva.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					svf.setVisible(true);
					svf.reset();
				}
			});
			file.add(salva);
			file.addSeparator();
			JMenuItem esci=new JMenuItem("Esci");
			esci.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					jf.dispose();
					System.exit(0);
				}
			});
			file.add(esci);
			this.add(file);
		}
	}
	private class Griglia extends JLayeredPane{
		private static final long serialVersionUID = 2649455664088031043L;
		ArrayList<CagePanel> cagesp;
		
		public Griglia() {
			this.setLayout(new GridLayout(buffer.size(),buffer.size()));
			this.setBorder(new EmptyBorder(20, 20, 20, 20));
			this.cagesp=new ArrayList<>();
			createGrid();
		}
		public void createGrid() {
			for(int i=0;i<buffer.size();i++) {
				for(int j=0;j<buffer.size();j++) {
					Cage cg=Checker.trovaCage(i, j, buffer.getCages());
					CagePanel cp=new CagePanel(cg,i,j);
					this.cagesp.add(cp);
					this.add(cp);
				}
			}
			//this.setBorder(new EmptyBorder(0, 0, 20, 0));
		}
		public void refresh() {
			
	    	//System.out.println("refreshing");
			for(CagePanel cpl:cagesp) {
				cpl.refreshText();
				cpl.revalidate();
				cpl.repaint();
			}
			this.revalidate();
		    this.repaint();
		    jf.revalidate();
		    jf.repaint();
		}
		public void setNotEditable() {
			for(CagePanel cpl:cagesp) {
				cpl.setNotEditable();
			}
		}
	}
	private class PannelloAzioni extends JPanel{
		private static final long serialVersionUID = 443137784485382233L;
		private static ArrayList<JButton> buttons=new ArrayList<>();
		private JPanel arrows;
		private int currSol;
		public PannelloAzioni() {
			this.setBorder(new EmptyBorder(50, 0, 50, 20));
			this.setLayout(new GridLayout(4,1));
			this.add(Box.createRigidArea(new Dimension(0, 10)));
			this.currSol=0;
			arrowPanel();
			JButton verifica=new JButton("Verifica");
			JButton soluzione=new JButton("Soluzione");
			verifica.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					//System.out.println(kkus.verifica());
					if(kkus.verifica())
						message.setText("OK");
					else
						message.setText("NO");
					actionMessage();
					message.revalidate();
					message.repaint();
				}
			});
			soluzione.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					kkbs.risolvi();
					solutions=kkbs.getSolution();
					if(solutions.size()>1) {showArrows();buffer=solutions.get(0);}
					else if(solutions.size()==0) message.setText("Non ci sono soluzioni");
					else {
						buffer=solutions.get(0);
					}
					grid.setNotEditable();
					grid.refresh();
					
				}
			});
			buttons.add(verifica);
			buttons.add(soluzione);
			for(JButton bt:buttons) {
				bt.setBackground(white);
				bt.setPreferredSize(new Dimension(100,100));
				this.add(bt);
			}
			this.add(arrows);
		}
		private void arrowPanel() {
			this.arrows=new JPanel();
			JButton rewind=new JButton("<<");
			JButton forward=new JButton(">>");
			rewind.setBackground(white);
			rewind.setPreferredSize(new Dimension(50,50));
			rewind.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(currSol>0) {
						currSol--;
						buffer=solutions.get(currSol);
						//System.out.println("Soluzione numero:"+currSol);
						System.out.println(buffer);
						grid.refresh();}
				}
			});
			this.arrows.add(rewind);
			forward.setBackground(white);
			forward.setPreferredSize(new Dimension(50,50));
			forward.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(currSol<solutions.size()-1) {
						currSol++;
						buffer=solutions.get(currSol);
						//System.out.println("Soluzione numero:"+currSol);
						//System.out.println(buffer);
						grid.refresh();}
				}
			});
			this.arrows.add(forward);
			hideArrows();
		}
		private void showArrows() {
			this.arrows.setVisible(true);
		}
		private void hideArrows() {
			this.arrows.setVisible(false);
		}
	}
	private class CagePanel extends JPanel {
	    private static final long serialVersionUID = -9026115318513994023L;
		private Cage cage;
	    private int i;
	    private int j;
	    private boolean editable;
	    private JTextField text;
	    private CagePanel(Cage cage) {
	        this.cage = cage;
	        this.setPreferredSize(new Dimension(30, 30));
	        this.setBorder(new LineBorder(Color.BLACK, 2));
	        this.setBackground(white);
	        Font cagef=new Font("FiraSans", Font.BOLD, 20);
	        this.setFont(cagef);
	        this.editable=true;
	    }
	    public CagePanel(Cage cg,int i,int j) {
	    	this(cg);
			createText(i,j);
	    }
	    private void createText(int i,int j) {
	    	if(buffer.getValue(i, j)==0)
				 text = new JTextField("");
			else
				text=new JTextField(buffer.getValue(i, j)+"");
			//Text
			text.setFont(defaultf);
			text.setHorizontalAlignment(SwingConstants.CENTER);
			text.setPreferredSize(new Dimension(50,50));
			
			AbstractDocument ad=(AbstractDocument)text.getDocument();
			ad.setDocumentFilter(new NumberFilter(i,j));
			if(!editable) text.setEditable(false);
	    	this.i=i;
	    	this.j=j;
	    	this.add(text);
	    }
	    @Override
	    protected void paintComponent(Graphics g) {
	       super.paintComponent(g);
	       g.drawString(String.valueOf(cage.getResult()+""+cage.getOp()), 20, 20);
	    }
	    public void setNotEditable() {
			this.editable=false;
			this.text.setEditable(false);
		}
	    private void setTextField(int i,int j) {
	    	this.remove(text);
	    	createText(i,j);
	    }
	    public String getTextField() {
	    	return this.getTextField();
	    }
	    public void refreshText() {
	    	//System.out.println(buffer.getValue(i, j)+"");
	    	setTextField(i,j);
	    }
	}
	private class NumberFilter extends DocumentFilter {
		private int i;
		private int j;
		public NumberFilter(int i,int j) {
			this.i=i;
			this.j=j;
		}
	    @Override
	    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	        int n=buffer.size();
	    	if (text.matches("[1-"+n+"]*") && fb.getDocument().getLength() + text.length() <= 1) { 
	    		//System.out.println(text);
	    		kkus.insert(i, j, Integer.parseInt(text));
	    		checkComplete();
	            fb.replace(offset, length, text, attrs);
	        }
	    }
	    private void checkComplete() {
	    	if(buffer.getCnt()==buffer.size()*buffer.size() && kkus.verifica()) {
	    		grid.setNotEditable();
	    		message.setText("Risolto!");}
    		//System.out.println("CNT:"+buffer.getCnt()+" Size:"+(buffer.size()*buffer.size())); 
	    }
	    @Override
	    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
	    	int n=buffer.size();
	    	if (text.matches("[1-"+n+"]*") && fb.getDocument().getLength() + text.length() <= 1) { 
	    		//System.out.println(text);
	    		kkus.insert(i, j, Integer.parseInt(text));
	    		checkComplete();
	            fb.insertString(offset, text, attrs);
	        }
	    }
	    @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
            kkus.remove(i, j);
            //System.out.println(0);
        }
	}
}