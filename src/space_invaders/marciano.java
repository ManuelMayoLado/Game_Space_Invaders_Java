package space_invaders;

public class marciano {
	public punto punto;
	public float ancho;
	public float alto;
	public float recarga;
	public boolean abaixo;
	public boolean morto;
	public float morrendo;
	
	public marciano(punto punto,float ancho,float alto,boolean abaixo,boolean morto) {
		this.punto = punto;
		this.ancho = ancho;
		this.alto = alto;
		this.recarga = 0;
		this.abaixo = abaixo;
		this.morto = morto;
		this.morrendo = 5;
	}
}
