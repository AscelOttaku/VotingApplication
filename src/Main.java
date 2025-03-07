import server.LocalVotingServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
             new LocalVotingServer(8080).lunchServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}