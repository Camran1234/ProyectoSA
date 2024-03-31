#Building the docker images
cd ../ProyectoSA_backend
docker build -t camranjuntinase/sa_pr:proyectosa_backend_1.0.5 .

cd ../ProyectoSA_frontend
docker build -t camranjuntinase/sa_pr:proyectosa_frontend_1.0.5 .

cd ..
#Pushing the docker images
docker push camranjuntinase/sa_pr:proyectosa_backend_1.0.5

docker push camranjuntinase/sa_pr:proyectosa_frontend_1.0.5

#Configure Project
gcloud config set project proyectosa-415901
gcloud container clusters get-credentials sa-pr --zone us-central1

#Deploying the docker images
kubectl apply -f k8s/development-configure.yaml
