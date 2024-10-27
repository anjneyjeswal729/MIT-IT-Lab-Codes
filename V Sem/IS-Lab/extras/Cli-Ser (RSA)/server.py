import socket
import threading
import json
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.backends import default_backend

class KeyServer:
    def __init__(self, host='127.0.0.1', port=65432):
        self.server_address = (host, port)
        self.clients = {}  # Stores public keys by client ID
        self.private_keys = {}  # Private keys are stored securely

        # Start server socket
        self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.server_socket.bind(self.server_address)
        self.server_socket.listen()
        print(f"Server started on {host}:{port}")
        
        # Start accepting clients in a new thread
        threading.Thread(target=self.accept_clients, daemon=True).start()

    def accept_clients(self):
        while True:
            client_socket, client_address = self.server_socket.accept()
            print(f"Connected by {client_address}")
            threading.Thread(target=self.handle_client, args=(client_socket,), daemon=True).start()

    def handle_client(self, client_socket):
        with client_socket:
            while True:
                try:
                    data = client_socket.recv(4096).decode()
                    if not data:
                        break

                    request = json.loads(data)
                    action = request.get("action")
                    client_id = request.get("client_id")

                    if action == "register":
                        response = self.register_client(client_id)
                    elif action == "get_public_key":
                        target_id = request.get("target_id")
                        response = self.get_public_key(target_id)
                    else:
                        response = {"status": "error", "message": "Unknown action"}

                    client_socket.sendall(json.dumps(response).encode())

                except (ConnectionResetError, ConnectionAbortedError):
                    print("Connection with the client was lost.")
                    break

    def register_client(self, client_id):
        # Generate RSA key pair
        private_key = rsa.generate_private_key(
            public_exponent=65537,
            key_size=2048,
            backend=default_backend()
        )
        public_key = private_key.public_key()
        
        # Store the private and public keys securely
        self.private_keys[client_id] = private_key
        self.clients[client_id] = public_key
        
        # Serialize both keys to send them to the client
        private_pem = private_key.private_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PrivateFormat.PKCS8,
            encryption_algorithm=serialization.NoEncryption()
        ).decode()
        
        public_pem = public_key.public_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PublicFormat.SubjectPublicKeyInfo
        ).decode()
        
        print(f"Registered client {client_id} with a new key pair.")
        return {"status": "success", "private_key": private_pem, "public_key": public_pem}

    def get_public_key(self, target_id):
        public_key = self.clients.get(target_id)
        if public_key:
            public_pem = public_key.public_bytes(
                encoding=serialization.Encoding.PEM,
                format=serialization.PublicFormat.SubjectPublicKeyInfo
            ).decode()
            return {"status": "success", "public_key": public_pem}
        else:
            return {"status": "error", "message": "Public key not found"}

# Start the server
if __name__ == "__main__":
    KeyServer()
    input("Press Enter to stop the server...\n")
