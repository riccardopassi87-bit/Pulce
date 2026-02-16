# Use the Node.js image
FROM node:20

# Create the folder inside the container
WORKDIR /app

# Copy the package files first (to speed up builds)
COPY package*.json ./
RUN npm install

# Copy everything else
COPY . .

# Expose the port Vue uses
EXPOSE 5173

# Start the dev server and allow external connections
CMD ["npm", "run", "dev", "--", "--host"]