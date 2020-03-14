FROM node:alpine as builder
WORKDIR /drunk-karaoke-frontend
COPY . ./
RUN npm install
RUN npm run build
FROM nginx:1.14.1-alpine
COPY nginx/default.conf /etc/nginx/conf.d/
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /drunk-karaoke-frontend/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
