def call(parameters = [:]) {

  def stepName = 'generateMtaDescriptor'

  Set parameterKeys = ['srcJson']
    
  handlePipelineStepErrors (stepName: stepName, stepParameters: parameters) {
      def data = readJSON file: 'src/package.json'

      parsedName = data.name
      parsedVersion = data.version
      applicationName=params.APP_NAME

      mtaData  = readYaml text: libraryResource('mta.yml')
      mtaData['ID'] = "$parsedName"
      mtaData['version'] = "$parsedVersion"
      mtaData['modules'][0]['name'] = "$applicationName"
      mtaData['modules'][0]['parameters']['version'] = "$parsedVersion-\${timestamp}"

      writeYaml file: 'src/mta.yaml', data: mtaData
    }
}
