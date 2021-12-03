
public class Departamentos {
	private int codigo;
	private String nombre;
	private String localidad;
	
	public Departamentos(int codigo, String nombre, String localidad) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.localidad = localidad;
		
		
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public String toString() {
		return "Departamentos [codigo=" + codigo + ", nombre=" + nombre + ", localidad=" + localidad + "]";
	}
	
}
