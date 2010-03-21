package osgi2maven.bundleImport


import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.framework.cache.BundleCache;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.util.tracker.ServiceTracker;

class BundleReader {
	String group = null
	
	/**
	 * Reads an OSGI-Bundle and and converts it into a POM 
	 * @param file - the bundle
	 * @return the POM-object
	 */
	public Pom readDependencies(File file) {
		Map configMap = new StringMap(false);
		
		Felix felix = new Felix(configMap);
		felix.start();
		Bundle bundle = felix.getBundleContext().installBundle("reference:file:" + file);
		def bundles = []
		def pom = new Pom()
		
		pom.artifact = bundle.getHeaders().get(Constants.BUNDLE_SYMBOLICNAME)
		if (pom.artifact.contains(';')) {
			pom.artifact = pom.artifact.split(';')[0]
		}
		if (group) {
			pom.group = group
		} else {
			pom.group = pom.artifact
		}
		pom.version = bundle.getHeaders().get(Constants.BUNDLE_VERSION)
		if (file.isDirectory()) {
			pom.packaging = 'zip'
		}
		
		def requireBundlesString = bundle.getHeaders().get(Constants.REQUIRE_BUNDLE)
		pom.bundles = new RequireBundlesParser(requireBundlesString).getRequireBundles()

		bundle.uninstall()
		
		felix.stop()
		
		return pom
	}

}
