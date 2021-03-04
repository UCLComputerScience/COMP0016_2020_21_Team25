DEFAULT_PORT = "8100" 

if __name__ == "__main__":
    import argparse
    from os import system

    parser = argparse.ArgumentParser(prog="Concierge", description='Run the backend as a RESTful API service on localhost.')
    parser.add_argument("-port", "--port", "-p", "--p", type=str, default=DEFAULT_PORT, nargs="?", 
                    help='(optional) choose to run on a desired port - default is ' + DEFAULT_PORT)
    parser.add_argument('-l', '--l', action="store_true", help='include logging information during execution')
    parser.add_argument('-jar', '--jar', action="store_true", help="build application into a single JAR file.")
    args = parser.parse_args()

    log = " l" if args.l else ""
    if (args.jar):
        cmd = "set -e; mvn clean package; java -jar target/backend-api-1.0-SNAPSHOT.jar port={PORT}{LOG}"
    else:
        cmd = "set -e; mvn clean; mvn org.springframework.boot:spring-boot-maven-plugin:run -Dspring-boot.run.arguments=\"--port={PORT}{LOG}\""
    system(cmd.format(LOG=log, PORT=args.port))