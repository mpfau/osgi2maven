package osgi2maven.bundleImport


import java.io.File
import java.util.Map
import java.util.jar.JarFile
import java.util.jar.Manifest

import org.apache.felix.framework.Felix
import org.apache.felix.framework.util.StringMap
import org.osgi.framework.Bundle
import org.osgi.framework.Constants

class BundleReader {
    String group = null
    String dependencyGroup = null

    /**
     * Reads an OSGI-Bundle and and converts it into a POM 
     * @param file - the bundle
     * @return the POM-object
     */
    public Pom readDependencies(File file) {

        Pom pom = readFromJar(file)

        return pom
    }

    private Pom readFromJar(File file) {
        def pom = new Pom()
        def manifest;
        if (file.isDirectory()) {
            manifest = new Manifest(new File(file, 'META-INF/MANIFEST.MF').newInputStream())
            pom.packaging = 'zip'
        } else {
            manifest = new JarFile(file).manifest
        }

        pom.artifact = manifest.attr.getValue(Constants.BUNDLE_SYMBOLICNAME)
        if (pom.artifact.contains(';')) {
            pom.artifact = pom.artifact.split(';')[0]
        }
        if (group) {
            pom.group = group
        } else {
            pom.group = pom.artifact
        }
        pom.dependencyGroup = dependencyGroup
        pom.version = manifest.attr.getValue(Constants.BUNDLE_VERSION)

        def requireBundlesString = manifest.attr.getValue(Constants.REQUIRE_BUNDLE)
        pom.bundles = new RequireBundlesParser(requireBundlesString).getRequireBundles()
        return pom
    }

    private Pom readFromFelix(File file) {
        Map configMap = new StringMap(false);
        def pom = new Pom()
        Felix felix = new Felix(configMap);
        try {
            felix.start();
            Bundle bundle = felix.getBundleContext().installBundle("reference:file:" + file);
            def bundles = []

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
        } finally {
            felix.stop()
        }
        return pom
    }
}
