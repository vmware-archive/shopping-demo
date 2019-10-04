# shopping-demo

This is a sample demonstration of projectriff, in which we create a simple shopping cart.
User actions such as adding and removing items arrive as input. These events are then used
to compute the final state of the cart and display targeted advertisements.


## Install -- WORK IN PROGRESS

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
    helm repo add incubator http://storage.googleapis.com/kubernetes-charts-incubator
    helm repo update
    helm install --name my-kafka incubator/kafka
    ```
1. install riff HTTP gateway (follow the [README](https://github.com/projectriff/http-gateway))
1. configure a container registry for riff to push built images:
    ```sh
    riff credentials apply my-gcr --gcr <path to service account token file> --set-default-image-prefix
    ```
1. create streams:
    ```sh
    riff streaming stream create ad-events --provider my-kafka --content-type application/json
    riff streaming stream create cart-events --provider my-kafka --content-type application/json
    ```
1. create functions:
    ```sh
    riff function create ad-ingest \
        --git-repo https://github.com/sbawaska/shopping-demo.git \
        --sub-path ad-ingest/ \
        --tail
    riff function create cart-ingest \
        --git-repo https://github.com/sbawaska/shopping-demo.git \
        --sub-path cart-ingest/ \
        --tail
    ```
1. create deployers:
    ```sh
   riff core deployer create ad-ingest --function-ref ad-ingest
   riff core deployer create cart-ingest --function-ref cart-ingest
    ```
   
## Data ingestion

### Ads

In 1 terminal:
```sh
kubectl port-forward svc/ad-ingest-deployer 8080:80
```
In another one:
```sh
curl http://localhost:8080 --header 'Content-Type:application/json'  --data '{"itemId": 123, "message": "some great product"}'
```