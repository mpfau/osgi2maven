package osgi2maven.mavenExport
;

import org.apache.tools.ant.types.resources.comparators.Exists;
import org.junit.Before;
import org.junit.Test;

import osgi2maven.XmlUtils;
import osgi2maven.bundleImport.BundleReader;
import osgi2maven.bundleImport.Pom;
import osgi2maven.mavenExport.MavenDeployer 

import static org.junit.Assert.*;

class MavenDeployerTest {
	def repo = new File('build/mavenTestRepo')
	
	@Before
	void deleteAndCreateTestRepository() {
		def ant = new AntBuilder()
		ant.delete dir: repo
		repo.mkdirs()
	}
	
	@Test
	void publishOsgiArtifact() {
		def deployer = new MavenDeployer(repo.toURI().toString(), '', '')
		def bundle = new File('/home/map/dev/eclipse-lism2/plugins/org.eclipse.jface_3.4.2.M20090107-0800.jar')
		def group = 'eclipse-34'
		deployer.publishOsgiArtifact(group, bundle)
		assertTrue new File('build/mavenTestRepo/eclipse-34/org.eclipse.jface/3.4.2.M20090107-0800/org.eclipse.jface-3.4.2.M20090107-0800.jar').exists()

		def uploadedPom = new File('build/mavenTestRepo/eclipse-34/org.eclipse.jface/3.4.2.M20090107-0800/org.eclipse.jface-3.4.2.M20090107-0800.pom')
		def reader = new BundleReader(group: group)
		XmlUtils.compareXml 'Did not upload correct POM', reader.readDependencies(bundle).toString(), uploadedPom.text
	}
	
	@Test
	void publishArtifact() {
		def deployer = new MavenDeployer(repo.toURI().toString(), '', '')
		def group = 'exampleArtifact'
		def artifactName = 'eclipse'
		def version = '3.4'
		def packaging = 'binary'
		def expectedPom = new Pom(group: group, artifact: artifactName, version: version, packaging: packaging, bundles: [])
		def file = new File('resources/test/eclipse')
		deployer.publishArtifact group, artifactName, version, packaging, file
		
		assertTrue new File('build/mavenTestRepo/exampleArtifact/eclipse/3.4/eclipse-3.4.binary').exists()

		def uploadedPom = new File('build/mavenTestRepo/exampleArtifact/eclipse/3.4/eclipse-3.4.pom')
		def reader = new BundleReader(group: group)
		XmlUtils.compareXml 'Did not upload correct POM', expectedPom.toString(), uploadedPom.text
		
	}

}
