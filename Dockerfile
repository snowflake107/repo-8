FROM golang:1.15.11-alpine3.13

LABEL "com.github.actions.name"="Build Harness"
LABEL "com.github.actions.description"="Run any build-harness make target"
LABEL "com.github.actions.icon"="tool"
LABEL "com.github.actions.color"="blue"

RUN apk --update --no-cache add \
      bash \
      ca-certificates \
      coreutils \
      curl \
      gcc \
      git \
      gettext \
      go \
      grep \
      groff \
      jq \
      libc-dev \
      libc6-compat \
      libffi-dev \
      make \
      perl \
      python3-dev \
      py-pip \
      py3-ruamel.yaml && \
    python3 -m pip install --upgrade pip setuptools wheel && \
    pip3 install --no-cache-dir \
      cryptography==41.0.2 \
      awscli==1.29.9 \
      boto==2.49.0 \
      boto3==1.28.9 \
      iteration-utilities==0.11.0 \
      PyGithub==1.59 && \
    git config --global advice.detachedHead false

SHELL ["/bin/bash", "-o", "pipefail", "-c"]
RUN curl -fsSL --retry 3 https://apk.cloudposse.com/install.sh | bash

## Install as packages

## Codefresh required additional libraries for alpine
## So can not be curl binary
RUN apk --update --no-cache add \
      gomplate@cloudposse \
      helm@cloudposse \
      helmfile@cloudposse \
      terraform-0.15@cloudposse terraform-1@cloudposse \
      terraform-config-inspect@cloudposse \
      terraform-docs@cloudposse \
      vert@cloudposse \
      yq@cloudposse \
      fetch@cloudposse && \
    sed -i /PATH=/d /etc/profile

# Use Terraform 1 by default
ARG DEFAULT_TERRAFORM_VERSION=0.15
RUN update-alternatives --set terraform /usr/share/terraform/$DEFAULT_TERRAFORM_VERSION/bin/terraform && \
  mkdir -p /build-harness/vendor && \
  cp -p /usr/share/terraform/$DEFAULT_TERRAFORM_VERSION/bin/terraform /build-harness/vendor/terraform

# Patch for old Makefiles that expect a directory like x.x from the 0.x days.
# Fortunately, they only look for the current version, so we only need links
# for the current major version.
RUN v=$(curl -s https://checkpoint-api.hashicorp.com/v1/check/terraform | jq -r -M '.current_version' | cut -d. -f1-2) && \
    major=${v%%\.*} && n=$(( ${v##*\.} + 1 )) && \
    for (( x=0; x <= $n; x++ )); do ln -s /usr/local/terraform/{${major},${major}.${x}}; done

COPY ./ /build-harness/

ENV INSTALL_PATH /usr/local/bin

WORKDIR /build-harness

ARG PACKAGES_PREFER_HOST=true
RUN make -s bash/lint make/lint
RUN make -s readme/deps

ENTRYPOINT ["/usr/bin/make"]
