FROM node:lts-alpine
ENV NODE_ENV=production
WORKDIR /usr/src/frontend
COPY . .
RUN chown -R node /usr/src/frontend
RUN npm install --production --silent
EXPOSE 8080
USER node
CMD ["npm", "run", "dev"]
