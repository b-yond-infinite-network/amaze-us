# Stage 1: build
FROM node:12.14.1-stretch-slim as BUILDER
WORKDIR /app
COPY . ./
RUN npm i && npm run build


# Stage 2 - production
FROM nginx:1.17.7-alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d
COPY --from=BUILDER /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
