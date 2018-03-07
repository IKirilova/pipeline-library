def call(parameters = [:]) {

  def stepName = 'generateMtaDescriptor'

  Set parameterKeys = ['srcJson' , 'mtaFile']
    
  handlePipelineStepErrors (stepName: stepName, stepParameters: parameters) {
      File sourceJson = parameters.srcJson
      File mtaFile = parameters.mtaFile
      
      def data = readJSON file: sourceJson

      parsedName = data.name
      parsedVersion = data.version
      applicationName=params.APP_NAME

      mtaData  = readYaml text: libraryResource('mta.yml')
      mtaData['ID'] = "$parsedName"
      mtaData['version'] = "$parsedVersion"
      mtaData['modules'][0]['name'] = "$applicationName"
      mtaData['modules'][0]['parameters']['version'] = "$parsedVersion-\${timestamp}"

      writeYaml file: mtaFile, data: mtaData
    }
}
