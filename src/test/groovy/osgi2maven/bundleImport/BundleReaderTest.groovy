package osgi2maven.bundleImport;

import org.junit.Test

import osgi2maven.XmlUtils

class BundleReaderTest {
    @Test
    public void readBundle() {
        File bundle = new File("resources/test/org.eclipse.jface_3.4.2.M20090107-0800.jar")
        def reader = new BundleReader(group: 'eclipseTest')
        def jfaceExpectedPom = """<?xml version="1.0" encoding="UTF-8"?>
		<!--
		Apache Maven 2 POM generated by osgi2maven
		http://wiki.github.com/mpfau/osgi2maven/
		-->
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
		  <modelVersion>4.0.0</modelVersion>
		  <groupId>eclipseTest</groupId>
		  <artifactId>org.eclipse.jface</artifactId>
		  <version>3.4.2.M20090107-0800</version>
		  <packaging>jar</packaging>
		  <dependencies>
			<dependency>
			  <groupId>eclipseTest</groupId>
			  <artifactId>org.eclipse.swt</artifactId>
			  <version>[3.4.0,4.0.0)</version>
			  <scope>compile</scope>
			</dependency>
			<dependency>
			  <groupId>eclipseTest</groupId>
			  <artifactId>org.eclipse.core.commands</artifactId>
			  <version>[3.4.0,4.0.0)</version>
			  <scope>compile</scope>
			</dependency>
			<dependency>
			  <groupId>eclipseTest</groupId>
			  <artifactId>org.eclipse.equinox.common</artifactId>
			  <version>[3.2.0,4.0.0)</version>
			  <scope>compile</scope>
			</dependency>
		  </dependencies>
		</project>"""
        XmlUtils.compareXml "did not create the POM as expected", jfaceExpectedPom, reader.readDependencies(bundle).toString()

        bundle = new File("resources/test/org.apache.ant_1.7.0.v200803061910/")
        reader = new BundleReader()
        def antExpectedPom = """<?xml version="1.0" encoding="UTF-8"?>
		<!--
		Apache Maven 2 POM generated by osgi2maven
		http://wiki.github.com/mpfau/osgi2maven/
		-->
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
		  <modelVersion>4.0.0</modelVersion>
		  <groupId>org.apache.ant</groupId>
		  <artifactId>org.apache.ant</artifactId>
		  <version>1.7.0.v200803061910</version>
		  <packaging>zip</packaging>
		  <dependencies>
			<dependency>
			  <groupId>org.eclipse.osgi</groupId>
			  <artifactId>org.eclipse.osgi</artifactId>
			  <version>[1.0,)</version>
			  <scope>compile</scope>
			</dependency>
		  </dependencies>
		</project>"""
        XmlUtils.compareXml "did not create the POM as expected", antExpectedPom, reader.readDependencies(bundle).toString()

        bundle = new File("resources/test/org.eclipse.ecf.identity_2.0.0.v20080611-1715.jar")
        def ecfExpectedPom = """<?xml version="1.0" encoding="UTF-8"?>
		<!--
		Apache Maven 2 POM generated by osgi2maven
		http://wiki.github.com/mpfau/osgi2maven/
		-->
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
		  <modelVersion>4.0.0</modelVersion>
		  <groupId>org.eclipse.ecf.identity</groupId>
		  <artifactId>org.eclipse.ecf.identity</artifactId>
		  <version>2.0.0.v20080611-1715</version>
		  <packaging>jar</packaging>
		  <dependencies>
			<dependency>
			  <groupId>org.eclipse.equinox.common</groupId>
			  <artifactId>org.eclipse.equinox.common</artifactId>
			  <version>[1.0,)</version>
			  <scope>compile</scope>
			</dependency>
			<dependency>
			  <groupId>org.eclipse.equinox.registry</groupId>
			  <artifactId>org.eclipse.equinox.registry</artifactId>
			  <version>[1.0,)</version>
			  <scope>compile</scope>
			</dependency>
		  </dependencies>
		</project>"""
        XmlUtils.compareXml "did not create the POM as expected", ecfExpectedPom, reader.readDependencies(bundle).toString()
    }
}
