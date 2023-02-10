package skijumping;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class SkiJumping{
	/*static Connection c = null;
	static Map<Integer, Integer> stan = new HashMap<>();

	public SkiJumping() {
		super("Konkurs skoków narciarskich w ramach Pucharu Świata");
		setLayout(new FlowLayout());
        
        JLayeredPane layers = new JLayeredPane();
        layers.setLayout(new FlowLayout());
        
        JPanel layerOne = new JPanel();
        layerOne.add(new JLabel("Zaloguj jako:"));
        layers.add(layerOne, Integer.valueOf(2));
        
        JPanel layerTwo = new JPanel();
        JButton button1 = new JButton("Organizator");
        layerTwo.add(button1);
        JButton button2 = new JButton("Kibic");
        layerTwo.add(button2);
        layers.add(layerTwo, Integer.valueOf(1));
        
        button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean ok = loginPanel();
				if (ok) organizer();
				else buttonActionPerformed(evt);
			}
		});
        
        button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
        
        add(layers);
        
        setSize(300, 100);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	private String[] frameGetText(int n, String nazwa) {
		return new String[]{"aaa"};
	}
	
	String passwd = "aaa";
	private boolean loginPanel() {
     	String[] s = frameGetText(1, "Zaloguj");
     	return s[0].equals(passwd);
    }
	
	public static void dodajReprezentację() {
        FrameAddRep frameAddRep = new FrameAddRep(c);
        frameAddRep.dodajReprezentację();
	}
	
	public static void dodajZawodnika() {
        FrameAddZaw frameAddZaw = new FrameAddZaw(c);
        frameAddZaw.dodajZawodnika();
	}
	
	public static void organizer() {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JFrame frame = new JFrame("Organizator");
            	frame.setLayout(new FlowLayout());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                JPanel panel = new JPanel();
            	JButton button1 = new JButton("Dodaj reprezentację");
            	frame.add(button1);
            	button1.addActionListener(new ActionListener() {
            		@Override
        			public void actionPerformed(ActionEvent e) {
        				dodajReprezentację();
        			}
            	});
            	
            	JButton button2 = new JButton("Dodaj zawodnika");
            	frame.add(button2);
            	button2.addActionListener(new ActionListener() {
            		@Override
        			public void actionPerformed(ActionEvent e) {
        				dodajZawodnika();
        			}
            	});

            	JButton button3 = new JButton("Wybierz konkurs");
            	frame.add(button3);
            	button3.addActionListener(new ActionListener() {
            		@Override
        			public void actionPerformed(ActionEvent e) {
                        ChooseKonFrame chooseKonFrame = new ChooseKonFrame(c);
                        chooseKonFrame.runFrame();
            		}
            	});
            	frame.setSize(300, 200);
            	frame.setVisible(true);
        		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		frame.setLocationRelativeTo(null);
            }
        });
	}*/
	
	public static JTextField addTextField(JFrame frame, JPanel panel, String s) {
        /*
		panel.add(new JLabel(s), BorderLayout.EAST);
        panel.setOpaque(true);
        JTextArea textArea = new JTextArea(15, 50);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(Font.getFont(Font.SANS_SERIF));
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());
        JTextField input = new JTextField(20);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        inputpanel.add(input);
        panel.add(inputpanel);
        input.requestFocus();
		return input;
         */
		return (JTextField) addField(frame, panel, s, new JTextField(20));
	}

	public static JComponent addField(JFrame frame, JPanel panel, String s, JComponent c) {
		panel.add(new JLabel(s), BorderLayout.EAST);
		//panel.setOpaque(true);
		JTextArea textArea = new JTextArea(15, 50);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setFont(Font.getFont(Font.SANS_SERIF));
		JPanel inputpanel = new JPanel();
		inputpanel.setLayout(new FlowLayout());
		JComponent input = c;
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		inputpanel.add(input);
		panel.add(inputpanel);
		input.requestFocus();
		return input;
	}


	/*private void buttonActionPerformed(ActionEvent evt) {
		JOptionPane.showMessageDialog(SkiJumping.this, "Niepoprawne hasło!");		
	}
	
	public static void main(String[] args) throws SQLException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
        	//Class.forName("com.postgresql.jdbc.Driver");
			c = DriverManager
			   .getConnection("jdbc:postgresql://localhost/skijumping",
			   "postgres", "iks");
			File f = new File("/src/tabele.sql");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SkiJumping().setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}*/
}
