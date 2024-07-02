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

int main() {
    // Client setup
    int client_socket;
    struct sockaddr_in server_addr;

    client_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (client_socket == -1) {
        perror("Error creating socket");
        exit(EXIT_FAILURE);
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(PORT);

    if (connect(client_socket, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
        perror("Error connecting to the server");
        close(client_socket);
        exit(EXIT_FAILURE);
    }

    printf("Connected to the server.\n");

    // Get the number of packets and maximum number of tokens from the user
    int num_packets;
    printf("Enter the number of packets to send: ");
    scanf("%d", &num_packets);

    int max_tokens;
    printf("Enter the maximum number of tokens: ");
    scanf("%d", &max_tokens);

    // Send packets to the server periodically
    Packet packet;

    for (int i = 0; i < num_packets; i++) {
        printf("Enter size for Packet %d (in KB): ", i + 1);
        scanf("%d", &packet.packet_size);

        struct timeval current_time;
        gettimeofday(&current_time, NULL);
        packet.arrival_time = current_time.tv_sec;

        // Check if the packet size is within the maximum number of tokens
        if (packet.packet_size <= max_tokens) {
            send(client_socket, &packet, sizeof(packet), 0);
            max_tokens -= packet.packet_size;  // Update the remaining tokens
        } else {
            printf("Packet of size %d KB exceeds maximum tokens. Skipped.\n", packet.packet_size);
        }

        sleep(1);  // Wait for a second between sending packets
    }

    // Close the socket
    close(client_socket);

    return 0;
}

