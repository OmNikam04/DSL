import socket
import datetime
import time

# Remember to run client 2 times as 
# Berkely algorithm synchronizes clocks on multiple clients at same time
HOST = 'localhost'  # IP of master (update this)
PORT = 12345

def get_time():
    return datetime.datetime.now()

def main():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))

        # Send current time
        current_time = get_time()
        s.sendall(str(current_time.timestamp()).encode())

        # Receive offset
        offset = float(s.recv(1024).decode())
        print(f"[Slave] Received offset: {offset:.2f} seconds")

        # Adjust local time (simulation)
        adjusted_time = current_time + datetime.timedelta(seconds=offset)
        print(f"[Slave] Adjusted time: {adjusted_time.strftime('%H:%M:%S')}")

if __name__ == '__main__':
    main()
