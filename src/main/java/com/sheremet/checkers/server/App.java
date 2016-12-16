package com.sheremet.checkers.server;

import checkers.server.Main;
import checkers.server.Server;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args){
        Server server = new Server(3000);
        server.run();
    }
}
