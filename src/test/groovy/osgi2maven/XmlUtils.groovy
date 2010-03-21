package osgi2maven;

import org.custommonkey.xmlunit.DetailedDiff 
import org.custommonkey.xmlunit.Diff 
import org.custommonkey.xmlunit.XMLUnit;
import static org.junit.Assert.*;

class XmlUtils {
	
	static void compareXml(errorMessage, expectedXml, testXml) {
		XMLUnit.setIgnoreWhitespace true
		DetailedDiff myDiff = new DetailedDiff(new Diff(expectedXml, testXml))
		myDiff.allDifferences.each { 
			println "DIFF> $it" 
		}
		
		assertTrue(errorMessage, myDiff.identical())
	}
}
