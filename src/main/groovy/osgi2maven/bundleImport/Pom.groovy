package osgi2maven.bundleImport


import osgi2maven.Constants;
import groovy.text.GStringTemplateEngine 


class Pom {
	String group, artifact, version, dependencyGroup
	String packaging = "jar"
	List<RequiredBundle> bundles
	
	boolean isZip() {
		return packaging == "zip"
	}
	
	String toString() {
		def binding = [url: Constants.URL,"group": group, "artifact": artifact, "version": version, "packaging": packaging, "bundles": bundles, "dependencyGroup": dependencyGroup]
		String templateText = this.getClass().getResourceAsStream('Pom.template').text
		def engine = new GStringTemplateEngine()
		def template = engine.createTemplate(templateText).make(binding)
		template.toString()
	}
	
}
