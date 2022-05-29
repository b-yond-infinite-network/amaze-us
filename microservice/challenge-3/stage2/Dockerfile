FROM python:3

WORKDIR /usr/src/app

COPY ./stage2/requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

# Copy app source
COPY ./stage2/ .

CMD tail -f /dev/null