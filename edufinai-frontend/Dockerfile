# Multi-stage build cho React app
# Stage 1: Build
FROM node:18-alpine AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci --only=production=false

# Copy source code
COPY . .

# Railway tự động inject tất cả environment variables vào build stage
# React sẽ tự động đọc các biến REACT_APP_* từ environment
# Không cần ARG vì Railway tự động pass env vars vào container

# Build app với environment variables từ Railway
# Railway sẽ tự động inject REACT_APP_* variables vào đây
RUN npm run build

# Stage 2: Production với Nginx
FROM nginx:alpine

# Railway tự động inject PORT (mặc định 80)
ENV PORT=80

# Copy custom nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy build files từ stage 1
COPY --from=build /app/build /usr/share/nginx/html

# Expose port (Railway sẽ tự động detect từ đây)
EXPOSE 80

# Start nginx
# Railway sẽ tự động map PORT env variable đến container port 80
CMD ["nginx", "-g", "daemon off;"]

