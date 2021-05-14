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

# ## Install AWS CLI 2
# RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" && \
#     unzip awscliv2.zip && \
#     ./aws/install 

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
RUN pip3 install -U jupyter-book yapf \
    isort autopep8 RISE ipywidgets \
    boto3 iplantuml ruamel-yaml pandas matplotlib seaborn pandoc \
    numpy pyarrow \
    scipy statsmodels \
    psutil nbresuse \
    psycopg2-binary \
    jupyter_contrib_nbextensions jupyterthemes && \
    jupyter contrib nbextension install --user


RUN jupyter nbextension enable codefolding/main && \
    jupyter nbextension enable spellchecker/main && \
    jupyter nbextension enable collapsible_headings/main && \
    jupyter nbextension enable snippets_menu/main && \
    jupyter nbextension enable code_prettify/code_prettify && \
    jupyter nbextension enable code_prettify/isort && \
    jupyter nbextension enable execute_time/ExecuteTime && \
    jupyter nbextension enable zenmode/main && \
    jupyter nbextension enable toc2/main && \
    jupyter nbextension enable toggle_all_line_numbers/main && \
    jupyter nbextension enable splitcell/splitcell && \
    jupyter nbextension enable freeze/main && \
    jupyter nbextension enable scratchpad/main && \
    jupyter nbextension enable rise/main && \
    jupyter nbextension enable highlighter/highlighter && \
    jupyter nbextension enable hide_input/main && \
    jupyter nbextension enable move_selected_cells/main && \
    jupyter nbextension enable hide_input_all/main

#
# Copy local files as late as possible to avoid cache busting
COPY start.sh start-notebook.sh start-singleuser.sh /usr/local/bin/

USER root
RUN jupyter serverextension enable --py nbresuse --sys-prefix && \
    jupyter nbextension install --py nbresuse --sys-prefix && \
    jupyter nbextension enable --py nbresuse --sys-prefix && \
    jupyter nbextension enable nbresuse --py --sys-prefix

ENV APACHE_SPARK_VERSION=2.4.7 \
    HADOOP_VERSION=2.7

RUN mkdir --parent /usr/share/man/man1 && \
    apt-get -y update && \
    apt-get install --no-install-recommends -y openjdk-11-jre-headless ca-certificates-java wget gnupg && \
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    java -version


# RUN which java
ENV JAVA_HOME=/usr/

# 3.1.1 0E35D769AF1D94484AFAB95E2759FBD34E692AA2A6DB2FEFB0EE654BE9DDA1F01C96412F03B5E6AE4BDBA69DC622DD12CAC95002BEAB24096FD315EEF2A5F245
# 3.0.2 E90B31E58F6D95A42900BA4D288261D71F6C19FA39C1CB71862B792D1B5564941A320227F6AB0E09D946F16B8C1969ED2DEA2A369EC8F9D2D7099189234DE1BE
# 2.4.7 2.7 0F5455672045F6110B030CE343C049855B7BA86C0ECB5E39A075FF9D093C7F648DA55DED12E72FFE65D84C32DCD5418A6D764F2D6295A3F894A4286CC80EF478
# Using the preferred mirror to download the file
RUN cd /tmp && \
    wget -q $(wget -qO- https://www.apache.org/dyn/closer.lua/spark/spark-${APACHE_SPARK_VERSION}/spark-${APACHE_SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz\?as_json | \
    python -c "import sys, json; content=json.load(sys.stdin); print(content['preferred']+content['path_info'])") && \
    echo "0F5455672045F6110B030CE343C049855B7BA86C0ECB5E39A075FF9D093C7F648DA55DED12E72FFE65D84C32DCD5418A6D764F2D6295A3F894A4286CC80EF478 *spark-${APACHE_SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz" | sha512sum -c - && \
    tar xzf spark-${APACHE_SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz -C /usr/local --owner root --group root --no-same-owner && \
    rm spark-${APACHE_SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz
RUN cd /usr/local && ln -s spark-${APACHE_SPARK_VERSION}-bin-hadoop${HADOOP_VERSION} spark

ENV SPARK_HOME=/usr/local/spark
ENV PYTHONPATH=$SPARK_HOME/python:$SPARK_HOME/python/lib/py4j-0.10.7-src.zip:$SPARK_HOME/python/lib/pyspark.zip:${HOME}/workspaces/ 
ENV SPARK_OPTS="--driver-java-options=-Xms8192M --driver-java-options=-Xmx16834M --driver-java-options=-Dlog4j.logLevel=info"
ENV PATH=$PATH:$SPARK_HOME/bin

USER $NB_UID
# RUN jt -r
# EXPOSE 9001
EXPOSE 4040
EXPOSE 8000