def call(parameters = [:]) {

  def stepName = 'generateMtaDescriptor'

  Set parameterKeys = ['applicationName', 'srcFile', 'targetFile']
    
  handlePipelineStepErrors (stepName: stepName, stepParameters: parameters) {
      
      def data = readJSON file: parameters.srcFile
      appName = params.applicationName
      mtaData  = readYaml text: libraryResource('mta.yml')
      mtaData['ID'] = data.name
      mtaData['version'] = data.version
      mtaData['modules'][0]['name'] = appName
      mtaData['modules'][0]['parameters']['version'] = "${data.version}-\${timestamp}"

      writeYaml file: parameters.targetFile, data: mtaData
    }
}
