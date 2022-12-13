# 开源生态 CICD 流程 TODO
<https://wangchujiang.com/github-actions>

```yaml
trigger:
  - RELEASE
workflow:
  - CI:
      maven:
        deploy: jar
        cache: update ~/.m2 path by docker
        CD: null🥶🥶🥶
      docker:
        build: image (maven package use cache)
        cache: update ~/.m2 path by docker
        push:
          - docker registry (docker.io)
          - github package docker registry (ghcr.io)
        CD:
          docker.image: k8s deployment+service
          k8s:
            - https://okteto.com
  - CICD:
      Dockerfile:
        build: image (maven package use cache)
        cache: update ~/.m2 path by docker
        deploy: SAAS
        SAAS:
          - https://render.com
          - https://railway.com
```
