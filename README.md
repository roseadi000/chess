# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

[Sequence Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YOlAowADWMABCwBwwGQCOqTmYwjUwpQq8GQ5nqgScTmG4zm6mA9nm9TGAFEoN46s9Xu8UF81GAXGMrugONdbg8qtRKDAUWjftVKYDKsVQTAAKxOYJQsYw1RwhHLanQepwXwIN5oCDMEAFNAocAwSAwAY1fGEzi0imwIFsTjcdHjSZQaZzc4HMYbVTbKC7RbLNZjI4nM42gmYbVcemVP6ieq3LI5SgACgyfrAlE+3zAAEp1VRRADKrJ5EoVOp6vCwABVAYBg1GlDRxOKZRqVTxow6WoAMSQnBgWcohZgOksMFzszENzuj3rsE2SDA8VbAzzMGACFJHBbKAAHtkwBpC8mSwCgV6VPUe4WY3GGeSavUFOO7pP2g90EjpypsAQCjH6UCmRgwezOdzeQt+ajBUOpu3R0fXhbeR7nQF0iUwRdi3UFdPWqb0YDQHwEAQbcVDLSCU1UWoQFJUMexzAZCwLbQl2goFjFqBQODeTdtFQwwgQwktsNwlAFB8AcA2ADj4iIiCSKg0tyIrKi3nYgctzXBitXYd10WDOcwzUJCsDdXUYL3Sh6n1Yd2yWRFbWWbiB3aCAQLQfTlkuO9NQfEEnxgcIIU5NtjRgAzTWM+JTPMyz9iuTsyULPQDCpL8oE0HQNMfMB6jZDlRi5Et30RYUkLFCUYClNAZTlBUB0MLR5BClAVTMNVwM8bw-H8aB2HhGJKzgJFpDgBQYAAGQgLJChiss-i0xpWg6boegMdR8jQKFXJQNZzUtDgAtJR4XjecNcRs6L7NimBwUhRK33hD9kXC+pVqxHEcjK8DAseAbYAFCL7q2kodvi19kqOxFHqFEUMslaVZWYBUlUoa61WemSdUGkYZrm-QLR2K41I9Pd4IQbqawDLqevWnJoyk9CBMwtMUEzbMZuIpNBLLCjq1rWj5CbFsZpJLsYAASTQKVUSButdLmUdayaBGFv46nMI0qT6kQ5D6Klul0R7AA5CVK18TgLyvG9CkhuzXufABGD7YS+z80Xqdp4kMGahbeeadiMAoAHJmGOMAQHiMC1SYsjVzg9d+YbOjbs57noFuOUexHWSYBFrYdnl3d7vqLmecjsB48Rq0tZQa9Js23cYqNk2eTNk6LZgK2bYFwxY4dq0nbQV2YHdz3veJJOE2J5icLuUNxPiLieL432hMqCjRJgQfJID6TKhR9EcZrDJVBU11ZPU5PFe0iZa78wyxi8nzQPcqyXT1xltvqJz9p0383I8u1j7M0+PKW9ngv0QxHsil7mXegdT6fIxhpVFAhTK2VcrAwgPKa2RhtAlXBsSSqVVfABC8CgdAMQ4iJEwdgnGvgsB9X9hqeoDRpBIg6kidoSJRrjVUJNYYL9zJszJOdPGWBL7AgNrtZyQDTYgJ+hiNa2IIzILYXdRWYUaTcOLqyF8Aiy5CNOvAP6ECAY5T5iDRWEjIYL03oNFh6AN7Q01LBEQgcMb2CIdjbqRDOEEznkTCWzEYDpkHsPEyr80BUyLJLYSVYaxiRHtoZmo4eIn0KKHDM0weLQCQAALxQMSMeCtLEoBlkhFChNt5kOnjxVWYB1Y+E1pePOOtC762ZOCY2SiUrLFiV5BJyTdiqlST3P2FjYyBxnnRXJ3dXGpg4CgbgA8eJeN4toPxpFx7llqNIUZZNDB9PkEnKGcl6iEI4qvdei90n7hgCMK4cjr6OX4ccyRMhEHfxkdAP+RczmAOhMA46YD-pZUBnlWBBUEHFW-hIyqXh0H+FuG8fw2AayPA6nOGAABxdsGgSHdMGg0OFNDRr2HbMwyJPirkcLERtU5vC9qlwaRXb8BLLp4g7lc56v9iUAMUS8wRx1hHvI0Z8rR3zFS6Npfo1ghj0TGMKPs0hPTMkwGQDkBFMIAzSrALKtQjiu7XKGVhdxZNPEipmTTQJ9MQkSTCc2CJ3jWExLiQOFpKTxb+OXHkiVWS5a5PFYNQeRSSllO1gXRlDlalkvLk0+JUAkkpNpWkh18FVnAFVWPWo6YYUysRQGXVASJ4VjhWTKVc4lULk6UJcV8ElWzwyRpReWyc2It2QgVSQqDkw2WFimEH4GjjCbSgDm0gPyG3CMEQIdpNjxH7CgaOelFimmSKAe4o7jTjrtO25W7Zx0XBgJ0E50jHm8NvlCRtiKW1tvbJ2vYppB3DpnQiMYE6EBTvPce+d7ZF1zGXauj+QUbmhV-sYf+DlnlJVZaldR4pNHQPlD8+BRVdAAtpUC6qAQOAAHY3BOBQE4GISJghwGagANngKxeFf4ijbX6jvIabQui9HbTis16AoQLvbK+lamJOFVKviSi5f7lFstUVS8RtLQ70vCixnhTKEoss499VRHKgNcpAzojUeiN0GLMfUEVawRh0bmMjOthbA59zkCgJVAY9OhiVSqgZaq7Wpk1WAbVuLzKpvtemoJtZo3hJFVcoNVqQ2tNtbMg58FZY5OcQ6t1hS1Yaw4LnfOt5fU7X9fUwNlr4jWraeVDp6r-O9NCWs8zcbjMGfbAGDT+ZfN6qcxmbARVDAKtzaVgJOnJXFv6cFjZup6j5dM8pGtpi5L1r1Lu5t5CD1zCPfUHtfaB1DrwvvOdyxJ0gGnTNy9965iPsyZelda6hPyO3aMAbah91jHbaN5byxT3TYfheq9N6lummK8+rbocv4fsE1+zdImA0gKk5Ar5MC4GFXfaVaDao0E1UsKMjGmwcFIASGAcHyEIBQ4AFIQBrPhwW-h5v3EI69Yj+TmgZnIz0SjSWolQmwNe8HUBhQYygGsY70gGMiIuhGbbZzSUJZUZXHjuIJH8ekQyjd1Sf3Mo4+S9lgGfvcr+6DKACmNRlnLaa7yPi1MU+AFTmn0B6eHsZz1re3T4IACtUdoEMybmsnXqVONLYxfNpMbMTJ1XVxz8yDUFKNUzE17mLXNO8zaiNDWnVBdt669E7rwulMi+U6LushesZqU4OpYnyWeeS-71L4FA+G6y57mNuX7cW7N4V4rDmyJOYAGbBPRygRsJqGdXKVTAIpMBPXpcswWnPkrAvrJReiJVHqItRcqbF58d8xflyby3tvtKXddLRoHJrayI1ObrJV-TNeYB9gHDAdXmuIC07n53hfjX2xblDk3jmpZKzZN75pfv7Yb-IWHz6+Pwm-Wi8OiAq22R3F-l-xXtkvyi1kppsjAEXp1mvN1mKmHtpOugru9g5Ltpck9oDnchFG9sLm9J-q8gBulJylAtomBgDv8gYICiDsCjVF4BrtDrDtQS2IgHcLAMANgBToQHkAUDADjuYHjochQlQjQnQr0MYPikxoSj8KPnwuPl-lxtzmIdSnzstPfugWzrwr+jIRJpXN9sBkQbyvJsAQgaAW1pcmKl3u1twHgAGIwXgGZsFoMh3uYUwSmkfrTBWIsmMoYIzDGivvMu4csh7lMsvvmq4QsksqGDXiWhKmWkKkKBYVANWrWmYn1nAaocyMgUzs9j-K9lFIgdgaJhPl9pLjoTyr8hBkgrPuBEAA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
