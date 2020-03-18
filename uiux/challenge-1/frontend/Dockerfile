FROM node:alpine as builder

# Setting work directory
WORKDIR /drunk-karaoke-frontend

# Copy all the files
COPY . ./

# Install depds and build
RUN npm install
RUN npm run build

# Download nginx image
FROM nginx:1.14.1-alpine

# Copy the nginx conf
COPY nginx/default.conf /etc/nginx/conf.d/
RUN rm -rf /usr/share/nginx/html/*

# Copy dist/* files to nginx/html. This server listens on 9000
COPY --from=builder /drunk-karaoke-frontend/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
