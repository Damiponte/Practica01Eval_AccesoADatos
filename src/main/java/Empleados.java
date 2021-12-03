
public class Empleados {
	private int codigo;
	private String nombre;
	private String apellido;
	private String puesto;
	private int sueldo;
	private int codigoDepartamento;
	
	public Empleados(int codigo, String nombre, String apellido, String puesto, int sueldo, int codigoDepartamento) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.puesto = puesto;
		this.sueldo = sueldo;
		this.codigoDepartamento = codigoDepartamento;

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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public int getSueldo() {
		return sueldo;
	}

	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}

	public int getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(int codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	@Override
	public String toString() {
		return "Empleados [codigo=" + codigo + ", nombre=" + nombre + ", apellido=" + apellido + ", puesto=" + puesto
				+ ", sueldo=" + sueldo + ", codigoDepartamento=" + codigoDepartamento + "]";
	}
	
	

}
