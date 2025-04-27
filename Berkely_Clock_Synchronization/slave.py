import socket
import datetime
import time
# Remember to run client 2 times as 
# Berkely algorithm synchronizes clocks on multiple clients at same time

MASTER_IP = 'localhost'
PORT = 12345

print("[Slave] Starting...")

s = socket.socket()
s.connect((MASTER_IP, PORT))

# Send current slave time
now = datetime.datetime.now().timestamp()
s.send(str(now).encode())

# Receive offset from master
offset = float(s.recv(1024).decode())
adjusted_time = datetime.datetime.fromtimestamp(now + offset)

print(f"[Slave] Received offset: {offset:.2f} seconds")
print(f"[Slave] Adjusted Time: {adjusted_time.strftime('%H:%M:%S')}")

s.close()
