# Stage 1 - Build Angular App inside temporary Node container
FROM node:10-alpine as builder

WORKDIR /usr/src/app
COPY . ./
RUN npm install
RUN npm run ng build  --prod

# Stage 2 - Deploy with NGNIX
FROM nginx:1.15.2-alpine

COPY --from=builder /usr/src/app/dist/boat-app /var/www
#COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 4200

ENTRYPOINT ["nginx","-g","daemon off;"]
