package osgi2maven.mavenExport
;

import groovy.util.AntBuilder;

import java.io.File;

import osgi2maven.bundleImport.BundleReader;
import osgi2maven.bundleImport.Pom;

class MavenDeployer {
	def ant = new AntBuilder()
	
	String repository
	String user
	String password
	
	MavenDeployer(repository, user, password) {
		this.repository = repository
		this.user = user
		this.password = password
	}
	
	/**
	 * publishes an osgi-bundle to a maven repository and creates the necessary POM file.
	 * @param group
	 * @param file
	 */
	void publishOsgiArtifact(String group, File file) {
		BundleReader reader = new BundleReader(group: group)
		Pom pom = reader.readDependencies(file)
		def pomFile = new File('build/myPom.xml')
		pomFile.text = pom.toString()
		// use the maven ant task to deploy an artifact
		if (pom.isZip()) {
			File zippedBundle = new File("build/${pom.artifact}-${pom.version}.zip")
			ant.zip(basedir: file, destfile: zippedBundle)
			file = zippedBundle
		}
		publish(pomFile, file)
	}
	
	/**
	 * publishes any artifact to a maven repository (a POM file is also created).
	 * This artifact won't declare any dependencies.
	 * @param group
	 * @param artifactName
	 * @param version
	 * @param file
	 */
	void publishArtifact(String group, String artifactName, String version, String packaging, File file) {
		def pom = new Pom(group: group, artifact: artifactName, version: version, packaging: packaging, bundles: [])
		def pomFile = new File('build/myPom.xml')
		pomFile.text = pom.toString()
		publish pomFile, file
	}
	
	private publish(File pom, File artifact) {
		ant.typedef(resource:"org/apache/maven/artifact/ant/antlib.xml")
		ant.pom(id:"mypom", file: pom)	 
		ant.deploy(file: artifact) {
			ant.remoteRepository(url: repository) {
				ant.authentication(username: user, password: password)
			}
			ant.pom(refid:'mypom')
		}
	}
}
