import socket
import json
from cryptography.hazmat.primitives import serialization

class KeyClient:
    def __init__(self, client_id, host='127.0.0.1', port=65432):
        self.client_id = client_id
        self.server_address = (host, port)
        self.private_key = None
        self.public_key = None

    def connect_to_server(self):
        try:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
                s.connect(self.server_address)

                # Register with the server to get private and public keys
                self.register_client(s)
                
                # Prompt to get the public key of another client
                while True:
                    target_id = input(f"\nEnter the ID of the client to fetch their public key (or 'exit' to quit): ")
                    if target_id.lower() == 'exit':
                        break
                    self.request_public_key(s, target_id)

        except ConnectionAbortedError:
            print("Connection was aborted. Please check the server and try again.")
        except ConnectionResetError:
            print("Connection was reset by the server. Please reconnect.")

    def register_client(self, server_socket):
        # Send registration request
        request = {"action": "register", "client_id": self.client_id}
        server_socket.sendall(json.dumps(request).encode())
        response = json.loads(server_socket.recv(4096).decode())
        
        if response.get("status") == "success":
            # Load the provided private and public keys
            private_pem = response["private_key"].encode()
            public_pem = response["public_key"].encode()
            
            self.private_key = serialization.load_pem_private_key(private_pem, password=None)
            self.public_key = serialization.load_pem_public_key(public_pem)
            
            print(f"Registered with ID {self.client_id}")
            print("Private Key:\n", private_pem.decode())
            print("Public Key:\n", public_pem.decode())
        else:
            print("Failed to register:", response.get("message"))

    def request_public_key(self, server_socket, target_id):
        # Request the public key of another client from the server
        request = {"action": "get_public_key", "client_id": self.client_id, "target_id": target_id}
        server_socket.sendall(json.dumps(request).encode())
        response = json.loads(server_socket.recv(4096).decode())

        if response.get("status") == "success":
            public_pem = response["public_key"].encode()
            print(f"Public key of {target_id}:\n{public_pem.decode()}")
        else:
            print("Failed to get public key:", response.get("message"))

# Run the client
if __name__ == "__main__":
    client_id = input("Enter your client ID: ")
    client = KeyClient(client_id=client_id)
    client.connect_to_server()
