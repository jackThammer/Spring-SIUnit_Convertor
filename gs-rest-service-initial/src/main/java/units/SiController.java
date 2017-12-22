package units;

import java.text.DecimalFormat;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiController {
	    String convertedString;
		String convertedUnitString;
		double multiplication_factor;
	    
		@RequestMapping("users/si")
	    public Si si(@RequestParam(value="units", defaultValue="0") String units) throws ScriptException {
	    	
	    	if(units.equals("0")) {
	    		return new Si("ERROR: NO UNITES PASSED","0000");
	    	}else {
	    	debugUnits(units);	
	    	DecimalFormat df = new DecimalFormat("#.##############");	
	    	return new Si(convertedString, df.format(multiplication_factor));
	    	}
	    }
	    
	    
public String debugUnits(String units) throws ScriptException {
	    	//First Split
	    	String[] parts = units.split("(?<=[\\-\\/\\*\\(\\)])");
	    	//Second Split
	    	int c=0;
	    	String[] parts_split = new String[2*parts.length-1];
	    	for(int i=0;i<parts.length;i++) {
	    		//System.out.println("i="+i);
				//System.out.println("Parts="+parts[i]+"PART DELETED="+parts[i].substring(parts[i].length() - 1)	+"REMAINDER="+parts[i].substring(0,parts[i].length() - 1));
	    		if((parts[i].substring(parts[i].length() - 1)).equals("/")||(parts[i].substring(parts[i].length() - 1)).equals("*")||(parts[i].substring(parts[i].length() - 1)).equals(")")) {
	    			//System.out.println("PartsSymbole="+parts[i]);
					parts_split[c]=parts[i].substring(0,parts[i].length() - 1);
	    			c++;
	    			parts_split[c]=parts[i].substring(parts[i].length() - 1);
	    			c++;
	     		}else if((parts[i].substring(parts[i].length() - 1)).equals("(")) {
	     			//System.out.println("PartBracket="+parts[i]);
	     			parts_split[c]=parts[i];
	     			c++;
	     		}
	    		else {
	     			//System.out.println("Part="+parts[i]);
	     			parts_split[c]=parts[i];
	     			c++;
	     		}
	    			
			}
	    	
	    	reset();
	    	//String[] convertedString = null;
	    	for(int i=0;i<parts_split.length;i++) {
				//System.out.println("Parts="+parts_split[i]);
	    		findConvertion(parts_split[i]);
			}
	    	
	    	calculateUnit(convertedUnitString);
			return null;
	    	
	    }

	
		
		private void findConvertion(String parts) {
			
			// Cases
						//minute min
			
						if(parts.equalsIgnoreCase("minute")||parts.equalsIgnoreCase("min")) {
							convertedString=convertedString+"s";
							convertedUnitString=convertedUnitString+Double.toString(60);
						}
						//hours h
						else if(parts.equalsIgnoreCase("hours")||parts.equalsIgnoreCase("h")) {
							convertedString=convertedString+"s";
							convertedUnitString=convertedUnitString+Double.toString(3600);
						}
						//day	d
						else if(parts.equalsIgnoreCase("day")||parts.equalsIgnoreCase("d")) {
							convertedString=convertedString+"s";
							convertedUnitString=convertedUnitString+Double.toString(86400);
						}
						// Degree case for (pi/180) °
						else if(parts.equalsIgnoreCase("degree")||parts.equalsIgnoreCase("°")) {
							convertedString=convertedString+"rad";
							convertedUnitString=convertedUnitString+Double.toString(Math.PI/180);
						}
						//degree	'
						else if(parts.equalsIgnoreCase("degree")||parts.equalsIgnoreCase("'")) {
							convertedString=convertedString+"rad";
							convertedUnitString=convertedUnitString+Double.toString(Math.PI/10800);
						}
						//second	''
						else if(parts.equalsIgnoreCase("degree")||parts.equalsIgnoreCase("''")) {
							convertedString=convertedString+"rad";
							convertedUnitString=convertedUnitString+Double.toString(Math.PI/648000);
						}
						//hectare	ha
						else if(parts.equalsIgnoreCase("hectare")||parts.equalsIgnoreCase("ha")) {
							convertedString=convertedString+"m(2)";
							convertedUnitString=convertedUnitString+Double.toString(10000);
						}
						//liter	L
						else if(parts.equalsIgnoreCase("liter")||parts.equalsIgnoreCase("L")) {
							convertedString=convertedString+"m(3)";
							convertedUnitString=convertedUnitString+Double.toString(0.001);
						}
						//tonne	t
						else if(parts.equalsIgnoreCase("tonne")||parts.equalsIgnoreCase("t")) {
							convertedString=convertedString+"kg";
							convertedUnitString=convertedUnitString+Double.toString(Math.pow(10, 3));
						}
						
						else {
							convertedString=convertedString+parts;
							convertedUnitString=convertedUnitString+parts;
							
						}
			
		//	System.out.println("convertedString= "+convertedString+"convertedUnitString= "+convertedUnitString);
		}

		private void calculateUnit(String convertedUnitString) throws ScriptException {
			ScriptEngineManager mgr = new ScriptEngineManager();
		    ScriptEngine engine = mgr.getEngineByName("JavaScript");
		    multiplication_factor= (double) engine.eval(convertedUnitString);
			
		}
		private void reset() {
			convertedUnitString="";
			convertedString="";
		}
	
}
