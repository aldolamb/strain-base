import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Strain {
	private String strain_name;
	private String genotype;
	private String strain_background;
	private String DNA_on_array;
	private String record_creation_date;
	private String strain_creation_date;
	private String strain_created_by;
	private String location;
	private String new_location;
	private int line_number;
	private String notes;
	private String remarks;
	private String result;
	private String strain_maintenance;
	private String expression;

	public Strain() {
		strain_name = "";
		genotype = "";
		strain_background = "";
		DNA_on_array = "";
		record_creation_date = "";
		strain_creation_date = "";
		strain_created_by = "";
		location = "";
		new_location = "";
		line_number = 0;
		notes = "";
		remarks = "";
		result = "";
		strain_maintenance = "";
		expression = "";
	}

	public Strain(String strain_name, String genotype, String strain_background, String dNA_on_array,
			String record_creation_date, String strain_creation_date, String strain_created_by, String location,
			String new_location, int line_number, String notes, String remarks, String result,
			String strain_maintenance, String expression) {
		super();
		this.strain_name = strain_name;
		this.genotype = genotype;
		this.strain_background = strain_background;
		DNA_on_array = dNA_on_array;
		this.record_creation_date = record_creation_date;
		this.strain_creation_date = strain_creation_date;
		this.strain_created_by = strain_created_by;
		this.location = location;
		this.new_location = new_location;
		this.line_number = line_number;
		this.notes = notes;
		this.remarks = remarks;
		this.result = result;
		this.strain_maintenance = strain_maintenance;
		this.expression = expression;
	}

	public Strain(ResultSet resultSet) throws SQLException {
		strain_name = resultSet.getString("strain_name");
		genotype = resultSet.getString("genotype");
		strain_background = resultSet.getString("strain_background");
		DNA_on_array = resultSet.getString("DNA_on_array");
		record_creation_date = resultSet.getString("record_creation_date");
		strain_creation_date = resultSet.getString("strain_creation_date");
		strain_created_by = resultSet.getString("strain_created_by");
		location = resultSet.getString("location");
		new_location = resultSet.getString("new_location");
		line_number = 0;
		// line_number = Integer.parseInt(resultSet.getString("line_number")));
		notes = resultSet.getString("notes");
		remarks = resultSet.getString("remarks");
		result = resultSet.getString("result");
		strain_maintenance = resultSet.getString("strain_maintenance");
		expression = resultSet.getString("expression");
	}

	public String getStrain_name() {
		return strain_name;
	}

	public void setStrain_name(String strain_name) {
		this.strain_name = strain_name;
	}

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	public String getStrain_background() {
		return strain_background;
	}

	public void setStrain_background(String strain_background) {
		this.strain_background = strain_background;
	}

	public String getDNA_on_array() {
		return DNA_on_array;
	}

	public void setDNA_on_array(String dNA_on_array) {
		DNA_on_array = dNA_on_array;
	}

	public String getRecord_creation_date() {
		return record_creation_date;
	}

	public void setRecord_creation_date(String record_creation_date) {
		this.record_creation_date = record_creation_date;
	}

	public String getStrain_creation_date() {
		return strain_creation_date;
	}

	public void setStrain_creation_date(String strain_creation_date) {
		this.strain_creation_date = strain_creation_date;
	}

	public String getStrain_created_by() {
		return strain_created_by;
	}

	public void setStrain_created_by(String strain_created_by) {
		this.strain_created_by = strain_created_by;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNew_location() {
		return new_location;
	}

	public void setNew_location(String new_location) {
		this.new_location = new_location;
	}

	public int getLine_number() {
		return line_number;
	}

	public void setLine_number(int line_number) {
		this.line_number = line_number;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStrain_maintenance() {
		return strain_maintenance;
	}

	public void setStrain_maintenance(String strain_maintenance) {
		this.strain_maintenance = strain_maintenance;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public List<String> getValues() {
		return new ArrayList<String>(Arrays.asList(strain_name, genotype, strain_background, DNA_on_array, record_creation_date,
				strain_creation_date, strain_created_by, location, new_location, Integer.toString(line_number), notes,
				remarks, result, strain_maintenance, expression));
	}

	public List<String >getColumns() {
		return new ArrayList<String>(Arrays.asList("strain_name", "genotype", "strain_background", "DNA_on_array", "record_creation_date",
				"strain_creation_date", "strain_created_by", "location", "new_location", "line_number", "notes",
				"remarks", "result", "strain_maintenance", "expression"));
	}
}
