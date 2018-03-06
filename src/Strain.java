import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Strain</h1> Used to record Strain information.
 * <p>
 * <b>Note:</b> Requires implementation of column data types as well
 *
 * @author John Aldo Lamberti
 * @see Search
 * @version 1.0
 * @since 03-01-2018
 */
public class Strain {

	private List<String> keys;
	private HashMap<String, String> data;

	/**
	 * Creates an empty Strain.
	 * <p>
	 * Sets all data to empty string.
	 */
	public Strain() {
		data = new HashMap<String, String>();
		keys = new ArrayList<String>();

		keys = Arrays.asList("strain_name", "genotype", "strain_background", "DNA_on_array",
				"record_creation_date", "strain_creation_date", "strain_created_by", "location", "new_location",
				"line_number", "notes", "remarks", "result", "strain_maintenance", "expression");
		
		for (String key : keys)
			data.put(key, "");
	}

	/**
	 * Creates a Strain from a given ResultSet
	 * <p>
	 * 
	 * @param resultSet
	 *            result set for filling in data
	 * @throws SQLException
	 * @see ResultSet
	 */
	@SuppressWarnings("unchecked")
	public Strain(ResultSet resultSet) throws SQLException {
		data = new HashMap<String, String>();
		keys = new ArrayList<String>();
		
		System.out.println("before");
		ResultSetMetaData rsmd = resultSet.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++)
			keys.add(rsmd.getColumnName(i));
		System.out.println("after");

		for (String key : keys) {
			String value = resultSet.getString(key);
			if (!(value == null || value.isEmpty()))
				data.put(key, value);
			else
				data.put(key, "");
		}
	}

	/**
	 * Returns true if and only if Strain data is entirely empty.
	 * 
	 * @return True if all data strings are empty.
	 */
	public boolean isEmpty() {
		for (String value : data.values()) {
			if (!value.isEmpty())
				return false;
		}
		return true;
	}

	/**
	 * Associates the specified value with the specified key in this map. If the map
	 * previously contained a mapping for the key, the old value is replaced.
	 * 
	 * @param key
	 *            key with which the specified value is to be associated
	 * @param value
	 *            value to be associated with the specified key
	 */
	public void set(String key, String value) {
		data.put(key, value);
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this map
	 * contains no mapping for the key.
	 * 
	 * @param key
	 *            the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this map
	 *         contains no mapping for the key. However, the key could also be
	 *         mapped to null
	 */
	public String get(String key) {
		return data.get(key);
	}

	/**
	 * Call to return the Hashmap of data contained in the Strain Class. Each key
	 * represents a column in the database and each value is the associated value
	 * for that strain.
	 * 
	 * @return The Hashmap of data contained in the strain class
	 */
	public HashMap<String, String> getData() {
		return data;
	}

	/**
	 * Call to return the keys of the Hashmap contained in the Strain class. Each
	 * key represents a column in the database and can be used to find the given
	 * value of that key.
	 * 
	 * @return List of keys of Hashmap contained in the Strian class.
	 */
	public List<String> getKeys() {
		return keys;
	}
}