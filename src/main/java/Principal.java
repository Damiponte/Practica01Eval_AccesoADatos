import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Principal {

	public static void main(String[] args) throws IOException {
		ArrayList<Empleados> listaEmpleados = new ArrayList<Empleados>();
		ArrayList<Departamentos> listaDepartamentos = new ArrayList<Departamentos>();
		
		BufferedReader bufrEmpleados = Files.newBufferedReader(Paths.get("C:\\Users\\Usuario\\Documents\\Jose Antonio\\Acceso a datos\\empleados.csv")); 
		BufferedReader bufrDepartamentos = Files.newBufferedReader(Paths.get("C:\\Users\\Usuario\\Documents\\Jose Antonio\\Acceso a datos\\departamentos.csv"));
		
		Stream<String> lineasEmpleados = bufrEmpleados.lines();
		Stream<String> lineasDepartamentos = bufrDepartamentos.lines();
		
		lineasEmpleados.forEach()
			
			
	}
}
