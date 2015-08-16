import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Font;


public class main extends BasicGame {
	
	private boolean run = false;
	
	private boolean movementoVertical = false;
	private boolean movementoHorizontal = false;

	private static int altoVentana = 550;
	private static int anchoVentana = 600;
	
	private static float anchoPj = 40;
	private static float altoPj = 20;
	
	private static float anchoCanon = 5;
	private static float altoCanon = 10;
	
	private static float velocidadePj = 2.5f;
	
	private static int cargaBala = 40;
	
	private static int anchoCadro = 10;
	private static int altoCadro = 10;
	
	private static float velocidadeBala = 8;
	
	private static int numMarcianosFila = 10;
	private static int numFilasMarcianos = 5;
	private static int numMarcianos = numMarcianosFila * numFilasMarcianos;
	
	private int numMarcianosVivos = numMarcianos;
	
	private float descansoMarcianosInicio = numMarcianos;
	private float descansoMarcianos0 = descansoMarcianosInicio;
	private float descansoMarcianos = descansoMarcianos0;
	
	private static float anchoMarciano = 25;
	private static float altoMarciano = 25;
	
	private static float velocidadeMarciano = 5;
	
	private static int direccionMarcianos = 0;
	
	private float xMenor = anchoVentana;
	private float xMaior = 0;
	private float yMaior = 0;
	
	private int puntuacion = 0;
	private boolean gameOver = false;
	
	
	private punto punto_pj = new punto(anchoVentana/2-anchoPj/2,altoVentana-altoPj);
	
	private pj pj = new pj(punto_pj,anchoPj,altoPj,anchoCanon,altoCanon,velocidadePj,cargaBala);
	
	private ArrayList<marciano> arrayMarcianos= new ArrayList <marciano> ();
	
	private ArrayList<bala> arrayBalas= new ArrayList <bala> ();
	
	private ArrayList<cadro> arrayCadros = new ArrayList <cadro> ();
	
	
	//FUNCIÓNS
	
	private void crearMarcianos() {
		arrayMarcianos.clear();
		for (int u=0; u<numFilasMarcianos; u++){
			for (int i=0; i<numMarcianosFila; i++){
				arrayMarcianos.add(new marciano(new punto(anchoVentana/(numMarcianosFila+2)*i+(anchoVentana-(anchoVentana/(numMarcianosFila+2)*numMarcianosFila))/2,u*(anchoMarciano*2)+anchoMarciano*3),anchoMarciano, altoMarciano, false));
			}
		}
	}
	
	private void crearDefensas() {
		arrayCadros.clear();
		int tamanhoBunquer = anchoVentana/8;
		
		arrayCadros.add(new cadro(new punto(anchoVentana/16-anchoCadro/4,(altoVentana-altoVentana/8)), anchoCadro, altoCadro));
		arrayCadros.add(new cadro(new punto(anchoVentana/16-anchoCadro/4+tamanhoBunquer-anchoCadro/2,(altoVentana-altoVentana/8)), anchoCadro, altoCadro));
		arrayCadros.add(new cadro(new punto(anchoVentana/16-anchoCadro/4+tamanhoBunquer*2,(altoVentana-altoVentana/8)), anchoCadro, altoCadro));
		arrayCadros.add(new cadro(new punto(anchoVentana/16-anchoCadro/4+tamanhoBunquer*3-anchoCadro/2,(altoVentana-altoVentana/8)), anchoCadro, altoCadro));
		arrayCadros.add(new cadro(new punto(anchoVentana/16-anchoCadro/4+tamanhoBunquer*4,(altoVentana-altoVentana/8)), anchoCadro, altoCadro));
		arrayCadros.add(new cadro(new punto(anchoVentana/16-anchoCadro/4+tamanhoBunquer*5-anchoCadro/2,(altoVentana-altoVentana/8)), anchoCadro, altoCadro));
		arrayCadros.add(new cadro(new punto(anchoVentana/16-anchoCadro/4+tamanhoBunquer*6,(altoVentana-altoVentana/8)), anchoCadro, altoCadro));
		arrayCadros.add(new cadro(new punto(anchoVentana/16-anchoCadro/4+tamanhoBunquer*7-anchoCadro/2,(altoVentana-altoVentana/8)), anchoCadro, altoCadro));
		
		for (int u=1;u<5;u++) {
			
			for (int i=anchoVentana/16-anchoCadro/4; i<tamanhoBunquer+anchoVentana/16; i += anchoCadro) {
				
				arrayCadros.add(new cadro(new punto(i,(altoVentana-altoVentana/8)-altoCadro*u), anchoCadro, altoCadro));
				arrayCadros.add(new cadro(new punto(i+anchoVentana/4,(altoVentana-altoVentana/8)-altoCadro*u), anchoCadro, altoCadro));
				arrayCadros.add(new cadro(new punto(i+(anchoVentana/4)*2,(altoVentana-altoVentana/8)-altoCadro*u), anchoCadro, altoCadro));
				arrayCadros.add(new cadro(new punto(i+(anchoVentana/4)*3,(altoVentana-altoVentana/8)-altoCadro*u), anchoCadro, altoCadro));
				
			}
		}
	}
	
	private void Posicions() {
		xMenor = anchoVentana;
		xMaior = 0;
		yMaior = 0;
		for (int i=0; i<arrayMarcianos.size(); i++) {
			if (!arrayMarcianos.get(i).morto && arrayMarcianos.get(i).punto.x < xMenor) {
				xMenor = arrayMarcianos.get(i).punto.x;
			}
			if (!arrayMarcianos.get(i).morto && arrayMarcianos.get(i).punto.x > xMaior) {
				xMaior = arrayMarcianos.get(i).punto.x;
			}
			if (!arrayMarcianos.get(i).morto && arrayMarcianos.get(i).punto.y > yMaior) {
				yMaior = arrayMarcianos.get(i).punto.y;
			}
		}
	}
	
	//////
	
	public main(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new main("Space Invaders"));
		app.setDisplayMode(anchoVentana, altoVentana, false);
		app.setVSync(true);
		app.setShowFPS(false);
		app.start();
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		//ponemos color por defecto blanco
		
		g.setColor(new Color(255, 255, 255));
		
		//debuxamos tanque (2 rectangulos: vehiculo + canón)
		
		g.fillRect(pj.punto.x,pj.punto.y,pj.ancho,pj.alto);
		g.fillRect(pj.punto.x+pj.ancho/2-pj.anchoCanon/2, pj.punto.y-pj.altoCanon, pj.anchoCanon, pj.altoCanon);
		
		//debuxamos balas
		
		for (int i=0; i<arrayBalas.size(); i++) {
			g.fillRect(arrayBalas.get(i).punto.x, arrayBalas.get(i).punto.y, arrayBalas.get(i).ancho, arrayBalas.get(i).alto);
		}
		
		//debuxamos defensas
		
		g.setColor(new Color(120, 100, 80));
		
		for (int i=0; i<arrayCadros.size(); i++) {
			g.fillRect(arrayCadros.get(i).punto.x, arrayCadros.get(i).punto.y, arrayCadros.get(i).ancho, arrayCadros.get(i).alto);
		}
		
		g.setColor(new Color(255, 255, 255));
		
		//debuxamos marcianos
		
		for (int i=0; i<arrayMarcianos.size(); i++) {
			if (!arrayMarcianos.get(i).morto) {
				g.setColor(new Color(20,150,20));
				g.fillRect(arrayMarcianos.get(i).punto.x,arrayMarcianos.get(i).punto.y,arrayMarcianos.get(i).ancho,arrayMarcianos.get(i).alto);
				g.setColor(new Color(255,255,255));
			}
		}
		
		//debuxamos puntuacion
		
		g.drawString("score: "+puntuacion, 5, 0);
		if (numMarcianosVivos <= 0) {
			g.setColor(new Color(0,255,0));
			g.drawString("VICTORIA", anchoVentana-80, 0);
		}
		else if (gameOver) {
			g.setColor(new Color(255,0,0));
			g.drawString("DERROTA", anchoVentana-80, 0);
		}
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
			
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		if (!run) {
			StartGame();
		}
		
		Input input = gc.getInput();
		
		//reiniciar
		
		if (input.isKeyDown(Input.KEY_RETURN)) {
			run = false;
		}
		
		
		//disparo
		
		if (pj.carga > cargaBala) {
			pj.carga = cargaBala;
		}
		
		if (input.isKeyDown(Input.KEY_SPACE) && pj.carga == cargaBala && !gameOver) {
			arrayBalas.add(new bala(new punto(pj.punto.x+pj.ancho/2-pj.anchoCanon/2,pj.punto.y-pj.altoCanon), pj.anchoCanon, pj.altoCanon, velocidadeBala));
			pj.carga = 0;
			
		}
		
		if (pj.carga < cargaBala) {
			pj.carga += 1;
		}
		
		
		//movemento pj
		
		if (!gameOver) {
			
			if (input.isKeyDown(Input.KEY_LEFT) || (input.isKeyDown(Input.KEY_A))) {
				pj.punto.x -= pj.velocidade;
			}
			
			if (input.isKeyDown(Input.KEY_RIGHT) || (input.isKeyDown(Input.KEY_D))) {
				pj.punto.x += pj.velocidade;
			}
			
			if (pj.punto.x < 0) {
				pj.punto.x = 0;
			}
			
			if (pj.punto.x > anchoVentana-pj.ancho) {
				pj.punto.x = anchoVentana-pj.ancho;
			}
		}
		
		//movemento bala
		
		for (int i=0; i<arrayBalas.size(); i++) {
			arrayBalas.get(i).punto.y -= arrayBalas.get(i).velocidade;
			if (arrayBalas.get(i).punto.y < -arrayBalas.get(i).alto) {
				arrayBalas.remove(i);
			}
		}
		
		//movemento marcianos
		
			//movemento horizontal
		
		if (yMaior > altoVentana - altoMarciano*2) {
			gameOver = true;
		}
		
		if (descansoMarcianos == 0) {
			movementoHorizontal = true;
		}
		else {
			movementoHorizontal = false;
		}
		
		if (!movementoVertical && movementoHorizontal && !gameOver) {
			
			for (int i=0; i<arrayMarcianos.size(); i++) {
					
				if (direccionMarcianos == 0) {
					arrayMarcianos.get(i).punto.x -= velocidadeMarciano;
				}
				if (direccionMarcianos == 1) {
					arrayMarcianos.get(i).punto.x += velocidadeMarciano;
				}
					
				if (arrayMarcianos.get(i).punto.x < 0) {
					arrayMarcianos.get(i).punto.x = 0;
				}
					
				if (arrayMarcianos.get(i).punto.x > anchoVentana-anchoMarciano) {
					arrayMarcianos.get(i).punto.x = anchoVentana-anchoMarciano;
				}
					
			}
		}
		
		if (descansoMarcianos > 0) {
			descansoMarcianos -= 1;
			if (descansoMarcianos <= 0) {
				descansoMarcianos = 0;
			}
		}
		else {
			descansoMarcianos = descansoMarcianos0;
		}
		
			//movemento vertical
		
		Posicions();
		
		if (descansoMarcianos == 0) {
			
			if (xMenor == 0 && !movementoVertical && direccionMarcianos == 0) {
				movementoVertical = true;
			}
			
			if (xMaior == anchoVentana-anchoMarciano && !movementoVertical && direccionMarcianos == 1) {
				movementoVertical = true;
			}
		}
		
		if (movementoVertical && descansoMarcianos == 0 && !gameOver) {
			for (int i=0; i<arrayMarcianos.size(); i++) {
				arrayMarcianos.get(i).punto.y += altoMarciano;
				if (xMenor == 0) {
					direccionMarcianos = 1;
				}
				else {
					direccionMarcianos = 0;
				}
			movementoVertical = false;
			descansoMarcianos = descansoMarcianos0;
			}
			
		}
		
		//colisions
		
		for (int i=0; i<arrayBalas.size(); i++) {
			
			for (int u=0; u<arrayMarcianos.size(); u++) {
				if (!arrayMarcianos.get(u).morto) {
					Rectangle rectanguloMarciano = new Rectangle(arrayMarcianos.get(u).punto.x, arrayMarcianos.get(u).punto.y, arrayMarcianos.get(u).ancho, arrayMarcianos.get(u).alto);
					Rectangle rectanguloBala = new Rectangle(arrayBalas.get(i).punto.x, arrayBalas.get(i).punto.y, arrayBalas.get(i).ancho, arrayBalas.get(i).alto);
					if (rectanguloBala.intersects(rectanguloMarciano)) {
						arrayMarcianos.get(u).morto = true;
						arrayBalas.remove(i);
						pj.carga += cargaBala/4;
						numMarcianosVivos -= 1;
						puntuacion += 100;
						descansoMarcianos0 = descansoMarcianosInicio - (numMarcianos-numMarcianosVivos);
						if (numMarcianosVivos == 1) {
							descansoMarcianos0 = 0;
						}
						if (descansoMarcianos0 < 0) {
							descansoMarcianos0 = 0;
						}
						break;
					}
				}
			}
			
		}
		
	for (int i=0; i<arrayBalas.size(); i++) {
		
		for (int u=0; u<arrayCadros.size(); u++) {
			Rectangle rectanguloCadro = new Rectangle(arrayCadros.get(u).punto.x, arrayCadros.get(u).punto.y, arrayCadros.get(u).ancho, arrayCadros.get(u).alto);
			Rectangle rectanguloBala = new Rectangle(arrayBalas.get(i).punto.x, arrayBalas.get(i).punto.y, arrayBalas.get(i).ancho, arrayBalas.get(i).alto);
			if (rectanguloBala.intersects(rectanguloCadro)) {
				arrayCadros.remove(u);
				arrayBalas.remove(i);
				pj.carga += cargaBala/4;
				break;
			}
		}
	}
	
	}
	
	
	private void StartGame() {
		crearMarcianos();
		crearDefensas();
		Posicions();
		arrayBalas.clear();
		descansoMarcianos0 = descansoMarcianosInicio;
		direccionMarcianos = 0;
		numMarcianosVivos = numMarcianos;
		run = true;
		gameOver = false;
	}

}
