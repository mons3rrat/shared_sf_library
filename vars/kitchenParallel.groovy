def call (ArrayList<String> instanceNames, String cookbook) {
    def parallelNodes = [:]
	for (int i = 0; i < instanceNames.size(); i++) {
		def instanceName = instanceNames.get(i)
		parallelNodes["tk-${instanceName}"] = {
			result = sh(script: "cd cookbooks/${cookbook}/ && berks install && sudo kitchen test --destroy always ${instanceName}", returnStatus: true)
			if (result != 0) {
				echo "kitchen returned non-zero exit status"
				echo "Archiving test-kitchen logs"
				archive(includes: ".kitchen/logs/${instanceName}.log")
				error("kitchen returned non-zero exit status")
			}
		}
    }
    parallel parallelNodes
}
