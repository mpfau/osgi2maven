import osgi2maven.mavenExport.MavenDeployer

def repo = 'http://localhost:8081/nexus/content/repositories/thirdparty'
def user = 'admin'
def pw = 'admin123'
def deployer = new MavenDeployer(repo, user, pw)
// publish any osgi-jar you want to, the pom is generated automatically
def bundles = []
new File(/C:\dev\eclipse3.6.1\plugins/).eachFile {bundles << it}
//bundles = [
//    new File(/C:\dev\eclipse3.6.1\plugins\org.eclipse.jst.server.generic.ui_1.0.505.v20100428.jar/),
//    new File(/C:\dev\eclipse3.6.1\plugins\org.eclipse.wst.web.ui_1.1.400.v201004141630.jar/)
//]
def failedBundles = []
bundles.each { bundle ->
    def group = 'eclipse-M20100909'
    try {
        if ("http://localhost:8081/nexus/content/repositories/thirdparty/${group}/")
            deployer.publishOsgiArtifact(group, bundle)
    } catch (Exception e) {
        e.printStackTrace()
        failedBundles << bundle
    }
}

println " -> Failed: "
failedBundles.each { println it.getAbsolutePath() }
System.exit 0

