# shopping-demo

This is a sample demonstration of projectriff, in which we create a simple shopping cart.
User actions such as adding and removing items arrive as input. These events are then used
to compute the final state of the cart and display targeted advertisements.

## Prerequisites

1. install [helm 2.13](https://github.com/helm/helm/releases/tag/v2.13.1) cli
1. initialize helm
    ```sh
    kubectl create serviceaccount tiller -n kube-system
    kubectl create clusterrolebinding tiller --clusterrole cluster-admin --serviceaccount kube-system:tiller
    helm init --wait --service-account tiller
    ```
1. add projectriff helm repo:
    ```sh
    helm repo add incubator http://storage.googleapis.com/kubernetes-charts-incubator
    helm repo update
    ```
1. install riff with core and streaming runtimes
    ```sh
    helm install projectriff/riff --name riff --set riff.runtimes.core.enabled=true --set riff.runtimes.streaming.enabled=true --devel
    ```
1. install kafka:
    ```sh
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm install --name my-kafka bitnami/kafka
    ```
1. setup kafka provider:
    ```
    cat <<EOF | kubectl create -f -
    ---
    apiVersion: streaming.projectriff.io/v1alpha1
    kind: KafkaProvider
    metadata:
      name: franz
    spec:
      bootstrapServers: my-kafka:9092
    EOF
    ```
1. install riff HTTP gateway:
    ```
    kubectl apply -f https://storage.googleapis.com/projectriff/riff-http-gateway/riff-http-gateway-0.5.0-snapshot.yaml
    ```
1. configure a container registry for riff to push built images:
    ```sh
    riff credentials apply my-gcr --gcr <path to service account token file> --set-default-image-prefix
    ```

## Set up application infrastructure

### Ad pipeline

Run:
```
DOCKER_REPO="your-username" ./run-pipeline.sh ad
```

### Cart pipeline

Run:
```
DOCKER_REPO="your-username" ./run-pipeline.sh cart
```
   
## Data ingestion

### Ads

```sh
curl http://localhost:8080 --header 'Content-Type:application/json' --data '{"itemId": 123, "message": "some great product"}'
```
### Cart events

```sh
curl http://localhost:9090 --header 'Content-Type:application/json' --data '{"userId": 42, "itemId": 123, "action": "REMOVE"}'
```

## Stream processing

### Cart State

```bash
riff streaming stream create cart-aggregates --provider franz-kafka-provisioner --content-type 'application/json'
riff streaming processor create cart --function-ref cart --input cart-events --output cart-aggregates
```

## Redis persistence

1. start redis
```bash
helm install stable/redis --name my-redis --set usePassword=false
```
