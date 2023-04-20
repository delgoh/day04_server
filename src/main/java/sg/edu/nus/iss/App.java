package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App 
{
    public static void main( String[] args ) throws NumberFormatException, IOException
    {
        System.out.println( "Hello Server!" );
        if (args.length != 2) {
            System.out.println("Please run the file with the following 2 arguments: <filename.txt> <Port>");
            System.exit(0);
        }

        String fileName = args[0];
        String port = args[1];

        File cookieFile = new File(fileName);
        if (!cookieFile.exists()) {
            System.out.println("Cookie file not found.");
            System.exit(0);
        } else {
            System.out.println("hi");
        }

        String msgReceived = "";

        ServerSocket ss = new ServerSocket(Integer.parseInt(port));
        Socket socket = ss.accept();

        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            
            try (OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                while (!msgReceived.equals("close")) {
                    msgReceived = dis.readUTF();

                    if (msgReceived.equals("get-cookie")) {
                        Cookie cookieInstance = new Cookie();
                        cookieInstance.readCookieFile(fileName);
                        dos.writeUTF(cookieInstance.getRandomCookie());
                        dos.flush();

                        System.out.println("Cookie sent out.");
                    } else if (msgReceived.equals("close")) {
                        dos.writeUTF("Bye bye");
                        dos.flush();

                        System.out.println("Server closed by client");
                    } else {
                        dos.writeUTF("Invalid command");
                        dos.flush();
                    }
                }

                dos.close();
                bos.close();
                os.close();

            } catch (EOFException e) {
                System.out.println("this error ran");
                e.printStackTrace();
            }

            dis.close();
            bis.close();
            is.close();

        } catch (EOFException e) {
            System.out.println("it is not opening stream");
            socket.close();
            ss.close();
        }
    }
}
