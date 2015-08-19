import java.util.ArrayList;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Image;


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
	
	private static int cargaBala = 50;
	
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
	
	private static int anchoMarciano = 25;
	private static int altoMarciano = 25;
	
	private static int anchoBalaMarciano = 5;
	private static int altoBalaMarciano = 5;
	
	private static float velocidadeBalaMarciano = 3;
	
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
	private ArrayList<bala> arrayBalasMarcianos= new ArrayList <bala> ();
	
	private ArrayList<cadro> arrayCadros = new ArrayList <cadro> ();
	
	private Image spriteMarciano1;
	private Image spriteMarciano2;
	
	private int numImagenMarciano = 1;
	
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
		
		//debuxamos suelo
		
		int gris = 10; 
		
		for (int i=altoVentana-altoVentana/4;i<altoVentana;i += 5){
			g.setColor(new Color(gris, gris, gris));
			g.fillRect(0,i,anchoVentana,altoVentana-i);
			gris += 3;
		}

		//debuxamos tanque (2 rectangulos: vehiculo + canón)
		
		if (pj.carga == cargaBala){
			g.setColor(new Color(200, 200, 200));
		}
		else{
			g.setColor(new Color(200, 200, (200+cargaBala)-(int)pj.carga));
		}
		
		if (gameOver) {
			g.setColor(new Color(255, 100, 100));
		}
		
		g.fillRect(pj.punto.x+pj.ancho/8,pj.punto.y,pj.ancho-pj.ancho/4,pj.alto/2);
		g.fillRect(pj.punto.x,pj.punto.y+pj.alto/2,pj.ancho,pj.alto/2);
		g.fillRect(pj.punto.x+pj.ancho/2-pj.anchoCanon/2, pj.punto.y-pj.altoCanon, pj.anchoCanon, pj.altoCanon);
		
		//debuxamos balas
		
		for (int i=0; i<arrayBalas.size(); i++) {
			Random rand = new Random ();
			g.setColor(new Color(rand.nextInt((255) + 1), rand.nextInt((255) + 1), 255));
			g.fillRect(arrayBalas.get(i).punto.x, arrayBalas.get(i).punto.y, arrayBalas.get(i).ancho, arrayBalas.get(i).alto);
		}
		
		for (int i=0; i<arrayBalasMarcianos.size(); i++) {
			Random rand = new Random ();
			g.setColor(new Color(255, rand.nextInt((255) + 1), rand.nextInt((255) + 1)));
			g.fillRect(arrayBalasMarcianos.get(i).punto.x, arrayBalasMarcianos.get(i).punto.y, arrayBalasMarcianos.get(i).ancho, arrayBalasMarcianos.get(i).alto);
		}
		
		//debuxamos defensas
		
		float color_restar = 0;
		
		for (int i=0; i<arrayCadros.size(); i++) {
			g.setColor(new Color(150-(int)color_restar, 130-(int)color_restar, 110-(int)color_restar));
			g.fillRect(arrayCadros.get(i).punto.x, arrayCadros.get(i).punto.y, arrayCadros.get(i).ancho, arrayCadros.get(i).alto);
			color_restar += 0.20;
		}
		
		//debuxamos marcianos
		
		for (int i=0; i<arrayMarcianos.size(); i++) {
			if (!arrayMarcianos.get(i).morto) {
				g.setColor(new Color(200,200,200));
				//g.fillRect(arrayMarcianos.get(i).punto.x,arrayMarcianos.get(i).punto.y,arrayMarcianos.get(i).ancho,arrayMarcianos.get(i).alto);
				if (numImagenMarciano == 1) {
					spriteMarciano1.draw(arrayMarcianos.get(i).punto.x-anchoMarciano/8,arrayMarcianos.get(i).punto.y-altoMarciano/8,arrayMarcianos.get(i).ancho+anchoMarciano/4,arrayMarcianos.get(i).alto+altoMarciano/4);
				}
				else {
					spriteMarciano2.draw(arrayMarcianos.get(i).punto.x-anchoMarciano/8,arrayMarcianos.get(i).punto.y-altoMarciano/8,arrayMarcianos.get(i).ancho+anchoMarciano/4,arrayMarcianos.get(i).alto+altoMarciano/4);
				}
				g.setColor(new Color(255,255,255));
			}
		}
		
		//debuxamos puntuacion
		
		g.setColor(new Color(255, 255, 255));
		
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
		
		spriteMarciano1 = new Image("img/sprite_marciano1.png");
		spriteMarciano2 = new Image("img/sprite_marciano2.png");
	
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
		
		//movemento balas
		
		for (int i=0; i<arrayBalas.size(); i++) {
			arrayBalas.get(i).punto.y -= arrayBalas.get(i).velocidade;
			if (arrayBalas.get(i).punto.y < -arrayBalas.get(i).alto) {
				arrayBalas.remove(i);
			}
		}
		
		for (int i=0; i<arrayBalasMarcianos.size(); i++) {
			arrayBalasMarcianos.get(i).punto.y += arrayBalasMarcianos.get(i).velocidade;
			if (arrayBalasMarcianos.get(i).punto.y > altoVentana) {
				arrayBalasMarcianos.remove(i);
			}
		}
		
		//disparo marcianos
		
		if (!gameOver) {
			for (int i=0; i<arrayMarcianos.size(); i++) {
					Random rand = new Random ();
					if (rand.nextInt((1000)) > 998 && !arrayMarcianos.get(i).morto && arrayBalasMarcianos.size() < 4) {
						arrayBalasMarcianos.add(new bala(new punto(arrayMarcianos.get(i).punto.x+anchoMarciano/2-anchoBalaMarciano/2,arrayMarcianos.get(i).punto.y+altoMarciano), anchoBalaMarciano, altoBalaMarciano, velocidadeBalaMarciano));
				}
			}
		}
		
		//movemento marcianos
		
			//movemento horizontal
		
		if (yMaior > altoVentana - altoMarciano*4) {
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
			
			if (numImagenMarciano == 1) {
				numImagenMarciano = 2;
			}
			else {
				numImagenMarciano = 1;
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
						pj.carga += cargaBala/2;
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
		
		boolean choque_bala = false;
		
		for (int u=0; u<arrayCadros.size(); u++) {
			Rectangle rectanguloCadro = new Rectangle(arrayCadros.get(u).punto.x, arrayCadros.get(u).punto.y, arrayCadros.get(u).ancho, arrayCadros.get(u).alto);
			Rectangle rectanguloBala = new Rectangle(arrayBalas.get(i).punto.x, arrayBalas.get(i).punto.y, arrayBalas.get(i).ancho, arrayBalas.get(i).alto);
			if (rectanguloBala.intersects(rectanguloCadro)) {
				arrayCadros.remove(u);
				choque_bala = true;
			}
		}
		
		if (choque_bala){
			arrayBalas.remove(i);
			pj.carga += cargaBala/2;
		}
	}
	
	for (int i=0; i<arrayBalasMarcianos.size(); i++) {
		
		boolean choque_bala = false;
		
		for (int u=0; u<arrayCadros.size(); u++) {
			Rectangle rectanguloCadro = new Rectangle(arrayCadros.get(u).punto.x, arrayCadros.get(u).punto.y, arrayCadros.get(u).ancho, arrayCadros.get(u).alto);
			Rectangle rectanguloBala = new Rectangle(arrayBalasMarcianos.get(i).punto.x, arrayBalasMarcianos.get(i).punto.y, arrayBalasMarcianos.get(i).ancho, arrayBalasMarcianos.get(i).alto);
			if (rectanguloBala.intersects(rectanguloCadro)) {
				arrayCadros.remove(u);
				choque_bala = true;
			}
		}
		
		if (choque_bala){
			arrayBalasMarcianos.remove(i);
			}
		}
	
	
	for (int i=0; i<arrayBalasMarcianos.size(); i++) {
		Rectangle rectanguloBala = new Rectangle(arrayBalasMarcianos.get(i).punto.x, arrayBalasMarcianos.get(i).punto.y, arrayBalasMarcianos.get(i).ancho, arrayBalasMarcianos.get(i).alto);
		Rectangle rectanguloPj = new Rectangle(pj.punto.x,pj.punto.y,pj.ancho,pj.alto);
		if (rectanguloBala.intersects(rectanguloPj)) {
			gameOver = true;
			arrayBalasMarcianos.remove(i);
			}
		}
	
	}
	
	
	private void StartGame() {
		crearMarcianos();
		crearDefensas();
		Posicions();
		arrayBalas.clear();
		arrayBalasMarcianos.clear();
		descansoMarcianos0 = descansoMarcianosInicio;
		direccionMarcianos = 0;
		numMarcianosVivos = numMarcianos;
		run = true;
		gameOver = false;
	}

}
