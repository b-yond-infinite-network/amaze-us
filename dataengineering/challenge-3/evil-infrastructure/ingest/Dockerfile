ARG BASE_CONTAINER=python:3.7-slim-buster

# docker pull ubuntu:bionic
FROM $BASE_CONTAINER
## Base on https://github.com/jupyter/docker-stacks/blob/master/base-notebook/Dockerfile
LABEL maintainer="Nitsuga Chram <agustin.march@gmail.com>"
ARG NB_USER=analytics
ARG NB_UID=1000
ARG NB_GID=100
# default password for user
ARG PW=docker

USER root

ENV DEBIAN_FRONTEND noninteractive

# Option1: Using unencrypted password/ specifying password
RUN groupadd -g ${NB_GID} -o ${NB_GROUP:-${NB_USER}}
RUN useradd -m ${NB_USER} --uid=${NB_UID} -g ${NB_GID}  && echo "${NB_USER}:${PW}" | chpasswd
# Configure environment
ENV SHELL=/bin/bash \
    USER=$NB_USER \
    UID=$NB_UID \
    GID=$NB_GID

ENV HOME=/home/${NB_USER} \
    PATH=/home/${NB_USER}/.local/bin:${PATH}

##SUDO USERS
RUN apt-get update && apt-get install --no-install-recommends -y sudo && \
    echo "$NB_USER ALL=(ALL) NOPASSWD:ALL" > /etc/sudoers.d/notebook

RUN apt-get update && apt-get install --no-install-recommends -y \
    build-essential inkscape \
    pandoc texlive-xetex texlive-fonts-recommended jq \
    curl git zip unzip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy a script that we will use to correct permissions after running certain commands
COPY fix-permissions /usr/local/bin/fix-permissions
RUN chmod a+rx /usr/local/bin/fix-permissions

# RUN fix-permissions ${HOME}
# Enable prompt color in the skeleton .bashrc before creating the default NB_USER
RUN sed -i 's/^#force_color_prompt=yes/force_color_prompt=yes/' /etc/skel/.bashrc
USER $NB_UID
WORKDIR $HOME
# Setup work directory for backward-compatibility
RUN fix-permissions /home/${USER}
COPY requirements.txt .
RUN pip3 install -U -r requirements.txt
