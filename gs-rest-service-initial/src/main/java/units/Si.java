package units;

public class Si {
	 	private String unit_name;
		private String multiplication_factor;

	   
	    public Si(String unit_name, String multiplication_factor) {
	        this.unit_name = unit_name;
	        this.multiplication_factor = multiplication_factor;
	    }
	    public String getUnit_Name() {
	        return unit_name;
	    }

	    public String getMultiplication_Factor() {
	        return multiplication_factor;
	    }
}
