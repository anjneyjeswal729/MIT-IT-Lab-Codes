// server.c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <sys/time.h>

#define PORT 1234

typedef struct {
    int packet_size;
    int arrival_time;
} Packet;

typedef struct {
    double last_update_time;
    int tokens;  // Changed data type to int for discrete token amounts
    double max_rate;
    double replenishment_rate;
} TokenBucket;

void updateBucket(TokenBucket *bucket) {
    struct timeval current_time;
    gettimeofday(&current_time, NULL);
    double elapsed_time = (current_time.tv_sec - bucket->last_update_time) + (current_time.tv_usec - bucket->last_update_time) / 1000000.0;
    double tokens_added = elapsed_time * bucket->replenishment_rate;

    bucket->tokens = (bucket->tokens + tokens_added) > bucket->max_rate ? (int)bucket->max_rate : (int)(bucket->tokens + tokens_added);
    bucket->last_update_time = current_time.tv_sec + (current_time.tv_usec / 1000000.0);
}

int checkTokenBucket(TokenBucket *bucket, int packet_size) {
    updateBucket(bucket);

    if (bucket->tokens >= packet_size) {
        bucket->tokens -= packet_size;
        return 1;  // Packet can pass
    } else {
        return 0;  // Packet will be queued or dropped
    }
}

int main() {
    TokenBucket bucket;

    printf("Enter the token bucket parameters:\n");

    printf("Enter the maximum rate (in KBps): ");
    scanf("%lf", &bucket.max_rate);

    printf("Enter the replenishment rate (in KBps): ");
    scanf("%lf", &bucket.replenishment_rate);

    printf("Enter the bucket size (in KB): ");
    scanf("%d", &bucket.tokens);

    printf("Enter the initial number of tokens (in KB): ");  // Added user input for initial tokens
    scanf("%d", &bucket.tokens);

    bucket.last_update_time = 0;
    // Server setup
    int server_socket, client_socket;
    struct sockaddr_in server_addr, client_addr;
    socklen_t addr_len = sizeof(client_addr);

    server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (server_socket == -1) {
        perror("Error creating socket");
        exit(EXIT_FAILURE);
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(PORT);

    if (bind(server_socket, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
        perror("Error binding");
        close(server_socket);
        exit(EXIT_FAILURE);
    }

    if (listen(server_socket, 5) == -1) {
        perror("Error listening");
        close(server_socket);
        exit(EXIT_FAILURE);
    }

    printf("Token Bucket Server is waiting for connections...\n");

    // Accept client connection
    client_socket = accept(server_socket, (struct sockaddr *)&client_addr, &addr_len);
    if (client_socket == -1) {
        perror("Error accepting connection");
        close(server_socket);
        exit(EXIT_FAILURE);
    }

    printf("Client connected.\n");

    // Handle incoming packets and token bucket
  Packet packet;
	while (1) {
    ssize_t recv_size = recv(client_socket, &packet, sizeof(packet), 0);

    if (recv_size <= 0) {
        printf("Connection closed by the client.\n");
        break;
    }

    printf("Received packet of size %d KB at time %d seconds.\n", packet.packet_size, packet.arrival_time);

    // Check if the packet size is within the available tokens in the bucket
    if (checkTokenBucket(&bucket, packet.packet_size)) {
        printf("Packet of size %d KB passed through the token bucket.\n", packet.packet_size);
    } else {
        printf("Packet of size %d KB queued or dropped due to insufficient tokens.\n", packet.packet_size);
    }
}

    // Close sockets
    close(client_socket);
    close(server_socket);

    return 0;
}

