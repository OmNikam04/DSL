import socket
import datetime

HOST = '0.0.0.0'  # Listen on all interfaces
PORT = 12345

client_times = []

s = socket.socket()
s.bind((HOST, PORT))
s.listen()

print("[Master] Waiting for slaves...")

# Number of slaves expected
N = 2

for _ in range(N):
    conn, addr = s.accept()
    print(f"[Master] Connected to {addr}")
    client_time = float(conn.recv(1024).decode())
    client_times.append((conn, client_time))

master_time = datetime.datetime.now().timestamp()
all_times = [master_time] + [time for _, time in client_times]
avg_time = sum(all_times) / len(all_times)

# Calculate and send offsets
for conn, time in client_times:
    offset = avg_time - time
    conn.send(str(offset).encode())
    conn.close()

print("[Master] Done synchronizing!")
s.close()
