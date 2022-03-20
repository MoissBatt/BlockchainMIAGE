FROM node:lts-alpine

RUN npm install -g http-server

WORKDIR /app

COPY package*.json ./

# installe les dépendances du projet
RUN npm install

COPY . .

RUN npm run build

EXPOSE 3000
CMD [ "http-server", "-p 3000", "dist" ]