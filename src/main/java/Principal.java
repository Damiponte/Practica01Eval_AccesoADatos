import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Principal {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParserConfigurationException, TransformerException {
		ArrayList<Empleados> listaEmpleados = new ArrayList<Empleados>();
		ArrayList<Departamentos> listaDepartamentos = new ArrayList<Departamentos>();
		
		BufferedReader bufrEmpleados = Files.newBufferedReader(Paths.get("csv\\empleados.csv")); 
		BufferedReader bufrDepartamentos = Files.newBufferedReader(Paths.get("csv\\departamentos.csv"));
		
		Stream<String> lineasEmpleados = bufrEmpleados.lines();
		Stream<String> lineasDepartamentos = bufrDepartamentos.lines();
		
		System.out.println("EMPLEADOS");
		lineasEmpleados.forEach(linea->{
			//System.out.println(linea);
			
			String[] empleadoPropiedades = linea.split(",");
			//System.out.println("codigo: " + empleadoPropiedades[0] + " nombre: " + empleadoPropiedades[1]);
			
			Empleados empleado = new Empleados(
					Integer.parseInt(empleadoPropiedades[0]),
					empleadoPropiedades[1],
					empleadoPropiedades[2], 
					empleadoPropiedades[3],
					Integer.parseInt(empleadoPropiedades[4]),
					Integer.parseInt(empleadoPropiedades[5]));
			
			System.out.println("\t"+empleado.toString());
			
			
			listaEmpleados.add(empleado);
			
			
		});
		
		System.out.println("\nDEPARTAMENTOS");
		lineasDepartamentos.forEach(linea->{
			//System.out.println(linea);
			
			String[] departamentoPropiedades = linea.split(",");
			//System.out.println("codigo: " + empleadoPropiedades[0] + " nombre: " + empleadoPropiedades[1]);
			
			Departamentos departamento = new Departamentos(
					Integer.parseInt(departamentoPropiedades[0]),
					departamentoPropiedades[1],
					departamentoPropiedades[2]);
			
			System.out.println("\t"+departamento.toString());
			
			listaDepartamentos.add(departamento);
			
			
		});
		
		
		//Insertar datos 
		insertarDatos(listaEmpleados, listaDepartamentos);
		
		//Generar fichero
		generarFichero();
		
		//GenerarXML
		generarXML(listaEmpleados, listaDepartamentos);
	
	}

	private static void generarXML(ArrayList<Empleados> listaEmpleados, ArrayList<Departamentos> listaDepartamentos) throws ParserConfigurationException, TransformerException {
		System.out.println("\nGENERANDO XML");
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factoria.newDocumentBuilder();
		
		Document documento = db.newDocument();
		
		Element empresa = documento.createElement("Empresa");
		documento.appendChild(empresa);
		
		Element empleados = documento.createElement("Empleados");
		empresa.appendChild(empleados);
		
		Element departamentos = documento.createElement("Departamentos");
		empresa.appendChild(departamentos);
		
		
		//GENEREAR EMPLEADOS
		for (int i = 0; i < listaEmpleados.size(); i++) {
			Element empleado = documento.createElement("Empleado");
			empleados.appendChild(empleado);
			
			Element codigo = documento.createElement("codigo");
			codigo.setTextContent(String.valueOf(listaEmpleados.get(i).getCodigo())); 
			empleado.appendChild(codigo);
			
			Element nombre = documento.createElement("nombre");
			nombre.setTextContent(listaEmpleados.get(i).getNombre()); 
			empleado.appendChild(nombre);
			
			Element apellido = documento.createElement("apellido");
			apellido.setTextContent(listaEmpleados.get(i).getApellido()); 
			empleado.appendChild(apellido);
			
			Element puesto = documento.createElement("puesto");
			puesto.setTextContent(listaEmpleados.get(i).getPuesto()); 
			empleado.appendChild(puesto);
			
			Element sueldo = documento.createElement("sueldo");
			sueldo.setTextContent(String.valueOf(listaEmpleados.get(i).getSueldo())); 
			empleado.appendChild(sueldo);
			
			Element codigoDep = documento.createElement("codigoDep");
			codigoDep.setTextContent(String.valueOf(listaEmpleados.get(i).getCodigoDepartamento())); 
			empleado.appendChild(codigoDep);
		}
		System.out.println("\templeados generados");
		
		//GENERAR DEPARTAMENTOS 
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			Element departamento = documento.createElement("Departamento");
			departamentos.appendChild(departamento);
			
			Element codigo = documento.createElement("codigo");
			codigo.setTextContent(String.valueOf(listaDepartamentos.get(i).getCodigo())); 
			departamento.appendChild(codigo);
			
			Element nombre = documento.createElement("nombre");
			nombre.setTextContent(listaDepartamentos.get(i).getNombre()); 
			departamento.appendChild(nombre);
			
			Element localidad = documento.createElement("localidad");
			localidad.setTextContent(String.valueOf(listaDepartamentos.get(i).getLocalidad())); 
			departamento.appendChild(localidad);
		}
		System.out.println("\tdepartamentos generados");
		
		//ESCRIBIR XML
		TransformerFactory transformerF = TransformerFactory.newInstance();
		Transformer transformer = transformerF.newTransformer();
		DOMSource dom = new DOMSource(documento);
		StreamResult sr = new StreamResult(new File("csv\\datos.xml"));
		transformer.transform(dom, sr);
		System.out.println("\tXML ESCRITO CON ÉXITO");
	}

	private static void generarFichero() throws ClassNotFoundException, SQLException, IOException {
		Class.forName("org.postgresql.Driver");
		Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost/PracticaEval01","postgres","1234");
		
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery("SELECT nombre FROM departamentos");
	
		//CONSULTA 1
		System.out.println("\nNOMBRES DEPARTAMENTOS:\n");
		String leido1 = "";
		while(rs.next()) {
			String leyendo = rs.getString(1);
			System.out.println("\tnombre: " + leyendo);
			leido1 = leido1 + leyendo + "\n";
		}
		//System.out.println(leido1);
		System.out.println("\n**********");
		//CONSULTA 2
		System.out.println("\nDatos de los empleados que trabajan en el departamento 20:".toUpperCase());
		st = conexion.createStatement();
		rs = st.executeQuery("SELECT * FROM empleados WHERE codigodepartamento = 20");
		
		String leido2 = "";
		while(rs.next()) {
			String leyendo = 
			"\n\tCodigo: " + rs.getInt(1) +
			"\n\tNombre: " + rs.getString(2) +
			"\n\tApellido: " +rs.getString(3) + 
			"\n\tPuesto: " + rs.getString(4) + 
			"\n\tSalario: " + rs.getInt(5) + 
			"\n\tCodigo Departamento: " + rs.getInt(6);
			leido2 = leido2 + leyendo;
			System.out.println(leyendo);
		}
		
		//ESCRIBIENDO EN TXT
		BufferedWriter bw = Files.newBufferedWriter(Paths.get("csv\\consultas.txt"));
		bw.write(leido1+ "\n**********\n" + leido2.replaceAll("\\t", ""));
		bw.flush();
	}

	private static void insertarDatos(ArrayList<Empleados> listaEmpleados, ArrayList<Departamentos> listaDepartamentos) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("\nINSERTANDO");
		Class.forName("org.postgresql.Driver");
		Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost/PracticaEval01","postgres","1234");
		
		Statement as = conexion.createStatement();	
		as.executeUpdate("DELETE FROM Empleados");
		as.executeUpdate("DELETE FROM Departamentos");
		
		//Inserción empleados
		for (int i = 0; i < listaEmpleados.size(); i++) {
			PreparedStatement ps = conexion.prepareStatement("INSERT INTO Empleados VALUES (?,?,?,?,?,?)");
			ps.setInt(1, listaEmpleados.get(i).getCodigo());
			ps.setString(2, listaEmpleados.get(i).getNombre());
			ps.setString(3, listaEmpleados.get(i).getApellido());
			ps.setString(4, listaEmpleados.get(i).getPuesto());
			ps.setInt(5, listaEmpleados.get(i).getSueldo());
			ps.setInt(6, listaEmpleados.get(i).getCodigoDepartamento());
			ps.executeUpdate();
		}
		
		System.out.println("\templeados insertados");
		
		//Insercion departamentos
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO Departamentos VALUES (?,?,?)");
			sentencia.setInt(1, listaDepartamentos.get(i).getCodigo());
			sentencia.setString(2, listaDepartamentos.get(i).getNombre());
			sentencia.setString(3, listaDepartamentos.get(i).getLocalidad());
			sentencia.executeUpdate();
		}
		System.out.println("\tdepartamentos insertados");
		

		
		
		
	}
	
	
}
