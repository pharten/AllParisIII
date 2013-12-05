package parisWork;

import java.io.Serializable;

public class ChemicalFamily extends Object implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2342393489792029771L;
	private int id = 0;
	private String name = "";
	private String comments = "";
	
	public ChemicalFamily() {
		super();
	}
	
	public ChemicalFamily(int id, String name){
		super();
		this.id = id;
		this.name = name;
	}
	
	public ChemicalFamily(int id, String name, String comments){
		super();
		this.id = id;
		this.name = name;
		this.comments = comments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
