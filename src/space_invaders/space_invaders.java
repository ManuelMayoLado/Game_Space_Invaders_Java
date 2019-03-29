package space_invaders;
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


public class space_invaders extends BasicGame {
	
	private boolean run = false;
	
	private boolean movVertical = false;
	private boolean movHorizontal = false;

	private static int altoV = 550;
	private static int anchoV = 600;
	
	private static float anchoPj = 40;
	private static float altoPj = 20;
	
	private static float anchoCanon = 5;
	private static float altoCanon = 10;
	
	private static float velocidadePj = 2.5f;
	
	private static int cargaBala = 50;
	
	private static int anchoCadro = 5;
	private static int altoCadro = 5;
	
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
	
	private float xMenor = anchoV;
	private float xMaior = 0;
	private float yMaior = 0;
	
	private int puntuacion = 0;
	private boolean gameOver = false;
	
	private punto punto_pj = new punto(anchoV/2-anchoPj/2,altoV-altoPj);
	
	private pj pj = new pj(punto_pj,anchoPj,altoPj,anchoCanon,altoCanon,velocidadePj,cargaBala);
	
	private ArrayList<marciano> arrayMarcianos= new ArrayList <marciano> ();
	
	private ArrayList<bala> arrayBalas= new ArrayList <bala> ();
	private ArrayList<bala> arrayBalasMarcianos= new ArrayList <bala> ();
	
	private ArrayList<cadro> arrayCadros = new ArrayList <cadro> ();
	
	private Image spriteMarciano1;
	private Image spriteMarciano2;
	
	private int numImagenMarciano = 1;
	
	//FUNCI�NS
	
	private void crearMarcianos() {
		arrayMarcianos.clear();
		for (int u=0; u<numFilasMarcianos; u++){
			boolean abaixo;
			if (u==numFilasMarcianos-1) {
				abaixo = true;
			} else {
				abaixo = false;
			}
			for (int i=0; i<numMarcianosFila; i++){
				arrayMarcianos.add(new marciano(
					new punto(
						anchoV/(numMarcianosFila+2)*i+(anchoV-(anchoV/(numMarcianosFila+2)*numMarcianosFila))/2,
						u*(anchoMarciano*2)+anchoMarciano*3),
					anchoMarciano, 
					altoMarciano,
					abaixo,
					false));
			}
		}
	}
	
	private void crearDefensas() {
		arrayCadros.clear();
		int tamanhoBunquer = anchoV/8;
		
		for (int y=0;y<4;y++) {
			
			for (int i=0;i<=7;i++) {
				
				float pos_x;
				float ancho_columna;
				
				if (y==3) {
					ancho_columna = 6;
				} else {
					ancho_columna = 4;
				}
				
				if (i%2==0) {
					pos_x = anchoV/16-anchoCadro/4+tamanhoBunquer*i;
				} else {
					pos_x = anchoV/16-anchoCadro/4+tamanhoBunquer*i-(anchoCadro*(ancho_columna-1));
				}
				
				for (int x=0;x<ancho_columna;x++) {
					
					arrayCadros.add(new cadro(new punto(
							pos_x+anchoCadro*x,
							(altoV-altoV/8)-y*altoCadro), 
							anchoCadro, 
							altoCadro));
					
				}
			
			}
	
		}
		
		for (int u=4;u<8;u++) {
			
			for (int i=anchoV/16-anchoCadro/4; i<tamanhoBunquer+anchoV/16; i += anchoCadro) {
				
				arrayCadros.add(new cadro(
									new punto(i,(altoV-altoV/8)-altoCadro*u), 
									anchoCadro, 
									altoCadro));
				arrayCadros.add(new cadro(
									new punto(i+anchoV/4,(altoV-altoV/8)-altoCadro*u), 
									anchoCadro, 
									altoCadro));
				arrayCadros.add(new cadro(
									new punto(i+(anchoV/4)*2,(altoV-altoV/8)-altoCadro*u), 
									anchoCadro, 
									altoCadro));
				arrayCadros.add(new cadro(
									new punto(i+(anchoV/4)*3,(altoV-altoV/8)-altoCadro*u), 
									anchoCadro, 
									altoCadro));
				
			}
		}
	}
	
	private void Posicions() {
		xMenor = anchoV;
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
	
	public space_invaders(String title) {
		super(title);
	}

	public static void main(final String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new space_invaders("Space Invaders"));
		app.setDisplayMode(anchoV, altoV, false);
		app.setVSync(true);
		app.setShowFPS(false);
		app.start();
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		//debuxamos suelo
		
		int gris = 10; 
		
		for (int i=altoV-altoV/2;i<altoV;i += 5){
			g.setColor(new Color(gris, gris, gris));
			g.fillRect(0,i,anchoV,altoV-i);
			gris += 1;
		}

		//debuxamos tanque (2 rectangulos: vehiculo + can�n)
		
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
			g.fillRect(
					arrayBalas.get(i).punto.x,
					arrayBalas.get(i).punto.y,
					arrayBalas.get(i).ancho,
					arrayBalas.get(i).alto);
		}
		
		for (int i=0; i<arrayBalasMarcianos.size(); i++) {
			Random rand = new Random ();
			g.setColor(new Color(rand.nextInt((255) + 1), 200, 150));
			g.fillRect(
					arrayBalasMarcianos.get(i).punto.x, 
					arrayBalasMarcianos.get(i).punto.y, 
					arrayBalasMarcianos.get(i).ancho,
					arrayBalasMarcianos.get(i).alto);
		}
		
		//debuxamos defensas
		
		float color_restar = 0;
		
		for (int i=0; i<arrayCadros.size(); i++) {
			g.setColor(new Color(100-(int)color_restar, 80-(int)color_restar, 60-(int)color_restar));
			g.fillRect(
					arrayCadros.get(i).punto.x, 
					arrayCadros.get(i).punto.y, 
					arrayCadros.get(i).ancho, 
					arrayCadros.get(i).alto);
			color_restar += 0.02;
		}
		
		//debuxamos marcianos
		
		for (int i=0; i<arrayMarcianos.size(); i++) {
			if (!arrayMarcianos.get(i).morto) {
				g.setColor(new Color(200,200,200));
				if (numImagenMarciano == 1) {
					spriteMarciano1.draw(
							arrayMarcianos.get(i).punto.x-anchoMarciano/8,
							arrayMarcianos.get(i).punto.y-altoMarciano/8,
							arrayMarcianos.get(i).ancho+anchoMarciano/4,
							arrayMarcianos.get(i).alto+altoMarciano/4);
				}
				else {
					spriteMarciano2.draw(
							arrayMarcianos.get(i).punto.x-anchoMarciano/8,
							arrayMarcianos.get(i).punto.y-altoMarciano/8,
							arrayMarcianos.get(i).ancho+anchoMarciano/4,
							arrayMarcianos.get(i).alto+altoMarciano/4);
				}
				g.setColor(new Color(255,255,255));
			}
		}
		
		//debuxamos puntuacion
		
		g.setColor(new Color(255, 255, 255));
		
		g.drawString("score: "+puntuacion, 5, 0);
		if (numMarcianosVivos <= 0) {
			g.setColor(new Color(0,255,0));
			g.drawString("VICTORIA", anchoV-80, 0);
		}
		else if (gameOver) {
			g.setColor(new Color(255,0,0));
			g.drawString("DERROTA", anchoV-80, 0);
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
			arrayBalas.add(new bala(
							new punto(
									pj.punto.x+pj.ancho/2-pj.anchoCanon/2,
									pj.punto.y-pj.altoCanon),
							pj.anchoCanon,
							pj.altoCanon, 
							velocidadeBala));
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
			
			if (pj.punto.x > anchoV-pj.ancho) {
				pj.punto.x = anchoV-pj.ancho;
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
			if (arrayBalasMarcianos.get(i).punto.y > altoV) {
				arrayBalasMarcianos.remove(i);
			}
		}
		
		//disparo marcianos
		
		if (!gameOver) {
			for (int i=0; i<arrayMarcianos.size(); i++) {
				if (arrayMarcianos.get(i).abaixo && arrayMarcianos.get(i).recarga == 0) {
					Random rand = new Random ();
					if (rand.nextInt((1000)) >  990 && !arrayMarcianos.get(i).morto && arrayBalasMarcianos.size() < 4) {
						arrayBalasMarcianos.add(new bala(
												new punto(
														arrayMarcianos.get(i).punto.x+anchoMarciano/2-anchoBalaMarciano/2,
														arrayMarcianos.get(i).punto.y+altoMarciano), 
												anchoBalaMarciano,
												altoBalaMarciano, 
												velocidadeBalaMarciano));
						arrayMarcianos.get(i).recarga = 120;
					}
				}
				if (arrayMarcianos.get(i).recarga > 0) {
					arrayMarcianos.get(i).recarga -= 1;
				}
			}
		}
		
		//movemento marcianos
		
			//movemento horizontal
		
		if (yMaior > altoV - altoMarciano*4) {
			gameOver = true;
		}
		
		if (descansoMarcianos == 0) {
			movHorizontal = true;
		}
		else {
			movHorizontal = false;
		}
		
		if (!movVertical && movHorizontal && !gameOver) {
			
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
					
				if (arrayMarcianos.get(i).punto.x > anchoV-anchoMarciano) {
					arrayMarcianos.get(i).punto.x = anchoV-anchoMarciano;
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
			
			if (xMenor == 0 && !movVertical && direccionMarcianos == 0) {
				movVertical = true;
			}
			
			if (xMaior == anchoV-anchoMarciano && !movVertical && direccionMarcianos == 1) {
				movVertical = true;
			}
		}
		
		if (movVertical && descansoMarcianos == 0 && !gameOver) {
			for (int i=0; i<arrayMarcianos.size(); i++) {
				arrayMarcianos.get(i).punto.y += altoMarciano;
				if (xMenor == 0) {
					direccionMarcianos = 1;
				}
				else {
					direccionMarcianos = 0;
				}
			movVertical = false;
			descansoMarcianos = descansoMarcianos0;
			}
			
		}
		
		//colisions
		
		for (int i=0; i<arrayBalas.size(); i++) {
			
			for (int u=0; u<arrayMarcianos.size(); u++) {
				if (!arrayMarcianos.get(u).morto) {
					Rectangle rectanguloMarciano = new Rectangle(
											arrayMarcianos.get(u).punto.x, 
											arrayMarcianos.get(u).punto.y, 
											arrayMarcianos.get(u).ancho,
											arrayMarcianos.get(u).alto);
					Rectangle rectanguloBala = new Rectangle(
											arrayBalas.get(i).punto.x, 
											arrayBalas.get(i).punto.y, 
											arrayBalas.get(i).ancho, 
											arrayBalas.get(i).alto);
					if (rectanguloBala.intersects(rectanguloMarciano)) {
						arrayMarcianos.get(u).morto = true;
						if (arrayMarcianos.get(u).abaixo) {
							arrayMarcianos.get(u).abaixo = false;
							if (u-numMarcianosFila >= 0) {
								arrayMarcianos.get(u-numMarcianosFila).abaixo = true;
							}
						}
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
			Rectangle rectanguloCadro = new Rectangle(
									arrayCadros.get(u).punto.x, 
									arrayCadros.get(u).punto.y, 
									arrayCadros.get(u).ancho, 
									arrayCadros.get(u).alto);
			Rectangle rectanguloBala = new Rectangle(
									arrayBalas.get(i).punto.x, 
									arrayBalas.get(i).punto.y,
									arrayBalas.get(i).ancho, 
									arrayBalas.get(i).alto);
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
			Rectangle rectanguloCadro = new Rectangle(
									arrayCadros.get(u).punto.x,
									arrayCadros.get(u).punto.y, 
									arrayCadros.get(u).ancho, 
									arrayCadros.get(u).alto);
			Rectangle rectanguloBala = new Rectangle(
									arrayBalasMarcianos.get(i).punto.x,
									arrayBalasMarcianos.get(i).punto.y,
									arrayBalasMarcianos.get(i).ancho, 
									arrayBalasMarcianos.get(i).alto);
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
		Rectangle rectanguloBala = new Rectangle(
									arrayBalasMarcianos.get(i).punto.x, 
									arrayBalasMarcianos.get(i).punto.y,
									arrayBalasMarcianos.get(i).ancho, 
									arrayBalasMarcianos.get(i).alto);
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
		puntuacion = 0;
		run = true;
		gameOver = false;
	}

}
