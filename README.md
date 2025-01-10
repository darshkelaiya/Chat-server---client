
# Chat Application (Server-Client Model) in Java

This project is a simple chat application that uses a client-server model with a graphical user interface (GUI) for the client. The server handles multiple client connections simultaneously and facilitates message broadcasting among all connected clients.

## Features

- **Server-side**:
  - Handles multiple clients concurrently using threads.
  - Broadcasts messages from one client to all other connected clients.
  - Manages client connections and disconnections.

- **Client-side**:
  - Provides a simple GUI to send and receive messages.
  - Messages are displayed in a text area.
  - Supports real-time message updates from the server.

## Technologies Used

- **Java**:
  - Java Socket Programming for client-server communication.
  - Java Swing for creating the GUI for the client.
  - Multi-threading for handling concurrent client connections.

- **Other**:
  - BufferedReader and PrintWriter for reading and sending messages.

## How to Run

1. Clone or download this repository to your local machine.
2. Compile the Java files using `javac`:
   ```bash
   javac OSServer.java
   javac ChatClientGUI.java
   ```
3. First, run the **server** (OSServer):
   ```bash
   java OSServer
   ```
4. Then, run the **client** (ChatClientGUI):
   ```bash
   java ChatClientGUI
   ```

5. You can open multiple client windows to simulate communication between multiple clients.

## How to Use

1. **Server**:
   - The server listens on port `1235` and waits for incoming client connections.
   - Once a client connects, the server starts a new thread to handle that client's messages.
   - The server broadcasts messages to all connected clients, except the sender.

2. **Client**:
   - The client connects to the server at IP `127.0.0.1` and port `8080`.
   - Messages can be typed into the text field and sent by clicking the "Send" button.
   - Incoming messages from the server are displayed in the chat area.

## Example

1. Start the server by running `java OSServer`.
2. Start the client by running `java ChatClientGUI`.
3. Type a message in the client’s text field and click "Send."
4. The message is broadcasted to all other connected clients, and it will appear in their respective chat areas.

## Code Explanation

### **OSServer.java**
- The server listens for incoming client connections on port `1235`.
- Each client connection is handled by a separate `OSClientHandler` thread.
- The server maintains a set of connected clients and broadcasts received messages to all clients except the sender.

### **OSClientHandler.java**
- A `clientHandler` class that manages individual client connections.
- Reads messages from the client and forwards them to the server for broadcasting.
- When a client disconnects, it is removed from the set of connected clients.

### **ChatClientGUI.java**
- A simple GUI-based client application using Java Swing.
- Allows users to type and send messages to the server.
- Displays incoming messages from other clients in the chat area.

## Potential Improvements

- Add user authentication (e.g., login, registration).
- Implement private messaging between specific clients.
- Enhance the GUI with more features like displaying active users.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

This README provides a clear overview of how the chat application works, how to set it up, and the main components of the code.
