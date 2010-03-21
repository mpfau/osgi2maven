package osgi2maven.bundleImport


import static org.junit.Assert.*;

import org.junit.Test;
import osgi2maven.bundleImport.RequireBundlesParser 



class RequireBundlesParserTest {

	@Test
	public void testGetBundles() {
		def bundles = new RequireBundlesParser('org.eclipse.swt;bundle-version="[3.4.0,4.0.0)";visibility:=reexport,org.eclipse.core.commands;bundle-version="[3.4.0,4.0.0)";visibility:=reexport,org.eclipse.equinox.common;bundle-version="[3.2.0,4.0.0)"').getRequireBundles()
		bundles.each {println it}
		assertEquals("The required string contained three bundles", 3, bundles.size())
		assertEquals bundles[0].toString(), "name: org.eclipse.swt; version: [3.4.0,4.0.0); visibility: reexport; resolution: mandatory"
		assertEquals bundles[1].toString(), "name: org.eclipse.core.commands; version: [3.4.0,4.0.0); visibility: reexport; resolution: mandatory"
		assertEquals bundles[2].toString(), "name: org.eclipse.equinox.common; version: [3.2.0,4.0.0); visibility: private; resolution: mandatory"

		assertEquals("An empty required string should not lead to required bundles", 0, new RequireBundlesParser("").getRequireBundles().size())
	}
	
	@Test
	public void testNull() {
		assertEquals([], new RequireBundlesParser(null).getRequireBundles())
	}

}
