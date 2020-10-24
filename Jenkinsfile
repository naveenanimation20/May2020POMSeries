pipeline { 
agent any 
    stages { 
        stage ('Build') { 
            steps{
                echo "Building"

            }
        }
        stage ('Test') { 
        steps{
                echo "Test"
                sh "mvn clean install"

            }
        }
        stage ('QA') { 
        steps{
                echo "QA"

            }
        }
        stage ('Deploy') { 
        steps{
                echo "Deploy"

            }
        }
        stage ('Monitor') { 
 steps{
                echo "Monitor"

            }
        }
 
    }           
 }
