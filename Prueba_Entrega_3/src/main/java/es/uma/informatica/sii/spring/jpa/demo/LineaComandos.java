package es.uma.informatica.sii.spring.jpa.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.uma.informatica.sii.spring.jpa.demo.repositories.DietaRepository;

@Component
public class LineaComandos implements CommandLineRunner {
	private DietaRepository repository;
	public LineaComandos(DietaRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		for (String s: args) {
			System.out.println(s);
		}
		// Cambio linea de comandos al hacer el nombre de la dieta unico
		if (args.length > 0) {
			var dieta = repository.findByNombre(args[0]);
			if (dieta.isEmpty()) {
				System.out.println("No existe la dieta con nombre " + args[0]);
			} else {
				System.out.println("La dieta con nombre " + args[0] + " tiene id " + dieta.get().getId());
			}
		}
	}

}
