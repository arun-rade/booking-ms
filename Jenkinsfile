pipeline {
    agent { label 'teamA-jslave' }

	options {
		buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
	}

	tools {
		maven 'maven'
	}
    stages {
		stage('Code Compilation') {
			steps {
				echo 'compiling the code'
				sh 'mvn clean compile'
			}
		}

		stage('JUnit Execution'){
			steps {
				echo 'Executing JUnit tests....'
				sh 'mvn test'
			}
		}
	}
}