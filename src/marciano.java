
public class marciano {
	public punto punto;
	public float ancho;
	public float alto;
	public float velocidade;
	public int direccion;
	public boolean morto;
	
	public marciano(punto punto,float ancho,float alto,float velocidade,int direccion,boolean morto) {
		this.punto = punto;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidade = velocidade;
		this.direccion = direccion;
		this.morto = morto;
	}
}
