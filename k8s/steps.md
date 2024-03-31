#Backend build
docker build -t camranjuntinase/sa_pr:proyectosa_backend .
#Frontend build
docker build -t camranjuntinase/sa_pr:proyectosa_frontendVV1 .

#Mysql build
docker build -t camranjuntinase/sa_pr:mysql_ticketsys .

#Dockers Push
docker push camranjuntinase/sa_pr:proyectosa_backend

docker push camranjuntinase/sa_pr:proyectosa_frontendVV1

docker push camranjuntinase/sa_pr:mysql_ticketsys

#Configure GCLOUD CLI
gcloud auth login
gcloud config set project proyectosa-415901

#Configurar cluster
gcloud container clusters get-credentials NOMBRE_DEL_CLUSTER --zone ZONA_DEL_CLUSTER
gcloud container clusters get-credentials sa-pr --zone us-central1

#Execute
kubectl apply -f k8s/

