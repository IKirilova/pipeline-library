def call(parameters = [:]) {

  def stepName = 'generateMtaDescriptor'

  Set parameterKeys = ['applicationName', 'srcFile', 'targetFile']
    
  handlePipelineStepErrors (stepName: stepName, stepParameters: parameters) {
      
      def data = readJSON file: parameters.srcFile

      mtaData  = readYaml text: libraryResource('mta.yml')
      mtaData['ID'] = data.name
      mtaData['version'] = data.version
      mtaData['modules'][0]['name'] = =params.applicationName
      mtaData['modules'][0]['parameters']['version'] = "$parsedVersion-\${timestamp}"

      writeYaml file: parameters.targetFile, data: mtaData
    }
}
