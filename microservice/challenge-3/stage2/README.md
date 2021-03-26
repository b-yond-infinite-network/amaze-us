# Stage 2

This app validates the fuel state on the booster and execute stage 2 if all the fuel was consumed.

## Run the app

_This app is tested with python 3._

First, create the virtual environment and activate it:
```bash
python3 -m venv venv
source venv/bin/activate
pip3 install -r requirements.txt
```

Now, to run the app:
```bash
python3 stage2.py
```

To run the tests:
```bash
python3 -m unittest
```

## App configurations

The app has the following environment variables:

| Variable | Default | Description |
| :------: | :-----: | ----------- |
| BOOSTER_URL | http://localhost:3000 | Url to the booster API |
| TANKS | Tank1,Tank2 | Tanks titles separated by comma (',') |

## Docker

To build a docker image with the stage 2 app, you can run the following command, replacing the image name and version:
```bash
docker build -t <image_name>:<version> .
```
