pipeline {
    agent any

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


		stage('check java , mvn , git version') {
			steps {
				echo 'version check the code'
				sh 'git --version; java --version; /opt/apache-maven-3.9.9/bin/mvn --version'
			}
		}
	}
}