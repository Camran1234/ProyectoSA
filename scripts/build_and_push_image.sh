#!/bin/bash

# Inicializar las variables
environment=""
region=""
project_id=""
repository=""
image_name=""
tag=""
directory=""

# Analizar los argumentos
for arg in "$@"
do
    case $arg in
        --environment=*)
        environment="${arg#*=}"
        shift
        ;;
        --region=*)
        region="${arg#*=}"
        shift
        ;;
        --project_id=*)
        project_id="${arg#*=}"
        shift
        ;;
        --repository=*)
        repository="${arg#*=}"
        shift
        ;;
        --image_name=*)
        image_name="${arg#*=}"
        shift
        ;;
        --tag=*)
        tag="${arg#*=}"
        shift
        ;;
        --directory=*)
        directory="${arg#*=}"
        shift
        ;;
    esac
done

echo "ENVIRONMENT $environment"
echo "REGION: $region"
echo "PROJECT_ID: $project_id"
echo "REPOSITORY: $repository"
echo "IMAGE_NAME: $image_name"
echo "TAG: $tag"
echo "DIRECTORY: $directory"

# Cambiar al directorio del microservicio
cd $directory
ls 

# Construir la imagen Docker
docker build -t $image_name:latest-$environment .

# Etiquetar la imagen con la etiqueta de Git
docker tag $image_name:latest-$environment $region/$project_id/$repository/$image_name:latest-$environment
docker tag $image_name:latest-$environment $region/$project_id/$repository/$image_name:$tag-$environment

# Empujar la imagen a Artifact Registry
docker push $region/$project_id/$repository/$image_name:latest-$environment
docker push $region/$project_id/$repository/$image_name:$tag-$environment
