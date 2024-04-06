# JSON-Database-With-Java

This project acts as practice for creating connections between a server and multiple clients, one at a time, where JSON is used for input and output streams. A client sends one request to the server, gets one reply, then exits, and the server awaits for another connection with a new client. The server cannot shut down by itself; a client needs to send an exit request to the server for the server to stop.

For the client to send requests to the server, the client should provide all the information through command-line arguments. There are four types of requests: set, get, delete, and exit, and two types of responses: OK and ERROR. The JCommander library is used to parse the arguments and Gson library to work with JSON.

A JSON database is simulated using a map object in the server side where information is lost after the server closes.

Arguments passed by the client through command-line arguments should follow the following format:

-t [type of request] -k [key] -v "[value associated with the key]"

-t is the type of the request (either set, get, delete, or exit). -k is the key (in case of set, get, or delete). -v is the value to save in the database (in case of set only).

Example:

Starting the server:

    > java Main
    Server started!

Starting the clients:

Client 1:

    > java Main -t get -k 1
    Client started!
    Sent: {"type":"get","key":"1"}
    Received: {"response":"ERROR","reason":"No such key"}

Client 2:

    > java Main -t set -k 1 -v HelloWorld!
    Client started!
    Sent: {"type":"set","key":"1","value":"HelloWorld!"}
    Received: {"response":"OK"}

Client 3:

    > java Main -t get -k 1
    Client started!
    Sent: {"type":"get","key":"1"}
    Received: {"response":"OK","value":"HelloWorld!"}

Client 4:

    > java Main -t delete -k 1
    Client started!
    Sent: {"type":"delete","key":"1"}
    Received: {"response":"OK"}

Client 5:

    > java Main -t delete -k 1
    Client started!
    Sent: {"type":"delete","key":"1"}
    Received: {"response":"ERROR","reason":"No such key"}

Client 6:

    > java Main -t get -k 1
    Client started!
    Sent: {"type":"get","key":"1"}
    Received: {"response":"ERROR","reason":"No such key"}

Client 7:

    > java Main -t exit
    Client started!
    Sent: {"type":"exit"}
    Received: {"response":"OK"}
