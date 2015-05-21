package parisWork;

import java.util.concurrent.Callable;

public class ChemicalsConcurrent implements Callable<Chemicals> {
	String filename = null;

	public ChemicalsConcurrent(String filename) {
		this.filename = filename;
	}

	@Override
	public Chemicals call() throws Exception {
		return Chemicals.readFromFile(filename);
	}

}
