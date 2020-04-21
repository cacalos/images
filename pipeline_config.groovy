allow_scm_jenkinsfile = true
application_environments{
    dev{
        short_name = "dev"
        long_name = "Development"
		/*
        helm_configuration_repository ="https://github.com/cacalos/images.git"
        helm_configuration_repository_credential = "github-cacalos"
		*/
        tiller_namespace = "testjks"
        k8s_credential = "kube_config"
        k8s_context = "dev"
        promote_previous_image = false
		chart_values_file = "dev.yaml"
    }
    test{
        short_name = "test"
        long_name = "Test"
        k8s_context = "test"
    }
    prod{
        short_name = "prod"
        long_name = "Production"
        k8s_credential = "cluster1-config"
        k8s_context = "production"
    }
}
libraries{
    common{
        makefile_config = '''
PKG_LIST := $(shell go list ./... | grep -vE &quot;test|cmd\\/httpif|cmd\\/nrfclient|cmd\\/usmsfperf&quot;)
GO_FILES := $(shell find . -name &quot;*.go&quot; | grep -v /vendor/ | grep -v _test.go)
MAKEIMAGE_SHELL := sh ./cmd/usmsf/jenkins_build.sh
'''
    }
	helm{
        overriding = "resources/helm_charts/dish_samsung/overriding-values/uangel/t2x-master1/ci-cd-test/values.yaml"
        name_space = "testjks"
        name = "usmsf-jks"
        service = "resources/helm_charts/dish_samsung/dish-smsf-1.2.1.tgz"
	}
    sdp{
		images{
			registry = "https://registry-1.docker.io/v2/"
			cred = "docker-hub"
			repository = "joyddung"
			docker_args = "-u 0:0 --env JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk --env SONAR_SCANNER_OPTS=-Xmx2048m"
		}
	}
    make
    gitfile
    github{
    	source_type = "github"
 	}
    email{
        final_stage_sendmail = false
        fail_mail_lists = "cacalos1@uangel.com"
        success_mail_lists = "cacalos@korea.com"
    }
    kubernetes{
        helm_configuration_repository ="https://github.com/cacalos/images.git"
        helm_configuration_repository_credential = "github-cacalos-pwd"
        tiller_namespace = "testjks"
        k8s_credential = "kube_config"
        k8s_context = "dev"
        promote_previous_image = false
    }
    sonarqube{
        credential_id = "sonarqube"
        enforce_quality_gate = true
    }
}
