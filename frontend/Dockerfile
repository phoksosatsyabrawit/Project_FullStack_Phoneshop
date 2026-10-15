# ---- Stage 1: build (compiles Angular into plain HTML/CSS/JS) ----
# Use a Node version your Angular version supports (check `node -v` locally).
FROM node:22-alpine AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
RUN npm run build -- --configuration production

# ---- Stage 2: serve the files with nginx (already non-root, port 8080) ----
FROM nginxinc/nginx-unprivileged:1.27-alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/*/browser/ /usr/share/nginx/html/

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget -qO- http://127.0.0.1:8080/ || exit 1
