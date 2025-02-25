pipeline {

	agent any

	options {
		buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '3'))
	}

	tools {
		maven 'maven'
	}

	stages {
		stage('Code Compilation') {
			steps {
				echo 'Starting Code Compilation...'
				sh 'mvn clean compile'
				echo 'Code Compilation Completed Successfully!'
			}
		}
		stage('Code QA Execution') {
			steps {
				echo 'Running JUnit Test Cases...'
				sh 'mvn clean test'
				echo 'JUnit Test Cases Completed Successfully!'
			}
		}
		stage('SonarQube Code Quality') {
            environment {
                scannerHome = tool 'qube'
            }
            steps {
                echo 'Starting SonarQube Code Quality Scan...'
                withSonarQubeEnv('sonar-server') {
                    sh 'mvn sonar:sonar'
                }
                echo 'SonarQube Scan Completed. Checking Quality Gate...'
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
                echo 'Quality Gate Check Completed!'
            }
        }
		stage('Code Package') {
			steps {
				echo 'Creating JAR Artifact...'
				sh 'mvn clean package'
				echo 'WAR Artifact Created Successfully!'
			}
		}
		stage('Build & Tag Docker Image') {
			steps {
				echo 'Building Docker Image with Tags...'
				sh "docker build -t arunrade/booking-ms:latest -t booking-ms:latest ."
				echo 'Docker Image Build Completed!'
			}
		}
		stage('Docker Image Scanning') {
			steps {
				echo 'Scanning Docker Image with Trivy...'
				echo 'Docker Image Scanning Completed!'
			}
		}
		stage('Push Docker Image to Docker Hub') {
			steps {
				script {
					withCredentials([string(credentialsId: 'dockerhubCred', variable: 'dockerhubCred')]) {
						sh 'docker login docker.io -u arunrade -p ${dockerhubCred}'
						echo 'Pushing Docker Image to Docker Hub...'
						sh 'docker push arunrade/booking-ms:latest'
						echo 'Docker Image Pushed to Docker Hub Successfully!'
					}
				}
			}
		}
		stage('Push Docker Image to Amazon ECR') {
			steps {
				script {
					withDockerRegistry([credentialsId: 'ecr:ap-south-1:ecr-credentials', url: "https://703671926591.dkr.ecr.ap-south-1.amazonaws.com"]) {
						echo 'Tagging and Pushing Docker Image to ECR...'
						sh '''
                            docker images
                            docker tag booking-ms:latest 703671926591.dkr.ecr.ap-south-1.amazonaws.com/booking-ms:latest
                            docker push 703671926591.dkr.ecr.ap-south-1.amazonaws.com/booking-ms:latest
                        '''
						echo 'Docker Image Pushed to Amazon ECR Successfully!'
					}
				}
			}
		}
		stage('Upload Docker Image to Nexus') {
			steps {
				script {
					withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
						sh 'docker login http://13.233.12.135:8085/repository/booking-ms/ -u admin -p ${PASSWORD}'
						echo "Push Docker Image to Nexus : In Progress"
						sh 'docker tag booking-ms 13.233.12.135:8085/booking-ms:latest'
						sh 'docker push 13.233.12.135:8085/booking-ms'
						echo "Push Docker Image to Nexus : Completed"
					}
				}
			}
		}
        stage('Clean Up Local Docker Images') {
            steps {
                echo 'Cleaning Up Local Docker Images...'
                sh '''
                    docker rmi arunrade/booking-ms:latest || echo "Image not found or already deleted"
                    docker rmi booking-ms:latest || echo "Image not found or already deleted"
                    docker rmi 703671926591.dkr.ecr.ap-south-1.amazonaws.com/booking-ms:latest || echo "Image not found or already deleted"
                    docker image prune -f
                '''
                echo 'Local Docker Images Cleaned Up Successfully!'
            }
        }
	}
}