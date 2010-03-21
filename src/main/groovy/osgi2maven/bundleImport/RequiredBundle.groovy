package osgi2maven.bundleImport



class RequiredBundle {
	String name
	String version
	String visibility
	String resolution
	
	String toString() {
		return "name: $name; version: $version; visibility: $visibility; resolution: $resolution"
	}
	
}
