import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class main extends BasicGame {
	
	private boolean run = false;
	
	private boolean movementoVertical = false;

	private static int altoVentana = 600;
	private static int anchoVentana = 600;
	private static float anchoPj = 40;
	private static float altoPj = 20;
	private static float anchoCanon = 5;
	private static float altoCanon = 10;
	private static float velocidadePj = 3;
	private static int cargaBala = 30;
	
	private static float velocidadeBala = 12;
	
	private static int numMarcianosFila = 10;
	private static int numFilasMarcianos = 4;
	private static int numMarcianos = numMarcianosFila * numFilasMarcianos;
	
	private static float anchoMarciano = 25;
	private static float altoMarciano = 25;
	private static float velocidadeMarciano = .5f;
	private static int direccionMarcianos = 0;

	private int MarcianosQueSeMoven0 = numMarcianos - numMarcianosFila;
	private int MarcianosQueSeMovenF = numMarcianos;
	private int CambiarMarcianosQueSeMoven0 = 10;
	private int CambiarMarcianosQueSeMoven = CambiarMarcianosQueSeMoven0;
	
	private float xMenor = anchoVentana;
	private float xMaior = 0;
	
	private punto punto_pj = new punto(anchoVentana/2-anchoPj/2,altoVentana-altoPj);
	
	private pj pj = new pj(punto_pj,anchoPj,altoPj,anchoCanon,altoCanon,velocidadePj,cargaBala);
	
	private ArrayList<marciano> arrayMarcianos=new ArrayList <marciano> ();
	
	private ArrayList<bala> arrayBalas=new ArrayList <bala> ();
	
	
	//FUNCIÓNS
	
	private void crearMarcianos() {
		arrayMarcianos.clear();
		for (int u=0; u<numFilasMarcianos; u++){
			for (int i=0; i<numMarcianosFila; i++){
				arrayMarcianos.add(new marciano(new punto(anchoVentana/(numMarcianosFila+2)*i+(anchoVentana-(anchoVentana/(numMarcianosFila+2)*numMarcianosFila))/2,u*anchoMarciano*2),anchoMarciano, altoMarciano, velocidadeMarciano, direccionMarcianos, false));
			}
		}
	}
	
	private void Posicions() {
		xMenor = anchoVentana;
		xMaior = 0;
		for (int i=0; i<arrayMarcianos.size(); i++) {
			if (!arrayMarcianos.get(i).morto && arrayMarcianos.get(i).punto.x < xMenor) {
				xMenor = arrayMarcianos.get(i).punto.x;
			}
			if (!arrayMarcianos.get(i).morto && arrayMarcianos.get(i).punto.x > xMaior) {
				xMaior = arrayMarcianos.get(i).punto.x;
			}
		}
	}
	
	//////
	
	public main(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new main("Slic2DConceptos"));
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
		
		//debuxamos marcianos
		for (int i=0; i<arrayMarcianos.size(); i++) {
			if (!arrayMarcianos.get(i).morto) {
				g.fillRect(arrayMarcianos.get(i).punto.x,arrayMarcianos.get(i).punto.y,arrayMarcianos.get(i).ancho,arrayMarcianos.get(i).alto);
			}
		}
		
		//debuxamos balas
		
		for (int i=0; i<arrayBalas.size(); i++) {
			g.fillRect(arrayBalas.get(i).punto.x, arrayBalas.get(i).punto.y, arrayBalas.get(i).ancho, arrayBalas.get(i).alto);
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
		
		if (input.isKeyDown(Input.KEY_SPACE) && pj.carga == cargaBala) {
			arrayBalas.add(new bala(new punto(pj.punto.x+pj.ancho/2-pj.anchoCanon/2,pj.punto.y-pj.altoCanon), pj.anchoCanon, pj.altoCanon, velocidadeBala));
			pj.carga = 0;
			
		}
		
		if (pj.carga < cargaBala) {
			pj.carga += 1;
		}
		
		//movemento pj
		
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
		
		//movemento bala
		
		for (int i=0; i<arrayBalas.size(); i++) {
			arrayBalas.get(i).punto.y -= arrayBalas.get(i).velocidade;
			if (arrayBalas.get(i).punto.y < -arrayBalas.get(i).alto) {
				arrayBalas.remove(i);
			}
		}
		
		//movemento marcianos
		
			//movemento horizontal
		
		if (!movementoVertical) {
			for (int i=MarcianosQueSeMoven0; i<MarcianosQueSeMovenF; i++) {
					
				if (arrayMarcianos.get(i).direccion == 0) {
					arrayMarcianos.get(i).punto.x -= arrayMarcianos.get(i).velocidade;
				}
				if (arrayMarcianos.get(i).direccion == 1) {
					arrayMarcianos.get(i).punto.x += arrayMarcianos.get(i).velocidade;
				}
					
				if (arrayMarcianos.get(i).punto.x < 0) {
					arrayMarcianos.get(i).punto.x = 0;
				}
					
				if (arrayMarcianos.get(i).punto.x > anchoVentana-anchoMarciano) {
					arrayMarcianos.get(i).punto.x = anchoVentana-anchoMarciano;
				}
					
			}
		}
		
			//movemento vertical
		
		Posicions();
		
		if (xMenor == 0) {
			movementoVertical = true;
		}
		
		if (xMaior == anchoVentana-anchoMarciano) {
			movementoVertical = true;
		}
		
		if (CambiarMarcianosQueSeMoven == 1 && movementoVertical) {
			for (int i=MarcianosQueSeMoven0; i<MarcianosQueSeMovenF; i++) {
				arrayMarcianos.get(i).punto.y += altoMarciano;
				arrayMarcianos.get(i).velocidade += velocidadeMarciano;
				if (xMenor == 0) {
					arrayMarcianos.get(i).direccion = 1;
				}
				else {
					arrayMarcianos.get(i).direccion = 0;
				}
			movementoVertical = false;
			}
			
		}
		
		CambiarMarcianosQueSeMoven -= 1;
		
		if (CambiarMarcianosQueSeMoven == 0) {
			
			MarcianosQueSeMoven0 = MarcianosQueSeMoven0 - numMarcianosFila;
			MarcianosQueSeMovenF = MarcianosQueSeMovenF - numMarcianosFila;
			
			if (MarcianosQueSeMoven0 < 0) {
				MarcianosQueSeMovenF = numMarcianos;
				MarcianosQueSeMoven0 = numMarcianos - numMarcianosFila;
			}
			
			CambiarMarcianosQueSeMoven = CambiarMarcianosQueSeMoven0;
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
						break;
					}
				}
			}
		}
		
	}
	
	private void StartGame() {
		crearMarcianos();
		Posicions();
		run = true;
	}

}
