pipeline {
    agent any

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

		stage('Code Packaging') {
			steps {
				echo 'Packaging code......'
				sh 'mvn clean package'
			}
		}
	}
}