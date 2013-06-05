package osgi2maven.bundleImport



import org.osgi.framework.Constants


class RequireBundlesParser {

    String requiredBundles
    public RequireBundlesParser(String requiredBundles) {
        this.requiredBundles = requiredBundles
    }

    List getRequireBundles() {
        def bundles = []
        if (requiredBundles != null) {
            getStringBundles().each { String bundle ->
                bundles << getBundle(bundle)
            }
        }
        return bundles
    }

    private RequiredBundle getBundle(String string) {
        List elements = string.split(";")
        String name = elements[0]
        elements.remove(0)
        RequiredBundle bundle = new RequiredBundle(name: name, resolution: Constants.RESOLUTION_MANDATORY, visibility: Constants.VISIBILITY_PRIVATE, version: "[1.0,)")
        elements.each { String element ->
            if (element.contains(Constants.BUNDLE_VERSION_ATTRIBUTE)) {
                bundle.setVersion(element.substring(element.indexOf('"') + 1).replaceAll('"',''.trim()))
            } else if (element.contains(Constants.RESOLUTION_DIRECTIVE)){
                bundle.setResolution(element.substring(element.indexOf('=') + 1))
            } else if (element.contains(Constants.VISIBILITY_DIRECTIVE)){
                bundle.setVisibility(element.substring(element.indexOf('=') + 1))
            }
        }
        return bundle
    }

    private List getStringBundles() {
        def bundles = []
        String bundle
        while((bundle = getNextBundle()) != "") {
            bundles << bundle
        }
        return bundles
    }

    private String getNextBundle() {
        boolean inQuotation = false
        int position = 0
        for (String c in requiredBundles) {
            if (c == '"') {
                inQuotation = ! inQuotation
            }
            if (c == ',' && !inQuotation) {
                String bundle = requiredBundles.substring(0, position)
                requiredBundles = requiredBundles.substring(bundle.length() + 1)
                return bundle
            }
            position ++
        }
        if (position == requiredBundles.length()) {
            String bundle = requiredBundles
            requiredBundles = ""
            return bundle
        }
        return ""
    }
}




